package org.labcabrera.sample.users.interfaces.http.mappers;

import org.labcabrera.sample.users.domain.User;
import org.labcabrera.sample.users.interfaces.http.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDto toDto(User domain);

}
