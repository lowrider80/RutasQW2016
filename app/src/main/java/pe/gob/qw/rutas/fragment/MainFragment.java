package pe.gob.qw.rutas.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.gob.qw.rutas.R;

/**
 * Fragmento para la sección de "Inicio"
 */
public class MainFragment extends Fragment {
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    //private MainAdapter adaptador;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        this.getActivity().setTitle("Inicio");
        //reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        //layoutManager = new LinearLayoutManager(getActivity());
        //reciclador.setLayoutManager(layoutManager);

        //adaptador = new MainAdapter();
        //reciclador.setAdapter(adaptador);
        return view;
    }
}
