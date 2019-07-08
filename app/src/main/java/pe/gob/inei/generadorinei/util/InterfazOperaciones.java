package pe.gob.inei.generadorinei.util;

/**
 * Created by dmorales on 26/02/2018.
 */

public interface InterfazOperaciones {
    public void guardarDatos();
    public void llenarVariables();
    public void cargarDatos();
    public void llenarVista();
    public boolean validarDatos();
    public String getNombreTabla();
}
