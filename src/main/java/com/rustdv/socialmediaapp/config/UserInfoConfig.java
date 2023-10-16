package com.rustdv.socialmediaapp.config;

import com.rustdv.socialmediaapp.entity.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserInfoConfig {

    @Bean
    public UserInfo userInfo() {
        return new UserInfo();

    }
}
