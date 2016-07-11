package pe.gob.qw.rutas;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.gob.qw.rutas.entities.RECORDCARDITEM;
import pe.gob.qw.rutas.entities.RUTASCOLEGIOS;
import pe.gob.qw.rutas.entities.SECCIONFICHAGRABADA;
import pe.gob.qw.rutas.entities.SMDETFOTOSRUTAS;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.fragment.DatePickerFragment;
import pe.gob.qw.rutas.sqlite.dao.RECORDCARDITEM_DAO;
import pe.gob.qw.rutas.sqlite.dao.RUTASCOLEGIOS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMDETFOTOSRUTAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMFICHASGRABADAS_DAO;
import pe.gob.qw.rutas.util.Utility;

public class FichaRutasVerif3CardActivity extends Activity implements LocationListener {
    private int id;
    private Button buttonAcept, buttonCancel, bt_hacerfoto;
    private SMFICHASGRABADAS OBJSMFICHASGRABADAS = null;
    private SECCIONFICHAGRABADA OBJSECCIONFICHAGRABADA = null;
    private int[] array2 = {9};
    private List<RECORDCARDITEM> listFormato;
    private LinearLayout.LayoutParams lp;
    private int[] array = {11};
    private int division = 0;
    public String MODULAR="";
    private Date fechaFoto;
    private ViewGroup mContainerView;
    private List<RECORDCARDITEM> itens;
    private boolean paginacion=false;

    //GPS!
    private Location location;
    protected LocationManager locationManager;
    boolean isGPSEnabled = false;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 15;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private double latitude=0;
    private double longitude=0;
    private List<String> imageIDs=null;


    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    private List<SMFICHASGRABADAS> listFichas;

    public List listcarros=new ArrayList<String>();

    //spinners

