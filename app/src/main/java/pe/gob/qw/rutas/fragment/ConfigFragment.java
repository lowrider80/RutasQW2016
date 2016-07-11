package pe.gob.qw.rutas.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.gob.qw.rutas.R;
import pe.gob.qw.rutas.communications.PostHTTP;
import pe.gob.qw.rutas.entities.LIBERACIONRECORDCARDITEM;
import pe.gob.qw.rutas.entities.SMDETFOTOS;
import pe.gob.qw.rutas.entities.SMDETFOTOSRUTAS;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.font.MaterialDesignIconsTextView;
import pe.gob.qw.rutas.sqlite.dao.ACTACOLEGIOSITEM_DAO;
import pe.gob.qw.rutas.sqlite.dao.LIBERACIONRECORDCARDITEM_DAO;
import pe.gob.qw.rutas.sqlite.dao.RECORDCARDITEM_DAO;
import pe.gob.qw.rutas.sqlite.dao.SESION_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMDETFOTOSRUTAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMDETFOTOS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMFICHASGRABADAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.TABLETPOSTORES_DAO;
import pe.gob.qw.rutas.sqlite.dao.TABLETPROVEEDORES_DAO;
import pe.gob.qw.rutas.sqlite.setDataService;
import pe.gob.qw.rutas.sqlite.setDataServiceLiberacion;
import pe.gob.qw.rutas.sqlite.setServicio;
import pe.gob.qw.rutas.util.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private List<SMFICHASGRABADAS> listFichas;
    private RecyclerView listEnvios;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //private ListView listEnvios;
    private ProgressDialog pd;
    private MaterialDesignIconsTextView button_senddata;
    private MaterialDesignIconsTextView button_backupdata;
    private MaterialDesignIconsTextView button_syncraciones;
    private int year_= 0, month_= 0,day_= 0;
    private boolean cerrar = true;
    private String codUT;
    private EditText txtFechaLiberacion;

    @Override
    public void onStart() {
        super.onStart();
    }

    public ConfigFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.fragment_config, container, false);

        //listEnvios = (ListView) myFragmentView.findViewById(R.id.listEnvios);
        listEnvios = (RecyclerView) myFragmentView.findViewById(R.id.listEnvios);
        listEnvios.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ConfigFragment.this.getActivity());
        listEnvios.setLayoutManager(mLayoutManager);

        ConfigFragment.this.getActivity().setTitle("Opciones de Configuración");

        button_senddata = (MaterialDesignIconsTextView) myFragmentView.findViewById(R.id.button_senddata);
        button_senddata.setOnClickListener(new OnClickSendData());

        button_backupdata = (MaterialDesignIconsTextView) myFragmentView.findViewById(R.id.button_backupdata);
        button_backupdata.setOnClickListener(new OnClickBackupData());

        button_syncraciones = (MaterialDesignIconsTextView) myFragmentView.findViewById(R.id.button_syncraciones);
        button_syncraciones.setOnClickListener(new OnClickSyncRaciones());

        setAdapter();

        return myFragmentView;
    }

    private class OnClickSendData implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            listFichas = SMFICHASGRABADAS_DAO.Listar(ConfigFragment.this.getActivity(),true);
            if(listFichas.size() > 0) {
                pd = new ProgressDialog(ConfigFragment.this.getActivity());
                pd.setTitle("Enviando Fichas");
                pd.setMessage("Espere un momento");
                pd.setCancelable(false);
                pd.show();
                new Thread() {
                    public void run() {
                        try {
                            String data ="";
                            for(SMFICHASGRABADAS entidad : listFichas) {
                                if(entidad.iEstado < 4) {
                                    int contador=0;
                                    List<SMDETFOTOS> listFotos = SMDETFOTOS_DAO.ListarTodo(ConfigFragment.this.getActivity(), entidad.idFichasGrabadas);
                                    for(SMDETFOTOS foto : listFotos) {
                                        Bitmap bmp = Utility.decodeSampledBitmapFromResource(foto.vNombreFoto, 480, 360);
                                        String json="{\"base64Foto\":\""+ Utility.getBase64(bmp)+"\",\"vNombreFoto\":\""+foto.vNombreFoto+".jpg"+"\"}";
                                        PostHTTP serviceFoto = new PostHTTP();
                                        serviceFoto.execute("getfotos",json);
                                        data = serviceFoto.get();
                                        if(data.trim().equals("1")) contador++;
                                    }

                                    if(entidad.cCategoria.trim().equals("L")) {
                                        if(entidad.iCodFicha!=18) {
                                            List<LIBERACIONRECORDCARDITEM> list = LIBERACIONRECORDCARDITEM_DAO.ListarXidFichasGrabadas(ConfigFragment.this.getActivity(), entidad.idFichasGrabadas);
                                            for(LIBERACIONRECORDCARDITEM Objeto : list) {
                                                if(Objeto.iIndexCombo == 1) Objeto.iPuntaje = 100;
                                                if(Objeto.iIndexCombo == 2) Objeto.iPuntaje = 50;

                                                Log.e("data",""+list.size());
                                                data = setDataServiceLiberacion.SendData(ConfigFragment.this.getActivity(), entidad,Objeto);

                                                PostHTTP service = new PostHTTP();
                                                service.execute("RegistrarFichaLiberacion",data);
                                                Log.e("data",service.get());
                                                JSONObject objeto = new JSONObject(service.get());

                                                if(objeto.getInt("iCodDetFicha")>0) LIBERACIONRECORDCARDITEM_DAO.BorrarXid(ConfigFragment.this.getActivity(), Objeto.iLIBERACIONRECORDCARDITEM);
                                            }

                                            list = LIBERACIONRECORDCARDITEM_DAO.ListarXidFichasGrabadas(ConfigFragment.this.getActivity(), entidad.idFichasGrabadas);

                                            if(list.size()==0)
                                                SMFICHASGRABADAS_DAO.ActualizarEstado(ConfigFragment.this.getActivity(), entidad.idFichasGrabadas, 5);
                                        }
                                        else {
                                            data = setDataService.SendDataLiberacionRaciones(ConfigFragment.this.getActivity(), entidad);

                                            PostHTTP service = new PostHTTP();
                                            service.execute("RegistrarActaLiberacionRacion",data);
                                            Log.e("data",service.get());
                                            JSONObject objeto = new JSONObject(service.get());
                                            if(objeto.getInt("iCodDetFicha")>0) {
                                                Log.e("data","entro");
                                                for(SMDETFOTOS foto : listFotos) { Utility.getDelete(foto.vNombreFoto); }
                                                SMFICHASGRABADAS_DAO.ActualizarEstado(ConfigFragment.this.getActivity(), entidad.idFichasGrabadas, 4);
                                            }
                                        }
                                    }
                                    else if(entidad.cCategoria.trim().equals("D") || entidad.cCategoria.trim().equals("V")) {
                                        data = setDataService.SendDataSeguimientoRaciones(ConfigFragment.this.getActivity(), entidad);

                                        PostHTTP service = new PostHTTP();
                                        service.execute("RegistrarFichaSeguimiento",data);
                                        Log.e("data",service.get());
                                        JSONObject objeto = new JSONObject(service.get());

                                        if(objeto.getInt("iCodDetFicha")>0) {
                                            Log.e("data","entro");
                                            for(SMDETFOTOS foto : listFotos) { Utility.getDelete(foto.vNombreFoto); }

                                            SMFICHASGRABADAS_DAO.ActualizarEstado(ConfigFragment.this.getActivity(), entidad.idFichasGrabadas, 4);
                                        }
                                    }
                                    else if(entidad.cCategoria.trim().equals("T")){
                                        data = setDataService.SendDataRutas(ConfigFragment.this.getActivity(),entidad);

                                        PostHTTP service = new PostHTTP();
                                        service.execute("RegistrarFichaSeguimiento",data);
                                        Log.e("data",service.get());
                                        JSONObject objeto = new JSONObject(service.get());
                                        if(objeto.getInt("iCodDetFicha")>0) {
                                            Log.e("data","entro");
                                            for(SMDETFOTOS foto : listFotos) { Utility.getDelete(foto.vNombreFoto); }
                                            SMFICHASGRABADAS_DAO.ActualizarEstado(ConfigFragment.this.getActivity(), entidad.idFichasGrabadas, 4);
                                        }
                                    }
                                    else if(entidad.cCategoria.trim().equals("R")) {
                                        int contador2=0;
                                        List<SMDETFOTOSRUTAS> listFotosRutas = SMDETFOTOSRUTAS_DAO.ListarTodo(ConfigFragment.this.getActivity(), entidad.idFichasGrabadas);
                                        for(SMDETFOTOSRUTAS foto2 : listFotosRutas) {
                                            //Bitmap bmp2 = Utility.getImagen(foto2.vNombreFoto);
                                            Bitmap bmp2 = Utility.decodeSampledBitmapFromResource(foto2.vNombreFoto,480,360);

                                            //String json = "{\"base64Foto\":\"" + Utility.getBase64Scale(bmp2, 480, 360) + "\",\"vNombreFoto\":\"" + foto2.vNombreFoto + ".jpg" + "\"}";
                                            String json="{\"base64Foto\":\""+ Utility.getBase64(bmp2)+"\",\"vNombreFoto\":\""+foto2.vNombreFoto+".jpg"+"\"}";
                                            PostHTTP serviceFoto = new PostHTTP();
                                            serviceFoto.execute("Getfotos", json);
                                            data = serviceFoto.get();
                                            Log.e("data", "" + data.trim());
                                            if (data.trim().equals("1"))
                                                contador2++;
                                        }

                                        data = setDataService.SendDataRutas(ConfigFragment.this.getActivity(), entidad);
                                        PostHTTP service = new PostHTTP();
                                        service.execute("RegistrarFichaSeguimiento",data);
                                        Log.e("data",service.get());
                                        JSONObject objeto = new JSONObject(service.get());
                                        if(objeto.getInt("iCodDetFicha")>0) {
                                            Log.e("data","entro");
                                            for(SMDETFOTOS foto : listFotos) { Utility.getDelete(foto.vNombreFoto); }
                                            for(SMDETFOTOSRUTAS foto : listFotosRutas) { Utility.getDelete(foto.vNombreFoto); }
                                            SMFICHASGRABADAS_DAO.ActualizarEstado(ConfigFragment.this.getActivity(), entidad.idFichasGrabadas, 4);
                                        }
                                    }
                                    else if (entidad.cCategoria.trim().equals("S")) {
                                        data = setDataService.SendDataSeguimiento(ConfigFragment.this.getActivity(), entidad) ;

                                        PostHTTP service = new PostHTTP();
                                        service.execute("RegistrarFicha",data);
                                        Log.e("data",service.get());
                                        JSONObject objeto = new JSONObject(service.get());

                                        if(objeto.getInt("iCodDetFicha")>0) {
                                            Log.e("data","entro");
                                            for(SMDETFOTOS foto : listFotos) { Utility.getDelete(foto.vNombreFoto); }

                                            SMFICHASGRABADAS_DAO.ActualizarEstado(ConfigFragment.this.getActivity(), entidad.idFichasGrabadas, 4);
                                        }
                                    }
                                    else {
                                        //pd.setTitle("Enviando Ficha");
                                        data = setDataService.SendData(ConfigFragment.this.getActivity(), entidad) ;

                                        PostHTTP service = new PostHTTP();
                                        service.execute("RegistrarFicha",data);
                                        Log.e("data",service.get());
                                        JSONObject objeto = new JSONObject(service.get());


                                        if(objeto.getInt("iCodDetFicha")>0) {
                                            Log.e("data","entro");
                                            for(SMDETFOTOS foto : listFotos) { Utility.getDelete(foto.vNombreFoto); }

                                            SMFICHASGRABADAS_DAO.ActualizarEstado(ConfigFragment.this.getActivity(), entidad.idFichasGrabadas, 4);
                                        }
                                    }
                                }
                            }
                            Message message = handlerEnvios.obtainMessage();
                            handlerEnvios.sendMessage(message);
                        }
                        catch(Exception exception) {
                            Log.e("button_senddata", exception.getMessage());
                            Message message = handlerEnvios.obtainMessage();
                            handlerEnvios.sendMessage(message);
                        }
                    }
                }.start();
            }
            else  Utility.alert(ConfigFragment.this.getActivity(), "Todos los datos fueron Eviados", true);
        }
    }

    final Handler handlerEnvios = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pd.dismiss();
            setAdapter();
            // button_senddata.setText("Cerrar Sesión");
        }
    };

    private class OnClickBackupData implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alert = new AlertDialog.Builder(ConfigFragment.this.getActivity());
            alert.setTitle("¿Desea sacar una copia de seguridad de la información de las fichas?");
            alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        //File sd = Environment. getExternalStorageDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File sd = new File(Environment.getExternalStorageDirectory() + "/SupervisionFichas/");
                        File data = Environment.getDataDirectory();
                        if (!sd.exists()) { sd.mkdir(); }
                        //if (sd.canWrite()) {

                        ConfigFragment.this.getActivity().getPackageName();
                        String currentDBPath = "/data/data/" + ConfigFragment.this.getActivity().getPackageName() + "/databases/BDQALIWARMA";
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
                        Date date = new Date();
                        String backupDBPath = "backup" + dateFormat.format(date) + ".db";
                        File currentDB = new File(currentDBPath);
                        File backupDB = new File(sd, backupDBPath);

                        if (currentDB.exists()) {
                            FileChannel src = new FileInputStream(currentDB).getChannel();
                            FileChannel dst = new FileOutputStream(backupDB).getChannel();
                            dst.transferFrom(src, 0, src.size());
                            src.close();
                            dst.close();
                        }

                        AlertDialog.Builder alertRes = new AlertDialog.Builder(ConfigFragment.this.getActivity());
                        alertRes.setTitle("Se ha generado la copia de seguridad en la ruta: '/SupervisionFichas/" + backupDBPath + "'.");
                        alertRes.setMessage("Contáctese con el área de informática de Sede Central si desea enviar la copia de seguridad y necesite soporte.");

                        alertRes.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) { }
                        });

                        alertRes.show();
                    }
                    catch (Exception e) { }
                }
            });

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) { }
            });

            alert.show();
        }
    }

    private class OnClickSyncRaciones implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final Dialog dialog = new Dialog(ConfigFragment.this.getActivity());
            dialog.setTitle("SINCRONIZAR LIBERACIÓN DE RACIONES");
            dialog.setContentView(R.layout.popup_sync_raciones);
            dialog.setCancelable(false);

            final EditText txtRUC= (EditText) dialog.findViewById(R.id.txtRUC);
            txtFechaLiberacion= (EditText) dialog.findViewById(R.id.txtFechaLiberacion);
            txtFechaLiberacion.setKeyListener(null);
            txtFechaLiberacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int validatelimit = 0;
                    mostrarCalendario(v, validatelimit);
                }

                public void mostrarCalendario(View v, int validate){
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(ConfigFragment.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );

                    dpd.dismissOnPause(true);
                    dpd.setAccentColor(getResources().getColor(R.color.colorAccent));
                    dpd.show(ConfigFragment.this.getActivity().getFragmentManager(), "Datepickerdialog");
                }
            });

            txtRUC.setText("");

            final Button buttonCancel = (Button)dialog.findViewById(R.id.buttonCancel);
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            final Button buttonAcept = (Button)dialog.findViewById(R.id.buttonAcept);
            buttonAcept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtRUC.getText().toString().length()==11 &&txtFechaLiberacion.getText().toString().trim().length()>0 ) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ConfigFragment.this.getActivity());
                        alert.setTitle("¿Desea actualizar las liberaciones de ración?");
                        alert.setMessage("Si actualiza las liberaciones se eliminarán las actas calificadas y no calificadas aún y sólo se traerá la información respectiva a los datos ingresados si existieran.");
                        alert.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog2, int whichButton) {
                                dialog2.dismiss();
                                dialog.dismiss();
                                setCOLEGIOSITEM(txtFechaLiberacion.getText().toString().trim(),txtRUC.getText().toString().trim());
                            }
                        });

                        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog2, int whichButton) { }
                        });
                        alert.show();
                    }
                    else Utility.alert(ConfigFragment.this.getActivity(), "Ingrese un RUC valido de 11 digitos y una fecha de liberación",true);

                }
            });
            dialog.show();
        }
    }

    public void setCOLEGIOSITEM(final String dFechaLiberacion, final String cRucProveedor) {
        pd = new ProgressDialog(this.getActivity());
        pd.setTitle("Cargando Liberaciones de Ración");
        pd.setMessage("Espere un momento");
        pd.setCancelable(false);
        pd.show();
        new Thread() {
            public void run() {
                Message message = handlerCOLEGIOSITEM.obtainMessage();
                Bundle bundle = new Bundle();
                codUT= SESION_DAO.BuscarUt(ConfigFragment.this.getActivity());
                bundle.putInt("data", setServicio.updateLIBERACIONRACION(ConfigFragment.this.getActivity(),codUT.trim(),dFechaLiberacion, cRucProveedor));

                message.setData(bundle);
                handlerCOLEGIOSITEM.sendMessage(message);
            }
        }.start();
    }

    final Handler handlerCOLEGIOSITEM = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pd.dismiss();
            Bundle bundle = msg.getData();
            int total=bundle.getInt("data");
            if(total > 0) Utility.alert(ConfigFragment.this.getActivity(), "Raciones de Colegios Cargados:" + total,false);
            else Utility.alert(ConfigFragment.this.getActivity(), "Compruebe su conexión a Internet o que haya registrado previamente una liberación para el proveedor y la fecha ingresada", false);
        }
    };

    private void setAdapter() {
        listFichas = SMFICHASGRABADAS_DAO.Listar(this.getActivity(),false);
        mAdapter = new MyAdapter(ConfigFragment.this.getActivity(), listFichas);
        listEnvios.setAdapter(mAdapter);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        year_ = year;
        month_ = monthOfYear;
        day_ = dayOfMonth;

        Calendar c = Calendar.getInstance();
        c.set(year_, month_, day_);
        String myFormat = "yyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        txtFechaLiberacion.setText(sdf.format(c.getTime()));
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private Activity context;
        private List<SMFICHASGRABADAS> listFichas;

        public MyAdapter(Activity context, List<SMFICHASGRABADAS> listFichas) {
            this.context = context;
            this.listFichas = listFichas;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            LinearLayout ll_button;
            TextView lblFicha;
            TextView lblTitulo;
            TextView lblEstablecimiento;
            TextView lblEstado;
            Button button_delete;

            public ViewHolder(View itemView) {
                super(itemView);
                ll_button = (LinearLayout) itemView.findViewById(R.id.ll_button);
                lblFicha = (TextView) itemView.findViewById(R.id.lblFicha);
                lblTitulo  = (TextView) itemView.findViewById(R.id.lblTitulo);
                lblEstablecimiento = (TextView) itemView.findViewById(R.id.lblEstablecimiento);

                lblEstado = (TextView) itemView.findViewById(R.id.lblEstado);
                button_delete = (Button) itemView.findViewById(R.id.button_delete);
            }
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item_ficha, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            final int idFichasGrabadas= listFichas.get(position).idFichasGrabadas;
            final int iCodFicha= listFichas.get(position).iCodFicha;

            holder.lblFicha.setText(listFichas.get(position).vDescFicha);

            if(listFichas.get(position).objSMMAEFICHAS.cCategoria.equals("I")) {
                holder.lblTitulo.setText("Dirección de Establecimiento");
                holder.lblEstablecimiento.setText(TABLETPOSTORES_DAO.BuscarRuc(context, listFichas.get(position).cCodEstablecimiento).vDireccionEstablecimiento);
            }
            else holder.lblEstablecimiento.setText(TABLETPROVEEDORES_DAO.BuscarcCodProveedor(context, listFichas.get(position).cCodProveedor).cRUC);

            if(listFichas.get(position).iEstado==2)
                holder.lblEstado.setText("Finalizada Correctamente");
            else  if(listFichas.get(position).iEstado==3)
                holder.lblEstado.setText("Finalizada Descalificada");
            else  if(listFichas.get(position).iEstado==4) {
                holder.lblEstado.setText("La Ficha se envio Correctamente");
                holder.lblEstado.setTextColor(getResources().getColor(R.color.md_green900));
            }
            else  if(listFichas.get(position).iEstado==5) {
                holder.lblEstado.setText("Envio de datos con éxito");
                holder.lblEstado.setTextColor(getResources().getColor(R.color.md_green900));
            }
            else {
                holder.lblEstado.setText("Problemas de envío");
                holder.lblEstado.setTextColor(Color.RED);
            }

            switch(iCodFicha) {
                case 7: case 8: case 17: case 18:
                    holder.ll_button.setVisibility(View.VISIBLE);
                    holder.button_delete.setVisibility(View.VISIBLE);
                    break;
                default:
                    holder.ll_button.setVisibility(View.GONE);
                    holder.button_delete.setVisibility(View.GONE);
                    break;
            }

            holder.button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ConfigFragment.this.getActivity());
                    alert.setTitle("¿Desea eliminar la ficha seleccionada?");
                    alert.setMessage("Si elimina esta ficha, no podrá enviarla.");
                    alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // OBJSMFICHASGRABADAS.iTotalPreguntas=RECORDCARDITEM_DAO.GetTotalPreguntas(FichasRacionesActivity.this,
                            // OBJSMFICHASGRABADAS.idFichasGrabadas);
                            switch(iCodFicha) {
                                case 7: case 8: case 17:
                                    RECORDCARDITEM_DAO.BorrarRecordFicha(ConfigFragment.this.getActivity(),idFichasGrabadas);
                                    SMFICHASGRABADAS_DAO.BorrarId(ConfigFragment.this.getActivity(), idFichasGrabadas);
                                    break;
                                case 18:
                                    RECORDCARDITEM_DAO.BorrarRecordFicha(ConfigFragment.this.getActivity(),idFichasGrabadas);
                                    ACTACOLEGIOSITEM_DAO.ActualizarColegiosEliminarActa(ConfigFragment.this.getActivity(), idFichasGrabadas);
                                    SMFICHASGRABADAS_DAO.BorrarId(ConfigFragment.this.getActivity(), idFichasGrabadas);
                                    break;
                                default:
                                    break;
                            }

                            setAdapter();
                            dialog.dismiss();

                        }
                    });

                    alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) { }
                    });

                    alert.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return listFichas.size();
        }


    }
}
