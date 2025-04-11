package com.example.kostku;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kostku.model.Transaction;
import com.example.kostku.model.User;
import com.example.kostku.model.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private boolean isErrorField = false;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private UserSession userSession = UserSession.getInstance();

    private boolean validateFieldLength(String string) {
        if (string == null || string.length() < 1) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fetchDataFromFirebase();
        Log.d("fdatabase", "onDataChange: " + userSession.getUsername());
        Log.d("fdatabase", "onDataChange: " + userSession.getRole());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.usernameEditText);
        EditText password = findViewById(R.id.passwordEditText);
        TextView loginError = findViewById(R.id.loginErrorLabel);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isPassed = validateLoginField(username.getText().toString(), password.getText().toString(), loginError);
                if (isPassed) {
                    Intent intent;
                    if (userSession.getRole() == 0) {
                        intent = new Intent(LoginActivity.this, ChooseKostActivity.class);
                    } else {
                        intent = new Intent(LoginActivity.this, HomeActivity.class);
                    }
                    startActivity(intent);
                    finish();
                    Log.d("fdatabase", "onDataChange: username session: " + userSession.getUsername());
                    Log.d("fdatabase", "onDataChange: " + userSession.getRole());
                }


            }
        });

        TextView loginGuest = findViewById(R.id.guest_login);
        loginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ChooseKostActivity.class);
                startActivity(intent);
            }
        });


    }

    private void fetchDataFromFirebase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("user");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = new User(userSnapshot);
                    users.add(user);
                    Log.d("fdatabase", "onDataChange: " + user.getUsername());
                    Log.d("fdatabase", "onDataChange: " + user.getPassword());
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.d("fdatabase", "onDataChange: " + error.getMessage());
            }
        };
        mDatabase.addValueEventListener(postListener);

        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("transaction");
        ValueEventListener postTransactionListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot transactionSnapshot : snapshot.getChildren()) {
                    Transaction transaction = new Transaction(transactionSnapshot);
                    transactions.add(transaction);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.d("fdatabase", "onDataChange: " + error.getMessage());
            }
        };
        mDatabase.addValueEventListener(postTransactionListener);
    }

    private boolean validateLoginField(String username, String password, TextView textView) {
        if (validateFieldLength(username)) {
            displayLoginErrorText(textView, "Username must be filled");
            return false;
        }

        if (validateFieldLength(password)) {
            displayLoginErrorText(textView, "Password must be filled");
            return false;
        }

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    userSession.setUsername(user.getUsername());
                    userSession.setRole(user.getRole());
                    for (Transaction transaction : transactions) {
                        if (transaction.getPhone_number().equals(user.getUsername())) {
                            userSession.setIdKost(transaction.getKost_id());
                            break;
                        }
                    }
                    return true;
                } else {
                    displayLoginErrorText(textView, "Password false");
                    return false;
                }
            }
        }
        displayLoginErrorText(textView, "User does not Exist");
        return false;
    }

    private void displayLoginErrorText(TextView textView, String errorMessage) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(errorMessage);
    }

}
