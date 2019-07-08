package pe.gob.inei.generadorinei.model.pojos.componentes;

public class PUbicacion {

    private String _id;
    private String modulo;
    private String numero;
    private String var_dep;
    private String var_prov;
    private String var_dist;
    private String id_tabla;


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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getVar_dep() {
        return var_dep;
    }

    public void setVar_dep(String var_dep) {
        this.var_dep = var_dep;
    }

    public String getVar_prov() {
        return var_prov;
    }

    public void setVar_prov(String var_prov) {
        this.var_prov = var_prov;
    }

    public String getVar_dist() {
        return var_dist;
    }

    public void setVar_dist(String var_dist) {
        this.var_dist = var_dist;
    }

    public String getId_tabla() {
        return id_tabla;
    }

    public void setId_tabla(String id_tabla) {
        this.id_tabla = id_tabla;
    }

}
