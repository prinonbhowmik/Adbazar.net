package com.adbazarnet.Models;

import java.util.List;

public class CategorisQueryModel {
    private int count;
    private String next;
    private String previous;
    private List<CategoriesModel> results = null;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<CategoriesModel> getResults() {
        return results;
    }
}
