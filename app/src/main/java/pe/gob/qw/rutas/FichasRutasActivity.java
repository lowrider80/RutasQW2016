package pe.gob.qw.rutas;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.gob.qw.rutas.entities.RECORDCARDITEM;
import pe.gob.qw.rutas.entities.SMDETFOTOS;
import pe.gob.qw.rutas.entities.SMFICHASGBOTROS;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.entities.TABLETSUPERVISORES;
import pe.gob.qw.rutas.fragment.DatePickerFragment;
import pe.gob.qw.rutas.fragment.TimePickerFragment;
import pe.gob.qw.rutas.sqlite.dao.LIBERACIONRUTAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.RECORDCARDITEM_DAO;
import pe.gob.qw.rutas.sqlite.dao.SESION_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMDETFOTOS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMFICHASGOTROS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMFICHASGRABADAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.TABLETSUPERVISORES_DAO;
import pe.gob.qw.rutas.util.Utility;


/**
 * Demonstrates a "screen-slide" animation using a {@link ViewPager}. Because {@link ViewPager}
 * automatically plays such an animation when calling {@link ViewPager#setCurrentItem(int)}, there
 * isn't any animation-specific code in this sample.
 *
 * <p>This sample shows a "next" button that advances the user to the next step in a wizard,
 * animating the current screen out (to the left) and the next screen in (from the right). The
 * reverse animation is played when the user presses the "previous" button.</p>
 *
 *
 */
@SuppressWarnings("deprecation")
@SuppressLint({"DefaultLocale", "ValidFragment"})
public class FichasRutasActivity extends TabActivity implements LocationListener {

	TabHost th2;
	TabHost th3;
	TabHost th4;
	private Button btnFoto;
	public TextView TextoIEE,TextoDescarga,Textoverificacion;
	private SMFICHASGRABADAS OBJSMFICHASGRABADAS=null;

    private ProgressDialog pdData ;
    private Date fechaFoto;
    private Gallery gallery ;
	private Location location;
	protected LocationManager locationManager;
	boolean isGPSEnabled = false;
	private static final long MIN_TIME_BW_UPDATES = 1000 * 15;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
	private double latitude=0;
	private double longitude=0;
	//public ArrayList<String> list_compsolid2;
	private long fecharegistro = 0;

	private SMFICHASGBOTROS OBJSMFICHASGBOTROS=null;

    private List<String> imageIDs=null;
	public long fechaplazo=0;
	private EditText txt_lFechaF;
	private EditText txt_lHoraF;
	private int year_, month_,day_,hour_,minute_;

	private String codUT;

	private List<SMFICHASGRABADAS> listFichas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fichas_rutas);
		//list_compsolid2 = new ArrayList<String>();
		//list_compsolid2.add("SELECCIONE");
		//list_compsolid2.add("1");
		//list_compsolid2.add("2");
		//list_compsolid2.add("3");

		//if(!getIntent().getExtras().getStringArrayList("list_compsolid").isEmpty()){
