package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.FICHATECNICAPRESENTACION;
import pe.gob.qw.rutas.sqlite.SQLite;

public class FICHATECNICAPRESENTACION_DAO {

		private static String NOMBRE_TABLA="FICHATECNICAPRESENTACION";
		
		public static int Agregar(Context context, FICHATECNICAPRESENTACION entidad)
		   {
		       int id = 0;
		       SQLite admin=new SQLite(context,null);
		       SQLiteDatabase bd=admin.getWritableDatabase();
		       ContentValues registro=new ContentValues();
		       registro.put("iCodFichaTecnicaPresentacion",entidad.iCodFichaTecnicaPresentacion);   
		       registro.put("iCodFichaTecnica",entidad.iCodFichaTecnica);
		       registro.put("vNombre",entidad.vNombre);  
		       
		       id = (int) bd.insert(NOMBRE_TABLA, null, registro);
		       bd.close();    
		       return id;

		   } 
		
		
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
					 	registro.put("iCodFichaTecnicaPresentacion",json_data.getInt("iCodFichaTecnicaPresentacion"));  
			            registro.put("iCodFichaTecnica",json_data.getInt("iCodFichaTecnica")); 
			            registro.put("vNombre",json_data.getString("vNombre").trim());

			            int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
						//Log.e("AddFichaTecnica","insertando " + id);
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
		
		 public static List<FICHATECNICAPRESENTACION> Listar(Context context, int iCodFichaTecnica)
		    {
		       List<FICHATECNICAPRESENTACION> list=new ArrayList<FICHATECNICAPRESENTACION>();
		       SQLite admin=new SQLite(context,null); 
		       SQLiteDatabase bd=admin.getWritableDatabase();
		       Cursor fila;
		       int numRows;
		        if(bd!=null)
		        {
		        	String query="select iCodFichaTecnicaPresentacion,iCodFichaTecnica,"
		        			+ "vNombre from "+ NOMBRE_TABLA+" where iCodFichaTecnica="+iCodFichaTecnica;	        	
		           fila=bd.rawQuery(query,null);
		           numRows = fila.getCount();   
		           fila.moveToFirst();   
		               for (int i = 0; i < numRows; ++i) 
		               {   

		            	   FICHATECNICAPRESENTACION entidad= new FICHATECNICAPRESENTACION();            
		                   entidad.iCodFichaTecnicaPresentacion=fila.getInt(0);             
		                   entidad.iCodFichaTecnica=fila.getInt(1);
		                   entidad.vNombre=fila.getString(2);
		                 
		                   list.add(entidad);
		                      
		                   fila.moveToNext();   
		               }   
		           
		        }
		       bd.close();   
		       return list;
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
