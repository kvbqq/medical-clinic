package com.example.medical_clinic.mapper;

import com.example.medical_clinic.model.CreateUserCommand;
import com.example.medical_clinic.model.User;
import com.example.medical_clinic.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toUserEntity(CreateUserCommand command);
}
