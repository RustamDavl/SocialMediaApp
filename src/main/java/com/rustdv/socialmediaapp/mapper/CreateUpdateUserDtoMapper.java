package com.rustdv.socialmediaapp.mapper;

import com.rustdv.socialmediaapp.dto.createupdate.CreateUpdateUserDto;
import com.rustdv.socialmediaapp.entity.User;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Builder
@Value
public class CreateUpdateUserDtoMapper implements Mapper<CreateUpdateUserDto, User> {

    PasswordEncoder passwordEncoder;
    @Override
    public User mapFrom(CreateUpdateUserDto object) {
        return User.builder()
                .name(object.getName())
                .email(object.getUsername())
                .password(passwordEncoder.encode(object.getPassword()))
                .build();
    }
}
