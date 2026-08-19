package de.muenchen.oss.foerdermittel.backend.projekt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjektService {

    private final ProjektRepository projektRepository;

    public Page<Projekt> getProjekte(
            ProjektFilter filter,
            Pageable pageable) {

        return projektRepository.findAll(
                ProjektSpecifications.filter(filter),
                pageable
        );
    }
}