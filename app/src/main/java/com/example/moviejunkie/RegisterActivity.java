package com.example.moviejunkie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviejunkie.SqlLite.AppDatabase;
import com.example.moviejunkie.SqlLite.MovieDAO;
import com.example.moviejunkie.SqlLite.UserSQL;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private TextInputEditText UsernameRegisterInput;
    private TextInputLayout UsernameRegisterLayout;
    private TextInputEditText PasswordRegister1Input;
    private TextInputLayout PasswordRegister1Layout;
    private TextInputEditText PasswordRegister2Input;
    private TextInputLayout PasswordRegister2Layout;

    private Button Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        progressBar=findViewById(R.id.RegisterProgress);

        UsernameRegisterInput=findViewById(R.id.RegisterUsernameInput);
        UsernameRegisterLayout=findViewById(R.id.RegisterUsernameLayout);

        PasswordRegister1Input=findViewById(R.id.RegiserPassword1Input);
        PasswordRegister1Layout=findViewById(R.id.RegiserPassword1Layout);


        PasswordRegister2Input=findViewById(R.id.RegiserPassword2Input);
        PasswordRegister2Layout=findViewById(R.id.RegiserPassword2Layout);

        Register=findViewById(R.id.RegisterButton);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_register(v);
            }
        });

    }

    private void do_register(final View v) {
        Boolean IsOk = true;

        if (UsernameRegisterInput.getText() == null || UsernameRegisterInput.getText().toString().isEmpty()) {
            IsOk = false;
            UsernameRegisterInput.setError("Username required");
        }

        if (PasswordRegister1Input.getText().toString().length() < 4) {
            IsOk = false;
            PasswordRegister1Input.setError("Username must be at least 4 characters long");
        }

        if (PasswordRegister1Input.getText() == null || PasswordRegister1Input.getText().toString().isEmpty()) {
            IsOk = false;
            PasswordRegister1Input.setError("Password required");
        }

        if (PasswordRegister2Input.getText().toString().length() < 4) {
            IsOk = false;
            PasswordRegister2Input.setError("Username must be at least 4 characters long");
        }

        if (PasswordRegister2Input.getText() == null || PasswordRegister2Input.getText().toString().isEmpty()) {
            IsOk = false;
            PasswordRegister2Input.setError("Password required");
        }

        if (!PasswordRegister2Input.getText().toString().equals(PasswordRegister1Input.getText().toString())){
            IsOk=false;
            PasswordRegister1Input.setError("Passwords are not the same");
            PasswordRegister2Input.setError("Passwords are not the same");

        }



        if (IsOk) {

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });

                    AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                    MovieDAO dao = appDatabase.getMovieDAO();
                    UserSQL userSQL=dao.CheckIfNameTaken(UsernameRegisterInput.getText().toString());


                    try {
                        Thread.sleep(4000);
//                      wait(4000);
                    } catch (InterruptedException e) {
                        Log.d("LOGIN", "run: " + e.getMessage());
                    }

                    if (userSQL!=null){
                        Snackbar.make(v, "Username is all ready taken", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(getResources().getColor(R.color.colorGrey3))
                                .setTextColor(getResources().getColor(R.color.colorOnBackground))
                                .show();
                    }else{

                        UserSQL userSQL1=new UserSQL(UsernameRegisterInput.getText().toString(),PasswordRegister1Input.getText().toString(),1);
                        dao.RegisterUser(userSQL1);


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                });



                            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                    }



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });




                }
            });
        }


    }
}
