package com.api.crud.controllers;

import com.api.crud.dtos.PostDto;
import com.api.crud.models.PostModel;
import com.api.crud.models.UserModel;
import com.api.crud.services.PostService;
import com.api.crud.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user/post")
public class PostController {
    final PostService postService;
    final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> createPost(@PathVariable(name = "id") UUID id,
                                             @RequestBody @Valid PostDto postDto){
        Optional<UserModel> userModelOptional = userService.findById(id);

        if(userModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user");
        }
        var postModel = new PostModel();
        BeanUtils.copyProperties(postDto, postModel);
        postModel.setPostOwner(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAllPostsFromUserById(@PathVariable(name = "id") UUID id){
        if (!userService.existsById(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user");
        }
        List<PostModel> posts = postService.findPostsByUserId(id);
        if (posts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No post was found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(postService.findPostsByUserId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable(name = "id") UUID id){
        Optional<PostModel> postModelOptional = postService.findPostsById(id);
        if (postModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This post does not exist");
        }
        postService.delete(postModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully");
    }
}
