package de.muenchen.oss.foerdermittel.backend.common;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface InsertAndUpdateRepository<T, I> extends Repository<T, I> {

    T insert(T entity);

    T insert(T entity, boolean flush);

    T update(T entity);

    T update(T entity, boolean flush);
}
