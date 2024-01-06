
insert into consumer (id,first_name, last_name, email, password) values (1,'Kirill', 'Kasyanov', 'kirillkasanov291@gmail.com', '123456');

insert into contact_info (id,contact_type, contact_value,consumer_id) values (1,'EMAIL', 'contact@example.com',1);


insert into animal_location (id,latitude, longitude, city)
values (1,12.34, 56.78, 'NY');


insert into animal (id,breed, type, gender, age, description)
values (1,'LABRADOR', 'DOG', 'MALE', 3, 'DescriptionValue');

 insert into announcement (announcement_id,header, consumer_id, animal_id, animal_location_id,created_at,updated_at)
values (1,'Header', 1, 1, 1,'2023-12-08 11:06:35.405666','2023-12-08 11:06:35.405666');

insert into role (id,role_name)
values (1,'ROLE_USER');

insert into consumers_roles (consumer_id,role_id) values (1,1);

insert into comment (id,created_at, text, consumer_id, announcement_id)
values (1,'2023-01-02 00:00:00', 'CommentText', 1, 1);

insert into announcement_photos (announcement_id,photo_urls) values (1,"url1");