-- 카테고리 데이터 삽입 (8개)
INSERT INTO categories (name) VALUES ('상의');
INSERT INTO categories (name) VALUES ('아우터');
INSERT INTO categories (name) VALUES ('바지');
INSERT INTO categories (name) VALUES ('스니커즈');
INSERT INTO categories (name) VALUES ('가방');
INSERT INTO categories (name) VALUES ('모자');
INSERT INTO categories (name) VALUES ('양말');
INSERT INTO categories (name) VALUES ('액세서리');

-- 브랜드 데이터 삽입 (9개: A~I)
INSERT INTO brands (name) VALUES ('A');
INSERT INTO brands (name) VALUES ('B');
INSERT INTO brands (name) VALUES ('C');
INSERT INTO brands (name) VALUES ('D');
INSERT INTO brands (name) VALUES ('E');
INSERT INTO brands (name) VALUES ('F');
INSERT INTO brands (name) VALUES ('G');
INSERT INTO brands (name) VALUES ('H');
INSERT INTO brands (name) VALUES ('I');

-- 상품 데이터 삽입 (브랜드별 카테고리별 가격)
-- 브랜드 A
INSERT INTO products (brand_id, category_id, price) VALUES (1, 1, 11200); -- A, 상의
INSERT INTO products (brand_id, category_id, price) VALUES (1, 2, 5500);  -- A, 아우터
INSERT INTO products (brand_id, category_id, price) VALUES (1, 3, 4200);  -- A, 바지
INSERT INTO products (brand_id, category_id, price) VALUES (1, 4, 9000);  -- A, 스니커즈
INSERT INTO products (brand_id, category_id, price) VALUES (1, 5, 2000);  -- A, 가방
INSERT INTO products (brand_id, category_id, price) VALUES (1, 6, 1700);  -- A, 모자
INSERT INTO products (brand_id, category_id, price) VALUES (1, 7, 1800);  -- A, 양말
INSERT INTO products (brand_id, category_id, price) VALUES (1, 8, 2300);  -- A, 액세서리

-- 브랜드 B
INSERT INTO products (brand_id, category_id, price) VALUES (2, 1, 10500); -- B, 상의
INSERT INTO products (brand_id, category_id, price) VALUES (2, 2, 5900);  -- B, 아우터
INSERT INTO products (brand_id, category_id, price) VALUES (2, 3, 3800);  -- B, 바지
INSERT INTO products (brand_id, category_id, price) VALUES (2, 4, 9100);  -- B, 스니커즈
INSERT INTO products (brand_id, category_id, price) VALUES (2, 5, 2100);  -- B, 가방
INSERT INTO products (brand_id, category_id, price) VALUES (2, 6, 2000);  -- B, 모자
INSERT INTO products (brand_id, category_id, price) VALUES (2, 7, 2000);  -- B, 양말
INSERT INTO products (brand_id, category_id, price) VALUES (2, 8, 2200);  -- B, 액세서리

-- 브랜드 C
INSERT INTO products (brand_id, category_id, price) VALUES (3, 1, 10000); -- C, 상의
INSERT INTO products (brand_id, category_id, price) VALUES (3, 2, 6200);  -- C, 아우터
INSERT INTO products (brand_id, category_id, price) VALUES (3, 3, 3300);  -- C, 바지
INSERT INTO products (brand_id, category_id, price) VALUES (3, 4, 9200);  -- C, 스니커즈
INSERT INTO products (brand_id, category_id, price) VALUES (3, 5, 2200);  -- C, 가방
INSERT INTO products (brand_id, category_id, price) VALUES (3, 6, 1900);  -- C, 모자
INSERT INTO products (brand_id, category_id, price) VALUES (3, 7, 2200);  -- C, 양말
INSERT INTO products (brand_id, category_id, price) VALUES (3, 8, 2100);  -- C, 액세서리

-- 브랜드 D
INSERT INTO products (brand_id, category_id, price) VALUES (4, 1, 10100); -- D, 상의
INSERT INTO products (brand_id, category_id, price) VALUES (4, 2, 5100);  -- D, 아우터
INSERT INTO products (brand_id, category_id, price) VALUES (4, 3, 3000);  -- D, 바지
INSERT INTO products (brand_id, category_id, price) VALUES (4, 4, 9500);  -- D, 스니커즈
INSERT INTO products (brand_id, category_id, price) VALUES (4, 5, 2500);  -- D, 가방
INSERT INTO products (brand_id, category_id, price) VALUES (4, 6, 1500);  -- D, 모자
INSERT INTO products (brand_id, category_id, price) VALUES (4, 7, 2400);  -- D, 양말
INSERT INTO products (brand_id, category_id, price) VALUES (4, 8, 2000);  -- D, 액세서리

