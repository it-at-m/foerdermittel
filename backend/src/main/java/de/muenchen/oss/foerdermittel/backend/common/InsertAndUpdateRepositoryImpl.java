package de.muenchen.oss.foerdermittel.backend.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceUnitUtil;
import jakarta.persistence.metamodel.EntityType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InsertAndUpdateRepositoryImpl<T> implements InsertAndUpdateRepository<T> {

    private final EntityManager entityManager;

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
        PersistenceUnitUtil persistenceUnitUtil = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();

        Object id = persistenceUnitUtil.getIdentifier(entity);

        if (id == null) {
            throw new IllegalStateException("Entity ID must not be null");
        }

        Class<?> entityClass = entity.getClass();
        EntityType<?> entityType = entityManager.getMetamodel().entity(entityClass);
        Class<?> idType = entityType.getIdType().getJavaType();

        if (!idType.isInstance(id)) {
            throw new IllegalStateException("Unexpected ID type: " + id.getClass());
        }

        if (entityManager.find(entityClass, id) == null) {
            throw new EntityNotFoundException(
                    String.format("%s does not exist: %s", entity.getClass().getCanonicalName(), id));
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
