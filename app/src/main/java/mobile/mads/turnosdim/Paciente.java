package mobile.mads.turnosdim;

/**
 * Created by mati on 8/17/16.
 */

public class Paciente {

    private String idpaciente;
    private String TokenPaciente;
    private String nombre;

    public void Paciente(String idpaciente, String token, String nombre){

        this.idpaciente = idpaciente;
        this.TokenPaciente = token;
        this.nombre = nombre;
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


    public void setIdpaciente(String idpaciente) {
        this.idpaciente = idpaciente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTokenPaciente(String TokenPaciente) {
        this.TokenPaciente = TokenPaciente;
    }
}
