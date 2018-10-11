package com.example.tingc6190.tutorfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginToHomeButton = findViewById(R.id.login_to_home_button);
        loginToHomeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == loginToHomeButton)
        {
            //launch home screen
            startActivity(new Intent(this, HomeActivity.class));
        }

    }
}
