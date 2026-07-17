package com.subhash.ims.mapper;

import com.subhash.ims.dto.request.ProductRequestDto;
import com.subhash.ims.dto.response.ProductResponseDto;
import com.subhash.ims.entity.Product;
import org.mapstruct.*;

@Mapper(config = MapStructConfig.class)
public interface ProductMapper {

    //@Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "status", ignore = true)
    Product toEntity(ProductRequestDto request);

    @Mapping(source = "category.id",
            target = "categoryId")

    @Mapping(source = "category.name",
            target = "categoryName")
    ProductResponseDto toResponse(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntity(ProductRequestDto request,
                      @MappingTarget Product product);
}
