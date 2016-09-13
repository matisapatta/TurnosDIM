package layout;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import database.DBManager;
import mobile.mads.turnosdim.DetalleTurno;
import mobile.mads.turnosdim.JSONConstants;
import mobile.mads.turnosdim.ObjectStruct;
import mobile.mads.turnosdim.Paciente;
import mobile.mads.turnosdim.R;
import mobile.mads.turnosdim.ServiceHandler;
import mobile.mads.turnosdim.WSConstants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserSettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText nombre;
    private EditText dni;
    private EditText email;
    private EditText fnac;
    private EditText sexo;
    private EditText cobertura;
    private EditText plan;
    private EditText tel;
    private EditText telad;
    private Button edit;
    private Button save;

    private Paciente paciente;
    private String success;
    private String url;
    private DBManager db;

    public UserSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment UserSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserSettingsFragment newInstance() {
        UserSettingsFragment fragment = new UserSettingsFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
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

        View v = inflater.inflate(R.layout.fragment_user_settings, container, false);


        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        nombre = (EditText)getActivity().findViewById(R.id.contentNombre);
        dni = (EditText)getActivity().findViewById(R.id.contentDNI);
        email = (EditText)getActivity().findViewById(R.id.contentEmail);
        fnac = (EditText)getActivity().findViewById(R.id.contentFnac);
        sexo = (EditText)getActivity().findViewById(R.id.contentSexo);
        cobertura = (EditText)getActivity().findViewById(R.id.contentCoberturaPaciente);
        plan = (EditText)getActivity().findViewById(R.id.contentPlanPaciente);
        tel = (EditText)getActivity().findViewById(R.id.contentTel);
        telad = (EditText)getActivity().findViewById(R.id.contentTelAd);
        edit = (Button)getActivity().findViewById(R.id.btnEditUser);
        save = (Button)getActivity().findViewById(R.id.btnSaveUser);

        nombre.setEnabled(false);
        dni.setEnabled(false);
        email.setEnabled(false);
        fnac.setEnabled(false);
        sexo.setEnabled(false);
        cobertura.setEnabled(false);
        plan.setEnabled(false);
        tel.setEnabled(false);
        telad.setEnabled(false);
        save.setVisibility(View.INVISIBLE);

        db = new DBManager(getContext());
        paciente = db.getPaciente(getContext());
        if(paciente!=null){
            url = WSConstants.StringConstants.WS_URL+ WSConstants.StringConstants.WS_COMANDO_MISDATOS+ WSConstants.StringConstants.WS_ID_PACIENTE+
                    paciente.getIdpaciente()+ WSConstants.StringConstants.WS_TOKEN+paciente.getTokenPaciente()+ WSConstants.StringConstants.WS_FORMATO;
            new HttpRequestTask().execute(url);


            nombre.setText(paciente.getNombre());
            email.setText(paciente.getEmail());
            fnac.setText(paciente.getFnac());
            dni.setText(paciente.getDni());
            sexo.setText(paciente.getSexo());
            cobertura.setText(paciente.getCobertura());
            plan.setText(paciente.getPlan());
            tel.setText(paciente.getTel());
            telad.setText(paciente.getTelad());
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tel.setEnabled(true);
                telad.setEnabled(true);
                email.setEnabled(true);
                tel.setBackground(getActivity().getResources().getDrawable(R.drawable.background_editable));
                telad.setBackground(getActivity().getResources().getDrawable(R.drawable.background_editable));
                email.setBackground(getActivity().getResources().getDrawable(R.drawable.background_editable));
                edit.setVisibility(View.INVISIBLE);
                save.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = WSConstants.StringConstants.WS_URL+ WSConstants.StringConstants.WS_COMANDO_PACIENTE_GUARDAR_DATOS+
                        WSConstants.StringConstants.WS_ID_PACIENTE+paciente.getIdpaciente()+ WSConstants.StringConstants.WS_TOKEN+
                        paciente.getTokenPaciente()+ WSConstants.StringConstants.WS_TELEFONOADICIONAL+telad.getText()+
                        WSConstants.StringConstants.WS_FECHA_NAC+fnac.getText()+ WSConstants.StringConstants.WS_SEXO+sexo.getText()+
                        WSConstants.StringConstants.WS_EMAIL+email.getText();
                new HttpRequestTask2().execute(url);
            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
        void onFragmentInteraction();
    }


    public class HttpRequestTask extends AsyncTask<String , Void, String> {
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

                        success = jsonObject.getString(JSONConstants.JSON_SUCCESS);
                        if(success.equals("1")){
                            paciente.setNombre(jsonObject.getString(JSONConstants.JSON_NOMBRE));
                            paciente.setDni(jsonObject.getString(JSONConstants.JSON_DNI));
                            paciente.setEmail(jsonObject.getString(JSONConstants.JSON_EMAIL));
                            paciente.setFnac(jsonObject.getString(JSONConstants.JSON_FECHA_NAC));
                            paciente.setSexo(jsonObject.getString(JSONConstants.JSON_SEXO));
                            paciente.setCobertura(jsonObject.getString(JSONConstants.JSON_COBERTURA2));
                            paciente.setPlan(jsonObject.getString(JSONConstants.JSON_PLAN));
                            paciente.setTel(jsonObject.getString(JSONConstants.JSON_TEL));
                            paciente.setTelad(jsonObject.getString(JSONConstants.JSON_TEL_AD));
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

                } else {
                    Toast.makeText(getActivity(), string,Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Se ha producido un error desconocido",Toast.LENGTH_LONG).show();
            }
            db.close();

        }

    }

    public class HttpRequestTask2 extends AsyncTask<String , Void, String> {
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
                        success = jsonObject.getString(JSONConstants.JSON_SUCCESS);
                        return jsonObject.getString(JSONConstants.JSON_MENSAJE);

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
                    Toast.makeText(getActivity(), string,Toast.LENGTH_LONG).show();
                    FragmentManager transaction = ((FragmentActivity)getContext()).getSupportFragmentManager();
                    transaction.beginTransaction().replace(R.id.content_main,UserSettingsFragment.newInstance())
                            .commit();
                } else {
                    Toast.makeText(getActivity(),"Se ha producido un error al guardar el turno" ,Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Se ha producido un error desconocido",Toast.LENGTH_LONG).show();
            }
        }

    }

}

