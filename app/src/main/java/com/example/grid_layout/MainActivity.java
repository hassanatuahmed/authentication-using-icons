package com.example.grid_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//tgshbgsudggsghh

    }

    public void onImageClicked(View view) {
        Toast.makeText(MainActivity.this, "item selected", Toast.LENGTH_LONG).show();
//        Log.v(TAG, " click");
    }

    }

