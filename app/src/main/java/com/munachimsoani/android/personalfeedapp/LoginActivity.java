package com.munachimsoani.android.personalfeedapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.munachimsoani.android.personalfeedapp.R.menu.register_menu;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputLayout mEmailTextInputLayout;
    private TextInputLayout mPasswordTextInputLayout;

    // Firebase Instance Variable

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailTextInputLayout = findViewById(R.id.text_input_email);
        mPasswordTextInputLayout = findViewById(R.id.text_input_password);

        mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign In");


        // Get hold of the firebase instance variable
        mAuth = FirebaseAuth.getInstance();



    }

    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
        attemptRegistration();
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
            mEmailTextInputLayout.setError("Email cannot be empty");
            mEmailTextInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return  false;
        } else if(!emailInput.contains("@")){

            mEmailTextInputLayout.setError("Email is invalid");
            mEmailTextInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
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
            mPasswordTextInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));

            return  false;
        } else if(passwordInput.length() < 8){

            mPasswordTextInputLayout.setError("Password cannot be less than 8 characters");
            mPasswordTextInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));

            return  false;


        } else {

            mPasswordTextInputLayout.setError(null);
            return true;

        }
    }


    public void attemptRegistration(){
        if(!validateEmail() | !validatePassword()){
            return;
        } else {
            String email = mEmailTextInputLayout.getEditText().getText().toString();
            String password = mPasswordTextInputLayout.getEditText().getText().toString();

            Toast.makeText(LoginActivity.this,"Login Loading", Toast.LENGTH_SHORT).show();


            // Use FirebaseAuth to sign in with email & password

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){
                        showErrorDialog("There was a problem signing in!. Please check your login details");
                    } else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
            });
        }
    }




    // Alert Dialog if Login fails

    private void showErrorDialog(String message){

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}


