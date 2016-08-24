package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import database.DBManager;
import mobile.mads.turnosdim.Paciente;
import mobile.mads.turnosdim.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DBManager db;
    private Paciente paciente;
    private TextView text;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Elementos para manejar pager superior
    int[] imagenes = {
            R.drawable.pager1,
            R.drawable.pager2,
            R.drawable.pager3
    };
    ManagerPager managerPager;
    ViewPager mViewPager;

    //Handler para hacer el autoscroleo del pager
    private android.os.Handler handler;
    private int delay = 2500; //milisegundos de delay para cambio de pagina del pager
    private int page = 0;
    Runnable runnable = new Runnable() {
        public void run() {
            if (managerPager.getCount() == page+1) {
                page = 0;
            } else {
                page++;
            }
            mViewPager.setCurrentItem(page, true);
            handler.postDelayed(this, delay);
        }
    };

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        handler = new android.os.Handler();
        //Creo el pager
        managerPager = new ManagerPager(getActivity().getSupportFragmentManager());
        //Agrego las imagenes al pager utilizando fragmentos MainViewPager que utiliza el layout
        //de fragment_main_view_pager.x ml.
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        for (int i = 0; i < imagenes.length; i++) {
            managerPager.agregarFragmentos(MainViewPagerFragment.newInstance(imagenes[i]));
        }
        mViewPager.setAdapter(managerPager);

        //Hago override de onPageSelected para guardar el numero de pagina actual y utilizarlo
        //en el handler para el auto scrolleo del pager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
        text = (TextView)getActivity().findViewById(R.id.fragmentMainText);
        db = new DBManager(getContext());
        paciente = db.getPaciente(getContext());
        text.setText(paciente.getNombre());


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

    //Override para que funcione bien el handler de autoscrolleo de pager (lo saque de google)
    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    //Override para que funcione bien el handler de autoscrolleo de pager (lo saque de google)
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    //Clase que extiende de un Adapter de Pager, es estandar para manejarlo, sacada de google
    public class ManagerPager extends FragmentPagerAdapter {

        List<Fragment> fragmentos;
        public ManagerPager(FragmentManager fm) {
            super(fm);
            fragmentos = new ArrayList();
        }

        public void agregarFragmentos(Fragment xfragmento){
            fragmentos.add(xfragmento);
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }
    }

}
