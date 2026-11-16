package de.muenchen.oss.foerdermittel.backend.antrag;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.antrag.dto.AntragProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.bewilligung.BewilligungRepository;
import de.muenchen.oss.foerdermittel.backend.common.ConflictException;
import de.muenchen.oss.foerdermittel.backend.common.DeleteNotAllowedException;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AntragServiceTest {

    private static final Long ANTRAG_ID = 1L;
    private static final String PROJNR = "3325101";

    @Mock
    private AntragRepository antragRepository;

    @Mock
    private ProjektService projektService;

    @Mock
    private BewilligungRepository bewilligungRepository;

    @InjectMocks
    private AntragService unitUnderTest;

    private static Projekt projekt() {
        final Projekt projekt = new Projekt();
        projekt.setProjnr(PROJNR);
        return projekt;
    }

    private static Antrag antrag(final Antragstyp antragstyp) {
        final Antrag antrag = new Antrag();
        antrag.setAntragstyp(antragstyp);
        antrag.setProjekt(projekt());
        return antrag;
    }

    @Nested
    class GetAntragFormContext {

        private static UniqueAntrag uniqueAntrag(final String projnr, final Antragstyp antragstyp, final Long id) {
            return new UniqueAntrag() {
                @Override
                public String getProjnr() {
                    return projnr;
                }

                @Override
                public Antragstyp getAntragstyp() {
                    return antragstyp;
                }

                @Override
                public Long getId() {
                    return id;
                }
            };
        }

        @Test
        void givenUniqueAntraege_thenReturnTheirIdsPerProjekt() {
            final String otherProjnr = "1124101";
            when(projektService.getProjektFormContextDTOs()).thenReturn(List.of(
                    new ProjektFormContextDTO(PROJNR, "Projekt 1", "Straße 1", "11", BigDecimal.ONE),
                    new ProjektFormContextDTO(otherProjnr, "Projekt 2", "Straße 2", "12", BigDecimal.ONE)));
            when(antragRepository.findUniqueAntraege(Set.of(Antragstyp.E, Antragstyp.U))).thenReturn(List.of(
                    uniqueAntrag(PROJNR, Antragstyp.E, 1L),
                    uniqueAntrag(PROJNR, Antragstyp.U, 2L)));

            final AntragFormContext result = unitUnderTest.getAntragFormContext();

            assertThat(result.projekte()).containsExactly(
                    new AntragProjektFormContextDTO(PROJNR, "Projekt 1", "Straße 1", "11", 1L, 2L),
                    new AntragProjektFormContextDTO(otherProjnr, "Projekt 2", "Straße 2", "12", null, null));
        }
    }

    @Nested
    class CreateAntrag {

        @Test
        void givenDuplicateUniqueTyp_thenThrowConflict() {
            when(projektService.getProjekt(PROJNR)).thenReturn(projekt());
            when(antragRepository.existsOtherAntrag(PROJNR, Antragstyp.U, null)).thenReturn(true);

            assertThatThrownBy(() -> unitUnderTest.createAntrag(antrag(Antragstyp.U), PROJNR))
                    .isInstanceOf(ConflictException.class);
            verify(antragRepository, never()).insert(any());
        }

        @Test
        void givenNonUniqueTyp_thenSkipDuplicateCheckAndInsert() {
            final Antrag antrag = antrag(Antragstyp.F);
            when(projektService.getProjekt(PROJNR)).thenReturn(projekt());
            when(antragRepository.insert(antrag)).thenReturn(antrag);

            assertThat(unitUnderTest.createAntrag(antrag, PROJNR)).isEqualTo(antrag);
            verify(antragRepository, never()).existsOtherAntrag(any(), any(), any());
        }
    }

    @Nested
    class UpdateAntrag {

        @Test
        void givenDuplicateUniqueTyp_thenExcludeOwnIdAndThrowConflict() {
            when(antragRepository.findById(ANTRAG_ID)).thenReturn(Optional.of(antrag(Antragstyp.F)));
            when(antragRepository.existsOtherAntrag(PROJNR, Antragstyp.E, ANTRAG_ID)).thenReturn(true);

            assertThatThrownBy(() -> unitUnderTest.updateAntrag(antrag(Antragstyp.E), ANTRAG_ID))
                    .isInstanceOf(ConflictException.class);
            verify(antragRepository, never()).update(any());
        }
    }

    @Nested
    class DeleteAntrag {

        @Test
        void givenBewilligungExists_thenThrowDeleteNotAllowed() {
            when(antragRepository.findById(ANTRAG_ID)).thenReturn(Optional.of(antrag(Antragstyp.E)));
            when(bewilligungRepository.existsByAntragId(ANTRAG_ID)).thenReturn(true);

            assertThatThrownBy(() -> unitUnderTest.deleteAntrag(ANTRAG_ID))
                    .isInstanceOf(DeleteNotAllowedException.class);
            verify(antragRepository, never()).delete(any());
        }

        @Test
        void givenNoBewilligung_thenDelete() {
            final Antrag antrag = antrag(Antragstyp.E);
            when(antragRepository.findById(ANTRAG_ID)).thenReturn(Optional.of(antrag));

            unitUnderTest.deleteAntrag(ANTRAG_ID);

            verify(antragRepository).delete(antrag);
        }
    }
}
