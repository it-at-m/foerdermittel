package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportProjektuebersichtFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProjektService {

    private final ProjektRepository projektRepository;
    private final ProjektMapper projektMapper;

    public Projekt getProjekt(final String projnr) {
        return ServiceUtils.getEntityOrThrowNotFoundException(projnr, projektRepository, Projekt.class);
    }

    public List<ReportProjektuebersichtFormContextDTO> getReportProjektuebersichtFormContextDTOs() {
        return projektMapper.toReportFormContext(projektRepository.findAll());
    }
}
