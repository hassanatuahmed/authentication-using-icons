package com.example.grid_layout;

public class Model {
    private int img;
    private boolean isSelected = false;

    public Model(int img) {
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
