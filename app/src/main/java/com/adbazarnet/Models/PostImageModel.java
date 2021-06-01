package com.adbazarnet.Models;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;

public class PostImageModel {

    private String image;

    public PostImageModel(String image) {
        this.image = image;
    }
}
