package de.muenchen.oss.foerdermittel.backend.archiv.dto;

import de.muenchen.oss.foerdermittel.backend.archiv.Archiv;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ArchivMapper {

    @Mapping(source = "projekte.projnr", target = "projnr")
    @Mapping(source = "projekte.pname", target = "pname")
    @Mapping(source = "projekte.pstrasse", target = "pstrasse")
    @Mapping(source = "projekte.fob_fb", target = "fob_fb")
    ArchivResponseDTO toDTO(Archiv archiv);

    Archiv toEntity(ArchivCreateDTO archivCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projekte", ignore = true)
    Archiv toEntity(ArchivUpdateDTO dto);
}