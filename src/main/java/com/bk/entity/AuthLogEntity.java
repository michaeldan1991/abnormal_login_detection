package com.bk.entity;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_log")
public class AuthLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int userId;
    private String timestamp;
    private String loginStatus;
    private String ipAddress;
    private String deviceType;
    private String location;
    private double sessionDuration;
    private int failedAttempts;
    private double behavioralScore;
    private int anomaly;
    @CreationTimestamp
    private LocalDateTime createDate;

}
