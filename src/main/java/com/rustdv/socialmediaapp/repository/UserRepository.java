package com.rustdv.socialmediaapp.repository;

import com.rustdv.socialmediaapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmailAndPassword(String email, String password);

    //@EntityGraph("withSenderAndRecipientLists")
    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);




}