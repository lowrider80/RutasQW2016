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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pe.gob.qw.rutas.R;
import pe.gob.qw.rutas.entities.ITEM;
import pe.gob.qw.rutas.entities.LIBERACIONRECORDCARDITEM;
import pe.gob.qw.rutas.entities.RECORDCARDITEM;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.sqlite.dao.RECORDCARDITEM_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMFICHASGRABADAS_DAO;
import pe.gob.qw.rutas.util.ExpandibleListView;
import pe.gob.qw.rutas.util.Utility;

import static android.widget.AdapterView.OnItemSelectedListener;

//import android.widget.AdapterView.OnItemSelectedListener;

public class FichaRutasVerif2SeccionSlideFragment extends Fragment implements LocationListener {
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
    private int[] array={10};
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

    private String resultado;




    /////////////////////////////////


    //public ArrayAdapter<String> adapterCombo2;
    //////////////////////////////////////

    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    private List<SMFICHASGRABADAS> listFichas;



    public List listComponenteCombo2=new ArrayList<String>();
    public ArrayAdapter<String> arrayCombo2;
    private Spinner Spinner1;
    private Spinner Spinner2;
    private EditText otrostexto,placa,marca;

    private int posicion1=0,posicion2=0;
    private SMFICHASGRABADAS OBJSMFICHASGRABADAS=null;

    public static FichaRutasVerif2SeccionSlideFragment create(int pageNumber, int NUM_PAGES, int NUM_ROWS, int idFichasGrabadas, int idCodFicha, String cCategoria, int iTipoEstablecimiento, String cCodProveedor, String cCodEstablecimiento) {
        FichaRutasVerif2SeccionSlideFragment fragment = new FichaRutasVerif2SeccionSlideFragment();

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

    public FichaRutasVerif2SeccionSlideFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_record_card_slide, container, false);
        getLocation();

        OBJSMFICHASGRABADAS =SMFICHASGRABADAS_DAO.Buscar(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
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
        getLocation();
        return rootView;
    }

    public void ListaVerificacion2(){
        AddButtonAgregarView();
        itens=RECORDCARDITEM_DAO.ListarSeccionRutasDescarga(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), idFichasGrabadas, array);

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
        LinearLayout lnBtn=new LinearLayout(this.getActivity());
        Button btnAgregar3=new Button(this.getActivity());
        btnAgregar3.setBackgroundResource(R.drawable.button_selector);
        lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.setMargins(10, 0, 10, 10);
        //lp.setMargins(50, 0, 150, 50);
        btnAgregar3.setLayoutParams(lp);
        btnAgregar3.setText("AGREGAR");
        btnAgregar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                dialog.setTitle("TRANSBORDO EN RUTA");
                dialog.setContentView(R.layout.pop_up_transporte_add3);
                dialog.setCancelable(false);
                getLocation();
                listFichas = SMFICHASGRABADAS_DAO.ListarUltima(FichaRutasVerif2SeccionSlideFragment.this.getActivity(),true);
                int id = RECORDCARDITEM_DAO.AgregarTransporte2(FichaRutasVerif2SeccionSlideFragment.this.getActivity(),
                        idFichasGrabadas,
                        idCodFicha,
                        cCodProveedor,
                        cCodEstablecimiento);
                listFormato = RECORDCARDITEM_DAO.ListarFormatoTransporteValores(FichaRutasVerif2SeccionSlideFragment.this.getActivity(),
                        idCodFicha,
                        array,
                        id);
               final LinearLayout pll = (LinearLayout) dialog.findViewById(R.id.linear);
               final LinearLayout pll2 = (LinearLayout) dialog.findViewById(R.id.linear2);
               final LinearLayout pll3 =(LinearLayout)dialog.findViewById(R.id.linearmedio);





                final LinearLayout llTempAA = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                pll2.setVisibility(View.VISIBLE);

