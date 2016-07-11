/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pe.gob.qw.rutas.fragment;


import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.gob.qw.rutas.FichaRutasVerif3CardActivity;
import pe.gob.qw.rutas.FichasRutasActivity;
import pe.gob.qw.rutas.R;
import pe.gob.qw.rutas.entities.ITEM;
import pe.gob.qw.rutas.entities.LIBERACIONRECORDCARDITEM;
import pe.gob.qw.rutas.entities.RECORDCARDITEM;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.sqlite.dao.LIBERACIONRUTAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.RECORDCARDITEM_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMDETFOTOSRUTAS_DAO;
import pe.gob.qw.rutas.util.ExpandibleListView;
import pe.gob.qw.rutas.util.Utility;

//import android.widget.AdapterView.OnItemSelectedListener;

public class FichaRutasVerif3SeccionSlideFragment extends Fragment {
    //private SMFICHASGRABADAS OBJSMFICHASGRABADAS=null;
    private String cCodProveedor;
    private String cCodEstablecimiento;
    private int pageNumber;
    private int NUM_PAGES;
    private int idFichasGrabadas;
    private int idCodFicha;
    private int ancho;
    private int NUM_ROWS;
    private int pInicial;
    private int pFinal;
    private boolean paginacion=false;
    private int itemCount = 0;
    private List<RECORDCARDITEM> itens;
    private List<RECORDCARDITEM> sub_itens;
    private List<LIBERACIONRECORDCARDITEM> itensLiberacion;

    private List<RECORDCARDITEM> listFormat;
    private List<RECORDCARDITEM> listFormat2;
    private List<RECORDCARDITEM> listFormat3;
    private List<RECORDCARDITEM> listrevaluar;
    private List<RECORDCARDITEM> listdatosvehiculo;
    private List<String> spin;

    private String cCategoria="";
    private ListView listaEquipos;
    private int iTipoEstablecimiento=0;
    private ViewGroup mContainerView;
    private LinearLayout llTemp;
    private LinearLayout llTemp1;
    private LinearLayout llTemp2;
    private LinearLayout.LayoutParams lp;
    private int[] array={11};
    //private
    int prueba=0;
    private double salto=0;
    private int division = 0;
    private boolean checkboxOn = false;
    //private TextView titulo;

    private List<ITEM> listItemsResponse;
    private ExpandibleListView listItems;
    private List<RECORDCARDITEM> listFormato;

    public List<String> Lista2;




    //GPS!
    private Location location;
    protected LocationManager locationManager;
    boolean isGPSEnabled = false;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 15;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private double latitude=0;
    private double longitude=0;

    //Foto

    private Date fechaFoto;
    private SMFICHASGRABADAS OBJSMFICHASGRABADAS=null;
    private List<String> imageIDs=null;

    public String MODULAR;
    private EditText TexBox3;

    public static FichaRutasVerif3SeccionSlideFragment create(int pageNumber, int NUM_PAGES, int NUM_ROWS, int idFichasGrabadas, int idCodFicha, String cCategoria, int iTipoEstablecimiento, String cCodProveedor, String cCodEstablecimiento) {
        FichaRutasVerif3SeccionSlideFragment fragment = new FichaRutasVerif3SeccionSlideFragment();
        Bundle args = new Bundle();
        args.putInt("pageNumber", pageNumber);
        args.putString("cCategoria", cCategoria);
        args.putInt("idCodFicha", idCodFicha);
        args.putInt("idFichasGrabadas", idFichasGrabadas);
        args.putInt("NUM_ROWS", NUM_ROWS);
        args.putInt("NUM_PAGES", NUM_PAGES);
        args.putInt("iTipoEstablecimiento", iTipoEstablecimiento);
        args.putString("cCodProveedor", cCodProveedor);
        args.putString("cCodEstablecimiento", cCodEstablecimiento);
        fragment.setArguments(args);
        return fragment;
    }

