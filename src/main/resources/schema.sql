-- 各種テーブル削除
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS hotels;
DROP TABLE IF EXISTS area;

-- エリアテーブル
CREATE TABLE area (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

-- hotelテーブル
CREATE TABLE hotels (
    id SERIAL PRIMARY KEY,
    area_id INTEGER,
    name VARCHAR(255),
    address VARCHAR(255),
    detail VARCHAR(255),
    image VARCHAR(255),
    image2 VARCHAR(255),
    image3 VARCHAR(255),
    capacity INTEGER,
    price INTEGER
);

-- 顧客テーブル
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(60),
    address VARCHAR(150),
    tel VARCHAR(11),
    email VARCHAR(256) UNIQUE,
    password VARCHAR(255),
    image VARCHAR(255),
    card_no VARCHAR(255),
    code VARCHAR(255),
    expiry VARCHAR(255)
);

-- 予約テーブル
CREATE TABLE reservation (
    id SERIAL PRIMARY KEY,
    hotel_id INTEGER REFERENCES hotels(id),
    customer_id INTEGER REFERENCES customers(id),
    date DATE DEFAULT CURRENT_DATE
);
