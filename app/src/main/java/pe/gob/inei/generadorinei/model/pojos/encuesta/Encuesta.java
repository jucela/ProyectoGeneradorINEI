package pe.gob.inei.generadorinei.model.pojos.encuesta;

public class Encuesta {
    private String _id;
    private String titulo;
    private String tipo;


    public String get_id() {
        return _id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
