package com.gntech.api.mappers;

import com.gntech.api.domain.UserDomain;
import com.gntech.api.dto.UserDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true)
)
public interface UserMapper {

    UserDTO entityToDto(UserDomain userDomain);

    UserDomain dtoToEntity(UserDTO userDTO);
}
