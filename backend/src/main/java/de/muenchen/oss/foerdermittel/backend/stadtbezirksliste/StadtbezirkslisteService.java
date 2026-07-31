package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.security.Authorities;
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
    private final ListennameRepository listennameRepository;


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
    @Transactional(readOnly = true)
    public StadtbezirkslisteFormContext getStadtbezirksListeFormContext() {
        log.info("Get Stadtbezirk form context");
        return new StadtbezirkslisteFormContext(listennameRepository.findAllKurzBezn());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Listenname createListenname(final Listenname listenname) {
    log.debug("Create Listenname {}", listenname);
    return listennameRepository.insert(listenname);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Listenname updateListenname(Listenname listenname, String kurzBez) {
        Listenname foundListenname = ServiceUtils.getEntityOrThrowNotFoundException(
                kurzBez, listennameRepository);

        foundListenname.setBezeichnung(listenname.getBezeichnung());

        foundListenname.getStadtbezirkslisten().clear();

        listenname.getStadtbezirkslisten().forEach(assignment -> {
            assignment.setListenName(foundListenname);
            assignment.setId(new StadtbezirkslistePrimaryKey(
                    kurzBez,
                    assignment.getStadtbezirk().getStadtbezirk()
            ));
        });

        foundListenname.getStadtbezirkslisten()
                .addAll(listenname.getStadtbezirkslisten());

        log.debug("Update Listenname {}", foundListenname);

        return listennameRepository.update(foundListenname);
    }


    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteListenname(final String kurzBez) {
        log.debug("Delete Listenname with ID {}", kurzBez);
        ServiceUtils.getEntityOrThrowNotFoundException(kurzBez, listennameRepository);
        listennameRepository.deleteById(kurzBez);
    }

}

