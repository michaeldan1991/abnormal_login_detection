package com.bk.repo;

import com.bk.entity.AuthLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthLogRepository extends JpaRepository<AuthLogEntity, Long> {
    @Query("SELECT DISTINCT a.userId FROM AuthLogEntity a")
    List<Integer> findDistinctUserIds();
}