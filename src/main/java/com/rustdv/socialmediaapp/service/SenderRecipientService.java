package com.rustdv.socialmediaapp.service;

import com.rustdv.socialmediaapp.entity.RequestStatus;
import com.rustdv.socialmediaapp.entity.SenderRecipient;
import com.rustdv.socialmediaapp.exception.NotFoundException;
import com.rustdv.socialmediaapp.repository.SenderRecipientRepository;
import com.rustdv.socialmediaapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SenderRecipientService {

    private final SenderRecipientRepository senderRecipientRepository;

    private final UserRepository userRepository;


    public Optional<SenderRecipient> findById(Long id) {
        return Optional.empty();
    }


    public boolean delete(SenderRecipient entity) {
        return false;
    }


    public List<SenderRecipient> findAllBySenderId(Long id) {
        return senderRecipientRepository.findAllBySenderId(id);
    }
    public List<SenderRecipient> findAllByRecipientId(Long id) {
        return senderRecipientRepository.findAllByRecipientId(id);
    }

    @Transactional
    public SenderRecipient sendRequest(Long senderId, Long recipientId) {

        Optional<SenderRecipient> maybeObject = senderRecipientRepository.
                findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(senderId, recipientId);

        if (maybeObject.isPresent()) {
            var objectToSave = maybeObject.get();
            objectToSave.setRequestStatus(RequestStatus.FRIENDS);
            return senderRecipientRepository.saveAndFlush(objectToSave);
        }


        var maybeSender = userRepository.findById(senderId);
        var maybeRecipient = userRepository.findById(recipientId);
        var senderRecipientToSave = SenderRecipient.builder()
                .requestStatus(RequestStatus.SUBSCRIBED)
                .build();

        senderRecipientToSave.addSender(maybeSender.orElseThrow(() -> {
            throw new NotFoundException("there is no such user by id : " + senderId);
        }));
        senderRecipientToSave.addRecipient(maybeRecipient.orElseThrow(() -> {
            throw new NotFoundException("there is no such user by id : " + senderId);
        }));


        return senderRecipientRepository.save(senderRecipientToSave);
    }

    @Transactional
    public void cancelSubscription(Long senderId, Long recipientId) {

        var maybeSenderRecipient = senderRecipientRepository.findBySenderIdAndRecipientId(senderId, recipientId);

        SenderRecipient senderRecipient = maybeSenderRecipient.orElseThrow(() -> {
            throw new NotFoundException("there is no such entity by these ids : " + senderId + " and " + recipientId);
        });

        senderRecipientRepository.delete(senderRecipient);

    }

    @Transactional
    public SenderRecipient acceptRequest(Long senderId, Long recipientId) {

        var maybeSenderRecipient = senderRecipientRepository.findBySenderIdAndRecipientId(senderId, recipientId);

        SenderRecipient senderRecipient = maybeSenderRecipient.orElseThrow(() -> {
            throw new NotFoundException("there is no such entity by these ids : " + senderId + " and " + recipientId);
        });

        //change request status from subscriber to friends
        senderRecipient.setRequestStatus(RequestStatus.FRIENDS);

        return senderRecipientRepository.saveAndFlush(senderRecipient);

    }

    @Transactional
    //who - user's id who had been deleted; from - user's id who deleted somebody
    public SenderRecipient deleteFriend(Long who, Long from) {
        var maybeSenderRecipient = senderRecipientRepository.findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(who, from);

        SenderRecipient senderRecipient = maybeSenderRecipient.orElseThrow(() -> {
            throw new NotFoundException("there is no such entity by these ids : " + who + " and " + from);
        });

        /*
         if sender deletes recipient - when we need to change them in db.Now deleted(recipient in db)
        becomes sender and sender becomes recipient.
        method allows us to override links
         */
        if (senderRecipient.getSender().getId().longValue() == from.longValue()) {
            senderRecipient.overrideLinks(senderRecipient.getSender(), senderRecipient.getRecipient());
        }

        senderRecipient.setRequestStatus(RequestStatus.SUBSCRIBED);
        return senderRecipientRepository.saveAndFlush(senderRecipient);

    }

    public List<SenderRecipient> findAllSubscriptions(Long id) {

        return senderRecipientRepository.findAllBySenderIdAnAndRequestStatus(id);
    }

    public List<SenderRecipient> findAllSubscribers(Long id) {

        return senderRecipientRepository.findAllByRecipientIdAnAndRequestStatus(id);
    }


    public List<SenderRecipient> findAllBySenderIdOrRecipientIdAndRequestStatus(Long userId) {

        return senderRecipientRepository.findAllBySenderIdOrRecipientIdAndRequestStatus(userId);
    }

    public List<SenderRecipient> findFriendsByUserId(Long userId) {
        return senderRecipientRepository.findFriendsByUserId(userId);
    }


    public List<SenderRecipient> findAllBySenderIdOrRecipientIdAndRequestStatusAsFriends(Long userId) {
        return senderRecipientRepository.findAllBySenderIdOrRecipientIdAndRequestStatusAsFriends(userId);
    }



    public SenderRecipient update(SenderRecipient updated) {
        return null;
    }
}
