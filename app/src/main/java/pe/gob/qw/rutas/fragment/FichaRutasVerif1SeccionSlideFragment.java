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

import pe.gob.qw.rutas.FichasRutasActivity;
import pe.gob.qw.rutas.R;
import pe.gob.qw.rutas.adapter.InputFilterMinMax;
import pe.gob.qw.rutas.entities.RECORDCARDITEM;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.sqlite.dao.LIBERACIONRUTAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.RECORDCARDITEM_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMFICHASGRABADAS_DAO;
import pe.gob.qw.rutas.util.Utility;

//import android.widget.AdapterView.OnItemSelectedListener;

public class FichaRutasVerif1SeccionSlideFragment extends Fragment implements LocationListener {
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
    private List<RECORDCARDITEM> itens;
    private List<RECORDCARDITEM> listFormat;
    private List<RECORDCARDITEM> listFormat2;

    private List<RECORDCARDITEM> listFormato;

    private String cCategoria="";
    private ListView listaEquipos;
    private int iTipoEstablecimiento=0;
    private ViewGroup mContainerView;
    private LinearLayout llTemp;

    private LinearLayout.LayoutParams lp;
    private int[] array={9};
    private double salto=0;
    private int division = 0;
    private boolean checkboxOn = false;


    //GPS!
    private Location location;
    protected LocationManager locationManager;
    boolean isGPSEnabled = false;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private double latitude=0;
    private double longitude=0;

    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    private List<SMFICHASGRABADAS> listFichas;


    private int sitiodescarga,otrovehi=0;
    private SMFICHASGRABADAS OBJSMFICHASGRABADAS=null;

    public static FichaRutasVerif1SeccionSlideFragment create(int pageNumber, int NUM_PAGES, int NUM_ROWS, int idFichasGrabadas, int idCodFicha, String cCategoria, int iTipoEstablecimiento, String cCodProveedor, String cCodEstablecimiento) {
        FichaRutasVerif1SeccionSlideFragment fragment = new FichaRutasVerif1SeccionSlideFragment();
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

    public FichaRutasVerif1SeccionSlideFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_record_card_slide, container, false);

        OBJSMFICHASGRABADAS =SMFICHASGRABADAS_DAO.Buscar(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
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
        getLocation() ;
        return rootView;
    }

    public void ListaVerificacion2(){
        AddButtonAgregarView();

        itens=RECORDCARDITEM_DAO.ListarSeccionRutasDescarga(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), idFichasGrabadas, array);

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
        getLocation() ;
        listFichas = SMFICHASGRABADAS_DAO.ListarUltima(this.getActivity(),true);


