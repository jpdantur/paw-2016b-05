create table if not exists store_categories (
	category_id integer identity primary key,
	name varchar(100),
	parent integer,
	--category_path text,
	created timestamp default current_timestamp,
	last_updated timestamp default current_timestamp
	--constraint parent_fk foreign key (parent) references store_categories 
);

create table if not exists store_items (
	item_id integer identity primary key,
	name varchar(100),
	description text,
	price decimal(20,2),
	sold integer default 0,
	category integer,
	email varchar(100),
	created timestamp default current_timestamp,
	last_updated timestamp default current_timestamp,
	constraint category_fk foreign key (category) references store_categories
);