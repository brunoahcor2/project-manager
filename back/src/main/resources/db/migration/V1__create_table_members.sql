create table members (
    project_id bigint not null,
    person_id bigint not null,
    primary key (project_id, person_id)
)