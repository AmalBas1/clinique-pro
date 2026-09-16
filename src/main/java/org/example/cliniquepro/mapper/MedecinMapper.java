package org.example.cliniquepro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.example.cliniquepro.entity.Medecin;
import org.example.cliniquepro.dto.MedecinDTO;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MedecinMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rendezVous", ignore = true)
    Medecin toEntity(MedecinDTO medecinDTO);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(target = "rendezVousId", ignore = true)
    MedecinDTO toDTO(Medecin medecin);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "rendezVous", ignore = true)
    void updateEntityFromDTO(MedecinDTO medecinDTO, @MappingTarget Medecin medecin);

    List<MedecinDTO> toDtoList(List<Medecin> medecins);


}