package com.hobber89.androidOpenSpecificFileType;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ShowContentActivity extends AppCompatActivity {
    private EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);

        Intent intent = getIntent();
        FileContentModel content = intent.getParcelableExtra("content");

        contentEditText = findViewById(R.id.contentEditText);
        contentEditText.setText(content.getContent());
    }
}