package com.example.tingc6190.tutorfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tingc6190.tutorfinder.Welcome.Welcome;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button registerButton;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.login_button);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            //launch home screen
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == registerButton)
        {
            //launch register screen
            startActivity(new Intent(this, RegisterActivity.class));
        }
        if (v == loginButton)
        {
            //launch login screen
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
