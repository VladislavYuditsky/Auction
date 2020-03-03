package com.yuditsky.auction.service.impl.util;

import org.mindrot.jbcrypt.BCrypt;

public class Encoder {
    private final String gensalt;

    public Encoder() {
        gensalt = BCrypt.gensalt();
    }

    public String encode(String password) {
        return BCrypt.hashpw(password, gensalt);
    }

    public boolean checkPassword(String password, String hashPassword) throws IllegalArgumentException {
        return BCrypt.checkpw(password, hashPassword);
    }
}
