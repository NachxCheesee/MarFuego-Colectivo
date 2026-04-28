# MarFuego Colectivo – 001D Desarrollo FullStack 1

## Descripción
Sistema de arquitectura de microservicios diseñado para la gestión integral de la cadena gastronómica **MarFuego Colectivo**, con operaciones en Puerto Montt, Ancud y Castro. El proyecto se centra en resolver la sincronización de disponibilidad de platos en tiempo real, el control de inventario y la gestión eficiente de mesas y pedidos a través de una arquitectura escalable y desacoplada.

## Equipo
| Nombre | GitHub |
| :--- | :--- |
| [Ignacio Catalán] | @NachxCheesee |
| [Brayan Gonzalez] | @Bra7anG |
| [Gabriel Vargas] | @AsperFire |

## Microservicios Implementados
| # | Microservicio | Puerto | Descripción |
| :--- | :--- | :--- | :--- |
| 1 | ms-locales | 8081 | Gestión centralizada de sedes físicas y control de estado de mesas (Libre/Ocupada). |

## Tecnologías Utilizadas
- **Java 17 / Spring Boot 4.0.6**: Framework base para el desarrollo de servicios.
- **Spring Data JPA + Hibernate**: Gestión de la persistencia y mapeo de entidades.
- **H2 Database**: Base de datos en memoria para desarrollo y pruebas rápidas.
- **Flyway Migration**: Control de versiones del esquema de base de datos SQL.
- **Spring Web**: Creación de APIs RESTful.
- **Bean Validation**: Validación de integridad de datos en DTOs.
- **SLF4J**: Registro de logs para auditoría y depuración.

## Cómo Ejecutar el Proyecto
1. Clonar el repositorio: `git clone [URL]`
2. Configurar la base de datos en `application.properties`
3. Ejecutar cada microservicio: `./mvnw spring-boot:run`

## Estado del Proyecto
- [x] Análisis de Dominio y Reglas de Negocio.
- [ ] Implementación de ms-locales — EN DESARROLLO.
- [ ] Configuración de base de datos y migraciones iniciales.