//			list_compsolid2 = getIntent().getExtras().getStringArrayList("list_compsolid");
		//}


		//ESTO ES PARA LA PESTAÑA 2 DONDE CARGAN POR DEFECTO LOS 5 CAMPOS YA ESTABLECIDOS EN BD
        int[] arrayElaboracion = {8,9,11,12};
        OBJSMFICHASGRABADAS = SMFICHASGRABADAS_DAO.Buscar(this);
        List<RECORDCARDITEM> listRecordCardItemVerificacion= RECORDCARDITEM_DAO.ListarRecordCardPadresSeguimiento(this,
																												OBJSMFICHASGRABADAS.idFichasGrabadas,
																												OBJSMFICHASGRABADAS.iCodFicha,
																												arrayElaboracion);
		for(int i=0;i<listRecordCardItemVerificacion.size();i++)
		{
			int cantCampos= RECORDCARDITEM_DAO.CantidadCamposVaciosRecordCardItemVerificacion(this,
																							OBJSMFICHASGRABADAS.iCodFicha,
																							new int[]{listRecordCardItemVerificacion.get(i).iCodTipoSeccion},
																							listRecordCardItemVerificacion.get(i).iRecordCardItem);
        	if(cantCampos>0)
			{
				RECORDCARDITEM_DAO.EliminarRegistroVerificacion(this,
																OBJSMFICHASGRABADAS.iCodFicha,
																new int[]{listRecordCardItemVerificacion.get(i).iCodTipoSeccion},
																listRecordCardItemVerificacion.get(i).iRecordCardItem);
			}
        }

        imageIDs= SMDETFOTOS_DAO.Listar(this, OBJSMFICHASGRABADAS.idFichasGrabadas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



		int IEE= LIBERACIONRUTAS_DAO.TotalIEE(this,OBJSMFICHASGRABADAS.idFichasGrabadas);
		int descarga=Contar();
		int contarIEE = RECORDCARDITEM_DAO.GetSumaIEERCVerificacion(this,OBJSMFICHASGRABADAS.idFichasGrabadas, "T", 0,OBJSMFICHASGRABADAS.iCodFicha);

		TextoIEE=(TextView) findViewById(R.id.lblnumeroie);
		TextoIEE.setText("   Cantidad  Máxima de IEES: "+IEE);
		TextoDescarga=(TextView) findViewById(R.id.lblnumerodescargas);
		TextoDescarga.setText("   Cantidad IEES en Descargas: "+descarga);
		Textoverificacion=(TextView)findViewById(R.id.lblverificacion);
		Textoverificacion.setText("   Cantidad IEES Verificadas: "+contarIEE);


		th2=getTabHost();
		TabSpec spec=th2.newTabSpec("DESCARGA");
		spec.setContent(new Intent(this, FichaRutasVerif1SeccionActivity.class));
		spec.setIndicator("DESCARGA");
		th2.addTab(spec);

		th3=getTabHost();
		TabSpec spec2 =th3.newTabSpec("TRASBORDO");
		spec2.setContent(new Intent(this, FichaRutasVerif2SeccionActivity.class));
		spec2.setIndicator("TRASBORDO");
		th3.addTab(spec2);

		th4=getTabHost();
		TabSpec spec3 =th4.newTabSpec("VERIFICACION DE LA DISTRIBUCION EN IE");
		spec3.setContent(new Intent(this, FichaRutasVerif3SeccionActivity.class));
		spec3.setIndicator("VERIFICACION DE LA DISTRIBUCION EN IE");
		th4.addTab(spec3);

		this.setTitle("FICHA DE VERIFICACION DE RUTAS ");

	}
	private int Contar(){

		int resultado=RECORDCARDITEM_DAO.GetSumaIEERC(this,OBJSMFICHASGRABADAS.idFichasGrabadas,"N",2,30);
		return resultado;

	}
	public void btnCalifica(View v){
		OBJSMFICHASGRABADAS = SMFICHASGRABADAS_DAO.Buscar(this);



		int total= RECORDCARDITEM_DAO.GetCantidadRC(this,OBJSMFICHASGRABADAS.idFichasGrabadas,"T",0,OBJSMFICHASGRABADAS.iCodFicha);
		if (total>0){

			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("AVISO");
			alert.setMessage("¿ESTAS SEGURO QUE DESEAS FINALIZAR LA FICHA?");
			alert.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {


					codUT= SESION_DAO.BuscarUt(FichasRutasActivity.this);

					int descarga = RECORDCARDITEM_DAO.GetCantidadRCporSeccion(FichasRutasActivity.this,OBJSMFICHASGRABADAS.idFichasGrabadas,9,0,OBJSMFICHASGRABADAS.iCodFicha);
					int trasbordo= RECORDCARDITEM_DAO.GetCantidadRCporSeccion(FichasRutasActivity.this,OBJSMFICHASGRABADAS.idFichasGrabadas,10,0,OBJSMFICHASGRABADAS.iCodFicha);
					int verificacion= RECORDCARDITEM_DAO.GetCantidadRCporSeccion(FichasRutasActivity.this, OBJSMFICHASGRABADAS.idFichasGrabadas, 11, 0, OBJSMFICHASGRABADAS.iCodFicha);
					Calendar cal2= Calendar.getInstance();
					fechaplazo=cal2.getTimeInMillis();
					String mensaje="RESULTADO GENERAL";
					if(SMFICHASGRABADAS_DAO.Actualizar(FichasRutasActivity.this, OBJSMFICHASGRABADAS)){
//				locationManager.removeUpdates(this);
						SMFICHASGRABADAS_DAO.AgregarFichaGBOTROS(FichasRutasActivity.this, OBJSMFICHASGRABADAS,descarga,trasbordo,verificacion,0, Integer.parseInt(codUT),fechaplazo);
						setCalificaTransporte(mensaje, descarga, trasbordo, verificacion, 0);
					}
					else {
						Utility.alert(FichasRutasActivity.this, "Error intentelo otra vez", true);

					}
				}
			});

			alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			});

			alert.show();


		}
		else{ Utility.alert(this, "No hay Registros de Descarga,Trasbordo o Verificacion de IE en la Ficha", true); }
	}


	public void setCalificaTransporte(String sms, int a, int b, int c, int d)//Se muestran y mandan los puntajes
	{
		OBJSMFICHASGBOTROS= SMFICHASGOTROS_DAO.Buscar(this,OBJSMFICHASGRABADAS.idFichasGrabadas);
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popup_transporterutas);
		dialog.setCancelable(false);//para que el dialog no sea cancelable

		final LinearLayout ll_r1 = (LinearLayout) dialog.findViewById(R.id.llr1);
		ll_r1.setVisibility(View.VISIBLE);
		final LinearLayout ll_r2 = (LinearLayout) dialog.findViewById(R.id.llr2);
		ll_r2.setVisibility(View.VISIBLE);
		final LinearLayout ll_r3 = (LinearLayout) dialog.findViewById(R.id.llr3);
		ll_r3.setVisibility(View.VISIBLE);
		final LinearLayout ll_r4 = (LinearLayout) dialog.findViewById(R.id.llr4);
		ll_r4.setVisibility(View.VISIBLE);

		final LinearLayout ll_mensaje = (LinearLayout) dialog.findViewById(R.id.ll_mensaje);
		ll_mensaje.setVisibility(View.VISIBLE);
		final TextView lblnombresup =(TextView) dialog.findViewById(R.id.lblnombresyapellidos);
		lblnombresup.setText(OBJSMFICHASGBOTROS.vNSupervisor);
		final TextView lblDni=(TextView) dialog.findViewById(R.id.lbldni);
		lblDni.setText(String.valueOf(OBJSMFICHASGBOTROS.vDNISupervisor));
		final EditText lblmonitor=(EditText) dialog.findViewById(R.id.lblmonitor);

		final TABLETSUPERVISORES entidad = TABLETSUPERVISORES_DAO.Buscar(this,
				lblDni.getText().toString().trim());

		if (entidad!=null){

			lblmonitor.setText(entidad.vDesCargo);	lblmonitor.setEnabled(false);
			lblmonitor.setClickable(false);}

		else
		{
			lblmonitor.setEnabled(true);	lblmonitor.setClickable(true);}

		TextView lblTitulo= (TextView) dialog.findViewById(R.id.lblTitulo);


		TextView lbl4= (TextView) dialog.findViewById(R.id.lbl4);
		TextView lbl5= (TextView) dialog.findViewById(R.id.lbl5);
		TextView lbl6= (TextView) dialog.findViewById(R.id.lbl6);

		TextView lblMensaje= (TextView) dialog.findViewById(R.id.lblMensaje);
		TextView lblVehiculosConformes= (TextView) dialog.findViewById(R.id.lblVehiculosConformes);
		TextView lblVehiculosNConformes= (TextView) dialog.findViewById(R.id.lblVehiculosNConformes);
		TextView lblTotalVehiculos= (TextView) dialog.findViewById(R.id.lblTotalVehiculos);
		txt_lFechaF = (EditText) dialog.findViewById(R.id.txt_lFechaF);
		txt_lHoraF = (EditText) dialog.findViewById(R.id.txt_lHoraF);

		txt_lFechaF.setKeyListener(null);
		txt_lHoraF.setKeyListener(null);

		txt_lFechaF.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mostrarCalendario2(v);
			}
		});

		txt_lFechaF.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				return;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				return;
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				return;
			}
		});


		txt_lHoraF.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimePickerDialog(v);
			}
		});


		final EditText lblObs=(EditText) dialog.findViewById(R.id.txtObs);
		lblMensaje.setText(sms);


		lbl4.setText("N° DESCARGAS DE PRODUCTOS EN RUTA:");
		lbl5.setText("N° DE TRASBORDOS EN LA RUTA DE TRANSPORTE:");
		lbl6.setText("N° DE IE VERIFICADAS:");

		lblVehiculosConformes.setText(String.valueOf(a));
		lblVehiculosNConformes.setText(String.valueOf(b));
		lblTotalVehiculos.setText(String.valueOf(c));



		final Button buttonAcept = (Button)dialog.findViewById(R.id.buttonAcept);
		buttonAcept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Calendar cx = Calendar.getInstance();
				cx.set(Calendar.YEAR, year_);
				cx.set(Calendar.MONTH, month_);
				cx.set(Calendar.DAY_OF_MONTH, day_);
				cx.set(Calendar.HOUR_OF_DAY, hour_);
				cx.set(Calendar.MINUTE, minute_);
				cx.set(Calendar.MILLISECOND, 0);

				OBJSMFICHASGRABADAS.lFechaFin = cx.getTimeInMillis();
				SMFICHASGRABADAS_DAO.Actualizar(FichasRutasActivity.this,OBJSMFICHASGRABADAS);


				if(OBJSMFICHASGRABADAS.cCategoria.equals("S") || OBJSMFICHASGRABADAS.cCategoria.equals("T") || OBJSMFICHASGRABADAS.cCategoria.equals("O") || OBJSMFICHASGRABADAS.cCategoria.equals("L")
						|| OBJSMFICHASGRABADAS.cCategoria.equals("R")) {

					if (OBJSMFICHASGRABADAS.lFecha < OBJSMFICHASGRABADAS.lFechaFin)
					{
						if(lblmonitor.getText().toString().length()>0)
						{
							if(txt_lFechaF.getText().toString().length()>0 && txt_lHoraF.getText().toString().length()>0){
								if(lblObs.getText().toString().length()>0){

									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									try {
										Date date = sdf.parse(txt_lFechaF.getText().toString());
										long startDate = date.getTime();
										OBJSMFICHASGRABADAS.iEstado=2;
										OBJSMFICHASGRABADAS.vObservacion = lblObs.getText().toString();
										//OBJSMFICHASGRABADAS.lFechaFin=fecharegistro;
										OBJSMFICHASGRABADAS.vEmpresaResponsableMedico=lblmonitor.getText().toString();
										SMFICHASGRABADAS_DAO.Actualizar(FichasRutasActivity.this, OBJSMFICHASGRABADAS);
										Intent intent = new Intent(FichasRutasActivity.this,MainActivity.class);
										startActivity(intent);
										finish();
										dialog.dismiss();
									} catch (ParseException e) {
										e.printStackTrace();
									}
								}
								else
								{
									Utility.alert(FichasRutasActivity.this, "Debes Ingresar una Observacion Final",true);}
							}
							else
							{
								Utility.alert(FichasRutasActivity.this, "Debes Ingresar Fecha de Registro",true);}
						}
						else
						{
							Utility.alert(FichasRutasActivity.this, "Debes Ingresar Cargo de la Persona que Registra la Ficha",true);}
					}

					else
					{
						Utility.alert(FichasRutasActivity.this, "Fecha Final no puede ser menor a Fecha Inicial", true);
					}

				}
			}
		});
		dialog.show();
	}

	public void showTimePickerDialog(View v)
	{
		DialogFragment timePickerFragment = new TimePickerFragment()
		{
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState)
			{
				// TODO Auto-generated method stub
				final Calendar c = Calendar.getInstance();
				hour_ = c.get(Calendar.HOUR);
				minute_ = c.get(Calendar.MINUTE);
				int day = c.get(Calendar.DAY_OF_MONTH);

				TimePickerDialog dialogoPicker = new TimePickerDialog(getActivity(), this, hour_, minute_, true);
				//dialogoPicker.getDatePicker().setMaxDate(new Date().getTime());

				return dialogoPicker;
			}

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute)
			{
				hour_ = hourOfDay;
				minute_ = minute;
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();

				c.set(Calendar.YEAR, 0);
				c.set(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, 0);
				c.set(Calendar.HOUR_OF_DAY, hourOfDay);
				c.set(Calendar.MINUTE, minute);

				//String myFormat = "hh:mm";
				//String formatoIngles = "yyyy-MM-dd";
				SimpleDateFormat format = new SimpleDateFormat("HH:mm");
				//SimpleDateFormat sdfe = new SimpleDateFormat(formatoIngles);

				String formatted = format.format(c.getTimeInMillis());
				txt_lHoraF.setText(formatted);


			}
		};

		timePickerFragment.show(this.getFragmentManager(), "datePicker");
	}
	public void mostrarCalendario2(View v)
	{
		DialogFragment datePickerFragment = new DatePickerFragment()
		{
			@Override
			public void onDateSet(DatePicker view, int year, int month, int day)
			{
				Calendar c = Calendar.getInstance();
				year_ = year;
				month_ = month;
				day_ = day;

				c.set(year, month, day);
				String myFormat = "dd/MM/yy";
				String formatoIngles = "yyyy-MM-dd";
				SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
				SimpleDateFormat sdfe = new SimpleDateFormat(formatoIngles);
				txt_lFechaF.setText(sdf.format(c.getTime()));

				//lFechaSupervision = c.getTimeInMillis(); //+ c.getTimeInMillis() ;

				// lFechaSupervision = lFechaSupervision + lHoraInicio;

	            /* SimpleDateFormat formatted = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	            String a = formatted.format(new Date(lFechaSupervision));
	            txtFechaSupervision.setText(a);*/
			}

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState)
			{
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog dialogoPicker = new DatePickerDialog(getActivity(), this, year, month, day);
				dialogoPicker.getDatePicker().setMaxDate(new Date().getTime());

				//Calendar m_calendar_min = Calendar.getInstance();
				//m_calendar_min.set(Calendar.YEAR, 2016);
				//m_calendar_min.set(Calendar.MONTH, 2);
				//m_calendar_min.set(Calendar.DAY_OF_MONTH, 20);

				//dialogoPicker.getDatePicker().setMinDate(m_calendar_min.getTime().getTime());
				dialogoPicker.getDatePicker().setMinDate(1455944400000L); // 2016/02/20 // http://currentmillis.com/
				return dialogoPicker;
			}
		};

		//myTimePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
		datePickerFragment.show(this.getFragmentManager(), "datePicker");
	}


	public void btnFoto(View v)	{
		OBJSMFICHASGRABADAS = SMFICHASGRABADAS_DAO.Buscar(this);

		fechaFoto = new Date();
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		Uri uriSavedImage = Uri.fromFile(Utility.SetImage(OBJSMFICHASGRABADAS.cCodEstablecimiento+fechaFoto.getTime()));
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
		startActivityForResult(cameraIntent, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			SimpleDateFormat sdfs=new SimpleDateFormat("dd/MMM/yy HH:mm:ss");

			SMDETFOTOS entidad = new SMDETFOTOS();
			entidad.idFichasGrabadas=OBJSMFICHASGRABADAS.idFichasGrabadas;
			//entidad.vLatitud=latitude;
			//entidad.vLongitud=longitude;

			entidad.vNombreFoto=OBJSMFICHASGRABADAS.cCodEstablecimiento+fechaFoto.getTime();
			entidad.dtFecReg=sdfs.format(fechaFoto);
			SMDETFOTOS_DAO.Agregar(this, entidad);

			imageIDs.add(OBJSMFICHASGRABADAS.cCodEstablecimiento+fechaFoto.getTime());
			gallery.setAdapter(new ImageAdapter(this));

			//img.setImageBitmap(bMap);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void getLocation() {
		try {
			locationManager = (LocationManager) this
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);


			if (!isGPSEnabled) {
				// no network provider is enabled
				Intent viewIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(viewIntent);
			} else {
				if (location == null) {
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("GPS Enabled", "GPS Enabled");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if (location != null) {
							onLocationChanged(location);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onLocationChanged(Location location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {
		locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
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

		Intent viewIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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




	/////////////////////////////////////////////////////////////////////////////////////
	public class ImageAdapter extends BaseAdapter {
		private Context context;
		private int itemBackground;

		public ImageAdapter(Context c) {
			context = c;
		}

		public int getCount() {
			return imageIDs.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			// imageView.setImageResource(imageIDs[position]);
			imageView.setImageBitmap(Utility.getImagenCompres(imageIDs.get(position)));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(120, 80));
			imageView.setBackgroundResource(itemBackground);
			return imageView;

		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		int IEE= LIBERACIONRUTAS_DAO.TotalIEE(this,OBJSMFICHASGRABADAS.idFichasGrabadas);
		int descarga=Contar();
		int contarIEE = RECORDCARDITEM_DAO.GetSumaIEERCVerificacion(this,OBJSMFICHASGRABADAS.idFichasGrabadas, "T", 0,OBJSMFICHASGRABADAS.iCodFicha);

		TextoIEE=(TextView) findViewById(R.id.lblnumeroie);
		TextoIEE.setText("   Cantidad  Máxima de IEES: "+IEE);
		TextoDescarga=(TextView) findViewById(R.id.lblnumerodescargas);
		TextoDescarga.setText("   Cantidad IEES en Descargas: "+descarga);
		Textoverificacion=(TextView)findViewById(R.id.lblverificacion);
		Textoverificacion.setText("   Cantidad IEES Verificadas: "+contarIEE);

		//finish();
		//startActivity(getIntent());




	}







}


 

