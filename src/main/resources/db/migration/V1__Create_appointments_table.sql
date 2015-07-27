create table appointments (
    id SERIAL UNIQUE not null PRIMARY KEY,
    name varchar(50) not null,
    phoneNumber varchar(50) not null,
    delta int not null,
    time timestamp with time zone not null
);