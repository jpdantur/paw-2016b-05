create table if not exists store_items (
	item_id serial primary key,
	name varchar(100),
	description text,
	price decimal(20,2),
	sold integer default 0,
	created timestamp default current_timestamp,
	last_updated timestamp default current_timestamp
)
