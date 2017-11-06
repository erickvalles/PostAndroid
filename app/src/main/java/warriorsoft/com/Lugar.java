package warriorsoft.com;

/**
 * Created by Erick on 06/11/2017.
 */

public class Lugar {
    String nombre,foto,descripcion;

    public Lugar(String nombre, String foto, String descripcion) {
        this.nombre = nombre;
        this.foto = foto;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
