insert into permission (name) values
('REGISTER'), ('LOGIN'), ('CONFIRM'), ('DENY'), ('APPROVE');

insert into authority (name) values ('ROLE_ADMIN'), ('ROLE_USER');

insert into authorities_permissions (authority_id, permission_id) values
(1, 2), (1, 4), (1, 5),
(2, 1), (2, 2), (2, 3);

-- password-> Admin123!!!

insert into user_entity (id, username, password, user_role, enabled) values
(1, 'admin@gmail.com', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 0, true),
(2, 'user1@gmail.com', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 1, true),
(3, 'user2@gmail.com', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 1, true),
(4, 'user3@gmail.com', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 1, true),
(5, 'nikolina@gmail.com', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 1, true);

insert into user_authority (user_id, authority_id) values
(1, 1),
(2, 2),
(3, 2),
(4, 2),
(5, 2);


insert into issuer(id, user_id, email,name,surname,organisation,organisation_unit)
values(1,1, 'admin@gmail.com','Vuk','Saric','Tim 17','admin');

insert into subject(id,user_id, name,surname,certificate,email,isca,organisation,organisation_unit, request_status) values
(1,2,'Marjana','Zalar',false,'user1@gmail.com',false,'Tim 17','HR', 'CONFIRMED'),
(2,3,'Nikolina','Ivankovic',false,'user2@gmail.com',false,'Tim 17','SD', 'CONFIRMED'),
(3,4,'Marko','Marko',false,'user3@gmail.com',false,'Tim 17','Cistacica', 'CONFIRMED'),
(4,5,'End','Entity',false,'nikolina@gmail.com',false,'Tim 17','Proba', 'CONFIRMED');

insert into verification_token(id,token,email,expiry_date) values
(1, 'hgrkfpsksmcujef', 'vuk.saric@gmail.com', '2021-01-02'),
(2, 'aaallliiibbbuuu', 'marjana.zalar@gmail.com', '2021-01-02'),
(3, 'dddoooiiiuuuyyy', 'nikolina.ivankovic@gmail.com', '2021-01-02'),
(4, 'bbbnnnmmmvvvkkk', 'marko.ivankovic@gmail.com', '2021-01-02'),
(5, 'pppoooiiiuuuyyy', 'end.entity@gmail.com', '2021-01-02');


alter sequence user_entity_id_seq restart with 6;
alter sequence subject_id_seq restart with 5;
alter sequence issuer_id_seq restart with 2;
alter sequence verification_token_id_seq restart with 6;