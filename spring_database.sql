\c postgres
DROP DATABASE IF EXISTS team_dev_yasumu;
DROP ROLE IF EXISTS student;
CREATE ROLE student WITH PASSWORD 'himitu' LOGIN;
CREATE DATABASE team_dev_yasumu OWNER student ENCODING 'UTF8';
