package de.muenchen.oss.foerdermittel.backend.projekt;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class ProjektSpecifications {

    private ProjektSpecifications() {
    }

    public static Specification<Projekt> filter(ProjektFilter filter) {

        List<Specification<Projekt>> specifications = new ArrayList<>();

        addContains(specifications, "projnr", filter.projnr());
        addEquals(specifications, "fobFb", filter.fobFb());
        addContains(specifications, "kurKurzbez", filter.kurKurzbez());
        addContains(specifications, "uasUa", filter.uasUa());
        addContains(specifications, "pname", filter.pname());
        addContains(specifications, "pstrasse", filter.pstrasse());

        return Specification.allOf(specifications);
    }

    private static void addContains(
            List<Specification<Projekt>> specifications,
            String field,
            String value) {

        if (value == null || value.isBlank()) {
            return;
        }

        specifications.add(
                (root, query, cb) ->
                        cb.like(
                                cb.lower(root.get(field)),
                                "%" + value.toLowerCase() + "%"
                        )
        );
    }

    private static <T> void addEquals(
            List<Specification<Projekt>> specifications,
            String field,
            T value) {

        if (value == null) {
            return;
        }

        specifications.add(
                (root, query, cb) ->
                        cb.equal(root.get(field), value)
        );
    }
}