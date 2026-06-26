package cl.marfuego.ms_inventario.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI configurarOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("MarFuego - API Inventario")
                        .description("Microservicio encargado de la gestion de inventario")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo de desarrollo MarFuego")
                                .url("https://github.com/NachxCheesee/MarFuego-Colectivo")
                        )
                );
    }
}
