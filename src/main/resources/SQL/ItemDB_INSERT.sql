-- Ctrl + A 후 데이터 입력

	SET SQL_SAFE_UPDATES = 0;
    UPDATE items SET image_paths = REPLACE(image_paths, 'C:/Users/A/auction_uploads/', '') WHERE id IS NOT NULL;
    SET SQL_SAFE_UPDATES = 1;

     -- items 테이블 INSERT 문 (축구 카테고리)

     INSERT INTO items (category, name, start_price, buy_now_price, end_time, image_paths, transaction_method, return_policy, seller_id) VALUES
     ('soccer', '손흥민 친필 사인 유니폼', 100000, 500000, '2025-08-15 23:59:59', 'C:/Users/A/auction_uploads/football_1.jpg', '택배', '불가', 'seller1'),
     ('soccer', '2002 월드컵 기념 축구공', 50000, 200000, '2025-08-16 23:59:59', 'C:/Users/A/auction_uploads/football_2.jpg', '직거래', '가능', 'seller2'),
     ('soccer', '레알 마드리드 챔피언스리그 우승 기념 머플러', 30000, 100000, '2025-08-17 23:59:59', 'C:/Users/A/auction_uploads/football_3.jpg', '택배', '불가', 'seller3'),
     ('soccer', '맨체스터 유나이티드 올드 유니폼', 70000, 300000, '2025-08-18 23:59:59', 'C:/Users/A/auction_uploads/football_4.jpg', '택배', '가능', 'seller4'),
     ('soccer', '나이키 티엠포 레전드 축구화', 80000, 250000, '2025-08-19 23:59:59', 'C:/Users/A/auction_uploads/football_5.jpg', '직거래', '불가', 'seller5'),
     ('soccer', 'FC 바르셀로나 팀 사인 포스터', 40000, 150000, '2025-08-20 23:59:59', 'C:/Users/A/auction_uploads/football_6.jpg', '택배', '가능', 'seller6'),
     ('soccer', '아디다스 프레데터 축구화', 90000, 280000, '2025-08-21 23:59:59', 'C:/Users/A/auction_uploads/football_7.jpg', '택배', '불가', 'seller7'),
    ('soccer', '리버풀 FC 엠블럼 뱃지', 20000, 70000, '2025-08-22 23:59:59', 'C:/Users/A/auction_uploads/football_8.jpg', '직거래', '가능', 'seller8'),
    ('soccer', '국가대표팀 트레이닝복 상의', 60000, 220000, '2025-08-23 23:59:59', 'C:/Users/A/auction_uploads/football_9.jpg', '택배', '불가', 'seller9');
    
    -- items_detail 테이블 INSERT 문 (축구 카테고리)
    -- ITEM_ID는 items 테이블에 INSERT된 후 자동으로 생성되는 ID를 참조해야 합니다.
    -- 따라서 실제 사용 시에는 items INSERT 후 생성된 ID를 확인하여 아래 ITEM_ID를 업데이트해야 합니다.
    -- 여기서는 예시를 위해 1부터 9까지의 임시 ID를 사용합니다.
    INSERT INTO ITEMS_DETAIL (ITEM_ID, SIZE, RELEASE_YEAR, COUNTRY, MATERIAL, ITEM_CONDITION, COLOR, BRAND, FEATURES) VALUES
    (1, '성인 XL', 2023, '대한민국', '폴리에스터', '새상품', '레드', '나이키', '손흥민 친필 사인, 정품 인증서 포함'),
    (2, '5호', 2002, '한국', '가죽', '사용감 있음', '화이트/블랙', '아디다스', '2002 월드컵 공식구 디자인'),
    (3, 'Free', 2024, '스페인', '아크릴', '새상품', '화이트/골드', '아디다스', '챔피언스리그 15회 우승 기념'),
    (4, '성인 L', 1999, '영국', '폴리에스터', '빈티지', '레드', '엄브로', '베컴 마킹, 희귀 아이템'),
    (5, '270mm', 2022, '베트남', '천연가죽', '사용감 적음', '블랙', '나이키', 'FG 스터드, 컨트롤 향상'),
    (6, 'A3', 2023, '스페인', '종이', '새상품', '멀티', '나이키', '메시, 페드리, 가비 등 주요 선수 사인'),
    (7, '265mm', 2024, '인도네시아', '인조가죽', '새상품', '블랙/레드', '아디다스', '컨트롤 프레임, 정확한 슈팅'),
    (8, 'Free', 2023, '영국', '금속', '새상품', '레드', '나이키', '리버풀 공식 라이센스 제품'),
    (9, '성인 M', 2024, '대한민국', '폴리에스터', '새상품', '화이트', '나이키', '국가대표팀 훈련용');
    
     INSERT INTO items (category, name, start_price, buy_now_price, end_time, image_paths, transaction_method, return_policy, seller_id) VALUES
     ('basket-ball', '마이클 조던 친필 사인 농구공', 200000, 1000000, '2025-08-15 23:59:59', 'C:/Users/A/auction_uploads/basketball_1.jpg', '택배', '불가', 'seller10'),
     ('basket-ball', '코비 브라이언트 레이커스 유니폼', 150000, 700000, '2025-08-16 23:59:59', 'C:/Users/A/auction_uploads/basketball_2.jpg', '직거래', '가능', 'seller11'),
     ('basket-ball', '스테판 커리 언더아머 농구화', 100000, 400000, '2025-08-17 23:59:59', 'C:/Users/A/auction_uploads/basketball_3.jpg', '택배', '불가', 'seller12'),
     ('basket-ball', 'NBA 공식 경기 농구공', 80000, 300000, '2025-08-18 23:59:59', 'C:/Users/A/auction_uploads/basketball_4.jpg', '택배', '가능', 'seller13'),
     ('basket-ball', '르브론 제임스 클리블랜드 유니폼', 120000, 550000, '2025-08-19 23:59:59', 'C:/Users/A/auction_uploads/basketball_5.jpg', '직거래', '불가', 'seller14'),
     ('basket-ball', '나이키 에어 조던 1 레트로', 180000, 800000, '2025-08-20 23:59:59', 'C:/Users/A/auction_uploads/basketball_6.jpg', '택배', '가능', 'seller15'),
     ('basket-ball', '카이리 어빙 시그니처 농구화', 90000, 350000, '2025-08-21 23:59:59', 'C:/Users/A/auction_uploads/basketball_7.jpg', '택배', '불가', 'seller16'),
     ('basket-ball', '보스턴 셀틱스 팀 사인 포스터', 60000, 250000, '2025-08-22 23:59:59', 'C:/Users/A/auction_uploads/basketball_8.jpg', '직거래', '가능', 'seller17'),
     ('basket-ball', 'NBA 팀 로고 스냅백 모자', 40000, 150000, '2025-08-23 23:59:59', 'C:/Users/A/auction_uploads/basketball_9.jpg', '택배', '불가', 'seller18');
    
    -- items_detail 테이블 INSERT 문 (농구 카테고리)
    -- ITEM_ID는 items 테이블에 INSERT된 후 자동으로 생성되는 ID를 참조해야 합니다.
    -- 따라서 실제 사용 시에는 items INSERT 후 생성된 ID를 업데이트해야 합니다.
    -- 여기서는 예시를 위해 101부터 109까지의 임시 ID를 사용합니다. (이전 축구 아이템 ID와 겹치지 않도록 임의로 설정)
    INSERT INTO ITEMS_DETAIL (ITEM_ID, SIZE, RELEASE_YEAR, COUNTRY, MATERIAL, ITEM_CONDITION, COLOR, BRAND, FEATURES) VALUES
    (10, '7호', 1998, '미국', '가죽', '새상품', '오렌지', '스팔딩', '마이클 조던 친필 사인, NBA 공식구'),
    (11, '성인 XL', 2006, '미국', '폴리에스터', '최상', '퍼플/골드', '나이키', '코비 브라이언트 실착 유니폼'),
    (12, '280mm', 2023, '베트남', '합성섬유', '새상품', '블루', '언더아머', '스테판 커리 시그니처, 플로우 쿠셔닝'),
    (13, '7호', 2024, '태국', '합성가죽', '새상품', '오렌지', '스팔딩', 'NBA 공식 경기용, 실내외 겸용'),
    (14, '성인 L', 2016, '미국', '폴리에스터', '상', '와인', '나이키', '르브론 제임스 클리블랜드 복귀 시즌 유니폼'),
    (15, '275mm', 1985, '중국', '가죽', '빈티지', '블랙/레드', '나이키', '오리지널 에어 조던 1, 한정판'),
    (16, '265mm', 2022, '베트남', '합성섬유', '새상품', '블랙/화이트', '나이키', '카이리 어빙 시그니처, 반응성 좋은 쿠셔닝'),
    (17, 'A2', 2023, '미국', '종이', '새상품', '멀티', 'NBA', '제이슨 테이텀, 제일런 브라운 등 사인'),
    (18, 'Free', 2024, '중국', '면', '새상품', '블랙', '뉴에라', '골든스테이트 워리어스 로고');
   
 -- items 테이블 INSERT 문 (야구 카테고리)
     INSERT INTO items (category, name, start_price, buy_now_price, end_time, image_paths, transaction_method, return_policy, seller_id) VALUES
     ('base-ball', '류현진 친필 사인 야구공', 100000, 400000, '2025-08-15 23:59:59', 'C:/Users/A/auction_uploads/baseball_1.jpg', '택배', '불가', 'seller19'),
     ('base-ball', '이정후 샌프란시스코 자이언츠 유니폼', 80000, 350000, '2025-08-16 23:59:59', 'C:/Users/A/auction_uploads/baseball_2.jpg', '직거래', '가능', 'seller20'),
     ('base-ball', 'MLB 공식 경기 야구 배트', 120000, 500000, '2025-08-17 23:59:59', 'C:/Users/A/auction_uploads/baseball_3.jpg', '택배', '불가', 'seller21'),
     ('base-ball', '김하성 샌디에이고 파드리스 모자', 50000, 200000, '2025-08-18 23:59:59', 'C:/Users/A/auction_uploads/baseball_4.jpg', '택배', '가능', 'seller22'),
     ('base-ball', '야구 글러브 (내야수용)', 70000, 250000, '2025-08-19 23:59:59', 'C:/Users/A/auction_uploads/baseball_5.jpg', '직거래', '불가', 'seller23'),
     ('base-ball', 'KBO 리그 공식 야구공', 30000, 100000, '2025-08-20 23:59:59', 'C:/Users/A/auction_uploads/baseball_6.jpg', '택배', '가능', 'seller24'),
     ('base-ball', '오타니 쇼헤이 LA 다저스 유니폼', 150000, 600000, '2025-08-21 23:59:59', 'C:/Users/A/auction_uploads/baseball_7.jpg', '택배', '불가', 'seller25'),
    ('base-ball', '야구 헬멧 (타자용)', 60000, 220000, '2025-08-22 23:59:59', 'C:/Users/A/auction_uploads/baseball_8.jpg', '직거래', '가능', 'seller26'),
    ('base-ball', '야구화 (스파이크)', 90000, 300000, '2025-08-23 23:59:59', 'C:/Users/A/auction_uploads/baseball_9.jpg', '택배', '불가', 'seller27');
    
    -- items_detail 테이블 INSERT 문 (야구 카테고리)
    -- ITEM_ID는 items 테이블에 INSERT된 후 자동으로 생성되는 ID를 참조해야 합니다.
    -- 따라서 실제 사용 시에는 items INSERT 후 생성된 ID를 업데이트해야 합니다.
    -- 여기서는 예시를 위해 201부터 209까지의 임시 ID를 사용합니다.
    INSERT INTO ITEMS_DETAIL (ITEM_ID, SIZE, RELEASE_YEAR, COUNTRY, MATERIAL, ITEM_CONDITION, COLOR, BRAND, FEATURES) VALUES
    (19, '공인구', 2023, '대한민국', '가죽', '새상품', '화이트', 'KBO', '류현진 친필 사인, 정품 인증서 포함'),
    (20, '성인 L', 2024, '미국', '폴리에스터', '새상품', '화이트', '나이키', '이정후 데뷔 시즌 유니폼'),
    (21, '34인치', 2023, '미국', '나무', '새상품', '블랙', '배트', 'MLB 공식 인증, 홈런 타자용'),
    (22, 'Free', 2024, '미국', '면', '새상품', '블루', '뉴에라', '김하성 선수 착용 모델'),
    (23, '11.5인치', 2022, '일본', '가죽', '최상', '브라운', '미즈노', '내야수 전용, 길들이기 완료'),
    (24, '공인구', 2024, '대한민국', '가죽', '새상품', '화이트', 'KBO', 'KBO 리그 공식 사용구'),
    (25, '성인 XL', 2024, '미국', '폴리에스터', '새상품', '블루', '나이키', '오타니 쇼헤이 다저스 이적 기념 유니폼'),
    (26, '성인용', 2023, '미국', '플라스틱', '새상품', '블랙', '에보쉴드', '타자 보호용, MLB 공식 사용'),
    (27, '275mm', 2023, '베트남', '합성가죽', '새상품', '블랙', '나이키', '야구 전용 스파이크, 잔디용');
    
     -- items 테이블 INSERT 문 (골프 카테고리)
     INSERT INTO items (category, name, start_price, buy_now_price, end_time, image_paths, transaction_method, return_policy, seller_id) VALUES
     ('golf', '타이틀리스트 Pro V1 골프공 (1더즌)', 50000, 80000, '2025-08-15 23:59:59', 'C:/Users/A/auction_uploads/golf_1.jpg', '택배', '가능', 'seller28'),
     ('golf', '캘러웨이 에픽 플래시 드라이버', 300000, 700000, '2025-08-16 23:59:59', 'C:/Users/A/auction_uploads/golf_2.jpg', '택배', '불가', 'seller29'),
     ('golf', '핑 G425 아이언 세트 (5-P)', 800000, 1500000, '2025-08-17 23:59:59', 'C:/Users/A/auction_uploads/golf_3.jpg', '직거래', '가능', 'seller30'),
     ('golf', '오디세이 투볼 퍼터', 150000, 300000, '2025-08-18 23:59:59', 'C:/Users/A/auction_uploads/golf_4.jpg', '택배', '불가', 'seller31'),
     ('golf', '나이키 골프화 (남성용)', 100000, 200000, '2025-08-19 23:59:59', 'C:/Users/A/auction_uploads/golf_5.jpg', '택배', '가능', 'seller32'),
     ('golf', '테일러메이드 스파이더 X 퍼터', 200000, 450000, '2025-08-20 23:59:59', 'C:/Users/A/auction_uploads/golf_6.jpg', '직거래', '불가', 'seller33'),
     ('golf', '골프 거리 측정기 (레이저)', 180000, 350000, '2025-08-21 23:59:59', 'C:/Users/A/auction_uploads/golf_7.jpg', '택배', '가능', 'seller34'),
     ('golf', 'PXG 0311T 웨지 (52, 56, 60도)', 250000, 500000, '2025-08-22 23:59:59', 'C:/Users/A/auction_uploads/golf_8.jpg', '택배', '불가', 'seller35'),
     ('golf', '골프 캐디백 (스탠드형)', 120000, 250000, '2025-08-23 23:59:59', 'C:/Users/A/auction_uploads/golf_9.jpg', '직거래', '가능', 'seller36');
    
    -- items_detail 테이블 INSERT 문 (골프 카테고리)
    -- ITEM_ID는 items 테이블에 INSERT된 후 자동으로 생성되는 ID를 참조해야 합니다.
    -- 따라서 실제 사용 시에는 items INSERT 후 생성된 ID를 업데이트해야 합니다.
    -- 여기서는 예시를 위해 301부터 309까지의 임시 ID를 사용합니다.
    INSERT INTO ITEMS_DETAIL (ITEM_ID, SIZE, RELEASE_YEAR, COUNTRY, MATERIAL, ITEM_CONDITION, COLOR, BRAND, FEATURES) VALUES
    (28, '1더즌', 2024, '미국', '우레탄', '새상품', '화이트', '타이틀리스트', '최고급 투어볼, 스핀 컨트롤 우수'),
    (29, '10.5도', 2023, '일본', '티타늄', '최상', '블랙', '캘러웨이', 'AI 페이스 디자인, 비거리 향상'),
    (30, '5-P', 2022, '미국', '스테인리스 스틸', '상', '실버', '핑', '관용성 높은 아이언, 초중급자용'),
    (31, '34인치', 2023, '미국', '스틸', '새상품', '실버', '오디세이', '투볼 정렬 시스템, 안정적인 스트로크'),
    (32, '270mm', 2024, '베트남', '합성가죽', '새상품', '화이트', '나이키', '방수 기능, 편안한 착용감'),
    (33, '33인치', 2023, '미국', '스틸', '최상', '레드', '테일러메이드', '높은 MOI, 직진성 우수'),
    (34, 'N/A', 2024, '중국', '플라스틱', '새상품', '블랙', '부쉬넬', '정확한 거리 측정, 슬로프 기능'),
    (35, '52, 56, 60', 2023, '미국', '카본 스틸', '상', '블랙', 'PXG', '정교한 샷 컨트롤, 스핀량 증대'),
    (36, 'N/A', 2024, '한국', '나일론', '새상품', '블랙', '미즈노', '경량 스탠드백, 수납 공간 충분');
         -- items 테이블 INSERT 문 (런닝 카테고리)
     INSERT INTO items (category, name, start_price, buy_now_price, end_time, image_paths, transaction_method, return_policy, seller_id) VALUES
     ('running', '나이키 에어 줌 페가수스 40', 80000, 180000, '2025-08-15 23:59:59', 'C:/Users/A/auction_uploads/running_1.jpg', '택배', '가능', 'seller37'),
     ('running', '아디다스 울트라부스트 23', 100000, 220000, '2025-08-16 23:59:59', 'C:/Users/A/auction_uploads/running_2.jpg', '택배', '불가', 'seller38'),
     ('running', '호카 본디 8 런닝화', 120000, 250000, '2025-08-17 23:59:59', 'C:/Users/A/auction_uploads/running_3.jpg', '직거래', '가능', 'seller39'),
     ('running', '가민 포러너 965 GPS 런닝 시계', 400000, 700000, '2025-08-18 23:59:59', 'C:/Users/A/auction_uploads/running_4.jpg', '택배', '불가', 'seller40'),
     ('running', '언더아머 런닝 반팔 티셔츠', 30000, 70000, '2025-08-19 23:59:59', 'C:/Users/A/auction_uploads/running_5.jpg', '택배', '가능', 'seller41'),
     ('running', '뉴발란스 프레쉬폼 X 880v13', 90000, 190000, '2025-08-20 23:59:59', 'C:/Users/A/auction_uploads/running_6.jpg', '직거래', '불가', 'seller42'),
     ('running', '써코니 엔돌핀 프로 3', 150000, 300000, '2025-08-21 23:59:59', 'C:/Users/A/auction_uploads/running_7.jpg', '택배', '가능', 'seller43'),
     ('running', '런닝용 암밴드 스마트폰 홀더', 10000, 25000, '2025-08-22 23:59:59', 'C:/Users/A/auction_uploads/running_8.jpg', '택배', '불가', 'seller44'),
     ('running', '컴프레션 런닝 양말', 15000, 40000, '2025-08-23 23:59:59', 'C:/Users/A/auction_uploads/running_9.jpg', '직거래', '가능', 'seller45');
    
    -- items_detail 테이블 INSERT 문 (런닝 카테고리)
    -- ITEM_ID는 items 테이블에 INSERT된 후 자동으로 생성되는 ID를 참조해야 합니다.
    -- 따라서 실제 사용 시에는 items INSERT 후 생성된 ID를 업데이트해야 합니다.
    -- 여기서는 예시를 위해 401부터 409까지의 임시 ID를 사용합니다.
    INSERT INTO ITEMS_DETAIL (ITEM_ID, SIZE, RELEASE_YEAR, COUNTRY, MATERIAL, ITEM_CONDITION, COLOR, BRAND, FEATURES) VALUES
    (37, '260mm', 2024, '베트남', '메쉬', '새상품', '블랙/화이트', '나이키', '일상 런닝 및 훈련용, 반응성 좋은 쿠셔닝'),
    (38, '270mm', 2023, '중국', '프라임니트', '최상', '화이트', '아디다스', '최고의 에너지 리턴, 장거리 런닝용'),
    (39, '265mm', 2024, '베트남', '엔지니어드 메쉬', '새상품', '네이비', '호카', '최대 쿠셔닝, 편안한 착용감'),
    (40, 'N/A', 2024, '대만', '실리콘/플라스틱', '새상품', '블랙', '가민', 'AMOLED 디스플레이, 고급 런닝 지표'),
    (41, 'M', 2023, '베트남', '폴리에스터', '새상품', '그레이', '언더아머', '흡습속건, 경량 소재'),
    (42, '255mm', 2024, '베트남', '메쉬', '최상', '그레이/블루', '뉴발란스', '데일리 트레이너, 안정적인 착지감'),
    (43, '275mm', 2023, '중국', '엔지니어드 메쉬', '새상품', '화이트/블랙', '써코니', '카본 플레이트, 레이싱 성능'),
    (44, 'Free', 2024, '중국', '네오프렌', '새상품', '블랙', '나이키', '모든 스마트폰 호환, 방수 기능'),
    (45, 'L', 2023, '한국', '나일론/스판덱스', '새상품', '블랙', '컴프레스포츠', '혈액 순환 개선, 피로 감소');
    
     -- items 테이블 INSERT 문 (탁구 카테고리)
     INSERT INTO items (category, name, start_price, buy_now_price, end_time, image_paths, transaction_method, return_policy, seller_id) VALUES
     ('pingpong', '버터플라이 티모 볼 라켓', 150000, 300000, '2025-08-15 23:59:59', 'C:/Users/A/auction_uploads/pingpong_1.jpg', '택배', '가능', 'seller46'),
     ('pingpong', '도닉 블루스톰 Z1 탁구 러버', 50000, 90000, '2025-08-16 23:59:59', 'C:/Users/A/auction_uploads/pingpong_2.jpg', '택배', '불가', 'seller47'),
     ('pingpong', 'XIOM 베가 프로 탁구 러버', 45000, 80000, '2025-08-17 23:59:59', 'C:/Users/A/auction_uploads/pingpong_3.jpg', '직거래', '가능', 'seller48'),
     ('pingpong', 'ITTF 공인 탁구공 (100개입)', 30000, 60000, '2025-08-18 23:59:59', 'C:/Users/A/auction_uploads/pingpong_4.jpg', '택배', '불가', 'seller49'),
     ('pingpong', '탁구대 (접이식)', 400000, 800000, '2025-08-19 23:59:59', 'C:/Users/A/auction_uploads/pingpong_5.jpg', '직거래', '불가', 'seller50'),
     ('pingpong', '탁구 로봇 (훈련용)', 250000, 500000, '2025-08-20 23:59:59', 'C:/Users/A/auction_uploads/pingpong_6.jpg', '택배', '가능', 'seller51'),
     ('pingpong', '탁구화 (미즈노 웨이브 드라이브)', 80000, 150000, '2025-08-21 23:59:59', 'C:/Users/A/auction_uploads/pingpong_7.jpg', '택배', '불가', 'seller52'),
    ('pingpong', '탁구 라켓 케이스', 20000, 40000, '2025-08-22 23:59:59', 'C:/Users/A/auction_uploads/pingpong_8.jpg', '직거래', '가능', 'seller53'),
    ('pingpong', '탁구 네트 및 포스트 세트', 35000, 70000, '2025-08-23 23:59:59', 'C:/Users/A/auction_uploads/pingpong_9.jpg', '택배', '불가', 'seller54');
    
    -- items_detail 테이블 INSERT 문 (탁구 카테고리)
    -- ITEM_ID는 items 테이블에 INSERT된 후 자동으로 생성되는 ID를 참조해야 합니다.
    -- 따라서 실제 사용 시에는 items INSERT 후 생성된 ID를 업데이트해야 합니다.
    -- 여기서는 예시를 위해 501부터 509까지의 임시 ID를 사용합니다.
    INSERT INTO ITEMS_DETAIL (ITEM_ID, SIZE, RELEASE_YEAR, COUNTRY, MATERIAL, ITEM_CONDITION, COLOR, BRAND, FEATURES) VALUES
    (46, 'FL', 2023, '일본', '합판', '새상품', '블랙/레드', '버터플라이', '공격형 라켓, 스피드와 스핀 조화'),
    (47, '2.1mm', 2024, '독일', '고무', '새상품', '블루', '도닉', '강력한 스핀, 빠른 스피드'),
    (48, '2.0mm', 2023, '독일', '고무', '최상', '블랙', 'XIOM', '프로 선수용, 뛰어난 컨트롤'),
    (49, '40+', 2024, '중국', '플라스틱', '새상품', '화이트', '니타쿠', 'ITTF 공인구, 내구성 우수'),
    (50, '274x152.5x76cm', 2023, '중국', 'MDF', '최상', '블루', '스타', '접이식, 실내용'),
    (51, 'N/A', 2024, '중국', '플라스틱', '새상품', '화이트', '파워로봇', '다양한 구질 훈련 가능, 리모컨 포함'),
    (52, '260mm', 2023, '베트남', '합성섬유', '새상품', '블루/화이트', '미즈노', '경량성, 뛰어난 접지력'),
    (53, 'N/A', 2024, '중국', 'EVA', '새상품', '블랙', '버터플라이', '라켓 2개 수납 가능, 보호 기능'),
    (54, 'N/A', 2023, '중국', '금속/나일론', '새상품', '블랙', '스타', '클램프 방식, 설치 용이');
         -- items 테이블 INSERT 문 (수영 카테고리)
     INSERT INTO items (category, name, start_price, buy_now_price, end_time, image_paths, transaction_method, return_policy, seller_id) VALUES
     ('swim', '스피도 LZR 레이서 X 수영복 (남성용)', 200000, 400000, '2025-08-15 23:59:59', 'C:/Users/A/auction_uploads/swim_1.jpg', '택배', '불가', 'seller55'),
     ('swim', '아레나 코브라 울트라 수경', 50000, 90000, '2025-08-16 23:59:59', 'C:/Users/A/auction_uploads/swim_2.jpg', '택배', '가능', 'seller56'),
     ('swim', '펑키타 원피스 수영복 (여성용)', 80000, 150000, '2025-08-17 23:59:59', 'C:/Users/A/auction_uploads/swim_3.jpg', '직거래', '불가', 'seller57'),
     ('swim', '수영모 (실리콘)', 15000, 30000, '2025-08-18 23:59:59', 'C:/Users/A/auction_uploads/swim_4.jpg', '택배', '가능', 'seller58'),
     ('swim', '수영 귀마개 및 코마개 세트', 10000, 20000, '2025-08-19 23:59:59', 'C:/Users/A/auction_uploads/swim_5.jpg', '택배', '불가', 'seller59'),
     ('swim', '오리발 (숏핀)', 40000, 80000, '2025-08-20 23:59:59', 'C:/Users/A/auction_uploads/swim_6.jpg', '직거래', '가능', 'seller60'),
     ('swim', '수영 타월 (극세사)', 25000, 50000, '2025-08-21 23:59:59', 'C:/Users/A/auction_uploads/swim_7.jpg', '택배', '불가', 'seller61'),
     ('swim', '수영 가방 (메쉬)', 30000, 60000, '2025-08-22 23:59:59', 'C:/Users/A/auction_uploads/swim_8.jpg', '직거래', '가능', 'seller62'),
     ('swim', '킥판 및 풀부이 세트', 35000, 70000, '2025-08-23 23:59:59', 'C:/Users/A/auction_uploads/swim_9.jpg', '택배', '불가', 'seller63');
    
    -- items_detail 테이블 INSERT 문 (수영 카테고리)
    -- ITEM_ID는 items 테이블에 INSERT된 후 자동으로 생성되는 ID를 참조해야 합니다.
    -- 따라서 실제 사용 시에는 items INSERT 후 생성된 ID를 업데이트해야 합니다.
    -- 여기서는 예시를 위해 601부터 609까지의 임시 ID를 사용합니다.
    INSERT INTO ITEMS_DETAIL (ITEM_ID, SIZE, RELEASE_YEAR, COUNTRY, MATERIAL, ITEM_CONDITION, COLOR, BRAND, FEATURES) VALUES
    (55, 'M', 2024, '중국', '나일론/엘라스테인', '새상품', '블랙', '스피도', '경기용, 압축 기능, FINA 승인'),
    (56, 'Free', 2023, '이탈리아', '폴리카보네이트', '새상품', '블랙', '아레나', '안티포그, UV 차단, 넓은 시야'),
    (57, 'S', 2024, '호주', '폴리에스터', '새상품', '멀티', '펑키타', '독특한 디자인, 내염소성 우수'),
    (58, 'Free', 2023, '중국', '실리콘', '새상품', '블루', '나이키', '편안한 착용감, 머리카락 보호'),
    (59, 'Free', 2024, '중국', '실리콘', '새상품', '투명', '스피도', '방수 기능, 수영 시 필수'),
    (60, 'M', 2023, '중국', '실리콘', '최상', '블루', '아레나', '수영 훈련용, 발차기 강화'),
    (61, 'Free', 2024, '한국', '극세사', '새상품', '그레이', '코오롱', '빠른 건조, 휴대 용이'),
    (62, 'Free', 2023, '중국', '폴리에스터', '새상품', '블랙', '아레나', '메쉬 소재, 통기성 우수'),
    (63, 'Free', 2024, '중국', 'EVA', '새상품', '블루', '스피도', '수영 자세 교정, 코어 강화');