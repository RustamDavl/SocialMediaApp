package com.rustdv.socialmediaapp.dto.createupdate;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Value
@Builder
public class CreateUpdatePostDto {

    String title;
    String body;
    List<MultipartFile> images;
}
