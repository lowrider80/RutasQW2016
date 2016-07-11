package pe.gob.qw.rutas;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pe.gob.qw.rutas.entities.SECCIONFICHAGRABADA;
import pe.gob.qw.rutas.entities.SMDETFOTOSRUTAS;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.fragment.FichaRutasVerif3SeccionSlideFragment;
import pe.gob.qw.rutas.sqlite.dao.RECORDCARDITEM_DAO;
import pe.gob.qw.rutas.sqlite.dao.SECCIONFICHAGRABADA_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMDETFOTOSRUTAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMDETFOTOS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMFICHASGRABADAS_DAO;
import pe.gob.qw.rutas.util.Utility;

@SuppressWarnings("deprecation")
public class FichaRutasVerif3SeccionActivity extends AppCompatActivity implements LocationListener {
	public static  int idFicha;
	public static int getIdFicha() {
		return idFicha;
	}

	public static void setIdFicha(int idFicha) {
		FichaRutasVerif3SeccionActivity.idFicha = idFicha;
	}

	private SMFICHASGRABADAS OBJSMFICHASGRABADAS=null;
	private SECCIONFICHAGRABADA OBJSECCIONFICHAGRABADA=null;


	private double latitude=0;
	private double longitude=0;
	// flag for GPS status
	boolean isGPSEnabled = false;

	private Location location; // location

	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 15;

	// Declaring a Location Manager
	protected LocationManager locationManager;

	/**
	 * The number of pages (wizard steps) to show in this demo.
	 *
	 */
	private int NUM_PAGES = 0;
	private static int NUM_ROWS=10;

