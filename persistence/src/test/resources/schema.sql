CREATE TYPE BYTEA AS VARBINARY(1000000)

create table if not exists users (
	user_id serial primary key,
	first_name varchar(100),
	last_name varchar(100),
	username varchar(100),
	password varchar(100),
	email varchar(100),
	constraint unique_username unique(username),
	constraint unique_email unique(email)
);

-- $2a$10$2780YD5RxxP8TdDuH75OL.HraXvr7oqExc/ZqdCIT7WZ8XtA78eK2 = 'root'



create table if not exists store_categories (
	category_id serial primary key,
	category_name varchar(100),
	parent integer,
	created timestamp default current_timestamp,
	last_updated timestamp default current_timestamp,
	constraint parent_fk foreign key (parent) references store_categories
);

insert into store_categories (category_id, category_name, parent)
	select 0, 'root', 0;


create table if not exists store_items (
	item_id serial primary key,
	name varchar(100),
	description text,
	price decimal(20,2),
	sold integer default 0,
	category integer,
	used boolean default false,
	published boolean default false,
	owner integer,
	created timestamp default current_timestamp,
	last_updated timestamp default current_timestamp,
	constraint category_fk foreign key (category) references store_categories,
	constraint owner_fk foreign key (owner) references users
);

create table if not exists favourites (
	favourite_id serial primary key,
	user_id integer,
	store_item_id integer,
	constraint user_fk foreign key (user_id) references users,
	constraint store_item_fk foreign key (store_item_id) references store_items
);

create table if not exists comments (
	comment_id serial primary key,
	user_id integer references users on delete cascade,
	item_id integer references store_items on delete cascade,
	comment_content varchar(300),
	created timestamp default current_timestamp
);

create table if not exists images (
	image_id serial primary key,
	item_id integer REFERENCES store_items,
	content bytea,
	mime_type varchar(100)
);

create table if not exists roles (
	role_id serial primary key,
	role_name varchar(100),
	role_slug varchar(100) unique
);


create table if not exists user_roles (
	user_role_id serial primary key,
	user_id integer REFERENCES users,
	role_id integer REFERENCES roles
);


create table if not exists resource (
	resource_id serial primary key,
	resource_name varchar(100),
	resource_slug varchar(100)
);



create table if not exists resource_role (
	resource_role_id serial primary key,
	resource_id integer REFERENCES resource,
	role_id integer REFERENCES roles
);

create table if not exists sales (
	sale_id serial primary key,
	user_id integer REFERENCES users,
	item_id integer REFERENCES store_items,
	approved boolean default false
);