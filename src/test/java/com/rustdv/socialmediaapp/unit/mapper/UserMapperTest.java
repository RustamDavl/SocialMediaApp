package com.rustdv.socialmediaapp.unit.mapper;

import com.rustdv.socialmediaapp.dto.createupdate.CreateUpdateUserDto;
import com.rustdv.socialmediaapp.entity.User;
import com.rustdv.socialmediaapp.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;



class UserMapperTest {

    UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = UserMapper.INSTANCE;
    }

    @Test
    void userToReadUserDto() {

       var user = User.builder()
                .name("Anna")
                .email("annad@gmail.com")
                .password("strong")
                .build();

        var actualResult = userMapper.userToReadUserDto(user);

        assertThat(actualResult.getEmail()).isEqualTo(user.getEmail());
        assertThat(actualResult.getName()).isEqualTo(user.getName());
    }

    @Test
    void createUpdateUserDtoToUser() {

        CreateUpdateUserDto createUpdateUserDto = CreateUpdateUserDto.builder()
                .name("Anna")
                .email("annad@gmail.com")
                .password("strong")
                .build();

        var actualResult = userMapper.createUpdateUserDtoToUser(createUpdateUserDto);

        assertThat(actualResult.getEmail()).isEqualTo(createUpdateUserDto.getEmail());
        assertThat(actualResult.getName()).isEqualTo(createUpdateUserDto.getName());
        assertThat(actualResult.getPassword()).isEqualTo(createUpdateUserDto.getPassword());
    }
}