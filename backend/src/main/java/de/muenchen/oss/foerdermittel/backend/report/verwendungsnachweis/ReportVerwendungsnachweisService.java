package de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.validation.CommonSearchParameters;
import de.muenchen.oss.foerdermittel.backend.report.validation.ReportSearchParameterValidator;
import de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis.dto.ReportVerwendungsnachweisDTO;
import de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis.dto.ReportVerwendungsnachweisMapper;
import de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis.formcontext.ReportVerwendungsnachweisFormContext;
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

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportVerwendungsnachweisService {
    private final FoerderbereichService foerderbereichService;
    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;
    private final StadtbezirkService stadtbezirkService;
    private final KurzbezeichnungService kurzbezeichnungService;
    private final BauprogrammService bauprogrammService;
    private final UnterabschnittService unterabschnittService;
    private final SiedlungsgebietService siedlungsgebietService;
    private final KrankenhausService krankenhausService;
    private final ReportVerwendungsnachweisMapper reportVerwendungsnachweisMapper;
    private final ReportService reportService;

    private final ReportSearchParameterValidator reportSearchParameterValidator;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportVerwendungsnachweis(
            final ReportVerwendungsnachweisDTO parameters) {

        reportSearchParameterValidator.validateCommonSearchParameters(
                new CommonSearchParameters(
                        parameters.fb(),
                        parameters.sbl(),
                        parameters.bez(),
                        parameters.ua(),
                        parameters.sgt(),
                        parameters.kurz(),
                        parameters.bpg()));

        final String orderBy = parameters.sort().getOrderBy();

        return reportService.generateReport(
                reportVerwendungsnachweisMapper.toJasperParameters(parameters),
                ReportType.FMW_VN,
                parameters.type(),
                orderBy);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportVerwendungsnachweisFormContext getReportVerwendungsnachweisFormContext() {
        log.info("Get ReportAuswertungProjekte form context");

        return new ReportVerwendungsnachweisFormContext(
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
