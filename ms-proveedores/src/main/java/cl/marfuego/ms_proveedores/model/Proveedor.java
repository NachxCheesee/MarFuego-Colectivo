package cl.marfuego.ms_proveedores.model;

import cl.marfuego.ms_proveedores.enums.EstadoProveedor;
import cl.marfuego.ms_proveedores.enums.TipoPersona;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "proveedores")
@Schema(name = "Proveedor", description = "Entidad que representa a un distribuidor o proveedor de insumos de la cadena MarFuego")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único autoincremental (no se coloca de manera manual) del proveedor en la base de datos", example = "1")
    private Long id;

    @Schema(description = "Nombre de la empresa o del proveedor", example = "Distribuidora del Mar Ltda.")
    private String nombre;

    @Column(name = "tipo_insumo")
    @Schema(description = "Categoría o tipo de insumos que provee (Mariscos, Verduras, Desechables, etc.)", example = "Mariscos congelados")
    private String tipoInsumo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_persona")
    @Schema(description = "Clasificación legal del proveedor", example = "JURIDICA", allowableValues = {"NATURAL", "JURIDICA"})
    private TipoPersona tipoPersona;

    @Schema(description = "Teléfono de contacto del proveedor", example = "+56912345678")
    private String telefono;

    @Schema(description = "Correo electrónico de contacto comercial", example = "ventas@distribuidoradelmar.cl")
    private String email;

    @Schema(description = "Zona geográfica o sector de cobertura del proveedor", example = "Región de Los Lagos")
    private String zona;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    @Schema(description = "Estado operativo actual del proveedor", example = "ACTIVO", allowableValues = {"ACTIVO", "INACTIVO", "SUSPENDIDO"})
    private EstadoProveedor estado;


    public Proveedor() {}

    public Proveedor(Long id, String nombre, String tipoInsumo, TipoPersona tipoPersona, String telefono, String email, String zona, EstadoProveedor estado) {
        this.id = id;
        this.nombre = nombre;
        this.tipoInsumo = tipoInsumo;
        this.tipoPersona = tipoPersona;
        this.telefono = telefono;
        this.email = email;
        this.zona = zona;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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