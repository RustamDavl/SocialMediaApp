package com.rustdv.socialmediaapp.dto.read;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class PageableResponse<T> {

    List<T> content;
    Metadata metadata;

    public static <T> PageableResponse<T> of(Page<T> page) {
        var metadata = new Metadata(page.getNumber(), page.getTotalPages());
        return new PageableResponse<>(page.getContent(), metadata);
    }

    @Value
    public static class Metadata {
        int page;
        int totalPages;

    }


}
