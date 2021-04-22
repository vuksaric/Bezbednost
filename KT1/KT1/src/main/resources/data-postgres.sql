insert into permission (name) values
('REGISTER'), ('LOGIN');

insert into authority (name) values ('ROLE_ADMIN'), ('ROLE_USER');

insert into authorities_permissions (authority_id, permission_id) values
(1, 2),
(2, 1), (2, 2);

-- password-> Admin123!!!

insert into user_entity (id, username, password, user_role) values
(1, 'admin@gmail.com', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 0),
(2, 'user1@gmail.com', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 1),
(3, 'user2@gmail.com', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 1),
(4, 'user3@gmail.com', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 1),
(5, 'user4@gmail.com', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', 1);


insert into issuer(id, user_id, email,name,surname,organisation,organisation_unit)
values(1,1, 'vuk.saric@gmail.com','Vuk','Saric','Tim 17','admin');

insert into subject(id,user_id, name,surname,certificate,email,isca,organisation,organisation_unit, request_status) values
(1,2,'Marjana','Zalar',false,'marjana.zalar@gmail.com',false,'Tim 17','HR', 'CONFIRMED'),
(2,3,'Nikolina','Ivankovic',false,'nikolina.ivankovic@gmail.com',false,'Tim 17','SD', 'CONFIRMED'),
(3,4,'Marko','Marko',false,'marko.ivankovic@gmail.com',false,'Tim 17','Cistacica', 'CONFIRMED'),
(4,5,'End','Entity',false,'end.entity@gmail.com',false,'Tim 17','Proba', 'CONFIRMED');



alter sequence user_entity_id_seq restart with 6;
alter sequence subject_id_seq restart with 5;
alter sequence issuer_id_seq restart with 2;