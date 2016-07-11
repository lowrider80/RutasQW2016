package pe.gob.qw.rutas;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import pe.gob.qw.rutas.entities.SECCIONFICHAGRABADA;
import pe.gob.qw.rutas.entities.SMDETFOTOS;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.fragment.FichaRutasVerif2SeccionSlideFragment;
import pe.gob.qw.rutas.sqlite.dao.RECORDCARDITEM_DAO;
import pe.gob.qw.rutas.sqlite.dao.SECCIONFICHAGRABADA_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMDETFOTOS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMFICHASGRABADAS_DAO;

/**
 * Demonstrates a "screen-slide" animation using a {@link ViewPager}. Because {@link ViewPager}
 * automatically plays such an animation when calling {@link ViewPager#setCurrentItem(int)}, there
 * isn't any animation-specific code in this sample.
 *
 * <p>This sample shows a "next" button that advances the user to the next step in a wizard,
 * animating the current screen out (to the left) and the next screen in (from the right). The
 * reverse animation is played when the user presses the "previous" button.</p>
 *
 //* @see RecodCardSlideReloadedFragment
 */
@SuppressWarnings("deprecation")
public class FichaRutasVerif2SeccionActivity extends FragmentActivity implements LocationListener {
	public static  int idFicha;
	public static int getIdFicha() {
		return idFicha;
	}

	public static void setIdFicha(int idFicha) {
		FichaRutasVerif2SeccionActivity.idFicha = idFicha;
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



		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

		lblTitulo=(TextView) findViewById(R.id.lblTitulo);
		lblTitulo.setText("DATOS DE TRASBORDO EN LA RUTA DE TRANSPORTE");


		ll_chkNoAplica = (LinearLayout) findViewById(R.id.ll_chkNoAplica);
		ll_chkNoAplica.setVisibility(View.GONE);
		chkNoAplica=(CheckBox) findViewById(R.id.chkNoAplica);
		chkNoAplica.setVisibility(CheckBox.VISIBLE);
		chkNoAplica.setChecked(OBJSECCIONFICHAGRABADA.bNoAplicaSeccion);
		chkNoAplica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


				OBJSECCIONFICHAGRABADA.bNoAplicaSeccion=isChecked;
				SECCIONFICHAGRABADA_DAO.ActualizarEstado( FichaRutasVerif2SeccionActivity.this ,OBJSECCIONFICHAGRABADA);


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
			return FichaRutasVerif2SeccionSlideFragment.create(position,
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

		}
		else if(0<mPager.getCurrentItem() && NUM_PAGES>mPager.getCurrentItem())
		{

		}

	}

	private List<String> imageIDs=null;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {

			SimpleDateFormat sdfs=new SimpleDateFormat("dd/MMM/yy HH:mm:ss");
			SMDETFOTOS entidad = new SMDETFOTOS();
			entidad.idFichasGrabadas=OBJSMFICHASGRABADAS.idFichasGrabadas;
			entidad.vLatitud=latitude;
			entidad.vLongitud=longitude;



			SMDETFOTOS_DAO.Agregar(this, entidad);


			//img.setImageBitmap(bMap);
		}
	}

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
