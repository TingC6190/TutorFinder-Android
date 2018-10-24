package com.example.tingc6190.tutorfinder.Setting;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tingc6190.tutorfinder.Account.Account;
import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.TutorForm.TutorFormInitial;

public class Password extends Fragment {

    private EditText confirmEmail_et;
    private Button sendPasswordResetEmailButton;
    String email;
    HomeActivity homeActivity;

    public Password() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();

        email = homeActivity.getStudentEmail();

        return inflater.inflate(R.layout.content_change_password_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            confirmEmail_et = getView().findViewById(R.id.password_reset_email);
            sendPasswordResetEmailButton = getView().findViewById(R.id.send_password_reset_email_button);

            sendPasswordResetEmailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String confirmEmail = confirmEmail_et.getText().toString().trim();

                    if (!TextUtils.isEmpty(confirmEmail))
                    {
                        if (confirmEmail.equals(email))
                        {
                            homeActivity.sendPasswordResetEmail();

                            getFragmentManager().beginTransaction()
                                    .replace(R.id.content_container, new Account())
                                    .commit();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Please make sure email matches the one for this account.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Please confirm your current email.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
