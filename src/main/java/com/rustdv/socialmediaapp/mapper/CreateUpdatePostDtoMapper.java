package com.rustdv.socialmediaapp.mapper;

import com.rustdv.socialmediaapp.dto.createupdate.CreateUpdatePostDto;
import com.rustdv.socialmediaapp.entity.Post;
import com.rustdv.socialmediaapp.entity.PostImage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreateUpdatePostDtoMapper implements Mapper<CreateUpdatePostDto, Post> {
    @Override
    public Post mapFrom(CreateUpdatePostDto object) {

        var post = Post.builder()
                .title(object.getTitle())
                .body(object.getBody())
                .build();

        if (object.getImages() != null) {
            if (object.getImages().size() != 0) {

                var postImages = object.getImages().stream()
                        .map(multipartFile -> {
                            try {
                                return multipartFile.getBytes();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }).map(bytes -> PostImage.builder()
                                .imageBytes(bytes)
                                .build())
                        .toList();

                post.setPostImages(postImages);
//                postImages.forEach(
//                        post::addImage
//                );

            }
        }

        return post;

    }
}
