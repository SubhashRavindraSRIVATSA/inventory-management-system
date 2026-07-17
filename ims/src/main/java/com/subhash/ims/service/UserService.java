package com.subhash.ims.service;

import com.subhash.ims.dto.request.UserRequestDto;
import com.subhash.ims.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto request);

    UserResponseDto updateUser(Long id, UserRequestDto request);

    void deleteUser(Long id);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();

    UserResponseDto enableUser(Long id);

    UserResponseDto disableUser(Long id);

    UserResponseDto lockUser(Long id);

    UserResponseDto unlockUser(Long id);
}
