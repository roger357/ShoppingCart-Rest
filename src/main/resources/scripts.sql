-- CREATE DATABASE SHOPPING_CAR_SYS;

DROP TABLE IF EXISTS DISCOUNT_RULE;
DROP TABLE IF EXISTS DISCOUNT;
DROP TABLE IF EXISTS PROMOTION_DATE;
DROP TABLE IF EXISTS SHOPPING_CAR_PRODUCT;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS SHOPPING_CAR;
DROP TABLE IF EXISTS CLIENT;

CREATE TABLE IF NOT EXISTS CLIENT (
    ID INTEGER AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL,
    DOCUMENT VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS SHOPPING_CAR (
    ID INTEGER AUTO_INCREMENT PRIMARY KEY,
    CLIENT_ID INTEGER NOT NULL,
    SHOPPING_CAR_TYPE TINYINT NOT NULL DEFAULT 1,  -- indica si tiene promocion por fecha, vip, o es comun
    STATE INTEGER NOT NULL DEFAULT 1, -- 1 pendding   2 purchased  3 deleted
    CREATION_DATE DATE NOT NULL,
    VALUE FLOAT(12,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (CLIENT_ID) REFERENCES CLIENT(ID)
);

CREATE TABLE IF NOT EXISTS PRODUCT (
    ID INTEGER AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(50) NOT NULL,
    DESCRIPTION VARCHAR(255),
    VALUE FLOAT(12,2) NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS SHOPPING_CAR_PRODUCT (
    ID INTEGER AUTO_INCREMENT PRIMARY KEY,
    SHOPPING_CAR_ID INTEGER NOT NULL,
    PRODUCT_ID INTEGER NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    INSIDE BIT(1) NOT NULL DEFAULT 1,
    VALUE FLOAT(12,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (SHOPPING_CAR_ID) REFERENCES SHOPPING_CAR(ID),
    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(ID)
);

CREATE TABLE IF NOT EXISTS PROMOTION_DATE (
	ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	PROMO_DESC VARCHAR(100) NOT NULL DEFAULT "",
	PROMO_DATE DATE NOT NULL,
	PROMO_ACTIVE BIT(1) DEFAULT 1,
	PROMO_VIP BIT(1) DEFAULT 0
);

/** CREATE TABLE IF NOT EXISTS SIMPLE_PROMOTION_DATE (
    ID INTEGER AUTO_INCREMENT PRIMARY KEY,
    PROMO_DATE DATE NOT NULL,
    ACTIVE BIT(1) DEFAULT FALSE
); **/

-- REGLAS DE DESCUENTO
CREATE TABLE IF NOT EXISTS DISCOUNT (
	ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	COUNT_TYPE TINYINT NOT NULL,
	PRODUCT_COUNT_INIT INTEGER NOT NULL,      	-- INDICA A PARTIR DE QUE CANTIDAD APLICA EL DESCUENTO
	PRODUCT_COUNT_LIMIT INTEGER NOT NULL,      -- INDICA HASTA QUE CANTIDAD APLICA EL DESCUENTO
	ACTIVE BIT(1) DEFAULT 1
);

CREATE TABLE IF NOT EXISTS DISCOUNT_RULE (
	ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	DISCOUNT_ID INTEGER NOT NULL,
	SHOPPING_CAR_TYPE TINYINT NOT NULL,
	DISCOUNT_TYPE TINYINT NOT NULL,   	-- ESTO INDICA SI EL DESCUENTO ES % O UN VALOR NETO
	DISCOUNT_VALUE INTEGER NOT NULL,
	ACTIVE BIT(1) DEFAULT 1,
	FOREIGN KEY (DISCOUNT_ID) REFERENCES DISCOUNT(ID)
);
-- ----------------------------------------------------------

/**    INSERCION DE DATA PARA PRUEBAS    **/

/** Clientes **/

INSERT INTO CLIENT (NAME, DOCUMENT) VALUES ("Scotty Pippen", "P-12555874");
INSERT INTO CLIENT (NAME, DOCUMENT) VALUES ("Michel Roldan", "ER5566YY");
INSERT INTO CLIENT (NAME, DOCUMENT) VALUES ("Mike Jonson", "123334556");
INSERT INTO CLIENT (NAME, DOCUMENT) VALUES ("Jackeline O'donell", "78888-TY");
INSERT INTO CLIENT (NAME, DOCUMENT) VALUES ("Antwan Mase", "P-7777458");

/** PRODUCTOS **/
INSERT INTO PRODUCT (NAME, DESCRIPTION, VALUE) VALUES
("Men's Basketball Shoes", "Jordan AJ 1 Mid", 1200.00),
("Men's Golf Shoes", "Nike Men's 2020 Roshe G Golf Shoes", 240.00),
("Men's Tennis Shoes", "Asics Resolution 8 Mens Tennis Shoe", 300.00),
("Women's Tennis Shoes", "Asics Resolution 10 Womens Tennis Shoe", 290.00),
("Stripes Shorts", "4KRFT Sport 3-Stripes Shorts", 500.00),
("FreeLift Sport Prime Lite Tee", "A stretchy t-shirt made for mobility and dry comfort.", 650.00),
("Girls Are Awesome T Shirt", "A t-shirt with girl power.", 650.00),
("Stripes Shorts", "4KRFT Sport 3-Stripes Shorts", 1000.00),
("Tricot Pants", "Essentials 3-Stripes Tapered Tricot Pants", 700.00),
("Pride Linear Tee", "Pride Linear Tee", 500.00);

 /** PROMOCIONES POR FECHA ESPECIAL **/
INSERT INTO PROMOTION_DATE (PROMO_DATE, PROMO_ACTIVE, PROMO_VIP) VALUES
(STR_TO_DATE('2020-05-15', '%Y-%m-%d'), 1, 0),
(STR_TO_DATE('2020-05-16', '%Y-%m-%d'), 1, 0),
(STR_TO_DATE('2020-05-17', '%Y-%m-%d'), 1, 0),
(STR_TO_DATE('2020-05-18', '%Y-%m-%d'), 1, 0),
(STR_TO_DATE('2020-05-19', '%Y-%m-%d'), 1, 0);

/** DESCUENTO Y REGLAS DE DESCUENTO **/
INSERT INTO DISCOUNT(COUNT_TYPE, PRODUCT_COUNT_INIT, PRODUCT_COUNT_LIMIT, ACTIVE ) VALUES
(0,0, 5, 1),
(1,5, 10, 1),
(2,10, 0, 1);
-- INMEDIATAMENTE Selecciona el ultimo ID de la tabla DISCOUNT y complementa el resto de los valores
INSERT INTO DISCOUNT_RULE(DISCOUNT_ID, SHOPPING_CAR_TYPE, DISCOUNT_TYPE, DISCOUNT_VALUE, ACTIVE ) VALUES
(1, 0, 1, 200, 1 ),
(1, 2, 1, 200, 1 ),
(2, 0, 1, 200, 1 ),
(3, 0, 1, 200, 1 );


-- CARRITOS DE COMPRA PARA QUE EXISTAN CLIENTES VIP
INSERT INTO SHOPPING_CAR (CLIENT_ID,SHOPPING_CAR_TYPE,STATE,CREATION_DATE,VALUE) VALUES
(1,2,1,'2020-04-17',149800),
(1,2,1,'2020-04-19',149800),
(1,2,1,'2020-04-20',149800),
(1,2,1,'2020-04-21',149800),
(1,2,1,'2020-04-17',149800),
(1,2,1,'2020-04-16',149800),
(1,2,1,'2020-04-15',149800),
(1,2,1,'2020-04-15',149800),
(1,2,1,'2020-04-15',149800),
(2,2,1,'2020-04-17',149800),
(2,2,1,'2020-04-16',149800),
(2,2,1,'2020-04-15',149800),
(2,2,1,'2020-04-15',149800);

COMMIT;
