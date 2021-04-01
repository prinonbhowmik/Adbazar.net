package com.adbazarnet.Models;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryProductModel {
    private int count;
    private String next;
    private String previous;
    private List<ProductModel> results = new ArrayList<>();

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<ProductModel> getResults() {
        return results;
    }
}
