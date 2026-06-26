package de.muenchen.oss.foerdermittel.backend.common;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface InsertAndUpdateRepository<T> {

    T insert(T entity);

    T insert(T entity, boolean flush);

    T update(T entity);

    T update(T entity, boolean flush);
}
