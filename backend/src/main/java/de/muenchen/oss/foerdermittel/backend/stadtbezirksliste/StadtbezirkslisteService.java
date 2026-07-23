package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class StadtbezirkslisteService {


    private final StadtbezirkslisteRepository stadtbezirkslisteRepository;
    private final StadtbezirkRepository stadtbezirkRepository;
    private final ListennameRepository listennameRepository;



//    @PreAuthorize(Authorities.HAS_ANY_ROLE)
//    @Transactional(readOnly = true)
//    public List<Stadtbezirksliste> getStadtbezirke(final String kurzbez) {
//
//        log.info("Get Stadtbezirke for Listenname {}", kurzbez);
//
//        return stadtbezirkslisteRepository.findByListenName_Kurzbez(kurzbez);
//    }

//
//    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
//    public void setStadtbezirke(
//            final String kurzbez,
//            final List<BigDecimal> stadtbezirke) {
//
//        log.debug("Add Stadtbezirke {} for Listenname {}", stadtbezirke, kurzbez);
//
//
//        Listenname listenname = new Listenname();
//        listenname.setKurzbez(kurzbez);
//
//
//        for (BigDecimal bezirk : stadtbezirke) {
//
//            boolean existiert = stadtbezirkslisteRepository
//                    .findByListenName_Kurzbez(kurzbez)
//                    .stream()
//                    .anyMatch(e -> e.getId().getStadtbezirk().equals(bezirk));
//
//
//            if (!existiert) {
//
//                Stadtbezirksliste eintrag = new Stadtbezirksliste();
//
//                eintrag.setId(
//                        new StadtbezirkslistePrimaryKey(
//                                kurzbez,
//                                bezirk
//                        )
//                );
//
//                eintrag.setListenName(listenname);
//
//
//                Stadtbezirksliste stadtbezirk = new Stadtbezirksliste();
//                stadtbezirk.setStadtbezirk();
//
//                eintrag.setStadtbezirk(stadtbezirk);
//
//
//                stadtbezirkslisteRepository.insert(eintrag);
//            }
//        }
//    }


    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void setStadtbezirke(String kurzbez, List<BigDecimal> stadtbezirkIds) {

        Listenname listenname = listennameRepository.findById(kurzbez)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Listenname '" + kurzbez + "' wurde nicht gefunden."));

        // Alte Zuordnungen löschen
        stadtbezirkslisteRepository.deleteAll(
                stadtbezirkslisteRepository.findByListenName_Kurzbez(kurzbez));

        // Neue Zuordnungen anlegen
        for (BigDecimal stadtbezirkId : stadtbezirkIds) {

            Stadtbezirk stadtbezirk = stadtbezirkRepository.findById(stadtbezirkId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Stadtbezirk '" + stadtbezirkId + "' wurde nicht gefunden."));

            Stadtbezirksliste zuordnung = new Stadtbezirksliste();
            zuordnung.setId(new StadtbezirkslistePrimaryKey(kurzbez, stadtbezirkId));
            zuordnung.setListenName(listenname);
            zuordnung.setStadtbezirk(stadtbezirk);
            zuordnung.setBezeichnung(stadtbezirk.getBezeichnung());

            stadtbezirkslisteRepository.save(zuordnung);
        }
    }




    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteStadtbezirk(
            String kurzbez,
            BigDecimal stadtbezirk) {

        stadtbezirkslisteRepository
                .deleteByListenName_KurzbezAndStadtbezirk_Stadtbezirk(
                        kurzbez,
                        stadtbezirk);
    }

}

