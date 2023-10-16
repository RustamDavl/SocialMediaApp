package com.rustdv.socialmediaapp.service;

import com.rustdv.socialmediaapp.exception.NotFoundException;
import com.rustdv.socialmediaapp.repository.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;


    public byte[] findById(Long id) {
        var postImage = postImageRepository.findById(id)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("there is no image with id : " + id);
                        }
                );

        return postImage.getImageBytes();
    }

}
