package com.yuditsky.auction.dao.connection;

import java.util.ResourceBundle;

import static com.yuditsky.auction.Const.PROPERTIES_FILE_PATH;

public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTIES_FILE_PATH);

    public static DBResourceManager getInstance(){
        return instance;
    }

    public String getValue(String key){
        return resourceBundle.getString(key);
    }
}
