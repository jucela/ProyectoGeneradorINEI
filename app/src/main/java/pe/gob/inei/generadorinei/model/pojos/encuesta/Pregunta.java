package pe.gob.inei.generadorinei.model.pojos.encuesta;

public class Pregunta {
    private String _id;
    private String modulo;
    private String pagina;
    private String numero;
    private String tipo_pregunta;
    private String tipo_actividad;

    public Pregunta() {
    }

    public Pregunta(String _id, String modulo, String pagina, String numero, String tipo_pregunta, String tipo_actividad) {
        this._id = _id;
        this.modulo = modulo;
        this.pagina = pagina;
        this.numero = numero;
        this.tipo_pregunta = tipo_pregunta;
        this.tipo_actividad = tipo_actividad;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo_pregunta() {
        return tipo_pregunta;
    }

    public void setTipo_pregunta(String tipo_pregunta) {
        this.tipo_pregunta = tipo_pregunta;
    }

    public String getTipo_actividad() {
        return tipo_actividad;
    }

    public void setTipo_actividad(String tipo_actividad) {
        this.tipo_actividad = tipo_actividad;
    }
}
