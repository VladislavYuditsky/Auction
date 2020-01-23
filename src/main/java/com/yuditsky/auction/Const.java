package com.yuditsky.auction;

import java.time.format.DateTimeFormatter;

public class Const {
    public static final String PROPERTIES_FILE_PATH = "db";

    public static final DateTimeFormatter DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String ADD_NEW_USER = "INSERT INTO user (login, password, email) VALUES(?,?,?)";
    public static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user WHERE login=? AND password=?";
    public static final String UPDATE_USER_PASSWORD = "UPDATE user SET password=? WHERE login=?";
    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    public static final String UPDATE_USER_EMAIL = "UPDATE user SET email=? WHERE login=?";
    public static final String UPDATE_USER_BALANCE = "UPDATE user SET balance=? WHERE login=?";
    public static final String UPDATE_USER_ROLE = "UPDATE user SET role=? WHERE login=?";
    public static final String DELETE_USER_BY_LOGIN = "DELETE FROM user WHERE login=?";

    public static final String ADD_NEW_LOT = "INSERT INTO lot (name, description, location, start_price, min_price," +
            " seller_id) VALUES(?,?,?,?,?,?)";
    public static final String DELETE_LOT_BY_ID = "DELETE FROM lot WHERE lot_id=?";
    public static final String SELECT_LOT_BY_ID = "SELECT * FROM lot WHERE lot_id=?";
    public static final String UPDATE_LOT_DESCRIPTION = "UPDATE lot SET description=? WHERE lot_id=?";
    public static final String UPDATE_LOT_LOCATION = "UPDATE lot SET location=? WHERE lot_id=?";
    public static final String UPDATE_LOT_START_PRICE = "UPDATE lot SET start_price=? WHERE lot_id=?";
    public static final String UPDATE_LOT_MIN_PRICE = "UPDATE lot SET min_price=? WHERE lot_id=?";

    public static final String ADD_NEW_AUCTION = "INSERT INTO auction (auction_type, lot_id, finish_time)" +
            " VALUES(?,?,?)";
    public static final String SELECT_AUCTION_BY_ID = "SELECT * FROM auction WHERE auction_id=?";
    public static final String UPDATE_AUCTION_TYPE = "UPDATE auction SET auction_type=? WHERE auction_id=?";
    public static final String UPDATE_AUCTION_LOT = "UPDATE auction SET lot_id=? WHERE auction_id=?";
    public static final String UPDATE_AUCTION_FINISH_TIME = "UPDATE auction SET finish_time=? WHERE auction_id=?";
    public static final String DELETE_AUCTION_BY_ID = "DELETE FROM auction WHERE auction_id=?";

    public static final String ADD_NEW_BID = "INSERT INTO bid (bidder_id, sum, time, auction_id)" +
            " VALUES(?,?,?,?)";
    public static final String SELECT_BID_BY_ID = "SELECT * FROM bid WHERE bid_id=?";
    public static final String SELECT_BID_IDS_BY_AUCTION_ID = "SELECT * FROM bid WHERE auction_id=?";
    public static final String UPDATE_BID_BIDDER_ID = "UPDATE bid SET bidder_id=? WHERE bid_id=?";
    public static final String UPDATE_BID_SUM = "UPDATE bid SET sum=? WHERE bid_id=?";
    public static final String UPDATE_BID_TIME = "UPDATE bid SET time=? WHERE bid_id=?";
    public static final String UPDATE_BID_AUCTION_ID = "UPDATE bid SET auction_id=? WHERE bid_id=?";
    public static final String DELETE_BID_BY_ID = "DELETE FROM bid WHERE bid_id=?";
}
