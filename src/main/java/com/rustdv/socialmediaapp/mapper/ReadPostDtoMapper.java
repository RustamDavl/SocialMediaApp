package com.rustdv.socialmediaapp.mapper;

import com.rustdv.socialmediaapp.dto.read.ReadDateDto;
import com.rustdv.socialmediaapp.dto.read.ReadPostDto;
import com.rustdv.socialmediaapp.dto.read.ReadUserDto;
import com.rustdv.socialmediaapp.entity.Post;
import com.rustdv.socialmediaapp.entity.PostImage;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadPostDtoMapper implements Mapper<Post, ReadPostDto> {
    private final ReadUserDtoMapper readUserDtoMapper;

    @Override
    public ReadPostDto mapFrom(Post object) {
        var readPostDtoBuilder = ReadPostDto.builder()
                .id(object.getId())
                .title(object.getTitle())
                .userId(object.getUser().getId())
                .body(object.getBody())
                .readDateDto(
                        ReadDateDto.builder()
                                .year(object.getCreatedAt().getYear())
                                .month(object.getCreatedAt().getMonth().name().toLowerCase())
                                .day(object.getCreatedAt().getDayOfMonth())
                                .build()
                );

        if (object.getPostImages().size() != 0) {
            readPostDtoBuilder
                    .ids(object.getPostImages()
                            .stream()
                            .map(PostImage::getId)
                            .toList()
                    );
        }
        if (Hibernate.isInitialized(object.getUser())) {
            readPostDtoBuilder.readUserDto(readUserDtoMapper.mapFrom(object.getUser()));
        }

        return readPostDtoBuilder.build();
    }
}
