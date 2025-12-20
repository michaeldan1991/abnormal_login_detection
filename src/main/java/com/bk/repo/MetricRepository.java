package com.bk.repo;

import com.bk.dto.TookStats;
import com.bk.entity.MetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MetricRepository extends JpaRepository<MetricEntity, Long> {
    @Query("""
                SELECT COUNT(m)
                FROM MetricEntity m
                WHERE m.model = :model
                  AND m.anomaly = m.predict
                  AND m.type = :type
            """)
    long countCorrectPrediction(
            @Param("model") String model,
            @Param("type") int type
    );

    @Query("""
                SELECT COUNT(m)
                FROM MetricEntity m
                WHERE m.model = :model
                  AND m.anomaly <> m.predict
                  AND m.type = :type
            """)
    long countWrongPrediction(
            @Param("model") String model,
            @Param("type") int type
    );

    @Query("""
                SELECT COUNT(m)
                FROM MetricEntity m
                WHERE m.model = :model
                  AND m.anomaly = 1
                  AND m.predict = 0
                  AND m.type = :type
            """)
    long countFalseNegative(
            @Param("model") String model,
            @Param("type") int type
    );

    @Query("""
                SELECT COUNT(m)
                FROM MetricEntity m
                WHERE m.model = :model
                  AND m.anomaly = 0
                  AND m.predict = 1
                  AND m.type = :type
            """)
    long countFalsePositive(
            @Param("model") String model,
            @Param("type") int type
    );

    @Query("""
                SELECT 
                    MAX(m.took) AS maxTook,
                    MIN(m.took) AS minTook,
                    AVG(m.took) AS avgTook
                FROM MetricEntity m
                WHERE m.model = :model
                  AND m.type = :type
            """)
    TookStats getTookStatistics(
            @Param("model") String model,
            @Param("type") int type
    );

}