                for(int i=0;i<listFormato.size()-1;i++) {


                    final int posicion = i;
                    final LinearLayout pllh = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                    if (posicion != 4 && posicion != 5 && posicion!=6 && posicion!=7) {


                        final TextView titulo = new TextView(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                        final TextView subtitulo = new TextView(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                        final EditText TexBox;
                        final EditText TexBox2 = new EditText(FichaRutasVerif2SeccionSlideFragment.this.getActivity());

                            titulo.setText(listFormato.get(i).vDetalleSeccion);
                            titulo.setTextSize(14);
                            titulo.setPadding(5, 5, 5, 5);
                            titulo.setTypeface(null, Typeface.BOLD);
                            titulo.setGravity(Gravity.CENTER);
                            titulo.setTextColor(Color.parseColor("#FFFFFF"));
                            titulo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


                        switch (listFormato.get(i).iTipoControl) {
                            case 0:
                                if (listFormato.get(i).cSeccion.toString().trim().equals("T")) {

                                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    lp.setMargins(5, 10, 5, 0);
                                    subtitulo.setLayoutParams(lp);
                                    subtitulo.setBackgroundColor(Color.parseColor("#000000"));
                                    subtitulo.setPadding(15, 8, 15, 8);
                                    subtitulo.setTextColor(Color.parseColor("#FFFFFF"));
                                    subtitulo.setTextSize(15);
                                    subtitulo.setTypeface(null, Typeface.BOLD);
                                    subtitulo.setGravity(Gravity.CENTER);
                                    subtitulo.setText(listFormato.get(posicion).vDetalleSeccion);
                                    pllh.addView(subtitulo);
                                }

                                break;

                            case 13:
                                LinearLayout llTempX1 = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(5, 3, 5, 0);
                                llTempX1.setLayoutParams(lp);
                                llTempX1.setOrientation(LinearLayout.HORIZONTAL);
                                llTempX1.setGravity(Gravity.CENTER);
                                llTempX1.setBackgroundColor(Color.parseColor("#1976d2"));

                                LinearLayout llTempY1 = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(0, 0, 0, 0);
                                llTempY1.setLayoutParams(lp);
                                llTempY1.setGravity(Gravity.CENTER);
                                llTempY1.setOrientation(LinearLayout.VERTICAL);

                                titulo.setBackgroundColor((Color.parseColor("#1976d2")));
                                titulo.setWidth(division * 3);
                                titulo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                titulo.setTextColor(Color.WHITE);
                                titulo.setGravity(Gravity.CENTER);

                                llTempY1.addView(titulo);
                                llTempX1.addView(llTempY1);

                                LinearLayout llTempZ1 = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(2, 2, 2, 2);
                                llTempZ1.setLayoutParams(lp);
                                llTempZ1.setOrientation(LinearLayout.VERTICAL);
                                llTempZ1.setGravity(Gravity.CENTER);
                                llTempZ1.setBackgroundColor(Color.parseColor("#FFFFFF"));

                                TexBox2.setWidth(division * 4);
                                TexBox2.setHint("Ingrese" + listFormato.get(i).vDetalleSeccion);
                                TexBox2.setTextColor(Color.BLACK);
                                TexBox2.setGravity(Gravity.CENTER);
                                TexBox2.setKeyListener(null);
                                TexBox2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int validatelimit = 0;
                                        if (listFormato.get(posicion).vDetalleSeccion.contains("VENCIMIENTO"))
                                            validatelimit = 0;
                                        if (listFormato.get(posicion).vDetalleSeccion.contains("RECEP"))
                                            validatelimit = -1;
                                        showTimePickerDialog(v, validatelimit);
                                    }

                                    public void showTimePickerDialog(View v, int validate) {
                                        final int validateLimit = validate;
                                        DialogFragment datePickerFragment = new DatePickerFragment() {
                                            @Override
                                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                                Calendar c = Calendar.getInstance();
                                                c.set(year, month, day);
                                                String myFormat = "dd/MM/yy";
                                                String formatoIngles = "yyyy-MM-dd";
                                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                                                SimpleDateFormat sdfe = new SimpleDateFormat(formatoIngles);
                                                TexBox2.setText(sdf.format(c.getTime()));
                                            }

                                            @Override
                                            public Dialog onCreateDialog(Bundle savedInstanceState) {
                                                final Calendar c = Calendar.getInstance();
                                                int year = c.get(Calendar.YEAR);
                                                int month = c.get(Calendar.MONTH);
                                                int day = c.get(Calendar.DAY_OF_MONTH);
                                                DatePickerDialog dialogoPicker = new DatePickerDialog(getActivity(), this, 0, 0, 0);
                                                dialogoPicker.getDatePicker().setMinDate(listFichas.get(0).lFecha);
                                                long millis2 = 1000L * 60L * 60L * 24L * 30L;
                                                // Long.parseLong(str);
                                                long millis = Long.parseLong(String.valueOf(listFichas.get(0).lFecha));
                                                long millifinal = millis + millis2;
                                                dialogoPicker.getDatePicker().setMaxDate(new Date().getTime());
                                                Calendar m_calendar_min = Calendar.getInstance();
                                                if (validateLimit == 1)
                                                    dialogoPicker.getDatePicker().setMinDate(m_calendar_min.getTime().getTime());
                                                if (validateLimit == -1)
                                                    dialogoPicker.getDatePicker().setMaxDate(m_calendar_min.getTime().getTime());
                                                dialogoPicker.updateDate(year, month, day);
                                                return dialogoPicker;
                                            }
                                        };
                                        datePickerFragment.show(FichaRutasVerif2SeccionSlideFragment.this.getActivity().getFragmentManager(), "datePicker");
                                    }
                                });

                                TexBox2.addTextChangedListener(new TextWatcher() {

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        listFormato.get(posicion).vResultado = TexBox2.getText().toString();
                                        listFormato.get(posicion).bcheck=true;
                                        listFormato.get(posicion).bactivo=true;
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));

                                    }

                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                    }
                                });
                                //pllh.addView(TexBox2);
                                llTempZ1.addView(TexBox2);
                                TexBox2.setText(listFormato.get(i).vResultado);
                                llTempX1.addView(llTempZ1);
                                pllh.addView(llTempX1);
                                break;
                            case 2:

                                final LinearLayout llTempX = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(5, 3, 5, 0);
                                llTempX.setLayoutParams(lp);
                                llTempX.setOrientation(LinearLayout.HORIZONTAL);
                                llTempX.setGravity(Gravity.CENTER);
                                llTempX.setBackgroundColor(Color.parseColor("#1976d2"));

                                LinearLayout llTempY = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(0, 0, 0, 0);
                                llTempY.setLayoutParams(lp);
                                llTempY.setGravity(Gravity.CENTER);
                                llTempY.setOrientation(LinearLayout.VERTICAL);

                                llTempY.addView(titulo);
                                llTempX.addView(llTempY);

                                LinearLayout llTempZ = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(2, 2, 2, 2);
                                llTempZ.setLayoutParams(lp);
                                llTempZ.setOrientation(LinearLayout.VERTICAL);
                                llTempZ.setGravity(Gravity.CENTER);
                                llTempZ.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                TexBox = new EditText(FichaRutasVerif2SeccionSlideFragment.this.getActivity());

                                if (listFormato.get(i).vResultado.toString().trim().equals("")) {
                                    TexBox.setHint("Ingresar");
                                } else {
                                    TexBox.setText(listFormato.get(i).vResultado.toString());
                                }

                                if (listFormato.get(i).cSeccion.toString().trim().equals("N")) {

                                    TexBox.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    int maxLength = 8;
                                    TexBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                                } else if (listFormato.get(i).cSeccion.toString().trim().equals("P")) {

                                    TexBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                                    int maxLength = 6;
                                    TexBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                                } else {
                                    TexBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                                }

                                TexBox.setTextColor(Color.parseColor("#1976d2"));
                                TexBox.setTextSize(15);
                                TexBox.setTypeface(null, Typeface.BOLD);
                                TexBox.setGravity(Gravity.CENTER);
                                llTempZ.addView(TexBox);

                                TexBox.addTextChangedListener(new TextWatcher() {

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        listFormato.get(posicion).vResultado = TexBox.getText().toString();
                                        listFormato.get(posicion).bcheck=true;
                                        listFormato.get(posicion).bactivo=true;
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                    }

                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                    }
                                });
                                llTempX.addView(llTempZ);
                                pllh.addView(llTempX);


                                break;
                            case 8:


                                final LinearLayout llTEMPADRE = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(5, 3, 5, 0);
                                llTEMPADRE.setLayoutParams(lp);
                                llTEMPADRE.setOrientation(LinearLayout.VERTICAL);
                                llTEMPADRE.setGravity(Gravity.CENTER);
                                llTEMPADRE.setBackgroundColor(Color.parseColor("#1976d2"));


                                LinearLayout llTempXX = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(5, 3, 5, 0);
                                llTempXX.setLayoutParams(lp);
                                llTempXX.setOrientation(LinearLayout.HORIZONTAL);
                                llTempXX.setGravity(Gravity.CENTER);
                                llTempXX.setBackgroundColor(Color.parseColor("#1976d2"));

                                LinearLayout llTempYY = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(0, 0, 0, 0);
                                llTempYY.setLayoutParams(lp);
                                llTempYY.setGravity(Gravity.CENTER);
                                llTempYY.setOrientation(LinearLayout.VERTICAL);


                                llTempYY.addView(titulo);


                                llTempXX.addView(llTempYY);

                                LinearLayout llTempZZ = new LinearLayout(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(2, 2, 2, 2);
                                llTempZZ.setLayoutParams(lp);
                                llTempZZ.setOrientation(LinearLayout.VERTICAL);
                                llTempZZ.setGravity(Gravity.CENTER);
                                llTempZZ.setBackgroundColor(Color.parseColor("#FFFFFF"));

                                final Spinner Combo1 = new Spinner(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                final Spinner Combo2 = new Spinner(FichaRutasVerif2SeccionSlideFragment.this.getActivity());


                                List<String> list = new ArrayList<String>();
                                list.add("TERRESTRE");
                                list.add("FLUVIAL");
                                list.add("AEREO");
                                ArrayAdapter<String> adapterCombo = new ArrayAdapter<String>(FichaRutasVerif2SeccionSlideFragment.this.getActivity()
                                        , android.R.layout.simple_spinner_item, list);


                                List<String> list2 = new ArrayList<String>();
                                list2.add("SELECCIONE");
                                list2.add("OP1");
                                list2.add("OP2");
                                arrayCombo2 = new ArrayAdapter<String>(FichaRutasVerif2SeccionSlideFragment.this.getActivity()
                                        , android.R.layout.simple_spinner_item, list2);

                                if (listFormato.get(posicion).cSeccion.trim().equals("M")) {

                                    adapterCombo.setDropDownViewResource(R.layout.spinner);
                                    Combo1.setAdapter(adapterCombo);
                                    Combo1.setSelection(listFormato.get(posicion).iIndexCombo);
                                    llTempZZ.addView(Combo1);
                                    Combo1.setGravity(Gravity.CENTER);
                                    llTempXX.addView(llTempZZ);
                                    llTEMPADRE.addView(llTempXX);
                                    pllh.addView(llTEMPADRE);

                                } else if (listFormato.get(posicion).cSeccion.trim().equals("U")) {
                                    final Spinner Combo0 = new Spinner(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                                    List<String> list3 = new ArrayList<String>();
                                    list3.add("SI");
                                    list3.add("NO");
                                    ArrayAdapter<String> adapterCombo3 = new ArrayAdapter<String>(FichaRutasVerif2SeccionSlideFragment.this.getActivity()
                                            , android.R.layout.simple_spinner_item, list3);
                                    adapterCombo3.setDropDownViewResource(R.layout.spinner);
                                    Combo0.setAdapter(adapterCombo3);
                                    llTempZZ.addView(Combo0);
                                    llTempXX.addView(llTempZZ);
                                    //llTempXX.addView(llTempAA);
                                    llTEMPADRE.addView(llTempXX);
                                    pllh.addView(llTEMPADRE);
                                    Combo0.setOnItemSelectedListener(new OnItemSelectedListener() {
                                        public void onItemSelected(AdapterView<?> adapterView,
                                                                   View view, int pos, long id) {
                                            if (pos == 0) {
                                                pll2.setVisibility(View.VISIBLE);
                                                pll3.setVisibility(View.GONE);

                                            } else if (pos == 1) {
                                                pll2.setVisibility(View.GONE);
                                                pll3.setVisibility(View.VISIBLE);
                                            }
                                            listFormato.get(posicion).bcheck=true;
                                            listFormato.get(posicion).bactivo=true;
                                            listFormato.get(posicion).vResultado = Combo0.getSelectedItem().toString();
                                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                        }

                                        public void onNothingSelected(AdapterView<?> arg0) {
                                            // TODO Auto-generated method stub
                                        }
                                    });

                                } else if (listFormato.get(posicion).cSeccion.trim().equals("T")) {


                                }
                                break;

                        }

                        if (posicion == 3) {
                            pll2.setOrientation(LinearLayout.VERTICAL);
                            pll2.addView(pllh);
                        } else if (posicion == 4 || posicion == 5)
                        {
                            pll3.setOrientation(LinearLayout.VERTICAL);
                            pll3.addView(pllh);

                        } else {
                            pll.setOrientation(LinearLayout.VERTICAL);
                            pll.addView(pllh);

                        }

                    }
                    else {
                        final Spinner Combo3 = new Spinner(FichaRutasVerif2SeccionSlideFragment.this.getActivity());

                        if (posicion == 5) {

                            LinearLayout linear = (LinearLayout) dialog.findViewById(R.id.linearmedio);
                            linear.setOrientation(LinearLayout.VERTICAL);
                            final TextView texto = (TextView) dialog.findViewById(R.id.txtspinner);
                            texto.setText("MEDIO DE TRANSPORTE");
                            texto.setTextSize(14);
                            texto.setPadding(5, 5, 5, 5);
                            texto.setTypeface(null, Typeface.BOLD);
                            texto.setGravity(Gravity.CENTER);
                            texto.setTextColor(Color.parseColor("#FFFFFF"));

                            Spinner1 = (Spinner) dialog.findViewById(R.id.spinnermodo);
                            Spinner2 = (Spinner) dialog.findViewById(R.id.spinnermodo2);
                            List<String> list = new ArrayList<String>();

                            list.add("SELECCIONE");
                            list.add("TERRESTRE");
                            list.add("FLUVIAL");
                            list.add("AEREO");
                            ArrayAdapter<String> adapterCombo = new ArrayAdapter<String>(FichaRutasVerif2SeccionSlideFragment.this.getActivity()
                                    , android.R.layout.simple_spinner_item, list);
                            Spinner1.setAdapter(adapterCombo);

                            listComponenteCombo2.add("SELECCIONE");


                            final ArrayAdapter<String> adapterCombo2 = new ArrayAdapter<String>(FichaRutasVerif2SeccionSlideFragment.this.getActivity()
                                    , android.R.layout.simple_spinner_item, listComponenteCombo2);
                            Spinner2.setAdapter(adapterCombo2);
                            Spinner2.setGravity(Gravity.CENTER);
                           // Spin1.setSelection(listFormato.get(posicion).iIndexCombo);

                            Spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                    if(Spinner1.getSelectedItemPosition() == 0){
                                    posicion1=0;
                                    posicion2=0;
                                    }


                                   else if (Spinner1.getSelectedItemPosition() == 1) {
                                        adapterCombo2.clear();
                                        listComponenteCombo2.add("SELECCIONE");
                                        listComponenteCombo2.add("CAMION");
                                        listComponenteCombo2.add("FURGON,FURGONETA");
                                        listComponenteCombo2.add("CAMIONETA");
                                        listComponenteCombo2.add("AUTO");
                                        listComponenteCombo2.add("COMBI,CUSTER,MINIVAN");
                                        listComponenteCombo2.add("TRIMOTO,MOTOCAR,MOTOTAXI");
                                        listComponenteCombo2.add("TRICICLO");
                                        listComponenteCombo2.add("ACEMILA");
                                        listComponenteCombo2.add("OTROS");
                                        posicion1=1;
                                        posicion2=0;
                                        //   Combo2.setSelection(0);
                                        adapterCombo2.notifyDataSetChanged();
                                        Spinner2.setSelection(0);
                                        listFormato.get(4).vResultado = Spinner1.getSelectedItem().toString();
                                        listFormato.get(4).bcheck=true;
                                        listFormato.get(4).bactivo=true;
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(4));
                                        listFormato.get(posicion).vResultado = Spinner2.getSelectedItem().toString();
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                   //     Spinner2.setSelection(0);
                                    } else if (Spinner1.getSelectedItemPosition() == 2) {
                                        adapterCombo2.clear();
                                        listComponenteCombo2.add("SELECCIONE");
                                        listComponenteCombo2.add("CON MOTOR:BOTE,LANCHA,ETC");
                                        listComponenteCombo2.add("SIN MOTOR:CANOA,BALSA,ETC");
                                        listComponenteCombo2.add("OTROS");
                                        posicion1=2;
                                        posicion2=0;
                                        //Combo2.setSelection(1);
                                        adapterCombo2.notifyDataSetChanged();
                                        Spinner2.setSelection(0);
                                        listFormato.get(4).bcheck=true;
                                        listFormato.get(4).bactivo=true;
                                        listFormato.get(4).vResultado = Spinner1.getSelectedItem().toString();
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(4));
                                        listFormato.get(posicion).vResultado = Spinner2.getSelectedItem().toString();
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                     //   Spinner2.setSelection(0);
                                    } else {

                                        listComponenteCombo2.clear();
                                        listComponenteCombo2.add("SELECCIONE");
                                        listComponenteCombo2.add("AVION,AVIONETA");
                                        listComponenteCombo2.add("HELICOPTERO");
                                        listComponenteCombo2.add("OTRO");
                                        posicion1=position;
                                        posicion2=0;
                                        // Combo2.setSelection(2);
                                        adapterCombo2.notifyDataSetChanged();
                                        Spinner2.setSelection(0);
                                        listFormato.get(4).bcheck=true;
                                        listFormato.get(4).bactivo=true;
                                        listFormato.get(4).vResultado = Spinner1.getSelectedItem().toString();
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(4));
                                        listFormato.get(posicion).vResultado = Spinner2.getSelectedItem().toString();
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                       // Spinner2.setSelection(0);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parentView) {
                                    // your code here
                                }

                            });



                            final TextView titulo = (TextView) dialog.findViewById(R.id.txtspinner2);
                            titulo.setText(listFormato.get(i).vDetalleSeccion);
                            titulo.setTextSize(14);
                            titulo.setPadding(5, 5, 5, 5);
                            titulo.setTypeface(null, Typeface.BOLD);
                            titulo.setGravity(Gravity.CENTER);
                            titulo.setTextColor(Color.parseColor("#FFFFFF"));

                            final TextView texto2 = (TextView) dialog.findViewById(R.id.txtotros);
                            texto2.setText("ESPECIFIQUE/OTROS");
                            texto2.setTextSize(14);
                            texto2.setPadding(5, 5, 5, 5);
                            texto2.setTypeface(null, Typeface.BOLD);
                            texto2.setGravity(Gravity.CENTER);
                            texto2.setTextColor(Color.parseColor("#FFFFFF"));



                            otrostexto = (EditText) dialog.findViewById(R.id.edittextotros);
                            otrostexto.setHint("Ingresar");
                            otrostexto.setGravity(Gravity.CENTER);

                            final TextView texto3 = (TextView) dialog.findViewById(R.id.lblplaca);
                            texto3.setText("PLACA");
                            texto3.setTextSize(14);
                            texto3.setPadding(5, 5, 5, 5);
                            texto3.setTypeface(null, Typeface.BOLD);
                            texto3.setGravity(Gravity.CENTER);
                            texto3.setTextColor(Color.parseColor("#FFFFFF"));



                            final TextView texto4 = (TextView) dialog.findViewById(R.id.lblmarca);
                            texto4.setText("MARCA");
                            texto4.setTextSize(14);
                            texto4.setPadding(5, 5, 5, 5);
                            texto4.setTypeface(null, Typeface.BOLD);
                            texto4.setGravity(Gravity.CENTER);
                            texto4.setTextColor(Color.parseColor("#FFFFFF"));




                            placa = (EditText) dialog.findViewById(R.id.txtplaca);
                            placa.setHint("Ingresar");
                            placa.setGravity(Gravity.CENTER);
                            int maxLength = 7;
                            placa.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});


                           //

                            marca = (EditText) dialog.findViewById(R.id.txtmarcaa);
                            marca.setHint("Ingresar");
                            marca.setGravity(Gravity.CENTER);



                            Spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (Spinner1.getSelectedItemPosition() == 1 && position == 9) {

                                        otrostexto.setVisibility(View.VISIBLE);
                                        texto2.setVisibility(View.VISIBLE);
                                    }
                                    else if(Spinner1.getSelectedItemPosition()==1 && position==8)
                                    {

                                        placa.setVisibility(View.GONE);
                                        texto3.setVisibility(View.GONE);
                                        marca.setVisibility(View.GONE);
                                        texto4.setVisibility(View.GONE);

                                        listFormato.get(6).vResultado="---";
                                        listFormato.get(6).bcheck=false;
                                        listFormato.get(6).bactivo=false;
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(6));
                                        listFormato.get(7).vResultado="---";
                                        listFormato.get(7).bcheck=false;
                                        listFormato.get(7).bactivo=false;
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(7));

                                    }
                                    else if(Spinner1.getSelectedItemPosition() == 2 && position == 3)
                                    {
                                        otrostexto.setVisibility(View.VISIBLE);
                                        texto2.setVisibility(View.VISIBLE);
                                    }
                                    else if(Spinner1.getSelectedItemPosition() == 3 && position == 3)
                                    {
                                        otrostexto.setVisibility(View.VISIBLE);
                                        texto2.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                        otrostexto.setVisibility(View.GONE);
                                        texto2.setVisibility(View.GONE);
                                    }

                                   if(Spinner2.getSelectedItemPosition()==0)
                                   {
                                        posicion2=0;
                                   }
                                   else
                                   {
                                       listFormato.get(5).vResultado = Spinner2.getSelectedItem().toString();
                                       listFormato.get(5).bcheck=true;
                                       listFormato.get(5).bactivo=true;
                                       RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(5));
                                       posicion2=position;
                                   }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        ///////////////////////////////////TEXTOS//////////////////////////////////

                            placa.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    listFormato.get(6).vResultado = placa.getText().toString().toUpperCase();
                                    listFormato.get(6).bcheck=true;
                                    listFormato.get(6).bactivo=true;
                                    RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(6));
                                }
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                    Pattern pattern;
                                    Matcher matcher;
                                     if(placa.length()==7) {
                                         pattern = Pattern.compile("^[a-zA-Z0-9]{2,4}-[a-zA-Z0-9]{2,4}");
                                         matcher = pattern.matcher(placa.getText().toString());
                                         if (!matcher.matches()) {
                                             placa.setError("Formato de Placa Incorrecto");
                                             Utility.alert(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), "Formato de Placa Incorrecto digita nuevamente  ", true);
                                             placa.requestFocus();
                                             placa.setText("");
                                         }
                                     }
                                }
                            });

                            marca.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    listFormato.get(7).vResultado = marca.getText().toString();
                                    listFormato.get(7).bcheck=true;
                                    listFormato.get(7).bactivo=true;
                                    RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(7));
                                }

                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });

                            otrostexto.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    listFormato.get(3).vResultado = otrostexto.getText().toString();
                                    listFormato.get(3).bcheck=true;
                                    listFormato.get(3).bactivo=true;
                                    RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(3));
                                }

                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });

                        }



                    }



                }






                final Button buttonAcept = (Button) dialog.findViewById(R.id.buttonAcept);
                buttonAcept.setTag(id);
                buttonAcept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String latitud = String.valueOf(latitude);
                        String longitud= String.valueOf(longitude);
                        if(latitud!="0.0" || longitud !="0.0" )
                        {
                            listFormato.get(listFormato.size() - 1).vResultado= String.valueOf(latitude);
                            listFormato.get(listFormato.size() - 1).vObservacion= String.valueOf(longitude);
                            listFormato.get(listFormato.size() - 1).bactivo=true;
                            listFormato.get(listFormato.size() - 1).bcheck=true;
                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));
                            listFormato.get(0).vResultado=OBJSMFICHASGRABADAS.vPlaca.toUpperCase().toString();
                            listFormato.get(0).vObservacion="CONFORME";
                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(0));

                            int camposvacios = RECORDCARDITEM_DAO.CantidadCamposVaciosRecordCardItemVerificacion(FichaRutasVerif2SeccionSlideFragment.this.getActivity(),
                                    idCodFicha,
                                    array,
                                    Integer.parseInt(String.valueOf(buttonAcept.getTag())));


                            if(listFormato.get(2).vResultado.trim().equals("SI"))
                            {
                                if (camposvacios == 2) {
                                    RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));
                                    dialog.dismiss();
                                    ListaVerificacionUpdate();
                                }
                                else {camposvacios=camposvacios-2; Utility.alert(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), "Faltan llenar " + camposvacios + " campos.", true); }
                            }
                            else {
                                if (posicion1 == 0 || posicion2 == 0) {
                                    Utility.alert(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), "Debe Seleccionar Medio de Transporte", true);
                                }
                                else if (posicion1==1 && posicion2==9)
                                {
                                    if (camposvacios == 0) {
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));
                                        dialog.dismiss();
                                        ListaVerificacionUpdate();
                                    }
                                    else {Utility.alert(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), "Faltan llenar " + camposvacios + " campos.", true);}
                                }
                                else if(posicion1==2 && posicion2==3){
                                    if (camposvacios == 0) {
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));
                                        dialog.dismiss();
                                        ListaVerificacionUpdate();
                                    }
                                    else {Utility.alert(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), "Faltan llenar " + camposvacios + " campos.", true);}
                                }
                                else if(posicion1==3 && posicion2==3){
                                    if (camposvacios == 0) {
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));
                                        dialog.dismiss();
                                        ListaVerificacionUpdate();
                                    }
                                    else {Utility.alert(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), "Faltan llenar " + camposvacios + " campos.", true);}
                                    }
                                else{
                                    if (camposvacios == 1) {
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));
                                        dialog.dismiss();
                                        ListaVerificacionUpdate();
                                    } else {
                                        Utility.alert(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), "Faltan llenar " + camposvacios + " campos.", true);
                                    }

                                }
                            }

                         }
                        else{Utility.alert(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), "Las Coordenadas no son correctas", true); }
                    }
                });
                final Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
                buttonCancel.setTag(id);
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RECORDCARDITEM_DAO.EliminarRegistroVerificacion(FichaRutasVerif2SeccionSlideFragment.this.getActivity(),
                                idCodFicha,
                                array,
                                Integer.parseInt(String.valueOf(buttonCancel.getTag())));
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        lnBtn.addView(btnAgregar3);
        lnBtn.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        lnBtn.setOrientation(LinearLayout.HORIZONTAL);
        mContainerView.addView(lnBtn);
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
        LinearLayout llh2=new LinearLayout(this.getActivity());
        final TextView titulo2= new TextView(this.getActivity());
        final TextView descripcion2= new TextView(this.getActivity());



        resultado=itens.get(position[2]).vResultado.toString();

        if (resultado.trim().equals("SI"))
        {
            titulo.setText(itens.get(position[2]).vDetalleSeccion);
            descripcion.setText(itens.get(position[2]).vResultado);
            titulo2.setText(itens.get(position[3]).vDetalleSeccion);
            descripcion2.setText(itens.get(position[3]).vResultado);
        }

        else
        {
            titulo.setText(itens.get(position[5]).vDetalleSeccion);
            descripcion.setText(itens.get(position[5]).vResultado);
            titulo2.setText(itens.get(position[position.length - 3]).vDetalleSeccion);
            descripcion2.setText(itens.get(position[position.length - 3]).vResultado);
        }





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


        //final Spinner ComboBox2 = new Spinner(this.getActivity());
        //int division = 0;


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

                AlertDialog.Builder alert = new AlertDialog.Builder(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
                alert.setTitle("¡¡ELIMINAR REGISTRO!!");
                alert.setMessage("¿Seguro que deseas eliminar este registro??");
                alert.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                       RECORDCARDITEM_DAO.EliminarRegistroVerificacion(FichaRutasVerif2SeccionSlideFragment.this.getActivity(),
                               idCodFicha,array, Integer.parseInt(String.valueOf(num))
                               );
                        ListaVerificacionUpdate();
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
        mContainerView = (ViewGroup) FichaRutasVerif2SeccionSlideFragment.this.getActivity().findViewById(R.id.container);
        mContainerView.removeViews(0, mContainerView.getChildCount());

        //       array,
        //        idFichasGrabadas)<5) {
        AddButtonAgregarView();
        //}

        itens=RECORDCARDITEM_DAO.ListarSeccionRutasDescarga(FichaRutasVerif2SeccionSlideFragment.this.getActivity(), idFichasGrabadas, array);
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


    public Location getLocation() throws SecurityException {
        try {
            locationManager = (LocationManager) FichaRutasVerif2SeccionSlideFragment.this.getActivity()
                    .getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                showSettingsAlert(1);
            } else {
                this.canGetLocation = true;
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if (isNetworkEnabled) {
                    if(location==null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();

        }


        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        return longitude;
    }

    public void showSettingsAlert(int state){
        if(state==1) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(FichaRutasVerif2SeccionSlideFragment.this.getActivity());
            alertDialog.setTitle("GPS desactivado");

            alertDialog.setMessage("Por favor acceda a configuración de GPS y actívelo para el registro, se cerrará la aplicación");

            alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    FichaRutasVerif2SeccionSlideFragment.this.getActivity().startActivity(intent);
                    ((Activity) FichaRutasVerif2SeccionSlideFragment.this.getActivity()).finish();
                }
            });
            alertDialog.show();
        }
        else{
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            FichaRutasVerif2SeccionSlideFragment.this.getActivity().startActivity(intent);
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        getLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        getLocation();
    }

    @Override
    public void onProviderEnabled(String provider) {
        locationManager = (LocationManager)this.getActivity(). getSystemService(this.getActivity().LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }
    }

    @Override
    public void onProviderDisabled(String string) {
        // TODO Auto-generated method stub
        locationManager.removeUpdates(this);

        Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(viewIntent);

        if (string.toLowerCase().equals(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }
    }
}
