create database stocker charset utf8mb4 collate utf8mb4_general_ci;
create user stocker identified by '1234Qwer';
grant all privileges on stocker.* to 'stocker'@'%';
flush privileges;