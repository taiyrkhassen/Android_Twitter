package com.example.tweet;

public class Tweets {
    String user_name;
    String date;
    String tweet;
    public Tweets(){}

    public Tweets(String user_name, String date, String tweet){
        this.user_name = user_name;
        this.date = date;
        this.tweet = tweet;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getDate() {
        return date;
    }

    public String getTweet() {
        return tweet;
    }
}

