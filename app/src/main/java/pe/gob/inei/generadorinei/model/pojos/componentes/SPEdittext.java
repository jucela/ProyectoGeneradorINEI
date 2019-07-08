package pe.gob.inei.generadorinei.model.pojos.componentes;

public class SPEdittext {

    private String _id;
    private String id_pregunta;
    private String subpregunta;
    private String var_input;
    private String tipo;
    private String longitud;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(String id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public String getSubpregunta() {
        return subpregunta;
    }

    public void setSubpregunta(String subpregunta) {
        this.subpregunta = subpregunta;
    }

    public String getVar_input() {
        return var_input;
    }

    public void setVar_input(String var_input) {
        this.var_input = var_input;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
