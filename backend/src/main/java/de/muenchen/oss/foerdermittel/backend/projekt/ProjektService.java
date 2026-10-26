package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichRepository;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.*;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittRepository;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;

import java.math.BigDecimal;
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
    private final FoerderbereichRepository foerderbereichRepository;
    private final KurzbezeichnungRepository kurzbezeichnungRepository;
    private final UnterabschnittRepository unterabschnittRepository;
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
            ProjektFilter filter,
            Pageable pageable) {

        return projektRepository.findAll(
                ProjektSpecifications.filter(filter),
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

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Projekt createProjekt(final ProjektCreateDTO projektCreateDTO) {
        log.debug("Create Projekt {}", projektCreateDTO);

        final Projekt projekt = projektMapper.toEntity(projektCreateDTO);

        projekt.setProjnr(projektCreateDTO.projnr());
        projekt.setFoerderbereich(ServiceUtils.getEntityOrThrowNotFoundException(
                BigDecimal.valueOf(projektCreateDTO.fobFb()), foerderbereichRepository, Foerderbereich.class));
        projekt.setKurzbezeichnung(ServiceUtils.getEntityOrThrowNotFoundException(
                projektCreateDTO.kurzbez(), kurzbezeichnungRepository, Kurzbezeichnung.class));
        projekt.setUnterabschnitt(ServiceUtils.getEntityOrThrowNotFoundException(
                projektCreateDTO.uasUa(), unterabschnittRepository, Unterabschnitt.class));

        // Defaultwerte für NOT-NULL-Felder
        if (projekt.getKauf() == null) {
            projekt.setKauf(false);
        }
        if (projekt.getRefinanzierbar() == null) {
            projekt.setRefinanzierbar(true);
        }

        return projektRepository.insert(projekt);
    }


    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Projekt updateProjekt(final Projekt projekt, final String ha) {
        final Projekt foundProjekt = ServiceUtils.getEntityOrThrowNotFoundException(ha, projektRepository, Projekt.class);
        foundProjekt.setPname(projekt.getPname());
        log.debug("Update Projekt {}", foundProjekt);
        return projektRepository.update(foundProjekt);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteProjekt(final String ha) {
        log.debug("Delete Projekt with ID {}", ha);
        ServiceUtils.getEntityOrThrowNotFoundException(ha, projektRepository, Projekt.class);
        projektRepository.deleteById(ha);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public List<ReportAuswertungProjektFormContextDTO> getReportAuswertungProjektFormContextDTOs() {
        return projektMapper.toReportAuswertungProjektFormContext(projektRepository.findAllAuswertungProjekte());
    }

}
