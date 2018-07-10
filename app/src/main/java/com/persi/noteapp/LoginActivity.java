package com.persi.noteapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NotificationCompat;
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
            /*Intent intent = new Intent(this, ToDoList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            createNotificationChannel();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.btn_star_big_on)
                    .setContentTitle("Notification")
                    .setContentText("Test Notification")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);*/
            Intent newIntent = new Intent(LoginActivity.this, ToDoList.class);
            finish();
            startActivity(newIntent);
        }

    }

    /*private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }*/
}
