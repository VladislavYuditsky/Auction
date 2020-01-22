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
    private List<Payment> payments; //
    private List<Credit> credits; //
    private boolean isBlocked;

    public User(int userId, String login, String password, UserRole role, String email, BigDecimal balance,
                List<Payment> payments, List<Credit> credits, boolean isBlocked) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.balance = balance;
        this.payments = payments;
        this.credits = credits;
        this.isBlocked = isBlocked;
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

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                isBlocked == user.isBlocked &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                role == user.role &&
                email.equals(user.email) &&
                balance.equals(user.balance) &&
                payments.equals(user.payments) &&
                credits.equals(user.credits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password, role, email, balance, payments, credits, isBlocked);
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
                ", payments=" + payments +
                ", credits=" + credits +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
