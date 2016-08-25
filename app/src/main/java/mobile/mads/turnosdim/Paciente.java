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
    private String cobertura;
    private String email;
    private String fnac;
    private String sexo;
    private String tel;
    private String telad;
    private String plan;

    public Paciente(){
        this.id = 0;
        this.idpaciente = "";
        this.TokenPaciente = "";
        this.nombre = "";
        this.dni = "";
        this.cobertura= "";
        this.email = "";
        this.fnac = "";
        this.sexo = "";
        this.tel = "";
        this.telad = "";
        this.plan = "";

    }

    public Paciente(int id, String idpaciente, String dni, String nombre, String token){

        this.id = id;
        this.idpaciente = idpaciente;
        this.TokenPaciente = token;
        this.nombre = nombre;
        this.dni = dni;
    }

    public Paciente(int id, String idpaciente, String dni, String nombre, String token, String cobertura,
                    String email, String fnac, String sexo, String tel, String telad, String plan){

        this.id = id;
        this.idpaciente = idpaciente;
        this.TokenPaciente = token;
        this.nombre = nombre;
        this.dni = dni;
        this.cobertura = cobertura;
        this.email = email;
        this.fnac = fnac;
        this.sexo = sexo;
        this.tel = tel;
        this.telad = telad;
        this.plan = plan;
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

    public String getCobertura() {
        return cobertura;
    }

    public String getEmail() {
        return email;
    }

    public String getFnac() {
        return fnac;
    }

    public String getSexo() {
        return sexo;
    }

    public String getTel() {
        return tel;
    }

    public String getTelad() {
        return telad;
    }

    public String getPlan() {
        return plan;
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

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setTelad(String telad) {
        this.telad = telad;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
