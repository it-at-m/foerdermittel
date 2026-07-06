package de.muenchen.oss.foerdermittel.backend.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

@ExtendWith(MockitoExtension.class)
public class ServiceUtilsTest {

    @Mock
    CrudRepository<String, Long> repository;

    @Test
    void givenEntityFound_thenReturnEntity() {
        // Given
        final Long id = 1L;
        final String entity = "TestEntity";
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        // When
        final String result = ServiceUtils.getEntityOrThrowNotFoundException(id, repository);

        // Then
        verify(repository).findById(id);
        assertThat(result).isEqualTo(entity);
    }

    @Test
    void givenEntityNotFound_thenThrowNotFoundException() {
        // Given
        final Long id = 2L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        final NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            ServiceUtils.getEntityOrThrowNotFoundException(id, repository);
        });

        // Then
        verify(repository).findById(id);
        assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
    }
}
