package com.example.tweet;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    EditText r_email, r_password, r_password2;
    Button sign_up;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        r_email = findViewById(R.id.r_Email);
        r_password = findViewById(R.id.r_Password);
        r_password2 = findViewById(R.id.r_Password2);
        sign_up = findViewById(R.id.sign_up);

        mAuth = FirebaseAuth.getInstance();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!r_email.getText().toString().isEmpty() && !r_password.getText().toString().isEmpty()) {
                   if(r_password.getText().toString().equals(r_password2.getText().toString())) {
                       callRegistration(r_email.getText().toString(), r_password.getText().toString());
                   } else{
                       Toast.makeText(RegistrationActivity.this, "Passwords don't match", Toast.LENGTH_LONG).show();
                   }
                } else {
                    Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void callRegistration(String email, String password){
        //firebase code for registration
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("$$$$", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification();
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("#$$$$$$", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }
}
