package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.common.DeleteNotAllowedException;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
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
public class StadtbezirkslisteService {

    private final ListennameRepository listennameRepository;
    private final StadtbezirkslisteRepository stadtbezirkslisteRepository;

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
        listenname.getStadtbezirkslisten().forEach(assignment -> {
            assignment.setListenName(listenname);
            assignment.setId(new StadtbezirkslistePrimaryKey(
                    listenname.getKurzbez(),
                    assignment.getStadtbezirk().getStadtbezirk()));
        });

        log.debug("Create Listenname {}", listenname);
        return listennameRepository.insert(listenname);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Listenname updateListenname(final Listenname listenname, final String kurzBez) {
        final Listenname foundListenname = ServiceUtils.getEntityOrThrowNotFoundException(
                kurzBez, listennameRepository);

        foundListenname.setBezeichnung(listenname.getBezeichnung());
        foundListenname.updateStadtbezirke(listenname.getStadtbezirkslisten(), kurzBez);

        return listennameRepository.update(foundListenname);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteListenname(final String kurzBez) {

        log.debug("Delete Listenname with ID {}", kurzBez);

        if (stadtbezirkslisteRepository.existsByListenName_Kurzbez(kurzBez)) {
            throw new DeleteNotAllowedException(
                    Listenname.class, Stadtbezirk.class);
        }

        Listenname listenname = ServiceUtils.getEntityOrThrowNotFoundException(
                kurzBez,
                listennameRepository);

        listennameRepository.delete(listenname);
    }

}
