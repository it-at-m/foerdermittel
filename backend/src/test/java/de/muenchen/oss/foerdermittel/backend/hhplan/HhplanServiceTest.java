
package de.muenchen.oss.foerdermittel.backend.hhplan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.hhplan.dao.BasicHhplanDAO;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanMapper;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanResponseDTO;
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
    class GetHhplan {

        @Test
        void givenHhjahrAndPageable_thenReturnPageOfDTOs() {
            // Given
            final Pageable pageable = PageRequest.of(0, 10);

            final Hhplan entity1 = mock(Hhplan.class);
            final Hhplan entity2 = mock(Hhplan.class);

            final HhplanResponseDTO dto1 = mock(HhplanResponseDTO.class);
            final HhplanResponseDTO dto2 = mock(HhplanResponseDTO.class);

            final List<Hhplan> entities = List.of(entity1, entity2);
            final List<HhplanResponseDTO> expectedDTOs = List.of(dto1, dto2);

            final Page<Hhplan> entityPage = new PageImpl<>(entities, pageable, entities.size());

            when(hhplanRepository.findAllByHhjJahr(HHJAHR_ID, pageable))
                    .thenReturn(entityPage);
            when(hhplanMapper.toDTO(entity1)).thenReturn(dto1);
            when(hhplanMapper.toDTO(entity2)).thenReturn(dto2);

            // When
            final Page<HhplanResponseDTO> result = unitUnderTest.getHhplan(HHJAHR_ID, pageable);

            // Then
            verify(hhplanRepository, times(1))
                    .findAllByHhjJahr(HHJAHR_ID, pageable);
            verify(hhplanMapper, times(1)).toDTO(entity1);
            verify(hhplanMapper, times(1)).toDTO(entity2);

            assertThat(result.getContent()).containsExactlyElementsOf(expectedDTOs);
            assertThat(result.getNumber()).isZero();
            assertThat(result.getSize()).isEqualTo(10);
            assertThat(result.getTotalElements()).isEqualTo(2);
        }
    }

    @Nested
    class GetHhplanFormContextDTOs {

        @Test
        void givenEntitiesExists_thenReturnCorrectList() {
            // Given
            final List<BasicHhplanDAO> entities = List.of(
                    new BasicHhplanDAO(
                            BigDecimal.valueOf(2025), "3003000", "123456"),
                    new BasicHhplanDAO(
                            BigDecimal.valueOf(2026), "3003001", "654321"));

            final List<HhplanFormContextDTO> expectedDTOs = List.of(
                    new HhplanFormContextDTO(
                            BigDecimal.valueOf(2025), "3003000", "123456"),
                    new HhplanFormContextDTO(
                            BigDecimal.valueOf(2026), "3003001", "654321"));

            when(hhplanRepository.findReportHhplan()).thenReturn(entities);
            when(hhplanMapper.toFormContextFromBasic(entities)).thenReturn(expectedDTOs);

            // When
            final List<HhplanFormContextDTO> result = unitUnderTest.getHhplanFormContextDTOs();

            // Then
            verify(hhplanRepository, times(1)).findReportHhplan();
            verify(hhplanMapper, times(1)).toFormContextFromBasic(entities);

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
