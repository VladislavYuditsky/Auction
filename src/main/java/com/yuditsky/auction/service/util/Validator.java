package com.yuditsky.auction.service.util;

import com.yuditsky.auction.entity.Bid;
import com.yuditsky.auction.entity.Lot;
import com.yuditsky.auction.entity.User;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yuditsky.auction.Const.NULL;

public class Validator {
    private Pattern loginPattern = Pattern.compile("^[a-zA-Z1-9]{3,10}$");
    private Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,10}$");
    private Pattern emailPattern = Pattern.compile("^\\S+@\\S+\\.\\S+$");

    public boolean checkLogin(String login) {
        return checkString(login, loginPattern);
    }

    public boolean checkPassword(String password) {
        return checkString(password, passwordPattern);
    }

    public boolean checkText(String text) {
        return text != null && !text.equals("");
    }

    public boolean checkEmail(String email){
        return checkString(email, emailPattern);
    }

    public boolean checkUser(User user) {
        return user != null && checkPassword(user.getPassword()) && checkEmail(user.getEmail())
                && checkLogin(user.getLogin());
    }

    public boolean checkBid(Bid bid){
        return bid != null && checkBigDecimal(bid.getSum());
    }

    public boolean checkLot(Lot lot){
        return lot != null && checkText(lot.getName()) && checkText(lot.getDescription())
                && checkText(lot.getLocation()) && checkBigDecimal(lot.getStartPrice());
    }

    public boolean checkBigDecimal(BigDecimal value){
        return value != null && value.compareTo(NULL) > 0;
    }

    private boolean checkString(String stringForChecking, Pattern patternForChecking) {
        if (stringForChecking == null) {
            return false;
        }

        Matcher matcher = patternForChecking.matcher(stringForChecking);

        return matcher.matches();
    }

}
