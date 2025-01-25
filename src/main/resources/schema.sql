create table if not exists product (
    id bigint identity primary key,
    name varchar(255),
    description varchar(255),
    price varchar(255)
)