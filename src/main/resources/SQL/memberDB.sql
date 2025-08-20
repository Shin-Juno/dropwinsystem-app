# DATABASE 생성 및 선택

CREATE DATABASE IF NOT EXISTS dropwin;
use dropwin;

# 테이블 생성
DROP TABLE IF EXISTS members;
CREATE TABLE IF NOT EXISTS members(
	id VARCHAR(100) PRIMARY KEY,
	name VARCHAR(10) NOT NULL,
	pass VARCHAR(100) NOT NULL,
	email VARCHAR(30) NOT NULL,	
	mobile VARCHAR(13) NOT NULL,
	ess_option VARCHAR(1) NOT NULL, # 필수 체크란
	un_option VARCHAR(1), # 선택 체크란
	reg_date TIMESTAMP NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 회원 정보 추가
INSERT INTO members VALUES('midas', '홍길동', 
	'$2a$10$aWYm2BGI/0iMuemBeF4Y8.7WZeVKAoudv/VzgQx697lYlZgQxr/pe', 
	'midastop@naver.com', '010-1234-5678', 
	'2', '0', '2022-06-06 12:10:30');
INSERT INTO members VALUES('admin', '관리자', 
'$2a$10$b3t8sn6QZGHYaRx3OS5KUuPxzWZdY5yHPRxlSdAgByQ7v0BlCLzrO', 
	'midastop1@naver.com', '010-4321-8765',
	'1', '0', '2022-05-11 11:20:50');
INSERT INTO members VALUES('spring', '강감찬', 
'$2a$10$.g6l.wyIFO1.j4u4gvVtKOnG9ACBUT1GRlDwlMZcjBxZPrCAURLaG', 
	'midas@daum.net', '010-5687-5678',
	'1', '0', '2022-06-05 12:10:30');

commit;

ALTER TABLE members ADD COLUMN role VARCHAR(100) DEFAULT 'user';

UPDATE members SET role = 'admin' WHERE id = 'admin';

ALTER TABLE members 
    MODIFY COLUMN pass VARCHAR(100) NULL,
    MODIFY COLUMN mobile VARCHAR(13) NULL,
    ADD COLUMN provider VARCHAR(20) DEFAULT 'local',
    ADD COLUMN provider_id VARCHAR(100);
    
ALTER TABLE members ADD CONSTRAINT uq_provider_providerId UNIQUE (provider, provider_id);

SELECT * FROM members;
