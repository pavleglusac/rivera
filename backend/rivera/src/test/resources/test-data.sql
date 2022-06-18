INSERT INTO pricelist (cancellation_terms, end_date_time, price_per_day, price_per_hour, start_date_time, rentable_id) VALUES ('20', '9999-12-31 00:00:00', 40, 10, '2022-05-21 17:44:46.892548', 1);
INSERT INTO pricelist (cancellation_terms, end_date_time, price_per_day, price_per_hour, start_date_time, rentable_id) VALUES ('20', '9999-12-31 00:00:00', 50, 15, '2022-05-21 17:44:46.970543', 2);
INSERT INTO pricelist (cancellation_terms, end_date_time, price_per_day, price_per_hour, start_date_time, rentable_id) VALUES ('20', '9999-12-31 00:00:00', 40, 10, '2022-05-21 17:44:47.919979', 3);
INSERT INTO pricelist (cancellation_terms, end_date_time, price_per_day, price_per_hour, start_date_time, rentable_id) VALUES ('20', '9999-12-31 00:00:00', 40, 10, '2022-05-21 17:44:47.99524', 4);


INSERT INTO tag (name) VALUES ('wifi');
INSERT INTO tag (name) VALUES ('river');
INSERT INTO tag (name) VALUES ('canoe');
INSERT INTO tag (name) VALUES ('extreme');

INSERT INTO member_category (color, for_owner, name, number_of_points, percentage) VALUES ('#293462', false, 'Basic', 0, 5);
INSERT INTO member_category (color, for_owner, name, number_of_points, percentage) VALUES ('#F24C4C', false, 'Green', 30, 10);
INSERT INTO member_category (color, for_owner, name, number_of_points, percentage) VALUES ('#EC9B3B', false, 'Pro', 60, 15);
INSERT INTO member_category (color, for_owner, name, number_of_points, percentage) VALUES ('#F7D716', false, 'Gold', 90, 20);


INSERT INTO member_category (color, for_owner, name, number_of_points, percentage) VALUES ('#293462', true, 'Basic', 0, 5);
INSERT INTO member_category (color, for_owner, name, number_of_points, percentage) VALUES ('#F24C4C', true, 'Green', 30, 10);
INSERT INTO member_category (color, for_owner, name, number_of_points, percentage) VALUES ('#EC9B3B', true, 'Pro', 60, 15);
INSERT INTO member_category (color, for_owner, name, number_of_points, percentage) VALUES ('#F7D716', true, 'Gold', 90, 20);

INSERT INTO rules (income_percentage, points_per_reservation) VALUES  (50, 5);

DROP SEQUENCE IF EXISTS mysequence;
CREATE SEQUENCE mysequence
    INCREMENT 1
    START 1;
