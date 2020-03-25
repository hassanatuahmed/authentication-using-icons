package com.example.grid_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class LockScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.app_name), 0);
        String list = sharedPref.getString(getString(R.string.preference_file_key),"");
        if (list.isEmpty()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        Gson gson = new Gson();
        List<Model> modelListSave = gson.fromJson(list, new TypeToken<List<Model>>(){}.getType());
        Log.d("qqq", modelListSave.toString());
    }
}
