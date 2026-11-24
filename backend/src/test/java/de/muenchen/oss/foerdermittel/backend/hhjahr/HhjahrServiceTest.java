
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
            when(hhjahrRepository.findAll(pageable))
                    .thenReturn(expectedPage);
            when(hhplanRepository.findAllByHhjJahrIn(List.of(HHJAHR_ID)))
                    .thenReturn(List.of());

            // When
            final Page<Hhjahr> result = unitUnderTest.getHhjahre(pageable);

            // Then
            verify(hhjahrRepository, times(1)).findAll(pageable);
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

            when(hhjahrRepository.findAll(pageable))
                    .thenReturn(emptyPage);

            // When
            final Page<Hhjahr> result = unitUnderTest.getHhjahre(pageable);

            // Then
            verify(hhjahrRepository, times(1)).findAll(pageable);
            verify(hhplanRepository, never()).findAllByHhjJahrIn(List.of());

            assertThat(result).isEmpty();
        }
    }
}
