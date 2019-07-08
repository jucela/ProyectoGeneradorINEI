package pe.gob.inei.generadorinei.model.pojos.encuesta;

public class Flujo {
    private int orden_flujo_ini;
    private String nombre_flujo;
    private int numero_var;
    private String go_to;
    private int numero_go_to;
    private int numero_go_to_max;

    public int getOrden_flujo_ini() {
        return orden_flujo_ini;
    }

    public void setOrden_flujo_ini(int orden_flujo_ini) {
        this.orden_flujo_ini = orden_flujo_ini;
    }

    public String getNombre_flujo() {
        return nombre_flujo;
    }

    public void setNombre_flujo(String nombre_flujo) {
        this.nombre_flujo = nombre_flujo;
    }

    public int getNumero_var() {
        return numero_var;
    }

    public void setNumero_var(int numero_var) {
        this.numero_var = numero_var;
    }

    public String getGo_to() {
        return go_to;
    }

    public void setGo_to(String go_to) {
        this.go_to = go_to;
    }

    public int getNumero_go_to() {
        return numero_go_to;
    }

    public void setNumero_go_to(int numero_go_to) {
        this.numero_go_to = numero_go_to;
    }

    public int getNumero_go_to_max() {
        return numero_go_to_max;
    }

    public void setNumero_go_to_max(int numero_go_to_max) {
        this.numero_go_to_max = numero_go_to_max;
    }
}
