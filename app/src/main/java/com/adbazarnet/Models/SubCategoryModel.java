package com.adbazarnet.Models;

public class SubCategoryModel {
    private Integer id;
    private String name;
    private Integer adCount;
    private String slug;
    private String adType;
    private String categoryName;

    public SubCategoryModel() {
    }

    public SubCategoryModel(Integer id, String name, Integer adCount, String slug, String adType, String categoryName) {
        this.id = id;
        this.name = name;
        this.adCount = adCount;
        this.slug = slug;
        this.adType = adType;
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAdCount() {
        return adCount;
    }

    public String getSlug() {
        return slug;
    }

    public String getAdType() {
        return adType;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
