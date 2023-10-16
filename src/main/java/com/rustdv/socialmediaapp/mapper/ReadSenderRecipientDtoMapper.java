package com.rustdv.socialmediaapp.mapper;


import com.rustdv.socialmediaapp.dto.read.ReadSenderRecipientDto;
import com.rustdv.socialmediaapp.entity.SenderRecipient;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadSenderRecipientDtoMapper implements Mapper<SenderRecipient, ReadSenderRecipientDto> {

    private final ReadUserDtoMapper readUserDtoMapper;

    @Override
    public ReadSenderRecipientDto mapFrom(SenderRecipient object) {
        var objectBuilder = ReadSenderRecipientDto.builder()
                .senderId(object.getSender().getId())
                .recipientId(object.getRecipient().getId())
                .requestStatus(object.getRequestStatus().name().toLowerCase());

        if (Hibernate.isInitialized(object.getRecipient())) {
            objectBuilder.recipient(readUserDtoMapper.mapFrom(object.getRecipient()));

        }
        if (Hibernate.isInitialized(object.getSender())) {
            objectBuilder.sender(readUserDtoMapper.mapFrom(object.getSender()));
        }

        return objectBuilder.build();

    }
}
