package com.rustdv.socialmediaapp.mapper;

public interface Mapper <F, T> {
    T mapFrom(F object);
}
