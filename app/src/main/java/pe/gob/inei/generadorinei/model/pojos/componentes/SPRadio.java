package pe.gob.inei.generadorinei.model.pojos.componentes;

public class SPRadio {
    private String _id;
    private String id_pregunta;
    private String subpregunta;
    private String var_input;
    private String var_especifique;


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

    public String getVar_especifique() {
        return var_especifique;
    }

    public void setVar_especifique(String var_especifique) {
        this.var_especifique = var_especifique;
    }
}
