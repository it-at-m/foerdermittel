package de.muenchen.oss.foerdermittel.backend.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.bauleitung.Bauleitung;
import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.CrudRepository;

@ExtendWith(MockitoExtension.class)
public class ServiceUtilsTest {

    @Mock
    CrudRepository<String, String> repository;

    @Nested
    class GetEntityOrThrowNotFoundException {

        @Test
        void givenEntityFound_thenReturnEntity() {
            // Given
            final String id = "test";
            final String entity = "TestEntity";
            when(repository.findById(id)).thenReturn(Optional.of(entity));

            // When
            final String result = ServiceUtils.getEntityOrThrowNotFoundException(id, repository, Bauleitung.class);

            // Then
            verify(repository).findById(id);
            assertThat(result).isEqualTo(entity);
        }

        @Test
        void givenEntityNotFound_thenThrowNotFoundException() {
            // Given
            final String id = "test";
            when(repository.findById(id)).thenReturn(Optional.empty());

            // When
            final NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> ServiceUtils.getEntityOrThrowNotFoundException(id, repository, Bauleitung.class));

            // Then
            verify(repository).findById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("The %s with ID %s was not found.", Bauleitung.class.getSimpleName(), id));
        }

    }

    @Nested
    class CheckExistsOrThrowNotFoundException {

        @Test
        void givenEntityExists_thenDoNothing() {
            // Given
            final String id = "test";
            when(repository.existsById(id)).thenReturn(true);

            // When / Then
            assertDoesNotThrow(() -> ServiceUtils.checkExistsOrThrowNotFoundException(id, repository, Bauleitung.class));
            verify(repository).existsById(id);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "test";
            when(repository.existsById(id)).thenReturn(false);

            // When / Then
            final NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> ServiceUtils.checkExistsOrThrowNotFoundException(id, repository, Bauleitung.class));
            assertThat(exception.getMessage()).isEqualTo(String.format("The %s with ID %s was not found.", Bauleitung.class.getSimpleName(), id));
            verify(repository).existsById(id);
        }

    }
}
