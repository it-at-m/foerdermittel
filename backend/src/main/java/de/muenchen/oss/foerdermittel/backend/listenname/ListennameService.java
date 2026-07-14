package de.muenchen.oss.foerdermittel.backend.listenname;

import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.listenname.Listenname;
import de.muenchen.oss.foerdermittel.backend.listenname.ListennameFormContext;
import de.muenchen.oss.foerdermittel.backend.listenname.ListennameRepository;
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
public class ListennameService {

    private final ListennameRepository listennameRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Listenname getListenname(final String listennameId) {
        log.info("Get Listenname with ID {}", listennameId);
        return ServiceUtils.getEntityOrThrowNotFoundException(listennameId, listennameRepository);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Listenname> getAllListennamen(final Pageable pageable) {
        log.info("Get all Listennamen with Pageable {}", pageable);
        return listennameRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public ListennameFormContext getListennameFormContext() {
        log.info("Get Listenname form context");
        return new ListennameFormContext(listennameRepository.findAllListennamen());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Listenname createListenname(final Listenname listenname) {
        log.debug("Create Listenname {}", listenname);
        return listennameRepository.insert(listenname);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Listenname updateListenname(final Listenname listenname, final String listennameId) {
        final Listenname foundListenname = ServiceUtils.getEntityOrThrowNotFoundException(listennameId, listennameRepository);
        foundListenname.setBezeichnung(listenname.getBezeichnung());
        log.debug("Update Listenname {}", foundListenname);
        return listennameRepository.update(foundListenname);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteListenname(final String listennameId) {
        log.debug("Delete Listenname with ID {}", listennameId);
        ServiceUtils.getEntityOrThrowNotFoundException(listennameId, listennameRepository);
        listennameRepository.deleteById(listennameId);
    }
}
