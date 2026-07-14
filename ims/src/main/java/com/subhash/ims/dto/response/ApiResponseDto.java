package com.subhash.ims.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponseDto<T> {

    private LocalDateTime timestamp;

    private int status;

    private String message;

    private T data;
}
