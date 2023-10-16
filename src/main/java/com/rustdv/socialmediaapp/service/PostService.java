package com.rustdv.socialmediaapp.service;

import com.rustdv.socialmediaapp.entity.Post;
import com.rustdv.socialmediaapp.entity.User;
import com.rustdv.socialmediaapp.exception.NotFoundException;
import com.rustdv.socialmediaapp.repository.PostRepository;
import com.rustdv.socialmediaapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final UserService userService;


    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("there is no post with id : " + id);
                        }
                );
    }

    @Transactional
    public void delete(Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("there is no post with id : " + id);
                        }
                );

        postRepository.delete(post);
    }

    @Transactional
    public Post create(Post entity, Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("there is no user with id : " + userId);
        });

        entity.addUser(user);
        if (nonNullAndEmpty(entity.getPostImages())) {
            entity.getPostImages().forEach(
                    entity::addImageToPost
            );
        }

        return postRepository.saveAndFlush(entity);
    }

    @Transactional
    public Post update(Long postId, Post updated) {

        var post = postRepository.findById(postId).orElseThrow(
                () -> {
                    throw new NotFoundException("there is no post with id: " + postId);
                }
        );


        return postRepository.saveAndFlush(update(post, updated));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }


    public Page<Post> findAllByUserIdAndPageable(Long id, Pageable pageable) {
        return postRepository.findAllByUserId(id, pageable);
    }

    public List<Post> findAllByUserId(Long id) {
        return postRepository.findAllByUserId(id);
    }

    private Post update(Post old, Post updated) {
        if (updated.getTitle() != null) {
            old.setTitle(updated.getTitle());
        }
        if (updated.getBody() != null) {
            old.setBody(updated.getBody());
        }
        if (nonNullAndEmpty(updated.getPostImages())) {
            old.getPostImages().clear();
            updated.getPostImages().forEach(
                    old::addImage
            );
        }


        return old;

    }

    public List<Post> displayActivityFeedByUserId(Long userId) {
        List<List<Post>> posts = userService.findFriendsByUserId(userId)
                .stream()
                .map(user -> postRepository.findAllByUserId(user.getId()))
                .filter(posts1 -> !posts1.isEmpty())
                .toList();

        return posts
                .stream()
                .peek(posts1 -> posts1.sort(Comparator.reverseOrder()))
                .map(
                        posts1 -> posts1.get(0))
                .toList();
    }

    private <T> boolean nonNullAndEmpty(List<T> list) {
        if (list != null) {
            return !list.isEmpty();
        }
        return false;
    }
}
