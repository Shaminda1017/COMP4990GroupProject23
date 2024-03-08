package com.example.a1;

public class MyDataSet {

    public String text;
    public int image;

    public MyDataSet(String text, int image){
        this.text = text;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
