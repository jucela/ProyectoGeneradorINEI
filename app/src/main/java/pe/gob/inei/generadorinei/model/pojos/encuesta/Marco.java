package pe.gob.inei.generadorinei.model.pojos.encuesta;

import android.content.ContentValues;

public class Marco {
    private String _id;
    private String anio;
    private String mes;
    private String periodo;
    private String zona;
    private String conglomerado;
    private String ccdd;
    private String departamento;
    private String ccpp;
    private String provincia;
    private String ccdi;
    private String distrito;
    private String codccpp;
    private String nomccpp;
    private String ruc;
    private String razon_social;
    private String nombre_comercial;
    private String tipo_empresa;
    private String encuestador;
    private String supervisor;
    private String coordinador;
    private String frente;
    private String numero_orden;
    private String manzana_id;
    private String tipo_via;
    private String nombre_via;
    private String puerta;
    private String lote;
    private String piso;
    private String manzana;
    private String block;
    private String interior;
    private String estado;

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

    public String getConglomerado() {
        return conglomerado;
    }

    public void setConglomerado(String conglomerado) {
        this.conglomerado = conglomerado;
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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getNombre_comercial() {
        return nombre_comercial;
    }

    public void setNombre_comercial(String nombre_comercial) {
        this.nombre_comercial = nombre_comercial;
    }

    public String getTipo_empresa() {
        return tipo_empresa;
    }

    public void setTipo_empresa(String tipo_empresa) {
        this.tipo_empresa = tipo_empresa;
    }

    public String getEncuestador() {
        return encuestador;
    }

    public void setEncuestador(String encuestador) {
        this.encuestador = encuestador;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(String coordinador) {
        this.coordinador = coordinador;
    }

    public String getFrente() {
        return frente;
    }

    public void setFrente(String frente) {
        this.frente = frente;
    }

    public String getNumero_orden() {
        return numero_orden;
    }

    public void setNumero_orden(String numero_orden) {
        this.numero_orden = numero_orden;
    }

    public String getManzana_id() {
        return manzana_id;
    }

    public void setManzana_id(String manzana_id) {
        this.manzana_id = manzana_id;
    }

    public String getTipo_via() {
        return tipo_via;
    }

    public void setTipo_via(String tipo_via) {
        this.tipo_via = tipo_via;
    }

    public String getNombre_via() {
        return nombre_via;
    }

    public void setNombre_via(String nombre_via) {
        this.nombre_via = nombre_via;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
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

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", _id);
        contentValues.put("anio", anio);
        contentValues.put("mes", mes);
        contentValues.put("estado", estado);
        contentValues.put("periodo", periodo);
        contentValues.put("zona", zona);
        contentValues.put("conglomerado", conglomerado);
        contentValues.put("ccdd", ccdd);
        contentValues.put("departamento", departamento);
        contentValues.put("ccpp", ccpp);
        contentValues.put("provincia", provincia);
        contentValues.put("ccdi", ccdi);
        contentValues.put("distrito", distrito);
        contentValues.put("codccpp", codccpp);
        contentValues.put("nomccpp", nomccpp);
        contentValues.put("ruc", ruc);
        contentValues.put("razon_social", razon_social);
        contentValues.put("nombre_comercial", nombre_comercial);
        contentValues.put("tipo_empresa", tipo_empresa);
        contentValues.put("encuestador", encuestador);
        contentValues.put("supervisor", supervisor);
        contentValues.put("coordinador", coordinador);
        contentValues.put("frente", frente);
        contentValues.put("numero_orden", numero_orden);
        contentValues.put("manzana_id", manzana_id);
        contentValues.put("tipo_via", tipo_via);
        contentValues.put("nombre_via", nombre_via);
        contentValues.put("puerta", puerta);
        contentValues.put("lote", lote);
        contentValues.put("piso", piso);
        contentValues.put("manzana", manzana);
        contentValues.put("block", block);
        contentValues.put("interior", interior);
        contentValues.put("estado", estado);

        return contentValues;
    }
}
