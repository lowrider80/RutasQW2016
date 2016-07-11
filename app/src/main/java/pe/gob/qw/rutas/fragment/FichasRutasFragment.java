package pe.gob.qw.rutas.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.gob.qw.rutas.FichasRutasActivity;
import pe.gob.qw.rutas.R;
import pe.gob.qw.rutas.adapter.CustomUsersAdapter;
import pe.gob.qw.rutas.entities.ESPECIALISTAS;
import pe.gob.qw.rutas.entities.LIBERACIONRUTAS;
import pe.gob.qw.rutas.entities.RUTA;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.entities.TABLETPROVEEDORES;
import pe.gob.qw.rutas.entities.TABLETSUPERVISORES;
import pe.gob.qw.rutas.entities.TABLETVEHICULOS;
import pe.gob.qw.rutas.sqlite.dao.LIBERACIONRUTAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMFICHASGRABADAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.TABLETESPECIALISTAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.TABLETSUPERVISORES_DAO;
import pe.gob.qw.rutas.sqlite.dao.TABLETVEHICULOS_DAO;
import pe.gob.qw.rutas.util.Utility;

@SuppressLint({"DefaultLocale", "ValidFragment"})
public class FichasRutasFragment extends Fragment implements LocationListener {

	private final Context mContext;
	private SMFICHASGRABADAS OBJSMFICHASGRABADAS=null;
	private TABLETPROVEEDORES OBJTABLETPROVEEDORES=null;
	private Button buttonFicha;

	private Spinner ComboPlaca;
	private Spinner ComboRutas;

	private ListView lista1;

	private LinearLayout ll_actividad;
	private LinearLayout ll_apellidos;
	private LinearLayout ll_nombres;

	private long lFechaSupervision;

	private EditText txt_vNombresSupervisor;
	private EditText txt_vApellidosSupervisor;
	private AutoCompleteTextView txt_vDNISupervisor;
	private Button btnBuscar;
	private int division = 0;
	private int ancho;
//	private Adaptador adaptador;
	private ProgressDialog pd ;

	private TextView lbl_vNombre;
	private Button btnItems;
	private ViewGroup mContainerView;
	public int codigoitem=0;
	private int[] array={9};
	private List<LIBERACIONRUTAS> rutas;

	private List<TABLETVEHICULOS> listaVehiculos;
	private List<RUTA> listaRutas;

	private Location location;
	protected LocationManager locationManager;
	boolean isGPSEnabled = false;
	private static final long MIN_TIME_BW_UPDATES =  1000 * 30 * 1;;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
	private double latitude=0;
	private double longitude=0;

	//
	private EditText fecha;
	private EditText hora;
	private int year_, month_,day_,hour_,minute_;

	private long milliseconds;
	//

	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	private TextView lbl_vColegiatura;
	private TextView lbl_vProfesion,lbl_vNControlCalidad;
	private Spinner spespecialista;
	public String colegiatura,profesion;


	ArrayAdapter<String> myAdapter;

	public FichasRutasFragment(TABLETPROVEEDORES OBJTABLETPROVEEDORES, Context context) {
		super();
		this.OBJTABLETPROVEEDORES=OBJTABLETPROVEEDORES;


		this.mContext = context;
		getLocation();;
	}

	@Override
	public void onStart() { super.onStart(); }


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


		//COMIT DE PRUEBAsadsasddassdaadsasdsasdasaadsassdaa

		final View myFragmentView = inflater.inflate(R.layout.fragment_fichas_rutas, container, false);

		this.getActivity().setTitle("VERIFICACION DE RUTAS");
		ancho=(FichasRutasFragment.this.getActivity().getWindowManager().getDefaultDisplay().getWidth());
		//SETEANDO RUC DEL PROVEEDOR
		TextView lbl_cRUC = (TextView)myFragmentView.findViewById(R.id.lbl_cRUC);
		lbl_cRUC.setText(OBJTABLETPROVEEDORES.cRUC);

