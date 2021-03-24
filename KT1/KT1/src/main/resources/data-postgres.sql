insert into issuer(id,email,name,surname,organisation,organisation_unit)
values(1,'vuk.saric@gmail.com','Vuk','Saric','Tim 17','admin');

insert into subject(id,name,surname,certificate,email,isca,organisation,organisation_unit)
values(1,'Marjana','Zalar',false,'marjana.zalar@gmail.com',false,'Tim 17','HR');

insert into subject(id,name,surname,certificate,email,isca,organisation,organisation_unit)
values(2,'Nikolina','Ivankovic',false,'nikolina.ivankovic@gmail.com',false,'Tim 17','SD');

insert into subject(id,name,surname,certificate,email,isca,organisation,organisation_unit)
values(3,'Marko','Marko',false,'marko.ivankovic@gmail.com',false,'Tim 17','Cistacica');

insert into subject(id,name,surname,certificate,email,isca,organisation,organisation_unit)
values(4,'End','Entity',false,'end.entity@gmail.com',false,'Tim 17','Proba');

alter sequence users_id_seq restart with 5;