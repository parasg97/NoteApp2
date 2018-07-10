package com.persi.noteapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static android.app.Notification.VISIBILITY_PUBLIC;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText mUserName;
    private TextInputEditText mPassword;
    private String mPref="UserDetail";
    private String CHANNEL_ID="1";
    private int notificationId=20;


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
            //setting up special activity which is not a part of regular UX flow
            //PendingIntent pendingIntent =goToNewActivity();
            //this code is for accesing regular class from notification
            //PendingIntent pendingIntent=goToregularActivity();
            PendingIntent pendingIntent=addButtonNotification();
            createNotificationChannel();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.btn_star_big_on)
                    .setContentTitle("Login details")
                    .setContentText("Login Is Successful")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setVisibility(VISIBILITY_PUBLIC)
                    .addAction(android.R.drawable.btn_plus, "snoo",
                    pendingIntent);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mBuilder.build());
            Intent newIntent = new Intent(LoginActivity.this, ToDoList.class);
            finish();
            startActivity(newIntent);
        }

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Test Channel";
            String description = "Channel for testing";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public PendingIntent goToNewActivity(){
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        return pendingIntent;
    }

    public PendingIntent goToregularActivity(){
        Intent intent=new Intent(this,ToDoList.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public PendingIntent addButtonNotification(){
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        String ACTION_BUTTON="doSomething";
        intent.setAction(ACTION_BUTTON);
        intent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, intent, 0);
        return snoozePendingIntent;

    }
}
