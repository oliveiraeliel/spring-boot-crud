package com.api.crud.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class PostDto {
    @NotBlank
    private String postText;

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }
}