    public int spanexo=0,spcodigo=0,spentrega=0,spmotivo=0,spplazo=0,spestado=0,spacta=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_rutas_verif3_card);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setTitle("VERIFICACION DE LA DISTRIBUCION EN IES ");
        getLocation();
        lp = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        lp.setMargins(10, 0, 10, 10);
        OBJSMFICHASGRABADAS = SMFICHASGRABADAS_DAO.Buscar(this);
        //OBJSECCIONFICHAGRABADA = new SECCIONFICHAGRABADA();
        //OBJSECCIONFICHAGRABADA.idFichasGrabadas = OBJSMFICHASGRABADAS.idFichasGrabadas;
        //OBJSECCIONFICHAGRABADA.iCodFicha = OBJSMFICHASGRABADAS.iCodFicha;
        //OBJSECCIONFICHAGRABADA.iCodTipoSeccion = array[0];
       // OBJSECCIONFICHAGRABADA = SECCIONFICHAGRABADA_DAO.Obtener(this, OBJSECCIONFICHAGRABADA);




        id = RECORDCARDITEM_DAO.AgregarTransporte3(this,
                OBJSMFICHASGRABADAS.idFichasGrabadas,
                OBJSMFICHASGRABADAS.iCodFicha,
                OBJSMFICHASGRABADAS.cCodProveedor,
                OBJSMFICHASGRABADAS.cCodEstablecimiento);


        listFormato = RECORDCARDITEM_DAO.ListarFormatoTransporteValores(this,
                OBJSMFICHASGRABADAS.iCodFicha,
                array,
                id);

        bt_hacerfoto = (Button) findViewById(R.id.btnFotog);
        bt_hacerfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r = SMDETFOTOSRUTAS_DAO.VerificarFoto(FichaRutasVerif3CardActivity.this, OBJSMFICHASGRABADAS.idFichasGrabadas, MODULAR);
                if (r == 0) {
                    fechaFoto = new Date();
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri uriSavedImage = Uri.fromFile(Utility.SetImage(MODULAR));
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    Utility.alert(FichaRutasVerif3CardActivity.this, "Ya se tomo la Fotografia", true);
                }
            }
        });

        buttonAcept = (Button) findViewById(R.id.buttonAcept);
        buttonAcept.setTag(id);
        buttonAcept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                //String latitud = String.valueOf(latitude);
                //String longitud = String.valueOf(longitude);
                Double latitud = latitude;
                Double longitud=longitude;

                int existecolegio=RECORDCARDITEM_DAO.ContarColegios(FichaRutasVerif3CardActivity.this,OBJSMFICHASGRABADAS.idFichasGrabadas,"M",2,OBJSMFICHASGRABADAS.iCodFicha,MODULAR);

                if(existecolegio==1){
                    if (latitud !=  0.0 && longitud != 0.0) {
                        listFormato.get(listFormato.size() - 1).vResultado = String.valueOf(latitude);
                        listFormato.get(listFormato.size() - 1).vObservacion = String.valueOf(longitude);
                        listFormato.get(listFormato.size() - 1).bactivo=true;
                        listFormato.get(listFormato.size() - 1).bcheck=true;
                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(listFormato.size() - 1));

                        listFormato.get(0).vResultado=OBJSMFICHASGRABADAS.vPlaca.toUpperCase().toString();
                        listFormato.get(0).vObservacion="CONFORME";
                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(0));

                        int r = SMDETFOTOSRUTAS_DAO.VerificarFoto(FichaRutasVerif3CardActivity.this, OBJSMFICHASGRABADAS.idFichasGrabadas, MODULAR);
                        if (r > 0) {
                            int camposvacios = RECORDCARDITEM_DAO.CantidadCamposVaciosRecordCardItemVerificacion(FichaRutasVerif3CardActivity.this,
                                    OBJSMFICHASGRABADAS.iCodFicha, array, Integer.parseInt(String.valueOf(buttonAcept.getTag())));

                            if(spanexo==0  ||spentrega==0)
                            {Utility.alert(FichaRutasVerif3CardActivity.this, "Falta seleccionar Opciones,Verifique", true);}
                            else
                            {
                                if(spentrega==1){
                                    if(spplazo==0 || spestado==0 || spacta==0)
                                    {Utility.alert(FichaRutasVerif3CardActivity.this, "Falta seleccionar Opciones de Verificacion", true);}
                                    else
                                    {
                                        if (camposvacios == 0) {
                                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(listFormato.size() - 1));
                                            //Intent intent = new Intent(FichaRutasVerif3CardActivity.this,FichasRutasActivity.class);
                                           //startActivity(intent);
                                            //startActivity(getIntent());
                                            FichaRutasVerif3CardActivity.this.finish();
                                        } else {
                                            Utility.alert(FichaRutasVerif3CardActivity.this, "Faltan llenar " + camposvacios + " campos.", true);
                                        }
                                    }
                                }
                                else
                                {
                                    if(spmotivo==0){
                                        Utility.alert(FichaRutasVerif3CardActivity.this, "Falta seleccionar Motivo", true);
                                    }
                                    else
                                    {
                                        if (camposvacios == 0) {
                                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(listFormato.size() - 1));
                                            //Intent intent = new Intent(FichaRutasVerif3CardActivity.this,FichasRutasActivity.class);
                                            //startActivity(intent);
                                            FichaRutasVerif3CardActivity.this.finish();
                                        } else {
                                            Utility.alert(FichaRutasVerif3CardActivity.this, "Faltan llenar " + camposvacios + " campos.", true);
                                        }
                                    }
                                }
                            }
                        } else {
                            Utility.alert(FichaRutasVerif3CardActivity.this, "Falta tomar Fotografia", true);
                        }
                    } else {
                        Utility.alert(FichaRutasVerif3CardActivity.this, "Las Coordenadas no son correctas", true);
                    }
                }
                else
                {
                    Utility.alert(FichaRutasVerif3CardActivity.this, "Este Colegio ya se Verifico", true);
                }

            }
        });
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setTag(id);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int modulos = RECORDCARDITEM_DAO.ContarColegios(FichaRutasVerif3CardActivity.this,OBJSMFICHASGRABADAS.idFichasGrabadas,"M",2,OBJSMFICHASGRABADAS.iCodFicha,MODULAR);
                if (modulos>1)
                {
                    RECORDCARDITEM_DAO.EliminarRegistroVerificacion(FichaRutasVerif3CardActivity.this,
                            OBJSMFICHASGRABADAS.iCodFicha, array, Integer.parseInt(String.valueOf(buttonCancel.getTag())));
                    //Intent intent = new Intent(FichaRutasVerif3CardActivity.this,FichasRutasActivity.class);
                    //startActivity(intent);
                    FichaRutasVerif3CardActivity.this.finish();
                }
                else
                {
                    RECORDCARDITEM_DAO.EliminarRegistroVerificacion(FichaRutasVerif3CardActivity.this,
                            OBJSMFICHASGRABADAS.iCodFicha, array, Integer.parseInt(String.valueOf(buttonCancel.getTag())));
                    SMDETFOTOSRUTAS_DAO.BorrarFoto(FichaRutasVerif3CardActivity.this,OBJSMFICHASGRABADAS.idFichasGrabadas,MODULAR);
                    //Intent intent = new Intent(FichaRutasVerif3CardActivity.this,FichasRutasActivity.class);
                    //startActivity(intent);
                    FichaRutasVerif3CardActivity.this.finish();
                }
            }
        });

        Agregar();
    }

    public void Agregar() {
        listFichas = SMFICHASGRABADAS_DAO.ListarUltima(FichaRutasVerif3CardActivity.this, true);
        listcarros.add("S1");
        int pl = RECORDCARDITEM_DAO.ContarTransbordos(this, OBJSMFICHASGRABADAS.idFichasGrabadas, "", 10, OBJSMFICHASGRABADAS.iCodFicha);
        for (int a = 1; a <= pl; a++) {
            listcarros.add("T" + a);
        }
        ArrayAdapter<String> adapterComboV = new ArrayAdapter<String>(FichaRutasVerif3CardActivity.this
                , android.R.layout.simple_spinner_item, listcarros);
        final LinearLayout pll = (LinearLayout) this.findViewById(R.id.linear1);
        final LinearLayout pll2 = (LinearLayout) this.findViewById(R.id.linear2);
        final LinearLayout pll3 = (LinearLayout) this.findViewById(R.id.linearfoto);
        final LinearLayout pll4 = (LinearLayout) this.findViewById(R.id.linear3);
        final LinearLayout pll5 = (LinearLayout) this.findViewById(R.id.linear4);
        pll3.setVisibility(View.GONE);
        pll5.setVisibility(View.GONE);
        final EditText TexBox3 = new EditText(FichaRutasVerif3CardActivity.this);
        TexBox3.setText("");
        for (int i = 0; i < listFormato.size() - 1; i++) {
            final int posicion = i;
            final LinearLayout pllh = new LinearLayout(FichaRutasVerif3CardActivity.this);
            final TextView titulo = new TextView(FichaRutasVerif3CardActivity.this);
            final TextView subtitulo = new TextView(FichaRutasVerif3CardActivity.this);
            final EditText TexBox;
            final EditText TexBox2 = new EditText(FichaRutasVerif3CardActivity.this);
            final EditText motivo = (EditText) findViewById(R.id.txtotromotivo);
            final AutoCompleteTextView autotexto;

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
                    LinearLayout llTempX1 = new LinearLayout(FichaRutasVerif3CardActivity.this);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 3, 5, 0);
                    llTempX1.setLayoutParams(lp);
                    llTempX1.setOrientation(LinearLayout.HORIZONTAL);
                    llTempX1.setGravity(Gravity.CENTER);
                    llTempX1.setBackgroundColor(Color.parseColor("#1976d2"));

                    LinearLayout llTempY1 = new LinearLayout(FichaRutasVerif3CardActivity.this);
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

                    LinearLayout llTempZ1 = new LinearLayout(FichaRutasVerif3CardActivity.this);
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
                            datePickerFragment.show(FichaRutasVerif3CardActivity.this.getFragmentManager(), "datePicker");
                        }
                    });

                    TexBox2.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            listFormato.get(posicion).bactivo = true;
                            listFormato.get(posicion).bcheck = true;
                            listFormato.get(posicion).vResultado = TexBox2.getText().toString();
                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));

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

                    final LinearLayout llTempX = new LinearLayout(FichaRutasVerif3CardActivity.this);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 3, 5, 0);
                    llTempX.setLayoutParams(lp);
                    llTempX.setOrientation(LinearLayout.HORIZONTAL);
                    llTempX.setGravity(Gravity.CENTER);
                    llTempX.setBackgroundColor(Color.parseColor("#1976d2"));

                    LinearLayout llTempY = new LinearLayout(FichaRutasVerif3CardActivity.this);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                    lp.setMargins(0, 0, 0, 0);
                    llTempY.setLayoutParams(lp);
                    llTempY.setGravity(Gravity.CENTER);
                    llTempY.setOrientation(LinearLayout.VERTICAL);

                    llTempY.addView(titulo);
                    llTempX.addView(llTempY);

                    LinearLayout llTempZ = new LinearLayout(FichaRutasVerif3CardActivity.this);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                    lp.setMargins(2, 2, 2, 2);
                    llTempZ.setLayoutParams(lp);
                    llTempZ.setOrientation(LinearLayout.VERTICAL);
                    llTempZ.setGravity(Gravity.CENTER);
                    llTempZ.setBackgroundColor(Color.parseColor("#FFFFFF"));


                    autotexto=new AutoCompleteTextView(FichaRutasVerif3CardActivity.this);

                    TexBox = new EditText(FichaRutasVerif3CardActivity.this);
                    //
                    if (listFormato.get(i).cSeccion.toString().trim().equals("N")) {
                        TexBox3.setEnabled(false);
                        TexBox3.setTextColor(Color.parseColor("#000000"));
                        TexBox3.setTextSize(15);
                        TexBox3.setTypeface(null, Typeface.BOLD);
                        TexBox3.setGravity(Gravity.CENTER);
                        llTempZ.addView(TexBox3);
                    } else if (listFormato.get(i).cSeccion.toString().trim().equals("M")) {
                        autotexto.setInputType(InputType.TYPE_CLASS_NUMBER);
                        int maxLength = 7;
                        autotexto.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                        autotexto.setTextColor(Color.parseColor("#000000"));
                        autotexto.setTextSize(15);
                        autotexto.setTypeface(null, Typeface.BOLD);
                        autotexto.setGravity(Gravity.CENTER);
                        llTempZ.addView(autotexto);
                    } else {
                        if (listFormato.get(i).vResultado.toString().trim().equals("")) {
                            TexBox.setHint("Ingresar");
                        }
                        int maxLength = 8;
                        TexBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                        TexBox.setTextColor(Color.parseColor("#000000"));
                        TexBox.setTextSize(15);
                        TexBox.setTypeface(null, Typeface.BOLD);
                        TexBox.setGravity(Gravity.CENTER);
                        llTempZ.addView(TexBox);
                    }
                    autotexto.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String nomcolegio = autotexto.getText().toString().trim();
                                if(nomcolegio.length()<7)
                                {
                                    List<RUTASCOLEGIOS> entidad =  RUTASCOLEGIOS_DAO.BuscarModular(FichaRutasVerif3CardActivity.this,
                                            autotexto.getText().toString().trim());
                                    ArrayAdapter<RUTASCOLEGIOS> adapter = new ArrayAdapter<RUTASCOLEGIOS>(FichaRutasVerif3CardActivity.this,
                                            android.R.layout.simple_dropdown_item_1line, entidad);
                                    autotexto.setAdapter(adapter);
                                }
                                else
                                {
                                    MODULAR = autotexto.getText().toString();
                                String Cole = RECORDCARDITEM_DAO.Colegio(FichaRutasVerif3CardActivity.this, nomcolegio);
                                TexBox3.setText(Cole);
                                if (Cole.equals("")) {
                                    pll3.setVisibility(View.GONE);
                                    listFormato.get(posicion).bcheck = false;
                                    listFormato.get(posicion).bactivo = false;
                                    RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                                } else {
                                    pll3.setVisibility(View.VISIBLE);
                                    //MODULAR = listFormato.get(2).vResultado.toString().trim();
                                    listFormato.get(3).vResultado = TexBox3.getText().toString();
                                    listFormato.get(3).vObservacion = "";
                                    listFormato.get(3).bactivo = true;
                                    listFormato.get(3).bcheck = true;
                                    RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(3));
                                    listFormato.get(posicion).vResultado = autotexto.getText().toString();
                                    listFormato.get(posicion).bcheck = true;
                                    listFormato.get(posicion).bactivo = true;
                                    RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                                }

                            }

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
// PARAMETROS PARA LAYOUTS HASTA LA OPCION SI O NO
                    final LinearLayout llTEMPADRE = new LinearLayout(FichaRutasVerif3CardActivity.this);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 3, 5, 0);
                    llTEMPADRE.setLayoutParams(lp);
                    llTEMPADRE.setOrientation(LinearLayout.VERTICAL);
                    llTEMPADRE.setGravity(Gravity.CENTER);
                    llTEMPADRE.setBackgroundColor(Color.parseColor("#1976d2"));

                    LinearLayout llTempXX = new LinearLayout(FichaRutasVerif3CardActivity.this);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 3, 5, 0);
                    llTempXX.setLayoutParams(lp);
                    llTempXX.setOrientation(LinearLayout.HORIZONTAL);
                    llTempXX.setGravity(Gravity.CENTER);
                    llTempXX.setBackgroundColor(Color.parseColor("#1976d2"));

                    LinearLayout llTempYY = new LinearLayout(FichaRutasVerif3CardActivity.this);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                    lp.setMargins(0, 0, 0, 0);
                    llTempYY.setLayoutParams(lp);
                    llTempYY.setGravity(Gravity.CENTER);
                    llTempYY.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout llTempZZ = new LinearLayout(FichaRutasVerif3CardActivity.this);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                    lp.setMargins(2, 2, 2, 2);
                    llTempZZ.setLayoutParams(lp);
                    llTempZZ.setOrientation(LinearLayout.VERTICAL);
                    llTempZZ.setGravity(Gravity.CENTER);
                    llTempZZ.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    //PARAMETROS PARA LAYOUTS HASTA DESPUES DE OPCION SI O NO

                    final LinearLayout llTempAA = new LinearLayout(FichaRutasVerif3CardActivity.this);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5, 3, 5, 0);
                    llTempAA.setLayoutParams(lp);
                    llTempAA.setOrientation(LinearLayout.HORIZONTAL);
                    llTempAA.setGravity(Gravity.CENTER);
                    llTempAA.setBackgroundColor(Color.parseColor("#1976d2"));


                    LinearLayout llTempBB = new LinearLayout(FichaRutasVerif3CardActivity.this);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                    lp.setMargins(0, 0, 0, 0);
                    llTempBB.setLayoutParams(lp);
                    llTempBB.setGravity(Gravity.CENTER);
                    llTempBB.setOrientation(LinearLayout.VERTICAL);


                    LinearLayout llTempCC = new LinearLayout(FichaRutasVerif3CardActivity.this);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                    lp.setMargins(2, 2, 2, 2);
                    llTempCC.setLayoutParams(lp);
                    llTempCC.setOrientation(LinearLayout.VERTICAL);
                    llTempCC.setGravity(Gravity.CENTER);
                    llTempCC.setBackgroundColor(Color.parseColor("#FFFFFF"));


                    final Spinner Combo = new Spinner(FichaRutasVerif3CardActivity.this);
                    final Spinner Combo2 = new Spinner(FichaRutasVerif3CardActivity.this);
                    final Spinner Combo3 = new Spinner(FichaRutasVerif3CardActivity.this);

                    final Spinner Combo4 = new Spinner(FichaRutasVerif3CardActivity.this);
                    final Spinner Combo5 = new Spinner(FichaRutasVerif3CardActivity.this);
                    final Spinner Combo6 = new Spinner(FichaRutasVerif3CardActivity.this);
                    final Spinner Combo7 = new Spinner(FichaRutasVerif3CardActivity.this);


                    List<String> list = new ArrayList<String>();
                    if (listFormato.get(posicion).cSeccion.trim().equals("A")) {
                        list.add("SELECCIONE");
                        list.add("0");
                        list.add("1");
                        list.add("2");
                    }
                    if (listFormato.get(posicion).cSeccion.trim().equals("E")) {
                        list.add("SELECCIONE");
                        list.add("SI");
                        list.add("NO");
                    }
                    if (listFormato.get(posicion).cSeccion.trim().equals("M")) {
                        list.add("SELECCIONE");
                        list.add("Código modular de la IIEE no corresponde");
                        list.add("Local de la IIEE se encuentra cerrado");
                        list.add("No se encuentra el CAE o persona responsable de la recepción");
                        list.add("Otro motivo");


                    }
                    if (listFormato.get(posicion).cSeccion.trim().equals("P")) {
                        list.add("SELECCIONE");
                        list.add("CONFORME");
                        list.add("NO CONFORME");
                    }
                    if (listFormato.get(posicion).cSeccion.trim().equals("S")) {
                        list.add("SELECCIONE");
                        list.add("CONFORME");
                        list.add("NO CONFORME");
                    }
                    if (listFormato.get(posicion).cSeccion.trim().equals("C")) {
                        list.add("SELECCIONE");
                        list.add("CONFORME");
                        list.add("NO CONFORME");
                    }


                    ArrayAdapter<String> adapterCombo = new ArrayAdapter<String>(FichaRutasVerif3CardActivity.this
                            , android.R.layout.simple_spinner_item, list);
                    adapterCombo.setDropDownViewResource(R.layout.spinner);

                    //////TEXTOS
                    if (listFormato.get(posicion).cSeccion.trim().equals("V")) {

                        Combo.setAdapter(adapterComboV);
                        Combo.setSelection(0);
                        llTempZZ.addView(Combo);
                        Combo.setGravity(Gravity.CENTER);
                    } else if (listFormato.get(posicion).cSeccion.trim().equals("E")) {

                        Combo2.setAdapter(adapterCombo);
                        Combo2.setSelection(0);
                        llTempZZ.addView(Combo2);
                        Combo2.setGravity(Gravity.CENTER);
                    } else if (listFormato.get(posicion).cSeccion.trim().equals("M")) {

                        Combo3.setAdapter(adapterCombo);
                        Combo3.setSelection(0);
                        llTempZZ.addView(Combo3);
                        Combo3.setGravity(Gravity.CENTER);
                    } else if (listFormato.get(posicion).cSeccion.trim().equals("S")) {
                        Combo4.setAdapter(adapterCombo);
                        Combo4.setSelection(0);
                        llTempZZ.addView(Combo4);
                        Combo4.setGravity(Gravity.CENTER);
                    } else if (listFormato.get(posicion).cSeccion.trim().equals("C")) {
                        Combo5.setAdapter(adapterCombo);
                        Combo5.setSelection(0);
                        llTempZZ.addView(Combo5);
                        Combo5.setGravity(Gravity.CENTER);
                    } else if (listFormato.get(posicion).cSeccion.trim().equals("P")) {
                        Combo6.setAdapter(adapterCombo);
                        Combo6.setSelection(0);
                        llTempZZ.addView(Combo6);
                        Combo6.setGravity(Gravity.CENTER);
                    } else if (listFormato.get(posicion).cSeccion.trim().equals("A")) {
                        Combo7.setAdapter(adapterCombo);
                        Combo7.setSelection(0);
                        llTempZZ.addView(Combo7);
                        Combo7.setGravity(Gravity.CENTER);
                    } else {

                        Combo.setAdapter(adapterCombo);
                        Combo.setSelection(0);
                        llTempZZ.addView(Combo);
                        Combo.setGravity(Gravity.CENTER);
                    }

                    llTempYY.addView(titulo);
                    if (posicion < 7) {
                        llTempXX.addView(llTempYY);
                        llTempXX.addView(llTempZZ);
                        llTEMPADRE.addView(llTempXX);
                    } else {
                        llTempAA.addView(llTempYY);
                        llTempAA.addView(llTempZZ);
                        llTEMPADRE.addView(llTempAA);
                    }

                    // combo de entrega de productos
                    Combo2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            if (position == 0) {
                                spentrega = position;
                                spmotivo = 0;
                                spplazo = 0;
                                spestado = 0;
                                spacta = 0;
                            } else if (position == 2) {
                                spentrega = position;

                                pll4.setVisibility(View.VISIBLE);
                                pll2.setVisibility(View.GONE);
                                listFormato.get(8).vResultado = "NO CONFORME";
                                listFormato.get(8).bcheck = false;
                                listFormato.get(8).bactivo = false;
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(8));
                                listFormato.get(9).bcheck = false;
                                listFormato.get(9).bactivo = false;
                                listFormato.get(9).vResultado = "NO CONFORME";
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(9));
                                listFormato.get(10).vResultado = "NO CONFORME";
                                listFormato.get(10).bcheck = false;
                                listFormato.get(10).bactivo = false;
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(10));
                                listFormato.get(posicion).vResultado = Combo2.getSelectedItem().toString();
                                listFormato.get(posicion).bcheck = true;
                                listFormato.get(posicion).bactivo = true;
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                            } else {
                                spentrega = position;
                                pll4.setVisibility(View.GONE);
                                pll2.setVisibility(View.VISIBLE);
                                listFormato.get(posicion).bcheck = true;
                                listFormato.get(posicion).bactivo = true;
                                listFormato.get(posicion).vResultado = Combo2.getSelectedItem().toString();
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                        }
                    });

                    //combo de TIPO DE VEHICULO

                    Combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            spcodigo = position;
                            spanexo = position;
                            if (position == 0) {

                            } else {

                                listFormato.get(posicion).vResultado = Combo.getSelectedItem().toString();
                                listFormato.get(posicion).bcheck = true;
                                listFormato.get(posicion).bactivo = true;
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                            }
                            listFormato.get(posicion).vResultado = Combo.getSelectedItem().toString();
                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }
                    });
                    //SPINNER  MOTIVO  de NO ENTREGA
                    Combo3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            if (position == 0) {
                                spmotivo = position;
                                pll5.setVisibility(View.GONE);
                                listFormato.get(posicion).bactivo = false;
                                listFormato.get(posicion).bcheck = false;
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                            } else if (position == 4) {
                                spmotivo = position;
                                pll5.setVisibility(View.VISIBLE);
                                listFormato.get(posicion).vResultado = Combo3.getSelectedItem().toString();
                                listFormato.get(posicion).bactivo = true;
                                listFormato.get(posicion).bcheck = true;
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                                motivo.addTextChangedListener(new TextWatcher() {

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        listFormato.get(7).vObservacion = motivo.getText().toString();
                                        listFormato.get(7).bcheck = true;
                                        listFormato.get(7).bactivo = true;
                                        RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(7));
                                    }

                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                    }
                                });
                            } else {
                                spmotivo = position;
                                listFormato.get(posicion).bactivo = false;
                                listFormato.get(posicion).bcheck = false;
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                                pll5.setVisibility(View.GONE);
                            }
                            listFormato.get(posicion).vResultado = Combo3.getSelectedItem().toString();
                            RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            // your code here
                        }
                    });

                    ///ESTADO DE  PRODUCTOS
                    Combo4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                         @Override
                                                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                             if (position == 0) {
                                                                 spestado = position;
                                                                 listFormato.get(posicion).bactivo = false;
                                                                 listFormato.get(posicion).bcheck = false;
                                                                 RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                                                             } else {
                                                                 spestado = position;
                                                                 listFormato.get(posicion).vResultado = Combo4.getSelectedItem().toString();
                                                                 listFormato.get(posicion).bcheck = true;
                                                                 listFormato.get(posicion).bactivo = true;
                                                                 RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                                                             }

                                                         }

                                                         @Override
                                                         public void onNothingSelected(AdapterView<?> parent) {

                                                         }
                                                     }
                    );

                    /////ACTA DE ENTREGA

                    Combo5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                         @Override
                                                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                             if (position == 0) {
                                                                 spacta = position;
                                                                 listFormato.get(posicion).bactivo = false;
                                                                 listFormato.get(posicion).bcheck = false;
                                                                 RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                                                             } else {
                                                                 spacta = position;
                                                                 listFormato.get(posicion).vResultado = Combo5.getSelectedItem().toString();
                                                                 listFormato.get(posicion).bcheck = true;
                                                                 listFormato.get(posicion).bactivo = true;
                                                                 RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                                                             }

                                                         }

                                                         @Override
                                                         public void onNothingSelected(AdapterView<?> parent) {

                                                         }
                                                     }
                    );
                    //ENTREGA DENTRO DE PLAZO
                    Combo6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                         @Override
                                                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                             if (position == 0) {
                                                                 spplazo = position;

                                                                 listFormato.get(posicion).bactivo = false;
                                                                 listFormato.get(posicion).bcheck = false;
                                                                 RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                                                             } else {
                                                                 spplazo = position;
                                                                 listFormato.get(posicion).vResultado = Combo6.getSelectedItem().toString();
                                                                 listFormato.get(posicion).bcheck = true;
                                                                 listFormato.get(posicion).bactivo = true;
                                                                 RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                                                             }

                                                         }

                                                         @Override
                                                         public void onNothingSelected(AdapterView<?> parent) {

                                                         }
                                                     }
                    );

                    //COMBO DE ANEXO
                    Combo7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spanexo = position;
                            if (position == 0) {
                                listFormato.get(posicion).bactivo = false;
                                listFormato.get(posicion).bcheck = false;
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                            } else {
                                listFormato.get(posicion).vResultado = Combo7.getSelectedItem().toString();
                                listFormato.get(posicion).bcheck = true;
                                listFormato.get(posicion).bactivo = true;
                                RECORDCARDITEM_DAO.Actualizar(FichaRutasVerif3CardActivity.this, listFormato.get(posicion));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                                                     }
                    );


                    pllh.addView(llTEMPADRE);
                    break;
            }
            if (posicion < 7) {
                pll.setOrientation(LinearLayout.VERTICAL);
                pll.addView(pllh);
            } else if (posicion == 7) {
                pll4.setOrientation(LinearLayout.VERTICAL);
                pll4.addView(pllh);
            } else {
                pll2.setOrientation(LinearLayout.VERTICAL);
                pll2.addView(pllh);
            }

        }



    }
    private int Contar(){

        int resultado=RECORDCARDITEM_DAO.GetSumaIEERC(FichaRutasVerif3CardActivity.this,OBJSMFICHASGRABADAS.idFichasGrabadas,"N",2,30);
        return resultado;

    }
    public void btnFoto(View v)
    {
        if (imageIDs.size()>=1){
            Utility.alert(this, "Límite máximo de fotos.",true);
        }
        else {
            getLocation();
            fechaFoto = new Date();
            Intent cameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uriSavedImage = Uri.fromFile(Utility.SetImage(MODULAR));
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            startActivityForResult(cameraIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1  && resultCode == Activity.RESULT_OK) {
            final Dialog dialog = new Dialog(this);
            final String nombre1 = MODULAR;
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.popup_comment);
            dialog.setTitle("FOTO DE ACTA " + MODULAR);
            ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
            imageView.setImageBitmap(Utility.getImagen(MODULAR));
            final EditText txtNombre = (EditText) dialog.findViewById(R.id.txtNombre);
            final TextView lblNombre1 = (TextView) dialog.findViewById(R.id.lblNombre1);
            lblNombre1.setText(nombre1);
            final TextView lblTime = (TextView) dialog.findViewById(R.id.lblTime);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();

            final String currentDateTimeString = dateFormat.format(cal.getTime());
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            final String formattedDate = df.format(c.getTime());
            Button button = (Button) dialog.findViewById(R.id.btnGrabar);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    {
                        String nombre2 = txtNombre.getText().toString()+"_"+formattedDate;
                        if (Utility.getRename(nombre1, nombre2)) {
                            getLocation();
                            SMDETFOTOSRUTAS entidad = new SMDETFOTOSRUTAS();
                            entidad.idFichasGrabadas = OBJSMFICHASGRABADAS.idFichasGrabadas;
                            entidad.vLatitud = latitude;
                            entidad.vLongitud = longitude;
                            entidad.vNombreFoto = nombre2;
                            entidad.vModular=MODULAR;
                            entidad.dtFecReg = currentDateTimeString;
                            SMDETFOTOSRUTAS_DAO.Agregar(FichaRutasVerif3CardActivity.this, entidad);
                            dialog.dismiss();
                        }
                        else { Utility.alert(FichaRutasVerif3CardActivity.this, "Error", true); }
                    }
                }
            });
            dialog.show();
        }
    }

    public Location getLocation() throws SecurityException {
        try {
            locationManager = (LocationManager) this
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
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(FichaRutasVerif3CardActivity.this);
            alertDialog.setTitle("GPS desactivado");

            alertDialog.setMessage("Por favor acceda a configuración de GPS y actívelo para el registro, se cerrará la aplicación");

            alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    FichaRutasVerif3CardActivity.this.startActivity(intent);
                    ((Activity) FichaRutasVerif3CardActivity.this).finish();
                }
            });
            alertDialog.show();
        }
        else{
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            FichaRutasVerif3CardActivity.this.startActivity(intent);
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
        locationManager = (LocationManager)this. getSystemService(this.LOCATION_SERVICE);
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


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }
}
