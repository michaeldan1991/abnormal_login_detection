package com.bk.repo;

import com.bk.entity.MetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepository extends JpaRepository<MetricEntity, Long> {
}