package com.example.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.fragments.DialogsFragment.DialogsFragment;


public class MessagesHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_messages_history);
        final Intent intent = getIntent();
        final int id = intent.getIntExtra(DialogsFragment.HISTORY_ID, 0);
        Toast.makeText(this, String.valueOf(id),Toast.LENGTH_SHORT).show();
    }
}
