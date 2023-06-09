package com.example.roamify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class signUp extends AppCompatActivity {
    private Button SignIn;
    private EditText name,age, email, pass;
    FirebaseAuth mAuth;
    ProgressBar pb;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(signUp.this, "User Already logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomeScreen.class));
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        SignIn = findViewById(R.id.reset_password);
        name = findViewById(R.id.fullName);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email_forgot);
        pass = findViewById(R.id.password);
        pb = findViewById(R.id.progressBar);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                String mail, ps, inputed_name, inputed_age;
                mail = String.valueOf(email.getText());
                ps = String.valueOf(pass.getText());
                inputed_name= String.valueOf(name.getText());
                inputed_age= String.valueOf(age.getText());

                if(TextUtils.isEmpty(mail)){
                    pb.setVisibility(View.GONE);
                    Toast.makeText(signUp.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(ps)){
                    pb.setVisibility(View.GONE);
                    Toast.makeText(signUp.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(inputed_name)){
                    pb.setVisibility(View.GONE);
                    Toast.makeText(signUp.this,"Enter Name",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(inputed_age)){
                    pb.setVisibility(View.GONE);
                    Toast.makeText(signUp.this,"Enter age",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(mail, ps)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pb.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(signUp.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();

                                   // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(inputed_name+"#"+inputed_age)
                                            //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                            .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("user name, age update", "User profile updated.");
                                                    }
                                                }
                                            });

                                    startActivity(new Intent(getApplicationContext(), Second_Page.class));
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(signUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}