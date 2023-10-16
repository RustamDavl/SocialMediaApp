package com.rustdv.socialmediaapp.repository;

import com.rustdv.socialmediaapp.entity.RequestStatus;
import com.rustdv.socialmediaapp.entity.SenderRecipient;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SenderRecipientRepository extends JpaRepository<SenderRecipient, Long> {

    @EntityGraph("withSenderAndRecipient")
    @Query("""
            select sr
            from SenderRecipient sr
            where (sr.sender.id = :senderId and sr.recipient.id = :recipientId) or (sr.sender.id = :recipientId and sr.recipient.id = :senderId)
            """)
    Optional<SenderRecipient> findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(Long senderId, Long recipientId);


    @EntityGraph("withSenderAndRecipient")
    Optional<SenderRecipient> findBySenderIdAndRecipientId(Long senderId, Long recipientId);


    @Query(
            """
                    select sr from SenderRecipient sr
                    where sr.sender.id = :id and sr.requestStatus <> 'FRIENDS'
                    """
    )
    List<SenderRecipient> findAllBySenderId(Long id);

    @Query(
            """
                    select sr from SenderRecipient sr
                    where sr.recipient.id = :id and sr.requestStatus <> 'FRIENDS'
                    """
    )
    List<SenderRecipient> findAllByRecipientId(Long id);

    @Query(
            """
                    select sr from SenderRecipient sr
                    where (sr.sender.id = :userId or sr.recipient.id = :userId) and sr.requestStatus = 'FRIENDS'
                    """
    )
    List<SenderRecipient> findAllBySenderIdOrRecipientIdAndRequestStatus(Long userId);

    //    @EntityGraph("withRecipient")
    @EntityGraph("withSenderAndRecipient")
    @Query(
            """
                    select sr from SenderRecipient sr
                    where (sr.sender.id = :senderId and (sr.requestStatus = 'SUBSCRIBED' or sr.requestStatus = 'FRIENDS'))
                    or (sr.recipient.id = :senderId and sr.requestStatus = 'FRIENDS')
                    """
    )
    List<SenderRecipient> findAllBySenderIdAnAndRequestStatus(Long senderId);

    @EntityGraph("withSenderAndRecipient")
    @Query(
            """
                    select sr from SenderRecipient sr
                    where (sr.sender.id = :userId)
                    or (sr.recipient.id = :userId and sr.requestStatus = 'FRIENDS')
                    """
    )
    List<SenderRecipient> findAllByUserId(Long userId);

    @EntityGraph("withSenderAndRecipient")
    @Query(
            """
                    select sr from SenderRecipient sr
                    where (sr.recipient.id = :recipientId and (sr.requestStatus = 'SUBSCRIBED' or sr.requestStatus = 'FRIENDS'))
                    or (sr.sender.id = :recipientId and sr.requestStatus = 'FRIENDS')
                    """
    )
    List<SenderRecipient> findAllByRecipientIdAnAndRequestStatus(Long recipientId);

    @EntityGraph("withSenderAndRecipient")
    @Query("""
            select sr from SenderRecipient sr
            where sr.sender.id = :userId or (sr.recipient.id = :userId and sr.requestStatus = 'FRIENDS') 
            """)
    List<SenderRecipient> findFriendsByUserId(Long userId);

    @EntityGraph("withSenderAndRecipient")
    @Query("""
            select sr from SenderRecipient sr
            where sr.requestStatus = 'FRIENDS' and (sr.sender.id = :userId or sr.recipient.id = :userId)
            """)
    List<SenderRecipient> findAllBySenderIdOrRecipientIdAndRequestStatusAsFriends(Long userId);


}
