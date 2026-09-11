package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProjektMapper {

    @Mapping(source = "foerderbereich.fb", target = "foerderbereich")
    @Mapping(source = "stadtbezirk.stadtbezirk", target = "stadtbezirk")
    ProjektResponseDTO toDTO(Projekt projekt);

    @Mapping(source = "foerderbereich.fb", target = "foerderbereich")
    @Mapping(source = "stadtbezirk.stadtbezirk", target = "stadtbezirk")
    ProjektFormContextDTO toFormContext(Projekt projekt);

    List<ProjektFormContextDTO> toFormContext(List<Projekt> projektList);
}
