package org.example.cliniquepro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;
import org.example.cliniquepro.entity.User;
import org.example.cliniquepro.dto.UserDTO;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(UserDTO userDTO, @MappingTarget User user);

    List<UserDTO> toDtoList(List<User> users);
}
