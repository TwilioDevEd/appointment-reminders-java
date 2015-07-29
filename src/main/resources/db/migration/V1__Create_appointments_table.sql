create table appointments (
    id SERIAL UNIQUE not null PRIMARY KEY,
    name varchar(50) not null,
    phoneNumber varchar(50) not null,
    delta int not null,
    date varchar(20) not null,
    timeZone varchar(50) not null
);