package com.adbazarnet.Models;

public class SubCategoryModel {
    private Integer id;
    private String name;
    private Integer ad_count;
    private String slug;
    private String ad_type;
    private String category_name;

    public SubCategoryModel() {
    }

    public SubCategoryModel(Integer id, String name, Integer ad_count, String slug, String ad_type, String category_name) {
        this.id = id;
        this.name = name;
        this.ad_count = ad_count;
        this.slug = slug;
        this.ad_type = ad_type;
        this.category_name = category_name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAd_count() {
        return ad_count;
    }

    public String getSlug() {
        return slug;
    }

    public String getAd_type() {
        return ad_type;
    }

    public String getCategory_name() {
        return category_name;
    }
}
