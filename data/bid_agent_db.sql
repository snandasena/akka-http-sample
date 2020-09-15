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