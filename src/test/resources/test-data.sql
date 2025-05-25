-- 테스트용 초기 데이터
INSERT INTO categories (name) VALUES ('상의');
INSERT INTO categories (name) VALUES ('아우터');
INSERT INTO categories (name) VALUES ('바지');
INSERT INTO categories (name) VALUES ('스니커즈');
INSERT INTO categories (name) VALUES ('가방');
INSERT INTO categories (name) VALUES ('모자');
INSERT INTO categories (name) VALUES ('양말');
INSERT INTO categories (name) VALUES ('액세서리');

-- 테스트용 브랜드 (기본 데이터보다 적게)
INSERT INTO brands (name) VALUES ('TestA');
INSERT INTO brands (name) VALUES ('TestB');

-- 테스트용 상품 (일부만)
INSERT INTO products (brand_id, category_id, price) VALUES (1, 1, 10000);
INSERT INTO products (brand_id, category_id, price) VALUES (1, 2, 5000);
INSERT INTO products (brand_id, category_id, price) VALUES (2, 1, 12000);
INSERT INTO products (brand_id, category_id, price) VALUES (2, 3, 4000);
