package pe.gob.qw.rutas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.sqlite.dao.DBQUESTIONARY_DAO;
import pe.gob.qw.rutas.sqlite.dao.SESION_DAO;
import pe.gob.qw.rutas.sqlite.setServicio;
import pe.gob.qw.rutas.util.Utility;

/**
 * Created by uadin12 on 25/04/2016.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    //public static final String TRAVEL = "Travel";

    private TextInputEditText mUserView;
    private TextInputEditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private View mWelcomeFormView;
    private TextView txtLoading;
    private String codUT="";
    private String nomUT="";
    private int data;
    private boolean ready = false;

    private LoginPostHTTP mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.activity_login2);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mWelcomeFormView = findViewById(R.id.welcome_form);
        mUserView = (TextInputEditText) findViewById(R.id.user);
        mPasswordView = (TextInputEditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (Utility.isOnline(LoginActivity.this)){
                    if (id == R.id.btnLogin || id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                } else Utility.alert(LoginActivity.this, "No tiene conexión a internet", false);
                return false;
            }
        });

        txtLoading = (TextView) findViewById(R.id.txtLoading);
    }

    private void attemptLogin() {

        if (mAuthTask != null) {  return; }

        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String user = mUserView.getText().toString().toUpperCase();
        String password = mPasswordView.getText().toString().toUpperCase();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid user:
        if (TextUtils.isEmpty(user)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        }

        // Valid password:
        else if (TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        /*else if (!isAcountValid(user, password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }*/

        // There was an error; don't attempt login and focus the first form field with an error.
        if (cancel) focusView.requestFocus();
        else {
            // Show a progress spinner, and kick off a background task to perform the user login attempt.
            //String data="";
            try {
                JSONObject objJSONEnvio=new JSONObject();
                objJSONEnvio.put("vUsuario",user);
                objJSONEnvio.put("vPassport", password);

                showProgress(true);

                //LoginPostHTTP service = new LoginPostHTTP();
                mAuthTask = new LoginPostHTTP();
                //service.execute("Logueo",objJSONEnvio.toString());
                mAuthTask.execute("Logueo",objJSONEnvio.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAcountValid(String user, String password) {
        //TODO: Replace this with your own logic
        return user.equals(password);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            if (Utility.isOnline(LoginActivity.this)){
                attemptLogin();
            }
            else Utility.alert(LoginActivity.this, "No tiene conexión a internet", false);
        }
    }

    private void cargandoDatos() {
        final List<String> msgLoad = new ArrayList<String>();

        //msgLoad.add("Cargando Postores");                       //0
        msgLoad.add("Cargando Proveedores");                    //1
        msgLoad.add("Cargando Supervisores");                   //2
        msgLoad.add("Cargando Especialistas");                  //3
        msgLoad.add("Cargando Lista de Vehiculos");             //4
        msgLoad.add("Cargando Lista de Liberación Rutas");      //5
        msgLoad.add("Cargando Rutas de Colegios");              //6
        //msgLoad.add("Cargando Banco de Preguntas");             //7
        //msgLoad.add("Cargando Causales");                       //8
        msgLoad.add("Cargando Formato");                        //9
        msgLoad.add("Cargando Maestro de Fichas");              //10
        //msgLoad.add("Cargando Contratos");                      //11
        //msgLoad.add("Cargando Detalles de Liberación");         //12
        //msgLoad.add("Cargando Items");                          //13
        //msgLoad.add("Cargando Componentes");                    //14
        //msgLoad.add("Cargando Ficha Tecnica de Presentación");  //15
        msgLoad.add("Cargando Fichas");                         //16

        /*new Thread() {
            public void run() {
                //Message message = handlerTABLETPOSTORES.obtainMessage();
                for (int i = 0; i < msgLoad.size(); i++) {
                    txtLoading.setText(msgLoad.get(i).toString());
                    Bundle bundle = new Bundle();
                    switch (i) {
                        case 0:
                            bundle.putInt("data", setServicio.updateTABLETPOSTORES(LoginActivity.this, codUT));
                            break;
                        case 1:
                            bundle.putInt("data", setServicio.updateTABLETPROVEEDORES(LoginActivity.this, codUT));
                            break;
                    }

                    data = bundle.getInt("data");

                    if (data == 0){
                        Utility.alert(LoginActivity.this, "Error",true);
                        break;
                    }
                }
            }
        }.start();*/
        new Thread(){
                //new Runnable() {
                    //@Override
                    public void run() {
                        Boolean bypass;
                        for (int i = 0; i < msgLoad.size(); i++) {
                            bypass = false;
                            final int ii = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    txtLoading.setText(msgLoad.get(ii).toString());
                                }
                            });

                            data = 0;
                            Bundle bundle = new Bundle();
                            switch (i) {
                                /*case 0:
                                    bundle.putInt("data", setServicio.updateTABLETPOSTORES(LoginActivity.this, codUT));
                                    bypass = true;
                                    break;*/
                                case 0:
                                    bundle.putInt("data", setServicio.updateTABLETPROVEEDORES(LoginActivity.this, codUT));
                                    break;
                                case 1:
                                    bundle.putInt("data", setServicio.updateTABLETSUPERVISORES(LoginActivity.this, codUT));
                                    break;
                                case 2:
                                    bundle.putInt("data", setServicio.updateTABLETESPECIALISTAS(LoginActivity.this, codUT));
                                    break;
                                case 3:
                                    bundle.putInt("data", setServicio.updateTABLETVEHICULOS(LoginActivity.this, codUT));
                                    //EDIT
                                    bypass = true;
                                    break;

                                case 4:
                                    bundle.putInt("data", setServicio.updateTABLETLIBERACIONRUTAS(LoginActivity.this, codUT));
                                    bypass = true;
                                    break;
                                case 5:
                                    bundle.putInt("data", setServicio.updateTABLETRUTASCOLEGIOS(LoginActivity.this, codUT));
                                    bypass = true;
                                    break;

                                /*case 7:
                                    bundle.putInt("data", setServicio.updateSMBCOPREGUNTAS(LoginActivity.this));
                                    break;*/
                                /*case 8:
                                    bundle.putInt("data", setServicio.updateSMCAUSALES(LoginActivity.this));
                                    break;*/
                                case 6:
                                    bundle.putInt("data", setServicio.updateSMFORMATO(LoginActivity.this));
                                    break;
                                case 7:
                                    bundle.putInt("data", setServicio.updateSMMAEFICHAS(LoginActivity.this));
                                    break;
                                /*case 11:
                                    bundle.putInt("data", setServicio.updateCONTRATO(LoginActivity.this, codUT));
                                    break;*/
                                /*case 12:
                                    bundle.putInt("data", setServicio.updateDETALLELIBERACION(LoginActivity.this, codUT));
                                    bypass = true;
                                    break;*/
                                /*case 7:
                                    bundle.putInt("data", setServicio.updatePROVEEDORESITEMS(LoginActivity.this, codUT));
                                    break;*/
                                /*case 14:
                                    bundle.putInt("data", setServicio.updateCOMPONENTERACION(LoginActivity.this));
                                    bypass = true;
                                    break;*/
                                /*case 15:
                                    bundle.putInt("data", setServicio.updateFICHATECNICAPRESENTACION(LoginActivity.this));
                                    break;*/
                                case 8:
                                    bundle.putInt("data", DBQUESTIONARY_DAO.AgregarFichas(LoginActivity.this));
                                    ready = true;
                                    break;
                            }

                            data = bundle.getInt("data");

                            if (data == 0 && bypass == false) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        Utility.alert(LoginActivity.this, "Error Descargando Datos", true);
                                        mWelcomeFormView.setVisibility(View.GONE);
                                        showProgress(false);
                                    }
                                });
                                break;
                            }
                        }

                        if (ready){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    if(1== SESION_DAO.Agregar(LoginActivity.this,codUT, nomUT)) {
                                        //Utility.alert(LoginActivity.this, "Tablas Cargadas", true);
                                        Intent i=new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                    else {
                                        Utility.alert(LoginActivity.this, "Error Generando Fichas", true);
                                        mWelcomeFormView.setVisibility(View.GONE);
                                        showProgress(false);
                                    }
                                }
                            });

                        }
                    }
        }.start();
    }

    private class LoginPostHTTP extends AsyncTask<Object, Integer, String> {

        private HttpResponse responseGet;
        private HttpEntity resEntityGet;
        private HttpPost httpost;
        private DefaultHttpClient httpclient;

        @Override
        protected String doInBackground(Object... params) {

            String methodname1 = params[0].toString();
            String methodname2 = params[1].toString();
            String result = null;

            httpclient = new DefaultHttpClient();
            httpost = new HttpPost(Utility.SERVICE_URI+methodname1);
            //httpost = new HttpPost("http://app.qw.gob.pe/AndroidQW/sv /"+methodname1);
            try {
                httpost.setEntity(new ByteArrayEntity(methodname2.toString().getBytes("UTF8")));
                httpost.setHeader("Accept", "application/json");
                httpost.setHeader("Content-type", "application/json;charset=UTF-8");
                httpost.setHeader("Accept-Charset", "utf-8");
                responseGet = httpclient.execute(httpost);

                resEntityGet = responseGet.getEntity();
                if (resEntityGet != null) {
                    result = EntityUtils.toString(resEntityGet).trim();
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (result == null) result = "null";
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            String data="";
            mAuthTask = null;

            JSONObject jObject;
            try {
                jObject = new JSONObject(result);
                data=jObject.getString("vCodUT").trim();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(!data.equals("null") && !data.equals("")) {
                codUT = data;//setTABLETPOSTORES();
                nomUT = mPasswordView.getText().toString().toUpperCase();
                Utility.alert(LoginActivity.this, "Bienvenido " + nomUT,true);
                mWelcomeFormView.setVisibility(View.VISIBLE);
                cargandoDatos();
                //layoutWelcome.setVisibility(View.VISIBLE);
                //user_name.setText(mPasswordView.getText().toString().toUpperCase());
            }
            else {
                showProgress(false);
                Utility.alert(LoginActivity.this, "Ingrese Usuario y Contraseña",true);
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
                //showSnack("Usuario y/o Contraseña Incorrectos");
            }
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}