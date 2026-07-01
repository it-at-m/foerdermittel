package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietCreateDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietMapper;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietResponseDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietUpdateDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class SiedlungsgebietMapperTest {

    private final SiedlungsgebietMapper siedlungsgebietMapper = Mappers.getMapper(SiedlungsgebietMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Siedlungsgebiet entity = new Siedlungsgebiet(BigDecimal.valueOf(1), "test");

            // when
            SiedlungsgebietResponseDTO dto = siedlungsgebietMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getSiedlungsgebiet().toString());
            assertThat(dto.siedlungsgebiet()).isEqualTo(entity.getSiedlungsgebiet().intValueExact());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            SiedlungsgebietCreateDTO dto = new SiedlungsgebietCreateDTO(1, "Test 1");

            // when
            Siedlungsgebiet entity = siedlungsgebietMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getSiedlungsgebiet()).isEqualTo(BigDecimal.valueOf(dto.siedlungsgebiet()));
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            SiedlungsgebietUpdateDTO dto = new SiedlungsgebietUpdateDTO("Test 2");

            // when
            Siedlungsgebiet entity = siedlungsgebietMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getSiedlungsgebiet()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

}
