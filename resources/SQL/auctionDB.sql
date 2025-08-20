CREATE DATABASE IF NOT EXISTS dropwin;

DROP TABLE IF EXISTS product_details; -- product_details를 먼저 삭제해야 외래 키 제약 조건 오류가 발생하지 않습니다.
DROP TABLE IF EXISTS product;

use dropwin;

CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- INT -> BIGINT로 변경
    name VARCHAR(100) NOT NULL,         -- 상품 이름
    category VARCHAR(50) NOT NULL,      -- 카테고리 (축구, 농구 등)
    price INT NOT NULL,                 -- 가격
    bid_count INT DEFAULT 0,           -- 입찰 수
    remain_time VARCHAR(20),           -- 남은 시간
    seller VARCHAR(50),                -- 판매자
    image_url VARCHAR(255),            -- 이미지 경로 또는 URL
    created_at DATETIME DEFAULT NOW()  -- 생성일시
);

-- product_details 테이블 생성
CREATE TABLE product_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- INT -> BIGINT로 변경
    product_id BIGINT NOT NULL,           -- INT -> BIGINT로 변경 (product.id와 일치)
    size VARCHAR(50),                      -- 사이즈 (예: 270mm)
    release_year VARCHAR(10),              -- 발매년도 (예: 2021년)
    manufacture_country VARCHAR(50),       -- 제조국 (예: 미국)
    material VARCHAR(100),                 -- 재질 (예: 합성가죽)
    condition_description VARCHAR(255),    -- 상품상태 (예: 개봉했으나 미착용, 상품 손상 없음)
    color VARCHAR(50),                     -- 색상 (예: 화이트/블랙)
    brand VARCHAR(100),                    -- 브랜드 (예: 나이키)
    features TEXT,                         -- 특징 (예: 리미티드 에디션, 미개봉)
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);


