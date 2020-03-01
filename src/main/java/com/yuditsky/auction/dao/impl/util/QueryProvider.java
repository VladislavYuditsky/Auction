package com.yuditsky.auction.dao.impl.util;

public class QueryProvider {
    public static final String INSERT_USER = "INSERT INTO user (login, password, email) VALUES(?,?,?)";
    public static final String SELECT_ALL_USERS = "SELECT * FROM user";
    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    public static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE user_id=?";
    public static final String UPDATE_USER_BY_ID = "UPDATE user SET password=?, email=?, role=?, blocked=?, balance=?" +
            " WHERE user_id=?";
    public static final String DELETE_USER_BY_ID = "DELETE FROM user WHERE user_id=?";

    public static final String INSERT_LOT = "INSERT INTO lot (name, description, location, start_price, owner_id)" +
            " VALUES(?,?,?,?,?)";
    public static final String DELETE_LOT_BY_ID = "DELETE FROM lot WHERE lot_id=?";
    public static final String SELECT_ALL_LOTS = "SELECT * FROM lot";
    public static final String SELECT_LOT_BY_ID = "SELECT * FROM lot WHERE lot_id=?";
    public static final String SELECT_LOT_BY_OWNER_ID = "SELECT * FROM lot WHERE owner_id=?";
    public static final String SELECT_LOT_BY_OWNER_ID_WITH_LIMIT_AND_OFFSET = "SELECT * FROM lot WHERE owner_id=? " +
            "ORDER BY lot_id DESC LIMIT ? OFFSET ?";
    public static final String SELECT_LOTS_WITH_USER_BIDS = "SELECT * FROM lot WHERE lot_id IN (SELECT lot_id " +
            "FROM auction WHERE auction_id IN (SELECT auction_id FROM bid WHERE bidder_id=?))";
    public static final String SELECT_LOTS_WITH_USER_BIDS_WITH_LIMIT_AND_OFFSET = "SELECT * FROM lot WHERE lot_id IN " +
            "(SELECT lot_id FROM auction WHERE auction_id IN (SELECT auction_id FROM bid WHERE bidder_id=?))" +
            " LIMIT ? OFFSET ?";
    public static final String SELECT_ACTIVE_LOT_BY_AUCTION_TYPE = "SELECT * FROM lot WHERE lot_id IN (SELECT lot_id" +
            " FROM auction WHERE auction_type=? AND status='active')";
    public static final String SELECT_ACTIVE_LOT_BY_AUCTION_TYPE_WITH_LIMIT_AND_OFFSET = "SELECT * FROM lot" +
            " WHERE lot_id IN (SELECT lot_id FROM auction WHERE auction_type=? AND status='active') LIMIT ? OFFSET ?";
    public static final String SELECT_AWAITING_PAYMENT_LOTS = "SELECT * FROM lot WHERE lot_id IN (SELECT lot_id " +
            "FROM auction WHERE winner_id=?)";
    public static final String SELECT_AWAITING_PAYMENT_LOTS_WITH_LIMIT_AND_OFFSET = "SELECT * FROM lot WHERE lot_id " +
            "IN (SELECT lot_id FROM auction WHERE winner_id=?) LIMIT ? OFFSET ?";
    public static final String UPDATE_LOT_BY_ID = "UPDATE lot SET name=?, description=?, location=?, start_price=?," +
            " owner_id=? WHERE lot_id=?";

    public static final String INSERT_AUCTION = "INSERT INTO auction (auction_type, lot_id, status, winner_id)" +
            " VALUES(?,?,?,?)";
    public static final String SELECT_ALL_AUCTIONS = "SELECT * FROM auction";
    public static final String SELECT_AUCTION_BY_ID = "SELECT * FROM auction WHERE auction_id=?";
    public static final String SELECT_AUCTION_BY_TYPE = "SELECT * FROM auction WHERE auction_type=?";
    public static final String SELECT_AUCTION_BY_STATUS = "SELECT * FROM auction WHERE status=?";
    public static final String SELECT_AUCTION_BY_STATUS_WITH_LIMIT_AND_OFFSET = "SELECT * FROM auction WHERE status=? " +
            "LIMIT ? OFFSET ?";
    public static final String SELECT_AUCTION_BY_WINNER_ID = "SELECT * FROM auction WHERE winner_id=?";
    public static final String SELECT_AUCTION_BY_LOT_ID = "SELECT * FROM auction WHERE lot_id=?";
    public static final String UPDATE_AUCTION_BY_ID = "UPDATE auction SET auction_type=?, lot_id=?, status=?," +
            " winner_id=? WHERE auction_id=?";
    public static final String DELETE_AUCTION_BY_ID = "DELETE FROM auction WHERE auction_id=?";

