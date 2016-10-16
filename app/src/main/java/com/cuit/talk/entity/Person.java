package com.cuit.talk.entity;

/**
 * Created by inori on 16/10/9.
 */

public class Person {
    //用户ID
    private int id;
    //账号
    private String number;
    //密码
    private String password;
    //昵称
    private String nickname;
    //真实姓名
    private String truename;
    //性别
    private String sex;
    //年龄
    private int age;
    //电话
    private String phone;
    //住址
    private String address;

    public Person(int id, String number, String password, String nickname, String truename, String sex, int age, String phone, String address) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.nickname = nickname;
        this.truename = truename;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.address = address;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
