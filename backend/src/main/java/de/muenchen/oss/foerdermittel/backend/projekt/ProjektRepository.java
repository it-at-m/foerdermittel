package de.muenchen.oss.foerdermittel.backend.projekt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjektRepository
        extends JpaRepository<Projekt, String>,
        JpaSpecificationExecutor<Projekt> {
}