package de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis.dto.ReportAuswertungVerwendungsnachweisDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis.dto.ReportAuswertungVerwendungsnachweisMapper;
import de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis.formcontext.ReportAuswertungVerwendungsnachweisFormContext;
import de.muenchen.oss.foerdermittel.backend.report.validation.CommonSearchParameters;
import de.muenchen.oss.foerdermittel.backend.report.validation.ReportSearchParameterValidator;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
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
public class ReportAuswertungVerwendungsnachweisService {
    private final FoerderbereichService foerderbereichService;
    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;
    private final StadtbezirkService stadtbezirkService;
    private final KurzbezeichnungService kurzbezeichnungService;
    private final BauprogrammService bauprogrammService;
    private final UnterabschnittService unterabschnittService;
    private final SiedlungsgebietService siedlungsgebietService;
    private final KrankenhausService krankenhausService;
    private final ReportAuswertungVerwendungsnachweisMapper reportAuswertungVerwendungsnachweisMapper;
    private final ReportService reportService;
    private final ProjektService projektService;

    private final ReportSearchParameterValidator reportSearchParameterValidator;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportAuswertungVerwendungsnachweis(
            final ReportAuswertungVerwendungsnachweisDTO parameters) {

        reportSearchParameterValidator.validateCommonSearchParameters(
                new CommonSearchParameters(
                        parameters.fb(),
                        parameters.sbl(),
                        parameters.bez(),
                        parameters.ua(),
                        parameters.sgt(),
                        parameters.kurz(),
                        parameters.bpg()));

        if (StringUtils.hasText(parameters.krh())) {
            krankenhausService.checkExistsByKrankenhaus(parameters.krh());
        }

        final String orderBy = parameters.sort().getOrderBy();

        return reportService.generateReport(
                reportAuswertungVerwendungsnachweisMapper.toJasperParameters(parameters),
                ReportType.FMW_VN,
                parameters.type(),
                orderBy);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportAuswertungVerwendungsnachweisFormContext getReportAuswertungVerwendungsnachweisFormContext() {
        log.info("Get ReportAuswertungVerwendungsnachweis form context");

        return new ReportAuswertungVerwendungsnachweisFormContext(
                projektService.getReportAuswertungVerwendungsnachweisFormContextDTOs(),
                foerderbereichService.getFoerderbereichFormContextDTOs(),
                listennameStadtbezirkslisteService.getlistennameStadtbezirkslisteFormContextDTOs(),
                stadtbezirkService.getStadtbezirkFormContextDTOs(),
                unterabschnittService.getUnterabschnittFormContextDTOs(),
                kurzbezeichnungService.getKurzbezeichnungFormContextDTOs(),
                bauprogrammService.getBauprogrammFormContextDTOs(),
                siedlungsgebietService.getSiedlungsgebietFormContextDTOs(),
                krankenhausService.getKrankenhausFormContextDTOs(),
                List.of(Krisofp.values()));
    }
}
