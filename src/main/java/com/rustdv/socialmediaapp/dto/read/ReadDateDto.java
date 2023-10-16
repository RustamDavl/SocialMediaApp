package com.rustdv.socialmediaapp.dto.read;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReadDateDto {
    Integer year;
    String month;
    Integer day;
}
