package com.example.tweet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom_Adapter_Tweet extends ArrayAdapter<Tweets>{
    ArrayList<Tweets> tweets;
    TextView user_name, date, tweet;

    public Custom_Adapter_Tweet(Context context,  ArrayList<Tweets> t) {
        super(context, R.layout.list_item_tweet);
        tweets = t;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View custom_view = inflater.inflate(R.layout.list_item_tweet,parent, false);

        user_name = custom_view.findViewById(R.id.user_name);
        date = custom_view.findViewById(R.id.date);
        tweet = custom_view.findViewById(R.id.tweet);

        user_name.setText(tweets.get(position).getUser_name());

        date.setText(tweets.get(position).getDate());

        tweet.setText(tweets.get(position).getTweet());

        return custom_view;
    }

    @Override
    public int getCount() {
        return tweets.size();
    }
}
