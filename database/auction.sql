create schema if not exists auction default char set utf8;
use auction;

create table user
(
    user_id  int auto_increment
        primary key,
    login    varchar(16)                           not null,
    password varchar(60)                           not null,
    email    varchar(255)                          not null,
    role     enum ('user', 'admin') default 'user' not null,
    blocked  tinyint                default 0      not null,
    balance  decimal(14, 4)         default 0.0000 not null,
    constraint user_login_uindex
        unique (login)
);

create table credit
(
    credit_id   int auto_increment
        primary key,
    percent     decimal(14, 4)                not null,
    end_date    datetime                      not null,
    balance     decimal(14, 4) default 0.0000 not null,
    sum         decimal(14, 4) default 0.0000 not null,
    borrower_id int                           not null,
    constraint credit_user_user_id_fk
        foreign key (borrower_id) references user (user_id)
            on delete cascade
);

create table lot
(
    lot_id      int auto_increment
        primary key,
    name        varchar(45)                   not null,
    description varchar(1024)                 null,
    location    varchar(45)                   not null,
    start_price decimal(14, 4) default 0.0000 not null,
    owner_id    int                           not null,
    constraint lot_user_user_id_fk
        foreign key (owner_id) references user (user_id)
            on delete cascade
);

create table auction
(
    auction_id   int auto_increment
        primary key,
    auction_type enum ('direct', 'revers')               default 'direct'  not null,
    lot_id       int                                                       not null,
    status       enum ('waiting', 'active', 'completed') default 'waiting' not null,
    winner_id    int                                                       null,
    constraint auction_lot_lot_id_fk
        foreign key (lot_id) references lot (lot_id)
);

create table bid
(
    bid_id     int auto_increment
        primary key,
    bidder_id  int                           not null,
    sum        decimal(14, 4) default 0.0000 not null,
    time       datetime                      not null,
    auction_id int                           not null,
    constraint bid_auction_auction_id_fk
        foreign key (auction_id) references auction (auction_id)
            on delete cascade,
    constraint bid_user_user_id_fk
        foreign key (bidder_id) references user (user_id)
            on delete cascade
);

create table payment
(
    payment_id int auto_increment
        primary key,
    payer_id   int                           not null,
    sum        decimal(14, 4) default 0.0000 not null,
    lot_id     int                           not null,
    date       datetime                      not null,
    constraint payment_user_user_id_fk
        foreign key (payer_id) references user (user_id)
);


