DROP TABLE items;

CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    start_price INT NOT NULL,
    current_price INT NOT NULL DEFAULT 0, -- 현재가 (0으로 초기화)
    buy_now_price INT,
    end_time DATETIME NOT NULL,
    image_paths VARCHAR(1024),
    transaction_method VARCHAR(50),
    return_policy TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    seller_id VARCHAR(100),
    bid_count INT DEFAULT 0,
    winner_id VARCHAR(255) -- 낙찰자 아이디
);

-- 1. 안전 모드를 일시적으로 해제합니다.
SET SQL_SAFE_UPDATES = 0;

-- 2. 오류가 발생했던 UPDATE 쿼리를 실행합니다.
UPDATE items SET current_price = start_price WHERE current_price IS NULL;

-- 3. 안전 모드를 다시 켜서 데이터 보호 기능을 활성화합니다.
SET SQL_SAFE_UPDATES = 1;

SELECT * FROM items;