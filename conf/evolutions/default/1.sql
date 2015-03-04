# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table collectlink (
  id                        bigint not null,
  link                      varchar(80),
  signedup                  timestamp,
  constraint pk_collectlink primary key (id))
;

create table contact (
  id                        bigint not null,
  firstname                 varchar(30),
  lastname                  varchar(50),
  email                     varchar(100),
  site                      varchar(80),
  message                   varchar(1000),
  signedup                  timestamp,
  constraint pk_contact primary key (id))
;

create table link (
  id                        bigint not null,
  url                       varchar(80),
  title                     varchar(40),
  description               varchar(2000),
  signedup                  timestamp,
  constraint pk_link primary key (id))
;

create table newslatter (
  id                        bigint not null,
  name                      varchar(80),
  email                     varchar(100),
  signedup                  timestamp,
  constraint pk_newslatter primary key (id))
;

create sequence collectlink_seq;

create sequence contact_seq;

create sequence link_seq;

create sequence newslatter_seq;




# --- !Downs

drop table if exists collectlink cascade;

drop table if exists contact cascade;

drop table if exists link cascade;

drop table if exists newslatter cascade;

drop sequence if exists collectlink_seq;

drop sequence if exists contact_seq;

drop sequence if exists link_seq;

drop sequence if exists newslatter_seq;

