package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class User {
    private int userId;
    private String login;
    private String password;
    private UserRole role;
    private String email;
    private BigDecimal balance;
    private boolean blocked;
    private List<Bid> bids;
    private List<Lot> lots;
    private List<Payment> payments;
    private List<Credit> credits;

    public User(int userId, String login, String password, UserRole role, String email, BigDecimal balance,
                boolean blocked, List<Bid> bids, List<Lot> lots, List<Payment> payments, List<Credit> credits) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.balance = balance;
        this.blocked = blocked;
        this.bids = bids;
        this.lots = lots;
        this.payments = payments;
        this.credits = credits;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                blocked == user.blocked &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                role == user.role &&
                email.equals(user.email) &&
                balance.equals(user.balance) &&
                bids.equals(user.bids) &&
                lots.equals(user.lots) &&
                payments.equals(user.payments) &&
                credits.equals(user.credits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password, role, email, balance, blocked, bids, lots, payments, credits);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", blocked=" + blocked +
                ", bids=" + bids +
                ", lots=" + lots +
                ", payments=" + payments +
                ", credits=" + credits +
                '}';
    }
}