-- 브랜드 E
INSERT INTO products (brand_id, category_id, price) VALUES (5, 1, 10700); -- E, 상의
INSERT INTO products (brand_id, category_id, price) VALUES (5, 2, 5000);  -- E, 아우터
INSERT INTO products (brand_id, category_id, price) VALUES (5, 3, 3800);  -- E, 바지
INSERT INTO products (brand_id, category_id, price) VALUES (5, 4, 9900);  -- E, 스니커즈
INSERT INTO products (brand_id, category_id, price) VALUES (5, 5, 2300);  -- E, 가방
INSERT INTO products (brand_id, category_id, price) VALUES (5, 6, 1800);  -- E, 모자
INSERT INTO products (brand_id, category_id, price) VALUES (5, 7, 2100);  -- E, 양말
INSERT INTO products (brand_id, category_id, price) VALUES (5, 8, 2100);  -- E, 액세서리

-- 브랜드 F
INSERT INTO products (brand_id, category_id, price) VALUES (6, 1, 11200); -- F, 상의
INSERT INTO products (brand_id, category_id, price) VALUES (6, 2, 7200);  -- F, 아우터
INSERT INTO products (brand_id, category_id, price) VALUES (6, 3, 4000);  -- F, 바지
INSERT INTO products (brand_id, category_id, price) VALUES (6, 4, 9300);  -- F, 스니커즈
INSERT INTO products (brand_id, category_id, price) VALUES (6, 5, 2100);  -- F, 가방
INSERT INTO products (brand_id, category_id, price) VALUES (6, 6, 1600);  -- F, 모자
INSERT INTO products (brand_id, category_id, price) VALUES (6, 7, 2300);  -- F, 양말
INSERT INTO products (brand_id, category_id, price) VALUES (6, 8, 1900);  -- F, 액세서리

-- 브랜드 G
INSERT INTO products (brand_id, category_id, price) VALUES (7, 1, 10500); -- G, 상의
INSERT INTO products (brand_id, category_id, price) VALUES (7, 2, 5800);  -- G, 아우터
INSERT INTO products (brand_id, category_id, price) VALUES (7, 3, 3900);  -- G, 바지
INSERT INTO products (brand_id, category_id, price) VALUES (7, 4, 9000);  -- G, 스니커즈
INSERT INTO products (brand_id, category_id, price) VALUES (7, 5, 2200);  -- G, 가방
INSERT INTO products (brand_id, category_id, price) VALUES (7, 6, 1700);  -- G, 모자
INSERT INTO products (brand_id, category_id, price) VALUES (7, 7, 2100);  -- G, 양말
INSERT INTO products (brand_id, category_id, price) VALUES (7, 8, 2000);  -- G, 액세서리

-- 브랜드 H
INSERT INTO products (brand_id, category_id, price) VALUES (8, 1, 10800); -- H, 상의
INSERT INTO products (brand_id, category_id, price) VALUES (8, 2, 6300);  -- H, 아우터
INSERT INTO products (brand_id, category_id, price) VALUES (8, 3, 3100);  -- H, 바지
INSERT INTO products (brand_id, category_id, price) VALUES (8, 4, 9700);  -- H, 스니커즈
INSERT INTO products (brand_id, category_id, price) VALUES (8, 5, 2100);  -- H, 가방
INSERT INTO products (brand_id, category_id, price) VALUES (8, 6, 1600);  -- H, 모자
INSERT INTO products (brand_id, category_id, price) VALUES (8, 7, 2000);  -- H, 양말
INSERT INTO products (brand_id, category_id, price) VALUES (8, 8, 2000);  -- H, 액세서리

-- 브랜드 I
INSERT INTO products (brand_id, category_id, price) VALUES (9, 1, 11400); -- I, 상의
INSERT INTO products (brand_id, category_id, price) VALUES (9, 2, 6700);  -- I, 아우터
INSERT INTO products (brand_id, category_id, price) VALUES (9, 3, 3200);  -- I, 바지
INSERT INTO products (brand_id, category_id, price) VALUES (9, 4, 9500);  -- I, 스니커즈
INSERT INTO products (brand_id, category_id, price) VALUES (9, 5, 2400);  -- I, 가방
INSERT INTO products (brand_id, category_id, price) VALUES (9, 6, 1700);  -- I, 모자
INSERT INTO products (brand_id, category_id, price) VALUES (9, 7, 1700);  -- I, 양말
INSERT INTO products (brand_id, category_id, price) VALUES (9, 8, 2400);  -- I, 액세서리
