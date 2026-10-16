package de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag;

import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportFortsetzungsantragDTO;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportFortsetzungsantragFormContext;
import de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag.dto.FortsetzungsantragReportMapper;
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
public class FortsetzungsantragReportService {

    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;
    private final StadtbezirkService stadtbezirkService;
    private final ReportService reportService;
    private final FortsetzungsantragReportMapper fortsetzungsantragReportMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportFortsetzungsantrag(
            final ReportFortsetzungsantragDTO parameters) {
        if (StringUtils.hasText(parameters.sbl())) {
            listennameStadtbezirkslisteService.checkExistsByListenname(parameters.sbl());
        }
        if (StringUtils.hasText(parameters.bez())) {
            stadtbezirkService.checkExistsByStadtbezirk(new BigDecimal(parameters.bez()));
        }
        return reportService.generateReport(fortsetzungsantragReportMapper.toJasperParameters(parameters), ReportType.FMW_BEWILL4, parameters.type(),
                "order by v_fob_fb asc, v_projnr asc, v_bdatum asc");
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportFortsetzungsantragFormContext getReportFortsetzungsantrag() {
        log.info("Get ReportFortsetzungsantrag form context");
        return new ReportFortsetzungsantragFormContext(
                listennameStadtbezirkslisteService.getlistennameStadtbezirkslisteFormContextDTOs(),
                stadtbezirkService.getStadtbezirkFormContextDTOs());
    }

}
