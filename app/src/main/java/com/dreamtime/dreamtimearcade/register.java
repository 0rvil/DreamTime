package com.dreamtime.dreamtimearcade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    EditText mFullName,mEmail,mPassword;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    ProgressBar progressBar;
    public static final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullName = findViewById(R.id.nameet);
        mEmail = findViewById(R.id.emailet);
        mPassword = findViewById(R.id.passwordet);
        mRegisterBtn = findViewById(R.id.registerbtn);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = mEmail.getText().toString().trim();
                String Password = mPassword.getText().toString().trim();
                final String FullName = mFullName.getText().toString();


                if(TextUtils.isEmpty(Email)){
                    mEmail.setError("An Email Address Is Required.");
                    return;
                } if (TextUtils.isEmpty(Password)){mPassword.setError("Password is required.");
                return;}
                if(Password.length()<6)
                {
                    mPassword.setError("Password must be 6 characters long");
                    return;
                }progressBar.setVisibility(View.VISIBLE);

                    fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(register.this,"User Created",Toast.LENGTH_SHORT).show();

                                userID = fAuth.getCurrentUser().getUid();


                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                final Map<String,Object> user = new HashMap<>();

                                user.put("Name",FullName);
                                user.put("Email",Email);
                                user.put("Coin_Toss_High_Score",0);
                                user.put("Rps_High_Score",0);
                                user.put("TicTacToe_High_Score",0);
                                user.put("High_Score",0);


                               documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"On Success: User Profile Created for"+ userID);
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                                finish();
                            }else{
                                Toast.makeText(register.this,"Error ! "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
            }
        });
    }

    public void privacy(View view) {
        Intent privacy = new Intent(Intent.ACTION_VIEW, Uri.parse("https://elivro.github.io/DreamTimePrivacyPolicy/"));
        startActivity(privacy);
    }
}
