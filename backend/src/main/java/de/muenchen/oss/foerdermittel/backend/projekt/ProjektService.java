package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportProjektuebersichtFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProjektService {

    private final ProjektRepository projektRepository;
    private final ProjektMapper projektMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Projekt getProjekt(final String projekt) {
        log.info("Get Projekt {}", projekt);
        return ServiceUtils.getEntityOrThrowNotFoundException(projekt, projektRepository, Projekt.class);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Projekt> getProjekte(
            Pageable pageable) {

        return projektRepository.findAll(
                pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public ProjektFormContext getProjektFormContext() {
        log.info("Get Projekt form context");
        return new ProjektFormContext(projektRepository.findAllProjekte());
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public List<ProjektFormContextDTO> getProjektFormContextDTOs() {
        return projektMapper.toFormContext(projektRepository.findAll());
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public List<ReportProjektuebersichtFormContextDTO> getReportProjektuebersichtFormContextDTOs() {
        return projektMapper.toReportProjektuebersichtFormContext(projektRepository.findAllAsBasic());
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public List<ReportAuswertungProjektFormContextDTO> getReportAuswertungProjektFormContextDTOs() {
        return projektMapper.toReportAuswertungProjektFormContext(projektRepository.findAllAuswertungProjekte());
    }
}
