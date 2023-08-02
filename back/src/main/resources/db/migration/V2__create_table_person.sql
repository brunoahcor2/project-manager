create table person (
   id bigint generated by default as identity,
    active boolean not null,
    cpf varchar(11) not null,
    date_birth date,
    employee boolean not null,
    name varchar(100) not null,
    position varchar(50),
    primary key (id)
)