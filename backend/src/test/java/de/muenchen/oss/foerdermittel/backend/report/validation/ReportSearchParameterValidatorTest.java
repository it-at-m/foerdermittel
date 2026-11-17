
package de.muenchen.oss.foerdermittel.backend.report.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.referat.ReferatService;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class ReportSearchParameterValidatorTest {

    @Mock
    private FoerderbereichService foerderbereichService;

    @Mock
    private ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;

    @Mock
    private StadtbezirkService stadtbezirkService;

    @Mock
    private UnterabschnittService unterabschnittService;

    @Mock
    private SiedlungsgebietService siedlungsgebietService;

    @Mock
    private KurzbezeichnungService kurzbezeichnungService;

    @Mock
    private BauprogrammService bauprogrammService;

    @Mock
    private ReferatService referatService;

    @Mock
    private KrankenhausService krankenhausService;

    @InjectMocks
    private ReportSearchParameterValidator validator;

    @Test
    void givenValidFoerderbereich_thenCallsServiceWithBigDecimal() {
        validator.validateFoerderbereich("123.45");

        verify(foerderbereichService)
                .checkExistsByFoerderbereich(new BigDecimal("123.45"));
    }

    @Test
    void givenValidStadtbezirk_thenCallsServiceWithBigDecimal() {
        validator.validateStadtbezirk("42");

        verify(stadtbezirkService)
                .checkExistsByStadtbezirk(new BigDecimal("42"));
    }

    @Test
    void givenValidSiedlungsgebiet_thenCallsServiceWithBigDecimal() {
        validator.validateSiedlungsgebiet("12.5");

        verify(siedlungsgebietService)
                .checkExistsBySiedlungsgebiet(new BigDecimal("12.5"));
    }

    @Test
    void givenValidBauprogramm_thenCallsServiceWithBigDecimal() {
        validator.validateBauprogramm("100");

        verify(bauprogrammService)
                .checkExistsByBauprogramm(new BigDecimal("100"));
    }

    @Test
    void givenValidReferat_thenCallsServiceWithBigDecimal() {
        validator.validateReferat("5");

        verify(referatService)
                .checkExistsByReferat(new BigDecimal("5"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "abc", "12.34.56" })
    void givenInvalidFoerderbereich_thenThrowsBadRequest(String value) {
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> validator.validateFoerderbereich(value));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(
                "Ungültiger numerischer Suchparameter: " + value,
                exception.getReason());

        verifyNoInteractions(foerderbereichService);
    }

    @Test
    void givenInvalidStadtbezirk_thenThrowsBadRequest() {
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> validator.validateStadtbezirk("abc"));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(
                "Ungültiger numerischer Suchparameter: abc",
                exception.getReason());

        verifyNoInteractions(stadtbezirkService);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " " })
    void givenBlankFoerderbereich_thenDoesNotCallService(String value) {
        validator.validateFoerderbereich(value);

        verifyNoInteractions(foerderbereichService);
    }

    @Test
    void givenNullFoerderbereich_thenDoesNotCallService() {
        validator.validateFoerderbereich(null);

        verifyNoInteractions(foerderbereichService);
    }
}
