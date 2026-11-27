package de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv;

import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.dto.ReportAuswertungArchivDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.dto.ReportAuswertungArchivMapper;
import de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.formcontext.ReportAuswertungArchivFormContext;
import de.muenchen.oss.foerdermittel.backend.report.validation.CommonSearchParameters;
import de.muenchen.oss.foerdermittel.backend.report.validation.ReportSearchParameterValidator;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportAuswertungArchivService {

    private final FoerderbereichService foerderbereichService;
    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;
    private final StadtbezirkService stadtbezirkService;
    private final KurzbezeichnungService kurzbezeichnungService;
    private final ReportService reportService;
    private final ProjektService projektService;
    private final ReportAuswertungArchivMapper reportMapper;
    private final ReportSearchParameterValidator reportSearchParameterValidator;
    private final UnterabschnittService unterabschnittService;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportAuswertungArchiv(
            final ReportAuswertungArchivDTO parameters) {

        reportSearchParameterValidator.validateCommonSearchParameters(
                new CommonSearchParameters(
                        parameters.fb(),
                        null,
                        parameters.bez(),
                        parameters.ua(),
                        null,
                        parameters.kurz(),
                        null));

        final String orderBy = parameters.sort().getOrderBy();

        return reportService.generateReport(
                reportMapper.toJasperParameters(parameters),
                ReportType.FMW_ARCHIV,
                ReportFormat.PDF,
                orderBy);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportAuswertungArchivFormContext getReportAuswertungArchivFormContext() {
        log.info("Get ReportAuswertungArchiv form context");

        return new ReportAuswertungArchivFormContext(
                stadtbezirkService.getStadtbezirkFormContextDTOs(),
                foerderbereichService.getFoerderbereichFormContextDTOs(),
                unterabschnittService.getUnterabschnittFormContextDTOs(),
                kurzbezeichnungService.getKurzbezeichnungFormContextDTOs(),
                projektService.getReportAuswertungArchivFormContextDTOs(),
                List.of(Krisofp.values()));

    }
}
