package com.adbazarnet.Models;

import java.io.File;

public class PostImageModel {
    private String image;
    private String file;

    public PostImageModel(String image, String file) {
        this.image = image;
        this.file = file;
    }

    public String getImage() {
        return image;
    }

    public String getFile() {
        return file;
    }
}
