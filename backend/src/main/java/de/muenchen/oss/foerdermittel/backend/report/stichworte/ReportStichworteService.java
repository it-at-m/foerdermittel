package de.muenchen.oss.foerdermittel.backend.report.stichworte;

import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.stichworte.dto.ReportStichworteDTO;
import de.muenchen.oss.foerdermittel.backend.report.stichworte.dto.ReportStichworteMapper;
import de.muenchen.oss.foerdermittel.backend.report.stichworte.formcontext.ReportStichworteFormContext;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.StichwortbereichService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportStichworteService {

    static final String SORT = "ORDER BY stb_bereich ASC, nr ASC, wort ASC";

    private final StichwortbereichService stichwortbereichService;
    private final ReportService reportService;
    private final ReportStichworteMapper reportStichworteMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportStichworte(
            final ReportStichworteDTO parameters) {
        stichwortbereichService.checkExistsByBereich(parameters.bereich());
        return reportService.generateReport(reportStichworteMapper.toJasperParameters(parameters), ReportType.FMW_ABLAGEINDEX, ReportFormat.PDF, SORT);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportStichworteFormContext getReportStichworteFormContext() {
        log.info("Get ReportStichworte form context");
        return new ReportStichworteFormContext(stichwortbereichService.getStichwortbereichFormContextDTOs());
    }
}
