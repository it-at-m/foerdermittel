package de.muenchen.oss.foerdermittel.backend.listenname;

import de.muenchen.oss.foerdermittel.backend.security.Authorities;
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
    public Listenname getListenname(final String kurzbez) {
        log.info("Get Listenname with ID {}", kurzbez);

        return ServiceUtils.getEntityOrThrowNotFoundException(
                kurzbez,
                listennameRepository
        );
    }


    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Listenname> getAllListennamen(final Pageable pageable) {
        log.info("Get all Listennamen");

        return listennameRepository.findAll(pageable);
    }


    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Listenname createListenname(final Listenname listenname) {

        log.debug("Create Listenname {}", listenname);

        return listennameRepository.insert(listenname);
    }


    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Listenname updateListenname(
            final Listenname listenname,
            final String kurzbez) {

        Listenname vorhanden =
                ServiceUtils.getEntityOrThrowNotFoundException(
                        kurzbez,
                        listennameRepository
                );

        vorhanden.setBezeichnung(
                listenname.getBezeichnung()
        );

        log.debug("Update Listenname {}", vorhanden);

        return listennameRepository.update(vorhanden);
    }


    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteListenname(final String kurzbez) {

        log.debug("Delete Listenname {}", kurzbez);

        Listenname vorhanden =
                ServiceUtils.getEntityOrThrowNotFoundException(
                        kurzbez,
                        listennameRepository
                );

        listennameRepository.delete(vorhanden);
    }

}