package de.muenchen.oss.foerdermittel.backend.common;

public interface InsertAndUpdateRepository<T> {
    T insert(T entity);

    T insert(T entity, boolean flush);

    T update(T entity);

    T update(T entity, boolean flush);
}
