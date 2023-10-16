package com.rustdv.socialmediaapp.dto.read;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class ReadUserDto {

    Long id;
    String name;
    String email;
    LocalDate registerAt;
    List<ReadSenderRecipientDto> readSenderRecipientDtoList;
}
