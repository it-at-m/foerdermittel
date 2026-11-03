package de.muenchen.oss.foerdermittel.backend.projekt;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektCreateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektResponseDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektUpdateDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class ProjektMapperTest {

    private final ProjektMapper projektMapper = Mappers.getMapper(ProjektMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // Given
            final Projekt projekt = new Projekt();
            projekt.setProjnr("1124101");
            projekt.setJahr("2026");
            projekt.setLfdnr1("0");
            projekt.setLfdnr2("01");
            projekt.setPname("Test Projekt");
            projekt.setPstrasse("Teststraße 1");
            projekt.setProjart("Test");
            projekt.setRefinanzierbar(true);
            projekt.setKauf(false);
            projekt.setKripplatz(BigDecimal.valueOf(10));
            projekt.setNotizen("Testnotiz");

            // When
            final ProjektResponseDTO dto = projektMapper.toDTO(projekt);

            // Then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(projekt.getProjnr());
            assertThat(dto.projnr()).isEqualTo(projekt.getProjnr());
            assertThat(dto.jahr()).isEqualTo(2026);
            assertThat(dto.lfdnr1()).isEqualTo(0);
            assertThat(dto.lfdnr2()).isEqualTo(1);
            assertThat(dto.pname()).isEqualTo(projekt.getPname());
            assertThat(dto.pstrasse()).isEqualTo(projekt.getPstrasse());
            assertThat(dto.projart()).isEqualTo(projekt.getProjart());
            assertThat(dto.refinanzierbar()).isEqualTo(projekt.getRefinanzierbar());
            assertThat(dto.kauf()).isEqualTo(projekt.getKauf());
            assertThat(dto.kripplatz()).isEqualByComparingTo(projekt.getKripplatz());
            assertThat(dto.notizen()).isEqualTo(projekt.getNotizen());
        }
    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // Given
            final ProjektCreateDTO dto = new ProjektCreateDTO(
                    "1124101",
                    12,
                    null,
                    null,
                    "ABC",
                    "12",
                    null,
                    26,
                    0,
                    1,
                    "Test Projekt",
                    "Teststraße 1",
                    "Test",
                    true,
                    false,
                    BigDecimal.valueOf(10),
                    BigDecimal.valueOf(20),
                    BigDecimal.valueOf(30),
                    "Testnotiz",
                    null,
                    null,
                    null);

            // When
            final Projekt entity = projektMapper.toEntity(dto);

            // Then
            assertThat(entity).isNotNull();
            assertThat(entity.getProjnr()).isEqualTo(dto.projnr());
            assertThat(entity.getJahr()).isEqualTo("26");
            assertThat(entity.getLfdnr1()).isEqualTo("0");
            assertThat(entity.getLfdnr2()).isEqualTo("1");
            assertThat(entity.getPname()).isEqualTo(dto.pname());
            assertThat(entity.getPstrasse()).isEqualTo(dto.pstrasse());
            assertThat(entity.getProjart()).isEqualTo(dto.projart());
            assertThat(entity.getRefinanzierbar()).isEqualTo(dto.refinanzierbar());
            assertThat(entity.getKauf()).isEqualTo(dto.kauf());
            assertThat(entity.getKripplatz()).isEqualByComparingTo(dto.kripplatz());
            assertThat(entity.getKigaplatz()).isEqualByComparingTo(dto.kigaplatz());
            assertThat(entity.getHortplatz()).isEqualByComparingTo(dto.hortplatz());
            assertThat(entity.getNotizen()).isEqualTo(dto.notizen());

            assertThat(entity.getFobFb()).isNull();
            assertThat(entity.getUasUa()).isNull();
            assertThat(entity.getKurKurzbez()).isNull();
        }

        @Test
        void givenUpdateDTO_thenUpdatesEntityFields() {
            // Given
            final ProjektUpdateDTO dto = new ProjektUpdateDTO(
                    "Neuer Projektname",
                    "Neue Straße 1",
                    null,
                    null,
                    null,
                    null,
                    null,
                    "Neu",
                    true,
                    false,
                    BigDecimal.valueOf(10),
                    BigDecimal.valueOf(20),
                    BigDecimal.valueOf(30),
                    "Neue Notiz",
                    null,
                    null,
                    null);

            final Projekt entity = new Projekt();
            entity.setProjnr("1124101");
            entity.setJahr("2026");
            entity.setLfdnr1("0");
            entity.setLfdnr2("01");

            // When
            projektMapper.updateEntity(dto, entity);

            // Then
            assertThat(entity.getPname()).isEqualTo(dto.pname());
            assertThat(entity.getPstrasse()).isEqualTo(dto.pstrasse());
            assertThat(entity.getProjart()).isEqualTo(dto.projart());
            assertThat(entity.getRefinanzierbar()).isEqualTo(dto.refinanzierbar());
            assertThat(entity.getKauf()).isEqualTo(dto.kauf());
            assertThat(entity.getKripplatz()).isEqualByComparingTo(dto.kripplatz());
            assertThat(entity.getKigaplatz()).isEqualByComparingTo(dto.kigaplatz());
            assertThat(entity.getHortplatz()).isEqualByComparingTo(dto.hortplatz());
            assertThat(entity.getNotizen()).isEqualTo(dto.notizen());

            assertThat(entity.getProjnr()).isEqualTo("1124101");
            assertThat(entity.getJahr()).isEqualTo("2026");
            assertThat(entity.getLfdnr1()).isEqualTo("0");
            assertThat(entity.getLfdnr2()).isEqualTo("01");
        }
    }
}
