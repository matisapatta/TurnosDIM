package mobile.mads.turnosdim;

import android.provider.BaseColumns;

/**
 * Created by mati on 8/17/16.
 */

public class WSConstants {

    public WSConstants(){

    }
    public static abstract class StringConstants implements BaseColumns {

        public static final String WS_URL = "http://portalweb.dim.com.ar:8091/global.ashx?";
        public static final String WS_COMANDO_LOGIN = "comando=demo_login";
        public static final String WS_COMANDO_MISTURNOS = "comando=demo_mis_turnos";
        public static final String WS_COMANDO_GETESPECIALIDADES = "comando=demo_Get_especialidades";
        public static final String WS_COMANDO_GETMEDICOXESPECIALIDAD = "comando=demo_GetEspMedicosXidEspecialidad";
        public static final String WS_COMANDO_GETTURNOSCONSULTAS = "comando=demo_GetTurnosDisponibleConsultas";
        public static final String WS_COMANDO_MISDATOS = "comando=demo_mis_datos";
        public static final String WS_COMANDO_DARCONSULTA = "comando=demo_DarTurnoConsulta";
        public static final String WS_COMANDO_CANCELARTURNOMOTIVOS = "comando=demo_CancelarTurnoMotivos";
        public static final String WS_DNI = "&dni=";
        public static final String WS_TOKEN = "&token=";
        public static final String WS_PASS = "&pass=";
        public static final String WS_FORMATO = "&formato=indented";
        public static final String WS_ID_PACIENTE = "&idpaciente=";
        public static final String WS_TELEFONO = "&telefono=";
        public static final String WS_SEXO = "&sexo=";
        public static final String WS_FECHA_NAC = "&fechanacimiento=";
        public static final String WS_EMAIL = "&email=";
        public static final String WS_ESPECIALIDAD = "&especialidad=";
        public static final String WS_IDESPECIALIDAD = "&idespecialidad=";
        public static final String WS_IDMEDICO = "&idmedico=";
        public static final String WS_IDOBRASOCIAL = "&idobrasocial=";
        public static final String WS_IDPLAN = "&idplan=";



    }

}
