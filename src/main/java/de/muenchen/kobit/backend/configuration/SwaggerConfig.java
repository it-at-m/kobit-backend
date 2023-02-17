package de.muenchen.kobit.backend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerOpenAPI() {
        return new OpenAPI()
                .openapi("3.0.1")
                .info(
                        new Info()
                                .version(this.getClass().getPackage().getImplementationVersion())
                                .title("KoBIT Backend Service")
                                .contact(
                                        new Contact()
                                                .name("Patrick Bussler")
                                                .email("patrick.bussler@meunchen.de"))
                                .description(
                                        "Das ist das Backend für dei KoBIT App aus dem die Daten"
                                                + " geliefert werden."));
    }
}
