package pe.gob.inei.generadorinei.model.pojos.encuesta;

public class Usuario {
    private String _id;
    private String clave;
    private String cargo_id;
    private String dni;
    private String nombre;
    private String supervisor_id;
    private String coordinador_id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCargo_id() {
        return cargo_id;
    }

    public void setCargo_id(String cargo_id) {
        this.cargo_id = cargo_id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(String supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    public String getCoordinador_id() {
        return coordinador_id;
    }

    public void setCoordinador_id(String coordinador_id) {
        this.coordinador_id = coordinador_id;
    }
}
