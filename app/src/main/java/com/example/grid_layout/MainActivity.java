package com.example.grid_layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dunst.check.CheckableImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{

    RecyclerView mRecyclerView;
    private List<Model> mModelList;
    private List<Model> modelListSave;
    private RecyclerView.Adapter mAdapter;
    private int[] imgList = {R.drawable.instagram_48px, R.drawable.add_user_male_48px,
            R.drawable.adobe_photoshop_48px,
            R.drawable.cash_in_hand_48px, R.drawable.gears_48px,
            R.drawable.gift_48px, R.drawable.gmail_48px,
            R.drawable.google_plus_48px,
            R.drawable.java_coffee_cup_logo_48px, R.drawable.minimize_window_48px,
            R.drawable.minus_48px, R.drawable.money_bag_48px,
            R.drawable.musical_notes_48px, R.drawable.netflix_48px,
            R.drawable.password_48px, R.drawable.pdf_2_48px,
            R.drawable.pill_48px, R.drawable.raspberry_pi_48px,
            R.drawable.rubiks_cube_48px,
            R.drawable.school_48px, R.drawable.shutdown_48px,
            R.drawable.stethoscope_48px, R.drawable.trust_48px,
            R.drawable.twitter_48px, R.drawable.truck_48px, };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelListSave = new ArrayList<>();
        mModelList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new RecyclerViewAdapter(this, getListData(), this);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,5);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private List<Model> getListData() {
        for (int i = 0; i < imgList.length; i++) {
            mModelList.add(new Model(imgList[i]));
        }
        return mModelList;
    }

    @Override
    public void onItemClick(View view, int position) {
        Model model = mModelList.get(position);
        model.setSelected(!model.isSelected());
        view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
        if (model.isSelected()){
            modelListSave.add(model);
        } else {
            modelListSave.remove(model);
        }

        if (modelListSave.size() == 3){
            showDialog(modelListSave);
        }
//        Toast.makeText(MainActivity.this, "You clicked number , which is at cell position " + position, Toast.LENGTH_LONG).show();
    }

    private void showDialog(final List<Model> list){
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_save, viewGroup, false);

        RecyclerView rc = dialogView.findViewById(R.id.dialog_rcview);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,4);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(manager);
        rc.setAdapter(new RecyclerViewAdapter(this, list));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(dialogView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
<<<<<<< HEAD
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // sign in the user ...
                            Toast.makeText(MainActivity.this, "in dialog", Toast.LENGTH_SHORT).show();
                            // writing intent here

                    Intent intent = new Intent(MainActivity.this,ConvexActivity.class);
                    startActivity(intent);

                    // Collections.shuffle(imgList< >);




                }
=======
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(MainActivity.this, "in dialog", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.app_name), 0);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        Gson gson = new Gson();
                        String list = gson.toJson(modelListSave);
                        editor.putString(getString(R.string.preference_file_key), list);

                        editor.apply();
                        startActivity(new Intent(MainActivity.this,LockScreenActivity.class));
                        finish();
                    }
>>>>>>> dev
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

