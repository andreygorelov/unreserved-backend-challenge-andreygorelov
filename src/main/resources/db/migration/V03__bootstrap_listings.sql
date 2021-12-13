-- ADD YOUR MIGRATION IMPLEMENTATION

INSERT INTO listing (id, status, reserve_price, address1, city, region_code, country, property_sqft, bed_number, bath_number, condo_fees, listing_type, lot_sqft, building_id)
VALUES ('375e16e5-d5c2-11eb-ad41-0abe8932f955', 'UPCOMING', 1000000, '3701-215 Fort York Blvd', 'Toronto', 'ON', 'Canada', 900, 2, 2, 1000, 'CONDO', null, '000008f5-3374-4313-92fd-6a11d0068f2b'),
       ('375e16e5-d5c2-11eb-ad41-0abe8932f912', 'UPCOMING', 1380000, '3703-215 Fort York Blvd', 'Toronto', 'ON', 'Canada', 1200, 3, 3, 1100, 'CONDO', null, '000008f5-3374-4313-92fd-6a11d0068f2b'),
       ('375e16e5-d5c2-11eb-ad41-0abe8932f956', 'UPCOMING', 680000, '2803-231 Fort York Blvd', 'Toronto', 'ON', 'Canada', 600, 1, 1, 700, 'CONDO', null, '00003302-aa06-465b-889d-5ba853d983ea'),

       ('375e16e5-d5c2-11eb-ad41-0abe8932f232', 'UPCOMING', 1200000, '40 Upminster Way', 'Ottawa', 'ON', 'Canada', 2500, 2, 2.5, null, 'HOUSE', 4000, null),
       ('375e16e5-d5c2-11eb-ad41-0abe8932f598', 'UPCOMING', 600000, '44 Upminster Way', 'Ottawa', 'ON', 'Canada', 3500, 3, 3, null, 'HOUSE', 5000, null);


