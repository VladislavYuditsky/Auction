package com.yuditsky.auction.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class User implements Identified {
    private int id;
    private String login;
    private String password;
    private UserRole role;
    private String email;
    private BigDecimal balance;
    private boolean blocked;

    public User(int id, String login, String password, UserRole role, String email, BigDecimal balance, boolean blocked) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.balance = balance;
        this.blocked = blocked;
    }

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                blocked == user.blocked &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                role == user.role &&
                email.equals(user.email) &&
                balance.equals(user.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, email, balance, blocked);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", blocked=" + blocked +
                '}';
    }
}
