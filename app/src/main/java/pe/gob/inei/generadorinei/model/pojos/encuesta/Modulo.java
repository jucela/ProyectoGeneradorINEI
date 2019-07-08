package pe.gob.inei.generadorinei.model.pojos.encuesta;

public class Modulo {
    private String _id;
    private String numero;
    private String titulo;
    private String cabecera;
    private String tipo_actividad;

    public Modulo() {
    }

    public Modulo(String _id, String numero, String titulo, String cabecera, String tipo_actividad) {
        this._id = _id;
        this.numero = numero;
        this.titulo = titulo;
        this.cabecera = cabecera;
        this.tipo_actividad = tipo_actividad;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public String getTipo_actividad() {
        return tipo_actividad;
    }

    public void setTipo_actividad(String tipo_actividad) {
        this.tipo_actividad = tipo_actividad;
    }
}
