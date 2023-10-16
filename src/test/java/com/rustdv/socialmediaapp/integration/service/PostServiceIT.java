package com.rustdv.socialmediaapp.integration.service;

import com.rustdv.socialmediaapp.entity.Post;
import com.rustdv.socialmediaapp.integration.IT;
import com.rustdv.socialmediaapp.integration.IntegrationTestBase;
import com.rustdv.socialmediaapp.service.PostService;
import com.rustdv.socialmediaapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
@Sql(
        "classpath:sql/data.sql"
)
class PostServiceIT extends IntegrationTestBase {

    private final UserService userService;

    private final PostService postService;

    @Test
    void save() {

        Post post = Post.builder()
                .title("post title")
                .body("a huge post body")
                .createdAt(LocalDate.now())
                .build();
        postService.create(post, 1L);


        Optional<Post> maybePost = postService.findById(post.getId());

        assertThat(maybePost).isPresent();
        assertThat(maybePost.get().getBody()).isEqualTo(post.getBody());
        assertThat(maybePost.get().getTitle()).isEqualTo(post.getTitle());
        assertThat(maybePost.get().getUser().getEmail()).isEqualTo(post.getUser().getEmail());

    }

    @Test
    void update() {

        var updatedPost = Post.builder()
                .title("Updated title")
                .build();
        postService.update(2L, updatedPost);

        var actualResult = postService.findById(2L).orElse(null);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getTitle()).isEqualTo(updatedPost.getTitle());
        assertThat(actualResult.getBody()).isEqualTo("body of the post");


    }
}