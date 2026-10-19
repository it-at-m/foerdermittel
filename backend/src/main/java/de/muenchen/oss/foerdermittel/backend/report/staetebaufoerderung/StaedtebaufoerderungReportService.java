package de.muenchen.oss.foerdermittel.backend.report.staetebaufoerderung;

import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.staetebaufoerderung.dto.ReportStaedtebaufoerderungDTO;
import de.muenchen.oss.foerdermittel.backend.report.staetebaufoerderung.dto.StaedtebaufoerderungReportMapper;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.traeger.TraegerService;
import java.math.BigDecimal;
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
public class StaedtebaufoerderungReportService {

    private final TraegerService traegerService;
    private final ReportService reportService;
    private final StaedtebaufoerderungReportMapper reportStaedtebaufoerderungReportMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportStaedtebaufoerderung(
            final ReportStaedtebaufoerderungDTO parameters) {
        if (StringUtils.hasText(parameters.traeger())) {
            traegerService.checkExistsByTraeger(new BigDecimal(parameters.traeger()));
        }
        return reportService.generateReport(reportStaedtebaufoerderungReportMapper.toJasperParameters(parameters), ReportType.FMW_STAEDTEBAU, ReportFormat.PDF,
                "ORDER BY bdat ASC");
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportStaedtebaufoerderungFormContext getReportStaedtebaufoerderung() {
        log.info("Get ReportStaedtebaufoerderung form context");
        return new ReportStaedtebaufoerderungFormContext(
                traegerService.getTraegerFormContextDTOs());
    }
}
