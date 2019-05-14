package com.coffee_just.chatapp.bean;

import org.litepal.crud.LitePalSupport;

public class contactInfo extends LitePalSupport {
    public contactInfo(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String userName;
        private  String nick;//头像

    public contactInfo(String userName, String nick, String avatar) {
        this.userName = userName;
        this.nick = nick;
        this.avatar = avatar;
    }

    private String  avatar;//别名



}
