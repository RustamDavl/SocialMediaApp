package com.rustdv.socialmediaapp.controller;

import com.rustdv.socialmediaapp.service.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class PostImageRestController {

    private final PostImageService postImageService;


    @GetMapping(value = "/image/{imageId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getImageById(@PathVariable String imageId) {

        return postImageService.findById(Long.valueOf(imageId));
    }

}
