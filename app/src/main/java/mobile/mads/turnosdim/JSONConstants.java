package mobile.mads.turnosdim;

import android.provider.BaseColumns;


/**
 * Created by mati on 8/18/16.
 */

public final class JSONConstants implements BaseColumns {
    public static final String JSON_SUCCESS = "success";
    public static final String JSON_MENSAJE = "mensaje";
    public static final String JSON_IDPACIENTE = "idpaciente";
    public static final String JSON_NOMBRE = "nombre";
    public static final String JSON_TOKEN = "TokenPaciente";
    public static final String JSON_EMAIL = "email";
    public static final String JSON_REGISTRADO = "registrado";
    public static final String JSON_DNI = "idpaciente";
    public static final String JSON_COBERTURA = "Cobertura";
    public static final String JSON_FECHA_NAC = "fnac";
    public static final String JSON_TEL = "telefono";
    public static final String JSON_TEL_AD = "telefonoad";
    public static final String JSON_PLAN = "plan";
    public static final String JSON_SEXO = "idpaciente";
    public static final String JSON_ARRAY_TURNOS = "Turnos";
    public static final String JSON_ID_TURNO = "idTurno";
    public static final String JSON_FECHA_TURNO = "FechaTurno";
    public static final String JSON_HORA_TURNO = "HoraTurno";
    public static final String JSON_MEDICO = "Medico";
    public static final String JSON_ESPECIALIDAD = "Especialidad";
    public static final String JSON_CENTRO = "Centro";
    public static final String JSON_CONSULTORIO = "Consultorio";
    public static final String JSON_PREPARACION = "Preparacion";
    public static final String JSON_ESCONSULTA = "EsConsulta";
    public static final String JSON_ARRAY_PRACTICAS = "Practicas";

    // Campos de la práctica
    public static final String JSON_IDNOMENCLADOR = "idNomenclador";
    public static final String JSON_DESCRIPCION = "Descripcion";

    // Array especialidades y su campo
    public static final String JSON_ARRAY_ESPECIALIDADES = "Especialidades";
    public static final String JSON_ID = "id";
    public static final String JSON_DESCRIPCION_ESPECIALIDAD = "descripcion";

    // Array medicos x especialidad y sus campos (descripcion y ID igual que el anterior)
    public static final String JSON_ITEMS = "items";

    // Array para estudios, usa JSON_ITEMS, JSON_ID y JSON_DESCRIPCION
    // Array motivos de cancelación, usa JSON_ITEMS, JSON_ID y JSON_DESCRIPCION
    // Array de obras sociales activas, lo mismo





}
