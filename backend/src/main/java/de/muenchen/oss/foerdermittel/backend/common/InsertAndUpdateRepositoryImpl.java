package de.muenchen.oss.foerdermittel.backend.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

@RequiredArgsConstructor
public class InsertAndUpdateRepositoryImpl<T, ID> implements InsertAndUpdateRepository<T, ID> {

    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ID> entityInformation;

    @Override
    public T insert(T entity, boolean flush) {
        entityManager.persist(entity);
        if (flush) {
            entityManager.flush();
        }
        return entity;
    }

    @Override
    public T insert(T entity) {
        return insert(entity, false);
    }

    @Override
    public T update(T entity, boolean flush) {
        ID id = entityInformation.getId(entity);

        if (entityManager.find(entityInformation.getJavaType(), id) == null) {
            throw new EntityNotFoundException(
                    "Entity does not exist: " + id);
        }

        T updated = entityManager.merge(entity);

        if (flush) {
            entityManager.flush();
        }

        return updated;
    }

    @Override
    public T update(T entity) {
        return update(entity, false);
    }
}
