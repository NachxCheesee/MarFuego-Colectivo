package cl.marfuego.ms_proveedores.model;

import cl.marfuego.ms_proveedores.enums.EstadoProveedor;
import cl.marfuego.ms_proveedores.enums.TipoPersona;
import jakarta.persistence.*;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "tipo_insumo")
    private String tipoInsumo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_persona")
    private TipoPersona tipoPersona;

    private String telefono;

    private String email;

    private String zona;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
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