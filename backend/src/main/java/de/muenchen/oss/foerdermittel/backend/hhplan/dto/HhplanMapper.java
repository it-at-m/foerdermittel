
package de.muenchen.oss.foerdermittel.backend.hhplan.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.hhplan.Hhplan;
import de.muenchen.oss.foerdermittel.backend.hhplan.dao.BasicHhplanDAO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                NumberMapper.class,
                ListennameStadtbezirkslisteMapper.class
        }
)
public interface HhplanMapper {

    @Mapping(
            target = "id",
            expression = "java(hhplan.getId().getHhjJahr().toPlainString() + \"-\" + hhplan.getId().getProProjnr())"
    )
    @Mapping(source = "id.proProjnr", target = "proProjnr")
    @Mapping(source = "hhjJahr.hhjahr", target = "hhjJahr")
    @Mapping(source = "proProjnr.pname", target = "pname")
    @Mapping(source = "proProjnr.pstrasse", target = "pstrasse")
    @Mapping(source = "proProjnr.fobFb.fb", target = "fobFb")
    @Mapping(source = "proProjnr.bezStadtbezirk.stadtbezirk", target = "stadtbezirk")
    @Mapping(source = "proProjnr.bezStadtbezirk", target = "listenname")
    @Mapping(source = "anlagevon", target = "anlagevon")
    @Mapping(source = "anlagedatum", target = "anlagedatum")
    @Mapping(source = "aenderungvon", target = "aenderungvon")
    @Mapping(source = "aenderungsdatum", target = "aenderungsdatum")
    HhplanResponseDTO toDTO(Hhplan hhplan);

    default String mapStadtbezirkToListenname(Stadtbezirk stadtbezirk) {
        if (stadtbezirk == null || stadtbezirk.getStadtbezirkslisten() == null
                || stadtbezirk.getStadtbezirkslisten().isEmpty()) {
            return null;
        }

        return stadtbezirk.getStadtbezirkslisten().get(0).getListenName().getKurzbez();
    }

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
