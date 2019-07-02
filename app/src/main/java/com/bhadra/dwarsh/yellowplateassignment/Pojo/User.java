package com.bhadra.dwarsh.yellowplateassignment.Pojo;

public class User {
    String name,email,accesstoken;
    public User() {}
    public User(String name,String email,String accesstoken)
    {
        this.name = name;
        this.email = email;
        this.accesstoken = accesstoken;
    }

    public String getName() {
        return name;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
