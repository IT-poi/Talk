package com.cuit.talk.entity;

import java.io.Serializable;

/**
 * Created by rice on 16-10-15.
 */
public class PersonSimple implements Serializable{
    private String number;
    private String password;

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public String getPassword() {
        return password;
    }
}
