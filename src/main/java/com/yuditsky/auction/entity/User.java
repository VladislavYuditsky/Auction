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
    private List<Integer> bidIds;
    private List<Integer> lotIds;
    //private List<Payment> payments;
    //private List<Credit> credits;

    public User(int userId, String login, String password, UserRole role, String email, BigDecimal balance,
                boolean blocked, List<Integer> bidIds, List<Integer> lotIds) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.balance = balance;
        this.blocked = blocked;
        this.bidIds = bidIds;
        this.lotIds = lotIds;
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

    public List<Integer> getBidIds() {
        return bidIds;
    }

    public void setBidIds(List<Integer> bidIds) {
        this.bidIds = bidIds;
    }

    public List<Integer> getLotIds() {
        return lotIds;
    }

    public void setLotIds(List<Integer> lotIds) {
        this.lotIds = lotIds;
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
                bidIds.equals(user.bidIds) &&
                lotIds.equals(user.lotIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password, role, email, balance, blocked, bidIds, lotIds);
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
                ", bidIds=" + bidIds +
                ", lotIds=" + lotIds +
                '}';
    }
}
