package de.muenchen.oss.foerdermittel.backend.common;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Locale;
import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private SpecificationUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static <T> void addContains(
            final List<Specification<T>> specifications,
            final String value,
            final String... path) {
        if (value == null || value.isBlank()) {
            return;
        }
        specifications.add(
                (root, query, cb) -> cb.like(
                        cb.lower(path(root, path).as(String.class)),
                        "%" + value.toLowerCase(Locale.ROOT) + "%"));
    }

    public static <T> void addEquals(
            final List<Specification<T>> specifications,
            final Object value,
            final String... path) {
        if (value == null) {
            return;
        }
        specifications.add(
                (root, query, cb) -> cb.equal(path(root, path), value));
    }

    // walks root.get("a").get("b")... for nested attributes like fobFb.fb
    private static Path<?> path(final Root<?> root, final String... path) {
        Path<?> current = root;
        for (final String segment : path) {
            current = current.get(segment);
        }
        return current;
    }

}
