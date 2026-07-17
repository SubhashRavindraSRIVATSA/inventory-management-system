package com.subhash.ims.mapper;

import com.subhash.ims.dto.request.CategoryRequestDto;
import com.subhash.ims.dto.response.CategoryResponseDto;
import com.subhash.ims.entity.Category;
import org.mapstruct.*;

@Mapper(config = MapStructConfig.class)
public interface CategoryMapper {

    //@Mapping(target = "id", ignore = true)
    @Mapping(target = "parentCategory", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    Category toEntity(CategoryRequestDto request);

    @Mapping(source = "parentCategory.id",
            target = "parentCategoryId")
    CategoryResponseDto toResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentCategory", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    void updateEntity(CategoryRequestDto request,
                      @MappingTarget Category category);
}
