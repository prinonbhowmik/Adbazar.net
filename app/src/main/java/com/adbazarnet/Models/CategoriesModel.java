package com.adbazarnet.Models;

import java.util.ArrayList;
import java.util.List;

public class CategoriesModel {
    private Integer id;
    private String name;
    private String icon;
    private String ad_type;
    private String slug;
    private Integer ad_count;
    private List<SubCategoryModel> sub_categories = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getAd_type() {
        return ad_type;
    }

    public String getSlug() {
        return slug;
    }

    public Integer getAd_count() {
        return ad_count;
    }

    public List<SubCategoryModel> getSub_categories() {
        return sub_categories;
    }
}
