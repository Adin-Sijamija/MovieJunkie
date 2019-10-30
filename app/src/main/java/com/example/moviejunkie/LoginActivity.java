package com.example.moviejunkie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.tech.IsoDep;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviejunkie.Classes.Movie;
import com.example.moviejunkie.SqlLite.AppDatabase;
import com.example.moviejunkie.SqlLite.MovieDAO;
import com.example.moviejunkie.SqlLite.MovieSQL;
import com.example.moviejunkie.SqlLite.UserSQL;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private TextInputEditText UsernameInput;
    private TextInputLayout UsernameLayout;
    private TextInputEditText PasswordInput;
    private TextInputLayout PasswordLayout;
    private TextView Register;

    private Button LogIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                MovieDAO dao = appDatabase.getMovieDAO();
                dao.ResetAllLoggedUsers();
                Log.d("LOGIN", "run: users reset ");

            }
        });


        progressBar = findViewById(R.id.progressBar);
        UsernameLayout = findViewById(R.id.UsernameLayout);
        UsernameInput = findViewById(R.id.UserNameInput);

        PasswordLayout = findViewById(R.id.PasswordLayout);
        PasswordInput = findViewById(R.id.PasswordInput);

        Register = findViewById(R.id.RegisterTextView);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        LogIn = findViewById(R.id.LoginButton);
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_tryLogin(v);
            }
        });
    }

    private void do_tryLogin(final View v) {
        Boolean IsOk = true;

        if (UsernameInput.getText() == null || UsernameInput.getText().toString().isEmpty()) {
            IsOk = false;
            UsernameInput.setError("Username required");
        }

        if (UsernameInput.getText().toString().length() < 4) {
            IsOk = false;
            UsernameInput.setError("Username must be at least 4 characters long");
        }

        if (PasswordInput.getText() == null || PasswordInput.getText().toString().isEmpty()) {
            IsOk = false;
            PasswordInput.setError("Password required");
        }

        if (PasswordInput.getText().toString().length() < 5) {
            IsOk = false;
            PasswordInput.setError("Password must be at least 5 characters long");
        }
        if (IsOk) {

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {


                    Boolean IsOk = false;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });

                    AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                    MovieDAO dao = appDatabase.getMovieDAO();
                    int LoginStatus = dao.LogInUser(UsernameInput.getText().toString(), PasswordInput.getText().toString());

                    if (LoginStatus == 1) {
                        UserSQL userSQL = dao.GetCurrentUser();
                        Log.d("LOGIN", "curenet usser::username..." + userSQL.getUser_name() + "...password..." + userSQL.getUser_password());
                        IsOk = true;

                    }

                    try {
                        Thread.sleep(4000);
//                      wait(4000);
                    } catch (InterruptedException e) {
                        Log.d("LOGIN", "run: " + e.getMessage());
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                    if (IsOk){
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else{

                        Snackbar.make(v, "User not found! ", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(getResources().getColor(R.color.colorGrey3))
                                .setTextColor(getResources().getColor(R.color.colorOnBackground))
                                .show();
                    }

                }
            });
        }


    }


}


