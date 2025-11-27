package org.labcabrera.sample.users.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.users.domain.User;
import org.labcabrera.sample.users.infrastructure.persistence.jpa.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    User toDomain(UserEntity entity);

    @Mapping(target = "version", ignore = true)
    UserEntity toEntity(User domain);

}
