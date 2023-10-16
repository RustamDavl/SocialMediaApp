package com.rustdv.socialmediaapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@NamedEntityGraph(
//        name = "withSenderAndRecipientLists",
//        attributeNodes = {
//                @NamedAttributeNode("senders"),
//                @NamedAttributeNode("recipients")
//        }
//)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Column(name = "register_at")
    @CreationTimestamp
    private LocalDate registerAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    @ToString.Exclude
    @Builder.Default
    private List<SenderRecipient> senders = new ArrayList<>();

    @OneToMany(mappedBy = "recipient")
    @ToString.Exclude
    @Builder.Default
    private List<SenderRecipient> recipients = new ArrayList<>();

    public void addPost(Post post) {
        this.posts.add(post);
        post.setUser(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
