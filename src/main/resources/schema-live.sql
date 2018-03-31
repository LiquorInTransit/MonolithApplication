/*SET DATABASE UNIQUE NAME HSQLDB627A279E79
SET DATABASE GC 0
SET DATABASE DEFAULT RESULT MEMORY ROWS 0
SET DATABASE EVENT LOG LEVEL 0
SET DATABASE TRANSACTION CONTROL LOCKS
SET DATABASE DEFAULT ISOLATION LEVEL READ COMMITTED
SET DATABASE TRANSACTION ROLLBACK ON CONFLICT TRUE
SET DATABASE TEXT TABLE DEFAULTS ''
SET DATABASE SQL NAMES FALSE
SET DATABASE SQL REFERENCES FALSE
SET DATABASE SQL SIZE TRUE
SET DATABASE SQL TYPES FALSE
SET DATABASE SQL TDC DELETE TRUE
SET DATABASE SQL TDC UPDATE TRUE
SET DATABASE SQL CONCAT NULLS TRUE
SET DATABASE SQL UNIQUE NULLS TRUE
SET DATABASE SQL CONVERT TRUNCATE TRUE
SET DATABASE SQL AVG SCALE 0
SET DATABASE SQL DOUBLE NAN TRUE
SET FILES WRITE DELAY 500 MILLIS
SET FILES BACKUP INCREMENT TRUE
SET FILES CACHE SIZE 10000
SET FILES CACHE ROWS 50000
SET FILES SCALE 32
SET FILES LOB SCALE 32
SET FILES DEFRAG 0
SET FILES NIO TRUE
SET FILES NIO SIZE 256
SET FILES LOG TRUE
SET FILES LOG SIZE 50
//CREATE USER SA PASSWORD DIGEST 'd41d8cd98f00b204e9800998ecf8427e'
ALTER USER SA SET LOCAL TRUE
//CREATE SCHEMA PUBLIC AUTHORIZATION DBA
SET SCHEMA PUBLIC*/
CREATE MEMORY TABLE PUBLIC.CART_EVENT(ID BIGINT NOT NULL PRIMARY KEY,CART_EVENT_TYPE INTEGER,CREATED_AT TIMESTAMP,CUSTOMER_ID BIGINT,LAST_MODIFIED TIMESTAMP,PRODUCT_ID BIGINT,QTY INTEGER)
CREATE INDEX IDX_CART_EVENT_CUSTOMER ON PUBLIC.CART_EVENT(ID,CUSTOMER_ID)
CREATE MEMORY TABLE PUBLIC.CUSTOMER(ID BIGINT NOT NULL PRIMARY KEY,LOCATION_ADDRESS VARCHAR(255),LOCATION_CITY VARCHAR(255),LOCATION_LATITUDE DOUBLE,LOCATION_LONGITUDE DOUBLE,LOCATION_POSTAL_CODE VARCHAR(255),PAYMENT_METHOD VARCHAR(255),PROFILE_IMAGE_ID VARCHAR(255),STRIPE_ID VARCHAR(255),USER_ID BIGINT)
CREATE MEMORY TABLE PUBLIC.DELIVERY(ID BIGINT NOT NULL PRIMARY KEY,CREATED_AT TIMESTAMP,DRIVER_HOLD BIGINT,DRIVER_ID BIGINT,FEE DOUBLE,ORDER_ID BIGINT,QUOTE_ID BIGINT,STATUS VARCHAR(255),TRACKING_ID BINARY(255))
CREATE MEMORY TABLE PUBLIC.DELIVERY_DRIVER_BLACKLIST(DELIVERY_ID BIGINT NOT NULL,DRIVER_BLACKLIST BIGINT,CONSTRAINT FKL02MOV28I9MWHYQ0GIC01MGEB FOREIGN KEY(DELIVERY_ID) REFERENCES PUBLIC.DELIVERY(ID))
CREATE MEMORY TABLE PUBLIC.DELIVERY_TRACKING(ID BINARY(255) NOT NULL PRIMARY KEY,DELIVERY_ID BIGINT)
CREATE MEMORY TABLE PUBLIC.DRIVER(ID BIGINT NOT NULL PRIMARY KEY,CAR_COLOUR VARCHAR(255),CAR_MAKE VARCHAR(255),CAR_MODEL VARCHAR(255),CAR_PLATE VARCHAR(255),CAR_YEAR VARCHAR(255),PROFILE_IMAGE_ID VARCHAR(255),STRIPE_ID VARCHAR(255),USER_ID BIGINT)
CREATE MEMORY TABLE PUBLIC.LCBO_ORDER(ID BIGINT NOT NULL PRIMARY KEY,CREATED_AT TIMESTAMP,CUSTOMER_ID BIGINT,STATUS INTEGER,TOTAL DOUBLE)
CREATE MEMORY TABLE PUBLIC.LINE_ITEM(ID BIGINT NOT NULL PRIMARY KEY,PRODUCT_ID BIGINT,QTY INTEGER NOT NULL,ORDER_ID BIGINT,CONSTRAINT FK8HN671JMJSND77LB6AWVK3LSV FOREIGN KEY(ORDER_ID) REFERENCES PUBLIC.LCBO_ORDER(ID))
CREATE MEMORY TABLE PUBLIC.PLACE(ID BIGINT NOT NULL PRIMARY KEY,CUSTOMER_ID BIGINT,LOCATION_ADDRESS VARCHAR(255),LOCATION_CITY VARCHAR(255),LOCATION_LATITUDE DOUBLE,LOCATION_LONGITUDE DOUBLE,LOCATION_POSTAL_CODE VARCHAR(255),NAME VARCHAR(255))
CREATE MEMORY TABLE PUBLIC.PRODUCT(ID BIGINT NOT NULL PRIMARY KEY,ALCOHOL_CONTENT BIGINT,DESCRIPTION VARCHAR(2000),IMAGE_THUMB_URL VARCHAR(500),IMAGE_URL VARCHAR(500),IS_DEAD BOOLEAN,IS_DISCONTINUED BOOLEAN,IS_KOSHER BOOLEAN,IS_OCB BOOLEAN,IS_SEASONAL BOOLEAN,IS_VQA BOOLEAN,NAME VARCHAR(255),ORIGIN VARCHAR(255),PACKAGE VARCHAR(255),PACKAGE_UNIT_TYPE VARCHAR(255),PACKAGE_UNIT_VOLUME_IN_MILLILITERS BIGINT,PRICE_IN_CENTS BIGINT,PRIMARY_CATEGORY VARCHAR(255),PRODUCER_NAME VARCHAR(255),RELEASED_ON VARCHAR(255),SECONDARY_CATEGORY VARCHAR(255),SERVING_SUGGESTION VARCHAR(2000),STOCK_TYPE VARCHAR(255),STYLE VARCHAR(255),TAGS VARCHAR(500),TASTING_NOTE VARCHAR(2000),TERTIARY_CATEGORY VARCHAR(255),TOTAL_PACKAGE_UNITS BIGINT,UPDATED_AT VARCHAR(255),VARIETAL VARCHAR(255),VOLUME_IN_MILLILITERS BIGINT)
CREATE MEMORY TABLE PUBLIC.QUOTE(ID BIGINT NOT NULL PRIMARY KEY,CUSTOMER_ID BIGINT,DROPOFF_CUSTOMER_NAME VARCHAR(255),DROPOFF_DROPOFFETA INTEGER,DROPOFF_LOCATION_ADDRESS VARCHAR(255),DROPOFF_LOCATION_CITY VARCHAR(255),DROPOFF_LOCATION_LATITUDE DOUBLE,DROPOFF_LOCATION_LONGITUDE DOUBLE,DROPOFF_LOCATION_POSTAL_CODE VARCHAR(255),DROPOFF_ETA DOUBLE,ESTIMATED_AT TIMESTAMP,FEE DOUBLE NOT NULL,PICKUP_PICKUPETA INTEGER,PICKUP_STORE_ID BIGINT,PICKUP_STORE_LOCATION_ADDRESS VARCHAR(255),PICKUP_STORE_LOCATION_CITY VARCHAR(255),PICKUP_STORE_LOCATION_LATITUDE DOUBLE,PICKUP_STORE_LOCATION_LONGITUDE DOUBLE,PICKUP_STORE_LOCATION_POSTAL_CODE VARCHAR(255))
CREATE MEMORY TABLE PUBLIC.STORE(ID BIGINT NOT NULL PRIMARY KEY,ADDRESS VARCHAR(255),CITY VARCHAR(255),LATITUDE DECIMAL(15,13),LOCATION_ADDRESS VARCHAR(255),LOCATION_CITY VARCHAR(255),LOCATION_LATITUDE DOUBLE,LOCATION_LONGITUDE DOUBLE,LOCATION_POSTAL_CODE VARCHAR(255),LONGITUDE DECIMAL(16,13),POSTAL_CODE VARCHAR(255))
CREATE MEMORY TABLE PUBLIC.TRACKING_EVENT(ID BIGINT NOT NULL PRIMARY KEY,CREATED_AT TIMESTAMP,LOCATION_ADDRESS VARCHAR(255),LOCATION_CITY VARCHAR(255),LOCATION_LATITUDE DOUBLE,LOCATION_LONGITUDE DOUBLE,LOCATION_POSTAL_CODE VARCHAR(255),TRACKING_EVENT_TYPE VARCHAR(255),TRACKING_ID BINARY(255) NOT NULL,CONSTRAINT FKMH2YCUTGNN49KWJFHN30NSWFI FOREIGN KEY(TRACKING_ID) REFERENCES PUBLIC.DELIVERY_TRACKING(ID))
CREATE MEMORY TABLE PUBLIC.USER(ID BIGINT NOT NULL PRIMARY KEY,NON_EXPIRED BOOLEAN,NON_LOCKED BOOLEAN,EMAIL VARCHAR(255),ENABLED BOOLEAN NOT NULL,FIRST_NAME VARCHAR(255),LAST_NAME VARCHAR(255),PASSWORD VARCHAR(60),PHONE VARCHAR(255),ROLES VARCHAR(50))
ALTER SEQUENCE SYSTEM_LOBS.LOB_ID RESTART WITH 1
SET DATABASE DEFAULT INITIAL SCHEMA PUBLIC
GRANT USAGE ON DOMAIN INFORMATION_SCHEMA.SQL_IDENTIFIER TO PUBLIC
GRANT USAGE ON DOMAIN INFORMATION_SCHEMA.YES_OR_NO TO PUBLIC
GRANT USAGE ON DOMAIN INFORMATION_SCHEMA.TIME_STAMP TO PUBLIC
GRANT USAGE ON DOMAIN INFORMATION_SCHEMA.CARDINAL_NUMBER TO PUBLIC
GRANT USAGE ON DOMAIN INFORMATION_SCHEMA.CHARACTER_DATA TO PUBLIC
GRANT DBA TO SA
SET SCHEMA SYSTEM_LOBS
INSERT INTO BLOCKS VALUES(0,2147483647,0)
SET SCHEMA PUBLIC