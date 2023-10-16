package com.rustdv.socialmediaapp.dto.createupdate;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateUpdateSenderRecipientDto {

    Long senderId;
    Long recipientId;
    Long currentUserId;

}
