package cl.marfuego.ms_proveedores.dto;

import cl.marfuego.ms_proveedores.enums.EstadoProveedor;
import cl.marfuego.ms_proveedores.enums.TipoPersona;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "ProveedorDto", description = "Estructura de datos requerida para registrar o actualizar un proveedor de insumos")
public class ProveedorDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 letras")
    @Schema(description = "Nombre o razón social de la empresa proveedora", example = "Distribuidora del Mar Ltda.")
    private String nombre;

    @NotBlank(message = "El tipo de insumo no puede estar vacío")
    @Size(max = 50, message = "El tipo de insumo no puede superar los 50 caracteres")
    @Schema(description = "Categoría de productos que abastece a la cadena", example = "Mariscos congelados")
    private String tipoInsumo;

    @NotNull(message = "El tipo de persona es obligatorio")
    @Schema(description = "Clasificación legal del proveedor", example = "JURIDICA", allowableValues = {"NATURAL", "JURIDICA"})
    private TipoPersona tipoPersona;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
    @Schema(description = "Teléfono de contacto comercial", example = "+56912345678")
    private String telefono;

    @NotBlank(message = "El email no puede estar vacío")
    @Size(max = 50, message = "El email no puede superar los 50 caracteres")
    @Schema(description = "Correo electrónico para la gestión de pedidos", example = "ventas@distribuidoradelmar.cl")
    private String email;

    @NotBlank(message = "La zona no puede estar vacía")
    @Size(max = 50, message = "La zona no puede superar los 50 caracteres")
    @Schema(description = "Zona o región geográfica de cobertura", example = "Región de Los Lagos")
    private String zona;

    @NotNull(message = "El tipo de persona es obligatorio")
    @Schema(description = "Estado operativo asignado al proveedor", example = "ACTIVO", allowableValues = {"ACTIVO", "INACTIVO", "SUSPENDIDO"})
    private EstadoProveedor estado;

    public ProveedorDto() {
    }

    public ProveedorDto(String nombre, String tipoInsumo, TipoPersona tipoPersona, String telefono, String email, String zona, EstadoProveedor estado) {
        this.nombre = nombre;
        this.tipoInsumo = tipoInsumo;
        this.tipoPersona = tipoPersona;
        this.telefono = telefono;
        this.email = email;
        this.zona = zona;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(String tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public EstadoProveedor getEstado() {
        return estado;
    }

    public void setEstado(EstadoProveedor estado) {
        this.estado = estado;
    }


}
