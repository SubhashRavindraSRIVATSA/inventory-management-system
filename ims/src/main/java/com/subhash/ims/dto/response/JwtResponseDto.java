package com.subhash.ims.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponseDto {
    private String token;

    private String tokenType;

    private Long userId;

    private String fullName;

    private String email;

    private String role;
}
