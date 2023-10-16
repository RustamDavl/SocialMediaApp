package com.rustdv.socialmediaapp.integration.service;

import com.rustdv.socialmediaapp.entity.RequestStatus;
import com.rustdv.socialmediaapp.entity.SenderRecipient;
import com.rustdv.socialmediaapp.exception.NotFoundException;
import com.rustdv.socialmediaapp.integration.IT;
import com.rustdv.socialmediaapp.integration.IntegrationTestBase;
import com.rustdv.socialmediaapp.service.SenderRecipientService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
@Sql("classpath:sql/data.sql")
public class SenderRecipientIT extends IntegrationTestBase {

    private final SenderRecipientService senderRecipientService;

    @Test
    void save() {

        SenderRecipient actualResult = senderRecipientService.sendRequest(5L, 6L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getRequestStatus().name()).isEqualTo(RequestStatus.SUBSCRIBED.name());
        assertThat(actualResult.getSender().getEmail()).isEqualTo("alalla@gmail.com");
        assertThat(actualResult.getRecipient().getEmail()).isEqualTo("petya@gmail.com");


    }

    @Test
    void acceptRequest() {
        SenderRecipient actualResult = senderRecipientService.acceptRequest(1L, 2L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getRequestStatus().name()).isEqualTo("FRIENDS");
    }

    @Test
    void deleteFriend1() {
        SenderRecipient actualResult = senderRecipientService.deleteFriend(3L, 1L);


        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getSender().getId()).isEqualTo(3L);
        assertThat(actualResult.getRecipient().getId()).isEqualTo(1L);
        assertThat(actualResult.getRequestStatus().name()).isEqualTo("SUBSCRIBED");
    }

    @Test
    void deleteFriend2() {
        SenderRecipient actualResult = senderRecipientService.deleteFriend(1L, 3L);


        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getSender().getId()).isEqualTo(1L);
        assertThat(actualResult.getRecipient().getId()).isEqualTo(3L);
        assertThat(actualResult.getRequestStatus().name()).isEqualTo("SUBSCRIBED");
    }

    @Test
    void cancelSubscription() {
        senderRecipientService.cancelSubscription(1L, 2L);

        Assertions.assertThrows(NotFoundException.class, () -> senderRecipientService.cancelSubscription(1L, 2L));
    }
}
