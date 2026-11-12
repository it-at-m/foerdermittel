package de.muenchen.oss.foerdermittel.backend.configuration;

import de.muenchen.oss.foerdermittel.backend.projekt.ProjektFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.utils.SpringDocAnnotationsUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

@Configuration
@RequiredArgsConstructor
public class OpenAPIDocumentationConfiguration {

    public static final String SECURITY_SCHEME_NAME = "bearerAuth";

    private final OpenAPIProperties openAPIProperties;

    private static final List<Class<?>> ADDITIONAL_SCHEMAS = List.of(
            Pageable.class,
            ProjektFilter.class);

    @Bean
    public OpenApiCustomizer additionalSchemasCustomizer() {
        return openApi -> {
            if (openApi.getComponents() == null) {
                openApi.setComponents(new Components());
            }

            ADDITIONAL_SCHEMAS.forEach(type -> SpringDocAnnotationsUtils.extractSchema(
                    openApi.getComponents(),
                    type,
                    null,
                    null,
                    openApi.getSpecVersion()));
        };
    }

    @Bean
    public OpenAPI customOpenAPI() {
        final Components components = new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                        .name(SECURITY_SCHEME_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(new Info()
                        .title(openAPIProperties.getName())
                        .description(openAPIProperties.getDescription())
                        .version(openAPIProperties.getVersion()))
                .components(components);
    }
}
