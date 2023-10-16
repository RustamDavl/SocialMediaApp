package com.rustdv.socialmediaapp.repository;

import com.rustdv.socialmediaapp.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {


}
