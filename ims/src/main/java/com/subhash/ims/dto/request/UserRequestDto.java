package com.subhash.ims.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phone;

    @NotNull
    private Long roleId;
}
