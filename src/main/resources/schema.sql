drop table if exists products cascade;
drop table if exists categories cascade;

create table if not exists categories
(
    id   serial8 primary key,
    name varchar not null
);

create table if not exists products
(
    id   serial8 primary key,
    name varchar not null,
    category_id int8 not null,
    foreign key (category_id) references categories(id)
);