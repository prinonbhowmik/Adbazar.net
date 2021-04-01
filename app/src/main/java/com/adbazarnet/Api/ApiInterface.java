package com.adbazarnet.Api;


import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.CategorisQueryModel;
import com.adbazarnet.Models.SubCategoryProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("categories/")
    Call<List<CategoriesModel>> getProductsCategories();

    @GET("categories/?limit=1&offset=0&ad_type=electronics&search=Mobiles")
    Call<CategorisQueryModel> getSubCategories(@Query("limit") int limit,
                                               @Query("offset") int offset,
                                               @Query("ad_type") String ad_type,
                                               @Query("search") String search);

    @GET("posts/?limit=10&offset=-1&category__slug=mobile-phones")
    Call<SubCategoryProductModel> getSubCategoriesProduct(@Query("limit") int limit,
                                                          @Query("offset") int offset,
                                                          @Query("category__slug") String ad_type);
}
