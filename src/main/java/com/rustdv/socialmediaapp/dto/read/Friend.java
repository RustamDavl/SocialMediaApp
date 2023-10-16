package com.rustdv.socialmediaapp.dto.read;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Friend {

    List<ReadUserDto> friends;
}
