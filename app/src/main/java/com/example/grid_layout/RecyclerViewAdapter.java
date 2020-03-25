package com.example.grid_layout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Model> mModelList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    RecyclerViewAdapter(Context context, List<Model> modelList, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        mModelList = modelList;
        this.mClickListener = mClickListener;
    }

    RecyclerViewAdapter(Context context, List<Model> modelList) {
        this.mInflater = LayoutInflater.from(context);
        mModelList = modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rc_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Model model = mModelList.get(position);
//        holder.imageBtn.setBackgroundResource(R.drawable.instagram_48px);
//        holder.imageBtn.setBackgroundResource(model.getImg());
        holder.imageBtn.setImageResource(model.getImg());
        holder.view.setBackgroundColor(model.isSelected() && mClickListener != null ? Color.CYAN : Color.WHITE);
        holder.imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null){
                    mClickListener.onItemClick(holder.view, position);
                }
                /*model.setSelected(!model.isSelected());
                holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public void setList(List<Model> m){
        mModelList = m;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private View view;
        private ImageButton imageBtn;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageBtn = itemView.findViewById(R.id.imageButton);
            view.setBackgroundColor(Color.WHITE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}
