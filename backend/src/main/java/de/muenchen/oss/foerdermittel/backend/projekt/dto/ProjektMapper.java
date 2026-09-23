package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjektMapper {

//    @Mapping(source = "foerderbereich.fb", target = "foerderbereich")
    ProjektResponseDTO toDTO(Projekt projekt);

    @Mapping(source = "foerderbereich.fb", target = "foerderbereich")
    ProjektFormContextDTO toFormContext(Projekt projekt);

    List<ProjektFormContextDTO> toFormContext(List<Projekt> projektList);

    Projekt toEntity(ProjektCreateDTO projektCreateDTO);

    @Mapping(target = "projnr", ignore = true)
    Projekt toEntity(ProjektUpdateDTO projektUpdateDTO);
}
