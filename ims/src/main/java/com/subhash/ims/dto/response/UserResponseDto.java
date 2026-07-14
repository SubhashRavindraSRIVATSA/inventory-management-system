package com.subhash.ims.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Boolean enabled;

    private Boolean accountLocked;

    private String role;
}
