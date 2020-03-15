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
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{

    RecyclerView mRecyclerView;
    private List<Model> mModelList;
    private List<Model> modelListSave;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelListSave = new ArrayList<>();
        mModelList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new RecyclerViewAdapter(this, getListData(), this);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,4);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private List<Model> getListData() {
        for (int i = 1; i <= 24; i++) {
            mModelList.add(new Model(R.drawable.instagram_48px));
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
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // sign in the user ...
                            Toast.makeText(MainActivity.this, "in dialog", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

