package pe.gob.inei.generadorinei.util;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.gob.inei.generadorinei.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NombreSeccionFragment extends Fragment {

    private TextView txtNombreSeccion;
    private String nombreSeccion;



    public NombreSeccionFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public NombreSeccionFragment(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_nombre_seccion, container, false);
        txtNombreSeccion = (TextView) rootView.findViewById(R.id.txtNombreSeccion);
        txtNombreSeccion.setText(nombreSeccion);
        return rootView;
    }
}
