drop table if exists appointments;

create table appointments (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT not null,
    phoneNumber TEXT not null,
    delta int not null,
    date DATETIME not null,
    timeZone TEXT not null
);

insert into sqlite_sequence (name, seq) VALUES ("tableSeq", 1);
