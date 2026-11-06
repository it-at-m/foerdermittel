package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.AuswertungProjektDAO;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjektMapper {

    String FOB_FB = "fobFb";
    String UAS_UA = "uasUa";
    String KUR_KURZBEZ = "kurKurzbez";
    String BEZ_STADTBEZIRK = "bezStadtbezirk";
    String KRN_KRHNAME = "krnKrhname";
    String SGT_SIEDLUNGSGEBIET = "sgtSiedlungsgebiet";
    String BPG_BAUPROGRAMM = "bpgBauprogramm";

    @Mapping(source = "projnr", target = "id")
    @Mapping(source = "fobFb.fb", target = FOB_FB)
    @Mapping(source = "uasUa.ua", target = UAS_UA)
    @Mapping(source = "kurKurzbez.kurzbez", target = KUR_KURZBEZ)
    @Mapping(source = "bezStadtbezirk.stadtbezirk", target = BEZ_STADTBEZIRK)
    @Mapping(source = "krnKrhname.krhname", target = KRN_KRHNAME)
    @Mapping(source = "sgtSiedlungsgebiet.siedlungsgebiet", target = SGT_SIEDLUNGSGEBIET)
    @Mapping(source = "bpgBauprogramm.bauprogramm", target = BPG_BAUPROGRAMM)
    ProjektResponseDTO toDTO(Projekt projekt);

    @Mapping(target = FOB_FB, ignore = true)
    @Mapping(target = UAS_UA, ignore = true)
    @Mapping(target = KUR_KURZBEZ, ignore = true)
    @Mapping(target = KRN_KRHNAME, ignore = true)
    @Mapping(target = BEZ_STADTBEZIRK, ignore = true)
    @Mapping(target = SGT_SIEDLUNGSGEBIET, ignore = true)
    @Mapping(target = BPG_BAUPROGRAMM, ignore = true)
    Projekt toEntity(ProjektCreateDTO projektCreateDTO);

    @Mapping(target = "projnr", ignore = true)
    @Mapping(target = "jahr", ignore = true)
    @Mapping(target = "lfdnr1", ignore = true)
    @Mapping(target = "lfdnr2", ignore = true)
    @Mapping(target = UAS_UA, ignore = true)
    @Mapping(target = FOB_FB, ignore = true)
    @Mapping(target = KUR_KURZBEZ, ignore = true)
    @Mapping(target = KRN_KRHNAME, ignore = true)
    @Mapping(target = BEZ_STADTBEZIRK, ignore = true)
    @Mapping(target = SGT_SIEDLUNGSGEBIET, ignore = true)
    @Mapping(target = BPG_BAUPROGRAMM, ignore = true)
    Projekt toEntity(ProjektUpdateDTO projektUpdateDTO);

    @Mapping(source = "fobFb.fb", target = FOB_FB)
    @Mapping(source = "uasUa.ua", target = UAS_UA)
    @Mapping(source = "kurKurzbez.kurzbez", target = KUR_KURZBEZ)
    @Mapping(source = "bezStadtbezirk.stadtbezirk", target = BEZ_STADTBEZIRK)
    @Mapping(source = "krnKrhname.krhname", target = KRN_KRHNAME)
    @Mapping(source = "sgtSiedlungsgebiet.siedlungsgebiet", target = SGT_SIEDLUNGSGEBIET)
    @Mapping(source = "bpgBauprogramm.bauprogramm", target = BPG_BAUPROGRAMM)
    ProjektFormContextDTO toFormContext(Projekt projekt);

    List<ProjektFormContextDTO> toFormContext(List<Projekt> projektList);

    List<ReportProjektuebersichtFormContextDTO> toReportProjektuebersichtFormContext(
            List<BasicProjektDAO> projekte);

    List<ReportAuswertungProjektFormContextDTO> toReportAuswertungProjektFormContext(
            List<AuswertungProjektDAO> projekte);

    List<ReportBewilligungAuszahlungProjektFormContextDTO> toReportBewilligungAuszahlungProjektFormContext(
            List<BasicProjektDAO> projekte);

    List<ReportAnnahmeAnordnungSapFormContextDTO> toReportAnnahmeAnordnungSapFormContext(
            List<BasicProjektDAO> projekte);

    List<ReportAuswertungAntragFormContextDTO> toReportAuswertungAntragFormContext(
            List<BasicProjektDAO> projekte);
}
