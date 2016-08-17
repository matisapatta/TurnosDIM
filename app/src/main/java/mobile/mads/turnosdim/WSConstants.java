package mobile.mads.turnosdim;

import android.provider.BaseColumns;

/**
 * Created by mati on 8/17/16.
 */

public class WSConstants {

    public WSConstants(){

    }
    public static abstract class StringConstants implements BaseColumns {

        public static final String wsUrl = "http://portalweb.dim.com.ar:8091/global.ashx?";
        public static final String wsComandoLogin = "comando=demo_login";
        public static final String wsDni = "&dni=";
        public static final String wsToken = "&token=";
        public static final String wsPass = "&pass=";
        public static final String wsFormato = "&formato=indented";
        public static final String wsIdPaciente = "&idpaciente=";
        public static final String wsTelefono = "&telefono=";
        public static final String wsSexo = "&sexo=";
        public static final String wsFechaNac = "&fechanacimiento=";
        public static final String wsEmail = "&email=";
        public static final String wsEspecialidad = "&especialidad";
    }

}
