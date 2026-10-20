package de.muenchen.oss.foerdermittel.backend.report.auswertungprojekt;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.auswertungprojekt.dto.ReportAuswertungProjekteDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungprojekt.dto.ReportAuswertungProjekteMapper;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportAuswertungProjekteService {

    private final FoerderbereichService foerderbereichService;
    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;
    private final StadtbezirkService stadtbezirkService;
    private final KurzbezeichnungService kurzbezeichnungService;
    private final BauprogrammService bauprogrammService;
    private final UnterabschnittService unterabschnittService;
    private final SiedlungsgebietService siedlungsgebietService;

    private final ReportAuswertungProjekteMapper reportMapper;
    private final ReportService reportService;
    private final ProjektService projektService;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportAuswertungProjekt(
            final ReportAuswertungProjekteDTO parameters) {

        if (StringUtils.hasText(parameters.fb())) {
            foerderbereichService.checkExistsByFoerderbereich(
                    new BigDecimal(parameters.fb()));
        }

        if (StringUtils.hasText(parameters.sbl())) {
            listennameStadtbezirkslisteService.checkExistsByListenname(
                    parameters.sbl());
        }

        if (StringUtils.hasText(parameters.bez())) {
            stadtbezirkService.checkExistsByStadtbezirk(
                    new BigDecimal(parameters.bez()));
        }

        if (StringUtils.hasText(parameters.ua())) {
            unterabschnittService.checkExistsByUnterabschnitt(
                    parameters.ua());
        }

        if (StringUtils.hasText(parameters.sgt())) {
            siedlungsgebietService.checkExistsBySiedlungsgebiet(
                    new BigDecimal(parameters.sgt()));
        }

        if (StringUtils.hasText(parameters.kurz())) {
            kurzbezeichnungService.checkExistsByKurzbezeichnung(
                    parameters.kurz());
        }

        if (StringUtils.hasText(parameters.bpg())) {
            bauprogrammService.checkExistsByBauprogramm(
                    new BigDecimal(parameters.bpg()));
        }

        final String orderBy = parameters.sort().getOrderBy();

        return reportService.generateReport(
                reportMapper.toJasperParameters(parameters),
                ReportType.FMW_PROJEKTE,
                parameters.type(),
                orderBy);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportAuswertungProjektFormContext getReportAuswertungProjekt() {
        log.info("Get ReportAuswertungProjekte form context");

        return new ReportAuswertungProjektFormContext(
                projektService.getReportAuswertungProjektFormContextDTOs(),
                foerderbereichService.getFoerderbereichFormContextDTOs(),
                listennameStadtbezirkslisteService.getlistennameStadtbezirkslisteFormContextDTOs(),
                stadtbezirkService.getStadtbezirkFormContextDTOs(),
                unterabschnittService.getUnterabschnittFormContextDTOs(),
                kurzbezeichnungService.getKurzbezeichnungFormContextDTOs(),
                bauprogrammService.getBauprogrammFormContextDTOs(),
                siedlungsgebietService.getSiedlungsgebietFormContextDTOs(),
                List.of(Krisofp.values()));
    }
}
