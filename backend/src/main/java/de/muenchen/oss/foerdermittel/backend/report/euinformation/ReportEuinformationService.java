package de.muenchen.oss.foerdermittel.backend.report.euinformation;

import de.muenchen.oss.foerdermittel.backend.publikation.PublikationService;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.ReportEuinformationDTO;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.dto.ReportEuinformationMapper;
import de.muenchen.oss.foerdermittel.backend.report.euinformation.formcontext.ReportEuinformationFormContext;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportEuinformationService {

    private final ReportService reportService;
    private final PublikationService publikationService;
    private final ReportEuinformationMapper euinformationReportMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportEuinformationen(
            final ReportEuinformationDTO parameters) {

        return reportService.generateReport(euinformationReportMapper.toJasperParameters(parameters), ReportType.FMW_EUINFORMATIONEN, ReportFormat.PDF,
                "order by v_refbez, v_pub_kurzform asc, v_inhalt asc");
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportEuinformationFormContext getReportEuinformationen() {
        log.info("Get ReportEuinformation form context");
        return new ReportEuinformationFormContext(
                publikationService.getPublikationFormContextDTOs()
        );

    }

}
