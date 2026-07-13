package de.muenchen.oss.foerdermittel.backend.foerderbereich;

import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichCreateDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichMapper;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichResponseDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichUpdateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class FoerderbereichMapperTest {

    private final FoerderbereichMapper foerderbereichMapper = Mappers.getMapper(FoerderbereichMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Foerderbereich entity = new Foerderbereich(BigDecimal.valueOf(1), "test", false, true, false, true);

            // when
            FoerderbereichResponseDTO dto = foerderbereichMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getFb().toString());
            assertThat(dto.fb()).isEqualTo(entity.getFb().intValueExact());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            FoerderbereichCreateDTO dto = new FoerderbereichCreateDTO(1, "Test 1", false, true,false,true );

            // when
            Foerderbereich entity = foerderbereichMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getFb()).isEqualTo(BigDecimal.valueOf(dto.fb()));
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            FoerderbereichUpdateDTO dto = new FoerderbereichUpdateDTO("Test 2", false, true, false, true);

            // when
            Foerderbereich entity = foerderbereichMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getFb()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

}
