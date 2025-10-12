package com.bk.service;

import com.bk.dto.PartnerBaseResponse;
import com.bk.dto.PredictionData;
import com.bk.entity.AuthLogEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ExternalApiServiceImpl implements ExternalApiService {

    private static final Logger log = LoggerFactory.getLogger(ExternalApiServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public PredictionData callCheckLoginDetect(AuthLogEntity entity) {
        try {
            String url = "http://localhost:5000/predict";
            List<Object> features = List.of(
                    entity.getUserId(),
                    entity.getTimestamp(),
                    entity.getLoginStatus(),
                    entity.getIpAddress(),
                    entity.getDeviceType(),
                    entity.getLocation(),
                    entity.getSessionDuration(),
                    entity.getFailedAttempts(),
                    entity.getBehavioralScore()
            );

            Map<String, Object> bodyRequest = new HashMap<>();
            bodyRequest.put("features", features);
            HttpEntity<Object> body = new HttpEntity<>(bodyRequest);

            ResponseEntity<PartnerBaseResponse<PredictionData>> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            body,
                            new ParameterizedTypeReference<PartnerBaseResponse<PredictionData>>() {
                            }
                    );

            PartnerBaseResponse<PredictionData> responseBody = response.getBody();
            return responseBody.getData();
        } catch (Exception e) {
            log.error("Call predict failed", e);
            return null;
        }
    }
}
