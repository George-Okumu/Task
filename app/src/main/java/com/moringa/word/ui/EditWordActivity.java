package com.moringa.word.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.moringa.word.R;

public class EditWordActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        editText = findViewById(R.id.edit_word);
        button = findViewById(R.id.button_save);

        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == button){
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(editText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = editText.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, word);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        }

    }
}