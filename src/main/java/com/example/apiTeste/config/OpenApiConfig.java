package com.example.apiTeste.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info()
                        .title("API")
                        .description("Uma API Rest conectada ao banco de dados MySQL.")
                        .contact(new Contact().name("José Felipe Alexandre Martins").email("josefelipealexandre1997@gmail.com").url(""))
                        .version("Versão 0.0.1-SNAPSHOT"));
    }
}
