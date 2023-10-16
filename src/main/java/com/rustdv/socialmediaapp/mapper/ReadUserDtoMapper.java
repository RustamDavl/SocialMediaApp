package com.rustdv.socialmediaapp.mapper;

import com.rustdv.socialmediaapp.dto.read.ReadUserDto;
import com.rustdv.socialmediaapp.entity.User;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Component;

@Builder
@Value
@Component
public class ReadUserDtoMapper implements Mapper<User, ReadUserDto> {


    @Override
    public ReadUserDto mapFrom(User object) {
        return ReadUserDto.builder()
                .id(object.getId())
                .name(object.getName())
                .email(object.getEmail())
                .registerAt(object.getRegisterAt())
                .build();
    }
}
