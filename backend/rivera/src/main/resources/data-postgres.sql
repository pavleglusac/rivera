

INSERT INTO pricelist (id, cancellation_terms, end_date_time, price_per_day, price_per_hour, start_date_time, rentable_id) VALUES (1, '20', '9999-12-31 00:00:00', 40, 10, '2022-05-21 17:44:46.892548', 1);
INSERT INTO pricelist (id, cancellation_terms, end_date_time, price_per_day, price_per_hour, start_date_time, rentable_id) VALUES (2, '20', '9999-12-31 00:00:00', 50, 15, '2022-05-21 17:44:46.970543', 2);
INSERT INTO pricelist (id, cancellation_terms, end_date_time, price_per_day, price_per_hour, start_date_time, rentable_id) VALUES (3, '20', '9999-12-31 00:00:00', 40, 10, '2022-05-21 17:44:47.919979', 3);
INSERT INTO pricelist (id, cancellation_terms, end_date_time, price_per_day, price_per_hour, start_date_time, rentable_id) VALUES (4, '20', '9999-12-31 00:00:00', 40, 10, '2022-05-21 17:44:47.99524', 4);


INSERT INTO tag (id, name) VALUES (1, 'wifi');
INSERT INTO tag (id, name) VALUES (2, 'river');
INSERT INTO tag (id, name) VALUES (3, 'canoe');
INSERT INTO tag (id, name) VALUES (4, 'extreme');

INSERT INTO member_category (id, color, for_owner, name, number_of_points, percentage) VALUES (1, '#9b2437', false, 'Basic', 0, 5);
INSERT INTO member_category (id, color, for_owner, name, number_of_points, percentage) VALUES (2, '#35663D', false, 'Green', 30, 10);
INSERT INTO member_category (id, color, for_owner, name, number_of_points, percentage) VALUES (3, '#022741', false, 'Pro', 60, 15);
INSERT INTO member_category (id, color, for_owner, name, number_of_points, percentage) VALUES (4, '#F4A933', false, 'Gold', 90, 20);


INSERT INTO member_category (id, color, for_owner, name, number_of_points, percentage) VALUES (5, '#9b2437', true, 'Basic', 0, 5);
INSERT INTO member_category (id, color, for_owner, name, number_of_points, percentage) VALUES (6, '#35663D', true, 'Green', 30, 10);
INSERT INTO member_category (id, color, for_owner, name, number_of_points, percentage) VALUES (7, '#022741', true, 'Pro', 60, 15);
INSERT INTO member_category (id, color, for_owner, name, number_of_points, percentage) VALUES (8, '#F4A933', true, 'Gold', 90, 20);

INSERT INTO rules (id, income_percentage, points_per_reservation) VALUES  (1, 50, 5);


