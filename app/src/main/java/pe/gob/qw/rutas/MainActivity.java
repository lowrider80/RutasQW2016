package pe.gob.qw.rutas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.adapter.DrawerAdapter;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.entities.TABLETPOSTORES;
import pe.gob.qw.rutas.entities.TABLETPROVEEDORES;
import pe.gob.qw.rutas.fragment.ConfigFragment;
import pe.gob.qw.rutas.fragment.FichasRutasFragment;
import pe.gob.qw.rutas.fragment.MainFragment;
import pe.gob.qw.rutas.model.DrawerModel;
import pe.gob.qw.rutas.sqlite.dao.LIBERACIONRUTAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SESION_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMFICHASGRABADAS_DAO;
import pe.gob.qw.rutas.sqlite.dao.TABLETPROVEEDORES_DAO;
import pe.gob.qw.rutas.util.Utility;

public class MainActivity extends AppCompatActivity {

    private String nomUT, codUT;
    private Toolbar mToolbar;
    private ListView mDrawerList;
    private ArrayList<DrawerModel> list;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    NiftyDialogBuilder customDialog, customDialog2, customDialog3, customDialog4, customDialog5;

    private List<TABLETPROVEEDORES> entidad;
    private TABLETPOSTORES entidadi;
    private List<SMFICHASGRABADAS> listFichas;
    private ProgressDialog pd ;
    private int posicion;
    private Fragment InicioFragment;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private Handler mHandler;

    private boolean mShouldFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        customDialog = NiftyDialogBuilder.getInstance(this);
        customDialog2 = NiftyDialogBuilder.getInstance(this);
        customDialog3 = NiftyDialogBuilder.getInstance(this);
        customDialog4 = NiftyDialogBuilder.getInstance(this);
        customDialog5 = NiftyDialogBuilder.getInstance(this);

        nomUT= SESION_DAO.BuscarNombUt(this);
        codUT= SESION_DAO.BuscarCodUt(this);

        //getSupportActionBar().setTitle("Fichas QW");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.list_view);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        prepareNavigationDrawerItems();

        View headerView = getLayoutInflater().inflate(R.layout.nav_header_main, mDrawerList, false);
        mDrawerList.addHeaderView(headerView);// Add header before adapter (for pre-KitKat)

        ImageView logo = (ImageView) mDrawerList.findViewById(R.id.imageView);
        TextView txtUT = (TextView) mDrawerList.findViewById(R.id.txtUT);
        txtUT.setText("Hola, " + nomUT);

        int productImageId = getResources().getIdentifier("q"+codUT, "drawable", getPackageName());

        if (productImageId == 0) productImageId = getResources().getIdentifier("sin_imagen", "drawable", getPackageName());
        //asd
        //dfgs
        Picasso.with(this)
                .load(productImageId)
                .noFade().resize(80,0).error(R.drawable.sin_imagen).into(logo);

        mDrawerList.setAdapter(new DrawerAdapter(this, list));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        int color = getResources().getColor(R.color.material_grey_100);
        //color = Color.argb(0xCD, Color.red(color), Color.green(color),Color.blue(color));
        mDrawerList.setBackgroundColor(color);
        //mDrawerList.setBackgroundResource(R.drawable.selector_background_drawer);
        //mDrawerList.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mDrawerList.getLayoutParams().width = (int) getResources()
                .getDimension(R.dimen.drawer_width);

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mHandler = new Handler();

        if (savedInstanceState == null) {
            int position = 1;
            selectItem(position, list.get(position-1).getId());
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (!mShouldFinish && !mDrawerLayout.isDrawerOpen(mDrawerList)) {
            Toast.makeText(getApplicationContext(), "Presiona nuevamente retroceder para salir.", Toast.LENGTH_SHORT).show();
            mShouldFinish = true;
            mDrawerLayout.openDrawer(mDrawerList);
        } else if (!mShouldFinish && mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            if (mDrawerList.getCheckedItemPosition()>1) {
                //Utility.alert(MainActivity.this, "Test", false);
                selectItem(1, list.get(0).getId());
                mDrawerLayout.openDrawer(GravityCompat.START);
                mShouldFinish = false;
            }
            else {
                super.onBackPressed();
            }
        }
    }

