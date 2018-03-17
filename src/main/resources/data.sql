/*
 * Phil, Dan, Nadine, and John passwords are LIT2017
*/

insert into USER (id, first_name, last_name, email, phone, password, roles, enabled, non_expired, non_locked) values (1, 'Chris', 'Peckover', 'cjpeckover@hotmail.ca', '5195739232', '$2a$06$tRC3rmZvTdfpb0/Mqcp0FuR/v16iO1h1PUuDn8JjJ0rIaQxfRZI.i', 'CUSTOMER,DRIVER,ADMIN', TRUE, TRUE, TRUE);
insert into USER (id, first_name, last_name, email, phone, password, roles, enabled, non_expired, non_locked) values (2, 'Phil', 'Pedersen', 'phil@pedersen.com', '5195739232', '$2a$06$8k96vMdJX/nZQa.THDEPnerp0V6gyI4h1V2wXHWZ/998dvwYPX1AK', 'CUSTOMER,DRIVER', TRUE, TRUE, TRUE);
insert into USER (id, first_name, last_name, email, phone, password, roles, enabled, non_expired, non_locked) values (3, 'Daniel', 'Dennis', 'daniel@dennis.com', '5195739232', '$2a$06$8k96vMdJX/nZQa.THDEPnerp0V6gyI4h1V2wXHWZ/998dvwYPX1AK', 'CUSTOMER,DRIVER', TRUE, TRUE, TRUE);
insert into USER (id, first_name, last_name, email, phone, password, roles, enabled, non_expired, non_locked) values (4, 'Nadine', 'Midany', 'nadine@midany.com', '5195739232', '$2a$06$8k96vMdJX/nZQa.THDEPnerp0V6gyI4h1V2wXHWZ/998dvwYPX1AK', 'CUSTOMER,DRIVER', TRUE, TRUE, TRUE);
insert into USER (id, first_name, last_name, email, phone, password, roles, enabled, non_expired, non_locked) values (5, 'John', 'Smith', 'john@smith.com', '5195739232', '$2a$06$8k96vMdJX/nZQa.THDEPnerp0V6gyI4h1V2wXHWZ/998dvwYPX1AK', 'CUSTOMER,DRIVER', TRUE, TRUE, TRUE);

/*ADMIN ACCOUNT*/
insert into USER (id, first_name, last_name, email, password, roles, enabled, non_expired, non_locked) values (6, 'Chris', 'Peckover', 'admin', '$2a$06$8kFP4x.f.mwxZ7UOplIy4ueeY0m71k8bkvxzYweOZVXCn4z8f94ei', 'ADMIN,ACTUATOR', TRUE, TRUE, TRUE);




insert into CUSTOMER (id, user_id, stripe_id, location_address, location_city, location_postal_code, location_latitude, location_longitude, payment_method, profile_image_id) values (1, 1, 'cus_C3khD5mPYki4Hs', '23 Crestwood Dr.', 'Cambridge', 'N1S3N8', 43.3440436, -80.3302874, 'VISA', 'GGLM4P8');
insert into CUSTOMER (id, user_id, stripe_id, location_address, location_city, location_postal_code, location_latitude, location_longitude, payment_method, profile_image_id) values (2, 2, 'cus_C3kh05KMgcu0bX', '23 Crestwood Dr.', 'Cambridge', 'N1S3N8', 43.3440436, -80.3302874, 'AE', 't3nS4U3');
insert into CUSTOMER (id, user_id, stripe_id, location_address, location_city, location_postal_code, location_latitude, location_longitude, payment_method, profile_image_id) values (3, 3, 'cus_C3kiGsw3J8dDeu', '23 Crestwood Dr.', 'Cambridge', 'N1S3N8', 43.3440436, -80.3302874, 'MASTERCARD', 'OmesfTC');
insert into CUSTOMER (id, user_id, stripe_id, location_address, location_city, location_postal_code, location_latitude, location_longitude, payment_method) values (4, 4, 'cus_C3kiZMp51iX3og', '23 Crestwood Dr.', 'Cambridge', 'N1S3N8', 43.3440436, -80.3302874, 'VISA');
insert into CUSTOMER (id, user_id, stripe_id, location_address, location_city, location_postal_code, location_latitude, location_longitude, payment_method) values (5, 5, 'cus_C3kiLg0du676H1', '23 Crestwood Dr.', 'Cambridge', 'N1S3N8', 43.3440436, -80.3302874, 'VISA');

insert into DRIVER (id, user_id, stripe_id, car_make, car_model, car_year, car_colour, car_plate) values (1, 1, 'acct_1BgRGKJHPGq8a2JD', 'Kia', 'Rio', '2015', 'Blue', 'BXEC 717'); 
insert into DRIVER (id, user_id, stripe_id, car_make, car_model, car_year, car_colour, car_plate, profile_image_id) values (2, 2, 'acct_1BgRH9H5gQH7DkmE', 'Subaru', 'Forester', '2012', 'Grey', '7I77 135', 't3nS4U3');
insert into DRIVER (id, user_id, stripe_id, car_make, car_model, car_year, car_colour, car_plate, profile_image_id) values (3, 3, 'acct_1BgRHtGt4CvAsGQ2', 'Dodge', 'Grand Caravan', '2001', 'Tan', '7I77 135', 'OmesfTC');
insert into DRIVER (id, user_id, stripe_id, car_make, car_model, car_year, car_colour, car_plate) values (4, 4, 'acct_1BgRJJFpzQvyTNrz', 'Mini Cooper', 'S', '2017', 'Red', '7I77 135');
insert into DRIVER (id, user_id, stripe_id, car_make, car_model, car_year, car_colour, car_plate) values (5, 5, 'acct_1BgRKHLiACNw0t3s', 'Tesla', 'Model S', '2016', 'Black', '7I77 135');