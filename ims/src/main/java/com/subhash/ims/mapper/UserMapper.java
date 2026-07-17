package com.subhash.ims.mapper;

import com.subhash.ims.dto.request.UserRequestDto;
import com.subhash.ims.dto.response.UserResponseDto;
import com.subhash.ims.entity.User;
import org.mapstruct.*;

@Mapper(config = MapStructConfig.class)
public interface UserMapper {

    //@Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserRequestDto request);

    @Mapping(source = "role.name",
            target = "role")
    UserResponseDto toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateEntity(UserRequestDto request, @MappingTarget User user);
}
