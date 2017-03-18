package com.example.saber.filepersistencetest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.et);

        FileInputStream fis = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            fis = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            while((line = reader.readLine())!=null){
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        String inputText = sb.toString();
        if(!TextUtils.isEmpty(inputText)){
            editText.setText(sb.toString());
            editText.setSelection(inputText.length());
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    protected void onDestroy() {
        String inputText = editText.getText().toString();
        save(inputText);
        super.onDestroy();
    }

    private void save(String inputText) {
        FileOutputStream fos = null;
        BufferedWriter writer = null;

        try {
            fos = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
