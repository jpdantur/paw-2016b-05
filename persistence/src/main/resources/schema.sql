create table if not exists store_items (
	item_id serial primary key,
	name varchar(100),
	description text,
	price decimal(20,2),
	sold integer default 0,
	created timestamp default current_timestamp,
	last_updated timestamp default current_timestamp
);


create table if not exists store_categories (
	category_id serial primary key,
	name varchar(100),
	display_name varchar(100),
	parent integer,
	category_path text,
	created timestamp default current_timestamp,
	last_updated timestamp default current_timestamp
	--constraint parent_fk foreign key (parent) references store_categories 
);
