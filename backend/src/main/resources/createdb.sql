create database alchemy;
create user alchemy@localhost identified by 'alchemy';
grant usage on *.* to alchemy@localhost identified by 'alchemy';
grant all privileges on alchemy.* to alchemy@localhost;

