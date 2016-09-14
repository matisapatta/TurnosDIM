package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import mobile.mads.turnosdim.ObjectStruct;
import mobile.mads.turnosdim.Paciente;
import mobile.mads.turnosdim.TurnosStruct;
import mobile.mads.turnosdim.UsuariosStruct;

/**
 * Created by mati on 8/18/16.
 */

public class DBManager extends Observable {

    private DataBase b;


    public DBManager(Context context){

        b = new DataBase(context);
    }

    // Sólo utilizado para hacer update de la base de datos por algún cambio
    public void update(){
        SQLiteDatabase db = b.getWritableDatabase();
        b.onUpgrade(db, db.getVersion(), db.getVersion()+1);

    }

    public void guardarUsuario(String dni, String pwd, String nom){
        SQLiteDatabase db = b.getWritableDatabase();
        ContentValues registry = new ContentValues();
        registry.put(DBLayout.DBConstants.USUARIOS_TABLE_DNI, dni);
        registry.put(DBLayout.DBConstants.USUARIOS_TABLE_PWD, pwd);
        registry.put(DBLayout.DBConstants.USUARIOS_TABLE_NOM, nom);
        db.insert(DBLayout.DBConstants.USUARIOS_TABLE, null, registry);
        registry.clear();
        setChanged();
        notifyObservers();
    }

