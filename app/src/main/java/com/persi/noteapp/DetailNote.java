package com.persi.noteapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DetailNote extends AppCompatActivity {
    private TextView mUserId;
    private TextView mNoteId;
    private TextView mTitle;
    private TextView mStatus;
    private TextView mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            mUserId=findViewById(R.id.detail_user_id);
            mNoteId=findViewById(R.id.detail_note_id);
            mTitle=findViewById(R.id.detail_note);
            mStatus=findViewById(R.id.detail_status);
            mUserId.setText("User Id:"+Integer.toString(extras.getInt("userId")));
            mNoteId.setText("Note Id:"+Integer.toString(extras.getInt("noteId")));
            mTitle.setText("Title:"+extras.getString("title"));
            mStatus.setText("Status:"+Boolean.toString(extras.getBoolean("status")));

        }
        mUsername=findViewById(R.id.detail_user_name);
        SharedPreferences prefs = getSharedPreferences("UserDetail", MODE_PRIVATE);
        mUsername.setText(prefs.getString("name",null));
    }
}
