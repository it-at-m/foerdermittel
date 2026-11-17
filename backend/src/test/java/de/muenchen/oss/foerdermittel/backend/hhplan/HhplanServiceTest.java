
package de.muenchen.oss.foerdermittel.backend.hhplan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanMapper;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.api.Assertions;
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
class HhplanServiceTest {

    private static final BigDecimal HHJAHR_ID = BigDecimal.valueOf(2026);

    @Mock
    private HhplanRepository hhplanRepository;

    @Mock
    private HhplanMapper hhplanMapper;

    @InjectMocks
    private HhplanService unitUnderTest;

    @Nested
    class GetHhplanByHhjahr {

        @Test
        void givenHhjahr_thenReturnListOfEntities() {
            // Given
            final Hhplan entity1 = new Hhplan();
            final Hhplan entity2 = new Hhplan();
            final List<Hhplan> expectedEntities = List.of(entity1, entity2);

            when(hhplanRepository.findAllByHhjJahrIn(List.of(HHJAHR_ID)))
                    .thenReturn(expectedEntities);

            // When
            final List<Hhplan> result = unitUnderTest.getHhplanByHhjahr(HHJAHR_ID);

            // Then
            verify(hhplanRepository, times(1))
                    .findAllByHhjJahrIn(List.of(HHJAHR_ID));

            assertThat(result).containsExactlyElementsOf(expectedEntities);
        }
    }

    @Nested
    class GetHhplan {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Hhplan entity1 = new Hhplan();
            final Hhplan entity2 = new Hhplan();
            final List<Hhplan> entities = List.of(entity1, entity2);
            final Page<Hhplan> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(hhplanRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Hhplan> result = unitUnderTest.getHhplan(pageable);

            // Then
            verify(hhplanRepository, times(1)).findAll(pageable);

            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class GetHhplanFormContextDTOs {

        @Test
        void givenEntitiesExists_thenReturnCorrectList() {
            // Given
            final List<HhplanFormContextDTO> expectedDTOs = List.of(
                    new HhplanFormContextDTO(
                            BigDecimal.valueOf(2025), "3003000", "123456"),
                    new HhplanFormContextDTO(
                            BigDecimal.valueOf(2026), "3003001", "654321"));

            when(hhplanRepository.findReportHhplan()).thenReturn(List.of());
            when(hhplanMapper.toFormContextFromBasic(List.of()))
                    .thenReturn(expectedDTOs);

            // When
            final List<HhplanFormContextDTO> result = unitUnderTest.getHhplanFormContextDTOs();

            // Then
            verify(hhplanRepository, times(1)).findReportHhplan();
            verify(hhplanMapper, times(1)).toFormContextFromBasic(List.of());

            assertThat(result)
                    .hasSize(2)
                    .extracting(
                            HhplanFormContextDTO::hhjJahr,
                            HhplanFormContextDTO::proProjnr,
                            HhplanFormContextDTO::fipo)
                    .containsExactly(
                            Assertions.tuple(
                                    BigDecimal.valueOf(2025), "3003000", "123456"),
                            Assertions.tuple(
                                    BigDecimal.valueOf(2026), "3003001", "654321"));
        }
    }

    @Nested
    class GetHhplanFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<BigDecimal> entities = List.of(
                    BigDecimal.valueOf(2025),
                    BigDecimal.valueOf(2026));

            when(hhplanRepository.findAllHhplan()).thenReturn(entities);

            // When
            final HhplanFormContext result = unitUnderTest.getHhplanFormContext();

            // Then
            verify(hhplanRepository, times(1)).findAllHhplan();

            assertThat(result).isNotNull();
        }
    }
}
