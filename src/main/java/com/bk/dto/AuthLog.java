package com.bk.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthLog {

    @CsvBindByName(column = "User ID")
    private int userId;
    @CsvBindByName(column = "Timestamp")
    private String timestamp;
    @CsvBindByName(column = "Login Status")
    private String loginStatus;
    @CsvBindByName(column = "IP Address")
    private String ipAddress;
    @CsvBindByName(column = "Device Type")
    private String deviceType;
    @CsvBindByName(column = "Location")
    private String location;
    @CsvBindByName(column = "Session Duration")
    private double sessionDuration;
    @CsvBindByName(column = "Failed Attempts")
    private int failedAttempts;
    @CsvBindByName(column = "Behavioral Score")
    private double behavioralScore;
    @CsvBindByName(column = "Anomaly")
    private int anomaly;
    @CsvBindByName(column = "Is Night")
    private int isNight;

    @Override
    public String toString() {
        return "AuthLog{" +
                "userId=" + userId +
                ", timestamp='" + timestamp + '\'' +
                ", loginStatus='" + loginStatus + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", location='" + location + '\'' +
                ", sessionDuration=" + sessionDuration +
                ", failedAttempts=" + failedAttempts +
                ", behavioralScore=" + behavioralScore +
                ", anomaly=" + anomaly +
                '}';
    }
}
