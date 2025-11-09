create database thesis;
use thesis;
SELECT count(*) FROM thesis.auth_log;

-- thesis.auth_log definition

CREATE TABLE `auth_log` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `anomaly` int NOT NULL,
                            `behavioral_score` double NOT NULL,
                            `device_type` varchar(255) DEFAULT NULL,
                            `failed_attempts` int NOT NULL,
                            `ip_address` varchar(255) DEFAULT NULL,
                            `location` varchar(255) DEFAULT NULL,
                            `login_status` varchar(255) DEFAULT NULL,
                            `session_duration` double NOT NULL,
                            `timestamp` varchar(255) DEFAULT NULL,
                            `user_id` int NOT NULL,
                            `create_date` datetime(6) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `metric` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `anomaly` int NOT NULL,
                          `predict` int NOT NULL,
                          `took` bigint NOT NULL,
                          `user_id` int NOT NULL,
                          `type` int NOT NULL,
                          `create_date` datetime(6) DEFAULT NULL,
                          `model` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- LR --
select count(*) from metric where model = 'LR' and anomaly = predict and type=1;
select count(*) from metric where model = 'LR' and anomaly != predict and type=1;
select count(*) from metric where model = 'LR' and anomaly = 1 and predict = 0 and type=1;
select count(*) from metric where model = 'LR' and anomaly = 0 and predict = 1 and type=1;
select max(took), min(took), avg(took) from metric where model = 'LR' and type=1;
select max(took), min(took), avg(took) from metric where model = 'LR' and type=10;
select max(took), min(took), avg(took) from metric where model = 'LR' and type=100;

-- SVM --
select count(*) from metric where model = 'SVM' and anomaly = predict and type=1;
select count(*) from metric where model = 'SVM' and anomaly != predict and type=1;
select count(*) from metric where model = 'SVM' and anomaly = 1 and predict = 0 and type=1;
select count(*) from metric where model = 'SVM' and anomaly = 0 and predict = 1 and type=1;
select max(took), min(took), avg(took) from metric where model = 'SVM' and type=1;
select max(took), min(took), avg(took) from metric where model = 'SVM' and type=10;
select max(took), min(took), avg(took) from metric where model = 'SVM' and type=100;

DESCRIBE thesis.auth_log;
DESCRIBE thesis.metric;
select * from metric order by took desc;

