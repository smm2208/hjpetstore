-- frist drop database hjpetstore and user hjpetstore
drop database if exists hjpetstore;
create database hjpetstore;

-- create user hjpetstore and give the password  hjpetstore
grant all privileges on hjpetstore.* to hjpetstore identified by 'hjpetstore';

show databases;