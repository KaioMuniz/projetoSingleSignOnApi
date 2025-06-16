package br.com.cotiinformatica.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfiguration {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Controle de usuários - Coti Informática")
                .description("Documentação da API desenvolvida com Spring Boot")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Coti Informática")
                    .url("https://www.cotiinformatica.com.br")
                    .email("contato@cotiinformatica.com.br")));
    }
}
