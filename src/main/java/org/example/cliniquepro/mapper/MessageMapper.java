package org.example.cliniquepro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.example.cliniquepro.entity.Message;
import org.example.cliniquepro.dto.MessageDTO;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "notification", ignore = true)
    @Mapping(target = "id", ignore = true)
    Message toEntity(MessageDTO messageDTO);

    @Mapping(source = "notification.rendezVous.id", target = "rendezVousId")
    @Mapping(source = "notification.id", target = "notificationId")
    MessageDTO toDTO(Message message);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notification", ignore = true)
    void updateEntityFromDTO(MessageDTO messageDTO, @MappingTarget Message message);

    List<MessageDTO> toDtoList(List<Message> messages);
}
