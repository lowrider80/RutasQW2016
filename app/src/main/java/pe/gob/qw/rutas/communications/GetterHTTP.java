package pe.gob.qw.rutas.communications;


import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import pe.gob.qw.rutas.util.Utility;

public class GetterHTTP extends AsyncTask<Object, Integer, String>
{
	 private static HttpResponse response;
	    private static HttpEntity entity;
	    private static HttpGet getRequest;
	    private HttpClient httpClient;

	@Override
	protected String doInBackground(Object... params)
	{
        String methodname = params[0].toString();
        String url = Utility.SERVICE_URI + methodname;
        String result = null;
    
        try 
        {
        	httpClient = new DefaultHttpClient();
            getRequest = new HttpGet(url.trim());
            response = httpClient.execute(getRequest);
            entity = response.getEntity();
            
            if (null != entity)
            {
            	result = EntityUtils.toString(entity);
            }
        } 
        catch (Exception e)
        {
        	Log.e(e.getClass().getName(), e.getMessage());
        }
        return result;
	}

	@Override
	protected void onPostExecute(String result)
	{
       super.onPostExecute(result);
    }
}
