
insert into consumer (id,first_name, last_name, email, password,enabled) values (1,'user', 'user', 'user@gmail.com', '$2a$12$QttYfjkHasGQAlqvUL0H6OTIVuyPwlBUw2Wv1gh2NuPhX.B4GFlm.
',1);
insert into consumer (id,first_name, last_name, email, password,enabled) values (2,'admin', 'admin', 'admin@gmail.com', '$2a$12$CHqdqedKzAy5Wz2nHdMS4Okidi56eZIh61Ep9cNJXW.OOar.AkzKe
',1);

insert into contact_info (id,type, value,consumer_id) values (1,'EMAIL', 'contact@example.com',1);


insert into animal_location (id,latitude, longitude, city)
values (1,12.34, 56.78, 'NY');


insert into animal (id,breed, type, gender, age, description)
values (1,'LABRADOR', 'DOG', 'MALE', 3, 'DescriptionValue');

 insert into announcement (ad_id,header, consumer_id, animal_id,animal_location_id,created_at,updated_at)
values (1,'Header', 1, 1, 1,'2023-10-08 11:06:35.405666','2023-11-08 14:30:28.405666');

insert into role (id,role_name)
values (1,'ROLE_USER');

insert into role (id,role_name)
values (2,'ROLE_ADMIN');

insert into consumers_roles (consumer_id,role_id) values (1,1);
insert into consumers_roles (consumer_id,role_id) values (2,2);

insert into comment (id,created_at, text, consumer_id, ad_id)
values (1,'2023-10-08 11:06:35.405666', 'CommentText', 1, 1);

insert into announcement_photos (ad_id,photo_urls) values (1,"https://example.com/screenshot.png");