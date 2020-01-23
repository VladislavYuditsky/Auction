package com.yuditsky.auction;

public class Const {
    public static final String PROPERTIES_FILE_PATH = "db";

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
}
