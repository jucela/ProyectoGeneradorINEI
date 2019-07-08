package pe.gob.inei.generadorinei.model.pojos.componentes;

import android.content.ContentValues;

import pe.gob.inei.generadorinei.model.dao.SQLConstantes;

public class PGps {
    private String _id;
    private String modulo;
    private String numero;
    private String var_lat;
    private String var_long;
    private String var_alt;
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

    public String getVar_lat() {
        return var_lat;
    }

    public void setVar_lat(String var_lat) {
        this.var_lat = var_lat;
    }

    public String getVar_long() {
        return var_long;
    }

    public void setVar_long(String var_long) {
        this.var_long = var_long;
    }

    public String getVar_alt() {
        return var_alt;
    }

    public void setVar_alt(String var_alt) {
        this.var_alt = var_alt;
    }

    public String getId_tabla() {
        return id_tabla;
    }

    public void setId_tabla(String id_tabla) {
        this.id_tabla = id_tabla;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.gps_id,_id);
        contentValues.put(SQLConstantes.gps_numero,numero);
        contentValues.put(SQLConstantes.gps_modulo,modulo);
        contentValues.put(SQLConstantes.gps_var_alt,var_alt);
        contentValues.put(SQLConstantes.gps_var_lat,var_lat);
        contentValues.put(SQLConstantes.gps_var_long,var_long);
        contentValues.put(SQLConstantes.gps_id_tabla,id_tabla);
        return contentValues;
    }
}
