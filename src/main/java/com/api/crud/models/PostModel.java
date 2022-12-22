package com.api.crud.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_POSTS")
public class PostModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String postText;
    @ManyToOne
    private UserModel postOwner;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public UserModel getPostOwner() {
        return postOwner;
    }

    public void setPostOwner(UserModel postOwner) {
        this.postOwner = postOwner;
    }
}
