package layout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mobile.mads.turnosdim.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationsFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private GoogleMap map;
    private MapView mapView;

    public LocationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment LocationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationsFragment newInstance() {
        LocationsFragment fragment = new LocationsFragment();
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
        View v = inflater.inflate(R.layout.fragment_locations, container, false);
        mapView = (MapView) v.findViewById(R.id.mapView2);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);


        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        /*try {
            MapsInitializer.initialize(this.getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }*/

        return v;
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

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap map) {
//DO WHATEVER YOU WANT WITH GOOGLEMAP
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    123);

        }

        //map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        //MapsInitializer.initialize(this.getActivity());

        LatLng moron = new LatLng(-34.6490785,-58.6149477);
        LatLng moron2 = new LatLng(-34.649256,-58.6163895);
        LatLng central = new LatLng(-34.6419383,-58.5683163);
        LatLng complejidad = new LatLng(-34.6439118,-58.5639431);
        LatLng odonto = new LatLng(-34.6415484,-58.5679718);
        LatLng cardio = new LatLng(-34.6418285,-58.5683123);
        LatLng trauma = new LatLng(-34.6392324,-58.5634551);
        LatLng rivadavia = new LatLng(-34.6411901,-58.5701877);

        map.addMarker(new MarkerOptions()
                .title("DIM Sede Central")
                .snippet("Belgrano 136, Ramos Mejia, Buenos Aires, Argentina")
                .position(central)
        );
        map.addMarker(new MarkerOptions()
            .title("DIM Morón")
            .snippet("Av Rivadavia 17620, Morón, Buenos Aires, Argentina")
            .position(moron)
        );
        map.addMarker(new MarkerOptions()
                .title("DIM Morón 2")
                .snippet("Av Rivadavia 17601, Morón, Buenos Aires, Argentina")
                .position(moron2)
        );
        map.addMarker(new MarkerOptions()
                .title("DIM Alta Complejidad")
                .snippet("Espora 18, Ramos Mejia, Buenos Aires, Argentina")
                .position(complejidad)
        );
        map.addMarker(new MarkerOptions()
                .title("DIM Odontología")
                .snippet("Av de Mayo 67, 3er Piso, Ramos Mejia, Buenos Aires, Argentina")
                .position(odonto)
        );
        map.addMarker(new MarkerOptions()
                .title("DIM Cardiovascular")
                .snippet("Belgrano 135, Ramos Mejía, Buenos Aires, Argentina")
                .position(cardio)
        );
        map.addMarker(new MarkerOptions()
                .title("DIM Traumatología")
                .snippet("Monteagudo 50, Ramos Mejía, Buenos Aires, Argentina")
                .position(trauma)
        );
        map.addMarker(new MarkerOptions()
                .title("DIM Rivadavia")
                .snippet("Av. Rivadavia 14252, Ramos Mejía, Buenos Aires, Argentina")
                .position(rivadavia)
        );



        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 10);
        //map.animateCamera(cameraUpdate);
        LatLng temp = new LatLng(-34.644650,-58.591259);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(temp,13));

    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.

            } else {
                // User refused to grant permission.
            }
        }
    }
}

