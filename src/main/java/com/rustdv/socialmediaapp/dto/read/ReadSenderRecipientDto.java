package com.rustdv.socialmediaapp.dto.read;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReadSenderRecipientDto {
    Long id;
    Long senderId;
    Long recipientId;
    String requestStatus;
    ReadUserDto recipient;
    ReadUserDto sender;
}