        LinearLayout lnBtn=new LinearLayout(this.getActivity());
        Button btnAgregar2=new Button(this.getActivity());
        btnAgregar2.setBackgroundResource(R.drawable.button_selector);
        lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.setMargins(10, 0, 10, 10);
        //lp.setMargins(50, 0, 150, 50);
        btnAgregar2.setLayoutParams(lp);
        btnAgregar2.setText("AGREGAR");
        btnAgregar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int contarIEE = RECORDCARDITEM_DAO.GetSumaIEERCVerificacion(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), idFichasGrabadas, "T", 0, idCodFicha);
                int IEE = LIBERACIONRUTAS_DAO.TotalIEE(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), idFichasGrabadas);

                int r = Contar() + contarIEE;


                if (r < IEE) {

                    final Dialog dialog = new Dialog(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                    dialog.setTitle("DESCARGA DE PRODUCTOS EN RUTA");
                    dialog.setContentView(R.layout.pop_up_transporte_add2);
                    dialog.setCancelable(false);

                    int id = RECORDCARDITEM_DAO.AgregarTransporte(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),
                            idFichasGrabadas,
                            idCodFicha,
                            cCodProveedor,
                            cCodEstablecimiento);
                    listFormato = RECORDCARDITEM_DAO.ListarFormatoTransporteValores(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),
                            idCodFicha,
                            array,
                            id);

                    LinearLayout pll = (LinearLayout) dialog.findViewById(R.id.linearpadre);

                    final LinearLayout pll2 = (LinearLayout) dialog.findViewById(R.id.linear1);
                    final LinearLayout pll3 = (LinearLayout) dialog.findViewById(R.id.linear2);
                    pll3.setVisibility(View.GONE);

                    for (int i = 0; i < listFormato.size() - 1; i++) {

                        final int posicion = i;
                        final LinearLayout pllh = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                        final TextView titulo = new TextView(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                        final TextView subtitulo = new TextView(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                        final EditText TexBox;
                        final EditText TexBox2 = new EditText(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
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

                                LinearLayout llTempX1 = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(5, 3, 5, 0);
                                llTempX1.setLayoutParams(lp);
                                llTempX1.setOrientation(LinearLayout.HORIZONTAL);
                                llTempX1.setGravity(Gravity.CENTER);
                                llTempX1.setBackgroundColor(Color.parseColor("#1976d2"));

                                LinearLayout llTempY1 = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
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

                                LinearLayout llTempZ1 = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
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
                                                //long millis2 = 1000L * 60L * 60L * 24L * 1L;
                                                // Long.parseLong(str);
                                                Calendar date2 = Calendar.getInstance();
                                                date2.setTime(new Date());
                                                long millis = Long.parseLong(String.valueOf(listFichas.get(0).lFecha));
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
                                        datePickerFragment.show(FichaRutasVerif1SeccionSlideFragment.this.getActivity().getFragmentManager(), "datePicker");
                                    }
                                });

                                TexBox2.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        listFormato.get(posicion).bactivo = true;
                                        listFormato.get(posicion).bcheck = true;
                                        listFormato.get(posicion).vResultado = TexBox2.getText().toString();
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
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
                                final LinearLayout llTempX = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(5, 3, 5, 0);
                                llTempX.setLayoutParams(lp);
                                llTempX.setOrientation(LinearLayout.HORIZONTAL);
                                llTempX.setGravity(Gravity.CENTER);
                                llTempX.setBackgroundColor(Color.parseColor("#1976d2"));

                                LinearLayout llTempY = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(0, 0, 0, 0);
                                llTempY.setLayoutParams(lp);
                                llTempY.setGravity(Gravity.CENTER);
                                llTempY.setOrientation(LinearLayout.VERTICAL);

                                llTempY.addView(titulo);
                                llTempX.addView(llTempY);

                                LinearLayout llTempZ = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(2, 2, 2, 2);
                                llTempZ.setLayoutParams(lp);
                                llTempZ.setOrientation(LinearLayout.VERTICAL);
                                llTempZ.setGravity(Gravity.CENTER);
                                llTempZ.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                TexBox = new EditText(FichaRutasVerif1SeccionSlideFragment.this.getActivity());

                                if (listFormato.get(i).vResultado.toString().trim().equals("")) {
                                    TexBox.setHint("Ingresar");
                                }
                                if (listFormato.get(i).cSeccion.toString().trim().equals("N")) {

                                    TexBox.setInputType(InputType.TYPE_CLASS_NUMBER);
                                   // int maxLength = 3;
                                    //TexBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                                    TexBox.setFilters(new InputFilter[]{new InputFilterMinMax("1", "999")});
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
                                        listFormato.get(posicion).bactivo = true;
                                        listFormato.get(posicion).bcheck = true;
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
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

                                final LinearLayout llTEMPADRE = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                final Spinner Combo = new Spinner(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                final Spinner Combo2 = new Spinner(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(5, 3, 5, 0);
                                llTEMPADRE.setLayoutParams(lp);
                                llTEMPADRE.setOrientation(LinearLayout.VERTICAL);
                                llTEMPADRE.setGravity(Gravity.CENTER);
                                llTEMPADRE.setBackgroundColor(Color.parseColor("#1976d2"));

                                LinearLayout llTempXX = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(5, 3, 5, 0);
                                llTempXX.setLayoutParams(lp);
                                llTempXX.setOrientation(LinearLayout.HORIZONTAL);
                                llTempXX.setGravity(Gravity.CENTER);
                                llTempXX.setBackgroundColor(Color.parseColor("#1976d2"));

                                LinearLayout llTempYY = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(0, 0, 0, 0);
                                llTempYY.setLayoutParams(lp);
                                llTempYY.setGravity(Gravity.CENTER);
                                llTempYY.setOrientation(LinearLayout.VERTICAL);
                                llTempYY.addView(titulo);

                                llTempXX.addView(llTempYY);

                                LinearLayout llTempZZ = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(2, 2, 2, 2);
                                llTempZZ.setLayoutParams(lp);
                                llTempZZ.setOrientation(LinearLayout.VERTICAL);
                                llTempZZ.setGravity(Gravity.CENTER);
                                llTempZZ.setBackgroundColor(Color.parseColor("#FFFFFF"));


                                List<String> list = new ArrayList<String>();
                                list.add("SELECCIONE");
                                list.add("ALMACEN TEMPORAL");
                                list.add("VEHICULO");
                                list.add("CARRETERA");
                                list.add("LOCAL COMUNAL");
                                list.add("OTRO SITIO DE DESCARGA");


                                ArrayAdapter<String> adapterCombo = new ArrayAdapter<String>(FichaRutasVerif1SeccionSlideFragment.this.getActivity()
                                        , android.R.layout.simple_spinner_item, list);
                                adapterCombo.setDropDownViewResource(R.layout.spinner);

                                Combo.setAdapter(adapterCombo);
                                Combo.setSelection(listFormato.get(posicion).iIndexCombo);
                                llTempZZ.addView(Combo);
                                Combo.setGravity(Gravity.CENTER);


                                llTempXX.addView(llTempZZ);
                                //llTempXX.addView(llTempAA);
                                llTEMPADRE.addView(llTempXX);

                                //////TEXTOS
                                final LinearLayout llTempAA = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(5, 3, 5, 0);
                                llTempAA.setLayoutParams(lp);
                                llTempAA.setOrientation(LinearLayout.HORIZONTAL);
                                llTempAA.setGravity(Gravity.CENTER);
                                llTempAA.setBackgroundColor(Color.parseColor("#1976d2"));

                                LinearLayout llTempBB = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(0, 0, 0, 0);
                                llTempBB.setLayoutParams(lp);
                                llTempBB.setGravity(Gravity.CENTER);
                                llTempBB.setOrientation(LinearLayout.VERTICAL);
                                final TextView prueba = new TextView(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                prueba.setText("TIPO DE VEHICULO");
                                prueba.setTextSize(14);
                                prueba.setPadding(5, 5, 5, 5);
                                prueba.setTypeface(null, Typeface.BOLD);
                                prueba.setGravity(Gravity.CENTER);
                                prueba.setTextColor(Color.parseColor("#FFFFFF"));
                                prueba.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                llTempBB.addView(prueba);
                                llTempAA.addView(llTempBB);

                                LinearLayout llTempCC = new LinearLayout(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                                lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                                lp.setMargins(2, 2, 2, 2);
                                llTempCC.setLayoutParams(lp);
                                llTempCC.setOrientation(LinearLayout.VERTICAL);
                                llTempCC.setGravity(Gravity.CENTER);
                                llTempCC.setBackgroundColor(Color.parseColor("#FFFFFF"));

                                List<String> list2 = new ArrayList<String>();
                                list2.add("SELECCIONE");
                                list2.add("CAMION");
                                list2.add("FURGON,FURGONETA");
                                list2.add("CAMIONETA");
                                list2.add("AUTO");
                                list2.add("COMBI,CUSTER,MINIVAN");
                                list2.add("TRIMOTO,MOTOCAR,MOTOTAXI");
                                list2.add("TRICICLO");
                                list2.add("ACEMILA");
                                list2.add("CON MOTOR:BOTE ,LANCHA,ETC");
                                list2.add("SIN MOTOR:CANOA,BALSA.ETC");
                                list2.add("AVION,AVIONETA");
                                list2.add("HELICOPTERO");
                                list2.add("OTRO TIPO DE VEHICULO");


                                // Create the ArrayAdapter

                                ArrayAdapter<String> adapterCombo2 = new ArrayAdapter<String>(FichaRutasVerif1SeccionSlideFragment.this.getActivity()
                                        , android.R.layout.simple_spinner_item, list2);
                                adapterCombo2.setDropDownViewResource(R.layout.spinner);

                                Combo2.setAdapter(adapterCombo2);
                                llTempCC.addView(Combo2);

                                llTempAA.addView(llTempCC);
                                llTEMPADRE.addView(llTempAA);
                                pllh.addView(llTEMPADRE);


                                Combo2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView,
                                                               View view, int pos, long id) {
                                        // TODO Auto-generated method stub


                                        if (pos == 0) {
                                            otrovehi = 0;
                                        } else if (pos == 13) {
                                            pll3.setVisibility(View.VISIBLE);
                                            otrovehi = 13;
                                            listFormato.get(listFormato.size() - 1).bactivo = true;
                                            listFormato.get(listFormato.size() - 1).bcheck = true;
                                            listFormato.get(posicion).vObservacion = Combo2.getSelectedItem().toString();
                                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                        } else {
                                            pll3.setVisibility(View.GONE);
                                            otrovehi = pos;
                                            listFormato.get(listFormato.size() - 1).bactivo = true;
                                            listFormato.get(listFormato.size() - 1).bcheck = true;
                                            listFormato.get(posicion).vObservacion = Combo2.getSelectedItem().toString();
                                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                        }
                                    }

                                    // If no option selected
                                    public void onNothingSelected(AdapterView<?> arg0) {
                                        // TODO Auto-generated method stub
                                    }
                                });


                                Combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView,
                                                               View view, int pos, long id) {
                                        // TODO Auto-generated method stub
                                        if (pos == 0) {
                                            sitiodescarga = 0;
                                        } else if (pos == 1) {
                                            pll3.setVisibility(View.VISIBLE);
                                            llTempAA.setVisibility(View.GONE);
                                            sitiodescarga = 1;
                                            listFormato.get(posicion).bcheck = true;
                                            listFormato.get(posicion).bactivo = true;
                                            listFormato.get(posicion).vResultado = Combo.getSelectedItem().toString();
                                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                        } else if (pos == 2) {
                                            llTempAA.setVisibility(View.VISIBLE);
                                            pll3.setVisibility(View.GONE);
                                            sitiodescarga = 2;
                                            listFormato.get(posicion).bcheck = true;
                                            listFormato.get(posicion).bactivo = true;
                                            listFormato.get(posicion).vResultado = Combo.getSelectedItem().toString();
                                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                        }
                                        else if (pos == 3) {
                                            pll3.setVisibility(View.VISIBLE);
                                            llTempAA.setVisibility(View.GONE);
                                            sitiodescarga = 3;
                                            listFormato.get(posicion).bcheck = true;
                                            listFormato.get(posicion).bactivo = true;
                                            listFormato.get(posicion).vResultado = Combo.getSelectedItem().toString();
                                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                        }
                                        else if (pos == 4) {
                                            pll3.setVisibility(View.VISIBLE);
                                            llTempAA.setVisibility(View.GONE);
                                            sitiodescarga = 4;
                                            listFormato.get(posicion).bcheck = true;
                                            listFormato.get(posicion).bactivo = true;
                                            listFormato.get(posicion).vResultado = Combo.getSelectedItem().toString();
                                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                        }


                                        else if (pos == 5) {
                                            pll3.setVisibility(View.VISIBLE);
                                            llTempAA.setVisibility(View.GONE);
                                            sitiodescarga = 5;
                                            listFormato.get(posicion).bcheck = true;
                                            listFormato.get(posicion).bactivo = true;
                                            listFormato.get(posicion).vResultado = Combo.getSelectedItem().toString();
                                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                        } else {
                                            llTempAA.setVisibility(View.GONE);
                                            pll3.setVisibility(View.GONE);
                                            sitiodescarga = pos;
                                            listFormato.get(posicion).bcheck = true;
                                            listFormato.get(posicion).bactivo = true;
                                            listFormato.get(posicion).vResultado = Combo.getSelectedItem().toString();
                                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(posicion));
                                        }

                                    }

                                    public void onNothingSelected(AdapterView<?> arg0) {
                                        // TODO Auto-generated method stub
                                    }
                                });

                                break;

                        }
                        if (posicion == 4) {
                            pll3.setOrientation(LinearLayout.VERTICAL);
                            pll3.addView(pllh);

                        } else {

                            pll2.setOrientation(LinearLayout.VERTICAL);
                            pll2.addView(pllh);
                        }

                    }

                    final Button buttonAcept = (Button) dialog.findViewById(R.id.buttonAcept);
                    buttonAcept.setTag(id);
                    buttonAcept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String latitud = String.valueOf(latitude);
                            String longitud = String.valueOf(longitude);
                            if (latitud != "0.0" || longitud != "0.0") {
                                listFormato.get(listFormato.size() - 1).vResultado = String.valueOf(latitude);
                                listFormato.get(listFormato.size() - 1).vObservacion = String.valueOf(longitude);
                                listFormato.get(listFormato.size() - 1).bactivo = true;
                                listFormato.get(listFormato.size() - 1).bcheck = true;
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));

                                listFormato.get(0).vResultado=OBJSMFICHASGRABADAS.vPlaca.toUpperCase().toString();
                                listFormato.get(0).vObservacion="CONFORME";
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(0));

                                int contarIEE = RECORDCARDITEM_DAO.GetSumaIEERCVerificacion(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), idFichasGrabadas, "T", 0, idCodFicha);
                                int IEE = LIBERACIONRUTAS_DAO.TotalIEE(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), idFichasGrabadas);

                                if (sitiodescarga == 0) {
                                    Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "Debes Seleccionar sitio de Descarga de Productos ", true);
                                } else {
                                    int camposvacios = RECORDCARDITEM_DAO.CantidadCamposVaciosRecordCardItemVerificacion(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),
                                            idCodFicha, array, Integer.parseInt(String.valueOf(buttonAcept.getTag())));
                                    if (sitiodescarga == 1 || sitiodescarga==3 || sitiodescarga==4) {
                                        if (camposvacios == 0) {

                                            int r = Contar() + contarIEE;
                                            if (r <= IEE) {
                                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));
                                                dialog.dismiss();


                                                ListaVerificacionUpdate();
                                                Intent intent = new Intent(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),FichasRutasActivity.class);
                                                startActivity(intent);
                                                FichaRutasVerif1SeccionSlideFragment.this.getActivity().finish();


                                            } else {
                                                Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "El numero de IEE no puede exceder el total Establecido", true);
                                            }
                                        } else {
                                            Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "Faltan llenar " + camposvacios + " campos.", true);
                                        }
                                    } else if (sitiodescarga == 2) {
                                        if (otrovehi == 0) {
                                            Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "Debes Seleccionar un tipo de Vehiculo", true);
                                        } else if (otrovehi == 13) {
                                            int r = Contar() + contarIEE;
                                            if (camposvacios == 0) {
                                                if (r  <= IEE) {
                                                    RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));
                                                    dialog.dismiss();
                                                    ListaVerificacionUpdate();
                                                    Intent intent = new Intent(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),FichasRutasActivity.class);
                                                    startActivity(intent);
                                                    FichaRutasVerif1SeccionSlideFragment.this.getActivity().finish();
                                                } else {
                                                    Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "El numero de IEE no puede exceder el total Establecido", true);
                                                }
                                            } else {
                                                Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "Debes especificar que tipo de vehiculo se usara en descarga", true);
                                            }
                                        } else {
                                            if (camposvacios == 0 || camposvacios == 1) {
                                                int r = Contar() + contarIEE;
                                                if (r  <= IEE) {
                                                    listFormato.get(4).vResultado = "NO APLICA";
                                                    RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(4));
                                                    RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));
                                                    dialog.dismiss();
                                                    ListaVerificacionUpdate();
                                                    Intent intent = new Intent(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),FichasRutasActivity.class);
                                                    startActivity(intent);
                                                    FichaRutasVerif1SeccionSlideFragment.this.getActivity().finish();
                                                } else {
                                                    Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "El numero de IEE no puede exceder el total Establecido", true);
                                                }
                                            } else {
                                                Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "Faltan llenar " + camposvacios + " campos.", true);
                                            }
                                        }
                                    } else if (sitiodescarga == 3 || sitiodescarga == 4) {

                                        if (camposvacios < 2) {
                                            int r = Contar() + contarIEE;
                                            if (r  <= IEE) {
                                                listFormato.get(4).vResultado = "NO APLICA";
                                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(4));
                                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));
                                                dialog.dismiss();
                                                ListaVerificacionUpdate();
                                                Intent intent = new Intent(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),FichasRutasActivity.class);
                                                startActivity(intent);
                                                FichaRutasVerif1SeccionSlideFragment.this.getActivity().finish();
                                            } else {
                                                Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "El numero de IEE no puede exceder el total Establecido", true);
                                            }
                                        } else {
                                            Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "Faltan llenar " + camposvacios + " campos.", true);
                                        }
                                    } else {
                                        if (camposvacios == 0) {
                                            int r = Contar() + contarIEE;
                                            if (r  <= IEE) {
                                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), listFormato.get(listFormato.size() - 1));
                                                dialog.dismiss();
                                                ListaVerificacionUpdate();
                                                Intent intent = new Intent(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),FichasRutasActivity.class);
                                                startActivity(intent);
                                                FichaRutasVerif1SeccionSlideFragment.this.getActivity().finish();
                                            } else {
                                                Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "El numero de IEE no puede exceder el total Establecido", true);
                                            }
                                        } else {
                                            Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "Faltan llenar " + camposvacios + " campos.", true);
                                        }
                                    }
                                }
                            } else {
                                Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "Las Coordenadas no son correctas", true);
                            }
                        }
                    });
                    final Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
                    buttonCancel.setTag(id);
                    buttonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RECORDCARDITEM_DAO.EliminarRegistroVerificacion(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),
                                    idCodFicha, array, Integer.parseInt(String.valueOf(buttonCancel.getTag())));
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            else{

                    Utility.alert(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), "Ya no se puede agregar mas IEE porque excediste la capacidad", true);

            }
            }


        });

        lnBtn.addView(btnAgregar2);
        lnBtn.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        lnBtn.setOrientation(LinearLayout.HORIZONTAL);
        mContainerView.addView(lnBtn);
    }


    private int Contar(){
        int resultado=RECORDCARDITEM_DAO.GetSumaIEERC(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),idFichasGrabadas,"N",2,30);
        return resultado;

    }




  /*  private void addItem3(int[] position) {

        // Instantiate a new "row" view.
        final int[] posicion=position;

        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this.getActivity()).inflate(
                R.layout.dinamico, mContainerView, false);
        LinearLayout ll = (LinearLayout)newView.findViewById(R.id.linear);
        LinearLayout llf=new LinearLayout(this.getActivity());
        LinearLayout llr=new LinearLayout(this.getActivity());

        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        LinearLayout llh1=new LinearLayout(this.getActivity());
        final TextView titulo= new TextView(this.getActivity());
        final TextView descripcion= new TextView(this.getActivity());
        titulo.setText(itens.get(position[1]).vDetalleSeccion);
        descripcion.setText(itens.get(position[1]).vResultado);
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
        //titulo2.setText(itens.get(position[position.length - 1]).vDetalleSeccion);
        //descripcion2.setText(itens.get(position[position.length - 1]).vResultado);

        titulo2.setText("SITIO DE DESCARGA");
        descripcion2.setText(itens.get(position[position.length-4]).vResultado);


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
        llf.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        llf.setOrientation(LinearLayout.VERTICAL);
        //llr.setBackgroundResource(R.drawable.fondo);
        //llr.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        //llr.setOrientation(LinearLayout.VERTICAL);
        //llr.setGravity(Gravity.CENTER);

        ll.addView(llf);
        //ll.addView(llr);
        ll.setBackgroundResource(R.drawable.fondo);
        ll.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.HORIZONTAL);
        mContainerView.addView(newView);//, position[i]);

    }
*/


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

        titulo.setText(itens.get(position[1]).vDetalleSeccion);
        descripcion.setText(itens.get(position[1]).vResultado);
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
        titulo2.setText("SITIO DE DESCARGA");
        descripcion2.setText(itens.get(position[position.length-4]).vResultado);

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




        LinearLayout llh3=new LinearLayout(this.getActivity());

        final TextView titulo3= new TextView(this.getActivity());
        final TextView descripcion3= new TextView(this.getActivity());
        titulo3.setText("NUMERO DE IEES");
        descripcion3.setText(itens.get(position[position.length-3]).vResultado);

        titulo3.setBackgroundColor((Color.parseColor("#1976d2")));
        titulo3.setWidth(division * 4);
        titulo3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        titulo3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        titulo3.setTextColor(Color.WHITE);
        titulo3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        titulo3.setGravity(Gravity.CENTER);
        llh3.addView(titulo3);

        descripcion3.setBackgroundColor((Color.parseColor("#FFFFFF")));
        descripcion3.setWidth(division * 5);
        descripcion3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        descripcion3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        descripcion3.setTextColor(Color.BLACK);
        descripcion3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
        descripcion3.setGravity(Gravity.CENTER);
        llh3.addView(descripcion3);

        llh1.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        llh1.setOrientation(LinearLayout.HORIZONTAL);
        llh2.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        llh2.setOrientation(LinearLayout.HORIZONTAL);
        llh3.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        llh3.setOrientation(LinearLayout.HORIZONTAL);

        llf.addView(llh1);
        llf.addView(llh2);
        llf.addView(llh3);



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

                AlertDialog.Builder alert = new AlertDialog.Builder(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
                alert.setTitle("¡¡ELIMINAR REGISTRO!!");
                alert.setMessage("¿Seguro que deseas eliminar este registro??");
                alert.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        RECORDCARDITEM_DAO.EliminarRegistroVerificacion(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),
                                idCodFicha,array, Integer.parseInt(String.valueOf(num))
                        );
                        ListaVerificacionUpdate();
                        Intent intent = new Intent(FichaRutasVerif1SeccionSlideFragment.this.getActivity(),FichasRutasActivity.class);
                        startActivity(intent);
                        FichaRutasVerif1SeccionSlideFragment.this.getActivity().finish();
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
        mContainerView = (ViewGroup) FichaRutasVerif1SeccionSlideFragment.this.getActivity().findViewById(R.id.container);
        mContainerView.removeViews(0, mContainerView.getChildCount());

         //       array,
        //        idFichasGrabadas)<5) {
            AddButtonAgregarView();
        //}

        itens=RECORDCARDITEM_DAO.ListarSeccionRutasDescarga(FichaRutasVerif1SeccionSlideFragment.this.getActivity(), idFichasGrabadas, array);
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
            locationManager = (LocationManager) FichaRutasVerif1SeccionSlideFragment.this.getActivity()
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
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(FichaRutasVerif1SeccionSlideFragment.this.getActivity());
            alertDialog.setTitle("GPS desactivado");

            alertDialog.setMessage("Por favor acceda a configuración de GPS y actívelo para el registro, se cerrará la aplicación");

            alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    FichaRutasVerif1SeccionSlideFragment.this.getActivity().startActivity(intent);
                    ((Activity) FichaRutasVerif1SeccionSlideFragment.this.getActivity()).finish();
                }
            });
            alertDialog.show();
        }
        else{
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            FichaRutasVerif1SeccionSlideFragment.this.getActivity().startActivity(intent);
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
