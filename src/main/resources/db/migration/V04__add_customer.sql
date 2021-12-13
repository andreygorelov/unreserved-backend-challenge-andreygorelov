-- ADD YOUR MIGRATION IMPLEMENTATION

CREATE TABLE IF NOT EXISTS customer
(
    id            CHAR(36)       NOT NULL PRIMARY KEY,
    created_at    TIMESTAMP      NOT NULL,
    email         VARCHAR(255)   NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS listing_customer (
    PRIMARY KEY (listing_id, customer_id),
    listing_id VARCHAR(255) NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (listing_id, customer_id),
    CONSTRAINT FK_Listing_has_Customer FOREIGN KEY (listing_id) REFERENCES listing (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_Customer_has_Listing FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE NO ACTION);