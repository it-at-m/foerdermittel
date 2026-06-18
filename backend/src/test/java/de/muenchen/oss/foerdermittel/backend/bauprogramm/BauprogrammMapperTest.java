package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammCreateDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammMapper;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammResponseDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammUpdateDTO;
import lombok.AllArgsConstructor;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@AllArgsConstructor
public class BauprogrammMapperTest {

    private final BauprogrammMapper bauprogrammMapper = Mappers.getMapper(BauprogrammMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Bauprogramm entity = new Bauprogramm(BigDecimal.valueOf(1), "test");

            // when
            BauprogrammResponseDTO dto = bauprogrammMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getBauprogramm().toString());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            BauprogrammCreateDTO dto = new BauprogrammCreateDTO(1, "Test 1");

            // when
            Bauprogramm entity = bauprogrammMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getBauprogramm()).isEqualTo(BigDecimal.valueOf(dto.bauprogramm()));
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            BauprogrammUpdateDTO dto = new BauprogrammUpdateDTO("Test 2");

            // when
            Bauprogramm entity = bauprogrammMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getBauprogramm()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

}