    public FichaRutasVerif3SeccionSlideFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_record_card_slide, container, false);
        // Set the title view to show the page number.
        pageNumber = getArguments().getInt("pageNumber");
        NUM_ROWS = getArguments().getInt("NUM_ROWS");
        idFichasGrabadas= getArguments().getInt("idFichasGrabadas");
        idCodFicha= getArguments().getInt("idCodFicha");
        NUM_PAGES = getArguments().getInt("NUM_PAGES");
        cCategoria = getArguments().getString("cCategoria");
        ancho=(this.getActivity().getWindowManager().getDefaultDisplay().getWidth());
        iTipoEstablecimiento = getArguments().getInt("iTipoEstablecimiento");
        cCodProveedor=getArguments().getString("cCodProveedor");
        cCodEstablecimiento=getArguments().getString("cCodEstablecimiento");
        mContainerView = (ViewGroup) rootView.findViewById(R.id.container);
        pInicial=pageNumber*NUM_ROWS;
        pFinal=(pageNumber+1)*NUM_ROWS;
        if(cCategoria.trim().equals("R")){
            ListaVerificacion2();
        }
        paginacion=true;
        //getLocation();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ListaVerificacionUpdate();
    }

    public void ListaVerificacion2(){
        AddButtonAgregarView();
        itens=RECORDCARDITEM_DAO.ListarSeccionRutasDescarga(FichaRutasVerif3SeccionSlideFragment.this.getActivity(), idFichasGrabadas, array);

        System.out.println("Ficha:" + idFichasGrabadas + " lines:" + itens.size());

        ArrayList<Integer> arrayGroup=null;
        int actualGroup=-1;
        for(int i=0; i<itens.size()	; i++){
            if(actualGroup==-1){
                arrayGroup=new ArrayList<Integer>();
                actualGroup=itens.get(i).iRecordCardItemPadre;
                arrayGroup.add(i);
            }
            else{
                if(actualGroup!=itens.get(i).iRecordCardItemPadre){
                    actualGroup=itens.get(i).iRecordCardItemPadre;
                    int[] arrayGroupFinal= new int[arrayGroup.size()];
                    for(int j=0;j<arrayGroup.size();j++)
                    {
                        arrayGroupFinal[j]=arrayGroup.get(j);
                    }
                    addItem3(arrayGroupFinal);
                    arrayGroup=null;
                    arrayGroup=new ArrayList<Integer>();
                    arrayGroup.add(i);
                    //arrayGroup=null;
                }
                else{
                    arrayGroup.add(i);
                    if(i==itens.size()-1){
                        int[] arrayGroupFinal= new int[arrayGroup.size()];
                        for(int j=0;j<arrayGroup.size();j++)
                        {
                            arrayGroupFinal[j]=arrayGroup.get(j);
                        }
                        addItem3(arrayGroupFinal);
                    }
                }
            }
        }
        paginacion=true;
    }

    public void AddButtonAgregarView(){
            //getLocation();
            LinearLayout lnBtn=new LinearLayout(this.getActivity());
            Button btnAgregar3=new Button(this.getActivity());
            btnAgregar3.setBackgroundResource(R.drawable.button_selector);
            lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            lp.setMargins(10, 0, 10, 10);
            btnAgregar3.setLayoutParams(lp);
            btnAgregar3.setText("AGREGAR");
            btnAgregar3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int contarIEE = RECORDCARDITEM_DAO.GetSumaIEERCVerificacion(FichaRutasVerif3SeccionSlideFragment.this.getActivity(), idFichasGrabadas, "T", 0,idCodFicha);
                    int IEE = LIBERACIONRUTAS_DAO.TotalIEE(FichaRutasVerif3SeccionSlideFragment.this.getActivity(), idFichasGrabadas);
                    int descarga=Contar();
                    int total=descarga+contarIEE;
                    if (IEE < total || IEE==total) {
                        Utility.alert(FichaRutasVerif3SeccionSlideFragment.this.getActivity(), "Ya no se puede agregar mas IEE porque excediste la capacidad", true);
                    }
                    else {
                        Intent intent = new Intent(FichaRutasVerif3SeccionSlideFragment.this.getActivity(), FichaRutasVerif3CardActivity.class);
                        startActivity(intent);
                     //  FichaRutasVerif3SeccionSlideFragment.this.getActivity().finish();

                    }
                }
            });
        lnBtn.addView(btnAgregar3);
        lnBtn.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        lnBtn.setOrientation(LinearLayout.HORIZONTAL);
        mContainerView.addView(lnBtn);
    }
    private int Contar(){

        int resultado=RECORDCARDITEM_DAO.GetSumaIEERC(FichaRutasVerif3SeccionSlideFragment.this.getActivity(),idFichasGrabadas,"N",2,30);
        return resultado;

    }




    private void addItem3(int[] position) {
        // Instantiate a new "row" view.
        final int[] posicion=position;

        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this.getActivity()).inflate(
                R.layout.dinamico, mContainerView, false);
        LinearLayout ll = (LinearLayout)newView.findViewById(R.id.linear);
        LinearLayout llf=new LinearLayout(this.getActivity());
        LinearLayout llr=new LinearLayout(this.getActivity());
        int anchoLeft;

        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        LinearLayout llh1=new LinearLayout(this.getActivity());

        final TextView titulo= new TextView(this.getActivity());
        final TextView descripcion= new TextView(this.getActivity());

        titulo.setText(itens.get(position[2]).vDetalleSeccion);
        descripcion.setText(itens.get(position[2]).vResultado);
        division=ancho/12;

        titulo.setBackgroundColor((Color.parseColor("#1976d2")));
        titulo.setWidth(division * 4);
        titulo.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        titulo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        titulo.setTextColor(Color.WHITE);
        titulo.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        titulo.setGravity(Gravity.CENTER);
        llh1.addView(titulo);

        descripcion.setBackgroundColor((Color.parseColor("#FFFFFF")));
        descripcion.setWidth(division * 5);
        descripcion.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        descripcion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        descripcion.setTextColor(Color.BLACK);
        descripcion.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        descripcion.setGravity(Gravity.CENTER);
        llh1.addView(descripcion);

        LinearLayout llh2=new LinearLayout(this.getActivity());

        final TextView titulo2= new TextView(this.getActivity());
        final TextView descripcion2= new TextView(this.getActivity());
        //final Spinner ComboBox2 = new Spinner(this.getActivity());
        //int division = 0;
        titulo2.setText(itens.get(position[position.length - 9]).vDetalleSeccion);
        descripcion2.setText(itens.get(position[position.length - 9]).vResultado);

        titulo2.setBackgroundColor((Color.parseColor("#1976d2")));
        titulo2.setWidth(division * 4);
        titulo2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        titulo2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        titulo2.setTextColor(Color.WHITE);
        titulo2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        titulo2.setGravity(Gravity.CENTER);
        llh2.addView(titulo2);

        descripcion2.setBackgroundColor((Color.parseColor("#FFFFFF")));
        descripcion2.setWidth(division * 5);
        descripcion2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        descripcion2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        descripcion2.setTextColor(Color.BLACK);
        descripcion2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        descripcion2.setGravity(Gravity.CENTER);
        llh2.addView(descripcion2);

        llh1.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        llh1.setOrientation(LinearLayout.HORIZONTAL);
        llh2.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        llh2.setOrientation(LinearLayout.HORIZONTAL);
        llf.addView(llh1);
        llf.addView(llh2);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        llf.setBackgroundResource(R.drawable.fondo);
        llf.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        llf.setOrientation(LinearLayout.VERTICAL);
        llr.setBackgroundResource(R.drawable.fondo);
        llr.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        llr.setOrientation(LinearLayout.VERTICAL);

        Button edit= new Button(this.getActivity());
        edit.setBackgroundResource(R.drawable.button_selector);
        edit.setText("ELIMINAR");
        edit.setWidth(ancho * 3 / 12);
        final int num=itens.get(posicion[0]).iRecordCardItem;
         edit.setTag(itens.get(posicion[0]).iRecordCardItem);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(FichaRutasVerif3SeccionSlideFragment.this.getActivity());
                alert.setTitle("¡¡ELIMINAR REGISTRO!!");
                alert.setMessage("¿Seguro que deseas eliminar este registro??");
                alert.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                       RECORDCARDITEM_DAO.EliminarRegistroVerificacion(FichaRutasVerif3SeccionSlideFragment.this.getActivity(),
                               idCodFicha,array, Integer.parseInt(String.valueOf(num))
                               );
                        SMDETFOTOSRUTAS_DAO.BorrarFoto(FichaRutasVerif3SeccionSlideFragment.this.getActivity(),idFichasGrabadas,itens.get(posicion[2]).vResultado);
                        ListaVerificacionUpdate();
                        Intent intent = new Intent(FichaRutasVerif3SeccionSlideFragment.this.getActivity(),FichasRutasActivity.class);
                        startActivity(intent);
                        FichaRutasVerif3SeccionSlideFragment.this.getActivity().finish();
                    }
                });
                alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
            }
        });
        llr.setGravity(Gravity.CENTER);
        llr.addView(edit);
        ll.addView(llf);
        ll.addView(llr);
        ll.setBackgroundResource(R.drawable.fondo);
        ll.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.HORIZONTAL);
        mContainerView.addView(newView);//, position[i]);
    }

    public void ListaVerificacionUpdate(){
        mContainerView = (ViewGroup) FichaRutasVerif3SeccionSlideFragment.this.getActivity().findViewById(R.id.container);
        mContainerView.removeViews(0, mContainerView.getChildCount());
        //       array,
        //        idFichasGrabadas)<5) {
        AddButtonAgregarView();
        //}

        itens=RECORDCARDITEM_DAO.ListarSeccionRutasDescarga(FichaRutasVerif3SeccionSlideFragment.this.getActivity(), idFichasGrabadas, array);
        System.out.println("Ficha:" + idFichasGrabadas + " lines:" + itens.size());
        //List<RECORDCARDITEM> grupoItem=null;
        //for(int i=0; i<itens.size(); i++)
        ArrayList<Integer> arrayGroup=null;
        int actualGroup=-1;
        for(int i=0; i<itens.size()	; i++){
            if(actualGroup==-1){
                arrayGroup=new ArrayList<Integer>();
                actualGroup=itens.get(i).iRecordCardItemPadre;
                arrayGroup.add(i);
            }
            else{
                if(actualGroup!=itens.get(i).iRecordCardItemPadre){
                    actualGroup=itens.get(i).iRecordCardItemPadre;
                    int[] arrayGroupFinal= new int[arrayGroup.size()];
                    for(int j=0;j<arrayGroup.size();j++)
                    {
                        arrayGroupFinal[j]=arrayGroup.get(j);
                    }
                    addItem3(arrayGroupFinal);
                    arrayGroup=null;
                    arrayGroup=new ArrayList<Integer>();
                    arrayGroup.add(i);
                    //arrayGroup=null;
                }
                else{
                    arrayGroup.add(i);
                    if(i==itens.size()-1){
                        int[] arrayGroupFinal= new int[arrayGroup.size()];
                        for(int j=0;j<arrayGroup.size();j++)
                        {
                            arrayGroupFinal[j]=arrayGroup.get(j);
                        }
                        addItem3(arrayGroupFinal);
                    }
                }
            }
            //	if(grupoItem!=null){
            // 	}
            //int k = itens.get(i).cSeccion;
            //addItem(i);
            //addItem2(i);
        }
        paginacion=true;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
