package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammCreateDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammMapper;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammResponseDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammUpdateDTO;
import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

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

    @Nested
    class FormEntity {

        @Test
        void givenFormContext_thenReturnCorrectDTO() {
            // given
            BauprogrammFormContext formContext = new BauprogrammFormContext(List.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2)));

            // when
            BauprogrammFormContextDTO dto = bauprogrammMapper.toDTO(formContext);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.bauprogramme().stream()
                    .map(BigDecimal::valueOf)
                    .toList())
                    .containsExactlyElementsOf(formContext.bauprogramme());
        }

    }

}
