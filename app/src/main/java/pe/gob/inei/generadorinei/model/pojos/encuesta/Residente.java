package pe.gob.inei.generadorinei.model.pojos.encuesta;

import android.content.ContentValues;

import java.lang.reflect.Field;

public class Residente {
    private String id_vivienda;
    private String id_hogar;
    private String id_residente;
    private String nombre;
    private String parentesco;
    private String sexo;
    private String edad_anio;
    private String edad_mes;
    private String estado_civil;
    private String completo;
    private String cobertura;

    public Residente(){
    }

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

    public String getId_residente() {
        return id_residente;
    }

    public void setId_residente(String id_residente) {
        this.id_residente = id_residente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombre_var(){
        return "nombre";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getParentesco() {
        return parentesco;
    }

    public String getParentesco_var() {
        return "parentesco";
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getSexo() {
        return sexo;
    }

    public String getSexo_var() {
        return "sexo";
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEdad_anio() {
        return edad_anio;
    }

    public String getEdad_anio_var() {
        return "edad_anio";
    }

    public void setEdad_anio(String edad_anio) {
        this.edad_anio = edad_anio;
    }

    public String getEdad_mes() {
        return edad_mes;
    }

    public String getEdad_mes_var() {
        return "edad_mes";
    }

    public void setEdad_mes(String edad_mes) {
        this.edad_mes = edad_mes;
    }

    public String getEstado_civil() {
        return estado_civil;
    }

    public String getEstado_civil_var() {
        return "estado_civil";
    }

    public void setEstado_civil(String estado_civil) {
        this.estado_civil = estado_civil;
    }

    public String getCompleto() {
        return completo;
    }

    public String getCompleto_var() {
        return "completo";
    }

    public void setCompleto(String completo) {
        this.completo = completo;
    }

    public String getCobertura() {
        return cobertura;
    }

    public String getCobertura_var() {
        return "cobertuta";
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_vivienda", id_vivienda);
        contentValues.put("id_hogar", id_hogar);
        contentValues.put("id_residente", id_residente);
        contentValues.put("nombre", nombre);
        contentValues.put("parentesco", parentesco);

        return contentValues;
    }
}