    // Obtener la lista entera de usuarios
    public ArrayList<UsuariosStruct> getUsuarios() {
        ArrayList<UsuariosStruct> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DBLayout.DBConstants.USUARIOS_TABLE;
        SQLiteDatabase db = b.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                UsuariosStruct obj = new UsuariosStruct(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                list.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        b.close();
        return list;
    }

    public UsuariosStruct getUsuarioPorDNI(String dni){
        UsuariosStruct o = null;
        String selectQuery = "SELECT * FROM " + DBLayout.DBConstants.USUARIOS_TABLE + " WHERE " +
                DBLayout.DBConstants.USUARIOS_TABLE_DNI + " = " + dni;
        SQLiteDatabase db = b.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            o = new UsuariosStruct(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }

        cursor.close();
        b.close();
        return o;
    }


    // Buscar un paciente guardado.
    public Paciente getPaciente(Context context){
        Paciente paciente = null;
        String selectQuery = "SELECT * FROM " + DBLayout.DBConstants.USER_TABLE;
        SQLiteDatabase db = b.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            paciente = new Paciente(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                    cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8), cursor.getString(9),
                    cursor.getString(10),cursor.getString(11));
        }
        cursor.close();
        b.close();
        return paciente;
    }


    public TurnosStruct getTurno(String idTurno){
        TurnosStruct turno = null;
        String selectQuery = "SELECT * FROM " + DBLayout.DBConstants.TURNOS_TABLE+ " WHERE "
                + DBLayout.DBConstants.TURNOS_TABLE_IDTURNO + " = " + idTurno;
        SQLiteDatabase db = b.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            turno = new TurnosStruct(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                    cursor.getString(5), cursor.getString(6),cursor.getString(7),cursor.getString(8), cursor.getString(9),
                    cursor.getString(10));
        }
        cursor.close();
        b.close();
        return turno;
    }

    public void newTurno(TurnosStruct turno){
        SQLiteDatabase db = b.getWritableDatabase();
        ContentValues registry = new ContentValues();
        registry.put(DBLayout.DBConstants.TURNOS_TABLE_IDTURNO, turno.getIdTurno());
        registry.put(DBLayout.DBConstants.TURNOS_TABLE_FECHA_TURNO, turno.getFechaTurno());
        registry.put(DBLayout.DBConstants.TURNOS_TABLE_HORA_TURNO, turno.getHoraTurno());
        registry.put(DBLayout.DBConstants.TURNOS_TABLE_MEDICO, turno.getMedico());
        registry.put(DBLayout.DBConstants.TURNOS_TABLE_ESPECIALIDAD, turno.getEspecialidad());
        registry.put(DBLayout.DBConstants.TURNOS_TABLE_CENTRO, turno.getCentro());
        registry.put(DBLayout.DBConstants.TURNOS_TABLE_CONSULTORIO, turno.getConsultorio());
        registry.put(DBLayout.DBConstants.TURNOS_TABLE_COBERTURA, turno.getCobertura());
        registry.put(DBLayout.DBConstants.TURNOS_TABLE_PREPARACION, turno.getPreparacion());
        registry.put(DBLayout.DBConstants.TURNOS_TABLE_ESCONSULTA, turno.getEsConsulta());
        db.insert(DBLayout.DBConstants.TURNOS_TABLE, null, registry);
        registry.clear();
        setChanged();
        notifyObservers();
    }


    // Eliminar una entrada
    public void deleteEntry(int idpaciente){
        SQLiteDatabase db = b.getWritableDatabase();
        db.delete(DBLayout.DBConstants.USER_TABLE, DBLayout.DBConstants.USER_TABLE_IDPACIENTE + "=?",
                new String[]{String.valueOf(idpaciente)});
        setChanged();
        notifyObservers();
    }

    // Agregar una entrada a la BBDD
    public void newEntry ( Paciente paciente){
        SQLiteDatabase db = b.getWritableDatabase();
        ContentValues registry = new ContentValues();
        //registry.put(DBLayout.DBConstants.PASS_TABLE_ID, id);

        registry.put(DBLayout.DBConstants.USER_TABLE_IDPACIENTE, paciente.getIdpaciente());
        registry.put(DBLayout.DBConstants.USER_TABLE_DNI, paciente.getDni());
        registry.put(DBLayout.DBConstants.USER_TABLE_TOKEN, paciente.getTokenPaciente());
        registry.put(DBLayout.DBConstants.USER_TABLE_NOMBRE, paciente.getNombre());
        // Nuevo
        registry.put(DBLayout.DBConstants.USER_TABLE_COBERTURA, paciente.getCobertura());
        registry.put(DBLayout.DBConstants.USER_TABLE_EMAIL, paciente.getEmail());
        registry.put(DBLayout.DBConstants.USER_TABLE_FNAC, paciente.getFnac());
        registry.put(DBLayout.DBConstants.USER_TABLE_SEXO, paciente.getSexo());
        registry.put(DBLayout.DBConstants.USER_TABLE_TEL, paciente.getTel());
        registry.put(DBLayout.DBConstants.USER_TABLE_TELAD, paciente.getTelad());
        registry.put(DBLayout.DBConstants.USER_TABLE_PLAN, paciente.getPlan());
        // Fin nuevo
        db.insert(DBLayout.DBConstants.USER_TABLE, null, registry);
        registry.clear();
        setChanged();
        notifyObservers();
    }

    public void nuevaPractica(ObjectStruct practica, String idTurno){
        SQLiteDatabase db = b.getWritableDatabase();
        ContentValues registry = new ContentValues();
        registry.put(DBLayout.DBConstants.PRACTICAS_TABLE_IDTURNO,idTurno);
        registry.put(DBLayout.DBConstants.PRACTICAS_TABLE_IDNOMENCLADOR,practica.getIdObj());
        registry.put(DBLayout.DBConstants.PRACTICAS_TABLE_DESCRIPCION,practica.getDescripcion());
        db.insert(DBLayout.DBConstants.PRACTICAS_TABLE, null, registry);
        registry.clear();
        setChanged();
        notifyObservers();

    }

    public void updatePaciente(int id, Paciente paciente){
        SQLiteDatabase db = b.getWritableDatabase();
        ContentValues registry = new ContentValues();
        registry.put(DBLayout.DBConstants.USER_TABLE_IDPACIENTE, paciente.getIdpaciente());
        registry.put(DBLayout.DBConstants.USER_TABLE_DNI, paciente.getDni());
        registry.put(DBLayout.DBConstants.USER_TABLE_TOKEN, paciente.getTokenPaciente());
        registry.put(DBLayout.DBConstants.USER_TABLE_NOMBRE, paciente.getNombre());
        // Nuevo
        registry.put(DBLayout.DBConstants.USER_TABLE_COBERTURA, paciente.getCobertura());
        registry.put(DBLayout.DBConstants.USER_TABLE_EMAIL, paciente.getEmail());
        registry.put(DBLayout.DBConstants.USER_TABLE_FNAC, paciente.getFnac());
        registry.put(DBLayout.DBConstants.USER_TABLE_SEXO, paciente.getSexo());
        registry.put(DBLayout.DBConstants.USER_TABLE_TEL, paciente.getTel());
        registry.put(DBLayout.DBConstants.USER_TABLE_TELAD, paciente.getTelad());
        registry.put(DBLayout.DBConstants.USER_TABLE_PLAN, paciente.getPlan());
        // Fin nuevo
        String where = DBLayout.DBConstants.USER_TABLE_ID + "=?";
        db.update(DBLayout.DBConstants.USER_TABLE,registry, where, new String[]{String.valueOf(id)});
        registry.clear();
        setChanged();
        notifyObservers();

    }

    // Actualizar una entrada existente, usada para editar.
    public void updateEntry (int id, Paciente paciente){
        SQLiteDatabase db = b.getWritableDatabase();
        ContentValues registry = new ContentValues();
        registry.put(DBLayout.DBConstants.USER_TABLE_ID, paciente.getId());
        registry.put(DBLayout.DBConstants.USER_TABLE_IDPACIENTE, paciente.getIdpaciente());
        registry.put(DBLayout.DBConstants.USER_TABLE_DNI, paciente.getDni());
        registry.put(DBLayout.DBConstants.USER_TABLE_TOKEN, paciente.getTokenPaciente());
        registry.put(DBLayout.DBConstants.USER_TABLE_NOMBRE, paciente.getNombre());
        String where = DBLayout.DBConstants.USER_TABLE_ID + "=?";
        db.update(DBLayout.DBConstants.USER_TABLE, registry, where, new String[]{String.valueOf(id)});
        registry.clear();
        setChanged();
        notifyObservers();
    }

    // Método utilizado para borrar todas las entradas de la tabla
    public void deleteAll(){
        SQLiteDatabase db = b.getWritableDatabase();
        db.delete(DBLayout.DBConstants.USER_TABLE, null, null);
        db.delete(DBLayout.DBConstants.TURNOS_TABLE, null, null);
        db.delete(DBLayout.DBConstants.PRACTICAS_TABLE,null,null);
        setChanged();
        notifyObservers();
    }

    public void deleteTurnos(){

        SQLiteDatabase db = b.getWritableDatabase();
        db.delete(DBLayout.DBConstants.TURNOS_TABLE, null, null);
        setChanged();
        notifyObservers();

    }

    public void deletePractias(){

        SQLiteDatabase db = b.getWritableDatabase();
        db.delete(DBLayout.DBConstants.PRACTICAS_TABLE, null, null);
        setChanged();
        notifyObservers();

    }



    public void close(){
        b.close();
    }
}
