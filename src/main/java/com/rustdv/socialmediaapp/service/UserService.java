package com.rustdv.socialmediaapp.service;

import com.rustdv.socialmediaapp.entity.SenderRecipient;
import com.rustdv.socialmediaapp.entity.User;
import com.rustdv.socialmediaapp.exception.NotFoundException;
import com.rustdv.socialmediaapp.repository.SenderRecipientRepository;
import com.rustdv.socialmediaapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final SenderRecipientRepository senderRecipientRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("there is no user with id : " + id);
                        }
                );
    }

    public User findByEmailAndPassword(String email, String password) {

        var maybeUser = userRepository.findByEmailAndPassword(email, password);

        return maybeUser.orElseThrow(
                () -> {
                    throw new NotFoundException("bad credentials");
                }
        );
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public boolean delete(User entity) {
        userRepository.delete(entity);
        return true;
    }

    @Transactional
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Transactional
    public User update(User updated) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        return userRepository.findByEmail(email)
                .map(
                        user -> new CustomUserDetails(
                                user.getId(), user.getEmail(), user.getPassword(), Collections.emptyList()
                        )
                ).orElseThrow(
                        () -> {
                            throw new UsernameNotFoundException("there is no such user by email" + email);
                        }
                );
    }

    public List<User> findFriendsByUserId(Long userId) {
        var sederRecipientList = senderRecipientRepository.findFriendsByUserId(userId);
        List<User> users = new ArrayList<>();
        sederRecipientList.forEach(
                senderRecipient -> {
                    if (senderRecipient.getSender().getId().longValue() == userId.longValue()) {
                        users.add(senderRecipient.getRecipient());
                    } else if (senderRecipient.getRecipient().getId().longValue() == userId.longValue()) {
                        users.add(senderRecipient.getSender());
                    }

                }
        );
        return users;
    }
}
