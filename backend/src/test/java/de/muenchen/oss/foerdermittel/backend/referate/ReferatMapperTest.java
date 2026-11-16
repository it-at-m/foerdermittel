package de.muenchen.oss.foerdermittel.backend.referate;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.referat.Referat;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatCreateDTO;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatMapper;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatResponseDTO;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatUpdateDTO;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class ReferatMapperTest {

    private final ReferatMapper referatMapper = Mappers.getMapper(ReferatMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Referat entity = new Referat(BigDecimal.valueOf(1), "test");

            // when
            ReferatResponseDTO dto = referatMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getRefnr().toString());
            assertThat(dto.refnr()).isEqualTo(entity.getRefnr().intValueExact());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            ReferatCreateDTO dto = new ReferatCreateDTO(1, "Test 1");

            // when
            Referat entity = referatMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getRefnr()).isEqualTo(BigDecimal.valueOf(dto.refnr()));
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            ReferatUpdateDTO dto = new ReferatUpdateDTO("Test 2");

            // when
            Referat entity = referatMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getRefnr()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

    @Nested
    class ToFormContext {

        @Test
        void givenReferat_thenReturnsCorrectFormContextDTO() {
            // given
            Referat referat = new Referat(BigDecimal.valueOf(1), "Test Referat");

            // when
            ReferatFormContextDTO dto = referatMapper.toFormContext(referat);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.refnr()).isEqualTo(referat.getRefnr().toString());
        }

        @Test
        void givenReferatList_thenReturnsCorrectFormContextDTOList() {
            // given
            Referat referat1 = new Referat(BigDecimal.valueOf(1), "Test Referat 1");
            Referat referat2 = new Referat(BigDecimal.valueOf(2), "Test Referat 2");
            List<Referat> referatList = List.of(referat1, referat2);

            // when
            List<ReferatFormContextDTO> dtoList = referatMapper.toFormContext(referatList);

            // then
            assertThat(dtoList).hasSize(2);

            assertThat(dtoList.get(0).refnr()).isEqualTo(referat1.getRefnr().toString());
            assertThat(dtoList.get(0).bezeichnung()).isEqualTo(referat1.getBezeichnung());

            assertThat(dtoList.get(1).refnr()).isEqualTo(referat2.getRefnr().toString());
            assertThat(dtoList.get(1).bezeichnung()).isEqualTo(referat2.getBezeichnung());
        }
    }

}
