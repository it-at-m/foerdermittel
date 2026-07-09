package de.muenchen.oss.foerdermittel.backend.benutzerhinweis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BenutzerhinweisServiceTest {
    private static final String TEST_STRING = "testString";

    @Mock
    private BenutzerhinweisRepository benutzerhinweisRepository;

    @InjectMocks
    private BenutzerhinweisService unitUnderTest;

    @Nested
    class GetBenutzerhinweis {
        @Test
        void givenIdExists_thenReturnEntity() {
            // Given
            final String id = "test";
            final Benutzerhinweis entity = new Benutzerhinweis(id, TEST_STRING, TEST_STRING, TEST_STRING);
            when(benutzerhinweisRepository.findById(id)).thenReturn(Optional.of(entity));

            // When
            final Benutzerhinweis result = unitUnderTest.getBenutzerhinweis(id);

            // Then
            verify(benutzerhinweisRepository, times(1)).findById(id);
            assertThat(result).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "test";
            when(benutzerhinweisRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.getBenutzerhinweis(id));

            // Then
            verify(benutzerhinweisRepository, times(1)).findById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class CreateBenutzerhinweis {
        @Test
        void givenBenutzerhinweis_thenCallInsertEntity() {
            // Given
            final Benutzerhinweis entityToInsert = new Benutzerhinweis("test", TEST_STRING, TEST_STRING, TEST_STRING);
            final Benutzerhinweis expectedEntity = new Benutzerhinweis("test", TEST_STRING, TEST_STRING, TEST_STRING);
            when(benutzerhinweisRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Benutzerhinweis result = unitUnderTest.createBenutzerhinweis(entityToInsert);

            // Then
            verify(benutzerhinweisRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdateBenutzerhinweis {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final String id = "test";
            final Benutzerhinweis entityToUpdate = new Benutzerhinweis(id, "updated", "updated", "updated");
            final Benutzerhinweis foundEntity = new Benutzerhinweis(id, TEST_STRING, TEST_STRING, TEST_STRING);
            final Benutzerhinweis expectedEntity = new Benutzerhinweis(id, "updated", "updated", "updated");
            when(benutzerhinweisRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(benutzerhinweisRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Benutzerhinweis result = unitUnderTest.updateBenutzerhinweis(entityToUpdate, id);

            // Then
            verify(benutzerhinweisRepository, times(1)).findById(id);
            verify(benutzerhinweisRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "test";
            final Benutzerhinweis entityToUpdate = new Benutzerhinweis(id, TEST_STRING, TEST_STRING, TEST_STRING);
            when(benutzerhinweisRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateBenutzerhinweis(entityToUpdate, id));

            // Then
            verify(benutzerhinweisRepository, times(1)).findById(id);
            verify(benutzerhinweisRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

}
