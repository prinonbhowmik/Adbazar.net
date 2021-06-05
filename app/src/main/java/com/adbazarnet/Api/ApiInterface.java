package com.adbazarnet.Api;


import com.adbazarnet.Models.AdDetails;
import com.adbazarnet.Models.BidModel;
import com.adbazarnet.Models.CategoriesModel;
import com.adbazarnet.Models.CategorisQueryModel;
import com.adbazarnet.Models.ChatChannelModel;
import com.adbazarnet.Models.ChatModel;
import com.adbazarnet.Models.DashboardModel;
import com.adbazarnet.Models.EditAdModel;
import com.adbazarnet.Models.FavouriteAdDetails;
import com.adbazarnet.Models.FavouriteAds;
import com.adbazarnet.Models.LocationsModel;
import com.adbazarnet.Models.MembershipPackage;
import com.adbazarnet.Models.PostAdModel;
import com.adbazarnet.Models.ProductModel;
import com.adbazarnet.Models.SubCategoryProductModel;
import com.adbazarnet.Models.TransactionModel;
import com.adbazarnet.Models.User;
import com.adbazarnet.Models.UserDetailsModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("categories/")
    Call<List<CategoriesModel>> getProductsCategories(@Header("Accept-Language")String lang);

    @GET("categories/?limit=1&offset=0&ad_type=electronics&search=Mobiles")
    Call<CategorisQueryModel> getSubCategories(@Query("limit") int limit,
                                               @Query("offset") int offset,
                                               @Query("ad_type") String ad_type,
                                               @Query("search") String search);

    @GET("posts/?limit=10&offset=-1&category__slug=mobile-phones")
    Call<SubCategoryProductModel> getSubCategoriesProduct(@Header("Accept-Language")String lang,
                                                          @Query("limit") int limit,
                                                          @Query("offset") int offset,
                                                          @Query("category__slug") String ad_type);

    @GET("posts/?limit=20&offset=0&location__slug=Bangshal")
    Call<SubCategoryProductModel> getProductByLocation(@Header("Accept-Language")String lang,
                                                       @Query("limit") int limit,
                                                       @Query("offset") int offset,
                                                       @Query("location__slug") String slug);

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
    Call<List<ProductModel>> getAllAds(@Header("Accept-Language")String lang);

    @GET("locations/")
    Call<List<LocationsModel>> getAllLocations(@Header("Accept-Language")String lang);

    @PATCH("account/auth/profile/")
    Call<User> updateProfile(@Header("Authorization") String token, @Body User user);

    @PATCH("account/auth/profile/")
    @Multipart
    Call<User> updateImage(@Header("Authorization") String token, @Part MultipartBody.Part avatar);

    @PATCH("posts/{id}/")
    Call<AdDetails> editMyAds(@Header("Authorization") String token,@Path("id") int id ,@Body EditAdModel file);


    @PATCH("account/auth/change-password/")
    Call<UserDetailsModel> updatePassword(@Header("Authorization") String token, @Body UserDetailsModel user);

    @GET("posts/{id}/")
    Call<AdDetails> getAdDetails(@Header("Accept-Language")String lang,@Path("id") int id);

    @GET("posts/{id}/bids")
    Call<List<BidModel>> getBids(@Path("id") int id);

    @POST("posts/{id}/bids/")
    @FormUrlEncoded
    Call<BidModel> postBids(@Header("Authorization") String token,
                            @Path("id") int id,
                            @Field("amount") String amount,
                            @Field("ad") int ad);


    @POST("favourite-posts/")
    @FormUrlEncoded
    Call<List<FavouriteAds>> addToFav(@Header("Authorization") String token,
                                      @Field("user") int userId,
                                      @Field("ad") int adId);

    @GET("favourite-posts/")
    Call<List<FavouriteAds>> getFavouriteAds(@Header("Authorization") String token,
                                             @Header("Accept-Language")String lang);

    @GET("myposts/")
    Call<List<FavouriteAdDetails>> getMyAds(@Header("Authorization") String token);


    @GET("account/auth/dashboard")
    Call<DashboardModel> dashboardData(@Header("Authorization") String token);

    @DELETE("favourite-posts/{id}/")
    Call<FavouriteAds> deleteFavourites(@Header("Authorization") String token,
                                        @Path("id") int id);

    @DELETE("posts/{id}/")
    Call<FavouriteAdDetails> deleteMyAd(@Header("Authorization") String token,
                                        @Path("id") int id);

    @POST("posts/create/")
    Call<AdDetails> postsellAd(@Header("Authorization") String token, @Body PostAdModel object);


    @GET("chat/channels/")
    Call<List<ChatModel>> getChatList(@Header("Authorization") String token);

    @POST("chat/channels/")
    @FormUrlEncoded
    Call<ChatModel> createChannel(@Header("Authorization") String token,
                                  @Field("ad") int ad,
                                  @Field("receiver") int receiver);

    @POST("chat/create/")
    @FormUrlEncoded
    Call<ChatChannelModel> sendMsg(@Header("Authorization") String token,
                                   @Field("channel") int channel,
                                   @Field("message") String message);

    @GET("chat/channels/{id}/")
    Call<List<ChatChannelModel>> getChatDetails(@Header("Authorization") String token,
                                                @Path("id") int id);

    @Headers("Content-Type: application/json")
    @GET("account/membership/packages/")
    Call<List<MembershipPackage>> getPackages(@Header("Authorization") String token);

    @GET("account/payment-methods/")
    Call<List<MembershipPackage>> getBkash(@Header("Authorization") String token);

    @POST("account/membership-request/")
    Call<TransactionModel> membershipUpgrade(@Header("Authorization") String token, @Body TransactionModel object);


}
