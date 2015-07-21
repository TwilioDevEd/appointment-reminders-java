create table appointments (
    id int not null PRIMARY KEY,
    name varchar(50) not null,
    phoneNumber varchar(50) not null,
    delta int not null,
    time timestamp with time zone not null
);