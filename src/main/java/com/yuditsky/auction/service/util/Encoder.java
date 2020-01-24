package com.yuditsky.auction.service.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Encoder {
    public String encode(String string){
        String md5Hex = DigestUtils.md5Hex(string);
        return md5Hex;
    }
}
