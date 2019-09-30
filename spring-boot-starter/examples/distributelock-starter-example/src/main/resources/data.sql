-- do not rename this file, Spring Boot runs 'data.sql' on startup

create table lock (
    id int not null auto_increment primary key,
    lock_key varchar(255) unique,
    token varchar(255),
    expireAt timestamp,
);

create trigger log_trigger
after insert
on lock
for each row
call "org.chance.example.starter.distributelock.trigger.LoggingDatabaseTrigger"