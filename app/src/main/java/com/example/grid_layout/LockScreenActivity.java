package com.example.grid_layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LockScreenActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerView mRecyclerView;
    private List<Model> mModelList;
    private List<Model> modelListSave;
    private List<Model> modelListSaved;
    private RecyclerViewAdapter mAdapter;
    static int INF = 10000;

    LinearLayout l;
    View v1;
    View v2;
    View v3;
    int ccc = 0;
    int correctness = 0;
    Point[] polygon1;
    int[] originalPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);

        originalPos = new int[2];

        polygon1 = new Point[3];

        l = findViewById(R.id.l);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);

        resetV();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.app_name), 0);
        String list = sharedPref.getString(getString(R.string.preference_file_key),"");
        if (list.isEmpty()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        Gson gson = new Gson();
        modelListSaved = gson.fromJson(list, new TypeToken<List<Model>>(){}.getType());

        modelListSave = new ArrayList<>();
        mModelList = new ArrayList<>();
        setListData();

        mRecyclerView = findViewById(R.id.recycler_view_lock);
        mAdapter = new RecyclerViewAdapter(this, getListData(), this);
        GridLayoutManager manager = new GridLayoutManager(this,5);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

//        setPoint();

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

    private void setPoint(){
        String a = "";
        for (int i = 0; i < modelListSaved.size(); i++) {
            for (int j = 0; j < mModelList.size(); j++) {
                if (modelListSaved.get(i).getImg() == mModelList.get(j).getImg()){
                    a += j+"-";
                    Objects.requireNonNull(mRecyclerView.findViewHolderForAdapterPosition(j)).itemView.getLocationInWindow(originalPos);
                    polygon1[i] = new Point(originalPos[0], originalPos[1]);
                }
            }
        }
//        Toast.makeText(this, a, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        setPoint();
        view.getLocationInWindow(originalPos);
        l.setVisibility(View.VISIBLE);
        ccc += 1;
        if (isInside(polygon1, polygon1.length, new Point(originalPos[0], originalPos[1])))
        {
            chv(true);
//            Toast.makeText(this, "You clicked  position " + originalPos[0]+ ", "+ originalPos[1], Toast.LENGTH_LONG).show();
        }
        else {
            chv(false);
        }

    }

    private void chv(Boolean c) {
        mAdapter.setList(getListData());
        mRecyclerView.setAdapter(mAdapter);
//        setPoint();
        if(ccc == 1){
            v1.setVisibility(View.VISIBLE);
            if (c){
                v1.setBackground(getResources().getDrawable(R.color.green));
                correctness++;
            }
            else {
                v1.setBackground(getResources().getDrawable(R.color.red));
            }
        }
        else if(ccc == 2){
            v2.setVisibility(View.VISIBLE);
            if (c){
                v2.setBackground(getResources().getDrawable(R.color.green));
                correctness++;
            }
            else {
                v2.setBackground(getResources().getDrawable(R.color.red));
            }
        }
        else if(ccc == 3){
            v3.setVisibility(View.VISIBLE);
            if (c){
                v3.setBackground(getResources().getDrawable(R.color.green));
                correctness++;
            }
            else {
                v3.setBackground(getResources().getDrawable(R.color.red));
            }
            resetV();
        }
    }

    private void resetV() {
        ccc = 0;
        l.setVisibility(View.GONE);
        v1.setVisibility(View.INVISIBLE);
        v1.setBackground(getResources().getDrawable(R.color.colorAccent));
        v2.setVisibility(View.INVISIBLE);
        v2.setBackground(getResources().getDrawable(R.color.colorPrimary));
        v3.setVisibility(View.INVISIBLE);
        v2.setBackground(getResources().getDrawable(R.color.cardview_dark_background));
        if (correctness == 3){
            finish();
        }
        else {
            correctness = 0;
        }
    }

    static class Point
    {
        int x;
        int y;

        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    };

    // Given three colinear points p, q, r,
    // the function checks if point q lies
    // on line segment 'pr'
    static boolean onSegment(Point p, Point q, Point r)
    {
        if (q.x <= Math.max(p.x, r.x) &&
                q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) &&
                q.y >= Math.min(p.y, r.y))
        {
            return true;
        }
        return false;
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are colinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int orientation(Point p, Point q, Point r)
    {
        int val = (q.y - p.y) * (r.x - q.x)
                - (q.x - p.x) * (r.y - q.y);

        if (val == 0)
        {
            return 0; // colinear
        }
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    // The function that returns true if
    // line segment 'p1q1' and 'p2q2' intersect.
    static boolean doIntersect(Point p1, Point q1,
                               Point p2, Point q2)
    {
        // Find the four orientations needed for
        // general and special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
        {
            return true;
        }

        // Special Cases
        // p1, q1 and p2 are colinear and
        // p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1))
        {
            return true;
        }

        // p1, q1 and p2 are colinear and
        // q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1))
        {
            return true;
        }

        // p2, q2 and p1 are colinear and
        // p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2))
        {
            return true;
        }

        // p2, q2 and q1 are colinear and
        // q1 lies on segment p2q2
        if (o4 == 0 && onSegment(p2, q1, q2))
        {
            return true;
        }

        // Doesn't fall in any of the above cases
        return false;
    }

    // Returns true if the point p lies
    // inside the polygon[] with n vertices
    static boolean isInside(Point polygon[], int n, Point p)
    {
        // There must be at least 3 vertices in polygon[]
        if (n < 3)
        {
            return false;
        }

        // Create a point for line segment from p to infinite
        Point extreme = new Point(INF, p.y);

        // Count intersections of the above line
        // with sides of polygon
        int count = 0, i = 0;
        do
        {
            int next = (i + 1) % n;

            // Check if the line segment from 'p' to
            // 'extreme' intersects with the line
            // segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon[i], polygon[next], p, extreme))
            {
                // If the point 'p' is colinear with line
                // segment 'i-next', then check if it lies
                // on segment. If it lies, return true, otherwise false
                if (orientation(polygon[i], p, polygon[next]) == 0)
                {
                    return onSegment(polygon[i], p,
                            polygon[next]);
                }

                count++;
            }
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return (count % 2 == 1); // Same as (count%2 == 1)
    }


}
