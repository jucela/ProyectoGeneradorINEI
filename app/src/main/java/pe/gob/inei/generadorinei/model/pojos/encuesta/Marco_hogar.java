package pe.gob.inei.generadorinei.model.pojos.encuesta;

import android.content.ContentValues;

import pe.gob.inei.generadorinei.model.dao.SQLConstantes;

public class Marco_hogar {
    private String _id;
    private String anio;
    private String mes;
    private String periodo;
    private String zona;
    private String ccdd;
    private String departamento;
    private String ccpp;
    private String provincia;
    private String ccdi;
    private String distrito;
    private String codccpp;
    private String nomccpp;
    private String conglomerado;
    private String norden;
    private String manzana_id;
    private String tipvia;
    private String nomvia;
    private String nropta;
    private String lote;
    private String piso;
    private String mza;
    private String block;
    private String interior;
    private String usuario_id;
    private String usuario;
    private String nombre;
    private String dni;
    private String usuario_sup_id;
    private String estado;


    public Marco_hogar() {
        _id="";
        anio="";
        mes="";
        periodo="";
        zona="";
        ccdd="";
        departamento="";
        ccpp="";
        provincia="";
        ccdi="";
        distrito="";
        codccpp="";
        nomccpp="";
        conglomerado="";
        norden="";
        manzana_id="";
        tipvia="";
        nomvia="";
        nropta="";
        lote="";
        piso="";
        mza="";
        block="";
        interior="";
        usuario_id="";
        usuario="";
        nombre="";
        dni="";
        usuario_sup_id="";
        estado = "0";
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getCcdd() {
        return ccdd;
    }

    public void setCcdd(String ccdd) {
        this.ccdd = ccdd;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCcpp() {
        return ccpp;
    }

    public void setCcpp(String ccpp) {
        this.ccpp = ccpp;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCcdi() {
        return ccdi;
    }

    public void setCcdi(String ccdi) {
        this.ccdi = ccdi;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCodccpp() {
        return codccpp;
    }

    public void setCodccpp(String codccpp) {
        this.codccpp = codccpp;
    }

    public String getNomccpp() {
        return nomccpp;
    }

    public void setNomccpp(String nomccpp) {
        this.nomccpp = nomccpp;
    }

    public String getConglomerado() {
        return conglomerado;
    }

    public void setConglomerado(String conglomerado) {
        this.conglomerado = conglomerado;
    }

    public String getNorden() {
        return norden;
    }

    public void setNorden(String norden) {
        this.norden = norden;
    }

    public String getManzana_id() {
        return manzana_id;
    }

    public void setManzana_id(String manzana_id) {
        this.manzana_id = manzana_id;
    }

    public String getTipvia() {
        return tipvia;
    }

    public void setTipvia(String tipvia) {
        this.tipvia = tipvia;
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

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
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

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getUsuario_sup_id() {
        return usuario_sup_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setUsuario_sup_id(String usuario_sup_id) {
        this.usuario_sup_id = usuario_sup_id;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.marco_id,_id);
        contentValues.put(SQLConstantes.marco_anio,anio);
        contentValues.put(SQLConstantes.marco_mes,mes);
        contentValues.put(SQLConstantes.marco_periodo,periodo);
        contentValues.put(SQLConstantes.marco_zona,zona);
        contentValues.put(SQLConstantes.marco_ccdd,ccdd);
        contentValues.put(SQLConstantes.marco_departamento,departamento);
        contentValues.put(SQLConstantes.marco_ccpp,ccpp);
        contentValues.put(SQLConstantes.marco_provincia,provincia);
        contentValues.put(SQLConstantes.marco_ccdi,ccdi);
        contentValues.put(SQLConstantes.marco_distrito,distrito);
        contentValues.put(SQLConstantes.marco_codccpp,codccpp);
        contentValues.put(SQLConstantes.marco_nomccpp,nomccpp);
        contentValues.put(SQLConstantes.marco_conglomerado,conglomerado);
        contentValues.put(SQLConstantes.marco_norden,norden);
        contentValues.put(SQLConstantes.marco_manzana_id,manzana_id);
        contentValues.put(SQLConstantes.marco_tipvia,tipvia);
        contentValues.put(SQLConstantes.marco_nomvia,nomvia);
        contentValues.put(SQLConstantes.marco_nropta,nropta);
        contentValues.put(SQLConstantes.marco_lote,lote);
        contentValues.put(SQLConstantes.marco_piso,piso);
        contentValues.put(SQLConstantes.marco_mza,mza);
        contentValues.put(SQLConstantes.marco_block,block);
        contentValues.put(SQLConstantes.marco_interior,interior);
        contentValues.put(SQLConstantes.marco_usuario_id,usuario_id);
        contentValues.put(SQLConstantes.marco_usuario_sup_id,usuario_sup_id);
        contentValues.put(SQLConstantes.marco_usuario,usuario);
        contentValues.put(SQLConstantes.marco_nombre,nombre);
        contentValues.put(SQLConstantes.marco_dni,dni);
        contentValues.put(SQLConstantes.marco_estado,estado);
        return contentValues;
    }
}
