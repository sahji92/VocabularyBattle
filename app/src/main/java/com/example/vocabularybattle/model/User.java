package com.example.vocabularybattle.model;
import java.io.Serializable;
public class User implements Serializable {
    public String uid;
    public String name;
    public String username;
    @SuppressWarnings("WeakerAccess")
    public String email;
    public boolean isAuthenticated;
   public boolean isNew, isCreated;
    public String password;
    public User() {}

   public User(String uid, String name, String email,String username) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.username=username;
    }
    public User(String username, String password){
        this.username=username;
        this.password=password;
    }

    public User(String uid, String email, String usrname) {
        this.uid=uid;
        this.username=usrname;
        this.email=email;
    }
}