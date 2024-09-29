create database flower;
    use flower;

create table admins(
            id int auto_increment primary key,
            Username varchar(20) not null,
            Password varchar(20) not null
);

insert into admins(Username, Password) values('James', '11111');

use flower;
create table flowers(
    flowerId int auto_increment primary key,
    name varchar(100) not null,
    status varchar(100) not null ,
    price double,
    date date,
    image varchar(100)
);