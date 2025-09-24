package com.example.wellness360;

import androidx.lifecycle.ViewModel;

public class YourViewModel extends ViewModel {

    private String imageUri;

    public void setImageUri(String uri) {
        this.imageUri = uri;
    }

    public String getImageUri() {
        return imageUri;
    }
}