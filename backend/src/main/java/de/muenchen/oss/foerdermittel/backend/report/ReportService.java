package de.muenchen.oss.foerdermittel.backend.report;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportAuswertungProjekteDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportMapper;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichworteDTO;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportAuswertungProjektFormContext;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportStichworteFormContext;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.StichwortbereichService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportService {

    public static final String SORT_PARAMETER = "P_SORT";
    private final FoerderbereichService foerderbereichService;
    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;
    private final StadtbezirkService stadtbezirkService;
    private final KurzbezeichnungService kurzbezeichnungService;
    private final BauprogrammService bauprogrammService;
    private final UnterabschnittService unterabschnittService;
    private final SiedlungsgebietService siedlungsgebietService;

    private final StichwortbereichService stichwortbereichService;
    private final JasperReportService jasperReportService;
    private final ReportMapper reportMapper;
    private final ProjektService projektService;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportStichworte(
            final ReportStichworteDTO parameters) {
        stichwortbereichService.checkExistsByBereich(parameters.bereich());
        return generateReport(reportMapper.toJasperParameters(parameters), ReportType.FMW_ABLAGEINDEX, ReportFormat.PDF,
                "ORDER BY stb_bereich ASC, nr ASC, wort ASC");
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportStichworteFormContext getReportStichworte() {
        log.info("Get ReportStichworte form context");
        return new ReportStichworteFormContext(stichwortbereichService.getStichwortbereichFormContextDTOs());
    }

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

        return generateReport(
                reportMapper.toJasperParameters(parameters),
                ReportType.FMW_PROJEKTE,
                parameters.type(),
                orderBy);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportAuswertungProjektFormContext getReportAuswertungProjekt() {
        log.info("Get ReportProjektuebersicht form context");

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

    /// Utility function to create a [GeneratedReport].
    ///
    /// @param jasperParameters parameters to fill the report with
    /// @param reportType type of the report to generate
    /// @param reportFormat format of the report to generate
    /// @param sort sort parameter (SQL statement) to use for the Jasper report (passed seperate due to
    ///            SQL injection prevention)
    /// @return the generated report with file metadata
    private GeneratedReport generateReport(
            final Map<String, Object> jasperParameters,
            final ReportType reportType,
            final ReportFormat reportFormat,
            final String sort) {

        checkReportFormat(reportType, reportFormat);

        if (sort != null) {
            jasperParameters.put(SORT_PARAMETER, sort);
        } else {
            jasperParameters.remove(SORT_PARAMETER);
        }

        return new GeneratedReport(
                getDownloadFileName(reportType, reportFormat),
                reportFormat.getContentType(),
                outputStream -> jasperReportService.generateReportWithParameters(
                        reportType,
                        reportFormat,
                        jasperParameters,
                        outputStream));
    }

    /// Checks if a given [ReportFormat] is valid for a given [ReportType].
    ///
    /// @param reportType the ReportType to check against
    /// @param reportFormat the ReportFormat to check
    private static void checkReportFormat(final ReportType reportType, final ReportFormat reportFormat) {
        if (!reportType.supportsFormat(reportFormat)) {
            throw new IllegalArgumentException(
                    "Unsupported ReportFormat: " + reportFormat + " for ReportType: " + reportType);
        }
    }

    /// Calculates the file name depending on the desires [ReportType] and [ReportFormat] and uses
    /// timestamps as prefixes
    ///
    /// @param reportType requested report type
    /// @param reportFormat requested report format
    /// @return file name of the file to be generated
    private static String getDownloadFileName(final ReportType reportType, final ReportFormat reportFormat) {
        final String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return reportType.getFileName() + reportFormat.getFileSuffix() + "_" + timestamp + reportFormat.getFileExtension();
    }

}
