package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektCreateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportBewilligungAuszahlungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportProjektuebersichtFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
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
    private final FoerderbereichService foerderbereichService;
    private final StadtbezirkService stadtbezirkService;
    private final UnterabschnittService unterabschnittService;
    private final KurzbezeichnungService kurzbezeichnungService;
    private final BauprogrammService bauprogrammService;
    private final SiedlungsgebietService siedlungsgebietService;
    private final KrankenhausService krankenhausService;

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

        return new ProjektFormContext(
                projektMapper.toFormContext(projektRepository.findAll()),
                foerderbereichService.getFoerderbereichFormContextDTOs(),
                stadtbezirkService.getStadtbezirkFormContextDTOs(),
                unterabschnittService.getUnterabschnittFormContextDTOs(),
                kurzbezeichnungService.getKurzbezeichnungFormContextDTOs(),
                bauprogrammService.getBauprogrammFormContextDTOs(),
                siedlungsgebietService.getSiedlungsgebietFormContextDTOs(),
                krankenhausService.getKrankenhausFormContextDTOs(),
                List.of(Krisofp.values()),
                List.of(Krhzweck.values()));
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

        projekt.setFobFb(foerderbereichService.getFoerderbereich(BigDecimal.valueOf(projektCreateDTO.fobFb())));
        projekt.setKurKurzbez(kurzbezeichnungService.getKurzbezeichnung(projektCreateDTO.kurKurzbez()));
        projekt.setUasUa(unterabschnittService.getUnterabschnitt(projektCreateDTO.uasUa()));

        projekt.setStadtbezirk(
                projektCreateDTO.stadtbezirk() == null ? null : stadtbezirkService.getStadtbezirk(BigDecimal.valueOf(projektCreateDTO.stadtbezirk())));
        projekt.setKrankenhaus(projektCreateDTO.krhname() == null || projektCreateDTO.krhname().isBlank() ? null
                : krankenhausService.getKrankenhaus(projektCreateDTO.krhname()));
        projekt.setSiedlungsgebiet(projektCreateDTO.siedlungsgebiet() == null ? null
                : siedlungsgebietService.getSiedlungsgebiet(BigDecimal.valueOf(projektCreateDTO.siedlungsgebiet())));
        projekt.setBauprogramm(
                projektCreateDTO.bauprogramm() == null ? null : bauprogrammService.getBauprogramm(BigDecimal.valueOf(projektCreateDTO.bauprogramm())));

        projekt.setKauf(projekt.getKauf() != null && projekt.getKauf());
        projekt.setRefinanzierbar(projekt.getRefinanzierbar() == null || projekt.getRefinanzierbar());

        return projektRepository.insert(projekt);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Projekt updateProjekt(final ProjektUpdateDTO projektUpdateDTO, final String ha) {
        final Projekt foundProjekt = ServiceUtils.getEntityOrThrowNotFoundException(
                ha, projektRepository, Projekt.class);

        projektMapper.updateEntity(projektUpdateDTO, foundProjekt);

        if (projektUpdateDTO.fobFb() != null) {
            foundProjekt.setFobFb(foerderbereichService.getFoerderbereich(
                    BigDecimal.valueOf(projektUpdateDTO.fobFb())));
        }

        // Optionale Beziehungen
        foundProjekt.setStadtbezirk(projektUpdateDTO.stadtbezirk() == null ? null
                : stadtbezirkService.getStadtbezirk(
                        BigDecimal.valueOf(projektUpdateDTO.stadtbezirk())));

        foundProjekt.setKrankenhaus(projektUpdateDTO.krhname() == null
                || projektUpdateDTO.krhname().isBlank() ? null
                        : krankenhausService.getKrankenhaus(projektUpdateDTO.krhname()));

        foundProjekt.setSiedlungsgebiet(projektUpdateDTO.siedlungsgebiet() == null ? null
                : siedlungsgebietService.getSiedlungsgebiet(
                        BigDecimal.valueOf(projektUpdateDTO.siedlungsgebiet())));

        foundProjekt.setBauprogramm(projektUpdateDTO.bauprogramm() == null ? null
                : bauprogrammService.getBauprogramm(
                        BigDecimal.valueOf(projektUpdateDTO.bauprogramm())));

        if (projektUpdateDTO.kurKurzbez() != null) {
            foundProjekt.setKurKurzbez(kurzbezeichnungService.getKurzbezeichnung(
                    projektUpdateDTO.kurKurzbez()));
        }

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

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public List<ReportBewilligungAuszahlungProjektFormContextDTO> getReportBewilligungAuszahlungProjektFormContextDTOs() {
        return projektMapper.toReportBewilligungAuszahlungProjektFormContext(projektRepository.findAllAsBasic());
    }

}
