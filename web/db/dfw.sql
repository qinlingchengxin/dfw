drop database dfw;
CREATE DATABASE dfw CHARACTER SET utf8 COLLATE utf8_general_ci;
use dfw;
create table dfw(
    id int not null auto_increment,
    account varchar(50) not null,
    pwd varchar(50) not null,
    nickname varchar(500) not null,
    bestScore int default 0,
    lastScore int default 0,
    school varchar(500),
    cls varchar(500),
    sex int(11),
    age int(11),
    allScore int default 0,
    times int default  0,
    name varchar(50) default '',
    tel varchar(20) default  '',
    pro1 int default 0,
    pro2 int default 0,
    typ int default 0,
    level int default  0,
    registerTime datetime,
    leaveTime int default 0,
    loginFlag int default 0,
    city varchar(50) default '',
    grade int default 0,
    dfwTimes int default 0,
    primary key (id)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE dfw ADD INDEX allScore(allScore);

create table adm(
    id int(11) not null auto_increment,
    account varchar(50) not null default 'admin',
    pwd varchar(50) not null default 'yuanzhou700',
    primary key (id)
) ;

insert into adm(account, pwd) values('admin', 'yuanzhou700');
TRUNCATE TABLE dfw;