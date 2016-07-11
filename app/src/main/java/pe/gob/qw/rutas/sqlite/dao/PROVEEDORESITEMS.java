package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.gob.qw.rutas.sqlite.SQLite;

public class PROVEEDORESITEMS {
	
	
private static String NOMBRE_TABLA="PROVEEDORESITEMS";
	
    public static int AgregarServicio(Context context, JSONArray Array)
    {
        int i;
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        for(i=0;i<Array.length();i++)
		{           
			try {
				JSONObject json_data = Array.getJSONObject(i);
				 ContentValues registro=new ContentValues();
				 	registro.put("iCodUnidad",json_data.getInt("codigoUnidadTerritorial"));  
				 	registro.put("cCodEstablecimiento",json_data.getString("codigoEstablecimiento"));  
		            registro.put("cCodProveedor",json_data.getString("codigoProveedor").trim()); 
		            //registro.put("vNombreProveedor",json_data.getString("nombreProveedor").trim());		            
		            registro.put("vNombreItem",json_data.getString("nombreItem"));
		            registro.put("iCodItem",json_data.getInt("codigoItem"));  
		            registro.put("iTipoFicha",json_data.getInt("iTipoFicha")); 
		     
		            
		            int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
		            Log.e("AgregarServicio", "insertando " + id);
				      
		            if(0==id)
		            {
		            	bd.close();    
		            	i=Array.length();
		            	return 0;
		            	
		            }
				} catch (JSONException e) {
					bd.close();    
					return 0;
				}
			}
	       
	        bd.close();    
	        return i;

    }

	public static void Borrar(Context context) {
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		bd.delete(NOMBRE_TABLA, null, null);
		bd.close();
	}

    public static void BorrarData(Context context){
    	SQLite admin=new SQLite(context,null); 
        	SQLiteDatabase bd=admin.getWritableDatabase();
          if(bd!=null)
          {
             String query="delete from "+NOMBRE_TABLA;
         
             bd.execSQL(query);
             
         }
         bd.close();   
     }

}
