package com.munachimsoani.android.personalfeedapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import static com.munachimsoani.android.personalfeedapp.R.menu.register_menu;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputLayout mEmailTextInputLayout;
    private TextInputLayout mPasswordTextInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailTextInputLayout = findViewById(R.id.text_input_email);
        mPasswordTextInputLayout = findViewById(R.id.text_input_password);

        mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign In");


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(register_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.user_register:
                Intent intent = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //    Validate Email

    private boolean validateEmail(){
        String emailInput = mEmailTextInputLayout.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()){
            mEmailTextInputLayout.setError("Email  cannot be empty");
            return  false;
        } else {

            mEmailTextInputLayout.setError(null);
            return true;

        }

    }

//    Validate Password

    private boolean validatePassword(){
        String passwordInput = mPasswordTextInputLayout.getEditText().getText().toString().trim();

        if(passwordInput.isEmpty()){
            mPasswordTextInputLayout.setError("Password  cannot be empty");
            return  false;
        } else {

            mPasswordTextInputLayout.setError(null);
            return true;

        }
    }





}


