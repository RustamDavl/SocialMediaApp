package com.rustdv.socialmediaapp.service;

import java.util.Optional;

public interface IService<E> {

    E findById(Long id);

    boolean delete(E entity);

    E save(E entity);

    E update(E updated);



}
