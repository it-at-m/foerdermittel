package de.muenchen.oss.foerdermittel.backend.projekt;

import static de.muenchen.oss.foerdermittel.backend.common.SpecificationUtils.addContains;
import static de.muenchen.oss.foerdermittel.backend.common.SpecificationUtils.addEquals;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class ProjektSpecifications {

    private ProjektSpecifications() {
    }

    public static Specification<Projekt> filter(final ProjektFilter filter) {
        final List<Specification<Projekt>> specifications = new ArrayList<>();
        addContains(specifications, filter.projnr(), "projnr");
        addEquals(specifications, filter.fobFb(), "fobFb", "fb");
        addEquals(specifications, filter.kurz(), "kurKurzbez","kurzbez");
        addEquals(specifications, filter.ua(), "uasUa","ua");
        addContains(specifications, filter.pname(), "pname");
        addContains(specifications, filter.pstrasse(), "pstrasse");
        return Specification.allOf(specifications);
    }

}
