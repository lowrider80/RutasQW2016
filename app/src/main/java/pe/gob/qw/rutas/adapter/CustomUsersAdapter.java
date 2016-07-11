package pe.gob.qw.rutas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.qw.rutas.R;
import pe.gob.qw.rutas.entities.LIBERACIONRUTAS;

public class CustomUsersAdapter extends ArrayAdapter<LIBERACIONRUTAS> {
    public CustomUsersAdapter(Context context, ArrayList<LIBERACIONRUTAS> users) {
        super(context, 0, users);
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
         LIBERACIONRUTAS user = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }


        // Lookup view for data population
        TextView Nombre = (TextView) convertView.findViewById(R.id.nombreitem);
        TextView PlazoInicial = (TextView) convertView.findViewById(R.id.plazoinicial);
         TextView PlazoFinal = (TextView) convertView.findViewById(R.id.plazofinal);
         TextView NumeroIEE = (TextView) convertView.findViewById(R.id.NomIEE);
         TextView NumEntrega =(TextView) convertView.findViewById(R.id.Numentrega);


        // Populate the data into the template view using the data object
         Nombre.setText(user.vNomItem);
         PlazoInicial.setText(user.dFecIniciaPlazoEntrega);
         PlazoFinal.setText(user.dFecFinalPlazoEntrega);
         NumeroIEE.setText(String.valueOf(user.NomIEE));
         NumEntrega.setText(String.valueOf(user.iNumEntrega));

        // Return the completed view to render on screen
        return convertView;
    }
}
