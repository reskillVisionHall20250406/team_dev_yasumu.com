INSERT INTO area(name) VALUES('東北地方');
INSERT INTO area(name) VALUES('関東地方');
INSERT INTO area(name) VALUES('中部地方');
INSERT INTO area(name) VALUES('近畿地方');
INSERT INTO area(name) VALUES('中国地方');
INSERT INTO area(name) VALUES('四国地方');
INSERT INTO area(name) VALUES('九州地方');

INSERT INTO hotels(area_id, name, address, detail, image, image2, image3,capacity, price) VALUES (
    1,
    'ホテルニューショーヘイ',
    '東京都千代田区1-1',
    'ホテルニューショーヘイは、東京都千代田区に位置する落ち着いた雰囲気のビジネスホテルです。東京の中心にありながら、静かで快適な空間を提供し、出張や観光に最適です。清潔感のある客室と充実したアメニティで、ゆったりとした滞在をサポート。交通アクセスも良好で、主要駅や観光地への移動もスムーズです。心温まるサービスと利便性を兼ね備えた、都会の隠れ家的ホテルです。',
    '1.png',
    '2.png',
    '3.png',
    3 ,
    9000
);
INSERT INTO hotels(area_id, name, address, detail, image, image2, image3, capacity, price) VALUES
(1, 'ホテルA', '東京都A区', '説明A', '1.png', '2.png', '3.png', 2, 8000),
(2, 'ホテルB', '東京都B区', '説明B', '4.png', '5.png', '6.png', 4, 12000);