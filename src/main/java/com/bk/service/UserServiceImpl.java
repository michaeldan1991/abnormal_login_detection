package com.bk.service;

import com.bk.entity.AuthLogEntity;
import com.bk.entity.MetricEntity;
import com.bk.repo.AuthLogRepository;
import com.bk.repo.MetricRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthLogRepository authLogRepository;
    @Autowired
    private MetricRepository metricRepository;
    @Autowired
    private ExternalApiService externalApiService;

    @Override
    public void detectAbnormal() {
        var allAuthLogs = authLogRepository.findAll();
        List<MetricEntity> metricEntities = new ArrayList<>();
        for (AuthLogEntity e : allAuthLogs) {
            long start = System.currentTimeMillis();
            var metric = new MetricEntity();
            metric.setUserId(e.getUserId());
            metric.setAnomaly(e.getAnomaly());
            var predictData = externalApiService.callCheckLoginDetect(e);
            metric.setPredict(predictData.getPrediction());
            long end = System.currentTimeMillis();
            metric.setType(1);
            metric.setTook(end - start);
            metric.setModel(predictData.getModel());
            metricEntities.add(metric);
        }
        metricRepository.saveAll(metricEntities);
    }

    @Override
    public void detectAbnormalParallel() {
        var allAuthLogs = authLogRepository.findAll();

        ExecutorService executor = Executors.newFixedThreadPool(100); // Run 10 request at the same time

        List<CompletableFuture<MetricEntity>> futures = allAuthLogs.stream()
                .map(e ->
                        CompletableFuture.supplyAsync(() -> {
                            long start = System.currentTimeMillis();

                            var metric = new MetricEntity();
                            metric.setUserId(e.getUserId());
                            metric.setAnomaly(e.getAnomaly());

                            var predictData = externalApiService.callCheckLoginDetect(e);

                            long end = System.currentTimeMillis();
                            metric.setType(100);
                            metric.setPredict(predictData.getPrediction());
                            metric.setTook(end - start);
                            metric.setModel(predictData.getModel());

                            return metric;
                        }, executor)
                )
                .toList();

        List<MetricEntity> result = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        metricRepository.saveAll(result);

        executor.shutdown();
    }
}
