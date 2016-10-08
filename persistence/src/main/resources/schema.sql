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

insert into users (user_id, first_name, last_name, username, password, email)
	select 0, 'Sr.', 'Admin', 'system.admin', '$2a$10$2780YD5RxxP8TdDuH75OL.HraXvr7oqExc/ZqdCIT7WZ8XtA78eK2', 'admin@siglas.com'
	where not exists (
		select * from users where username = 'system.admin'
	);

create table if not exists store_categories (
	category_id serial primary key,
	name varchar(100),
	parent integer,
--	category_path text,
	created timestamp default current_timestamp,
	last_updated timestamp default current_timestamp,
	owner integer,
	constraint parent_fk foreign key (parent) references store_categories,
	constraint owner_fk foreign key (owner) references users
);

insert into store_categories (category_id, name, parent)
	select 0, 'root', 0 where not exists (
		select category_id from store_categories where category_id = 0
	);

create table if not exists store_items (
	item_id serial primary key,
	name varchar(100),
	description text,
	price decimal(20,2),
	sold integer default 0,
	category integer,
	used boolean,
	published boolean,
	created timestamp default current_timestamp,
	last_updated timestamp default current_timestamp,
	constraint category_fk foreign key (category) references store_categories
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
	user_id integer,
	comment_content varchar(300),
	created timestamp default current_timestamp,
	constraint user_fk foreign key (user_id) references users
);

create table if not exists images (
	image_id serial primary key,
	item_id integer,
	content bytea,
	mime_type varchar(100),
	filename varchar(100),
	constraint item_fk foreign key (item_id) references store_items
);

create table if not exists roles (
	role_id serial primary key,
	role_name varchar(100),
	role_slug varchar(100),
	constraint unique_role_slug unique(role_slug)
);

insert into roles (role_id, role_name, role_slug)
	select 0, 'root', 'ROOT' where not exists (
		select * from roles where role_slug = 'ROOT'
	);


create table if not exists user_roles (
	user_role_id serial primary key,
	user_id integer,
	role_id integer,
	constraint user_fk foreign key (user_id) references users,
	constraint role_fk foreign key (role_id) references roles
);

insert into user_roles (user_id, role_id)
	select 0, 0 where not exists (
		select * from user_roles where user_id = 0 and role_id = 0
	);

create table if not exists resource (
	resource_id serial primary key,
	resource_name varchar(100),
	resource_slug varchar(100)
);



create table if not exists resource_role (
	resource_role_id serial primary key,
	resource_id integer,
	role_id integer,
	constraint resource_fk foreign key (resource_id) references resource,
	constraint role_fk foreign key (role_id) references roles
);
