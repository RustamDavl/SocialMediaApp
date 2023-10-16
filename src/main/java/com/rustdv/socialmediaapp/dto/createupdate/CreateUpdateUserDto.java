package com.rustdv.socialmediaapp.dto.createupdate;

import lombok.*;

@Value
@Builder
public class CreateUpdateUserDto {

    String name;
    String username;
    String password;

}
