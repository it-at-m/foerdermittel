package de.muenchen.oss.foerdermittel.backend;

import de.muenchen.oss.foerdermittel.backend.configuration.security.SecurityProperties;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

/// Configures a mocked [JwtDecoder] as Spring bean to test authorization via roles.
///
/// When an Authorization header is provided in the request, the Bearer value is mapped to an
/// equivalent role if registered in [TestSecurityConfiguration#MOCKED_ROLES].
///
/// e.g. `Authorization: "Bearer reader"` -> Role reader
@TestConfiguration
@RequiredArgsConstructor
public class TestSecurityConfiguration {

    private final SecurityProperties securityProperties;

    private static final String TOKEN_WITHOUT_ROLE = "no-role";
    private static final List<String> MOCKED_ROLES = List.of("sachbearbeitung", "sachbearbeitunghaushalt", "admin");

    @Bean
    public JwtDecoder mockedJwtDecoder() {
        JwtDecoder mockedJwtDecoder = Mockito.mock(JwtDecoder.class);

        MOCKED_ROLES.forEach(role -> {
            Mockito.when(mockedJwtDecoder.decode(role))
                    .thenReturn(jwtWithRole(role));
        });

        Mockito.when(mockedJwtDecoder.decode(TOKEN_WITHOUT_ROLE))
                .thenReturn(jwtWithoutRole());

        return mockedJwtDecoder;
    }

    private Jwt jwtWithRole(String role) {
        return Jwt.withTokenValue(role)
                .header("alg", "none")
                .claim(
                        "resource_access",
                        Map.of(
                                securityProperties.getClientId(),
                                Map.of("roles", List.of(role))))
                .build();
    }

    private Jwt jwtWithoutRole() {
        return Jwt.withTokenValue(TOKEN_WITHOUT_ROLE)
                .header("alg", "none")
                .claim(
                        "resource_access",
                        Map.of(
                                securityProperties.getClientId(),
                                Map.of("roles", List.of())))
                .build();
    }

}
