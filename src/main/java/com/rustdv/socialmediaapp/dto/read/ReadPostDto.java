package com.rustdv.socialmediaapp.dto.read;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class ReadPostDto {

    Long id;
    String title;
    String body;
    // TODO: 14.07.2023 delete field 
//    LocalDate createdAt;
    ReadDateDto readDateDto;
    ReadUserDto readUserDto;
    Long userId;
    List<Long> ids;

}
