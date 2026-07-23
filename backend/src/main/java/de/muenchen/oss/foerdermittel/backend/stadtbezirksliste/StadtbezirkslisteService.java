package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkRepository;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Listenname getListenname(final String kurzBez) {
        log.info("Get Listenname with ID {}", kurzBez);
        return ServiceUtils.getEntityOrThrowNotFoundException(kurzBez, listennameRepository);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Listenname> getAllListennamen(final Pageable pageable) {
        log.info("Get all Listennamen with Pageable {}", pageable);
        return listennameRepository.findAll(pageable);
    }


    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Listenname createListenname(final Listenname listenname) {
    log.debug("Create Listenname {}", listenname);
    return listennameRepository.insert(listenname);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Listenname updateListenname(final Listenname listenname, final String kurzBez) {
        final Listenname foundListenname = ServiceUtils.getEntityOrThrowNotFoundException(kurzBez, listennameRepository);
        foundListenname.setBezeichnung(listenname.getBezeichnung());
        log.debug("Update Listenname {}", foundListenname);
        return listennameRepository.update(foundListenname);
    }


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

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteListenname(final String kurzBez) {
        log.debug("Delete Listenname with ID {}", kurzBez);
        ServiceUtils.getEntityOrThrowNotFoundException(kurzBez, listennameRepository);
        listennameRepository.deleteById(kurzBez);
    }

}