    public static final String INSERT_BID = "INSERT INTO bid (bidder_id, sum, time, auction_id)" +
            " VALUES(?,?,?,?)";
    public static final String SELECT_ALL_BIDS = "SELECT * FROM bid";
    public static final String SELECT_BID_BY_ID = "SELECT * FROM bid WHERE bid_id=?";
    public static final String SELECT_BID_BY_AUCTION_ID = "SELECT * FROM bid WHERE auction_id=?";
    public static final String SELECT_BID_BY_BIDDER_ID = "SELECT * FROM bid WHERE bidder_id=?";
    public static final String SELECT_BID_WITH_MIN_SUM_BY_AUCTION_ID = "SELECT * FROM bid WHERE sum=(SELECT MIN(sum)" +
            " FROM bid WHERE auction_id=?)";
    public static final String SELECT_BID_WITH_MIN_SUM_BY_BIDDER_ID_AND_AUCTION_ID = "SELECT * FROM bid WHERE" +
            " sum=(SELECT MIN(sum) FROM bid WHERE bidder_id=? AND auction_id=?)";
    public static final String SELECT_BID_WITH_MAX_SUM_BY_AUCTION_ID = "SELECT * from bid WHERE sum=(SELECT MAX(sum)" +
            " FROM bid WHERE auction_id=?)";
    public static final String UPDATE_BID_BY_ID = "UPDATE bid SET bidder_id=?, sum=?, time=?, auction_id=?" +
            " WHERE bid_id=?";
    public static final String DELETE_BID_BY_ID = "DELETE FROM bid WHERE bid_id=?";

    public static final String INSERT_PAYMENT = "INSERT INTO payment (payer_id, sum, lot_id, date) VALUES(?,?,?,?)";
    public static final String SELECT_ALL_PAYMENTS = "SELECT * FROM payment";
    public static final String SELECT_PAYMENT_BY_ID = "SELECT * FROM payment WHERE payment_id=?";
    public static final String SELECT_PAYMENT_BY_PAYER_ID = "SELECT * FROM payment WHERE payer_id=?";
    public static final String SELECT_PAYMENT_BY_PAYER_ID_WITH_LIMIT_AND_OFFSET = "SELECT * FROM payment" +
            " WHERE payer_id=? LIMIT ? OFFSET ?";
    public static final String UPDATE_PAYMENT_BY_ID = "UPDATE payment SET payer_id=?, sum=?, lot_id=?, date=?" +
            " WHERE payment_id=?";
    public static final String DELETE_PAYMENT_BY_ID = "DELETE FROM payment WHERE payment_id=?";

    public static final String INSERT_CREDIT = "INSERT INTO credit (percent, end_date, balance, sum, borrower_id)" +
            " VALUES(?,?,?,?,?)";
    public static final String SELECT_CREDIT_BY_ID = "SELECT * FROM credit WHERE credit_id=?";
    public static final String SELECT_CREDIT_BY_BORROWER_ID = "SELECT * FROM credit WHERE borrower_id=?";
    public static final String SELECT_CREDIT_BY_BORROWER_ID_WITH_LIMIT_AND_OFFSET = "SELECT * FROM credit" +
            " WHERE borrower_id=? ORDER BY credit_id DESC LIMIT ? OFFSET ?";
    public static final String SELECT_ALL_CREDITS = "SELECT * FROM credit";
    public static final String UPDATE_CREDIT_BY_ID = "UPDATE credit SET percent=?, end_date=?, balance=?, sum=?," +
            " borrower_id=? WHERE credit_id=?";
    public static final String UPDATE_CREDIT_BALANCE = "UPDATE credit SET balance=? WHERE credit_id=?";
    public static final String DELETE_CREDIT_BY_ID = "DELETE FROM credit WHERE credit_id=?";
}
