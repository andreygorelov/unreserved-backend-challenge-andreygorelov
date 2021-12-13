-- ADD YOUR MIGRATION IMPLEMENTATION

INSERT INTO customer(id, created_at, email)
VALUES ('175e16e5-d5c2-11eb-ad41-0abe8932f561', '2021-12-13T17:09:42.411', 'testuser1@domain.net'),
       ('275e16e5-d5c2-11eb-ad41-0abe8932f562', '2021-11-13T17:09:42.411', 'testuser2@domain.net'),
       ('675e16e5-d5c2-11eb-ad41-0abe8932f553', '2021-10-13T17:09:42.411', 'testuser7@domain.net'),
       ('455e16e5-d5c2-11eb-ad41-0abe8932f545', '2021-09-13T17:09:42.411', 'testuser8@domain.net'),
       ('375e16e5-d5c2-11eb-ad41-0abe8932f563', '2021-12-14T17:10:42.411', 'testuser3@domain.net');

INSERT INTO listing_customer(customer_id, listing_id)
VALUES ('175e16e5-d5c2-11eb-ad41-0abe8932f561', '375e16e5-d5c2-11eb-ad41-0abe8932f232'),
       ('175e16e5-d5c2-11eb-ad41-0abe8932f561', '375e16e5-d5c2-11eb-ad41-0abe8932f912'),
       ('275e16e5-d5c2-11eb-ad41-0abe8932f562', '375e16e5-d5c2-11eb-ad41-0abe8932f956'),
       ('455e16e5-d5c2-11eb-ad41-0abe8932f545', '375e16e5-d5c2-11eb-ad41-0abe8932f232'),
       ('375e16e5-d5c2-11eb-ad41-0abe8932f563', '375e16e5-d5c2-11eb-ad41-0abe8932f912');

