package com.persi.noteapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText mUserName;
    private TextInputEditText mPassword;
    private String mPref="UserDetail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserName=(TextInputEditText)findViewById(R.id.user_login_page);
        mPassword=(TextInputEditText)findViewById(R.id.password_login_page);
    }

    //this method is called when login button is pressed
    public void LoginUser(View v){

        View focusView=null;
        boolean cancel=false;
        //to reset the error messages
        mUserName.setError(null);
        mPassword.setError(null);

        if (TextUtils.isEmpty(mUserName.getText().toString())){
            mUserName.setError("This field is required");
            focusView = mUserName;
            cancel=true;
        }
        if (TextUtils.isEmpty(mPassword.getText().toString())) {
            mPassword.setError("This field is required");
            focusView = mPassword;
            cancel=true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        else {

            //user name and password is saved in shared pref
            SharedPreferences.Editor editor = getSharedPreferences(mPref, MODE_PRIVATE).edit();
            editor.putString("name", mUserName.getText().toString());
            editor.putString("password", mPassword.getText().toString());
            editor.apply();
            Intent newIntent = new Intent(LoginActivity.this, ToDoList.class);
            finish();
            startActivity(newIntent);
        }

    }
}
