package pe.gob.inei.generadorinei.model.pojos.encuesta;

public class Info_tabla {
    private String _id;
    private String nombre;
    private String sql_tabla;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSql_tabla() {
        return sql_tabla;
    }

    public void setSql_tabla(String sql_tabla) {
        this.sql_tabla = sql_tabla;
    }
}
