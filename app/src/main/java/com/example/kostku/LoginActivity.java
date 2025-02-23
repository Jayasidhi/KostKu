package com.example.kostku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private boolean isErrorField = false;

    private boolean validateFieldLength(String string) {
        if (string == null || string.length() < 1) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.usernameEditText);
        EditText password = findViewById(R.id.passwordEditText);
        TextView loginError = findViewById(R.id.loginErrorLabel);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                loginButton.setBackgroundResource(R.drawable.);

                boolean isPassed = validateLoginField(username.getText().toString(), password.getText().toString(), loginError);
                if(isPassed){
                    Intent intent = new Intent(LoginActivity.this, ChooseKostActivity.class);
                    startActivity(intent);
                }


            }
        });
    }

    private boolean validateLoginField(String username, String password, TextView textView) {
        if (validateFieldLength(username)) {
            displayLoginErrorText(textView, "Username must be filled", true);
            return false;
        }

        if (validateFieldLength(password)) {
            displayLoginErrorText(textView, "Password must be filled", true);
            return false;
        }
        displayLoginErrorText(textView, "", false);
        return true;
    }

    private void displayLoginErrorText(TextView textView, String errorMessage, boolean isVisible) {
        textView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        textView.setText(errorMessage);
    }

}
