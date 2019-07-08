package pe.gob.inei.generadorinei.model.pojos.encuesta;

public class FiltrosMarco {
    private String _id;
    private String filtro1;
    private String filtro2;
    private String filtro3;
    private String filtro4;
    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String nombre4;


    public FiltrosMarco() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getNombre3() {
        return nombre3;
    }

    public void setNombre3(String nombre3) {
        this.nombre3 = nombre3;
    }

    public String getNombre4() {
        return nombre4;
    }

    public void setNombre4(String nombre4) {
        this.nombre4 = nombre4;
    }

    public String getFiltro1() {
        return filtro1;
    }

    public void setFiltro1(String filtro1) {
        this.filtro1 = filtro1;
    }

    public String getFiltro2() {
        return filtro2;
    }

    public void setFiltro2(String filtro2) {
        this.filtro2 = filtro2;
    }

    public String getFiltro3() {
        return filtro3;
    }

    public void setFiltro3(String filtro3) {
        this.filtro3 = filtro3;
    }

    public String getFiltro4() {
        return filtro4;
    }

    public void setFiltro4(String filtro4) {
        this.filtro4 = filtro4;
    }
}