		//SETEANDO IDENTIFICACION DEL ESTABLECIMIENTO
		TextView lbl_vNombreProveedor = (TextView)myFragmentView.findViewById(R.id.lbl_vNombreProveedor);
		lbl_vNombreProveedor.setText(OBJTABLETPROVEEDORES.vNombreProveedor);

		//SETEANDO FECHA HORA SALIDA
//		lFechaSupervision = Calendar.getInstance().getTimeInMillis();
//		EditText txt_lFecha1 = (EditText)myFragmentView.findViewById(R.id.txt_lFecha1);
//		txt_lFecha1.setText(Utility.getFechaHora(new Date()));

		//SETEANDO CODIGO ESTABLECIMIENTO
		TextView lbl_cCodEstablecimiento = (TextView) myFragmentView.findViewById(R.id.lbl_cCodEstablecimiento);
		lbl_cCodEstablecimiento.setText(OBJTABLETPROVEEDORES.cCodEstablecimiento);

		//SETEANDO NOMBRE ESTABLECIMIENTO
		TextView lbl_vObservacion = (TextView) myFragmentView.findViewById(R.id.lbl_vObservacion);
		lbl_vObservacion.setText(OBJTABLETPROVEEDORES.vObservacion);

		//SETEANDO DIRECCIÓN ESTABLECIMIENTO
		TextView lbl_vDireccionEstablecimiento = (TextView) myFragmentView.findViewById(R.id.lbl_vDireccionEstablecimiento);
		lbl_vDireccionEstablecimiento.setText(OBJTABLETPROVEEDORES.vDireccionEstablecimiento);

		//INSTANCIANDO COMBO PARA LISTA DE PLACAS
		ComboPlaca = (Spinner)myFragmentView.findViewById(R.id.ComboPlaca);

		//INSTANCIANDO COMBO PARA LISTA DE PLACAS
		ComboRutas = (Spinner)myFragmentView.findViewById(R.id.ComboRutas);

		//INSTANCIANDO TIPO DE VEHICULO AUTOGENERADO POR COMBO
		lbl_vNombre = (TextView)myFragmentView.findViewById(R.id.lbl_vNombre);


		// FECHA DE LA FICHA

		fecha =(EditText) myFragmentView.findViewById(R.id.txtfecharuta);
		fecha.setKeyListener(null);
		fecha.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mostrarCalendario2(v);
			}
		});


		//HORA DE LA FICHA
		hora=(EditText) myFragmentView.findViewById(R.id.txthoraruta);
		hora.setKeyListener(null);
		hora.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimePickerDialog(v);
			}
		});




		spespecialista=(Spinner)myFragmentView.findViewById(R.id.spespecialista);

		lbl_vProfesion=(TextView)myFragmentView.findViewById(R.id.lbl_vProfesion);
		lbl_vColegiatura = (TextView) myFragmentView.findViewById(R.id.lbl_vColegiatura);

		lbl_vNControlCalidad=(TextView) myFragmentView.findViewById(R.id.lbl_vNControlCalidad);
		lbl_vNControlCalidad.setVisibility(View.GONE);


		//lista

		lista1 = (ListView) myFragmentView.findViewById(R.id.listaitems);

		//BOTON PARA SELECCIONAR ITEMS - PLAZO DE ENTREGA - IES
		btnItems = (Button)myFragmentView.findViewById(R.id.btnItems2);
		btnItems.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(FichasRutasFragment.this.getActivity());
				dialog.setTitle("FICHAS DE TRANSPORTE");
				dialog.setContentView(R.layout.items_rutas);
				dialog.setCancelable(false);




				final Spinner ComboItems = (Spinner) dialog.findViewById(R.id.spinnerItems);
				final List<LIBERACIONRUTAS> listaitems;

