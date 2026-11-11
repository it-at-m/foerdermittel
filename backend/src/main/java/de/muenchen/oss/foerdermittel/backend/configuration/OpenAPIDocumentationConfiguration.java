package de.muenchen.oss.foerdermittel.backend.configuration;

import de.muenchen.oss.foerdermittel.backend.projekt.ProjektFilter;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenAPIDocumentationConfiguration {

    public static final String SECURITY_SCHEME_NAME = "bearerAuth";

    private final OpenAPIProperties openAPIProperties;

    private static final List<Class<?>> ADDITIONAL_SCHEMAS = List.of(
            ProjektFilter.class
    );

    @Bean
    public OpenAPI customOpenAPI() {

       Components components = new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                        .name(SECURITY_SCHEME_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));
        ADDITIONAL_SCHEMAS.forEach(type ->
                registerSchema(components, type)
        );

        return new OpenAPI()
                .info(new Info()
                        .title(openAPIProperties.getName())
                        .description(openAPIProperties.getDescription())
                        .version(openAPIProperties.getVersion()))

                .components(components);
    }
    private static void registerSchema(
            Components components,
            Class<?> type
    ) {
        ResolvedSchema resolvedSchema =
                ModelConverters.getInstance()
                        .resolveAsResolvedSchema(
                                new AnnotatedType(type).resolveAsRef(true)
                        );

        if (resolvedSchema.referencedSchemas != null) {
            resolvedSchema.referencedSchemas.forEach(
                    components::addSchemas
            );
        }

        if (resolvedSchema.schema != null
                && resolvedSchema.schema.getName() != null) {
            components.addSchemas(
                    resolvedSchema.schema.getName(),
                    resolvedSchema.schema
            );
        }
    }
}
