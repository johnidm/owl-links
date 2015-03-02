# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table collectlink (
  id                        bigint auto_increment not null,
  link                      varchar(80),
  signedup                  datetime,
  constraint pk_collectlink primary key (id))
;

create table contact (
  id                        bigint auto_increment not null,
  firstname                 varchar(30),
  lastname                  varchar(50),
  email                     varchar(100),
  site                      varchar(80),
  message                   varchar(1000),
  signedup                  datetime,
  constraint pk_contact primary key (id))
;

create table link (
  id                        varchar(255) not null,
  url                       varchar(80),
  title                     varchar(40),
  description               varchar(100),
  signedup                  datetime,
  constraint pk_link primary key (id))
;

create table newslatter (
  id                        bigint auto_increment not null,
  name                      varchar(80),
  email                     varchar(100),
  signedup                  datetime,
  constraint pk_newslatter primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table collectlink;

drop table contact;

drop table link;

drop table newslatter;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

