create table products (
  id integer primary key auto_increment,
  sku varchar(100) not null,
  description varchar(500),
  category varchar(10),
  price double
);