package com.pi.andrew.rpipower;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText text,text2;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        text = (EditText)findViewById(R.id.editText);
        text2 = (EditText)findViewById(R.id.editText2);
        bt = (Button)findViewById(R.id.button_config);
        final SQLBase sql = new SQLBase(this);
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ContentValues value = new ContentValues();
                value.put("username",text.getText().toString());
                value.put("password",text2.getText().toString());
                final SQLiteDatabase database  = sql.getWritableDatabase();
                database.update("logins",value,"",null);
                Toast.makeText(LoginActivity.this,"Config Success...",Toast.LENGTH_LONG).show();
                database.close();
            }
        });
    }
}
