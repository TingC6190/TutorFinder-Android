package com.example.tingc6190.tutorfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.DataObject.Student;
import com.example.tingc6190.tutorfinder.Welcome.Welcome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button registerButton;
    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private String email;
    private EditText firstName_et;
    private EditText lastName_et;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
//
//        if(firebaseAuth.getCurrentUser() != null)
//        {
//            //launch home screen
//            finish();
//            startActivity(new Intent(this, HomeActivity.class));
//        }

        progressDialog = new ProgressDialog(this);

        emailField = findViewById(R.id.email_register);
        passwordField = findViewById(R.id.password_register);
        confirmPasswordField = findViewById(R.id.confirm_password_register);
        firstName_et = findViewById(R.id.first_name_register);
        lastName_et = findViewById(R.id.last_name_register);


        registerButton = findViewById(R.id.register_to_home_button);
        registerButton.setOnClickListener(this);
    }


    //create our user
    private void createUser()
    {
        email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String confirmPassword = confirmPasswordField.getText().toString().trim();
        final String firstName = firstName_et.getText().toString().trim();
        final String lastName = lastName_et.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)
                || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName))
        {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        //make sure user confirms their password
        if (confirmPassword.equals(password))
        {
            progressDialog.setMessage("Registering Account...");
            progressDialog.show();

            //try to create a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful())
                            {
                                finish();

                                //create student user in the database
                                DatabaseReference studentUserRef = FirebaseDatabase.getInstance().getReference().child("users/students/" + firebaseAuth.getUid());

                                Student studentUser = new Student(firstName, lastName, email, " ", " ");
                                studentUserRef.setValue(studentUser);


                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                Toast.makeText(RegisterActivity.this, "Registered Successfully.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Register Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "Please make sure password fields match.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {

        if (v == registerButton)
        {
            createUser();
        }

    }

}
