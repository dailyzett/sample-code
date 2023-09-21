create table users
(
    id       int auto_increment primary key,
    username varchar(45) not null,
    password varchar(45) not null,
    enabled  boolean     not null
);

create table authorities
(
    id        int auto_increment primary key,
    username  varchar(50) not null,
    authority varchar(50) not null
);

insert ignore into users
values (null, 'happy', '12345', '1');

insert ignore into authorities
    value (null, 'happy', 'write')