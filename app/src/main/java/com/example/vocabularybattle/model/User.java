package com.example.vocabularybattle.model;
import java.io.Serializable;
public class User implements Serializable {
    public String uid;
    public String name;
    @SuppressWarnings("WeakerAccess")
    public String email;
    public boolean isAuthenticated;
   public boolean isNew, isCreated;

    public User() {}

   public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}