/// esto es una prueba!!!!
				//Spinner ITEMS = (Spinner)  FichasRutasFragment.this.getActivity().findViewById(R.id.spinnerItems);
				listaitems = LIBERACIONRUTAS_DAO.SpinItems(FichasRutasFragment.this.getActivity(), OBJTABLETPROVEEDORES.cCodEstablecimiento);
				listaitems.add(0, new LIBERACIONRUTAS(0, "SELECCIONE",0));
				ArrayAdapter<LIBERACIONRUTAS> asd = new ArrayAdapter<LIBERACIONRUTAS>(FichasRutasFragment.this.getActivity(),R.layout.spinner_vista,
						listaitems);
				asd.setDropDownViewResource(R.layout.spinner);
				//adaptador.setDropDownViewResource(R.layout.spinner);
				//ComboItems.setAdapter(adaptador);
				ComboItems.setAdapter(asd);
				final Spinner ComboLiberacion=(Spinner) dialog.findViewById(R.id.spinnerliberacion);
				final TextView inicio =(TextView) dialog.findViewById(R.id.lblfi2);
				final TextView finalo =(TextView) dialog.findViewById(R.id.lblff2);
				final EditText numero=(EditText) dialog.findViewById(R.id.numeroiee);
				//numero.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "999")});


				ComboItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
						codigoitem=listaitems.get(pos).iCodItem;

						final List listaliberacion;
						listaliberacion=LIBERACIONRUTAS_DAO.SpinLiberacion(FichasRutasFragment.this.getActivity(),ComboItems.getSelectedItem().toString(), OBJTABLETPROVEEDORES.cCodEstablecimiento);
						listaliberacion.add(0);
						ArrayAdapter asd2 = new ArrayAdapter(FichasRutasFragment.this.getActivity(),R.layout.spinner_vista,
								listaliberacion);
						asd2.setDropDownViewResource(R.layout.spinner);
						ComboLiberacion.setAdapter(asd2);
						if(listaliberacion.size()!=1)
						{   int r=listaliberacion.size()-1;
							listaliberacion.remove(r);}
					}
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
				ComboItems.setSelection(0);


				ComboLiberacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						//codigoitem=listaitems.get(position-1).iCodItem;
						final List<LIBERACIONRUTAS> listaplazos;
						listaplazos=LIBERACIONRUTAS_DAO.ListarPlazos(FichasRutasFragment.this.getActivity(),ComboItems.getSelectedItem().toString(),
								Integer.parseInt(ComboLiberacion.getSelectedItem().toString()));
						if(Integer.parseInt(ComboLiberacion.getSelectedItem().toString())==0){
							inicio.setText("SELECCIONE");
							finalo.setText("SELECCIONE");
						}
						else{
							String f1 =listaplazos.get(0).dFecIniciaPlazoEntrega.toString();
							String substr=f1.substring(6, 19);
							long millisecond = Long.parseLong(substr);
							String dateString= DateFormat.format("MM/dd/yyyy ", new Date(millisecond)).toString();

							//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							//Date date1 = format.parse(f1);
							inicio.setText( dateString);
							String f2 =listaplazos.get(0).dFecFinalPlazoEntrega.toString();
							String substr2 =f2.substring(6, 19);
							long millisecond2= Long.parseLong(substr2);
							String dateString2= DateFormat.format("MM/dd/yyyy ", new Date(millisecond2)).toString();
							finalo.setText(dateString2);
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});




				final int posicion =ComboItems.getSelectedItemPosition();
				final Button buttonAcept = (Button) dialog.findViewById(R.id.buttonAcept);
				buttonAcept.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (numero.length()!=0){
							String sUsername = numero.getText().toString();
							Integer numie = Integer.parseInt(numero.getText().toString());
							if (inicio.getText().equals("SELECCIONE") || finalo.getText().equals("SELECCIONE") || sUsername.length() == 0 || numie == 0) {
								Utility.alert(FichasRutasFragment.this.getActivity(), "Faltan  Datos", true);
							} else {

								int IT = LIBERACIONRUTAS_DAO.ValidarITEMS(FichasRutasFragment.this.getActivity(), 0, ComboItems.getSelectedItem().toString());
								if (IT == 0) {
									int id = LIBERACIONRUTAS_DAO.AgregarITEMSRUTA(FichasRutasFragment.this.getActivity(), 0,
											codigoitem, ComboItems.getSelectedItem().toString(), inicio.getText().toString(), finalo.getText().toString(),
											Integer.parseInt(numero.getText().toString()), Integer.parseInt(ComboLiberacion.getSelectedItem().toString()));
									Itemsgrabados();
									dialog.dismiss();
								} else {
									Utility.alert(FichasRutasFragment.this.getActivity(), "Este ITEM ya fue Registrado ", true);
								}
							}
						}
						else {
							Utility.alert(FichasRutasFragment.this.getActivity(), "Debes ingresar cantidad de IE ", true);
						}
					}



				});
				final Button buttonCancel=(Button)dialog.findViewById(R.id.buttonCancel);
				buttonCancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});

		//PARA LA PARTE DEL RESPONSABLE DE LA FICHA
		txt_vDNISupervisor = (AutoCompleteTextView) myFragmentView.findViewById(R.id.txt_vDNISupervisor);

		txt_vDNISupervisor.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String dni = txt_vDNISupervisor.getText().toString().trim();
				if(dni.length()>2)
				{
					List<TABLETSUPERVISORES> entidad =  TABLETSUPERVISORES_DAO.BuscarDNI(FichasRutasFragment.this.getActivity(),
							txt_vDNISupervisor.getText().toString().trim());
					ArrayAdapter<TABLETSUPERVISORES> adapter = new ArrayAdapter<TABLETSUPERVISORES>(FichasRutasFragment.this.getActivity(),
							android.R.layout.simple_dropdown_item_1line, entidad);
					txt_vDNISupervisor.setAdapter(adapter);

				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});





		ll_apellidos = (LinearLayout)myFragmentView.findViewById(R.id.ll_apellidos);
		txt_vApellidosSupervisor = (EditText)myFragmentView.findViewById(R.id.txt_vApellidosSupervisor);

		ll_nombres = (LinearLayout)myFragmentView.findViewById(R.id.ll_nombres);
		txt_vNombresSupervisor = (EditText)myFragmentView.findViewById(R.id.txt_vNombresSupervisor);

		btnBuscar = (Button)myFragmentView.findViewById(R.id.btnBuscar);
		btnBuscar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (txt_vDNISupervisor.getText().toString().length() >= 8) {
					final TABLETSUPERVISORES entidad = TABLETSUPERVISORES_DAO.Buscar(FichasRutasFragment.this.getActivity(),
							txt_vDNISupervisor.getText().toString().trim());

					if (entidad != null) {
						ll_apellidos.setVisibility(View.GONE);
						txt_vApellidosSupervisor.setText("");
						txt_vNombresSupervisor.setText(entidad.vNombre);
						txt_vNombresSupervisor.setEnabled(false);
					}
					else {
						AlertDialog.Builder alert = new AlertDialog.Builder(FichasRutasFragment.this.getActivity());
						alert.setTitle("SUPERVISOR NO ENCONTRADO!");
						alert.setMessage("VERIFIQUE EL NÚMERO DE DNI antes de seleccionar REGISTRAR." + "\n\n" + "¡Desea registrar sus nombres y apellidos?");
						alert.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								ll_apellidos.setVisibility(View.VISIBLE);
							 	txt_vApellidosSupervisor.setEnabled(true);
							 	txt_vNombresSupervisor.setEnabled(true);
								txt_vApellidosSupervisor.setText("");
								txt_vNombresSupervisor.setText("");
							 	txt_vApellidosSupervisor.requestFocus();
							}
						});
						alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								txt_vDNISupervisor.requestFocus();
							}
						});
						alert.show();
					}
				}
				else
					Utility.alert(FichasRutasFragment.this.getActivity(), "El DNI debe contener 8 caracteres", true);
			}
		});
		//BOTON DE LA FICHA
		buttonFicha = (Button)myFragmentView.findViewById(R.id.buttonFicha);
		buttonFicha.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						FichasRutasFragment.this
								.getActivity());
				alert.setTitle("Desea ir a Ficha");
				alert.setMessage(OBJTABLETPROVEEDORES.objSMMAEFICHAS.vDescFicha);
				alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						buttonFicha();
					}
				});
				alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
				alert.show();
			}
		});
		ComboPlaca();
		getLocation() ;
		ComboEspecialista();
		return myFragmentView;
	}

	public void ComboEspecialista()
	{
		final List<ESPECIALISTAS> lista;
		lista = TABLETESPECIALISTAS_DAO.SpinEspecialistas(FichasRutasFragment.this.getActivity(), OBJTABLETPROVEEDORES.cCodEstablecimiento);
		ArrayAdapter<ESPECIALISTAS> asd = new ArrayAdapter<ESPECIALISTAS>(FichasRutasFragment.this.getActivity(),R.layout.spinner_vista,
				lista);
		asd.setDropDownViewResource(R.layout.spinner);
		spespecialista.setAdapter(asd);
		spespecialista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				colegiatura=lista.get(position).vColegiatura.toString();
				lbl_vColegiatura.setText(colegiatura);
				profesion=lista.get(position).vProfesion.toString();
				lbl_vProfesion.setText(profesion);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}



	public void ComboPlaca() {
		//QUERY PARA JALAR LAS PLACAS AL COMBOBOX
		listaVehiculos= TABLETVEHICULOS_DAO.Listar(this.getActivity(), OBJTABLETPROVEEDORES.cCodProveedor);
		listaVehiculos.add(0,new TABLETVEHICULOS(0,"SELECCIONE"));
		ArrayAdapter<TABLETVEHICULOS> adapterEnvase = new ArrayAdapter<TABLETVEHICULOS>(this.getActivity(), R.layout.spinner_vista,listaVehiculos);
		adapterEnvase.setDropDownViewResource(R.layout.spinner);
	    ComboPlaca.setAdapter(adapterEnvase);
		ComboPlaca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (-1 < position) {
					listaRutas = TABLETVEHICULOS_DAO.ListarRutas(FichasRutasFragment.this.getActivity(), OBJTABLETPROVEEDORES.cCodProveedor, listaVehiculos.get(position).vPlaca);
					listaRutas.add(0, new RUTA("SELECCIONE"));
					ArrayAdapter<RUTA> adapterRuta = new ArrayAdapter<RUTA>(FichasRutasFragment.this.getActivity(),
							R.layout.spinner_vista,
							listaRutas);
					adapterRuta.setDropDownViewResource(R.layout.spinner);
					final int posicion = position;
					ComboRutas.setAdapter(adapterRuta);

					lbl_vNombre.setText(listaVehiculos.get(position).vNombre);
				} else {
					//lblNComiteCompras.setText("Autogenerado");
					//lblItems.setText("Autogenerado");
					//lblDireccion.setText("Autogenerado");
					//lblTipo.setText("Autogenerado");
					//buttonFicha.setVisibility(View.GONE);
				}

				//Se agrega el tercer campo de inumentrega 01/04/2015 13:13
				//setAdapter(listaContrato.get(position).iCodContrato,listaContrato.get(position).cCodEstablecimiento,listaContrato.get(position).iNumEntrega);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				//User selected same item. Nothing to do.
			}
		});
		ComboPlaca.setSelection(0);
	}

	public void Itemsgrabados(){


		ArrayList<LIBERACIONRUTAS> arrayOfUsers = LIBERACIONRUTAS_DAO.ListarSeccion(this.getActivity(),0);
		CustomUsersAdapter adapter = new CustomUsersAdapter(this.getActivity(), arrayOfUsers);
		lista1.setAdapter(adapter);
		}




	public void buttonFicha() {

		final List listaentregas=LIBERACIONRUTAS_DAO.Comparar(FichasRutasFragment.this.getActivity(),0);
		int r=listaentregas.size();
		boolean prueba=true;
		for(int a=0;a<r;a++)
		{
			if(a==r-1)
			{
				/*if (listaentregas.get(a) != listaentregas.get(r- 1)) {
					prueba = false;
				}*/
			}
			else {
				if (listaentregas.get(a) != listaentregas.get(a + 1)) {
					prueba = false;
				}
			}

		}

	if (prueba==true)
	{
		String placa =ComboPlaca.getSelectedItem().toString();
		String Rutas=ComboRutas.getSelectedItem().toString();
		int c=LIBERACIONRUTAS_DAO.ContarITEMRUTA(FichasRutasFragment.this.getActivity(), 0);
		String latitud = String.valueOf(latitude);
		String longitud= String.valueOf(longitude);
		if (fecha.getText().length()>0 && hora.getText().length()>0) {
			if(latitud!="0.0" || longitud !="0.0" ) {
				if(placa.length()<=7) {
					if(lbl_vNombre.getText().toString().length()>0) {
						if (Rutas.length()!=10) {
							if(c>0) {
								if(txt_vDNISupervisor.getText().toString().length()>0) {
									if(txt_vNombresSupervisor.getText().toString().length()>0) {
										OBJSMFICHASGRABADAS = new SMFICHASGRABADAS();
										Calendar cx = Calendar.getInstance();
										cx.set(Calendar.YEAR, year_);
										cx.set(Calendar.MONTH, month_);
										cx.set(Calendar.DAY_OF_MONTH, day_);
										cx.set(Calendar.HOUR_OF_DAY, hour_);
										cx.set(Calendar.MINUTE, minute_);
										cx.set(Calendar.MILLISECOND, 0);
										OBJSMFICHASGRABADAS.lFecha = cx.getTimeInMillis();
										long millis = System.currentTimeMillis();
										if(OBJSMFICHASGRABADAS.lFecha<millis)
										{	OBJSMFICHASGRABADAS.cCodProveedor = OBJTABLETPROVEEDORES.cCodProveedor;
											OBJSMFICHASGRABADAS.cCodEstablecimiento = OBJTABLETPROVEEDORES.cCodEstablecimiento;
											OBJSMFICHASGRABADAS.iCodFicha = OBJTABLETPROVEEDORES.objSMMAEFICHAS.iCodFicha;
											OBJSMFICHASGRABADAS.vDescFicha = OBJTABLETPROVEEDORES.objSMMAEFICHAS.vDescFicha;
											//OBJSMFICHASGRABADAS.lFecha = lFechaSupervision;
											OBJSMFICHASGRABADAS.cCategoria = "R";
											OBJSMFICHASGRABADAS.vPlaca = ComboPlaca.getSelectedItem().toString().trim();
											OBJSMFICHASGRABADAS.vTipoVehiculo = lbl_vNombre.getText().toString();
											OBJSMFICHASGRABADAS.vDatosRelevantes = ComboRutas.getSelectedItem().toString().trim();
											OBJSMFICHASGRABADAS.vApellidosSupervisor = txt_vApellidosSupervisor.getText().toString();
											OBJSMFICHASGRABADAS.vNombresSupervisor = txt_vNombresSupervisor.getText().toString();
											OBJSMFICHASGRABADAS.vNSupervisor = txt_vApellidosSupervisor.getText().toString() + " " + txt_vNombresSupervisor.getText().toString();
											OBJSMFICHASGRABADAS.vDNISupervisor = txt_vDNISupervisor.getText().toString();
											OBJSMFICHASGRABADAS.vTelefonoRepresentanteLegal=latitud;
											OBJSMFICHASGRABADAS.vEmpresaResponsableSanidad=longitud;
											OBJSMFICHASGRABADAS.vNControlCalidad=spespecialista.getSelectedItem().toString();
											OBJSMFICHASGRABADAS.vColegiatura = lbl_vColegiatura.getText().toString();
											OBJSMFICHASGRABADAS.vEspecialidad=lbl_vProfesion.getText().toString();

											GetFicha();
										}
										else Utility.alert(this.getActivity(), "La Fecha de Registro no puede ser superior a la Fecha Actual", true);
									}
									else Utility.alert(this.getActivity(), "Debes colocar Nombre de supervisor", true);
								}
								else Utility.alert(this.getActivity(), "Debes colocar DNI de supervisor", true);
							}
							else Utility.alert(this.getActivity(), "Debes Seleccionar ITEMS", true);
						}
						else Utility.alert(this.getActivity(), "Debes Seleccionar una Ruta", true);
					}
					else Utility.alert(this.getActivity(), "Debes Seleccionar Tipo de Vehiculo", true);
				}
				else Utility.alert(this.getActivity(), "Debes Seleccionar un Vehiculo", true);
			}
			else Utility.alert(this.getActivity(), "Las Coordenadas no son correctas", true);
		}
		else Utility.alert(this.getActivity(), "Falta Colocar Hora o Fecha de Registro", true);
	}
	else{ Utility.alert(this.getActivity(), "Los Numeros de Entrega no pueden ser diferentes ", true);}
	}

	public void GetFicha() {
		pd = new ProgressDialog(this.getActivity());
        pd.setTitle("GENERANDO " + OBJTABLETPROVEEDORES.objSMMAEFICHAS.vDescFicha);
        pd.setMessage("Espere un momento"); 
        pd.setCancelable(false);
        pd.show();

        new Thread() {
			public void run() {
				Message message = handlerFICHAS.obtainMessage();
                Bundle bundle = new Bundle();
				int id=SMFICHASGRABADAS_DAO.Agregar(FichasRutasFragment.this.getActivity(), OBJSMFICHASGRABADAS);
				boolean id2 =LIBERACIONRUTAS_DAO.ActualizarEstadoITEM(FichasRutasFragment.this.getActivity(),id);
				if(0<id)
				{
				}
				else
					bundle.putInt("data",0);
                message.setData(bundle);
                handlerFICHAS.sendMessage(message);
            }
       }.start();
	}





	final Handler handlerFICHAS = new Handler() {
			@SuppressLint("LongLogTag")
			@Override
			public void handleMessage(Message msg) {
				pd.dismiss();
				Bundle bundle = msg.getData();
				int total=bundle.getInt("data");
				Intent intent = new Intent(FichasRutasFragment.this.getActivity(),FichasRutasActivity.class);
				startActivity(intent);
				FichasRutasFragment.this.getActivity().finish();
				Log.e("handlerTABLETPROVEEDORES","insertando " + String.valueOf(total) + " preguntas");
			}
	};


	public Location getLocation() throws SecurityException {
		try {
			locationManager = (LocationManager) mContext
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
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
			alertDialog.setTitle("GPS desactivado");

			alertDialog.setMessage("Por favor acceda a configuración de GPS y actívelo para el registro, se cerrará la aplicación");

			alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					mContext.startActivity(intent);
					((Activity) mContext).finish();
				}
			});
			alertDialog.show();
		}
		else{
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			mContext.startActivity(intent);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		getLocation();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		getLocation();
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}



	//EVENTO FECHA
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
				fecha.setText(sdf.format(c.getTime()));
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
				dialogoPicker.getDatePicker().setMinDate(1454302800000L); // 2016/02/20 // http://currentmillis.com/
				return dialogoPicker;
			}
		};
		datePickerFragment.show(FichasRutasFragment.this.getActivity().getFragmentManager(), "datePicker");
	}
	//EVENTO HORA


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
				hora.setText(formatted);


			}
		};

		timePickerFragment.show(FichasRutasFragment.this.getActivity().getFragmentManager(), "datePicker");
	}

}
	