INSERT INTO adventure (id, address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username, capacity, deleted, version) VALUES (nextval('mysequence'), '7 Grove St.', 3.3, 'Cairns', 'Australia', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Mountain Fishing', NULL, 1, 'marko', 4, false, 0);
INSERT INTO adventure (id, address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username, capacity, deleted, version) VALUES (nextval('mysequence'), '7 Grove St.', 2.3, 'Cairns', 'New Zeland', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Some adventure', NULL, 2, 'marko', 3, false, 0);
INSERT INTO adventure (id, address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username, capacity, deleted, version) VALUES (nextval('mysequence'), '1234 Main St.', 3.3, 'Zrenjanin', 'Serbia', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Very Cool Fishing', NULL, 3, 'marko', 5, false, 0);


INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (3, 'Rod 123');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (1, 'Silk 4');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (1, 'Golden Bait');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (1, 'Rod 123');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (2, 'Rod 123');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (3, 'Silk 4');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (2, 'Golden Bait');



INSERT INTO client (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, number_of_penalties, number_of_points, status, category_id) VALUES ('pera', 'Dobrovoljacka 35', 'Zrenjanin', 'Serbia', false, 'mrsisatim20+pera@outlook.com', NULL, 'Petar', '$2a$10$tonj9z9.COdaLJMbDYWoceKtivPCVmkSLSLg8LlDJGd7QRTlgh8Be', '+3815565456', '/images/clients/pera.jpg', 'Petrovic', 0, 0, 0, 1);
INSERT INTO client (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, number_of_penalties, number_of_points, status, category_id) VALUES ('mika', 'Dobrovoljacka 37', 'Zrenjanin', 'Serbia', false, 'mrsisatim20+mika@outlook.com', NULL, 'Mika', '$2a$10$yzjAFIt2hcdHnl85Kky.ue1/2jRcs9Gi8mkJCym.ZoQAw0p5xJ3UO', '+3815565456', '/images/clients/pera.jpg', 'Mikic', 0, 0, 0, 1);

INSERT INTO admin (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, status) VALUES ('admin', 'Cara Dusana 42', 'Zrenjanin', 'Serbia', false, 'mrsisatim20+admin@outlook.com', NULL, 'Branislav', '$2a$10$yzjAFIt2hcdHnl85Kky.ue1/2jRcs9Gi8mkJCym.ZoQAw0p5xJ3UO', '+3815565456', '/images/clients/pera.jpg', 'Bakic', 0);

INSERT INTO cottage (id,address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username, deleted, version) VALUES (nextval('mysequence'), '1234 Main St.', 3.3, 'Zrenjanin', 'Serbia', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Very Cool Cottage', NULL, 4, 'cowner', false,0);

INSERT INTO cottage (id,address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username, deleted, version) VALUES (nextval('mysequence'), '1234 Main St.', 3.3, 'Zrenjanin', 'Serbia', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Very Cool Cottage2', NULL, 4, 'cowner', false,0);

INSERT INTO boat (id,address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username, capacity, engine_power, engines_number, length, max_speed, type, deleted, version) VALUES (nextval('mysequence'), '1234 Main St.', 3.1, 'Zrenjanin', 'Serbia', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Very Cool Boat', NULL, 4, 'bowner',5,50,3,300,60,0, false, 0);

INSERT INTO boat (id,address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username, capacity, engine_power, engines_number, length, max_speed, type, deleted, version) VALUES (nextval('mysequence'), '1234 Main St.', 3.3, 'Zrenjanin', 'Serbia', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Very Cool Boat 2', NULL, 4, 'bowner',5,30,3,50,70,2, false, 0);

INSERT INTO boat (id,address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username, capacity, engine_power, engines_number, length, max_speed, type, deleted, version) VALUES (nextval('mysequence'), '1234 Main St.', 3.5, 'Zrenjanin', 'Serbia', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Very Cool Boat 3', NULL, 4, 'bowner',5,10,2,100,50,2, false, 0);

INSERT INTO cottage_owner (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, sign_up_description, status, number_of_points, category_id) VALUES ('marko2', 'Njegoseva 35', 'Zrenjanin', 'Serbia', false, 'mrsisatim20+marko2@outlook.com', NULL, 'Marko', 'sifra', '+3845135535', '/images/clients/marko2.jpg', 'Markovic', NULL, 0, 0, 5);
INSERT INTO cottage_owner (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, sign_up_description, status, number_of_points, category_id) VALUES ('cowner', 'adresa', 'Zrenjanin', 'Serbia', false, 'mrsisatim20+marko@outlook.com', NULL, 'Darko', '$2a$10$B4S75eElnprgm7w0xP.qy.AvCNOYws0bFuEL31aP7KbbXsVWoze8O', '+3845135535', '/images/clients/cowner.jpg', 'Markovic', NULL, 0, 0, 5);
INSERT INTO boat_owner (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, sign_up_description, status, number_of_points, category_id) VALUES ('bowner', 'adresa', 'Zrenjanin', 'Serbia', false, 'mrsisatim20+bowner@outlook.com', NULL, 'Zarko', '$2a$10$B4S75eElnprgm7w0xP.qy.AvCNOYws0bFuEL31aP7KbbXsVWoze8O', '+3845135535', '/images/clients/cowner.jpg', 'Markovic', NULL, 0, 0, 5);

INSERT INTO cottage_rooms(cottage_id,rooms,rooms_key) values (4,1,1);
INSERT INTO cottage_rooms(cottage_id,rooms,rooms_key) values (4,3,2);
INSERT INTO cottage_rooms(cottage_id,rooms,rooms_key) values (5,4,1);
INSERT INTO cottage_rooms(cottage_id,rooms,rooms_key) values (5,5,2);


INSERT INTO discount (capacity, end_date_time, price, start_date_time, rentable_id) VALUES (3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 1);
INSERT INTO discount (capacity, end_date_time, price, start_date_time, rentable_id) VALUES (4, '2022-08-18 17:00:00', 450, '2022-08-18 16:00:00', 1);
INSERT INTO discount (capacity, end_date_time, price, start_date_time, rentable_id) VALUES (3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 1);
INSERT INTO discount (capacity, end_date_time, price, start_date_time, rentable_id) VALUES (3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 1);
INSERT INTO discount (capacity, end_date_time, price, start_date_time, rentable_id) VALUES (3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 1);
INSERT INTO discount (capacity, end_date_time, price, start_date_time, rentable_id) VALUES (3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 3);
INSERT INTO discount (capacity, end_date_time, price, start_date_time, rentable_id) VALUES (3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 4);


INSERT INTO discount (capacity, end_date_time, price, start_date_time, rentable_id) VALUES (3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 6);
INSERT INTO discount (capacity, end_date_time, price, start_date_time, rentable_id) VALUES (5, '2022-09-13 01:00:00', 500, '2022-09-12 16:00:00', 6);
INSERT INTO discount (capacity, end_date_time, price, start_date_time, rentable_id) VALUES (6, '2022-08-13 01:00:00', 700, '2022-08-12 16:00:00', 6);




INSERT INTO discount_tags (discount_id, tags_id) VALUES (1, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (1, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (2, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (3, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (2, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (3, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (4, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (4, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (5, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (5, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (6, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (6, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (7, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (7, 4);


INSERT INTO discount_tags (discount_id, tags_id) VALUES (8, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (9, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (9, 2);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (10, 3);



INSERT INTO fishing_instructor (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, sign_up_description, status, biography, number_of_points, category_id) VALUES ('marko', 'Njegoseva 35', 'Zrenjanin', 'Serbia', false, 'mrsisatim20+marko@outlook.com', NULL, 'Mitar', '$2a$10$dmCJKPVedSm8ZPAGGgcyXubDfVXEghPgnbfGX0wZlPrDTO5iL4h4C', '+3845135535', '/images/clients/marko.jpg', 'Markovic', NULL, 0, 'Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam tellus ex non nislx diam, sed euismod augue dignissim ut. Aenean non rhoncus ante.', 0, 5);



INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (1, 'massage');
INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (2, 'therapy');
INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (3, 'basketball');
INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (4, 'massage');
INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (5, 'therapy');
INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (6, 'basketball');
INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (7, 'massage');
INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (8, 'therapy');
INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (8, 'basketball');

INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (1, '/images/adventures/1/img1.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (1, '/images/adventures/1/img2.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (1, '/images/adventures/1/img3.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (1, '/images/adventures/1/img4.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (1, '/images/adventures/1/img5.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (2, '/images/adventures/1/img3.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (2, '/images/adventures/1/img2.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (2, '/images/adventures/1/img1.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (2, '/images/adventures/1/img4.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (2, '/images/adventures/1/img5.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (3, '/images/adventures/1/img5.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (3, '/images/adventures/1/img4.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (3, '/images/adventures/1/img3.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (3, '/images/adventures/1/img2.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (3, '/images/adventures/1/img1.jpg');

INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (4, '/images/cottages/5/img5-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (4, '/images/cottages/5/img4-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (4, '/images/cottages/5/img3-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (4, '/images/cottages/5/img2-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (4, '/images/cottages/5/img1-min.jpg');

INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (5, '/images/cottages/5/img2-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (5, '/images/cottages/5/img4-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (5, '/images/cottages/5/img3-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (5, '/images/cottages/5/img5-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (5, '/images/cottages/5/img1-min.jpg');


INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (6, '/images/boats/1/img5-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (6, '/images/boats/1/img4-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (6, '/images/boats/1/img3-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (6, '/images/boats/1/img2-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (6, '/images/boats/1/img1-min.jpg');

INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (7, '/images/boats/1/img4-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (7, '/images/boats/1/img1-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (7, '/images/boats/1/img3-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (7, '/images/boats/1/img2-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (7, '/images/boats/1/img1-min.jpg');

INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (8, '/images/boats/1/img2-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (8, '/images/boats/1/img4-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (8, '/images/boats/1/img3-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (8, '/images/boats/1/img5-min.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (8, '/images/boats/1/img1-min.jpg');


INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (1, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (1, 'no destruction of property');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (2, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (2, 'no destruction of property');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (3, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (3, 'no destruction of property');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (4, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (4, 'no destruction of property');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (5, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (5, 'no destruction of property');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (6, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (6, 'no destruction of property');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (7, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (7, 'no destruction of property');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (8, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (8, 'no destruction of property');



INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (1, 1);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (1, 4);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (1, 2);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (2, 4);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (2, 2);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (3, 1);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (3, 2);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (4, 1);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (4, 2);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (5, 2);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (6, 2);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (7, 2);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (8, 2);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (5, 4);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (6, 1);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (7, 1);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (8, 4);


INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2022-05-21 19:44:47.521598', 130, '2022-05-21 18:44:47.521546', 'pera', 1, 0.5);
INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2022-05-21 19:44:47.521598', 130, '2022-05-21 18:44:47.521546', 'pera', 1, 0.5);
INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2022-06-21 19:44:47.521598', 130, '2022-06-21 18:44:47.521546', 'pera', 1, 0.5);
INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2023-05-21 19:44:47.521598', 1300, '2023-05-21 18:44:47.521546', 'pera', 4, 0.5);
INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2022-05-21 19:44:48.250883', 130, '2022-05-21 18:44:48.250856', 'mika', 4, 0.5);
INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2022-06-21 19:44:48.250883', 130, '2022-06-21 18:44:48.250856', 'mika', 4, 0.5);
INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2022-05-21 19:44:48.250883', 130, '2022-05-21 18:44:48.250856', 'mika', 5, 0.5);
INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2022-09-21 19:44:48.250883', 130, '2022-09-21 18:44:48.250856', 'mika', 4, 0.5);

INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2022-09-21 19:44:48.250883', 200, '2022-09-21 18:44:48.250856', 'mika', 6, 0.5);
INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2022-09-26 19:44:48.250883', 190, '2022-09-26 18:44:48.250856', 'mika', 7, 0.5);
INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2022-09-28 19:44:48.250883', 13, '2022-09-28 18:44:48.250856', 'mika', 8, 0.5);
INSERT INTO reservation (cancelled, end_date_time, price, start_date_time, client_id, rentable_id, owner_income_percentage) VALUES (false, '2021-10-21 19:44:48.250883', 130, '2021-09-21 18:44:48.250856', 'mika', 6, 0.5);



INSERT INTO review (posted, score, status, text, client_id, rentable_id, owner_id, type, version)
VALUES ('2022-05-21 17:44:47.41532', 3.3, 1,
        'Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi ',
        'pera', 1, null, 0, 0);
INSERT INTO review (posted, score, status, text, client_id, rentable_id, owner_id, type, version)
VALUES ('2022-05-21 17:44:47.483195', 3.4, 1,
        'Ut mollis erat non quam rhoncus sodales. Nulla facilisi. Duis sit amet pellentesque dolor. Aliquam mollis rhoncus risus vel ornare. Nulla pellentesque risus ac lorem efficitur auctor. Cras auctor turpis',
        'mika', 1, null, 0, 0);
INSERT INTO review (posted, score, status, text, client_id, rentable_id, owner_id, type, version) VALUES ('2022-05-21 17:44:47.948207', 2.0, 1, 'Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi ', 'pera', 3, null, 1, 0);
INSERT INTO review (posted, score, status, text, client_id, rentable_id, owner_id, type, version) VALUES ('2022-05-21 17:44:48.026171', 1.3, 1, 'Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi ', 'pera', 4, null, 1, 0);


INSERT INTO role (name) VALUES ('ROLE_COTTAGE_OWNER');
INSERT INTO role (name) VALUES ('ROLE_BOAT_OWNER');
INSERT INTO role (name) VALUES ('ROLE_FISHING_INSTRUCTOR');
INSERT INTO role (name) VALUES ('ROLE_CLIENT');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');



INSERT INTO user_role (user_id, role_id) VALUES ('pera', 4);
INSERT INTO user_role (user_id, role_id) VALUES ('mika', 4);
INSERT INTO user_role (user_id, role_id) VALUES ('marko', 3);
INSERT INTO user_role (user_id, role_id) VALUES ('cowner', 1);
INSERT INTO user_role (user_id, role_id) VALUES ('admin', 5);
INSERT INTO user_role (user_id, role_id) VALUES ('bowner', 2);

INSERT INTO reservation_report (reservation_report_type, resolved, sanction, showed_up, text, reservation_id, version)
VALUES (0, false, true, false, 'Client didnt show up', 1, 0);

INSERT INTO reservation_report (reservation_report_type, resolved, sanction, showed_up, text, reservation_id, version)
VALUES (1, false, true, false, 'Client didnt show up', 2, 0);

INSERT INTO reservation_report (reservation_report_type, resolved, sanction, showed_up, text, reservation_id, version)
VALUES (2, false, false, false, 'Client did show up', 3, 0);

INSERT INTO termination_request (id, description, status, person_id, version) values (1, 'Opis', 2, 'pera', 0);