INSERT INTO adventure (id, address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username, capacity) VALUES (1, '7 Grove St.', 3.3, 'Cairns', 'Australia', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Mountain Fishing', NULL, 1, 'marko', 4);
INSERT INTO adventure (id, address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username, capacity) VALUES (2, '7 Grove St.', 2.3, 'Cairns', 'New Zeland', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Some adventure', NULL, 2, 'marko', 3);
INSERT INTO adventure (id, address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username, capacity) VALUES (3, '1234 Main St.', 3.3, 'Zrenjanin', 'Serbia', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Very Cool Fishing', NULL, 3, 'marko', 5);


INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (1, 'Rod 123');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (1, 'Silk 4');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (1, 'Golden Bait');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (2, 'Rod 123');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (3, 'Rod 123');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (3, 'Silk 4');
INSERT INTO adventure_fishing_equipment (adventure_id, fishing_equipment) VALUES (3, 'Golden Bait');



INSERT INTO client (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, number_of_penalties, number_of_points, status) VALUES ('pera', 'Dobrovoljacka 35', 'Zrenjanin', 'Serbia', false, 'pera@gmail.com', NULL, 'Petar', '$2a$10$tonj9z9.COdaLJMbDYWoceKtivPCVmkSLSLg8LlDJGd7QRTlgh8Be', '+3815565456', '/images/clients/pera.jpg', 'Petrovic', 0, 0, NULL);
INSERT INTO client (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, number_of_penalties, number_of_points, status) VALUES ('mika', 'Dobrovoljacka 37', 'Zrenjanin', 'Serbia', false, 'mika@gmail.com', NULL, 'Mika', '$2a$10$yzjAFIt2hcdHnl85Kky.ue1/2jRcs9Gi8mkJCym.ZoQAw0p5xJ3UO', '+3815565456', '/images/clients/pera.jpg', 'Mikic', 0, 0, NULL);

INSERT INTO admin (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname) VALUES ('admin', 'Cara Dusana 42', 'Zrenjanin', 'Serbia', false, 'admin@gmail.com', NULL, 'Branislav', '$2a$10$yzjAFIt2hcdHnl85Kky.ue1/2jRcs9Gi8mkJCym.ZoQAw0p5xJ3UO', '+3815565456', '/images/clients/pera.jpg', 'Bakic');

INSERT INTO cottage (id, address, average_score, city, country, description, name, profile_picture, current_pricelist_id, owner_username) VALUES (4, '1234 Main St.', 3.3, 'Zrenjanin', 'Serbia', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla vulputate pharetra nulla, ut eleifend risus. Praesent elementum maximus quam mollis consequat', 'Very Cool Cottage', NULL, 4, 'cowner');

INSERT INTO cottage_owner (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, sign_up_description, status, number_of_points) VALUES ('marko2', 'Njegoseva 35', 'Zrenjanin', 'Serbia', false, 'marko2@gmail.com', NULL, 'Marko', 'sifra', '+3845135535', '/images/clients/marko2.jpg', 'Markovic', NULL, 0, 0);
INSERT INTO cottage_owner (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, sign_up_description, status, number_of_points) VALUES ('cowner', 'adresa', 'Zrenjanin', 'Serbia', false, 'marko@gmail.com', NULL, 'Marko', '$2a$10$B4S75eElnprgm7w0xP.qy.AvCNOYws0bFuEL31aP7KbbXsVWoze8O', '+3845135535', '/images/clients/cowner.jpg', 'Markovic', NULL, 0, 0);




INSERT INTO discount (id, capacity, end_date_time, price, start_date_time, rentable_id) VALUES (1, 3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 1);
INSERT INTO discount (id, capacity, end_date_time, price, start_date_time, rentable_id) VALUES (2, 4, '2022-08-18 17:00:00', 450, '2022-08-18 16:00:00', 1);
INSERT INTO discount (id, capacity, end_date_time, price, start_date_time, rentable_id) VALUES (3, 3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 1);
INSERT INTO discount (id, capacity, end_date_time, price, start_date_time, rentable_id) VALUES (4, 3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 1);
INSERT INTO discount (id, capacity, end_date_time, price, start_date_time, rentable_id) VALUES (5, 3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 1);
INSERT INTO discount (id, capacity, end_date_time, price, start_date_time, rentable_id) VALUES (6, 3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 3);
INSERT INTO discount (id, capacity, end_date_time, price, start_date_time, rentable_id) VALUES (7, 3, '2022-08-13 01:00:00', 300, '2022-08-12 16:00:00', 4);




INSERT INTO discount_tags (discount_id, tags_id) VALUES (1, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (1, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (2, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (2, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (3, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (3, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (4, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (4, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (5, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (5, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (6, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (6, 4);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (7, 3);
INSERT INTO discount_tags (discount_id, tags_id) VALUES (7, 4);



INSERT INTO fishing_instructor (username, address, city, country, deleted, email, last_password_reset_date, name, password, phone_number, photo, surname, sign_up_description, status, biography, number_of_points) VALUES ('marko', 'Njegoseva 35', 'Zrenjanin', 'Serbia', false, 'marko@gmail.com', NULL, 'Marko', '$2a$10$dmCJKPVedSm8ZPAGGgcyXubDfVXEghPgnbfGX0wZlPrDTO5iL4h4C', '+3845135535', '/images/clients/marko.jpg', 'Markovic', NULL, 0, 'Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam tellus ex non nislx diam, sed euismod augue dignissim ut. Aenean non rhoncus ante.', 0);



INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (1, 'massage');
INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (1, 'therapy');
INSERT INTO rentable_additional_services (rentable_id, additional_services) VALUES (1, 'basketball');

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
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (4, '/images/adventures/1/img5.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (4, '/images/adventures/1/img4.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (4, '/images/adventures/1/img3.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (4, '/images/adventuresFishing/1/img2.jpg');
INSERT INTO rentable_pictures (rentable_id, pictures) VALUES (4, '/images/adventures/1/img1.jpg');


INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (1, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (1, 'no destruction of property');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (2, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (2, 'no destruction of property');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (3, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (3, 'no destruction of property');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (4, 'no smoking');
INSERT INTO rentable_rules_of_conduct (rentable_id, rules_of_conduct) VALUES (4, 'no destruction of property');



INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (1, 1);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (1, 4);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (1, 2);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (2, 4);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (2, 2);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (3, 1);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (3, 2);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (4, 1);
INSERT INTO rentable_tags (rentable_id, tags_id) VALUES (4, 2);


INSERT INTO reservation (id, cancelled, end_date_time, price, start_date_time, client_id, rentable_id) VALUES (1, false, '2022-05-21 19:44:47.521598', 130, '2022-05-21 18:44:47.521546', 'pera', 1);
INSERT INTO reservation (id, cancelled, end_date_time, price, start_date_time, client_id, rentable_id) VALUES (2, false, '2022-05-21 19:44:48.250883', 130, '2022-05-21 18:44:48.250856', 'mika', 4);



INSERT INTO review (id, posted, score, status, text, client_id, rentable_id) VALUES (1, '2022-05-21 17:44:47.41532', 3.3, 0, 'Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi ', 'pera', 1);
INSERT INTO review (id, posted, score, status, text, client_id, rentable_id) VALUES (2, '2022-05-21 17:44:47.483195', 3.3, 0, 'Ut mollis erat non quam rhoncus sodales. Nulla facilisi. Duis sit amet pellentesque dolor. Aliquam mollis rhoncus risus vel ornare. Nulla pellentesque risus ac lorem efficitur auctor. Cras auctor turpis', 'mika', 1);
INSERT INTO review (id, posted, score, status, text, client_id, rentable_id) VALUES (3, '2022-05-21 17:44:47.948207', 3.3, 0, 'Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi ', 'pera', 3);
INSERT INTO review (id, posted, score, status, text, client_id, rentable_id) VALUES (4, '2022-05-21 17:44:48.026171', 3.3, 0, 'Duis lobortis ex diam, sed euismod augue dignissim ut. Aenean non rhoncus ante. Pellentesque sed fringilla erat, in rutrum metus. Maecenas nec quam pellentesque leo ornare aliquet. Praesent viverra, lectus a egestas suscipit, mi ', 'pera', 4);


INSERT INTO role (id, name) VALUES (1, 'ROLE_COTTAGE_OWNER');
INSERT INTO role (id, name) VALUES (2, 'ROLE_BOAT_OWNER');
INSERT INTO role (id, name) VALUES (3, 'ROLE_FISHING_INSTRUCTOR');
INSERT INTO role (id, name) VALUES (4, 'ROLE_CLIENT');
INSERT INTO role (id, name) VALUES (5, 'ROLE_ADMIN');



INSERT INTO user_role (user_id, role_id) VALUES ('pera', 4);
INSERT INTO user_role (user_id, role_id) VALUES ('marko', 3);
INSERT INTO user_role (user_id, role_id) VALUES ('cowner', 1);
INSERT INTO user_role (user_id, role_id) VALUES ('admin', 5);


