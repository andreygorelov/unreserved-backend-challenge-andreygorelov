-- DO NOT CHANGE THIS FILE

CREATE TABLE IF NOT EXISTS listing
(
    id            CHAR(36)       NOT NULL PRIMARY KEY,
    status        VARCHAR(50)    NOT NULL,
    reserve_price DECIMAL(12, 2) NULL DEFAULT NULL,
    address1      VARCHAR(256)   NULL DEFAULT NULL,
    city          VARCHAR(256)   NULL DEFAULT NULL,
    region_code   VARCHAR(2)     NULL DEFAULT NULL,
    country       VARCHAR(256)   NULL DEFAULT NULL,
    bed_number    INT(11)        NULL DEFAULT NULL,
    bath_number   DECIMAL(3, 1)  NULL DEFAULT NULL,
    property_sqft INT(11)        NULL DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS building
(
    id            CHAR(36)     NOT NULL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    floors_number INT(11)      NULL DEFAULT NULL
);

-- Bootstrap buildings
INSERT INTO building (id, name, floors_number)
VALUES ('375e16e5-d5c2-11eb-ad41-0abe8932f98d', 'Neptune 1', 39),
       ('000008f5-3374-4313-92fd-6a11d0068f2b', 'Neptune 2', 37),
       ('00003302-aa06-465b-889d-5ba853d983ea', 'Atlantis', 29);
