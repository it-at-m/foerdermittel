package de.muenchen.oss.foerdermittel.backend.hhjahr.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.hhjahr.Hhjahr;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                NumberMapper.class,
                HhplanMapper.class
        }
)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface HhjahrMapper {

    @Mapping(source = "hhjahr", target = "id", qualifiedByName = "bigDecimalToIntegerString")
    @Mapping(source = "hhjahr", target = "hhjahr")
    @Mapping(source = "hhplanlisten", target = "assignedProProjnr")
    HhjahrResponseDTO toDTO(Hhjahr hhjahr);

    @Mapping(source = "assignedProProjnr", target = "hhplanlisten")
    Hhjahr toEntity(HhjahrCreateDTO hhjahrCreateDTO);

    @Mapping(target = "hhjahr", ignore = true)
    @Mapping(source = "assignedProProjnr", target = "hhplanlisten")
    Hhjahr toEntity(HhjahrUpdateDTO hhjahrUpdateDTO);

    HhjahrFormContextDTO toFormContext(Hhjahr hhjahr);

    List<HhjahrFormContextDTO> toFormContext(List<Hhjahr> hhjahrList);

}
