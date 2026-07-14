package com.subhash.ims.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDto {

    private Long id;

    private String name;

    private String description;

    private Boolean active;

    private Long parentCategoryId;
}
