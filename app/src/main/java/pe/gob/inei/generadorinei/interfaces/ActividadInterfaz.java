package pe.gob.inei.generadorinei.interfaces;

/**
 * Created by dmorales on 26/02/2018.
 */

public interface ActividadInterfaz {
    public void realizarEvento(String variable, String valor, boolean cargandoDatos);
    public boolean existeEvento(String variable);
}
