create table if not exists store_categories (
	category_id serial primary key,
	name varchar(100),
	parent integer,
	category_path text,
	created timestamp default current_timestamp,
	last_updated timestamp default current_timestamp,
	constraint parent_fk foreign key (parent) references store_categories 
);

--with recursive tree as
--(
--	select category_id, name, parent, name::text as fullname
--	from store_categories
--	where category_id = 0
--	union all
--		select c.category_id, c.name, c.parent, (c2.fullname || '->' || c.name)::text as fullname
--		from store_categories as c
--			inner join tree as c2 on (c.parent=c2.category_id) where c.category_id <> 0
--) select * from tree

INSERT INTO store_categories (category_id, name, parent, category_path)
	SELECT 0, 'root', 0, '0' WHERE NOT EXISTS (
		SELECT category_id FROM store_categories WHERE category_id = 0
	);

create table if not exists store_items (
	item_id serial primary key,
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



