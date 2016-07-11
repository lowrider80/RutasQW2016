package pe.gob.qw.rutas.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Base64;
import android.view.Gravity;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    private static String urlToPing="www.google.com.pe";
    public static String fileImage="QALIWARMA/Image";


    //public final static String SERVICE_URI = "http://app.qaliwarma.gob.pe/FichasQW2016/svAndroid.svc/";//MIDIS
    //public final static String SERVICE_URI = "http://app.qaliwarma.gob.pe/lowrider/svAndroid.svc/";
    public final static String SERVICE_URI = "http://app.qaliwarma.gob.pe/lowrider/svAndroid.svc/";

    static public double redondear( double numero, int decimales ) {
	    return Math.round(numero* Math.pow(10,decimales))/ Math.pow(10,decimales);
	  }
	
	static public void alert(Context context, String message, boolean voz) {
		CharSequence text = message;
		int duration = Toast.LENGTH_LONG;
		
		CustomToast miToast = new CustomToast(context,duration);
		miToast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
		miToast.show(text);
		if(voz) {
			ToSpeech a=new ToSpeech(context, message);
		}
	}

    public static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    public static File SetImage(String nombre) {
    	  File imagesFolder = new File(Environment.getExternalStorageDirectory(), fileImage);
    		        imagesFolder.mkdirs();   
    		       return new File(imagesFolder, nombre+".jpg");
    }
    
    public static boolean getDelete(String nombre) {
         File imageFile = new File(Environment.getExternalStorageDirectory(),fileImage+"/"+nombre+".jpg");
         if(imageFile.exists()) {
        	 if(imageFile.delete())
        		 return true;
        	 else
        		 return false; 
         }
         return false;
    }

    public static boolean getRename(String nombre1, String nombre2) {

        File directory = new File(Environment.getExternalStorageDirectory(),fileImage);
        File from      = new File(directory, "/"+nombre1+".jpg");
        File to        = new File(directory, "/"+nombre2+".jpg");

        //File imageFile = new File(Environment.getExternalStorageDirectory(),fileImage+"/"+nombre+".jpg");
        //if(from.exists())
        //{
            if(from.renameTo(to))
                return true;
            else
                return false;
        //}

        //return false;

    }

    public static Bitmap getImagen(String nombre) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        Bitmap imagen=null;

        File imageFile = new File(Environment.getExternalStorageDirectory(),fileImage+"/"+nombre+".jpg");
        if(imageFile.exists()) {
            imagen = BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
        }

        return imagen;
    }
    
    @SuppressLint("SimpleDateFormat")
	public static String getFecha(Date fecha) {
        SimpleDateFormat e=new SimpleDateFormat("yyyy-MM-dd");
        return e.format(fecha);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getFechaHora(Date fecha) {
        SimpleDateFormat e=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return e.format(fecha);
    }
    
    public static String getHora(Date fecha) {
        SimpleDateFormat e=new SimpleDateFormat("HH:mm");
        return e.format(fecha);
    }
    
    public static String getBase64(Bitmap bm) {
       String dato64="";
       if (bm != null) {
       byte[] Imagen;
           ByteArrayOutputStream stream = new ByteArrayOutputStream();
           bm.compress(Bitmap.CompressFormat.JPEG, 90, stream);
           Imagen= stream.toByteArray();
           dato64= Base64.encodeToString(Imagen, Base64.NO_WRAP);
       }
       return dato64;
    }
   
    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
       int width = bm.getWidth();
       int height = bm.getHeight();
       float scaleWidth = ((float) newWidth) / width;
       float scaleHeight = ((float) newHeight) / height;
       // CREATE A MATRIX FOR THE MANIPULATION
       Matrix matrix = new Matrix();
       // RESIZE THE BIT MAP
       matrix.postScale(scaleWidth, scaleHeight);

       // "RECREATE" THE NEW BITMAP
       Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
       return resizedBitmap;
    }

    public static String getBase64Scale(Bitmap bm, int newHeight, int newWidth) {
        String dato64="";
        if (bm != null) {
            byte[] Imagen;
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            Imagen= stream.toByteArray();
            dato64= Base64.encodeToString(Imagen, Base64.NO_WRAP);

        }
        return dato64;
    }

    public static Bitmap getImagenCompres(String nombre) {
        Bitmap imagen=null;

        File imageFile = new File(Environment.getExternalStorageDirectory(),fileImage+"/"+nombre+".jpg");
        if(imageFile.exists()) {
            BitmapFactory.Options imageOpts = new BitmapFactory.Options();

            imageOpts.inSampleSize = 2;
            imagen = Bitmap.createScaledBitmap (BitmapFactory.decodeFile(imageFile.getAbsolutePath(), imageOpts), 96, 96, false);
        }

        return imagen;
    }

    public static Bitmap decodeSampledBitmapFromResource(String nombre, int reqWidth, int reqHeight) {

        File imageFile = new File(Environment.getExternalStorageDirectory(),fileImage+"/"+nombre+".jpg");
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static boolean isOnline(Context context) {
        NetworkInfo netinfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if ((netinfo == null || !netinfo.isConnected()) /*||!isOnlineNet()*/) {
            return false;
        }
        return true;
    }

    public static boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 "+urlToPing);

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
