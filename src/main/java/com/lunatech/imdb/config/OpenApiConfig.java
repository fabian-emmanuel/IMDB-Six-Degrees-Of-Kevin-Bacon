package com.lunatech.imdb.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;



@Configuration
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
public class OpenApiConfig {

    private final String moduleName;
    private final String apiVersion;

    public OpenApiConfig(
            @Value("${module-name}") String moduleName,
            @Value("${version}") String apiVersion) {
        this.moduleName = moduleName;
        this.apiVersion = apiVersion;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        final String apiTitle = String.format("%s API", StringUtils.capitalize(moduleName));
        final String apiDescription = String.format("API for %s. Contains public end points.", "Lunatech Movies");
        final Contact apiContact = new Contact().name(moduleName).url("website").email("email");
        return new OpenAPI()
                .info(new Info()
                        .title(apiTitle)
                        .description(apiDescription)
                        .termsOfService("Terms of service")
                        .contact(apiContact)
                        .version(apiVersion));
    }
}
