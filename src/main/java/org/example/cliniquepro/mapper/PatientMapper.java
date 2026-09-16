package org.example.cliniquepro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.example.cliniquepro.entity.Patient;
import org.example.cliniquepro.dto.PatientDTO;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "rendezVous", ignore = true)
    @Mapping(target = "medecin", ignore = true)
    Patient toEntity(PatientDTO patientDTO);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "medecin.id", target = "medecinId")
    @Mapping(target = "rendezVousIds", ignore = true)
    PatientDTO toDTO(Patient patient);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "rendezVous", ignore = true)
    @Mapping(target = "medecin", ignore = true)
    void updateEntityFromDTO(PatientDTO patientDTO, @MappingTarget Patient patient);

    List<PatientDTO> toDtoList(List<Patient> patients);


}
