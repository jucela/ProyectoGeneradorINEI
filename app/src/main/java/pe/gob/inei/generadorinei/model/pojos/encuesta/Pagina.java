package pe.gob.inei.generadorinei.model.pojos.encuesta;

public class Pagina {
    private String _id;
    private String numero;
    private String modulo;
    private String tipo_actividad;
    private String nombre;
    private String tipo_pagina;
    private int tipo_guardado;
    private String tabla_guardado;

    public Pagina(String _id, String numero, String modulo, String tipo_actividad, String nombre, String tipo_pagina) {
        this._id = _id;
        this.numero = numero;
        this.modulo = modulo;
        this.tipo_actividad = tipo_actividad;
        this.nombre = nombre;
        this.tipo_pagina = tipo_pagina;
    }

    public Pagina() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getTipo_actividad() {
        return tipo_actividad;
    }

    public void setTipo_actividad(String tipo_actividad) {
        this.tipo_actividad = tipo_actividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo_pagina() {
        return tipo_pagina;
    }

    public void setTipo_pagina(String tipo_pagina) {
        this.tipo_pagina = tipo_pagina;
    }

    public int getTipo_guardado() {
        return tipo_guardado;
    }

    public void setTipo_guardado(int tipo_guardado) {
        this.tipo_guardado = tipo_guardado;
    }

    public String getTabla_guardado() {
        return tabla_guardado;
    }

    public void setTabla_guardado(String tabla_guardado) {
        this.tabla_guardado = tabla_guardado;
    }
}
