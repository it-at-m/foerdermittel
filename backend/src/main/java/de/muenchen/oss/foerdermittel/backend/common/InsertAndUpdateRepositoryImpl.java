package de.muenchen.oss.foerdermittel.backend.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

@RequiredArgsConstructor
public class InsertAndUpdateRepositoryImpl<T, I> implements InsertAndUpdateRepository<T> {

    private final EntityManager entityManager;
    private final JpaEntityInformation<T, I> entityInformation;

    @Override
    public T insert(final T entity, final boolean flush) {
        entityManager.persist(entity);
        if (flush) {
            entityManager.flush();
        }
        return entity;
    }

    @Override
    public T insert(final T entity) {
        return insert(entity, false);
    }

    @Override
    public T update(final T entity, final boolean flush) {
        final I id = entityInformation.getId(entity);

        if (entityManager.find(entityInformation.getJavaType(), id) == null) {
            throw new EntityNotFoundException(
                    String.format("%s does not exist: %s", entityInformation.getJavaType().getCanonicalName(), id));
        }

        final T updated = entityManager.merge(entity);

        if (flush) {
            entityManager.flush();
        }

        return updated;
    }

    @Override
    public T update(final T entity) {
        return update(entity, false);
    }
}
