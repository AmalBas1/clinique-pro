package org.example.cliniquepro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.example.cliniquepro.entity.Notification;
import org.example.cliniquepro.dto.NotificationDTO;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "rendezVous", ignore = true)
    @Mapping(target = "id", ignore = true)
    Notification toEntity(NotificationDTO notificationDTO);

    @Mapping(source = "rendezVous.id", target = "rendezVousId")
    NotificationDTO toDTO(Notification notification);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rendezVous", ignore = true)
    void updateEntityFromDTO(NotificationDTO notificationDTO, @MappingTarget Notification notification);

    List<NotificationDTO> toDtoList(List<Notification> notifications);
}
