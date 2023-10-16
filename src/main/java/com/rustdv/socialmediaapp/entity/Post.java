package com.rustdv.socialmediaapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NamedEntityGraph(
        name = "withPostImages",
        attributeNodes = {
                @NamedAttributeNode("postImages")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "post")
public class
Post implements Comparable<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDate createdAt;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();

    public void addImage(PostImage postImage) {
        this.getPostImages().add(postImage);
        postImage.setPost(this);
    }

    public void addImageToPost(PostImage postImage) {
        postImage.setPost(this);
    }

    public void addUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body);
    }

    @Override
    public int compareTo(Post o) {
        return this.getCreatedAt().compareTo(o.getCreatedAt());
    }
}
