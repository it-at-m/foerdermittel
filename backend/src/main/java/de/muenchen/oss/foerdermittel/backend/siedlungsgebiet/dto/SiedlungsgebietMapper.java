package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto;

import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.Siedlungsgebiet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SiedlungsgebietMapper {

    @Mapping(source = "siedlungsgebiet", target = "id")
    @Mapping(source = "siedlungsgebiet", target = "siedlungsgebiet")
    SiedlungsgebietResponseDTO toDTO(Siedlungsgebiet siedlungsgebiet);

    Siedlungsgebiet toEntity(SiedlungsgebietCreateDTO siedlungsgebietCreateDTO);

    @Mapping(target = "siedlungsgebiet", ignore = true)
    Siedlungsgebiet toEntity(SiedlungsgebietUpdateDTO siedlungsgebietUpdateDTO);

}
