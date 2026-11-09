package de.muenchen.oss.foerdermittel.backend.antrag.dto;

import de.muenchen.oss.foerdermittel.backend.antrag.Antrag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AntragMapper {

    @Mapping(source = "projekt.projnr", target = "projnr")
    @Mapping(source = "projekt.pname", target = "pname")
    @Mapping(source = "projekt.pstrasse", target = "pstrasse")
    @Mapping(source = "projekt.foerderbereich.fb", target = "fob_fb")
    @Mapping(source = "projekt.stadtbezirk.stadtbezirk", target = "bez_stadtbezirk")
    AntragResponseDTO toDTO(Antrag antrag);

    @Mapping(source = "projnr", target = "projekt.projnr")
    @Mapping(target = "id", ignore = true)
    Antrag toEntity(AntragCreateDTO antragCreateDTO);

    @Mapping(target = "projekt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Antrag toEntity(AntragUpdateDTO antragUpdateDTO);
}
