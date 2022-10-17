create extension if not exists "uuid-ossp";

-- User Tables --

drop table if exists address_country cascade;

create table address_country(
	address_country_id serial,
	country_name varchar(50) unique not null,
	constraint pk_address_country primary key (address_country_id)
);

drop table if exists address_city cascade;
	
create table address_city(
	address_city_id serial,
	address_country_id int not null,
	city_name varchar(50) not null,
	constraint pk_address_city primary key (address_city_id),
	constraint fk_address_city_address_country foreign key (address_country_id) references address_country
);

drop table if exists user_diet cascade;

create table user_diet(
	diet_id serial,
	diet_name varchar(50) unique not null,
	constraint pk_user_diet primary key (diet_id)
);
	
drop table if exists users cascade;

create table users(
	user_id uuid default uuid_generate_v4(),
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	phone_number varchar(50) unique not null,
	email varchar(50) unique not null,
	passport_number varchar(50) unique not null,
	address_fl varchar(50) not null,
	address_sl varchar(50) not null,
	address_country_id int not null,
	address_city_id int not null,
	address_zipcode int not null,
	diet_id int,
	diet_comments varchar(250),
	rewards_miles int,
	constraint pk_users primary key (user_id),
	constraint fk_users_address_country foreign key (address_country_id) references address_country,
	constraint fk_users_address_city foreign key (address_city_id) references address_city,
	constraint fk_users_diet foreign key (diet_id) references user_diet			  
);

-- Airport Tables --

drop table if exists airports cascade;

create table airports(
	airport_id uuid default uuid_generate_v4(),
	airport_name varchar(50) not null,
	airport_abv_name varchar(5) not null,
	latitude double precision not null,
	longitude double precision not null,
	constraint pk_airports primary key (airport_id)
);
				
-- Flight Tables --

drop table if exists flight_model cascade;

create table flight_model(
	flight_model_id serial,
	flight_manufacturer_name varchar(50) not null,
	flight_model_number varchar(50) not null,
	seat_capacity int not null,
	-- for the sake of simplicity we assume that all flights are perfect grids
	-- where each col coresponds to a letter e.g. (col0 -> A, col1 -> B,...etc)
	seat_row_count int not null,
	seat_col_count int not null,
	constraint pk_flight_model primary key (flight_model_id)
);

drop table if exists meals cascade;

create table meals(
	meal_id serial,
	meal_name varchar(50) not null,
	meal_desc varchar(250) not null,
	constraint pk_meals primary key (meal_id)
);

drop table if exists flights cascade;

create table flights(
	flight_id uuid default uuid_generate_v4(),
	source_airport_id uuid,
	dest_airport_id uuid,
	flight_model_id int,
	departure_time time not null,
	est_arrival_time time not null,
	num_available_seats int not null,
	delay_time time,
	constraint pk_flights primary key (flight_id),
	constraint fk_flights_source_airport foreign key (source_airport_id) references airports(airport_id),
	constraint fk_flights_dest_airport foreign key (dest_airport_id) references airports(airport_id),
	constraint fk_flights_flight_model foreign key (flight_model_id) references flight_model
);

drop table if exists flight_meals cascade;

create table flight_meals(
	flight_id uuid,
	meal_id int,
	constraint pk_flight_meals primary key (flight_id,meal_id),
	constraint fk_flight_meals_flights foreign key (flight_id) references flights,
	constraint fk_flight_meals_meals foreign key (meal_id) references meals
);
	
-- Booking Tables --

drop table if exists luggage_status cascade;

create table luggage_status(
	luggage_status_id serial,
	luggage_status varchar(50),
	constraint pk_luggage_status primary key (luggage_status_id)
);

drop table if exists luggage cascade;

create table luggage(
	luggage_id uuid default uuid_generate_v4(),
	booking_id uuid not null,
	luggage_status_id int not null,
	constraint pk_luggage primary key (luggage_id),
	constraint fk_luggage_bookings foreign key (booking_id) references bookings,
	constraint fk_luggage_status foreign key (luggage_status_id) references luggage_status
);

drop table if exists bookings cascade;

create table bookings(
	user_id uuid,
	booking_id uuid default uuid_generate_v4(),
	flight_id uuid,
	meal_id int,
	seat_number varchar(3) not null,
	payment_status bool not null,
	constraint pk_bookings primary key (user_id,booking_id,flight_id),
	constraint fk_bookings_users foreign key (user_id) references users,
	constraint fk_bookings_flights foreign key (flight_id) references flights
);

-------------------------------- POPULATE TABLES --------------------------------

-- User Tables --
insert into address_country(country_name) values ('United States'),('Nigeria'),('India'),('South Africa');
insert into address_city(address_country_id,city_name) values(1,'New York'), (2,'Lagos'),(3,'Hyderabad'),(4,'Johannesburg');
insert into user_diet(diet_name) values ('Vegan'),('Vegiterian'),('No restrictions');
insert into users(first_name,last_name,phone_number,email,passport_number,address_fl,address_sl,
				  address_country_id,address_city_id,address_zipcode,
				 diet_id,diet_comments,rewards_miles)
		values ('John','Smith','+123456723423910','abc@gmail.com','A123115678','12 E 345 ST','APT 123',1,1,12345,1,'none',0),
		('Jane','Doe','+1234435678910','abcd@gmail.com','A1232345678','12 E 345 ST','APT 123',1,1,12345,1,'none',0),
		('Adekunle','Gold','+1284027134910','abcde@gmail.com','A12321678','12 E 345 ST','APT 123',2,3,12345,2,'No nuts',0),
		('Musa','Yaradua','+23423452403910','abcdef@gmail.com','A1124435678','12 E 345 ST','APT 123',3,3,12345,3,'Only onions',0);
insert into airports(airport_name,airport_abv_name,latitude,longitude)
	   values ('John F. Kennedy International Airport','JFK',40.6413,-73.7781),
	   		  ('Murtala Mohammed International Airport','MM2',6.5844,3.3333),
			  ('Hyderabad International Airport','GMR',17.2403,78.4294);
insert into flight_model(flight_manufacturer_name,flight_model_number,seat_capacity,seat_row_count,seat_col_count)
	   values ('boeing','737e',300,60,5),
	   		  ('aml','723e',600,60,10),
			  ('escrow','565f',100,25,4);
insert into meals(meal_name,meal_desc)
	   values ('Egba','Meal1'),
	   		  ('Pounded Yam','Meal2'),
			  ('Suya','Meal3');
-- insert into flights(source_airport_id,dest_airport_id,flight_model_id,departure_time,est_arrival_time,num_available_seats,delay_time)
-- 	   values ();
insert into flight_meals(flight_id,meal_id)
	   select f.flight_id, ms.meal_id
	   from flights as f
	   cross join meals as ms;
insert into luggage_status(luggage_status)
	   values ('Checked In'),('In Transit'),('Arrived at Final Destination'),('Claimed');
-- insert into bookings(user_id,flight_id,meal_id,seat_number,payment_status)
-- 	   values ();
select * from luggage_status

	  

