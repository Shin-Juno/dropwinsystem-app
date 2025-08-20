CREATE DATABASE IF NOT EXISTS dropwin;
use dropwin;

-- 공지사항 DB
DROP TABLE IF EXISTS notice;
CREATE TABLE IF NOT EXISTS notice (
    notice_id      INTEGER AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(1000) NOT NULL,      
    writer         VARCHAR(100),               	
    content        VARCHAR(10000) NOT NULL,            
    view_count     INT DEFAULT 0,       	    
    reg_date       TIMESTAMP NOT NULL,	        
    attach_file    VARCHAR(500),			            
    like_count     INT DEFAULT 0,            	
    dislike_count  INT DEFAULT 0             	
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE notice ADD COLUMN is_notice BOOLEAN DEFAULT FALSE;
UPDATE notice SET is_notice = TRUE WHERE notice_id IN (1, 2, 3);

SELECT notice_id, title, is_notice FROM notice WHERE is_notice = TRUE;

INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('회원가입 이벤트 안내', '신규 회원을 위한 이벤트를 진행합니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('서버 점검 안내', '금일 00시부터 서버 점검이 있습니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('2025년 휴무 일정', '2025년 주요 휴무일을 안내드립니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('신규 기능 업데이트', '게시판에 새로운 기능이 추가되었습니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('데이터 백업 완료', '모든 유저 데이터 백업이 완료되었습니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);

INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('정기 점검 예정', '다음 주 월요일 오전 2시부터 점검이 예정되어 있습니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('1:1 문의 응답 시간 변경', '문의 응답 시간이 단축되었습니다.', 0, CURRENT_TIMESTAMP, NULL, 'manager', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('공지사항 게시판 이용 안내', '공지사항 게시판 이용 시 주의사항을 확인하세요.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('보안 강화 업데이트', '개인정보 보호를 위한 보안이 강화되었습니다.', 0, CURRENT_TIMESTAMP, NULL, 'devteam', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('모바일 앱 출시 안내', '모바일 앱이 출시되었습니다. 많은 이용 바랍니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);

INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('시스템 오류 복구 완료', '일시적인 시스템 오류가 복구되었습니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('게시판 사용법 안내', '게시글 작성 및 수정 방법을 확인하세요.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('이벤트 종료 안내', '이벤트가 종료되었음을 안내드립니다.', 0, CURRENT_TIMESTAMP, NULL, 'eventmgr', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('사용자 약관 변경', '약관이 일부 변경되었습니다. 반드시 확인해 주세요.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('로그인 오류 해결 방법', '로그인 오류가 발생할 경우의 해결법 안내입니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);

INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('휴면계정 처리 안내', '1년 이상 미사용 계정은 휴면처리 됩니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('메일 인증 시스템 도입', '보안을 위해 메일 인증 시스템을 도입했습니다.', 0, CURRENT_TIMESTAMP, NULL, 'devteam', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('자료실 파일 삭제 안내', '오래된 자료실 파일이 삭제될 예정입니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('회원 등급제 도입', '이용 빈도에 따라 회원 등급제를 도입합니다.', 0, CURRENT_TIMESTAMP, NULL, 'admin', 0, 0);
INSERT INTO notice (title, content, view_count, reg_date, attach_file, writer, like_count, dislike_count) VALUES ('파일 업로드 오류 수정', '파일 업로드 시 오류가 수정되었습니다.', 0, CURRENT_TIMESTAMP, NULL, 'devteam', 0, 0);

commit;

select * from notice;

DESC notice;