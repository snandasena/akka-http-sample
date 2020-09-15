drop database bid_statas;
create database bid_statas;
use  bid_statas;
create table sites(
    id int(10) primary key,
    exchange_id int(10),
    exchange_site_id int(10),
    domain varchar(100) unique
);

create table sites_placements(
    id int(10) primary key,
    site_id int(10),
    tagid varchar(100),
    constraint site_id_fk_site_placements foreign key (site_id) references sites(id)
);

create table segments(
    id int(10) primary key,
    site_id int(10),
    placement_id int(10),
    constraint site_id_fk_segments foreign key (site_id) references sites(id),
    constraint placement_id_fk_segments foreign key(placement_id) references sites_placements(id)
);

DROP TABLE IF EXISTS journal;

CREATE TABLE IF NOT EXISTS journal (
  ordering SERIAL,
  persistence_id VARCHAR(255) NOT NULL,
  sequence_number BIGINT NOT NULL,
  deleted BOOLEAN DEFAULT FALSE,
  tags VARCHAR(255) DEFAULT NULL,
  message BLOB NOT NULL,
  PRIMARY KEY(persistence_id, sequence_number)
);

CREATE UNIQUE INDEX journal_ordering_idx ON journal(ordering);

DROP TABLE IF EXISTS snapshot;

CREATE TABLE IF NOT EXISTS snapshot (
  persistence_id VARCHAR(255) NOT NULL,
  sequence_number BIGINT NOT NULL,
  created BIGINT NOT NULL,
  snapshot BLOB NOT NULL,
  PRIMARY KEY (persistence_id, sequence_number)
);