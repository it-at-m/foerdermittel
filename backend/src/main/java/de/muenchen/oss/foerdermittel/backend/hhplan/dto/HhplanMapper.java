package de.muenchen.oss.foerdermittel.backend.hhplan.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.hhplan.Hhplan;
import de.muenchen.oss.foerdermittel.backend.hhplan.dao.BasicHhplanDAO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = NumberMapper.class)
public interface HhplanMapper {

    @Mapping(source = "id.hhjJahr", target = "id", qualifiedByName = "bigDecimalToIntegerString")
    @Mapping(source = "id.proProjnr", target = "proProjnr")
    @Mapping(source = "hhjJahr.hhjahr", target = "hhjJahr")
    @Mapping(source = "proProjnr.pname", target = "pname")
    @Mapping(source = "proProjnr.pstrasse", target = "pstrasse")
    @Mapping(source = "proProjnr.fobFb.fb", target = "fobFb")
    HhplanResponseDTO toDTO(Hhplan hhplan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hhjJahr", ignore = true)
    @Mapping(target = "proProjnr", ignore = true)
    Hhplan toEntity(HhplanResponseDTO dto);

    @Mapping(source = "proProjnr.projnr", target = "proProjnr")
    @Mapping(source = "hhjJahr.hhjahr", target = "hhjJahr")
    HhplanFormContextDTO toFormContext(Hhplan hhplan);

    List<HhplanFormContextDTO> toFormContext(List<Hhplan> hhplanList);

    ReportHhplanFormContextDTO toFormContext(BasicHhplanDAO hhplan);

    List<HhplanFormContextDTO> toFormContextFromBasic(List<BasicHhplanDAO> hhplanList);
}
