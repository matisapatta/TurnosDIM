package layout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import database.DBManager;
import mobile.mads.turnosdim.EntryActivity;
import mobile.mads.turnosdim.JSONConstants;
import mobile.mads.turnosdim.MainActivity;
import mobile.mads.turnosdim.Paciente;
import mobile.mads.turnosdim.ServiceHandler;
import mobile.mads.turnosdim.TurnosAdapter;
import mobile.mads.turnosdim.TurnosStruct;



import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import mobile.mads.turnosdim.R;
import mobile.mads.turnosdim.WSConstants;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TurnosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TurnosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TurnosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TurnosStruct turnos;
    private ArrayList<TurnosStruct> datosTurno;
    private RecyclerView recView;
    private String url;
    private DBManager db;
    private int listIndex = 0;
    private String success;
    private Paciente paciente;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TurnosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment TurnosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TurnosFragment newInstance() {
        TurnosFragment fragment = new TurnosFragment();
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
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_turnos, container, false);

        // Datos de test




        return view;
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

    @Override
    public void onStart(){
        super.onStart();
        db = new DBManager(getContext());
        paciente = db.getPaciente(getContext());
        datosTurno = new ArrayList<TurnosStruct>();
        //turnos = new TurnosStruct();

        url = WSConstants.StringConstants.WS_URL+WSConstants.StringConstants.WS_COMANDO_MISTURNOS+WSConstants.StringConstants.WS_ID_PACIENTE+
            paciente.getIdpaciente()+WSConstants.StringConstants.WS_TOKEN+paciente.getTokenPaciente()+WSConstants.StringConstants.WS_FORMATO;
        recView = (RecyclerView)getActivity().findViewById(R.id.turnosRecView);

        recView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        new HttpRequestTask().execute();
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

    public class HttpRequestTask extends AsyncTask<Void , Void, String> {
        //Before running code in separate thread
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getContext().getResources().getString(R.string.auth));
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                ServiceHandler sh = new ServiceHandler();

                // Make WS Call
                String jsonData = sh.doGetRequest(url);

                if(jsonData!=null){
                    try {
                        // Manejo de Array en JSON

                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONObject json_data;

                        success = jsonObject.getString(JSONConstants.JSON_SUCCESS);
                        if(success.equals("1")){
                            JSONArray jArray = jsonObject.getJSONArray("Turnos");
                                for(int i=0;i<jArray.length();i++){
                                    json_data = jArray.getJSONObject(i);
                                    turnos = new TurnosStruct();
                                    turnos.setMedico(json_data.getString(JSONConstants.JSON_MEDICO));
                                    turnos.setEspecialidad(json_data.getString(JSONConstants.JSON_ESPECIALIDAD));
                                    turnos.setFechaTurno(json_data.getString(JSONConstants.JSON_FECHA_TURNO));
                                    datosTurno.add(turnos);
                                    listIndex++;
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
                    TurnosAdapter adapter = new TurnosAdapter(datosTurno);
                    recView.setAdapter(adapter);

                } else {

                    Toast.makeText(getContext(), string,Toast.LENGTH_LONG).show();
                    TurnosAdapter adapter = new TurnosAdapter(datosTurno);
                    recView.setAdapter(adapter);

                }
            } else {
                Toast.makeText(getContext(), "Se ha producido un error desconocido",Toast.LENGTH_LONG).show();
            }
            db.close();
        }

    }
}




