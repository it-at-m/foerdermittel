package de.muenchen.oss.foerdermittel.backend.hhplan.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.hhplan.Hhplan;
import de.muenchen.oss.foerdermittel.backend.hhplan.dao.BasicHhplanDAO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = NumberMapper.class)
public interface HhplanMapper {

    @Mapping(source = "id.hhjJahr", target = "id", qualifiedByName = "bigDecimalToIntegerString")
    @Mapping(source = "id.hhjJahr", target = "hhjJahr")
    HhplanResponseDTO toDTO(Hhplan hhplan);

    @Mapping(source = "id.hhjJahr", target = "hhjJahr")
    HhplanFormContextDTO toFormContext(Hhplan hhplan);

    List<HhplanFormContextDTO> toFormContext(List<Hhplan> hhplanList);

    ReportHhplanFormContextDTO toFormContext(BasicHhplanDAO hhplan);

    List<HhplanFormContextDTO> toFormContextFromBasic(List<BasicHhplanDAO> hhplanList);
}
