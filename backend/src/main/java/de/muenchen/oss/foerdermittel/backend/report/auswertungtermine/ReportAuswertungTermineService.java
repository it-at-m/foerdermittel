package de.muenchen.oss.foerdermittel.backend.report.auswertungtermine;

import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto.ReportAuswertungTermineDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto.ReportAuswertungTermineMapper;
import de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.formcontext.ReportAuswertungTermineFormContext;
import de.muenchen.oss.foerdermittel.backend.report.validation.CommonSearchParameters;
import de.muenchen.oss.foerdermittel.backend.report.validation.ReportSearchParameterValidator;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportAuswertungTermineService {

    private final FoerderbereichService foerderbereichService;
    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;
    private final StadtbezirkService stadtbezirkService;
    private final KurzbezeichnungService kurzbezeichnungService;
    private final UnterabschnittService unterabschnittService;
    private final ReportAuswertungTermineMapper reportMapper;
    private final ReportService reportService;

    private final ReportSearchParameterValidator reportSearchParameterValidator;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportAuswertungTermine(
            final ReportAuswertungTermineDTO parameters) {

        reportSearchParameterValidator.validateCommonSearchParameters(
                new CommonSearchParameters(
                        parameters.fb(),
                        parameters.sbl(),
                        parameters.bez(),
                        parameters.ua(),
                        null,
                        parameters.kurz(),
                        null));

        final String orderBy = parameters.sort().getOrderBy();

        return reportService.generateReport(
                reportMapper.toJasperParameters(parameters),
                ReportType.FMW_TERMINE,
                ReportFormat.PDF,
                orderBy);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportAuswertungTermineFormContext getReportAuswertungTermineFormContext() {
        log.info("Get ReportAuswertungTermine form context");

        return new ReportAuswertungTermineFormContext(
                listennameStadtbezirkslisteService.getlistennameStadtbezirkslisteFormContextDTOs(),
                stadtbezirkService.getStadtbezirkFormContextDTOs(),
                foerderbereichService.getFoerderbereichFormContextDTOs(),
                unterabschnittService.getUnterabschnittFormContextDTOs(),
                kurzbezeichnungService.getKurzbezeichnungFormContextDTOs());

    }
}
