
package de.muenchen.oss.foerdermittel.backend.hhjahr;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.hhjahr.dto.HhjahrMapper;
import de.muenchen.oss.foerdermittel.backend.hhjahr.dto.HhjahrResponseDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class HhjahrMapperTest {

    private final HhjahrMapper hhjahrMapper = Mappers.getMapper(HhjahrMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Hhjahr entity = new Hhjahr(
                    BigDecimal.valueOf(2024),
                    "Haushaltsjahr 2024",
                    "Testnotiz",
                    new ArrayList<>());

            // when
            HhjahrResponseDTO dto = hhjahrMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo("2024");
            assertThat(dto.hhjahr()).isEqualTo(entity.getHhjahr());
            assertThat(dto.hhbezeichnung()).isEqualTo(entity.getHhbezeichnung());
            assertThat(dto.notizen()).isEqualTo(entity.getNotizen());
            assertThat(dto.assignedProProjnr()).isEmpty();
        }
    }
}
