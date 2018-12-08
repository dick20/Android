package com.example2.asus.lab3;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEditorActivity  extends AppCompatActivity {

    final String FILE_NAME = "myFile";
    Button but_save, but_load, but_clear2;
    EditText editText;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);
        but_save = findViewById(R.id.save);
        but_load = findViewById(R.id.load);
        but_clear2 = findViewById(R.id.clear2);
        editText = findViewById(R.id.edit);

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                    String str = editText.getText().toString();
                    fileOutputStream.write(str.getBytes());
                    //弹出 Toast
                    Toast.makeText(FileEditorActivity.this, "Save successfully", Toast.LENGTH_SHORT).show();
                    Log.i("TAG", "Successfully saved file.");
                } catch (IOException ex) {
                    Log.e("TAG", "Fail to save file.");
                }
            }
        });

        but_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (FileInputStream fileInputStream = openFileInput(FILE_NAME)) {
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    String str = new String(contents,"UTF-8");
                    editText.setText(str);
                    //弹出 Toast
                    Toast.makeText(FileEditorActivity.this, "Load successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    //弹出 Toast
                    Toast.makeText(FileEditorActivity.this, "Fail to read file.", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "Fail to read file.");
                }
            }
        });
        but_clear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(FileEditorActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
