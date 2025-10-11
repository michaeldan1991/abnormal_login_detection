create database thesis;
use thesis;
SELECT count(*) FROM thesis.auth_log;
INSERT INTO roles (id, name) VALUES
(1, 'ROOT'),
(2, 'ADMIN'),
(3, 'USER');