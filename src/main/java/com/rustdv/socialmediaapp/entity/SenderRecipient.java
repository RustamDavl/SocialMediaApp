package com.rustdv.socialmediaapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Objects;

@NamedEntityGraph(
        name = "withSenderAndRecipient",
        attributeNodes = {
                @NamedAttributeNode("sender"),
                @NamedAttributeNode("recipient")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "sender_recipient")
public class SenderRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @ToString.Exclude
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    @ToString.Exclude
    private User recipient;

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    public void addSender(User sender) {
        this.sender = sender;
        sender.getSenders().add(this);
    }

    public void addRecipient(User recipient) {
        this.recipient = recipient;
        recipient.getRecipients().add(this);
    }

    public void overrideLinks(User sender, User recipient) {
        sender.getSenders().remove(this);
        recipient.getRecipients().remove(this);

        this.sender = recipient;
        sender.getRecipients().add(this);

        this.recipient = sender;
        recipient.getSenders().add(this);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SenderRecipient that = (SenderRecipient) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
