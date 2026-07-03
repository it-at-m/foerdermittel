package de.muenchen.oss.foerdermittel.backend.traeger;

import de.muenchen.oss.foerdermittel.backend.traeger.Traeger;
import de.muenchen.oss.foerdermittel.backend.traeger.dto.TraegerCreateDTO;
import de.muenchen.oss.foerdermittel.backend.traeger.dto.TraegerMapper;
import de.muenchen.oss.foerdermittel.backend.traeger.dto.TraegerResponseDTO;
import de.muenchen.oss.foerdermittel.backend.traeger.dto.TraegerUpdateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class TraegerMapperTest {

    private final TraegerMapper traegerMapper = Mappers.getMapper(TraegerMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Traeger entity = new Traeger(BigDecimal.valueOf(1), "test");

            // when
            TraegerResponseDTO dto = traegerMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getKurzform().toString());
            assertThat(dto.kurzform()).isEqualTo(entity.getKurzform().intValueExact());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            TraegerCreateDTO dto = new TraegerCreateDTO(1, "Test 1");

            // when
            Traeger entity = traegerMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getKurzform()).isEqualTo(BigDecimal.valueOf(dto.kurzform()));
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            TraegerUpdateDTO dto = new TraegerUpdateDTO("Test 2");

            // when
            Traeger entity = traegerMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getKurzform()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

}
