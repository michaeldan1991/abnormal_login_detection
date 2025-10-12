package com.bk.service;

import com.bk.dto.PredictionData;
import com.bk.entity.AuthLogEntity;

public interface ExternalApiService {
    PredictionData callCheckLoginDetect(AuthLogEntity entity);
}
