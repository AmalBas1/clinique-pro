package org.example.cliniquepro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.example.cliniquepro.entity.Message;
import org.example.cliniquepro.dto.MessageDTO;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "rendezVous", ignore = true)
    @Mapping(target = "id", ignore = true)
    Message toEntity(MessageDTO messageDTO);

    @Mapping(source = "rendezVous.id", target = "rendezVousId")
    MessageDTO toDTO(Message message);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rendezVous", ignore = true)
    void updateEntityFromDTO(MessageDTO messageDTO, @MappingTarget Message message);

    List<MessageDTO> toDtoList(List<Message> messages);
}
