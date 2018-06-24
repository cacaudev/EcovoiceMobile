package com.example.cacau2.ecovoicetest;

import java.io.Serializable;

/**
 * Created by Cacau2 on 20/06/2018.
 */

public class Data implements Serializable {
    public User user;
    public String auth_token;

    public User getUser() {
        return user;
    }

    public String getAuth_token() {
        return auth_token;
    }
}
