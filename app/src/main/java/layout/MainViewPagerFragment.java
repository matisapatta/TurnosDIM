package layout;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mobile.mads.turnosdim.R;


public class MainViewPagerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_IMAGE = "imagen";
    private int imagen;
    // TODO: Rename and change types of parameters

    //private OnFragmentInteractionListener mListener;

    public MainViewPagerFragment() {
    }




    // TODO: Rename and change types and number of parameters
    public static MainViewPagerFragment newInstance(int imagen) {
        MainViewPagerFragment fragment = new MainViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE, imagen);
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            imagen = getArguments().getInt(ARG_IMAGE);
        }
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_view_pager, container, false);

        ImageView imagenView = (ImageView) rootView.findViewById(R.id.imageInPager);
        imagenView.setImageResource(imagen);
        return rootView;

    }

    //Comente todo aca abajo porque explotaba, se ve que son cosas de fragment que por usarlo en
    //un pager no sirve.

    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
