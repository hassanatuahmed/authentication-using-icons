package com.example.grid_layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener{

    RecyclerView mRecyclerView;
    private List<Model> mModelList;
    private List<Model> modelListSave;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modelListSave = new ArrayList<>();
        mModelList = new ArrayList<>();
        setListData();
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new RecyclerViewAdapter(this, getListData(), this);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,4);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private List<Model> getListData() {

        Collections.shuffle(mModelList);
        return mModelList;
    }

    private void setListData(){
        TypedArray im = getResources().obtainTypedArray(R.array.images);
        for (int i = 0; i < im.length(); i++) {
            mModelList.add(new Model(im.getResourceId(i, -1)));
        }
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
                        modelListSave.clear();
                        for (Model m :
                                mModelList) {
                            if (m.isSelected()) {
                                m.setSelected(false);
                            }
                        }
//                        mAdapter = new RecyclerViewAdapter(MainActivity.this, getListData(), MainActivity.this);
                        mAdapter.setList(getListData());
                        mRecyclerView.setAdapter(mAdapter);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this, "in dialog", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.app_name), 0);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        Gson gson = new Gson();
                        String list = gson.toJson(modelListSave);
                        editor.putString(getString(R.string.preference_file_key), list);

                        editor.apply();
                        startActivity(new Intent(MainActivity.this,LockScreenActivity.class));
                        finish();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

