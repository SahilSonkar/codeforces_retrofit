package com.example.codeforces_retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class feed {

    @SerializedName("JSON")
    @Expose
    ArrayList<data> arrayList;

    public ArrayList<data> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<data> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public String toString() {
        return "feed{" +
                "arrayList=" + arrayList +
                '}';
    }
}
