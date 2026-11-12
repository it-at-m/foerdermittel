
package de.muenchen.oss.foerdermittel.backend.projekt;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.AuswertungProjektDAO;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektCreateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektResponseDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportBewilligungAuszahlungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportProjektuebersichtFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.Siedlungsgebiet;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class ProjektMapperTest {

    private final ProjektMapper projektMapper = Mappers.getMapper(ProjektMapper.class);

    private Projekt createProjekt() {
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
        projekt.setKigaplatz(BigDecimal.valueOf(20));
        projekt.setHortplatz(BigDecimal.valueOf(30));
        projekt.setNotizen("Testnotiz");

        projekt.setFobFb(new Foerderbereich(
                BigDecimal.valueOf(12), "Test", false, false, false, false));
        projekt.setBezStadtbezirk(new Stadtbezirk(BigDecimal.valueOf(13), "Test"));

        final Hauptabschnitt hauptabschnitt = new Hauptabschnitt("HA", "Test");
        projekt.setUasUa(new Unterabschnitt("UA", "Test", hauptabschnitt));

        projekt.setKurKurzbez(new Kurzbezeichnung("KB1", "Test"));
        projekt.setSgtSiedlungsgebiet(new Siedlungsgebiet(BigDecimal.ONE, "Test"));
        projekt.setBpgBauprogramm(new Bauprogramm(BigDecimal.ONE, "Test"));

        return projekt;
    }

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            final Projekt projekt = createProjekt();

            final ProjektResponseDTO dto = projektMapper.toDTO(projekt);

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
            assertThat(dto.kigaplatz()).isEqualByComparingTo(projekt.getKigaplatz());
            assertThat(dto.hortplatz()).isEqualByComparingTo(projekt.getHortplatz());

            assertThat(dto.notizen()).isEqualTo(projekt.getNotizen());
            assertThat(dto.fobFb())
                    .isEqualByComparingTo(projekt.getFobFb().getFb());
            assertThat(dto.bezStadtbezirk())
                    .isEqualByComparingTo(projekt.getBezStadtbezirk().getStadtbezirk());
            assertThat(dto.uasUa()).isEqualTo(projekt.getUasUa().getUa());
            assertThat(dto.kurKurzbez())
                    .isEqualTo(projekt.getKurKurzbez().getKurzbez());
            assertThat(dto.sgtSiedlungsgebiet())
                    .isEqualByComparingTo(projekt.getSgtSiedlungsgebiet().getSiedlungsgebiet());
            assertThat(dto.bpgBauprogramm())
                    .isEqualByComparingTo(projekt.getBpgBauprogramm().getBauprogramm());
        }
    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            final ProjektCreateDTO dto = new ProjektCreateDTO("1124101", 12, "ABC", "12", 26, 0, 1, null, null, null,
                    "Test Projekt", "Teststraße 1", "Test", true, false, "Testnotiz", null, null, null,
                    BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(30));

            final Projekt entity = projektMapper.toEntity(dto);

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
            assertThat(entity.getBezStadtbezirk()).isNull();
            assertThat(entity.getSgtSiedlungsgebiet()).isNull();
            assertThat(entity.getBpgBauprogramm()).isNull();
            assertThat(entity.getKrnKrhname()).isNull();
        }

        @Test
        void givenUpdateDTO_thenUpdatesEntityFields() {

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

            // When
            final Projekt entity = projektMapper.toEntity(dto);

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

            assertThat(entity.getProjnr()).isNull();
            assertThat(entity.getJahr()).isNull();
            assertThat(entity.getLfdnr1()).isNull();
            assertThat(entity.getLfdnr2()).isNull();
        }
    }

    @Nested
    class ToFormContext {

        @Test
        void givenEntity_thenReturnsCorrectFormContextDTO() {

            final Projekt projekt = createProjekt();

            final ProjektFormContextDTO dto = projektMapper.toFormContext(projekt);

            assertThat(dto).isNotNull();
            assertThat(dto.projnr()).isEqualTo(projekt.getProjnr());
            assertThat(dto.pname()).isEqualTo(projekt.getPname());
            assertThat(dto.pstrasse()).isEqualTo(projekt.getPstrasse());

            assertThat(dto.fobFb()).isEqualByComparingTo(projekt.getFobFb().getFb());
            assertThat(dto.bezStadtbezirk()).isEqualByComparingTo(projekt.getBezStadtbezirk().getStadtbezirk());
        }

        @Test
        void givenEntityList_thenReturnsCorrectFormContextDTOList() {

            final Projekt projekt = createProjekt();

            final List<ProjektFormContextDTO> result = projektMapper.toFormContext(List.of(projekt));

            assertThat(result).containsExactly(projektMapper.toFormContext(projekt));
        }
    }

    @Nested
    class ReportMappings {

        @Test
        void givenBasicProjektDAOList_thenReturnsProjektuebersichtFormContextList() {

            final BasicProjektDAO projekt = new BasicProjektDAO("P-123", "Projektname", "Projektstraße 1");

            final List<ReportProjektuebersichtFormContextDTO> result = projektMapper.toReportProjektuebersichtFormContext(List.of(projekt));

            assertThat(result).containsExactly(
                    new ReportProjektuebersichtFormContextDTO(
                            "P-123", "Projektname", "Projektstraße 1"));
        }

        @Test
        void givenAuswertungProjektDAOList_thenReturnsAuswertungProjektFormContextList() {
            final AuswertungProjektDAO projekt = new AuswertungProjektDAO("2026", "Projektname", "Projektstraße 1", Krisofp.K);

            final List<ReportAuswertungProjektFormContextDTO> result = projektMapper.toReportAuswertungProjektFormContext(List.of(projekt));

            assertThat(result).containsExactly(
                    new ReportAuswertungProjektFormContextDTO(
                            "2026", "Projektstraße 1", "Projektname", Krisofp.K));
        }

        @Test
        void givenBasicProjektDAOList_thenReturnsBewilligungAuszahlungFormContextList() {

            final BasicProjektDAO projekt = new BasicProjektDAO("P-123", "Projektname", "Projektstraße 1");

            final List<ReportBewilligungAuszahlungProjektFormContextDTO> result = projektMapper
                    .toReportBewilligungAuszahlungProjektFormContext(List.of(projekt));

            assertThat(result).containsExactly(
                    new ReportBewilligungAuszahlungProjektFormContextDTO(
                            "P-123", "Projektstraße 1", "Projektname"));
        }
    }
}
