
package de.muenchen.oss.foerdermittel.backend.hhjahr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.hhplan.HhplanRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class HhjahrServiceTest {

    private static final BigDecimal HHJAHR_ID = BigDecimal.valueOf(2026);

    @Mock
    private HhjahrRepository hhjahrRepository;

    @Mock
    private HhplanRepository hhplanRepository;

    @Mock
    private Hhjahr hhjahr;

    @InjectMocks
    private HhjahrService unitUnderTest;

    @Nested
    class GetHhjahr {

        @Test
        void givenIdExists_thenReturnEntity() {
            // Given
            when(hhjahrRepository.findById(HHJAHR_ID))
                    .thenReturn(Optional.of(hhjahr));

            // When
            final Hhjahr result = unitUnderTest.getHhjahr(HHJAHR_ID);

            // Then
            verify(hhjahrRepository, times(1)).findById(HHJAHR_ID);

            assertThat(result).isEqualTo(hhjahr);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            when(hhjahrRepository.findById(HHJAHR_ID))
                    .thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.getHhjahr(HHJAHR_ID));

            // Then
            verify(hhjahrRepository, times(1)).findById(HHJAHR_ID);

            assertThat(exception.getMessage()).isEqualTo(
                    String.format(
                            "The %s with ID %s was not found.",
                            Hhjahr.class.getSimpleName(),
                            HHJAHR_ID));
        }
    }

    @Nested
    class GetHhjahre {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final Pageable pageable = PageRequest.of(0, 10);
            final Page<Hhjahr> expectedPage = new PageImpl<>(List.of(hhjahr), pageable, 1);

            when(hhjahr.getHhjahr()).thenReturn(HHJAHR_ID);
            when(hhjahrRepository.findAllHhjahre(pageable))
                    .thenReturn(expectedPage);
            when(hhplanRepository.findAllByHhjJahrIn(List.of(HHJAHR_ID)))
                    .thenReturn(List.of());

            // When
            final Page<Hhjahr> result = unitUnderTest.getHhjahre(pageable);

            // Then
            verify(hhjahrRepository, times(1)).findAllHhjahre(pageable);
            verify(hhplanRepository, times(1))
                    .findAllByHhjJahrIn(List.of(HHJAHR_ID));
            verify(hhjahr, times(1)).setHhplanlisten(List.of());

            assertThat(result).isEqualTo(expectedPage);
            assertThat(result.getContent()).containsExactly(hhjahr);
        }

        @Test
        void givenNoHhjahre_thenReturnEmptyPage() {
            // Given
            final Pageable pageable = PageRequest.of(0, 10);
            final Page<Hhjahr> emptyPage = Page.empty(pageable);

            when(hhjahrRepository.findAllHhjahre(pageable))
                    .thenReturn(emptyPage);

            // When
            final Page<Hhjahr> result = unitUnderTest.getHhjahre(pageable);

            // Then
            verify(hhjahrRepository, times(1)).findAllHhjahre(pageable);
            verify(hhplanRepository, never()).findAllByHhjJahrIn(List.of());

            assertThat(result).isEmpty();
        }
    }

    @Nested
    class GetHhjahrFormContext {

        @Test
        void givenEntitiesExists_thenReturnFormContext() {
            // Given
            when(hhjahrRepository.findAllHhjahr())
                    .thenReturn(List.of(HHJAHR_ID));

            // When
            final HhjahrFormContext result = unitUnderTest.getHhjahrFormContext();

            // Then
            verify(hhjahrRepository, times(1)).findAllHhjahr();

            assertThat(result).isNotNull();
        }
    }

    @Nested
    class CreateHhjahr {

        @Test
        void givenHhjahr_thenReturnCreatedEntity() {
            // Given
            when(hhjahrRepository.insert(hhjahr))
                    .thenReturn(hhjahr);

            // When
            final Hhjahr result = unitUnderTest.createHhjahr(hhjahr);

            // Then
            verify(hhjahrRepository, times(1)).insert(hhjahr);

            assertThat(result).isEqualTo(hhjahr);
        }
    }

    @Nested
    class UpdateHhjahr {

        @Test
        void givenEntityExists_thenReturnUpdatedEntity() {
            // Given
            final String hhbezeichnung = "Haushaltsjahr 2026";
            final String notizen = "Testnotiz";

            when(hhjahrRepository.findById(HHJAHR_ID))
                    .thenReturn(Optional.of(hhjahr));
            when(hhjahr.getHhbezeichnung()).thenReturn(hhbezeichnung);
            when(hhjahr.getNotizen()).thenReturn(notizen);
            when(hhjahrRepository.update(hhjahr))
                    .thenReturn(hhjahr);

            // When
            final Hhjahr result = unitUnderTest.updateHhjahr(hhjahr, HHJAHR_ID);

            // Then
            verify(hhjahrRepository, times(1)).findById(HHJAHR_ID);
            verify(hhjahr, times(1)).setHhbezeichnung(hhbezeichnung);
            verify(hhjahr, times(1)).setNotizen(notizen);
            verify(hhjahrRepository, times(1)).update(hhjahr);

            assertThat(result).isEqualTo(hhjahr);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            when(hhjahrRepository.findById(HHJAHR_ID))
                    .thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.updateHhjahr(hhjahr, HHJAHR_ID));

            // Then
            verify(hhjahrRepository, times(1)).findById(HHJAHR_ID);
            verify(hhjahrRepository, never()).update(hhjahr);

            assertThat(exception.getMessage()).isEqualTo(
                    String.format(
                            "The %s with ID %s was not found.",
                            Hhjahr.class.getSimpleName(),
                            HHJAHR_ID));
        }
    }

    @Nested
    class DeleteHhjahr {

        @Test
        void givenIdExists_thenDeleteEntity() {
            // Given
            when(hhjahrRepository.findById(HHJAHR_ID))
                    .thenReturn(Optional.of(hhjahr));

            // When
            unitUnderTest.deleteHhjahr(HHJAHR_ID);

            // Then
            verify(hhjahrRepository, times(1)).findById(HHJAHR_ID);
            verify(hhjahrRepository, times(1)).deleteById(HHJAHR_ID);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            when(hhjahrRepository.findById(HHJAHR_ID))
                    .thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.deleteHhjahr(HHJAHR_ID));

            // Then
            verify(hhjahrRepository, times(1)).findById(HHJAHR_ID);
            verify(hhjahrRepository, never()).deleteById(HHJAHR_ID);

            assertThat(exception.getMessage()).isEqualTo(
                    String.format(
                            "The %s with ID %s was not found.",
                            Hhjahr.class.getSimpleName(),
                            HHJAHR_ID));
        }
    }
}
