package de.muenchen.oss.foerdermittel.backend.projekt.tabs.allgemein;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.projekt.Krhzweck;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektFormContext;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektCreateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjektAllgemeinService {

    private final ProjektRepository projektRepository;
    private final ProjektMapper projektMapper;
    private final FoerderbereichService foerderbereichService;
    private final StadtbezirkService stadtbezirkService;
    private final UnterabschnittService unterabschnittService;
    private final KurzbezeichnungService kurzbezeichnungService;
    private final BauprogrammService bauprogrammService;
    private final SiedlungsgebietService siedlungsgebietService;
    private final KrankenhausService krankenhausService;

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

    public Projekt createProjekt(final ProjektCreateDTO projektCreateDTO) {
        final Projekt projekt = projektMapper.toEntity(projektCreateDTO);

        setRequiredFields(
                projekt,
                projektCreateDTO.fobFb(),
                projektCreateDTO.uasUa(),
                projektCreateDTO.kurKurzbez());

        setOptionalFields(
                projekt,
                projektCreateDTO.bezStadtbezirk(),
                projektCreateDTO.krnKrhname(),
                projektCreateDTO.sgtSiedlungsgebiet(),
                projektCreateDTO.bpgBauprogramm());

        projekt.setKauf(Boolean.TRUE.equals(projekt.getKauf()));
        projekt.setRefinanzierbar(!Boolean.FALSE.equals(projekt.getRefinanzierbar()));

        return projekt;
    }

    public Projekt updateProjekt(
            final Projekt projekt,
            final ProjektUpdateDTO projektUpdateDTO) {
        final Projekt mappedProjekt = projektMapper.toEntity(projektUpdateDTO);

        projekt.setPname(mappedProjekt.getPname());
        projekt.setPstrasse(mappedProjekt.getPstrasse());
        projekt.setProjart(mappedProjekt.getProjart());
        projekt.setNotizen(mappedProjekt.getNotizen());

        if (projektUpdateDTO.fobFb() != null) {
            projekt.setFobFb(
                    foerderbereichService.getFoerderbereich(
                            BigDecimal.valueOf(projektUpdateDTO.fobFb())));
        }

        if (projektUpdateDTO.kurKurzbez() != null) {
            projekt.setKurKurzbez(
                    kurzbezeichnungService.getKurzbezeichnung(projektUpdateDTO.kurKurzbez()));
        }

        setOptionalFields(
                projekt,
                projektUpdateDTO.bezStadtbezirk(),
                projektUpdateDTO.krnKrhname(),
                projektUpdateDTO.sgtSiedlungsgebiet(),
                projektUpdateDTO.bpgBauprogramm());

        if (projektUpdateDTO.kauf() != null) {
            projekt.setKauf(projektUpdateDTO.kauf());
        }

        if (projektUpdateDTO.refinanzierbar() != null) {
            projekt.setRefinanzierbar(projektUpdateDTO.refinanzierbar());
        }

        return projekt;
    }

    private void setRequiredFields(
            final Projekt projekt,
            final Integer fobFb,
            final String uasUa,
            final String kurKurzbez) {
        projekt.setFobFb(
                foerderbereichService.getFoerderbereich(BigDecimal.valueOf(fobFb)));
        projekt.setUasUa(unterabschnittService.getUnterabschnitt(uasUa));
        projekt.setKurKurzbez(kurzbezeichnungService.getKurzbezeichnung(kurKurzbez));
    }

    private void setOptionalFields(
            final Projekt projekt,
            final Integer bezStadtbezirk,
            final String krnKrhname,
            final Integer sgtSiedlungsgebiet,
            final Integer bpgBauprogramm) {
        projekt.setBezStadtbezirk(
                bezStadtbezirk == null
                        ? null
                        : stadtbezirkService.getStadtbezirk(BigDecimal.valueOf(bezStadtbezirk)));

        projekt.setKrnKrhname(
                krnKrhname == null || krnKrhname.isBlank()
                        ? null
                        : krankenhausService.getKrankenhaus(krnKrhname));

        projekt.setSgtSiedlungsgebiet(
                sgtSiedlungsgebiet == null
                        ? null
                        : siedlungsgebietService.getSiedlungsgebiet(
                                BigDecimal.valueOf(sgtSiedlungsgebiet)));

        projekt.setBpgBauprogramm(
                bpgBauprogramm == null
                        ? null
                        : bauprogrammService.getBauprogramm(BigDecimal.valueOf(bpgBauprogramm)));
    }
}
