package pe.gob.inei.generadorinei.model.pojos.componentes;

public class SPCheckbox {
    private String _id;
    private String id_pregunta;
    private String subpregunta;
    private String var_guardado;
    private String var_especifique;
    private String deshabilita;


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

    public String getVar_guardado() {
        return var_guardado;
    }

    public void setVar_guardado(String var_guardado) {
        this.var_guardado = var_guardado;
    }

    public String getVar_especifique() {
        return var_especifique;
    }

    public void setVar_especifique(String var_especifique) {
        this.var_especifique = var_especifique;
    }

    public String getDeshabilita() {
        return deshabilita;
    }

    public void setDeshabilita(String deshabilita) {
        this.deshabilita = deshabilita;
    }
}
