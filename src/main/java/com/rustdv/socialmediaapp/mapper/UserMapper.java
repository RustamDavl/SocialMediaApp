package com.rustdv.socialmediaapp.mapper;

import com.rustdv.socialmediaapp.dto.createupdate.CreateUpdateUserDto;
import com.rustdv.socialmediaapp.dto.read.ReadUserDto;
import com.rustdv.socialmediaapp.entity.User;
import com.rustdv.socialmediaapp.facade.UserServiceFacade;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = UserServiceFacade.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    ReadUserDto userToReadUserDto(User user);

    User createUpdateUserDtoToUser(CreateUpdateUserDto createUpdateUserDto);
}
