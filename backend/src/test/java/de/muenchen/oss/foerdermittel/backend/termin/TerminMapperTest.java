package de.muenchen.oss.foerdermittel.backend.termin;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.termin.dto.TerminCreateDTO;
import de.muenchen.oss.foerdermittel.backend.termin.dto.TerminMapper;
import de.muenchen.oss.foerdermittel.backend.termin.dto.TerminResponseDTO;
import de.muenchen.oss.foerdermittel.backend.termin.dto.TerminUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class TerminMapperTest {

    private final TerminMapper terminMapper = Mappers.getMapper(TerminMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            final Foerderbereich foerderbereich = new Foerderbereich();
            foerderbereich.setFb(BigDecimal.valueOf(123));

            final Stadtbezirk stadtbezirk = new Stadtbezirk();
            stadtbezirk.setStadtbezirk(BigDecimal.valueOf(11));

            final Projekt projekt = new Projekt();
            projekt.setProjnr("1124101");
            projekt.setPname("Test Projekt");
            projekt.setPstrasse("Teststraße 1");
            projekt.setFoerderbereich(foerderbereich);
            projekt.setStadtbezirk(stadtbezirk);

            final Termin entity = new Termin(
                    1L,
                    projekt,
                    LocalDate.of(2024, 9, 14),
                    "Max Mustermann",
                    true,
                    "12345678",
                    "Test");

            final TerminResponseDTO dto = terminMapper.toDTO(entity);

            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getId().toString());
            assertThat(dto.termin()).isEqualTo(entity.getTermin());
            assertThat(dto.zustaendig()).isEqualTo(entity.getZustaendig());
            assertThat(dto.telefon()).isEqualTo(entity.getTelefon());
            assertThat(dto.notizen()).isEqualTo(entity.getNotizen());
            assertThat(dto.projnr()).isEqualTo(entity.getProjekt().getProjnr());
            assertThat(dto.pname()).isEqualTo(entity.getProjekt().getPname());
            assertThat(dto.pstrasse()).isEqualTo(entity.getProjekt().getPstrasse());
            assertThat(dto.fob_fb()).isEqualTo(entity.getProjekt().getFoerderbereich().getFb());
            assertThat(dto.bez_stadtbezirk()).isEqualTo(entity.getProjekt().getStadtbezirk().getStadtbezirk());
        }
    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            final TerminCreateDTO dto = new TerminCreateDTO(
                    LocalDate.of(2024,9,15),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test 1",
                    "1124101");

            final Termin entity = terminMapper.toEntity(dto);

            assertThat(entity).isNotNull();
            assertThat(entity.getId()).isNull();
            assertThat(entity.getTermin()).isEqualTo(dto.termin());
            assertThat(entity.getZustaendig()).isEqualTo(dto.zustaendig());
            assertThat(entity.getTelefon()).isEqualTo(dto.telefon());
            assertThat(entity.getUeberwachung()).isEqualTo(dto.ueberwachung());
            assertThat(entity.getNotizen()).isEqualTo(dto.notizen());
            assertThat(entity.getProjekt()).isNotNull();
            assertThat(entity.getProjekt().getProjnr()).isEqualTo(dto.projnr());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            final TerminUpdateDTO dto = new TerminUpdateDTO(
                    LocalDate.of(2024,9,16),
                    true,
                    "Max Mustermann",
                    "12345678",
                    "Test 2");

            final Termin entity = terminMapper.toEntity(dto);

            assertThat(entity).isNotNull();
            assertThat(entity.getId()).isNull();
            assertThat(entity.getProjekt()).isNull();
            assertThat(entity.getTermin()).isEqualTo(dto.termin());
            assertThat(entity.getZustaendig()).isEqualTo(dto.zustaendig());
            assertThat(entity.getTelefon()).isEqualTo(dto.telefon());
            assertThat(entity.getNotizen()).isEqualTo(dto.notizen());
        }
    }
}
