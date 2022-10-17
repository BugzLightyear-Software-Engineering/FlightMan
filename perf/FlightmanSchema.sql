create extension if not exists "uuid-ossp";

drop table if exists address_country;

drop table if exists address_city;

drop table if exists user_diet;
	
drop table if exists users;

create table users(
	user_id uuid default uuid_generate_v4(),
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	phone_number varchar(50) not null,
	email varchar(50) not null,
	passport_number varchar(50) not null,
	address_fl varchar(50) not null,
	address_sl varchar(50) not null,
	address_country_id int not null,
	address_city_id int not null,
	address_zipcode int not null,
	diet_id int,
	diet_comments varchar(250),
	rewards_miles int,
	constraint primary key (user_id)
	constraint foreign key (fk_address_country_id) references address_country,
	constraint foreign key (fk_address_city) references address_city,
	constraint foreign key (fk_diet_id) references user_diet,			  
);
				  
