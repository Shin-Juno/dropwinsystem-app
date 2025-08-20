DROP TABLE items;

CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY, -- 물품 id
    category VARCHAR(50) NOT NULL, -- 카테고리
    name VARCHAR(255) NOT NULL, -- 물품명
    description TEXT NOT NULL, -- 상세설명
    start_price INT NOT NULL, -- 시작가
    buy_now_price INT, -- 즉시구매가
    end_time DATETIME NOT NULL, -- 입찰 마감 시간
    image_paths VARCHAR(1024), -- 파일 경로들을 콤마로 구분하여 저장
    transaction_method VARCHAR(50), -- 거래방식
    return_policy TEXT, -- 환불 및 교환 정책
    status VARCHAR(20) NOT NULL DEFAULT 'pending', -- 'pending', 'approved', 'rejected'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    seller_id VARCHAR(100), -- 판매자 아이디
    bid_count INT DEFAULT 0 -- 입찰 횟수
);


ALTER TABLE items ADD COLUMN winner_id VARCHAR(255);

SELECT * FROM items;

-- 1. 'items' 테이블에 'current_price'라는 새로운 정수형(INT) 컬럼을 추가합니다.
ALTER TABLE items ADD COLUMN current_price INT;
UPDATE items SET current_price = start_price WHERE current_price IS NULL;
SET SQL_SAFE_UPDATES = 0;
SET SQL_SAFE_UPDATES = 1;

-- 2. 기존에 등록된 상품들의 'current_price' 값을 'start_price'와 동일하게 초기화합니다.
--    새로 추가된 컬럼의 기본값은 NULL이므로, 기존 데이터에 대해 초기값을 설정해주는 것입니다.
UPDATE items SET current_price = start_price WHERE current_price IS NULL;


TRUNCATE TABLE items;
-- 2025-07-29: ProductDetails 통합을 위한 컬럼 추가
ALTER TABLE items
ADD COLUMN size VARCHAR(255),
ADD COLUMN release_year VARCHAR(255),
ADD COLUMN manufacture_country VARCHAR(255),
ADD COLUMN material VARCHAR(255),
ADD COLUMN condition_description TEXT,
ADD COLUMN color VARCHAR(255),
ADD COLUMN brand VARCHAR(255),
ADD COLUMN features TEXT;