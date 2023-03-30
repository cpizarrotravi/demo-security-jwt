create table if not exists `usuario`(
	id int not null primary key auto_increment,
	username varchar(50) not null,
	password varchar(50) not null,
	email varchar(50) not null,
	enabled boolean not null
);
