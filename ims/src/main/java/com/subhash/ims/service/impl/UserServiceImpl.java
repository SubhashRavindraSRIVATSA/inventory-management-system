package com.subhash.ims.service.impl;

import com.subhash.ims.dto.request.UserRequestDto;
import com.subhash.ims.dto.response.UserResponseDto;
import com.subhash.ims.entity.Role;
import com.subhash.ims.entity.User;
import com.subhash.ims.exception.ResourceAlreadyExistsException;
import com.subhash.ims.exception.ResourceNotFoundException;
import com.subhash.ims.mapper.UserMapper;
import com.subhash.ims.repository.RoleRepository;
import com.subhash.ims.repository.UserRepository;
import com.subhash.ims.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(UserRequestDto request) {
        log.info("Creating user : {}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists : " + request.getEmail());
        }

        if (request.getPhone() != null && userRepository.existsByPhone(request.getPhone())) {
            throw new ResourceAlreadyExistsException(
                    "Phone already exists : " + request.getPhone());
        }

//        Role role = roleRepository.findById(request.getRoleId())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "Role not found with id : "
//                                        + request.getRoleId()));

        User user = userMapper.toEntity(request);
       // user.setRole(role);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.setAccountLocked(false);
        User savedUser = userRepository.save(user);
        log.info("User created successfully : {}", savedUser.getId());
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto request) {
        log.info("Updating user : {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists : " + request.getEmail());
        }

        if (request.getPhone() != null && !request.getPhone().equals(user.getPhone()) && userRepository.existsByPhone(request.getPhone())) {
            throw new ResourceAlreadyExistsException("Phone already exists : " + request.getPhone());

        }

//        Role role = roleRepository.findById(request.getRoleId())
//                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id : "+ request.getRoleId()));

        userMapper.updateEntity(request, user);
//        user.setRole(role);
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        log.info("User updated successfully : {}", id);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user : {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
        userRepository.delete(user);
        log.info("User deleted successfully : {}", id);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        log.info("Fetching user : {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponseDto enableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
        user.setEnabled(true);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDto disableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
        user.setEnabled(false);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDto lockUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
        user.setAccountLocked(true);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDto unlockUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
        user.setAccountLocked(false);
        return userMapper.toResponse(userRepository.save(user));
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
    }
}
