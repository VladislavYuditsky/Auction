package com.yuditsky.auction.service.util;

import com.yuditsky.auction.entity.Auction;
import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yuditsky.auction.Const.NULL;

public class Validator {
    private Pattern loginPattern = Pattern.compile("^[a-zA-Z\\u0430-\\u044F\\u0410-\\u042F]{1}[a-zA-Z\\u0430-\\u044F\\u0410-\\u042F\\d\\u002E\\u005F]{3,10}$");
    private Pattern passwordPattern = Pattern.compile("^[a-zA-Z\\u0430-\\u044F\\u0410-\\u042F]{1}[a-zA-Z1-9\\u0430-\\u044F\\u0410-\\u042F]{5,20}$");
    private Pattern textPattern = Pattern.compile("^[a-zA-Z\\u0430-\\u044F\\u0410-\\u042F0-9 ]{1,1000}$");
    private Pattern emailPattern = Pattern.compile("^[a-zA-Z\\u0430-\\u044F\\u0410-\\u042F]{1}[a-zA-Z\\u0430-\\u044F\\u0410-\\u042F\\d\\u002E\\u005F]{3,10}$");
    private Pattern namePattern = Pattern.compile("^[a-zA-Z\\u0430-\\u044F\\u0410-\\u042F]{1}[a-zA-Z\\u0430-\\u044F\\u0410-\\u042F]{3,10}$");
    //drugoi pattern

    /*public boolean checkRating(float rating) {
        if (rating > 10 || rating < 0) {
            return false;
        } else {
            return true;
        }
    }*/

    public boolean checkLogin(String login) {
        return checkString(login, loginPattern);
    }

    public boolean checkPassword(String password) {
        return checkString(password, passwordPattern);
    }

    public boolean checkText(String text) {
        return checkString(text, textPattern);
    }

    public boolean checkName(String name){
        return checkString(name, namePattern);
    }

    public boolean checkEmail(String email){
        //return checkString(email, emailPattern);
        return true;
    }

    public boolean checkUser(User user) {
        return user != null && checkPassword(user.getPassword()) && checkEmail(user.getEmail())
                && checkLogin(user.getLogin());
    }

    public boolean checkBid(Bid bid){
        return bid != null && checkSum(bid.getSum());
    }

    public boolean checkLot(Lot lot){
        return lot != null && checkName(lot.getName()) && checkText(lot.getDescription())
                && checkName(lot.getLocation()) && checkSum(lot.getStartPrice());
    }

    public boolean checkSum(BigDecimal sum){
        return sum != null && sum.compareTo(NULL) > 0;
    }

    private boolean checkString(String stringForChecking, Pattern patternForChecking) {
        if (stringForChecking == null) {
            return false;
        }

        Matcher matcher = patternForChecking.matcher(stringForChecking);

        return matcher.matches();
    }
}