    private void prepareNavigationDrawerItems() {
        list = new ArrayList<>();
        list.add(new DrawerModel(0, "", "Inicio", R.drawable.ic_home_black));
        list.add(new DrawerModel(1, "", "Verificación de Rutas", R.drawable.ic_assignment_black));
        list.add(new DrawerModel(2, "", "Configuración", R.drawable.ic_menu_manage_black));
        list.add(new DrawerModel(3, "", "Cerrar Sesión", R.drawable.ic_menu_logout_black));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        /*if (id == R.id.action_logout) {
            logout();

            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        listFichas = SMFICHASGRABADAS_DAO.Listar(this,false);

        if(listFichas.size()>0) {

            customDialog3
                    .withTitle("Cerrar Sesión")                                  //.withTitle(null)  no title
                    .withTitleColor("#FFFFFF")                                  //def
                    .withDividerColor("#FFFFFF")                              //def
                    .withMessage("Tiene fichas pendientes de envío, ¿Desea cerrar sesión? (Se eliminará toda su información, incluyendo las fichas pendientes de envío, y no podrán ser recuperadas)")                     //.withMessage(null)  no Msg
                    .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                    .withDialogColor(getResources().getColor(R.color.colorAccent))                               //def  | withDialogColor(int resid)
                    .withDuration(300)                                          //def
                    .withEffect(Effectstype.Slidetop)                                         //def Effectstype.Slidetop
                    //.withButton1Text("CANCELAR")                                      //def gone
                    //.withButton2Text("ACEPTAR")
                    .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                    .setCustomView(R.layout.text_calificar, this)         //.setCustomView(View or ResId,context)
                    .findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pd = new ProgressDialog(MainActivity.this);
                    pd.setTitle("Cerrando sesión");
                    pd.setMessage("Espere un momento");
                    pd.setCancelable(false);
                    pd.show();
                    new Thread() {
                        public void run() {
                            customDialog4.dismiss();
                            SESION_DAO.BorrarData(MainActivity.this,true);
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                            pd.dismiss();

                            finish();
                        }
                    }.start();
                }
            });

            customDialog3
                    .findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startSynchronization();
                    customDialog3.dismiss();
                }
            });

            customDialog3.isCancelable(false).show();
        }
        else {

            customDialog4
                    .withTitle("Cerrar Sesión")                                  //.withTitle(null)  no title
                    .withTitleColor("#FFFFFF")                                  //def
                    .withDividerColor("#FFFFFF")                              //def
                    .withMessage("No tiene fichas pendientes de envío, ¿Desea continuar cerrando la sesión?")                     //.withMessage(null)  no Msg
                    .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                    .withDialogColor(getResources().getColor(R.color.colorAccent))                               //def  | withDialogColor(int resid)
                    .withDuration(300)                                          //def
                    .withEffect(Effectstype.Slidetop)                                         //def Effectstype.Slidetop
                    //.withButton1Text("CANCELAR")                                      //def gone
                    //.withButton2Text("ACEPTAR")
                    .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                    .setCustomView(R.layout.text_calificar, this)         //.setCustomView(View or ResId,context)
                    .findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pd = new ProgressDialog(MainActivity.this);
                    pd.setTitle("Cerrando sesión");
                    pd.setMessage("Espere un momento");
                    pd.setCancelable(false);
                    pd.show();
                    new Thread() {
                        public void run() {
                            customDialog4.dismiss();
                            SESION_DAO.BorrarData(MainActivity.this,true);
                            Intent i=new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                            pd.dismiss();

                            finish();

                        }
                    }.start();
                }
            });

            customDialog4
                    .findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog4.dismiss();
                }
            });

            customDialog4.isCancelable(false).show();
        }
    }

    public void startSynchronization() {
        /*Fragment InicioFragment = new EnviosReloadedFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, InicioFragment);
        transaction.addToBackStack(null);
        transaction.commit();*/
        mDrawerList.setItemChecked(0, true);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) { // because we have header, we skip clicking on it
                selectItem(position, list.get(position-1).getId());
            }

        }
    }

    private void selectItem(int position, int drawerTag) {
        if (position < 1) { // because we have header, we skip clicking on it
            return;
        }

        getFragmentByDrawerTag(drawerTag);

        //if (mDrawerList.getSelectedItemPosition()>0)
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void getFragmentByDrawerTag(int drawerTag) {
        InicioFragment = null;
        //Fragment fragment;
        //Fragment fragment = null;
        if (drawerTag == 0) {
            InicioFragment = new MainFragment();
            commitFragment(InicioFragment);
        }
        else if (drawerTag == 1) {
            buscarEstablecimiento(drawerTag);
        }
        else if (drawerTag == 2){
            InicioFragment = new ConfigFragment();
            commitFragment(InicioFragment);
        }
        else if (drawerTag == 3){
            logout();
        }
        else {
            InicioFragment = new Fragment();
            commitFragment(InicioFragment);
        }

        mShouldFinish = false;
        //return fragment;
    }


    private void buscarEstablecimiento(int drawerTagx){
        entidad = null;
        final int drawerTag = drawerTagx;

        customDialog
                .withTitle(null)                                  //.withTitle(null)  no title
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor(getResources().getColor(R.color.colorAccent))                               //def  | withDialogColor(int resid)
                .withDuration(300)                                          //def
                .withEffect(Effectstype.Slideleft)                                         //def Effectstype.Slidetop
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .setCustomView(R.layout.text_establecimiento, this)         //.setCustomView(View or ResId,context)
                .findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextInputEditText txtEstablecimiento = (TextInputEditText) customDialog.findViewById(R.id.txtEstablecimiento);

                if (txtEstablecimiento.getText().toString().length() > 0) {

                    entidad= TABLETPROVEEDORES_DAO.BuscarProveedoresxEstablecimientov2(MainActivity.this, txtEstablecimiento.getText().toString().trim(),"SI","R" );

                    if (entidad != null) {
                        if (entidad.size() > 0) {
                            if (entidad.size() > 1) {
                                customDialog2
                                        .withTitle(null)                                  //.withTitle(null)  no title
                                        //.withTitleColor("#FFFFFF")                                  //def
                                        //.withDividerColor("#FFFFFF")                              //def
                                        //.withMessage("Si descalifica al astablecimiento, esta ficha se guardará y no podrá modificarla.")                     //.withMessage(null)  no Msg
                                        .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                                        .withDialogColor(getResources().getColor(R.color.colorAccent))                               //def  | withDialogColor(int resid)
                                        .withDuration(200)                                          //def
                                        .withEffect(Effectstype.Fadein)                                         //def Effectstype.Slidetop
                                        .isCancelableOnTouchOutside(false)                           //def    | isCancelable(true)
                                        .setCustomView(R.layout.text_proveedores, MainActivity.this);         //.setCustomView(View or ResId,context);

                                final RadioGroup rG = (RadioGroup) customDialog2.findViewById(R.id.rgroupProveedores);

                                for (int i = 0; i < entidad.size(); i++) {
                                    RadioButton rb = new RadioButton(MainActivity.this);
                                    rb.setText((String) entidad.get(i).vNombreProveedor);
                                    //rb.setTextColor(Color.rgb(255, 255, 255));
                                    rb.setTag(entidad.get(i).cCodProveedor);
                                    rb.setId(i);

                                    rG.addView(rb);
                                }

                                customDialog2.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int selected = rG.getCheckedRadioButtonId();

                                        if (selected >= 0) {

                                            posicion = selected;
                                            LIBERACIONRUTAS_DAO.BorrarItems(MainActivity.this,0);
                                            InicioFragment = new FichasRutasFragment(entidad.get(posicion),MainActivity.this);
                                            commitFragment(InicioFragment);

                                            setTitle(list.get(drawerTag).getText());

                                        } else {
                                            Utility.alert(MainActivity.this, "Seleccione un proveedor", true);
                                        }

                                        customDialog2.dismiss();
                                        customDialog.dismiss();
                                    }
                                });

                                customDialog2
                                        .findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        customDialog2.dismiss();
                                    }
                                });

                                customDialog2.isCancelable(false).show();
                            }
                            else {
                                posicion = 0;

                                LIBERACIONRUTAS_DAO.BorrarItems(MainActivity.this, 0);
                                InicioFragment = new FichasRutasFragment(entidad.get(posicion), MainActivity.this);
                                commitFragment(InicioFragment);

                                setTitle(list.get(drawerTag).getText());
                                customDialog.dismiss();
                            }
                        }
                        else Utility.alert(MainActivity.this, "Establecimiento no encontrado", true);
                    }
                    else Utility.alert(MainActivity.this, "Establecimiento no encontrado", true);
                }
                else Utility.alert(MainActivity.this, "Ingrese codigo de establecimiento", true);
            }
        });

        customDialog
                .findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        customDialog.isCancelable(true).show();
    }

    public void commitFragment(Fragment fragment) {
        // Using Handler class to avoid lagging while
        // committing fragment in same time as closing
        // navigation drawer
        mHandler.post(new CommitFragmentRunnable(fragment));
    }

    private class CommitFragmentRunnable implements Runnable {

        private Fragment fragment;

        public CommitFragmentRunnable(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void run() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
        }
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
