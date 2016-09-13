package mobile.mads.turnosdim;

/**
 * Created by mati on 9/13/16.
 */

public class UsuariosStruct {

    private int id;
    private String dni;
    private String pwd;
    private String nom;

    public UsuariosStruct(){

    }

    public UsuariosStruct(int id, String dni, String pwd, String nom){
        this.id = id;
        this.dni = dni;
        this.pwd = pwd;
        this.nom = nom;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getPwd() {
        return pwd;
    }

    public String getNom() {
        return nom;
    }
}
