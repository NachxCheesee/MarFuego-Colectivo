package cl.marfuego.ms_pedidos.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI configurarOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MarFuego - API Pedidos")
                        .description("Microservicio encargado de la gestion de los pedidos junto con sus respectivos detalles")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo de desarrollo MarFuego")
                                .url("https://github.com/NachxCheesee/MarFuego-Colectivo")
                        )
                );
    }
}
