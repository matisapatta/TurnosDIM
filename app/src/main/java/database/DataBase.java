package database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mati on 8/18/16.
 */

public class DataBase extends SQLiteOpenHelper {
    public DataBase(Context context) {
        super(context, DBLayout.DBConstants.DB, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        createBDBase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + DBLayout.DBConstants.USER_TABLE);
        createBDBase(db);
    }

    private void createBDBase (SQLiteDatabase db) {
        //Crea las tablas
        db.execSQL(DBLayout.DBConstants.CREATE_USER_TABLE);
        db.execSQL(DBLayout.DBConstants.CREATE_TURNOS_TABLE);
        db.execSQL(DBLayout.DBConstants.CREATE_PRACTICAS_TABLE);

    }
}
