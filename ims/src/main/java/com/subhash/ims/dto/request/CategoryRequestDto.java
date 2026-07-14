package com.subhash.ims.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDto {

    @NotBlank
    private String name;

    private String description;

    private Boolean active;

    private Long parentCategoryId;
}
