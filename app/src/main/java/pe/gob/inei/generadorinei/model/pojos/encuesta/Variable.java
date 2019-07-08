package pe.gob.inei.generadorinei.model.pojos.encuesta;

public class Variable {
    private String _id;
    private String id_tabla;
    private String id_modulo;
    private String id_pagina;
    private String id_pregunta;
    private String pk;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_tabla() {
        return id_tabla;
    }

    public void setId_tabla(String id_tabla) {
        this.id_tabla = id_tabla;
    }

    public String getId_modulo() {
        return id_modulo;
    }

    public void setId_modulo(String id_modulo) {
        this.id_modulo = id_modulo;
    }

    public String getId_pagina() {
        return id_pagina;
    }

    public void setId_pagina(String id_pagina) {
        this.id_pagina = id_pagina;
    }

    public String getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(String id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }
}
