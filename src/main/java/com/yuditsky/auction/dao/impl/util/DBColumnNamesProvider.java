package com.yuditsky.auction.dao.impl.util;

public class DBColumnNamesProvider {
    //auction
    public static final String AUCTION_ID = "auction_id";
    public static final String AUCTION_TYPE = "auction_type";
    public static final String STATUS = "status";
    public static final String WINNER_ID = "winner_id";

    //bid
    public static final String BID_ID = "bid_id";
    public static final String BIDDER_ID = "bidder_id";
    public static final String SUM = "sum";
    public static final String TIME = "time";

    //credit
    public static final String CREDIT_ID = "credit_id";
    public static final String PERCENT = "percent";
    public static final String END_DATE = "end_date";
    public static final String BALANCE = "balance";
    public static final String BORROWER_ID = "borrower_id";

    //lot
    public static final String LOT_ID = "lot_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String LOCATION = "location";
    public static final String START_PRICE = "start_price";
    public static final String OWNER_ID = "owner_id";

    //payment
    public static final String PAYMENT_ID = "payment_id";
    public static final String PAYER_ID = "payer_id";
    public static final String DATE = "date";

    //user
    public static final String USER_ID = "user_id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String ROLE = "role";
    public static final String BLOCKED = "blocked";


}
