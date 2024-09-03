create database flower;
    use flower;

create table admins(
            id int auto_increment primary key,
            Username varchar(20) not null,
            Password varchar(20) not null
);

insert into admins(Username, Password) values('James', '11111');
