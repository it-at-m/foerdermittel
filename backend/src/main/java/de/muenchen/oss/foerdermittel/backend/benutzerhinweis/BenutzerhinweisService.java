package de.muenchen.oss.foerdermittel.backend.benutzerhinweis;

import static de.muenchen.oss.foerdermittel.backend.common.ExceptionMessageConstants.MSG_NOT_FOUND;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BenutzerhinweisService {

    private final BenutzerhinweisRepository benutzerhinweisRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Benutzerhinweis getBenutzerhinweis(final String viewId) {
        log.info("Get Benutzerhinweis with ID {}", viewId);
        return getBenutzerhinweisOrThrowException(viewId);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Benutzerhinweis createBenutzerhinweis(final Benutzerhinweis benutzerhinweis) {
        log.debug("Create Benutzerhinweis {}", benutzerhinweis);
        return benutzerhinweisRepository.insert(benutzerhinweis);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Benutzerhinweis updateBenutzerhinweis(final Benutzerhinweis benutzerhinweis, final String viewId) {
        final Benutzerhinweis foundBenutzerhinweis = getBenutzerhinweisOrThrowException(viewId);
        foundBenutzerhinweis.setFunktionsbeschreibung(benutzerhinweis.getFunktionsbeschreibung());
        foundBenutzerhinweis.setBedienung(benutzerhinweis.getBedienung());
        foundBenutzerhinweis.setPruefungVorgaben(benutzerhinweis.getPruefungVorgaben());
        log.debug("Update Benutzerhinweis {}", foundBenutzerhinweis);
        return benutzerhinweisRepository.update(foundBenutzerhinweis);
    }

    private Benutzerhinweis getBenutzerhinweisOrThrowException(final String viewId) {
        return benutzerhinweisRepository
                .findById(viewId)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_NOT_FOUND, viewId)));
    }
}
