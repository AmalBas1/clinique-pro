package org.example.cliniquepro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.example.cliniquepro.entity.RendezVous;
import org.example.cliniquepro.dto.RendezVousDTO;
import java.util.List;


@Mapper(componentModel = "spring")
public interface RendezVousMapper {

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "medecin", ignore = true)
    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "id", ignore = true)
    RendezVous toEntity(RendezVousDTO dto);

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "medecin.id", target = "medecinId")
    @Mapping(target = "messageId", ignore = true)
    @Mapping(target = "notificationId", ignore = true)
    RendezVousDTO toDTO(RendezVous rendezVous);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "medecin", ignore = true)
    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    void updateEntityFromDTO(RendezVousDTO dto, @MappingTarget RendezVous entity);

    List<RendezVousDTO> toDtoList(List<RendezVous> list);


}
