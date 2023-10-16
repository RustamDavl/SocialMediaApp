package com.rustdv.socialmediaapp.facade;

import com.rustdv.socialmediaapp.dto.createupdate.CreateUpdateSenderRecipientDto;
import com.rustdv.socialmediaapp.dto.read.Friend;
import com.rustdv.socialmediaapp.dto.read.ReadSenderRecipientDto;
import com.rustdv.socialmediaapp.dto.read.ReadUserDto;
import com.rustdv.socialmediaapp.mapper.ReadSenderRecipientDtoMapper;
import com.rustdv.socialmediaapp.mapper.ReadUserDtoMapper;
import com.rustdv.socialmediaapp.service.SenderRecipientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SenderRecipientServiceFacade {

    private final SenderRecipientService senderRecipientService;

    private final ReadSenderRecipientDtoMapper readSenderRecipientDtoMapper;

    private final ReadUserDtoMapper readUserDtoMapper;

    public ReadSenderRecipientDto sendRequest(CreateUpdateSenderRecipientDto createUpdateSenderRecipientDto) {

        return readSenderRecipientDtoMapper.mapFrom(
                senderRecipientService.sendRequest(
                        createUpdateSenderRecipientDto.getSenderId(),
                        createUpdateSenderRecipientDto.getRecipientId()
                )
        );
    }

    public List<ReadSenderRecipientDto> findAllBySenderId(Long id) {
        return senderRecipientService.findAllBySenderId(id)
                .stream()
                .map(readSenderRecipientDtoMapper::mapFrom)
                .toList();
    }

    public List<ReadSenderRecipientDto> findAllByRecipientId(Long id) {
        return senderRecipientService.findAllByRecipientId(id)
                .stream()
                .map(readSenderRecipientDtoMapper::mapFrom)
                .toList();
    }

    public List<ReadSenderRecipientDto> findAllSubscriptions(Long id) {
        return senderRecipientService.findAllSubscriptions(id)
                .stream()
                .map(readSenderRecipientDtoMapper::mapFrom)
                .toList();
    }

    public List<ReadSenderRecipientDto> findAllSubscribers(Long id) {
        return senderRecipientService.findAllSubscribers(id)
                .stream()
                .map(readSenderRecipientDtoMapper::mapFrom)
                .toList();
    }

    public void cancelSubscription(CreateUpdateSenderRecipientDto createUpdateSenderRecipientDto) {
        senderRecipientService.cancelSubscription(createUpdateSenderRecipientDto.getSenderId(),
                createUpdateSenderRecipientDto.getRecipientId());
    }

    public ReadSenderRecipientDto acceptRequest(Long senderId, Long recipientId) {
        return readSenderRecipientDtoMapper.mapFrom(senderRecipientService.acceptRequest(senderId, recipientId));
    }

    public List<ReadSenderRecipientDto> findAllBySenderIdOrRecipientIdAndRequestStatus(Long userId) {
        return senderRecipientService.findAllBySenderIdOrRecipientIdAndRequestStatus(userId)
                .stream()
                .map(readSenderRecipientDtoMapper::mapFrom)
                .toList();
    }

    public List<ReadUserDto> findAllBySenderIdOrRecipientIdAndRequestStatusAsFriends(Long userId) {
        var objects = senderRecipientService.findAllBySenderIdOrRecipientIdAndRequestStatusAsFriends(userId);
        List<ReadUserDto> friends = new ArrayList<>();

        objects.forEach(
                senderRecipient -> {
                    if (senderRecipient.getRecipient().getId().longValue() == userId.longValue()) {
                        friends.add(

                                readUserDtoMapper.mapFrom(senderRecipient.getSender())
                        );
                    } else if (senderRecipient.getSender().getId().longValue() == userId.longValue()) {
                        friends.add(
                                readUserDtoMapper.mapFrom(senderRecipient.getRecipient())
                        );
                    }


                }
        );
        return friends;
    }


    public List<ReadUserDto> findFriendsByUserId(Long userId) {
        var objects = senderRecipientService.findFriendsByUserId(userId);
        List<ReadUserDto> friends = new ArrayList<>();

        objects.forEach(
                senderRecipient -> {
                    if (senderRecipient.getRecipient().getId().longValue() == userId.longValue()) {
                        friends.add(

                                readUserDtoMapper.mapFrom(senderRecipient.getSender())
                        );
                    } else if (senderRecipient.getSender().getId().longValue() == userId.longValue()) {
                        friends.add(
                                readUserDtoMapper.mapFrom(senderRecipient.getRecipient())
                        );
                    }


                }
        );
        return friends;
    }

    public void deleteFriend(CreateUpdateSenderRecipientDto createUpdateSenderRecipientDto) {
        senderRecipientService.deleteFriend(
                createUpdateSenderRecipientDto.getSenderId(), createUpdateSenderRecipientDto.getRecipientId()
        );
    }


}
