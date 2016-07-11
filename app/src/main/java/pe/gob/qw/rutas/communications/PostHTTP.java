package pe.gob.qw.rutas.communications;


import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import pe.gob.qw.rutas.util.Utility;

public class PostHTTP extends AsyncTask<Object, Integer, String>
{
	
    private static HttpResponse responseGet;
    private static HttpEntity resEntityGet;
    private static HttpPost httpost;
    private DefaultHttpClient httpclient;

	@Override
	protected String doInBackground(Object... params)
	{

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
            	result =EntityUtils.toString(resEntityGet).trim();
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
        return result;
	}

	@Override
	protected void onPostExecute(String result)
	{
       super.onPostExecute(result);
    }
	
	
}