	/**
	 * The pager widget, which handles animation and allows swiping horizontally to access previous
	 * and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private ScreenSlidePagerAdapter mPagerAdapter;

	private Button btnArrowBack;
	private Button btnArrowNext;
	private TextView lblTitulo;

	private CheckBox chkNoAplica;
	private LinearLayout ll_chkNoAplica;

	private ProgressDialog pdData ;

	//public ArrayList<String> list_compsolid3;
    //
    //private Questionary questionary=null;

    private int ancho=0;
    
    private int[] array={9};
    // Check screen orientation or screen rotate event here



	private static final int CAMERA_REQUEST = 1888;
	public ImageView picOfDay;

	@SuppressLint("LongLogTag")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//if(!getIntent().getExtras().getStringArrayList("list_compsolid").){
		//list_compsolid = getIntent().getExtras().getStringArrayList("list_compsolid");
		//}

		OBJSMFICHASGRABADAS = SMFICHASGRABADAS_DAO.Buscar(this);
        OBJSECCIONFICHAGRABADA = new SECCIONFICHAGRABADA();
        OBJSECCIONFICHAGRABADA.idFichasGrabadas = OBJSMFICHASGRABADAS.idFichasGrabadas;
		OBJSECCIONFICHAGRABADA.iCodFicha=OBJSMFICHASGRABADAS.iCodFicha;
		OBJSECCIONFICHAGRABADA.iCodTipoSeccion=array[0];
        OBJSECCIONFICHAGRABADA= SECCIONFICHAGRABADA_DAO.Obtener(this, OBJSECCIONFICHAGRABADA);
		//if(OBJSMFICHASGRABADAS!=null)

		//list_compsolid3 = new ArrayList<String>();
		//list_compsolid3.add("SELECCIONE");

		//if (OBJSMFICHASGRABADAS.dOpcion2>0) list_compsolid3.add("1");
		//if (OBJSMFICHASGRABADAS.dOpcion3>0) list_compsolid3.add("2");
		//if (OBJSMFICHASGRABADAS.dOpcion4>0) list_compsolid3.add("3");

		//list_compsolid3.add("1");
		//list_compsolid3.add("2");

		int total = 0;
		if(OBJSMFICHASGRABADAS.cCategoria.equals("L"))
		{
		//	total = LIBERACIONRECORDCARDITEM_DAO.GetSize(this, OBJSMFICHASGRABADAS.idFichasGrabadas);
		//	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//	//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//	NUM_ROWS=10;
		}
		else
		{
			total = RECORDCARDITEM_DAO.GetSizeInSeccion(this, OBJSMFICHASGRABADAS.idFichasGrabadas,array);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		//NUM_ROWS = total;

		imageIDs= SMDETFOTOS_DAO.Listar(this, OBJSMFICHASGRABADAS.idFichasGrabadas);
		setContentView(R.layout.activity_ficha_rutas_verificacion);
		NUM_PAGES=1;//(total/NUM_ROWS);
		//NUM_PAGES=1;//total/NUM_ROWS;
		//int mod=total%NUM_ROWS;
		//if(0<mod)
		//	NUM_PAGES++;


		if(OBJSMFICHASGRABADAS==null)
		{
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
		}

		//Seteo de nombre de la actividad - Ficha Superovisi�n Modalidad Producto - Ficha Superovisi�n Modalidad Raciones
		this.setTitle(OBJSMFICHASGRABADAS.vDescFicha);

		Log.e("OBJSMFICHASGRABADAS.cCategoria", OBJSMFICHASGRABADAS.cCategoria);
		Log.e("RECORDCARDITEM_DAO.Agregar", "total " + total);

		lblTitulo=(TextView) findViewById(R.id.lblTitulo);
		lblTitulo.setText("VERIFICACION DE LA DISTRIBUCION DE PRODUCTOS EN LA INSTITUCION EDUCATIVA");

		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

		ll_chkNoAplica = (LinearLayout) findViewById(R.id.ll_chkNoAplica);
		ll_chkNoAplica.setVisibility(View.GONE);
		chkNoAplica=(CheckBox) findViewById(R.id.chkNoAplica);
		chkNoAplica.setVisibility(CheckBox.VISIBLE);
		chkNoAplica.setChecked(OBJSECCIONFICHAGRABADA.bNoAplicaSeccion);
		chkNoAplica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				OBJSECCIONFICHAGRABADA.bNoAplicaSeccion=isChecked;
				SECCIONFICHAGRABADA_DAO.ActualizarEstado( FichaRutasVerif3SeccionActivity.this ,OBJSECCIONFICHAGRABADA);
			}
		});
		lblTitulo = (TextView)findViewById(R.id.lblTitulo);
		//lblTitulo.setText(RECORDCARDITEM_DAO.ObtenerCabeceraSeccionVerificacion(this, OBJSMFICHASGRABADAS.idFichasGrabadas, OBJSMFICHASGRABADAS.iCodFicha, 9).vDetalleSeccion);
		ancho=(getWindowManager().getDefaultDisplay().getWidth());
		pdData = new ProgressDialog(this);
		pdData.setTitle("Cargando Datos");
		pdData.setMessage("Espere un momento");
		pdData.show();
		Cargar();
		getLocation() ;
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int position) {
			Log.e(" Fragment getItem",""+position);
			return FichaRutasVerif3SeccionSlideFragment.create(position,
					NUM_PAGES,
					NUM_ROWS,
					OBJSMFICHASGRABADAS.idFichasGrabadas,
					OBJSMFICHASGRABADAS.iCodFicha,
					OBJSMFICHASGRABADAS.cCategoria,
					OBJSMFICHASGRABADAS.iTipoEstablecimiento,
					OBJSMFICHASGRABADAS.cCodProveedor,
					OBJSMFICHASGRABADAS.cCodEstablecimiento);
		}
		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	public void Cargar()
	{
		new Thread() {
			public void run() {
				try
				{
					mPager.setAdapter(mPagerAdapter);
				}
				catch(Exception exception)
				{
					Log.e("Thread", exception.getMessage());
				}

				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	final Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			//update UI here depending on what message is received.
			super.handleMessage(msg);
			CargarData();
		}
	};

	public void CargarData()
	{
		pdData.dismiss();
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When changing pages, reset the action bar actions since they are dependent
				// on which page is currently active. An alternative approach is to have each
				// fragment expose actions itself (rather than the activity exposing actions),
				// but for simplicity, the activity provides the actions in this sample.
				SetTitulo();
				invalidateOptionsMenu();
			}
		});
		SetTitulo();
	}


	public void btnArrowBack()
	{
		mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		SetTitulo();
	}


	public void SetTitulo()
	{
		//lblTitulo.setText("Pagina "+(mPager.getCurrentItem() + 1) +" de "+NUM_PAGES);

		if(0==mPager.getCurrentItem())
		{
			//btnArrowBack.setVisibility(View.GONE);
		}
		else if(0<mPager.getCurrentItem() && NUM_PAGES>mPager.getCurrentItem())
		{
			//&& btnArrowNext.setVisibility(View.VISIBLE);
			//btnArrowBack.setVisibility(View.VISIBLE);
			//if(NUM_PAGES-1==mPager.getCurrentItem())
			//  btnArrowNext.setVisibility(View.GONE);
		}

	}

	private List<String> imageIDs=null;



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
		// TODO Auto-generated method stub
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		//  Utility.alert(this, "Lon: "+longitude+"\n Lat:"+latitude, false);
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

	@Override
	public void onProviderEnabled(String arg0) {
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

	public void CommentFoto(int posicion)
	{
		final Dialog dialog = new Dialog(this);
		final String nombre1 = imageIDs.get(posicion);
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.popup_comment);
		dialog.setTitle("Imagen: " + imageIDs.get(posicion));

		ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
		imageView.setImageBitmap(Utility.getImagen(imageIDs.get(posicion)));
		final EditText txtNombre = (EditText) dialog.findViewById(R.id.txtNombre);
		final TextView lblNombre1 = (TextView) dialog.findViewById(R.id.lblNombre1);
		lblNombre1.setText(nombre1);
		final TextView lblTime = (TextView) dialog.findViewById(R.id.lblTime);

		final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

       //String ax= Long.toString(fechaFoto.getTime());
	//	lblTime.setText("" + fechaFoto.getTime());
	///	lblTime.setText("" + );
		Button button = (Button) dialog.findViewById(R.id.btnGrabar);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			 {
				 String nombre2 = txtNombre.getText().toString();// + "_" + txtNombre.getText().toString() ;//+ "_"  // lblTime.getText().toString();
				 if (Utility.getRename(nombre1, nombre2)) {
					 SimpleDateFormat sdfs = new SimpleDateFormat("dd/MMM/yy HH:mm:ss");
					 SMDETFOTOSRUTAS entidad = new SMDETFOTOSRUTAS();
					 entidad.idFichasGrabadas = OBJSMFICHASGRABADAS.idFichasGrabadas;
					 entidad.vLatitud = latitude;
					 entidad.vLongitud = longitude;
					 entidad.vNombreFoto = nombre2;
					 entidad.vModular=
					 entidad.dtFecReg = currentDateTimeString;
					 SMDETFOTOSRUTAS_DAO.Agregar(FichaRutasVerif3SeccionActivity.this, entidad);
					 imageIDs.remove(nombre1);
					 imageIDs.add(nombre2);
					 dialog.dismiss();
				 }
				 else { Utility.alert(FichaRutasVerif3SeccionActivity.this, "Error", true); }
			 }
									}
			});
		dialog.show();
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1  && resultCode == Activity.RESULT_OK) {
			imageIDs.add(OBJSMFICHASGRABADAS.cCodEstablecimiento);//+fechaFoto.getTime());
			CommentFoto(imageIDs.size() - 1);
			//img.setImageBitmap(bMap);
			//gallery.setAdapter(new ImageAdapter(this));
		}
	}






	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{


		return false;
	}
}
