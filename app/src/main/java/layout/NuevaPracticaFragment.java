package layout;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import database.DBManager;
import mobile.mads.turnosdim.CustomSpinnerAdapter;
import mobile.mads.turnosdim.JSONConstants;
import mobile.mads.turnosdim.NuevaConsultaAdapter;
import mobile.mads.turnosdim.ObjectStruct;
import mobile.mads.turnosdim.Paciente;
import mobile.mads.turnosdim.R;
import mobile.mads.turnosdim.ServiceHandler;
import mobile.mads.turnosdim.TurnosStruct;
import mobile.mads.turnosdim.WSConstants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NuevaPracticaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NuevaPracticaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevaPracticaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnBuscar;
    private EditText txtPractica;
    private RecyclerView recView;
    private ObjectStruct practica;
    private ArrayList<ObjectStruct> dataPractica;
    private ArrayList<String> arrayPractica;
    private ArrayList<TurnosStruct> datosTurno;
    private TurnosStruct turnos;
    private Paciente paciente;
    private DBManager db;
    private String success;
    private String url;
    private String idmed;
    private Spinner spinnerPractica;
    private CustomSpinnerAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public NuevaPracticaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevaPracticaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NuevaPracticaFragment newInstance(String param1, String param2) {
        NuevaPracticaFragment fragment = new NuevaPracticaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nueva_practica, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        spinnerPractica = (Spinner)getActivity().findViewById(R.id.spinnerPractica);
        btnBuscar = (Button)getActivity().findViewById(R.id.btnBuscarPractica);
        txtPractica = (EditText)getActivity().findViewById(R.id.textPractica);
        recView = (RecyclerView)getActivity().findViewById(R.id.nuevaPracticaRecView);
        db = new DBManager(getContext());
        paciente = db.getPaciente(getContext());
        url = WSConstants.StringConstants.WS_URL+ WSConstants.StringConstants.WS_COMANDO_GET_ESTUDIOSMEDICOS+ WSConstants.StringConstants.WS_ID_PACIENTE+
                paciente.getIdpaciente()+ WSConstants.StringConstants.WS_TOKEN+paciente.getTokenPaciente()+ WSConstants.StringConstants.WS_FILTRO;
        new HttpRequestTaskPractica().execute(url);

        spinnerPractica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idmed = dataPractica.get(position).getIdObj();
                recView.setAdapter(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtPractica.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recView.setAdapter(null);
                ObjectStruct prac = dataPractica.get(spinnerPractica.getSelectedItemPosition());


                String param = WSConstants.StringConstants.WS_URL+WSConstants.StringConstants.WS_COMANDO_GET_TURNOSPRACTICA+
                        WSConstants.StringConstants.WS_ID_PACIENTE+paciente.getIdpaciente()+ WSConstants.StringConstants.WS_TOKEN+
                        paciente.getTokenPaciente()+WSConstants.StringConstants.WS_IDGRUPOMEDICO+prac.getIdObj()+
                        WSConstants.StringConstants.WS_IDOBRASOCIAL+"PRUEBA"+ WSConstants.StringConstants.WS_FORMATO;
                new HttpRequestTaskTurnos().execute(param);
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class HttpRequestTaskTurnos extends AsyncTask<String , Void, String> {
        //Before running code in separate thread
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getContext().getResources().getString(R.string.loading));
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                ServiceHandler sh = new ServiceHandler();

                // Make WS Call
                String jsonData = sh.doGetRequest(params[0]);

                if(jsonData!=null){
                    try {
                        // Manejo de Array en JSON

                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONObject json_data;
                        datosTurno = new ArrayList<TurnosStruct>();

                        success = jsonObject.getString(JSONConstants.JSON_SUCCESS);
                        if(success.equals("1")){
                            JSONArray jArray = jsonObject.getJSONArray("Turnos");
                            for(int i=0;i<jArray.length();i++){
                                json_data = jArray.getJSONObject(i);
                                turnos = new TurnosStruct();

                                turnos.setIdTurno(json_data.getString(JSONConstants.JSON_ID_TURNO));
                                turnos.setHoraTurno(json_data.getString(JSONConstants.JSON_HORA_TURNO));
                                turnos.setCentro(json_data.getString(JSONConstants.JSON_CENTRO));
                                turnos.setConsultorio(json_data.getString(JSONConstants.JSON_CONSULTORIO));
                                turnos.setCobertura(json_data.getString(JSONConstants.JSON_COBERTURA));
                                turnos.setPreparacion(json_data.getString(JSONConstants.JSON_PREPARACION));
                                //if(json_data.getString(JSONConstants.JSON_ESCONSULTA.toLowerCase()).equals("true")) {
                                turnos.setEsConsulta(true);
                                //} else {
                                //    turnos.setEsConsulta(false);
                                //}
                                // Estos son para la lista
                                turnos.setMedico(json_data.getString(JSONConstants.JSON_MEDICO));
                                turnos.setEspecialidad(json_data.getString(JSONConstants.JSON_ESPECIALIDAD));
                                turnos.setFechaTurno(json_data.getString(JSONConstants.JSON_FECHA_TURNO));

                                //db.newTurno(turnos);
                                datosTurno.add(turnos);


                            }

                            return success;
                        } else  {
                            return jsonObject.getString(JSONConstants.JSON_MENSAJE);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(String string) {
            progressDialog.dismiss();
            if(success!=null) {
                if(success.equals("1")){
                    // Set adapter
                    NuevaConsultaAdapter adapter = new NuevaConsultaAdapter(datosTurno,getActivity(),idmed);
                    recView.setAdapter(adapter);

                } else {
                    Toast.makeText(getContext(), string,Toast.LENGTH_LONG).show();
                    // NuevaConsultaAdapter adapter = new NuevaConsultaAdapter(datosTurno,getActivity());
                    // recView.setAdapter(adapter);
                    recView.setAdapter(null);

                }
            } else {
                Toast.makeText(getContext(), "Se ha producido un error desconocido",Toast.LENGTH_LONG).show();
            }
            db.close();
            datosTurno = null;
        }

    }

    public class HttpRequestTaskPractica extends AsyncTask< String , Void, String> {
        //Before running code in separate thread
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getContext().getResources().getString(R.string.loading));
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                ServiceHandler sh = new ServiceHandler();

                // Make WS Call
                String jsonData = sh.doGetRequest(params[0]);

                if(jsonData!=null){
                    try {
                        // Manejo de Array en JSON

                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONObject json_data;
                        dataPractica = new ArrayList<ObjectStruct>();
                        arrayPractica = new ArrayList<String>();
                        success = jsonObject.getString(JSONConstants.JSON_SUCCESS);
                        if(success.equals("1")){
                            JSONArray jArray = jsonObject.getJSONArray("items");
                            for(int i=0;i<jArray.length();i++){
                                json_data = jArray.getJSONObject(i);
                                practica = new ObjectStruct();
                                practica.setIdObj(json_data.getString(JSONConstants.JSON_ID));
                                practica.setDescripcion(json_data.getString(JSONConstants.JSON_DESCRIPCION));
                                arrayPractica.add(practica.getDescripcion());
                                dataPractica.add(practica);
                            }

                            return success;
                        } else  {
                            return jsonObject.getString(JSONConstants.JSON_MENSAJE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(String string) {
            progressDialog.dismiss();
            if(string!=null) {
                if(success.equals("1")){
                    // Set adapter
                    adapter = new CustomSpinnerAdapter(getContext(),arrayPractica);
                    spinnerPractica.setAdapter(adapter);

                } else {
                    Toast.makeText(getContext(), string,Toast.LENGTH_LONG).show();
                    spinnerPractica.setAdapter(null);
                }
            } else {
                Toast.makeText(getContext(), "Se ha producido un error desconocido",Toast.LENGTH_LONG).show();
                spinnerPractica.setAdapter(null);
            }
        }

    }
}
