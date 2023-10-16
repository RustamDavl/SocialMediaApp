package com.rustdv.socialmediaapp.repository;

import com.rustdv.socialmediaapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph("withPostImages")
    List<Post> findAllByUserId(Long id);

    @EntityGraph("withPostImages")
    @Override
    Optional<Post> findById(Long id);

    @EntityGraph("withPostImages")
    Page<Post> findAllByUserId(Long id, Pageable pageable);

}
