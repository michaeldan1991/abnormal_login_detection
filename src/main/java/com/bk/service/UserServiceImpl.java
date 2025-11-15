package com.bk.service;

import com.bk.entity.AuthLogEntity;
import com.bk.entity.MetricEntity;
import com.bk.repo.AuthLogRepository;
import com.bk.repo.MetricRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UserServiceImpl implements UserService {
    @Value("${system.config.mail.admin}")
    private String mailAdmin;
    @Value("${system.config.mail.user.enable}")
    private String enableSendMailUser;
    @Autowired
    private MailService mailService;
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
        List<Integer> userIdAbnormal = new ArrayList<>();
        for (AuthLogEntity e : allAuthLogs) {
            long start = System.currentTimeMillis();
            var metric = new MetricEntity();
            metric.setUserId(e.getUserId());
            metric.setAnomaly(e.getAnomaly());
            var predictData = externalApiService.callCheckLoginDetect(e);
            if (1 == predictData.getPrediction()) {
                userIdAbnormal.add(e.getUserId());
            }
            metric.setPredict(predictData.getPrediction());
            long end = System.currentTimeMillis();
            metric.setType(1);
            metric.setTook(end - start);
            metric.setModel(predictData.getModel());
            metricEntities.add(metric);
        }
        if(StringUtils.isNotBlank(mailAdmin) && !userIdAbnormal.isEmpty()) {
            String[] mailList = mailAdmin.split(";");
            mailService.sendSimpleEmail(mailList, "[System Alert] Abnormal login detect!!!", "Abnormal login detect for user list below: " + userIdAbnormal);
        }
        metricRepository.saveAll(metricEntities);
    }

    @Override
    public void detectAbnormalParallel() {
        var allAuthLogs = authLogRepository.findAll();

        ExecutorService executor = Executors.newFixedThreadPool(100); // Run 10 request at the same time
        List<Integer> userIdAbnormal = new ArrayList<>();
        List<CompletableFuture<MetricEntity>> futures = allAuthLogs.stream()
                .map(e ->
                        CompletableFuture.supplyAsync(() -> {
                            long start = System.currentTimeMillis();

                            var metric = new MetricEntity();
                            metric.setUserId(e.getUserId());
                            metric.setAnomaly(e.getAnomaly());

                            var predictData = externalApiService.callCheckLoginDetect(e);
                            if (1 == predictData.getPrediction()) {
                                userIdAbnormal.add(e.getUserId());
                            }
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
        if(StringUtils.isNotBlank(mailAdmin) && !userIdAbnormal.isEmpty()) {
            String[] mailList = mailAdmin.split(";");
            mailService.sendSimpleEmail(mailList, "[System Alert] Abnormal login detect!!!", "Abnormal login detect for user list below: " + userIdAbnormal);
        }
        metricRepository.saveAll(result);

        executor.shutdown();
    }
}
