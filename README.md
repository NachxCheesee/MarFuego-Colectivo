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
| 2 | ms-productos | 8082 | Gestión de catálogo de productos, control de stock (actual y mínimo) y validación de pertenencia a locales. |

## Tecnologías Utilizadas
- **Java 17 / Spring Boot 3.x**: Framework base para el desarrollo de servicios.
- **Spring Data JPA + Hibernate**: Gestión de la persistencia y mapeo de entidades.
- **Spring Cloud OpenFeign**: Comunicación declarativa entre microservicios (Sincronización Producto -> Local).
- **H2 Database**: Base de datos en memoria para desarrollo y pruebas rápidas.
- **Flyway Migration**: Control de versiones del esquema de base de datos SQL.
- **Spring Web**: Creación de APIs RESTful.
- **Bean Validation**: Validación de integridad de datos en DTOs.
- **SLF4J**: Registro de logs para auditoría y depuración.

## Cómo Ejecutar el Proyecto
1. Clonar el repositorio: `git clone [URL]`
2. Configurar las bases de datos en los respectivos archivos `application.properties`.
3. Asegurarse de que el microservicio de locales (puerto 8081) esté activo para pruebas de integración de productos.
4. Ejecutar cada microservicio: `./mvnw spring-boot:run`

## Estado del Proyecto
- [x] Análisis de Dominio y Reglas de Negocio.
- [x] Implementación de **ms-productos** — FUNCIONAL (Pendiente integración final).
- [ ] Implementación de **ms-locales** — EN DESARROLLO.
- [x] Configuración de base de datos y migraciones iniciales para productos.
