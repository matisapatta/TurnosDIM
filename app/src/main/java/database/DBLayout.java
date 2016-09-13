package database;

import android.provider.BaseColumns;

/**
 * Created by mati on 8/18/16.
 */

public class DBLayout {

    public DBLayout() {
    }

    public static abstract class DBConstants implements BaseColumns {
        static final String DB = "TurnosDIM";

        static final String USER_TABLE = "Usuarios";

        static final String USER_TABLE_ID = "_id";
        static final String USER_TABLE_IDPACIENTE = "idpaciente";
        static final String USER_TABLE_TOKEN = "TokenPaciente";
        static final String USER_TABLE_DNI = "dni";
        static final String USER_TABLE_NOMBRE = "nombre";
        static final String USER_TABLE_COBERTURA = "cobertura";
        static final String USER_TABLE_EMAIL = "email";
        static final String USER_TABLE_FNAC = "fnac";
        static final String USER_TABLE_SEXO = "sexo";
        static final String USER_TABLE_TEL = "telefono";
        static final String USER_TABLE_TELAD = "telefonoad";
        static final String USER_TABLE_PLAN = "plan";



        static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE
                + " (" + USER_TABLE_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_TABLE_IDPACIENTE + "  VARCHAR(10), "
                + USER_TABLE_DNI + " VARCHAR(8), "
                + USER_TABLE_NOMBRE + " VARCHAR(40), "
                + USER_TABLE_TOKEN + " VARCHAR(40), "
                + USER_TABLE_COBERTURA + " VARCHAR(40), "
                + USER_TABLE_EMAIL + " VARCHAR(40), "
                + USER_TABLE_FNAC + " VARCHAR(8), "
                + USER_TABLE_SEXO + " VARCHAR(2), "
                + USER_TABLE_TEL + " VARCHAR(20), "
                + USER_TABLE_TELAD + " VARCHAR(20), "
                + USER_TABLE_PLAN + " VARCHAR(40)" + ")";

        static final int CURRENT_VERSION = 4;

        static final String TURNOS_TABLE = "Turnos";

        static final String TURNOS_TABLE_ID = "_id";
        static final String TURNOS_TABLE_IDTURNO = "idTurno";
        static final String TURNOS_TABLE_FECHA_TURNO = "FechaTurno";
        static final String TURNOS_TABLE_HORA_TURNO = "HoraTurno";
        static final String TURNOS_TABLE_MEDICO = "Medico";
        static final String TURNOS_TABLE_ESPECIALIDAD = "Especialidad";
        static final String TURNOS_TABLE_CENTRO = "Centro";
        static final String TURNOS_TABLE_CONSULTORIO = "Consultorio";
        static final String TURNOS_TABLE_COBERTURA = "Cobertura";
        static final String TURNOS_TABLE_PREPARACION = "Preparacion";
        static final String TURNOS_TABLE_ESCONSULTA = "esConsulta";

        static final String CREATE_TURNOS_TABLE = "CREATE TABLE IF NOT EXISTS " + TURNOS_TABLE
                + " (" + TURNOS_TABLE_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TURNOS_TABLE_IDTURNO + "  VARCHAR(10), "
                + TURNOS_TABLE_FECHA_TURNO + " VARCHAR(10), "
                + TURNOS_TABLE_HORA_TURNO + " VARCHAR(5), "
                + TURNOS_TABLE_MEDICO + " VARCHAR(40), "
                + TURNOS_TABLE_ESPECIALIDAD + " VARCHAR(40), "
                + TURNOS_TABLE_CENTRO + " VARCHAR(40), "
                + TURNOS_TABLE_CONSULTORIO + " VARCHAR(10), "
                + TURNOS_TABLE_COBERTURA + " VARCHAR(40), "
                + TURNOS_TABLE_PREPARACION + " VARCHAR(100), "
                + TURNOS_TABLE_ESCONSULTA + " VARCHAR(5)" + ")";

        static final String PRACTICAS_TABLE = "Practicas";

        static final String PRACTICAS_TABLE_ID = "_id";
        static final String PRACTICAS_TABLE_IDTURNO = "idTurno";
        static final String PRACTICAS_TABLE_IDNOMENCLADOR = "idNomenclador";
        static final String PRACTICAS_TABLE_DESCRIPCION = "Descripcion";

        static final String CREATE_PRACTICAS_TABLE = "CREATE TABLE IF NOT EXISTS " + PRACTICAS_TABLE
                + " (" + PRACTICAS_TABLE_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRACTICAS_TABLE_IDTURNO + "  VARCHAR(10), "
                + PRACTICAS_TABLE_IDNOMENCLADOR + " VARCHAR(10), "
                + PRACTICAS_TABLE_DESCRIPCION + " VARCHAR(40)" + ")";

        static final String USUARIOS_TABLE = "Paciente";

        static final String USUARIOS_TABLE_ID = "_id";
        static final String USUARIOS_TABLE_DNI = "dni";
        static final String USUARIOS_TABLE_PWD = "pwd";
        static final String USUARIOS_TABLE_NOM = "nombre";

        static final String CREATE_USUARIOS_TABLE = "CREATE TABLE IF NOT EXISTS " + USUARIOS_TABLE
                + " (" + USUARIOS_TABLE_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USUARIOS_TABLE_DNI + "  VARCHAR(10), "
                + USUARIOS_TABLE_PWD + " VARCHAR(30), "
                + USUARIOS_TABLE_NOM + " VARCHAR(40)" + ")";



    }
}
