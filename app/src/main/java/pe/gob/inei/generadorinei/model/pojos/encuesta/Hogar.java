package pe.gob.inei.generadorinei.model.pojos.encuesta;

import android.content.ContentValues;

public class Hogar {
    private String id_vivienda;
    private String id_hogar;
    private String nom_jefe;
    private String estado;
    private String vive;
    private String nroviven;
    private String principal;
    private String cobertura;

    public String getId_vivienda() {
        return id_vivienda;
    }

    public void setId_vivienda(String id_vivienda) {
        this.id_vivienda = id_vivienda;
    }

    public String getId_hogar() {
        return id_hogar;
    }

    public void setId_hogar(String id_hogar) {
        this.id_hogar = id_hogar;
    }

    public String getNom_jefe() {
        return nom_jefe;
    }

    public String getNom_jefe_var() {
        return "nom_jefe";
    }

    public void setNom_jefe(String nom_jefe) {
        this.nom_jefe = nom_jefe;
    }

    public String getEstado() {
        return estado;
    }

    public String getEstado_var() {
        return "estado";
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getVive() {
        return vive;
    }

    public String getVive_var() {
        return "vive";
    }

    public void setVive(String vive) {
        this.vive = vive;
    }

    public String getNroviven() {
        return nroviven;
    }

    public String getNroviven_var() {
        return "nroviven";
    }

    public void setNroviven(String nroviven) {
        this.nroviven = nroviven;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getPrincipal_var() {
        return "principal";
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getCobertura() {
        return cobertura;
    }

    public String getCobertura_var() {
        return "cobertura";
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_vivienda", id_vivienda);
        contentValues.put("id_hogar", id_hogar);
        contentValues.put("nom_jefe", nom_jefe);
        contentValues.put("estado", estado);
        contentValues.put("principal", principal);

        return contentValues;
    }
}
