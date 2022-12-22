package com.api.crud.repositories;

import com.api.crud.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<PostModel, UUID> {
    List<PostModel> findByPostOwnerId(UUID id);

}
