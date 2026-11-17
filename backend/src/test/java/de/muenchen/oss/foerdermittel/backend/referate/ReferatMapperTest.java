package de.muenchen.oss.foerdermittel.backend.referate;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.referat.Referat;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatCreateDTO;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatMapper;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatResponseDTO;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatUpdateDTO;
import java.math.BigDecimal;
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

}
