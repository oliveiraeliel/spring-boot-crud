package com.api.crud.services;

import com.api.crud.models.PostModel;
import com.api.crud.models.UserModel;
import com.api.crud.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {
    final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostModel save(PostModel postModel){
        return postRepository.save(postModel);
    }

    public List<PostModel> findPostsByUserId(UUID id){
        return postRepository.findByPostOwnerId(id);
    }
    @Transactional
    public void delete(PostModel postModel){
        postRepository.delete(postModel);
    }

    public Optional<PostModel> findPostsById(UUID id) {
        return postRepository.findById(id);
    }
}
