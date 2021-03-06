package pe.gob.inei.generadorinei.greendao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "CARATULA".
 */
@Entity
public class Caratula {

    @Id
    private Long id;
    private String anio;
    private String mes;
    private String periodo;
    private String conglomerado;
    private String nom_dep;
    private String nom_prov;
    private String nom_dist;
    private String nom_ccpp;
    private String zona;
    private String manzana_id;
    private String vivienda;
    private String latitud;
    private String longitud;
    private String tipvia;
    private String tipvia_o;
    private String nomvia;
    private String nropta;
    private String block;
    private String interior;
    private String piso;
    private String mza;
    private String lote;
    private String km;
    private String telefono;
    private String t_hogar;
    private String usuario;
    private String cobertura;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Caratula() {
    }

    public Caratula(Long id) {
        this.id = id;
    }

    @Generated
    public Caratula(Long id, String anio, String mes, String periodo, String conglomerado, String nom_dep, String nom_prov, String nom_dist, String nom_ccpp, String zona, String manzana_id, String vivienda, String latitud, String longitud, String tipvia, String tipvia_o, String nomvia, String nropta, String block, String interior, String piso, String mza, String lote, String km, String telefono, String t_hogar, String usuario, String cobertura) {
        this.id = id;
        this.anio = anio;
        this.mes = mes;
        this.periodo = periodo;
        this.conglomerado = conglomerado;
        this.nom_dep = nom_dep;
        this.nom_prov = nom_prov;
        this.nom_dist = nom_dist;
        this.nom_ccpp = nom_ccpp;
        this.zona = zona;
        this.manzana_id = manzana_id;
        this.vivienda = vivienda;
        this.latitud = latitud;
        this.longitud = longitud;
        this.tipvia = tipvia;
        this.tipvia_o = tipvia_o;
        this.nomvia = nomvia;
        this.nropta = nropta;
        this.block = block;
        this.interior = interior;
        this.piso = piso;
        this.mza = mza;
        this.lote = lote;
        this.km = km;
        this.telefono = telefono;
        this.t_hogar = t_hogar;
        this.usuario = usuario;
        this.cobertura = cobertura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getConglomerado() {
        return conglomerado;
    }

    public void setConglomerado(String conglomerado) {
        this.conglomerado = conglomerado;
    }

    public String getNom_dep() {
        return nom_dep;
    }

    public void setNom_dep(String nom_dep) {
        this.nom_dep = nom_dep;
    }

    public String getNom_prov() {
        return nom_prov;
    }

    public void setNom_prov(String nom_prov) {
        this.nom_prov = nom_prov;
    }

    public String getNom_dist() {
        return nom_dist;
    }

    public void setNom_dist(String nom_dist) {
        this.nom_dist = nom_dist;
    }

    public String getNom_ccpp() {
        return nom_ccpp;
    }

    public void setNom_ccpp(String nom_ccpp) {
        this.nom_ccpp = nom_ccpp;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getManzana_id() {
        return manzana_id;
    }

    public void setManzana_id(String manzana_id) {
        this.manzana_id = manzana_id;
    }

    public String getVivienda() {
        return vivienda;
    }

    public void setVivienda(String vivienda) {
        this.vivienda = vivienda;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getTipvia() {
        return tipvia;
    }

    public void setTipvia(String tipvia) {
        this.tipvia = tipvia;
    }

    public String getTipvia_o() {
        return tipvia_o;
    }

    public void setTipvia_o(String tipvia_o) {
        this.tipvia_o = tipvia_o;
    }

    public String getNomvia() {
        return nomvia;
    }

    public void setNomvia(String nomvia) {
        this.nomvia = nomvia;
    }

    public String getNropta() {
        return nropta;
    }

    public void setNropta(String nropta) {
        this.nropta = nropta;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getMza() {
        return mza;
    }

    public void setMza(String mza) {
        this.mza = mza;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getT_hogar() {
        return t_hogar;
    }

    public void setT_hogar(String t_hogar) {
        this.t_hogar = t_hogar;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
