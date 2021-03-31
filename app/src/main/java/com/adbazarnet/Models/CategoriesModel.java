package com.adbazarnet.Models;

import java.util.List;

public class CategoriesModel {
    private Integer id;
    private String name;
    private String icon;
    private String adType;
    private String slug;
    private Integer adCount;
    private List<SubCategoryModel> subCategories = null;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getAdType() {
        return adType;
    }

    public String getSlug() {
        return slug;
    }

    public Integer getAdCount() {
        return adCount;
    }

    public List<SubCategoryModel> getSubCategories() {
        return subCategories;
    }
}
