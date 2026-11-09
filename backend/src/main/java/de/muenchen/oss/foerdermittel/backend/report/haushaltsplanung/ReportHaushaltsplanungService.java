package de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung;

import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.hhplan.HhplanService;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung.dto.ReportHaushaltsplanungDTO;
import de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung.dto.ReportHaushaltsplanungMapper;
import de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung.formcontext.ReportHaushaltsplanungFormContext;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
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
public class ReportHaushaltsplanungService {

    private final FoerderbereichService foerderbereichService;
    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;
    private final StadtbezirkService stadtbezirkService;
    private final HhplanService hhplanService;
    private final ReportService reportService;
    private final ReportHaushaltsplanungMapper reportHaushaltsplanungMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportHaushaltsplanung(
            final ReportHaushaltsplanungDTO parameters) {

        if (StringUtils.hasText(parameters.fb())) {
            foerderbereichService.checkExistsByFoerderbereich(new BigDecimal(parameters.fb()));
        }
        if (StringUtils.hasText(parameters.sbl())) {
            listennameStadtbezirkslisteService.checkExistsByListenname(parameters.sbl());
        }
        if (StringUtils.hasText(parameters.bez())) {
            stadtbezirkService.checkExistsByStadtbezirk(new BigDecimal(parameters.bez()));
        }

        final String orderBy = parameters.sort().getOrderBy();

        return reportService.generateReport(reportHaushaltsplanungMapper.toJasperParameters(parameters), ReportType.FMW_HAUSHALT1, parameters.type(),
                orderBy);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportHaushaltsplanungFormContext getReportHaushaltsplanungFormContext() {
        log.info("Get ReportHaushaltsplanung form context");
        return new ReportHaushaltsplanungFormContext(
                foerderbereichService.getFoerderbereichFormContextDTOs(),
                listennameStadtbezirkslisteService.getlistennameStadtbezirkslisteFormContextDTOs(),
                stadtbezirkService.getStadtbezirkFormContextDTOs(),
                hhplanService.getHhplanFormContextDTOs());

    }
}
