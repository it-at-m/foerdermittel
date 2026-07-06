package de.muenchen.oss.foerdermittel.backend.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.data.repository.CrudRepository;

public class ServiceUtilsTest {

    @Test
    void testGetEntityOrThrowNotFoundException_entityFound() {
        // Arrange
        Long id = 1L;
        String entity = "TestEntity";

        @SuppressWarnings("unchecked")
        CrudRepository<String, Long> repository = mock(CrudRepository.class);
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        String result = ServiceUtils.getEntityOrThrowNotFoundException(id, repository);

        // Assert
        assertThat(result, is(entity));
        verify(repository).findById(id);
    }

    @Test
    void testGetEntityOrThrowNotFoundException_entityNotFound() {
        // Arrange
        Long id = 2L;

        @SuppressWarnings("unchecked")
        CrudRepository<String, Long> repository = mock(CrudRepository.class);
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            ServiceUtils.getEntityOrThrowNotFoundException(id, repository);
        });
    }
}
