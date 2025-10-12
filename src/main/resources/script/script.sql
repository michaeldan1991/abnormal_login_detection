create database thesis;
use thesis;
SELECT count(*) FROM thesis.auth_log;

-- LR --
select count(*) from metric where model = 'LR' and anomaly != predict;
select count(*) from metric where model = 'LR' and anomaly = 1 and predict = 0;
select count(*) from metric where model = 'LR' and anomaly = 0 and predict = 1;
select max(took), min(took), avg(took) from metric where model = 'LR';

-- SVM --
select count(*) from metric where model = 'SVM' and anomaly != predict;
select count(*) from metric where model = 'SVM' and anomaly = 1 and predict = 0;
select count(*) from metric where model = 'SVM' and anomaly = 0 and predict = 1;
select max(took), min(took), avg(took) from metric where model = 'SVM';
DESCRIBE thesis.auth_log;
select * from metric order by took desc;