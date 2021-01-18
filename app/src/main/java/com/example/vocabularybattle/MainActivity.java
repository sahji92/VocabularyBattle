package com.example.vocabularybattle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity{
    private TextInputEditText username,password,repeatPassword;
    String usernamePattern="[a-zA-Z]+[0-9]";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.signupUsername);
        password=findViewById(R.id.signupPassword);
        repeatPassword=findViewById(R.id.signupRepeatPassword);
    }
    public void hideKeyboardOnTouch(View view){
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private boolean validate() {
        boolean valid = true;

        String id = username.getText().toString();
        String pwd = password.getText().toString();
        String rPwd=repeatPassword.getText().toString();

        if (id.isEmpty() || id.length()<6||id.length()>16) {
            username.setError("id must contain atleast 6 and atmost 16 characters");
            valid = false;
        }
        else if(!id.trim().matches(usernamePattern)){
            username.setError("store id must be start with alphabets followed by numbers");
            valid = false;
        }
        else {
            username.setError(null);
        }
        if (pwd.isEmpty() || pwd.length() < 6 || pwd.length() > 16) {
            password.setError("password must contain atleast 6 and atmost 16 characters");
            valid = false;
        } else {
            password.setError(null);
        }
        if (rPwd.isEmpty() || rPwd.length() < 6 || rPwd.length() > 16) {
            repeatPassword.setError("password must contain atleast 6 and atmost 16 characters");
            valid = false;
        } else {
            repeatPassword.setError(null);
        }
        if (!pwd.equals(rPwd)) {
            repeatPassword.setError("password do not match");
            password.setError("password do not match");
            valid = false;
        } else {
            repeatPassword.setError(null);
        }
        return valid;
    }
}