insert into accounts (id, secret, name, locked) values ('act1', 'tmtmLqan0LfZtn3byf/mwjWQ6g1DwOuOyY4s5Dhtk6M=', 'Account 1', 0); -- act1secret
insert into accounts (id, secret, name, locked) values ('act2', 'kVpTFVWIMVLvTtKGqELCSXVp25ej3tUNLkUSTPNAqvo=', 'Account 2', 0); -- act2secret

insert into account_roles (account_id, role) values ('act1', 'ROLE1');
insert into account_roles (account_id, role) values ('act1', 'ROLE3');

insert into account_roles (account_id, role) values ('act2', 'ROLE2');
insert into account_roles (account_id, role) values ('act2', 'ROLE3');