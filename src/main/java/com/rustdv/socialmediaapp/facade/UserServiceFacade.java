package com.rustdv.socialmediaapp.facade;

import com.rustdv.socialmediaapp.dto.createupdate.CreateUpdateUserDto;
import com.rustdv.socialmediaapp.dto.read.ReadUserDto;
import com.rustdv.socialmediaapp.mapper.CreateUpdateUserDtoMapper;
import com.rustdv.socialmediaapp.mapper.ReadUserDtoMapper;
import com.rustdv.socialmediaapp.mapper.UserMapper;
import com.rustdv.socialmediaapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceFacade {

    private final UserService userService;
    private final CreateUpdateUserDtoMapper createUpdateUserDtoMapper;
    private final ReadUserDtoMapper readUserDtoMapper;

    public ReadUserDto save(CreateUpdateUserDto createUpdateUserDto) {
        return readUserDtoMapper.mapFrom(userService.save(createUpdateUserDtoMapper.mapFrom(createUpdateUserDto)));
    }

    public ReadUserDto findByEmailAndPassword(CreateUpdateUserDto createUpdateUserDto) {
        return readUserDtoMapper.mapFrom(userService.findByEmailAndPassword(createUpdateUserDto.getUsername(), createUpdateUserDto.getPassword()));
    }

    public ReadUserDto findById(Long id) {
        return readUserDtoMapper.mapFrom(userService.findById(id));
    }

    public Page<ReadUserDto> findAll(Pageable pageable) {
        return userService.findAll(pageable)
                .map(readUserDtoMapper::mapFrom);
    }




}