-- 축구 카테고리 상품 데이터 (27개)
INSERT INTO product (name, category, price, bid_count, remain_time, seller, image_url) VALUES
('나이키 팬텀 축구화', '축구', 180000, 35, '1일 10시간', '축구사랑1', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+1'),
('아디다스 프레데터 축구화', '축구', 210000, 42, '0일 23시간', '골넣는재미', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+2'),
('푸마 퓨처 축구화', '축구', 150000, 28, '2일 5시간', '풋볼매니아', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+3'),
('미즈노 모렐리아 네오', '축구', 195000, 38, '0일 15시간', '클래식축구', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+4'),
('험멜 축구 양말 (3켤레)', '축구', 25000, 15, '1일 2시간', '스포츠용품', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+5'),
('나이키 플라이니트 축구공', '축구', 50000, 22, '0일 8시간', '공차자', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+6'),
('아디다스 티로 트레이닝복 상의', '축구', 75000, 30, '2일 18시간', '운동복전문', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+7'),
('푸마 골키퍼 장갑', '축구', 60000, 19, '0일 12시간', '수비의신', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+8'),
('축구 전술 보드', '축구', 30000, 10, '1일 7시간', '전술가', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+9'),
('나이키 브라질리아 더플백', '축구', 45000, 25, '0일 6시간', '가방나라', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+10'),
('아디다스 신가드', '축구', 20000, 18, '1일 14시간', '안전제일', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+11'),
('축구 훈련용 콘 세트', '축구', 18000, 12, '0일 9시간', '트레이닝샵', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+12'),
('푸마 팀라이즈 트레이닝 팬츠', '축구', 55000, 20, '2일 1시간', '운동복전문', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+13'),
('축구화 클리너 세트', '축구', 15000, 8, '0일 4시간', '슈즈케어', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+14'),
('나이키 스트라이크 축구공', '축구', 40000, 27, '1일 11시간', '공차자', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+15'),
('아디다스 캡틴 암밴드', '축구', 12000, 7, '0일 3시간', '팀장님', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+16'),
('축구 유니폼 (상의)', '축구', 65000, 150, '2일 6시간', '유니폼샵', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+17'),
('푸마 축구 스타킹', '축구', 10000, 10, '0일 5시간', '스포츠양말', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+18'),
('축구 훈련 조끼 (5개 세트)', '축구', 38000, 14, '1일 9시간', '팀훈련', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+19'),
('나이키 파크 트랙수트', '축구', 90000, 30, '0일 18시간', '운동복전문', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+20'),
('아디다스 풋살화', '축구', 110000, 28, '2일 3시간', '풋살러', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+21'),
('축구 심판 호루라기', '축구', 8000, 5, '0일 2시간', '심판용품', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+22'),
('푸마 에센셜 티셔츠', '축구', 28000, 16, '1일 1시간', '데일리웨어', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+23'),
('축구 골대 (미니)', '축구', 70000, 9, '0일 7시간', '미니골대', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+24'),
('나이키 아카데미 백팩', '축구', 60000, 21, '2일 10시간', '가방나라', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+25'),
('아디다스 축구공 (5호)', '축구', 35000, 26, '0일 14시간', '공차자', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+26'),
('축구 훈련용 사다리', '축구', 22000, 13, '1일 17시간', '트레이닝샵', 'https://placehold.co/600x400/000000/FFFFFF?text=축구+상품+27'); -- 여기에 seller 값이 누락되어 있었습니다. '트레이닝샵' 추가


-- 농구 카테고리 상품 데이터 (27개)
INSERT INTO product (name, category, price, bid_count, remain_time, seller, image_url) VALUES
('나이키 줌프릭 농구화', '농구', 170000, 30, '1일 12시간', '농구왕', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+1'),
('아디다스 하든 농구화', '농구', 165000, 28, '0일 20시간', '농구홀릭', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+2'),
('언더아머 커리 농구화', '농구', 185000, 33, '2일 8시간', '슛터', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+3'),
('스팔딩 농구공 (7호)', '농구', 45000, 20, '0일 10시간', '농구공전문', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+4'),
('나이키 농구 반바지', '농구', 35000, 15, '1일 3시간', '스포츠의류', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+5'),
('아디다스 농구 유니폼 세트', '농구', 80000, 25, '0일 7시간', '팀유니폼', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+6'),
('농구 골대 (이동식)', '농구', 250000, 200, '2일 15시간', '골대샵', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+7'),
('농구화 가방', '농구', 28000, 12, '0일 5시간', '가방콜렉터', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+8'),
('나이키 엘리트 농구 양말', '농구', 18000, 18, '1일 6시간', '양말전문', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+9'),
('아디다스 농구공 (5호)', '농구', 38000, 22, '0일 11시간', '농구공전문', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+10'),
('농구 팔꿈치 보호대', '농구', 15000, 8, '2일 2시간', '보호대샵', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+11'),
('언더아머 농구 티셔츠', '농구', 40000, 17, '0일 9시간', '스포츠의류', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+12'),
('농구 훈련용 콘', '농구', 10000, 6, '1일 13시간', '트레이닝샵', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+13'),
('나이키 농구 헤어밴드', '농구', 12000, 9, '0일 4시간', '액세서리', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+14'),
('아디다스 농구 백팩', '농구', 55000, 20, '2일 10시간', '가방콜렉터', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+15'),
('농구화 클리너', '농구', 13000, 7, '0일 3시간', '슈즈케어', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+16'),
('언더아머 농구 양말 (3켤레)', '농구', 22000, 14, '1일 5시간', '양말전문', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+17'),
('농구 림 네트', '농구', 8000, 5, '0일 2시간', '골대샵', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+18'),
('나이키 농구 슬리브', '농구', 25000, 11, '2일 11시간', '보호대샵', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+19'),
('아디다스 농구 트레이닝 팬츠', '농구', 48000, 19, '0일 16시간', '스포츠의류', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+20'),
('농구 심판복', '농구', 70000, 4, '1일 8시간', '심판용품', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+21'),
('언더아머 농구공 (3호)', '농구', 30000, 16, '0일 6시간', '농구공전문', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+22'),
('농구 훈련용 저지', '농구', 32000, 13, '2일 4시간', '팀유니폼', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+23'),
('나이키 농구 아대', '농구', 10000, 8, '0일 5시간', '액세서리', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+24'),
('아디다스 농구 캡', '농구', 20000, 10, '1일 10시간', '모자샵', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+25'),
('농구공 펌프', '농구', 15000, 7, '0일 8시간', '공용품', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+26'),
('언더아머 농구 양말', '농구', 16000, 12, '2일 1시간', '양말전문', 'https://placehold.co/600x400/000000/FFFFFF?text=농구+상품+27');

-- 야구 카테고리 상품 데이터 (27개)
INSERT INTO product (name, category, price, bid_count, remain_time, seller, image_url) VALUES
('미즈노 야구 글러브', '야구', 150000, 25, '1일 15시간', '글러브장인', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+1'),
('나이키 야구 배트', '야구', 120000, 20, '0일 22시간', '배트맨', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+2'),
('롤링스 야구공 (12개)', '야구', 30000, 18, '2일 7시간', '야구공샵', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+3'),
('뉴발란스 야구화', '야구', 95000, 22, '0일 14시간', '베이스러너', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+4'),
('야구 헬멧', '야구', 70000, 250, '1일 4시간', '안전야구', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+5'),
('야구 배팅 장갑', '야구', 28000, 17, '0일 9시간', '타자용품', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+6'),
('언더아머 야구 유니폼', '야구', 85000, 23, '2일 16시간', '팀유니폼', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+7'),
('야구 캐치볼 세트', '야구', 40000, 10, '0일 6시간', '캐치볼러', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+8'),
('야구 글러브 오일', '야구', 15000, 8, '1일 8시간', '글러브케어', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+9'),
('나이키 야구 모자', '야구', 25000, 19, '0일 11시간', '모자전문', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+10'),
('야구 연습용 티', '야구', 50000, 12, '2일 3시간', '연습용품', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+11'),
('롤링스 야구 배트 가방', '야구', 35000, 14, '0일 7시간', '가방콜렉터', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+12'),
('야구 심판복', '야구', 90000, 5, '1일 10시간', '심판용품', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+13'),
('뉴발란스 야구 양말', '야구', 12000, 9, '0일 4시간', '양말전문', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+14'),
('야구 보호대 세트', '야구', 60000, 16, '2일 9시간', '안전야구', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+15'),
('야구 투수 마운드', '야구', 180000, 3, '0일 2시간', '훈련용품', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+16'),
('미즈노 야구화 스파이크', '야구', 10000, 11, '1일 11시간', '슈즈케어', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+17'),
('야구 기록지', '야구', 8000, 6, '0일 3시간', '기록용품', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+18'),
('나이키 야구 티셔츠', '야구', 38000, 13, '2일 5시간', '스포츠의류', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+19'),
('야구 배팅 케이지', '야구', 300000, 2, '0일 1시간', '훈련용품', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+20'),
('언더아머 야구 양말 (3켤레)', '야구', 20000, 10, '1일 13시간', '양말전문', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+21'),
('야구공 디스플레이 케이스', '야구', 22000, 300, '0일 8시간', '진열용품', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+22'),
('미즈노 야구 연습공', '야구', 18000, 15, '2일 6시간', '연습용품', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+23'),
('야구 글러브 끈', '야구', 5000, 4, '0일 5시간', '글러브케어', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+24'),
('나이키 야구 슬리브', '야구', 20000, 10, '1일 9시간', '보호대샵', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+25'),
('야구 배트 그립 테이프', '야구', 10000, 8, '0일 6시간', '타자용품', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+26'),
('롤링스 야구공 (1개)', '야구', 3000, 12, '2일 2시간', '야구공샵', 'https://placehold.co/600x400/000000/FFFFFF?text=야구+상품+27');

-- 골프 카테고리 상품 데이터 (27개)
INSERT INTO product (name, category, price, bid_count, remain_time, seller, image_url) VALUES
('타이틀리스트 드라이버', '골프', 450000, 30, '1일 18시간', '장타왕', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+1'),
('캘러웨이 아이언 세트', '골프', 1200000, 25, '0일 23시간', '골프마스터', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+2'),
('테일러메이드 퍼터', '골프', 280000, 20, '2일 10시간', '숏게임달인', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+3'),
('브리지스톤 골프공 (12개)', '골프', 60000, 15, '0일 16시간', '골프공전문', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+4'),
('골프 장갑 (남성용)', '골프', 25000, 18, '1일 5시간', '골프웨어샵', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+5'),
('골프화 (스파이크리스)', '골프', 130000, 22, '0일 11시간', '필드위의신사', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+6'),
('골프 캐디백', '골프', 350000, 10, '2일 19시간', '골프백전문', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+7'),
('골프 거리 측정기', '골프', 400000, 12, '0일 8시간', '정확도100', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+8'),
('골프 우산', '골프', 45000, 7, '1일 12시간', '비오는날골프', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+9'),
('골프 모자', '골프', 30000, 14, '0일 6시간', '골프액세서리', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+10'),
('골프 연습 매트', '골프', 80000, 225, '2일 4시간', '연습의힘', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+11'),
('골프 티 (100개)', '골프', 15000, 10, '0일 5시간', '티박스', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+12'),
('골프 스윙 연습기', '골프', 120000, 8, '1일 15시간', '스윙교정', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+13'),
('골프 클럽 커버 세트', '골프', 70000, 11, '0일 9시간', '클럽보호', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+14'),
('골프 장갑 (여성용)', '골프', 25000, 16, '2일 7시간', '골프웨어샵', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+15'),
('골프 볼 마커', '골프', 10000, 6, '0일 3시간', '마커수집', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+16'),
('골프 타월', '골프', 20000, 13, '1일 6시간', '골프액세서리', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+17'),
('골프 연습망', '골프', 150000, 5, '0일 2시간', '실내연습', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+18'),
('골프웨어 상의 (폴로)', '골프', 90000, 19, '2일 12시간', '필드패션', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+19'),
('골프화 클리너', '골프', 12000, 7, '0일 4시간', '슈즈케어', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+20'),
('골프 연습용 볼 (10개)', '골프', 25000, 10, '1일 10시간', '연습의힘', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+21'),
('골프 스윙 분석기', '골프', 500000, 4, '0일 1시간', '기술향상', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+22'),
('골프 장갑 (양손)', '골프', 40000, 15, '2일 5시간', '골프웨어샵', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+23'),
('골프 클럽 세척 브러쉬', '골프', 8000, 3, '0일 3시간', '클럽케어', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+24'),
('골프 파우치', '골프', 35000, 12, '1일 7시간', '액세서리', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+25'),
('골프 연습용 퍼팅컵', '골프', 20000, 9, '0일 6시간', '퍼팅연습', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+26'),
('골프 스코어 카드 홀더', '골프', 18000, 155, '2일 1시간', '기록용품', 'https://placehold.co/600x400/000000/FFFFFF?text=골프+상품+27');

-- 러닝 카테고리 상품 데이터 (27개)
INSERT INTO product (name, category, price, bid_count, remain_time, seller, image_url) VALUES
('나이키 에어줌 러닝화', '러닝', 160000, 280, '1일 20시간', '런닝맨', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+1'),
('아디다스 울트라부스트 러닝화', '러닝', 190000, 38, '0일 23시간', '러닝크루', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+2'),
('아식스 젤카야노 러닝화', '러닝', 155000, 35, '2일 6시간', '마라톤맨', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+3'),
('뉴발란스 프레쉬폼 러닝화', '러닝', 140000, 32, '0일 18시간', '편안한러닝', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+4'),
('러닝용 컴프레션 타이츠', '러닝', 60000, 25, '1일 7시간', '운동복전문', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+5'),
('러닝 벨트 (물통 포함)', '러닝', 35000, 20, '0일 12시간', '수분섭취', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+6'),
('나이키 드라이핏 러닝 티셔츠', '러닝', 45000, 30, '2일 14시간', '땀흡수', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+7'),
('러닝용 스마트워치', '러닝', 300000, 15, '0일 9시간', '스마트러너', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+8'),
('아디다스 러닝 반바지', '러닝', 30000, 22, '1일 10시간', '가벼운러닝', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+9'),
('러닝용 헤드밴드', '러닝', 10000, 10, '0일 4시간', '땀방지', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+10'),
('아식스 러닝 양말 (3켤레)', '러닝', 20000, 18, '2일 3시간', '발편한양말', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+11'),
('러닝용 암밴드 (스마트폰)', '러닝', 18000, 14, '0일 7시간', '폰거치', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+12'),
('뉴발란스 러닝 재킷', '러닝', 110000, 28, '1일 13시간', '방풍방수', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+13'),
('러닝용 선글라스', '러닝', 70000, 11, '0일 5시간', '눈보호', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+14'),
('나이키 러닝 백팩', '러닝', 75000, 26, '2일 9시간', '러닝가방', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+15'),
('러닝화 클리너', '러닝', 15000, 9, '0일 3시간', '슈즈케어', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+16'),
('아디다스 러닝 캡', '러닝', 25000, 17, '1일 6시간', '햇빛차단', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+17'),
('러닝용 에너지젤 (5개)', '러닝', 20000, 12, '0일 2시간', '에너지충전', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+18'),
('아식스 러닝 트레이닝복', '러닝', 95000, 29, '2일 11시간', '운동복전문', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+19'),
('러닝용 스마트밴드', '러닝', 80000, 300, '0일 16시간', '활동량측정', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+20'),
('뉴발란스 러닝 티셔츠', '러닝', 38000, 21, '1일 8시간', '데일리러닝', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+21'),
('러닝용 물통 (500ml)', '러닝', 12000, 10, '0일 6시간', '수분섭취', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+22'),
('나이키 러닝 장갑', '러닝', 20000, 13, '2일 4시간', '손보호', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+23'),
('러닝용 반사 조끼', '러닝', 25000, 8, '0일 5시간', '야간러닝', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+24'),
('아디다스 러닝화 (초경량)', '러닝', 170000, 37, '1일 15시간', '빠른러닝', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+25'),
('러닝용 양말 (발목)', '러닝', 8000, 15, '0일 10시간', '편한발목', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+26'),
('아식스 러닝 반바지', '러닝', 32000, 19, '2일 1시간', '시원한러닝', 'https://placehold.co/600x400/000000/FFFFFF?text=러닝+상품+27');

-- 탁구 카테고리 상품 데이터 (27개)
INSERT INTO product (name, category, price, bid_count, remain_time, seller, image_url) VALUES
('버터플라이 탁구 라켓', '탁구', 120000, 25, '1일 14시간', '탁구왕', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+1'),
('니타쿠 탁구공 (6개)', '탁구', 20000, 18, '0일 20시간', '공치는재미', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+2'),
('탁구대 (접이식)', '탁구', 300000, 10, '2일 9시간', '탁구장인', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+3'),
('도닉 탁구 라켓 케이스', '탁구', 25000, 15, '0일 15시간', '라켓보호', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+4'),
('탁구 네트 및 포스트 세트', '탁구', 40000, 12, '1일 3시간', '네트전문', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+5'),
('탁구 라켓 고무 클리너', '탁구', 10000, 8, '0일 7시간', '라켓관리', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+6'),
('탁구화 (미끄럼 방지)', '탁구', 80000, 20, '2일 16시간', '발빠른탁구', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+7'),
('탁구 로봇 (초급용)', '탁구', 150000, 5, '0일 6시간', '연습파트너', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+8'),
('탁구대 커버', '탁구', 35000, 7, '1일 8시간', '테이블보호', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+9'),
('탁구 라켓 (초급자용)', '탁구', 30000, 17, '0일 11시간', '입문용', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+10'),
('탁구공 수거 네트', '탁구', 22000, 9, '2일 3시간', '공줍기', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+11'),
('탁구 훈련용 볼 머신', '탁구', 250000, 4, '0일 5시간', '훈련장비', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+12'),
('탁구 라켓 그립 테이프', '탁구', 8000, 10, '1일 10시간', '그립감', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+13'),
('탁구용 땀 밴드', '탁구', 5000, 6, '0일 4시간', '땀방지', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+14'),
('탁구 유니폼 상의', '탁구', 50000, 18, '2일 7시간', '탁구패션', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+15'),
('탁구화 가방', '탁구', 20000, 11, '0일 3시간', '슈즈가방', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+16'),
('탁구 라켓 (중급자용)', '탁구', 80000, 22, '1일 6시간', '실력향상', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+17'),
('탁구공 (12개입)', '탁구', 35000, 14, '0일 2시간', '연습용공', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+18'),
('탁구대 청소용 브러쉬', '탁구', 12000, 7, '2일 11시간', '테이블관리', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+19'),
('탁구 라켓 세트 (2인용)', '탁구', 60000, 16, '0일 16시간', '커플탁구', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+20'),
('탁구용 양말', '탁구', 10000, 9, '1일 8시간', '양말전문', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+21'),
('탁구공 케이스', '탁구', 7000, 5, '0일 6시간', '공보관', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+22'),
('탁구 라켓 고무 (1장)', '탁구', 30000, 13, '2일 4시간', '러버교체', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+23'),
('탁구대 보호대', '탁구', 18000, 8, '0일 5시간', '테이블보호', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+24'),
('탁구 훈련용 콘', '탁구', 15000, 10, '1일 9시간', '훈련용품', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+25'),
('탁구 라켓 엣지 테이프', '탁구', 5000, 6, '0일 3시간', '라켓보호', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+26'),
('탁구공 자동 서브기', '탁구', 100000, 3, '2일 2시간', '서브연습', 'https://placehold.co/600x400/000000/FFFFFF?text=탁구+상품+27');

-- 수영 카테고리 상품 데이터 (27개)
INSERT INTO product (name, category, price, bid_count, remain_time, seller, image_url) VALUES
('스피도 수영복 (남성용)', '수영', 70000, 28, '1일 16시간', '수영마스터', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+1'),
('아레나 수영복 (여성용)', '수영', 85000, 32, '0일 21시간', '수영여신', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+2'),
('미즈노 수경', '수영', 30000, 20, '2일 8시간', '눈보호', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+3'),
('수영모 (실리콘)', '수영', 15000, 25, '0일 14시간', '머리보호', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+4'),
('수영 귀마개', '수영', 8000, 10, '1일 4시간', '귀보호', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+5'),
('수영 코마개', '수영', 5000, 7, '0일 9시간', '코보호', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+6'),
('수영 킥판', '수영', 25000, 18, '2일 15시간', '발차기연습', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+7'),
('수영 풀 부이', '수영', 20000, 12, '0일 6시간', '팔힘강화', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+8'),
('수영 타월 (극세사)', '수영', 18000, 15, '1일 9시간', '빠른건조', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+9'),
('수영 가방 (방수)', '수영', 40000, 22, '0일 11시간', '방수가방', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+10'),
('수영복 세탁 세제', '수영', 10000, 8, '2일 3시간', '세탁용품', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+11'),
('수영 발차기 연습 밴드', '수영', 15000, 10, '0일 7시간', '발차기교정', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+12'),
('수영 오리발 (초급용)', '수영', 35000, 16, '1일 12시간', '추진력', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+13'),
('수영용 물안경 김서림 방지액', '수영', 7000, 5, '0일 4시간', '김서림방지', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+14'),
('수영장용 슬리퍼', '수영', 20000, 13, '2일 10시간', '미끄럼방지', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+15'),
('수영용 아쿠아 슈즈', '수영', 45000, 19, '0일 8시간', '발보호', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+16'),
('수영 레인 줄 (개인용)', '수영', 50000, 4, '1일 11시간', '개인레인', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+17'),
('수영 강습용 보드', '수영', 28000, 11, '0일 3시간', '초보자용', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+18'),
('수영복 (아동용)', '수영', 55000, 20, '2일 6시간', '아동수영복', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+19'),
('수영용 선크림 (방수)', '수영', 25000, 14, '0일 16시간', '피부보호', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+20'),
('수영장용 비치볼', '수영', 10000, 9, '1일 8시간', '물놀이용품', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+21'),
('수영복 건조대 (휴대용)', '수영', 15000, 7, '0일 5시간', '간편건조', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+22'),
('수영용 mp3 플레이어 (방수)', '수영', 120000, 6, '2일 4시간', '음악과수영', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+23'),
('수영장 입장권 (1회용)', '수영', 10000, 30, '0일 2시간', '입장권', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+24'),
('수영용 핀', '수영', 60000, 17, '1일 13시간', '발차기강화', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+25'),
('수영복 (래쉬가드)', '수영', 90000, 24, '0일 10시간', '자외선차단', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+26'),
('수영용 스노클', '수영', 30000, 188, '2일 1시간', '수중호흡', 'https://placehold.co/600x400/000000/FFFFFF?text=수영+상품+27');





-- 축구 카테고리 상품 상세 정보
INSERT INTO product_details (product_id, size, release_year, manufacture_country, material, condition_description, color, brand, features) VALUES
((SELECT id FROM product WHERE name = '나이키 팬텀 축구화' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '265mm', '2023년', '베트남', '합성가죽', '새상품', '블랙/레드', '나이키', '경량, 민첩성 강화'),
((SELECT id FROM product WHERE name = '아디다스 프레데터 축구화' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '270mm', '2024년', '인도네시아', '천연가죽', '새상품', '블랙/화이트', '아디다스', '강력한 슈팅, 볼 컨트롤'),
((SELECT id FROM product WHERE name = '푸마 퓨처 축구화' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '260mm', '2023년', '중국', '플라이니트', '사용감 적음', '블루/핑크', '푸마', '유연성, 편안한 착용감'),
((SELECT id FROM product WHERE name = '미즈노 모렐리아 네오' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '265mm', '2024년', '일본', '천연가죽', '새상품', '화이트/골드', '미즈노', '장인 정신, 최상급 터치감'),
((SELECT id FROM product WHERE name = '험멜 축구 양말 (3켤레)' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '한국', '면/폴리에스터', '새상품', '블랙', '험멜', '통기성, 발목 지지'),
((SELECT id FROM product WHERE name = '나이키 플라이니트 축구공' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '5호', '2024년', '베트남', '합성피혁', '새상품', '화이트/블랙', '나이키', '정확한 비행, 뛰어난 터치감'),
((SELECT id FROM product WHERE name = '아디다스 티로 트레이닝복 상의' AND category = '축구' ORDER BY created_at DESC LIMIT 1), 'L', '2023년', '중국', '폴리에스터', '새상품', '네이비', '아디다스', '흡습속건, 편안한 착용감'),
((SELECT id FROM product WHERE name = '푸마 골키퍼 장갑' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '9호', '2024년', '태국', '라텍스', '새상품', '블랙/옐로우', '푸마', '강력한 그립, 손가락 보호'),
((SELECT id FROM product WHERE name = '축구 전술 보드' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '중국', '플라스틱', '새상품', '화이트', '무브', '자석 부착, 전술 연습'),
((SELECT id FROM product WHERE name = '나이키 브라질리아 더플백' AND category = '축구' ORDER BY created_at DESC LIMIT 1), 'M', '2024년', '베트남', '폴리에스터', '새상품', '블랙', '나이키', '넓은 수납 공간, 내구성'),
((SELECT id FROM product WHERE name = '아디다스 신가드' AND category = '축구' ORDER BY created_at DESC LIMIT 1), 'L', '2023년', '중국', '플라스틱/EVA', '새상품', '화이트', '아디다스', '충격 흡수, 다리 보호'),
((SELECT id FROM product WHERE name = '축구 훈련용 콘 세트' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '소형', '2024년', '중국', '플라스틱', '새상품', '오렌지', '트레이닝마스터', '민첩성 훈련, 휴대 용이'),
((SELECT id FROM product WHERE name = '푸마 팀라이즈 트레이닝 팬츠' AND category = '축구' ORDER BY created_at DESC LIMIT 1), 'M', '2023년', '베트남', '폴리에스터', '새상품', '블랙', '푸마', '흡습속건, 편안한 움직임'),
((SELECT id FROM product WHERE name = '축구화 클리너 세트' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '한국', '솔/액체', '새상품', '멀티', '슈즈케어', '신발 오염 제거, 관리 용이'),
((SELECT id FROM product WHERE name = '나이키 스트라이크 축구공' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '5호', '2023년', '베트남', '합성피혁', '새상품', '레드/블루', '나이키', '정확한 컨트롤, 내구성'),
((SELECT id FROM product WHERE name = '아디다스 캡틴 암밴드' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '중국', '엘라스틱', '새상품', '블랙', '아디다스', '팀 주장용, 조절 가능'),
((SELECT id FROM product WHERE name = '축구 유니폼 (상의)' AND category = '축구' ORDER BY created_at DESC LIMIT 1), 'XL', '2023년', '태국', '폴리에스터', '새상품', '블루', '팀스포츠', '통기성 우수, 팀 로고 가능'),
((SELECT id FROM product WHERE name = '푸마 축구 스타킹' AND category = '축구' ORDER BY created_at DESC LIMIT 1), 'L', '2024년', '중국', '폴리에스터', '새상품', '화이트', '푸마', '발목 지지, 편안한 착용'),
((SELECT id FROM product WHERE name = '축구 훈련 조끼 (5개 세트)' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '메쉬', '새상품', '형광색', '트레이닝마스터', '팀 구분, 통기성'),
((SELECT id FROM product WHERE name = '나이키 파크 트랙수트' AND category = '축구' ORDER BY created_at DESC LIMIT 1), 'L', '2024년', '베트남', '폴리에스터', '새상품', '블랙', '나이키', '편안한 착용감, 활동성'),
((SELECT id FROM product WHERE name = '아디다스 풋살화' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '260mm', '2023년', '인도네시아', '합성가죽', '새상품', '블루/오렌지', '아디다스', '실내용, 뛰어난 접지력'),
((SELECT id FROM product WHERE name = '축구 심판 호루라기' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '중국', '금속/플라스틱', '새상품', '실버', '심판용품', '명확한 소리, 휴대 용이'),
((SELECT id FROM product WHERE name = '푸마 에센셜 티셔츠' AND category = '축구' ORDER BY created_at DESC LIMIT 1), 'M', '2023년', '베트남', '면', '새상품', '그레이', '푸마', '데일리 착용, 부드러운 소재'),
((SELECT id FROM product WHERE name = '축구 골대 (미니)' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '소형', '2024년', '중국', '스틸/폴리에스터', '새상품', '화이트', '미니골', '간편 설치, 휴대 용이'),
((SELECT id FROM product WHERE name = '나이키 아카데미 백팩' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '베트남', '폴리에스터', '새상품', '블랙', '나이키', '넓은 수납, 축구화 수납 가능'),
((SELECT id FROM product WHERE name = '아디다스 축구공 (5호)' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '5호', '2024년', '파키스탄', '합성피혁', '새상품', '화이트/블랙', '아디다스', '공식 규격, 훈련용'),
((SELECT id FROM product WHERE name = '축구 훈련용 사다리' AND category = '축구' ORDER BY created_at DESC LIMIT 1), '4m', '2023년', '중국', '플라스틱/나일론', '새상품', '옐로우', '트레이닝마스터', '민첩성 훈련, 접이식');

-- 농구 카테고리 상품 상세 정보
INSERT INTO product_details (product_id, size, release_year, manufacture_country, material, condition_description, color, brand, features) VALUES
((SELECT id FROM product WHERE name = '나이키 줌프릭 농구화' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '270mm', '2024년', '베트남', '합성섬유', '새상품', '블랙/그린', '나이키', '야니스 시그니처, 반응성 쿠셔닝'),
((SELECT id FROM product WHERE name = '아디다스 하든 농구화' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '265mm', '2023년', '인도네시아', '텍스타일', '새상품', '레드/블랙', '아디다스', '하든 시그니처, 안정적인 지지력'),
((SELECT id FROM product WHERE name = '언더아머 커리 농구화' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '275mm', '2024년', '중국', '메쉬/합성피혁', '새상품', '블루/화이트', '언더아머', '커리 시그니처, 경량성'),
((SELECT id FROM product WHERE name = '스팔딩 농구공 (7호)' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '7호', '2023년', '태국', '합성피혁', '새상품', '오렌지', '스팔딩', 'NBA 공식 규격, 뛰어난 그립'),
((SELECT id FROM product WHERE name = '나이키 농구 반바지' AND category = '농구' ORDER BY created_at DESC LIMIT 1), 'L', '2024년', '베트남', '폴리에스터', '새상품', '블랙', '나이키', '흡습속건, 편안한 착용감'),
((SELECT id FROM product WHERE name = '아디다스 농구 유니폼 세트' AND category = '농구' ORDER BY created_at DESC LIMIT 1), 'XL', '2023년', '중국', '폴리에스터', '새상품', '블루/화이트', '아디다스', '팀 유니폼, 통기성 우수'),
((SELECT id FROM product WHERE name = '농구 골대 (이동식)' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '중국', '스틸/폴리카보네이트', '새상품', '블랙', '골마스터', '높이 조절, 실내/외 겸용'),
((SELECT id FROM product WHERE name = '농구화 가방' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '한국', '폴리에스터', '새상품', '네이비', '슈즈백', '신발 수납, 통기성'),
((SELECT id FROM product WHERE name = '나이키 엘리트 농구 양말' AND category = '농구' ORDER BY created_at DESC LIMIT 1), 'M', '2024년', '베트남', '면/폴리에스터', '새상품', '화이트/블랙', '나이키', '발 편안함, 물집 방지'),
((SELECT id FROM product WHERE name = '아디다스 농구공 (5호)' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '5호', '2023년', '태국', '고무', '새상품', '블루/오렌지', '아디다스', '초보자용, 실내/외 겸용'),
((SELECT id FROM product WHERE name = '농구 팔꿈치 보호대' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '중국', '네오프렌', '새상품', '블랙', '프로텍트', '팔꿈치 보호, 압박 기능'),
((SELECT id FROM product WHERE name = '언더아머 농구 티셔츠' AND category = '농구' ORDER BY created_at DESC LIMIT 1), 'M', '2023년', '베트남', '폴리에스터', '새상품', '그레이', '언더아머', '흡습속건, 편안한 움직임'),
((SELECT id FROM product WHERE name = '농구 훈련용 콘' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '소형', '2024년', '중국', '플라스틱', '새상품', '옐로우', '트레이닝마스터', '드리블 훈련, 민첩성 향상'),
((SELECT id FROM product WHERE name = '나이키 농구 헤어밴드' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '면/스판덱스', '새상품', '블랙', '나이키', '땀 흡수, 머리 고정'),
((SELECT id FROM product WHERE name = '아디다스 농구 백팩' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '베트남', '폴리에스터', '새상품', '블랙', '아디다스', '넓은 수납, 농구공 수납 가능'),
((SELECT id FROM product WHERE name = '농구화 클리너' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '소형', '2023년', '한국', '액체', '새상품', '투명', '슈즈케어', '신발 오염 제거, 관리 용이'),
((SELECT id FROM product WHERE name = '언더아머 농구 양말 (3켤레)' AND category = '농구' ORDER BY created_at DESC LIMIT 1), 'L', '2024년', '중국', '면/폴리에스터', '새상품', '화이트', '언더아머', '발 편안함, 물집 방지'),
((SELECT id FROM product WHERE name = '농구 림 네트' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '중국', '나일론', '새상품', '화이트', '골마스터', '손쉬운 교체, 내구성'),
((SELECT id FROM product WHERE name = '나이키 농구 슬리브' AND category = '농구' ORDER BY created_at DESC LIMIT 1), 'M', '2024년', '베트남', '폴리에스터/스판덱스', '새상품', '블랙', '나이키', '근육 지지, 팔 보호'),
((SELECT id FROM product WHERE name = '아디다스 농구 트레이닝 팬츠' AND category = '농구' ORDER BY created_at DESC LIMIT 1), 'L', '2023년', '중국', '폴리에스터', '새상품', '그레이', '아디다스', '편안한 움직임, 흡습속건'),
((SELECT id FROM product WHERE name = '농구 심판복' AND category = '농구' ORDER BY created_at DESC LIMIT 1), 'XL', '2024년', '한국', '폴리에스터', '새상품', '블랙/화이트', '심판용품', '전문 심판용, 통기성'),
((SELECT id FROM product WHERE name = '언더아머 농구공 (3호)' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '3호', '2023년', '중국', '고무', '새상품', '레드', '언더아머', '유소년용, 실내/외 겸용'),
((SELECT id FROM product WHERE name = '농구 훈련용 저지' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '베트남', '메쉬', '새상품', '오렌지', '팀스포츠', '팀 구분, 통기성'),
((SELECT id FROM product WHERE name = '나이키 농구 아대' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '면/스판덱스', '새상품', '화이트', '나이키', '땀 흡수, 손목 지지'),
((SELECT id FROM product WHERE name = '아디다스 농구 캡' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '베트남', '면', '새상품', '블랙', '아디다스', '햇빛 차단, 편안한 착용'),
((SELECT id FROM product WHERE name = '농구공 펌프' AND category = '농구' ORDER BY created_at DESC LIMIT 1), '소형', '2023년', '중국', '플라스틱/금속', '새상품', '블랙', '공용품', '휴대 용이, 공기 주입'),
((SELECT id FROM product WHERE name = '언더아머 농구 양말' AND category = '농구' ORDER BY created_at DESC LIMIT 1), 'M', '2024년', '베트남', '면/폴리에스터', '새상품', '그레이', '언더아머', '발 편안함, 충격 흡수');

-- 야구 카테고리 상품 상세 정보
INSERT INTO product_details (product_id, size, release_year, manufacture_country, material, condition_description, color, brand, features) VALUES
((SELECT id FROM product WHERE name = '미즈노 야구 글러브' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '12인치', '2023년', '일본', '천연가죽', '새상품', '브라운', '미즈노', '내야수용, 견고한 내구성'),
((SELECT id FROM product WHERE name = '나이키 야구 배트' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '33인치', '2024년', '미국', '알루미늄', '새상품', '블랙/실버', '나이키', '경량, 강한 반발력'),
((SELECT id FROM product WHERE name = '롤링스 야구공 (12개)' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '중국', '가죽/코르크', '새상품', '화이트', '롤링스', '경식구, 훈련/경기용'),
((SELECT id FROM product WHERE name = '뉴발란스 야구화' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '270mm', '2024년', '베트남', '합성가죽', '새상품', '블랙/화이트', '뉴발란스', '뛰어난 접지력, 편안한 착용'),
((SELECT id FROM product WHERE name = '야구 헬멧' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '플라스틱/폼', '새상품', '블랙', '세이프가드', '충격 흡수, 안전 보호'),
((SELECT id FROM product WHERE name = '야구 배팅 장갑' AND category = '야구' ORDER BY created_at DESC LIMIT 1), 'M', '2024년', '베트남', '합성피혁', '새상품', '화이트', '타격마스터', '그립력 강화, 손 보호'),
((SELECT id FROM product WHERE name = '언더아머 야구 유니폼' AND category = '야구' ORDER BY created_at DESC LIMIT 1), 'L', '2023년', '중국', '폴리에스터', '새상품', '네이비', '언더아머', '흡습속건, 팀 로고 가능'),
((SELECT id FROM product WHERE name = '야구 캐치볼 세트' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '한국', '가죽/면', '새상품', '브라운', '캐치마스터', '초보자용, 간편한 캐치볼'),
((SELECT id FROM product WHERE name = '야구 글러브 오일' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '100ml', '2023년', '미국', '광물유', '새상품', '투명', '글러브케어', '글러브 관리, 유연성 유지'),
((SELECT id FROM product WHERE name = '나이키 야구 모자' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '베트남', '면', '새상품', '블랙', '나이키', '햇빛 차단, 편안한 착용'),
((SELECT id FROM product WHERE name = '야구 연습용 티' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '중국', '스틸/고무', '새상품', '블랙', '연습마스터', '타격 연습, 높이 조절'),
((SELECT id FROM product WHERE name = '롤링스 야구 배트 가방' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '중국', '폴리에스터', '새상품', '블랙', '롤링스', '배트 및 장비 수납, 내구성'),
((SELECT id FROM product WHERE name = '야구 심판복' AND category = '야구' ORDER BY created_at DESC LIMIT 1), 'XL', '2023년', '한국', '폴리에스터', '새상품', '블랙/그레이', '심판용품', '전문 심판용, 통기성'),
((SELECT id FROM product WHERE name = '뉴발란스 야구 양말' AND category = '야구' ORDER BY created_at DESC LIMIT 1), 'L', '2024년', '베트남', '면/폴리에스터', '새상품', '화이트', '뉴발란스', '발 편안함, 물집 방지'),
((SELECT id FROM product WHERE name = '야구 보호대 세트' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '플라스틱/폼', '새상품', '블랙', '세이프가드', '팔/다리 보호, 충격 흡수'),
((SELECT id FROM product WHERE name = '야구 투수 마운드' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '소형', '2024년', '미국', '고무/인조잔디', '새상품', '그린', '피칭마스터', '투구 연습, 휴대 용이'),
((SELECT id FROM product WHERE name = '미즈노 야구화 스파이크' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '일본', '금속', '새상품', '실버', '미즈노', '스파이크 교체, 접지력 강화'),
((SELECT id FROM product WHERE name = '야구 기록지' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '한국', '종이', '새상품', '화이트', '기록마스터', '경기 기록, 휴대 용이'),
((SELECT id FROM product WHERE name = '나이키 야구 티셔츠' AND category = '야구' ORDER BY created_at DESC LIMIT 1), 'M', '2023년', '베트남', '면/폴리에스터', '새상품', '블루', '나이키', '데일리 착용, 편안함'),
((SELECT id FROM product WHERE name = '야구 배팅 케이지' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '대형', '2024년', '중국', '스틸/나일론', '새상품', '블랙', '타격마스터', '타격 연습, 안전 보호'),
((SELECT id FROM product WHERE name = '언더아머 야구 양말 (3켤레)' AND category = '야구' ORDER BY created_at DESC LIMIT 1), 'L', '2023년', '중국', '면/폴리에스터', '새상품', '블랙', '언더아머', '발 편안함, 충격 흡수'),
((SELECT id FROM product WHERE name = '야구공 디스플레이 케이스' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '소형', '2024년', '중국', '아크릴', '새상품', '투명', '진열마스터', '야구공 보관, 전시용'),
((SELECT id FROM product WHERE name = '미즈노 야구 연습공' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '태국', '합성고무', '새상품', '화이트', '미즈노', '안전한 연습, 내구성'),
((SELECT id FROM product WHERE name = '야구 글러브 끈' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '미국', '가죽', '새상품', '브라운', '글러브케어', '글러브 수리, 교체용'),
((SELECT id FROM product WHERE name = '나이키 야구 슬리브' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '베트남', '폴리에스터/스판덱스', '새상품', '화이트', '나이키', '팔 보호, 근육 지지'),
((SELECT id FROM product WHERE name = '야구 배트 그립 테이프' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '중국', '고무', '새상품', '블랙', '타격마스터', '그립력 강화, 미끄럼 방지'),
((SELECT id FROM product WHERE name = '롤링스 야구공 (1개)' AND category = '야구' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '중국', '가죽/코르크', '새상품', '화이트', '롤링스', '연습용, 내구성');

-- 골프 카테고리 상품 상세 정보
INSERT INTO product_details (product_id, size, release_year, manufacture_country, material, condition_description, color, brand, features) VALUES
((SELECT id FROM product WHERE name = '타이틀리스트 드라이버' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '460cc', '2024년', '미국', '티타늄', '새상품', '블랙', '타이틀리스트', '비거리 향상, 관용성 우수'),
((SELECT id FROM product WHERE name = '캘러웨이 아이언 세트' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '5-PW', '2023년', '미국', '스테인리스 스틸', '새상품', '실버', '캘러웨이', '정확한 방향성, 쉬운 스윙'),
((SELECT id FROM product WHERE name = '테일러메이드 퍼터' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '34인치', '2024년', '미국', '스틸', '새상품', '블랙/레드', '테일러메이드', '안정적인 퍼팅, 뛰어난 터치감'),
((SELECT id FROM product WHERE name = '브리지스톤 골프공 (12개)' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '일본', '우레탄', '새상품', '화이트', '브리지스톤', '높은 스핀, 부드러운 타구감'),
((SELECT id FROM product WHERE name = '골프 장갑 (남성용)' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '24호', '2024년', '인도네시아', '합성피혁', '새상품', '화이트', '골프마스터', '뛰어난 그립, 편안한 착용'),
((SELECT id FROM product WHERE name = '골프화 (스파이크리스)' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '265mm', '2023년', '베트남', '합성가죽', '새상품', '블랙', '필드워크', '뛰어난 접지력, 편안한 보행'),
((SELECT id FROM product WHERE name = '골프 캐디백' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '9인치', '2024년', '중국', '폴리에스터', '새상품', '네이비', '백마스터', '넉넉한 수납, 경량'),
((SELECT id FROM product WHERE name = '골프 거리 측정기' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '한국', '플라스틱', '새상품', '블랙', '정확도100', '레이저 측정, 경사 보정'),
((SELECT id FROM product WHERE name = '골프 우산' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '대형', '2024년', '중국', '방수천', '새상품', '블랙', '레인쉴드', '강력 방수, 넓은 면적'),
((SELECT id FROM product WHERE name = '골프 모자' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '베트남', '면', '새상품', '화이트', '골프스타일', '햇빛 차단, 통기성'),
((SELECT id FROM product WHERE name = '골프 연습 매트' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '1.5m x 1m', '2024년', '중국', '인조잔디/고무', '새상품', '그린', '연습의신', '실내 연습, 휴대 용이'),
((SELECT id FROM product WHERE name = '골프 티 (100개)' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '다양', '2023년', '중국', '플라스틱', '새상품', '멀티', '티마스터', '다양한 높이, 내구성'),
((SELECT id FROM product WHERE name = '골프 스윙 연습기' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '한국', '스틸/고무', '새상품', '블랙', '스윙닥터', '스윙 교정, 근력 강화'),
((SELECT id FROM product WHERE name = '골프 클럽 커버 세트' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '드라이버/우드/유틸', '2023년', '중국', '합성피혁', '새상품', '블랙', '클럽프로텍터', '클럽 보호, 디자인'),
((SELECT id FROM product WHERE name = '골프 장갑 (여성용)' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '19호', '2024년', '인도네시아', '합성피혁', '새상품', '핑크', '골프마스터', '뛰어난 그립, 편안한 착용'),
((SELECT id FROM product WHERE name = '골프 볼 마커' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '소형', '2023년', '중국', '금속', '새상품', '실버', '마커콜렉터', '볼 위치 표시, 자석 부착'),
((SELECT id FROM product WHERE name = '골프 타월' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '한국', '면', '새상품', '블랙', '타월마스터', '클럽/볼 세척, 흡수성'),
((SELECT id FROM product WHERE name = '골프 연습망' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '3m x 2m', '2023년', '중국', '나일론/스틸', '새상품', '블랙', '연습의신', '실내/외 연습, 견고함'),
((SELECT id FROM product WHERE name = '골프웨어 상의 (폴로)' AND category = '골프' ORDER BY created_at DESC LIMIT 1), 'L', '2024년', '베트남', '폴리에스터', '새상품', '블루', '필드패션', '흡습속건, UV 차단'),
((SELECT id FROM product WHERE name = '골프화 클리너' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '소형', '2023년', '한국', '솔/액체', '새상품', '투명', '슈즈케어', '골프화 오염 제거, 관리 용이'),
((SELECT id FROM product WHERE name = '골프 연습용 볼 (10개)' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '중국', '플라스틱', '새상품', '화이트', '연습의신', '실내 연습, 내구성'),
((SELECT id FROM product WHERE name = '골프 스윙 분석기' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '미국', '플라스틱/전자', '새상품', '블랙', '스윙닥터', '스윙 데이터 분석, 휴대 용이'),
((SELECT id FROM product WHERE name = '골프 장갑 (양손)' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '22호', '2024년', '인도네시아', '합성피혁', '새상품', '블랙', '골프마스터', '양손 착용, 그립력 강화'),
((SELECT id FROM product WHERE name = '골프 클럽 세척 브러쉬' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '소형', '2023년', '중국', '플라스틱/솔', '새상품', '블랙', '클럽케어', '클럽 헤드 청소, 휴대 용이'),
((SELECT id FROM product WHERE name = '골프 파우치' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '소형', '2024년', '중국', '폴리에스터', '새상품', '그레이', '액세서리마스터', '작은 소품 수납, 휴대 용이'),
((SELECT id FROM product WHERE name = '골프 연습용 퍼팅컵' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '중국', '플라스틱', '새상품', '그린', '퍼팅마스터', '실내 퍼팅 연습, 자동 반환'),
((SELECT id FROM product WHERE name = '골프 스코어 카드 홀더' AND category = '골프' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '한국', '합성피혁', '새상품', '블랙', '기록마스터', '스코어 카드 보관, 휴대 용이');

-- 러닝 카테고리 상품 상세 정보
INSERT INTO product_details (product_id, size, release_year, manufacture_country, material, condition_description, color, brand, features) VALUES
((SELECT id FROM product WHERE name = '나이키 에어줌 러닝화' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '260mm', '2024년', '베트남', '메쉬', '새상품', '블랙/화이트', '나이키', '경량, 쿠셔닝 강화'),
((SELECT id FROM product WHERE name = '아디다스 울트라부스트 러닝화' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '270mm', '2023년', '중국', '프라임니트', '새상품', '그레이', '아디다스', '최상의 쿠셔닝, 편안함'),
((SELECT id FROM product WHERE name = '아식스 젤카야노 러닝화' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '265mm', '2024년', '인도네시아', '메쉬', '사용감 적음', '블루/옐로우', '아식스', '안정성, 장거리 러닝'),
((SELECT id FROM product WHERE name = '뉴발란스 프레쉬폼 러닝화' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '275mm', '2023년', '베트남', '합성섬유', '새상품', '네이비', '뉴발란스', '부드러운 착용감, 반응성'),
((SELECT id FROM product WHERE name = '러닝용 컴프레션 타이츠' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), 'M', '2024년', '중국', '폴리에스터/스판덱스', '새상품', '블랙', '컴프레션', '근육 지지, 피로 감소'),
((SELECT id FROM product WHERE name = '러닝 벨트 (물통 포함)' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '한국', '네오프렌', '새상품', '블랙', '하이드레이션', '수분 보충, 수납 공간'),
((SELECT id FROM product WHERE name = '나이키 드라이핏 러닝 티셔츠' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), 'L', '2024년', '베트남', '폴리에스터', '새상품', '블랙', '나이키', '흡습속건, 통기성'),
((SELECT id FROM product WHERE name = '러닝용 스마트워치' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '미국', '플라스틱/실리콘', '새상품', '블랙', '스마트런', 'GPS, 심박수 측정'),
((SELECT id FROM product WHERE name = '아디다스 러닝 반바지' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), 'M', '2024년', '중국', '폴리에스터', '새상품', '그레이', '아디다스', '경량, 편안한 움직임'),
((SELECT id FROM product WHERE name = '러닝용 헤드밴드' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '폴리에스터', '새상품', '블랙', '땀방지', '땀 흡수, 머리 고정'),
((SELECT id FROM product WHERE name = '아식스 러닝 양말 (3켤레)' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '한국', '면/스판덱스', '새상품', '화이트', '아식스', '발 편안함, 물집 방지'),
((SELECT id FROM product WHERE name = '러닝용 암밴드 (스마트폰)' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '베트남', '네오프렌', '새상품', '블랙', '폰런', '스마트폰 고정, 터치 가능'),
((SELECT id FROM product WHERE name = '뉴발란스 러닝 재킷' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), 'L', '2024년', '중국', '나일론', '새상품', '블루', '뉴발란스', '방풍, 경량'),
((SELECT id FROM product WHERE name = '러닝용 선글라스' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '폴리카보네이트', '새상품', '블랙', '아이쉴드', 'UV 차단, 경량'),
((SELECT id FROM product WHERE name = '나이키 러닝 백팩' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '베트남', '폴리에스터', '새상품', '블랙', '나이키', '경량, 수납 용이'),
((SELECT id FROM product WHERE name = '러닝화 클리너' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '소형', '2023년', '한국', '액체', '새상품', '투명', '슈즈케어', '러닝화 오염 제거, 관리 용이'),
((SELECT id FROM product WHERE name = '아디다스 러닝 캡' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '중국', '폴리에스터', '새상품', '화이트', '아디다스', '햇빛 차단, 통기성'),
((SELECT id FROM product WHERE name = '러닝용 에너지젤 (5개)' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '1회용', '2023년', '미국', '젤', '새상품', '멀티', '에너지부스트', '빠른 에너지 공급, 휴대 용이'),
((SELECT id FROM product WHERE name = '아식스 러닝 트레이닝복' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), 'XL', '2024년', '인도네시아', '폴리에스터', '새상품', '네이비', '아식스', '흡습속건, 편안한 착용감'),
((SELECT id FROM product WHERE name = '러닝용 스마트밴드' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '실리콘/전자', '새상품', '블랙', '헬스밴드', '활동량 측정, 심박수 모니터링'),
((SELECT id FROM product WHERE name = '뉴발란스 러닝 티셔츠' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), 'M', '2024년', '베트남', '폴리에스터', '새상품', '그린', '뉴발란스', '데일리 착용, 통기성'),
((SELECT id FROM product WHERE name = '러닝용 물통 (500ml)' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '500ml', '2023년', '중국', '플라스틱', '새상품', '블루', '하이드레이션', '휴대 용이, BPA Free'),
((SELECT id FROM product WHERE name = '나이키 러닝 장갑' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), 'M', '2024년', '베트남', '폴리에스터', '새상품', '블랙', '나이키', '보온성, 터치스크린 호환'),
((SELECT id FROM product WHERE name = '러닝용 반사 조끼' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '폴리에스터', '새상품', '형광옐로우', '세이프티런', '야간 안전, 높은 시인성'),
((SELECT id FROM product WHERE name = '아디다스 러닝화 (초경량)' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '260mm', '2024년', '인도네시아', '메쉬', '새상품', '화이트/블랙', '아디다스', '초경량, 빠른 스피드'),
((SELECT id FROM product WHERE name = '러닝용 양말 (발목)' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '한국', '면/스판덱스', '새상품', '블랙', '발편한양말', '발목 보호, 편안한 착용'),
((SELECT id FROM product WHERE name = '아식스 러닝 반바지' AND category = '러닝' ORDER BY created_at DESC LIMIT 1), 'L', '2024년', '베트남', '폴리에스터', '새상품', '네이비', '아식스', '흡습속건, 통기성 우수');

-- 탁구 카테고리 상품 상세 정보
INSERT INTO product_details (product_id, size, release_year, manufacture_country, material, condition_description, color, brand, features) VALUES
((SELECT id FROM product WHERE name = '버터플라이 탁구 라켓' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '쉐이크핸드', '2024년', '일본', '목재/고무', '새상품', '블랙/레드', '버터플라이', '공격형, 뛰어난 스핀'),
((SELECT id FROM product WHERE name = '니타쿠 탁구공 (6개)' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '40+', '2023년', '일본', '플라스틱', '새상품', '화이트', '니타쿠', '공식 경기구, 높은 내구성'),
((SELECT id FROM product WHERE name = '탁구대 (접이식)' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '중국', 'MDF/스틸', '새상품', '블루', '탁구마스터', '간편 설치, 공간 활용'),
((SELECT id FROM product WHERE name = '도닉 탁구 라켓 케이스' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', 'EVA', '새상품', '블랙', '도닉', '라켓 보호, 휴대 용이'),
((SELECT id FROM product WHERE name = '탁구 네트 및 포스트 세트' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '중국', '나일론/금속', '새상품', '블랙', '네트마스터', '간편 설치, 견고함'),
((SELECT id FROM product WHERE name = '탁구 라켓 고무 클리너' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '100ml', '2023년', '독일', '액체', '새상품', '투명', '러버케어', '고무 수명 연장, 먼지 제거'),
((SELECT id FROM product WHERE name = '탁구화 (미끄럼 방지)' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '260mm', '2024년', '베트남', '합성피혁/고무', '새상품', '블루/화이트', '스피드풋', '미끄럼 방지, 빠른 움직임'),
((SELECT id FROM product WHERE name = '탁구 로봇 (초급용)' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '소형', '2023년', '중국', '플라스틱', '새상품', '화이트', '로봇티칭', '초보자 연습, 다양한 구질'),
((SELECT id FROM product WHERE name = '탁구대 커버' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '중국', '방수천', '새상품', '그레이', '테이블프로텍터', '탁구대 보호, 방수 기능'),
((SELECT id FROM product WHERE name = '탁구 라켓 (초급자용)' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '쉐이크핸드', '2023년', '중국', '목재/고무', '새상품', '블루', '입문탁구', '초보자용, 쉬운 컨트롤'),
((SELECT id FROM product WHERE name = '탁구공 수거 네트' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '중국', '나일론/금속', '새상품', '블랙', '볼콜렉터', '공 수거 용이, 시간 절약'),
((SELECT id FROM product WHERE name = '탁구 훈련용 볼 머신' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '중형', '2023년', '중국', '플라스틱/전자', '새상품', '블랙', '트레이닝로봇', '다양한 훈련 모드, 자동 서브'),
((SELECT id FROM product WHERE name = '탁구 라켓 그립 테이프' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '한국', '고무', '새상품', '블랙', '그립마스터', '그립력 강화, 미끄럼 방지'),
((SELECT id FROM product WHERE name = '탁구용 땀 밴드' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '면', '새상품', '화이트', '땀방지', '땀 흡수, 편안한 착용'),
((SELECT id FROM product WHERE name = '탁구 유니폼 상의' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), 'L', '2024년', '베트남', '폴리에스터', '새상품', '블루/옐로우', '탁구스타일', '흡습속건, 통기성'),
((SELECT id FROM product WHERE name = '탁구화 가방' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '폴리에스터', '새상품', '블랙', '슈즈백', '신발 수납, 통기성'),
((SELECT id FROM product WHERE name = '탁구 라켓 (중급자용)' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '쉐이크핸드', '2024년', '일본', '목재/고무', '새상품', '그린/블랙', '실력향상', '균형 잡힌 성능, 다양한 기술'),
((SELECT id FROM product WHERE name = '탁구공 (12개입)' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '40+', '2023년', '중국', '플라스틱', '새상품', '오렌지', '연습볼', '훈련용, 내구성'),
((SELECT id FROM product WHERE name = '탁구대 청소용 브러쉬' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '소형', '2024년', '중국', '플라스틱/솔', '새상품', '블랙', '테이블케어', '탁구대 청소, 먼지 제거'),
((SELECT id FROM product WHERE name = '탁구 라켓 세트 (2인용)' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '목재/고무', '새상품', '멀티', '커플탁구', '2인용 세트, 휴대 용이'),
((SELECT id FROM product WHERE name = '탁구용 양말' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '한국', '면/스판덱스', '새상품', '블랙', '발편한양말', '발목 보호, 편안한 착용'),
((SELECT id FROM product WHERE name = '탁구공 케이스' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '소형', '2023년', '중국', '플라스틱', '새상품', '투명', '볼보관', '탁구공 보관, 휴대 용이'),
((SELECT id FROM product WHERE name = '탁구 라켓 고무 (1장)' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '독일', '고무', '새상품', '레드', '러버교체', '공격/수비용, 다양한 종류'),
((SELECT id FROM product WHERE name = '탁구대 보호대' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '중국', '고무', '새상품', '블랙', '테이블프로텍터', '탁구대 모서리 보호, 내구성'),
((SELECT id FROM product WHERE name = '탁구 훈련용 콘' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '소형', '2024년', '중국', '플라스틱', '새상품', '오렌지', '트레이닝마스터', '민첩성 훈련, 휴대 용이'),
((SELECT id FROM product WHERE name = '탁구 라켓 엣지 테이프' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '한국', '고무', '새상품', '블랙', '라켓케어', '라켓 보호, 내구성'),
((SELECT id FROM product WHERE name = '탁구공 자동 서브기' AND category = '탁구' ORDER BY created_at DESC LIMIT 1), '소형', '2024년', '중국', '플라스틱/전자', '새상품', '화이트', '서브마스터', '자동 서브, 개인 연습');

-- 수영 카테고리 상품 상세 정보
INSERT INTO product_details (product_id, size, release_year, manufacture_country, material, condition_description, color, brand, features) VALUES
((SELECT id FROM product WHERE name = '스피도 수영복 (남성용)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), 'M', '2024년', '베트남', '폴리에스터/스판덱스', '새상품', '블랙/블루', '스피도', '탄력성, 빠른 건조'),
((SELECT id FROM product WHERE name = '아레나 수영복 (여성용)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), 'L', '2023년', '중국', '나일론/스판덱스', '새상품', '네이비/핑크', '아레나', '편안한 착용감, 디자인'),
((SELECT id FROM product WHERE name = '미즈노 수경' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '일본', '실리콘/폴리카보네이트', '새상품', '블랙', '미즈노', '김서림 방지, 넓은 시야'),
((SELECT id FROM product WHERE name = '수영모 (실리콘)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '실리콘', '새상품', '블루', '아쿠아캡', '편안한 착용, 방수'),
((SELECT id FROM product WHERE name = '수영 귀마개' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '중국', '실리콘', '새상품', '투명', '이어쉴드', '방수, 귀 보호'),
((SELECT id FROM product WHERE name = '수영 코마개' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '실리콘', '새상품', '블랙', '노즈쉴드', '코 보호, 편안한 착용'),
((SELECT id FROM product WHERE name = '수영 킥판' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '표준', '2024년', '중국', 'EVA', '새상품', '블루', '킥마스터', '발차기 연습, 부력 제공'),
((SELECT id FROM product WHERE name = '수영 풀 부이' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '중국', 'EVA', '새상품', '블랙', '풀부이', '팔 훈련, 자세 교정'),
((SELECT id FROM product WHERE name = '수영 타월 (극세사)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '대형', '2024년', '한국', '극세사', '새상품', '블루', '퀵드라이', '빠른 건조, 높은 흡수력'),
((SELECT id FROM product WHERE name = '수영 가방 (방수)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', 'PVC', '새상품', '블랙', '드라이백', '완전 방수, 넉넉한 수납'),
((SELECT id FROM product WHERE name = '수영복 세탁 세제' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '500ml', '2024년', '한국', '액체', '새상품', '투명', '스윔케어', '수영복 전용, 염소 제거'),
((SELECT id FROM product WHERE name = '수영 발차기 연습 밴드' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2023년', '중국', '고무', '새상품', '블랙', '킥밴드', '발차기 교정, 근력 강화'),
((SELECT id FROM product WHERE name = '수영 오리발 (초급용)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '250mm', '2024년', '중국', '실리콘', '새상품', '블루', '핀마스터', '초보자용, 추진력 향상'),
((SELECT id FROM product WHERE name = '수영용 물안경 김서림 방지액' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '15ml', '2023년', '일본', '액체', '새상품', '투명', '안티포그', '김서림 방지, 선명한 시야'),
((SELECT id FROM product WHERE name = '수영장용 슬리퍼' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '260mm', '2024년', '중국', 'EVA', '새상품', '네이비', '아쿠아슬리퍼', '미끄럼 방지, 빠른 건조'),
((SELECT id FROM product WHERE name = '수영용 아쿠아 슈즈' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '270mm', '2023년', '베트남', '네오프렌/고무', '새상품', '블랙', '아쿠아슈즈', '발 보호, 미끄럼 방지'),
((SELECT id FROM product WHERE name = '수영 레인 줄 (개인용)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '5m', '2024년', '중국', '플라스틱/나일론', '새상품', '블루/화이트', '레인마스터', '개인 레인 구분, 훈련용'),
((SELECT id FROM product WHERE name = '수영 강습용 보드' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '표준', '2023년', '중국', 'EVA', '새상품', '옐로우', '강습보드', '초보자 강습, 부력 제공'),
((SELECT id FROM product WHERE name = '수영복 (아동용)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '120cm', '2024년', '베트남', '폴리에스터/스판덱스', '새상품', '핑크', '키즈스윔', '아동용, 귀여운 디자인'),
((SELECT id FROM product WHERE name = '수영용 선크림 (방수)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '50ml', '2023년', '한국', '크림', '새상품', '화이트', '선쉴드', '강력 방수, UV 차단'),
((SELECT id FROM product WHERE name = '수영장용 비치볼' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '60cm', '2024년', '중국', 'PVC', '새상품', '멀티', '비치볼', '물놀이용, 휴대 용이'),
((SELECT id FROM product WHERE name = '수영복 건조대 (휴대용)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '소형', '2023년', '중국', '플라스틱', '새상품', '화이트', '드라이랙', '간편 건조, 접이식'),
((SELECT id FROM product WHERE name = '수영용 mp3 플레이어 (방수)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '중국', '플라스틱/전자', '새상품', '블랙', '아쿠아뮤직', '완전 방수, 음악 감상'),
((SELECT id FROM product WHERE name = '수영장 입장권 (1회용)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '성인', '2023년', '한국', '종이', '새상품', '블루', '입장권', '수영장 1회 입장'),
((SELECT id FROM product WHERE name = '수영용 핀' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '260mm', '2024년', '중국', '실리콘', '새상품', '옐로우', '핀마스터', '발차기 강화, 훈련용'),
((SELECT id FROM product WHERE name = '수영복 (래쉬가드)' AND category = '수영' ORDER BY created_at DESC LIMIT 1), 'M', '2023년', '베트남', '폴리에스터/스판덱스', '새상품', '블랙', '래쉬가드', '자외선 차단, 체온 유지'),
((SELECT id FROM product WHERE name = '수영용 스노클' AND category = '수영' ORDER BY created_at DESC LIMIT 1), '프리사이즈', '2024년', '중국', '실리콘/플라스틱', '새상품', '블랙', '스노클마스터', '수중 호흡, 초보자용');








commit;
select * from product;
select * from product_details;
