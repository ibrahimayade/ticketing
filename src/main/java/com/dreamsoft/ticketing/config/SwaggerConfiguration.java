package com.dreamsoft.ticketing.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfiguration {
    @Value("${app.swagger-local.url}")
    private String swaggerLocalURL;

    @Value("${app.swagger-remote.url}")
    private String swaggerRemoteURL;
    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "Auth JWT";
        return new OpenAPI()
                .addServersItem(new Server().url(swaggerLocalURL))
                .addServersItem(new Server().url(swaggerRemoteURL))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("Bearer")
                                                .bearerFormat("JWT"))
                )
                .info(new Info().title("Ticketing application API").version("v1.0.0"));
    }

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> operation.addParametersItem(
                new Parameter()
                        .in("header")
                        .required(true)
                        .description("App name")
                        .name("app"));
    }
}
