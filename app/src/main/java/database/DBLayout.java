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
        static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE
                + " (" + USER_TABLE_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_TABLE_IDPACIENTE + "  VARCHAR(10), "
                + USER_TABLE_DNI + " VARCHAR(8), "
                + USER_TABLE_NOMBRE + " VARCHAR(40), "
                + USER_TABLE_TOKEN + " VARCHAR(40)" + ")";


        static final int CURRENT_VERSION = 1;


    }
}
