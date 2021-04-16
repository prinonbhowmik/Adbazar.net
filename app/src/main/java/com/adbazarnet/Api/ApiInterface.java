package com.adbazarnet.Api;


import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.CategorisQueryModel;
import com.adbazarnet.Models.ProductModel;
import com.adbazarnet.Models.SubCategoryProductModel;
import com.adbazarnet.Models.User;
import com.adbazarnet.Models.UserDetailsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("account/auth/")
    @FormUrlEncoded
    Call<UserDetailsModel> userLogin(@Field("username") String phone,
                                     @Field("password") String password);

    @POST("account/users/")
    @FormUrlEncoded
    Call<User> userRegister(@Field("name") String name,
                            @Field("phone_number") String phone,
                            @Field("email") String email,
                            @Field("password") String password);
    @GET("logout/")
    Call<UserDetailsModel> logoutUser();

    @GET("posts")
    Call<List<ProductModel>> getAllAds();


}
