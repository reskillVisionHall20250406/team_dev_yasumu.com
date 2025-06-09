-- 各種テーブル削除
DROP TABLE IF EXISTS area;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS hotel;


-- エリアテーブル
CREATE TABLE area
(
id SERIAL PRIMARY KEY,
name VARCHAR(MAX)
);
-- hotelテーブル
CREATE TABLE hotels
(
id SERIAL PRIMARY KEY,
area VARCHAR(MAX),
name VARCHAR(MAX),
address VARCHAR(MAX),
detail VARCHAR(MAX),
image VARCHAR(MAX),
price INTEGER
);
-- 顧客テーブル
CREATE TABLE customers
(
id SERIAL PRIMARY KEY,
name VARCHAR(60),
address VARCHAR(150),
tel VARCHAR(11),
email VARCHAR(256) UNIQUE,
password VARCHAR(MAX),
imge VARCHAR(MAX)
cardNo INTEGER
code INTEGER
expiry VARCHAR(MAX)
);
-- 予約テーブル
CREATE TABLE reservation
(
id SERIAL PRIMARY KEY REFERENCES,
customerId INTEGER customers(id),
date DATE DEFAULT CURRENT_DATE,
);