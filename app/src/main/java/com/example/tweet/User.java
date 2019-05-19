package com.example.tweet;
public class User {

    //public String username;
    public String status = "Broken....";

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User( String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}