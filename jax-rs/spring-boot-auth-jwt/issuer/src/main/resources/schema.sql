create table accounts (
  id varchar(100) primary key,
  secret varchar(100) not null,
  name varchar(500),
  locked numeric(1) default 0 not null
);

create table account_roles (
  account_id varchar(100) not null,
  role varchar(100) not null,
  PRIMARY KEY(account_id, role)
);
