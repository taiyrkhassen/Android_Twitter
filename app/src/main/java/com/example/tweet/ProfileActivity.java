package com.example.tweet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    ArrayList<Tweets> tweets;
    Custom_Adapter_Tweet adapter;
    TextView user_email;
    FloatingActionButton writeTw, exit;
    ListView lv;
    TextView email_user, status, day;
    DatabaseReference reference, reference1;
    String user_id;
    User user1;
    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init_Bar();



        tweets = new ArrayList<>();
        adapter = new Custom_Adapter_Tweet(this, tweets);

        lv = findViewById(R.id.Tweets_ListView);
        lv.setAdapter(adapter);

        writeTw = findViewById(R.id.write);
        exit = findViewById(R.id.exit);

        user_email = findViewById(R.id.user_name);
        mAuth = FirebaseAuth.getInstance();


        final FirebaseUser currrentUser = mAuth.getCurrentUser();


        reference1 = FirebaseDatabase.getInstance().getReference("users");

        user_id = currrentUser.getEmail().
                substring(0,currrentUser.getEmail().
                                length()-(currrentUser.getEmail().length()-4));

        email_user.setText(currrentUser.getEmail());
        day.setText(getDayOfWeek());

        writeNewUserStatus(user_id, "Broken...");

        status.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder statusBuilder = new AlertDialog.Builder(ProfileActivity.this);
                View stView = getLayoutInflater().inflate(R.layout.update_status, null);

                Button btStatus = stView.findViewById(R.id.update_status);
                final TextView statusText = stView.findViewById(R.id.write_status);

                statusBuilder.setView(stView);                    //MAKE ALERT DIALOG
                final AlertDialog dialog2 = statusBuilder.create();
                dialog2.show();
                btStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    if(!statusText.getText().toString().isEmpty()){
                       // DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("users");
                        status.setText(statusText.getText());
                        writeNewUserStatus(user_id, statusText.getText().toString());
                       // databaseReference2.push().setValue(statusText.getText());
                        dialog2.cancel();
                    } else{
                        Toast.makeText(ProfileActivity.this, "You should write something...", Toast.LENGTH_SHORT).show();
                    }

                    }
                });

                return false;
            }
        });




        reference = FirebaseDatabase.getInstance().getReference("Tweets");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tweets.clear();
                for (DataSnapshot var: dataSnapshot.getChildren()){
                    Tweets tw2 = var.getValue(Tweets.class);
                    tweets.add(0,tw2);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //User value = dataSnapshot.getValue(User.class);
                status.setText(dataSnapshot.child("users").child(user_id).getValue(User.class).getStatus());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //firebase code for signing out
                mAuth.signOut();
                finish();
            }
        });

        writeTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder tweetBuilder = new AlertDialog.Builder(ProfileActivity.this);

                View twView = getLayoutInflater().inflate(R.layout.write_tweet, null);
                final EditText twtext = twView.findViewById(R.id.tweet_text);
                Button btPost = twView.findViewById(R.id.post_tweet);

                tweetBuilder.setView(twView);                    //MAKE ALERT DIALOG
                final AlertDialog dialog = tweetBuilder.create();
                dialog.show();



                btPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Tweets tw = new Tweets();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();
                        if(!twtext.getText().toString().isEmpty()) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tweets");
                            tw.tweet = twtext.getText().toString();
                            tw.user_name = currrentUser.getEmail();
                            tw.date = dateFormat.format(date) + "";
                            databaseReference.push().setValue(tw);
                            //tweets.add(tw);
                            //adapter.notifyDataSetChanged();
                            dialog.cancel();
                        } else {
                            Toast.makeText(ProfileActivity.this, "You cant post empty tweet", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });


    }
    public void init_Bar(){
        email_user = findViewById(R.id.my_name);
        status = findViewById(R.id.status);
        day = findViewById(R.id.day_week);
    }

    public String getDayOfWeek(){
        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
        String day = simpleDateformat.format(now);
        return day;
    }
    private void writeNewUserStatus(String userId, String status) {
        User user = new User(status);

        reference1.child("users").child(userId).setValue(user);
    }

}
