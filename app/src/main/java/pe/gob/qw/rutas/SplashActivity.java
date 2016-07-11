package pe.gob.qw.rutas;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.sqlite.dao.SESION_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMFICHASGRABADAS_DAO;
import pe.gob.qw.rutas.util.Utility;

public class SplashActivity extends ActivityManagePermission {

    private KenBurnsView mKenBurns;
    private ImageView mLogo;
    private TextView welcomeText;
    private SMFICHASGRABADAS OBJSMFICHASGRABADAS=null;
    public int estado=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mLogo = (ImageView) findViewById(R.id.logo);
        welcomeText = (TextView) findViewById(R.id.welcome_text);

        setAnimation();

        askCompactPermissions(new String[]{PermissionUtils.Manifest_ACCESS_FINE_LOCATION, PermissionUtils.Manifest_ACCESS_COARSE_LOCATION, PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE}, new PermissionResult() {
            @Override
            public void permissionGranted() {
                AsyncTaskCargaDatos ATCargaDatos = new AsyncTaskCargaDatos(SplashActivity.this);
                ATCargaDatos.execute();

                OBJSMFICHASGRABADAS = SMFICHASGRABADAS_DAO.Buscar(SplashActivity.this);

                if(OBJSMFICHASGRABADAS!=null) {
                    if(OBJSMFICHASGRABADAS.iCodFicha!=17 && OBJSMFICHASGRABADAS.iCodFicha!=18 && OBJSMFICHASGRABADAS.iCodFicha!=21 && OBJSMFICHASGRABADAS.iCodFicha!=22 && OBJSMFICHASGRABADAS.iCodFicha!=23 && OBJSMFICHASGRABADAS.iCodFicha!=24 && OBJSMFICHASGRABADAS.iCodFicha!=30) {
                        if(OBJSMFICHASGRABADAS.cCategoria.equals("I")) {
                            if(OBJSMFICHASGRABADAS.iEstado==0) estado=6;
                            else if(OBJSMFICHASGRABADAS.iEstado==1) estado=7;
                        }
                        else if(OBJSMFICHASGRABADAS.cCategoria.equals("T")){
                            if(OBJSMFICHASGRABADAS.iEstado==0) estado=11;
                        }
                        else {
                            if (OBJSMFICHASGRABADAS.iEstado == 0) estado = 2;
                            else if (OBJSMFICHASGRABADAS.iEstado == 1) estado = 3;
                        }
                    }
                    else {
                        if(OBJSMFICHASGRABADAS.iCodFicha==17) {
                            if (OBJSMFICHASGRABADAS.iEstado == 0) estado = 4;
                        }
                        if(OBJSMFICHASGRABADAS.iCodFicha==18) {
                            if (OBJSMFICHASGRABADAS.iEstado == 0) estado = 5;
                        }
                        if(OBJSMFICHASGRABADAS.iCodFicha==24) {
                            if (OBJSMFICHASGRABADAS.iEstado == 0) estado = 9;
                        }
                        if(OBJSMFICHASGRABADAS.iCodFicha==30) {
                            if (OBJSMFICHASGRABADAS.iEstado == 0) estado = 10;
                        }
                        if(OBJSMFICHASGRABADAS.iCodFicha==21 || OBJSMFICHASGRABADAS.iCodFicha==22 || OBJSMFICHASGRABADAS.iCodFicha==23) {
                            if (OBJSMFICHASGRABADAS.iEstado == 0) estado = 8;
                        }
                    }
                }
                else {
                    if(SESION_DAO.Buscar(SplashActivity.this)==1) estado=1;
                }
            }

            @Override
            public void permissionDenied() {
                Utility.alert(SplashActivity.this,"Debe Aceptar todos los permisos para la ejecución de la Aplicación", false);
                finish();
            }
        });
    }

    public class AsyncTaskCargaDatos extends AsyncTask<Void, Integer, Void> {
        Context mContext;
        AsyncTaskCargaDatos(Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            publishProgress(0);
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(75);
                    publishProgress(i+1);
                }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            return null;
        }

        protected void onProgressUpdate(Integer... value) {
        }

        @Override
        protected void onPostExecute(Void result) {
            if(estado==0) {
                Intent i=new Intent(mContext, LoginActivity.class);
                startActivity(i);
                finish();
            }
            else if(estado==1) {
                Intent i=new Intent(mContext,MainActivity.class);
                startActivity(i);
                finish();
            }
            else if(estado==10) {
                Intent intent = new Intent(mContext, FichasRutasActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void setAnimation() {
        mKenBurns.setImageResource(R.drawable.login);
        animation1();
        //animation3();
    }

    private void animation1() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1000);

        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1000);

        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnimation).with(scaleXAnimation).with(scaleYAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();
    }

    private void animation3() {

        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
        alphaAnimation.setDuration(600);
        alphaAnimation.setStartDelay(1000);
        alphaAnimation.start();
    }
}
