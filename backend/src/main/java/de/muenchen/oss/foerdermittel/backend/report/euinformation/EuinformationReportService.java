package de.muenchen.oss.foerdermittel.backend.report.euinformation;

import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.EuinformationReportDTO;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.EuinformationReportMapper;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.formcontext.EuinformationReportFormContext;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EuinformationReportService {

    private final ReportService reportService;
    private final StadtbezirkService stadtbezirkService;
    private final EuinformationReportMapper euinformationReportMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportEuinformationen(
            final EuinformationReportDTO parameters) {

        return reportService.generateReport(euinformationReportMapper.toJasperParameters(parameters), ReportType.FMW_EUINFORMATIONEN, ReportFormat.PDF,
                "order [[replace]]");
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public EuinformationReportFormContext getReportEuinformationen() {
        log.info("Get ReportEuinformation form context");
        return new EuinformationReportFormContext(
                null
        );

    }

}
