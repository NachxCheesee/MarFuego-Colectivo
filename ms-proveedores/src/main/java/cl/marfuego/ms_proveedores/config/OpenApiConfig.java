package cl.marfuego.ms_proveedores.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean // Registra el componente de Swagger en el contenedor de Spring
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("MarFuego - API Proveedores") // Título personalizado
                        .description("Microservicio encargado de la gestión y control de los proveedores de MarFuego") // Su descripción
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo MarFuego")
                                .url("https://github.com/NachxCheesee/MarFuego-Colectivo")
                        )
                );
    }

}
