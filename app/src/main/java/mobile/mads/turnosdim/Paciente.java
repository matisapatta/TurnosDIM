package mobile.mads.turnosdim;

/**
 * Created by mati on 8/17/16.
 */

public class Paciente {

    private int id;
    private String idpaciente;
    private String TokenPaciente;
    private String nombre;
    private String dni;

    public Paciente(){
        this.id = 0;
        this.idpaciente = "";
        this.TokenPaciente = "";
        this.nombre = "";
        this.dni = "";

    }

    public Paciente(int id, String idpaciente, String token, String nombre, String dni){

        this.id = id;
        this.idpaciente = idpaciente;
        this.TokenPaciente = token;
        this.nombre = nombre;
        this.dni = dni;
    }

    public int getId() {
        return id;
    }

    public String getIdpaciente(){
        return idpaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTokenPaciente() {
        return TokenPaciente;
    }

    public String getDni() {
        return dni;
    }

    public void setIdpaciente(String idpaciente) {
        this.idpaciente = idpaciente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTokenPaciente(String TokenPaciente) {
        this.TokenPaciente = TokenPaciente;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setId(int id) {
        this.id = id;
    }
}
