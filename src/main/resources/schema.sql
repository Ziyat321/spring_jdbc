drop table if exists products cascade;
drop table if exists categories cascade;

create table categories
(
    id   serial primary key,
    name varchar(255) not null unique
);

create table products
(
    id          serial primary key,
    name        varchar(255)                    not null,
    price       double precision                not null,
    category_id int references categories (id) not null
);