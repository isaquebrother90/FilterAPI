package com.filterapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/** Esta classe contém configurações do Swagger. */
@Configuration
public class SwaggerConfig {

  @Autowired private Environment environment;

  /**
   * Configura o Swagger para documentar a API.
   *
   * @return OpenAPI
   */
  @Bean
  public OpenAPI customOpenAPI(@Value("${springdoc.api-docs.path}") String apiDocsPath) {
    String devUrl = environment.getProperty("filterapi.openapi.dev-url");
    return new OpenAPI()
        .info(
            new Info()
                .title("FilterAPI")
                .description(
                    "Microsserviço para filtro e geração de recomendação e relatórios de compras")
                .version("1.0")
                .license(
                    new License()
                        .name("MIT License")
                        .url("https://choosealicense.com/licenses/mit/")))
        .addServersItem(new Server().url(devUrl))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(
            new Components()
                .addSecuritySchemes(
                    "bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")));
  }
}
