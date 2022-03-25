package com.example.notificationpart;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;


public class notification extends AppCompatActivity {

    Button UploadPage, ReadPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        findViews();
        setListeners();
    }

    private void findViews(){
        UploadPage = (Button) findViewById(R.id.btnupload);
        ReadPage = (Button) findViewById(R.id.btnread);
    }

    private void setListeners(){
        UploadPage.setOnClickListener(view -> {
            Intent intent = new Intent(notification.this,NotificationUpload.class);
            startActivity(intent);
        });
        ReadPage.setOnClickListener(view -> {
            Intent intent = new Intent(notification.this,NotificationRead.class);
            startActivity(intent);
        });
    }
}