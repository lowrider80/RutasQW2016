package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.DETALLELIBERACION;
import pe.gob.qw.rutas.sqlite.SQLite;

public class DETALLELIBERACION_DAO {
	private static String NOMBRE_TABLA="DETALLELIBERACION";
	
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
				 	registro.put("iCodContrato",json_data.getInt("iCodContrato"));  
		            registro.put("cCodProveedor",json_data.getString("cCodProveedor").trim()); 
		            registro.put("cCodEstablecimiento",json_data.getString("cCodEstablecimiento").trim());
		            registro.put("iCodLiberacion",json_data.getInt("iCodLiberacion"));   
		            registro.put("iCodDetLiberacion",json_data.getInt("iCodDetLiberacion"));
		            registro.put("dcCantidad",json_data.getDouble("dcCantidad"));
		            registro.put("iCodFichaTecnica",json_data.getInt("iCodFichaTecnica"));
		            registro.put("vNombreFichaTecnica",json_data.getString("vNombreFichaTecnica").trim());
		            registro.put("iCodFichaTecnicaPresentacion", json_data.getString("iCodFichaTecnicaPresentacion"));
		            registro.put("Presentacion", json_data.getString("vFichaTecnicaPresentacion"));
		            registro.put("Marca", json_data.getString("vMarca"));
		            registro.put("iCodDetFicha", json_data.getInt("iCodDetFicha"));
		            registro.put("dcCantidad", json_data.getInt("dcCantidad"));
		            registro.put("iNumEntrega", json_data.getString("iNumEntrega"));
		            registro.put("iCodCronogramaEntrega", json_data.getInt("iCodCronogramaEntrega"));

		                int id = (int) bd.insert(NOMBRE_TABLA, null, registro);

				Log.e("AddDetalleLiberacion","insertando " + id);
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

    
    public static List<DETALLELIBERACION> ListarxContrato(Context context, int iCodContrato, String cCodEstablecimiento, String iNumEntrega) {
		List<DETALLELIBERACION> list=new ArrayList<DETALLELIBERACION>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;

		if(iNumEntrega == "") iNumEntrega = "0";

        if(bd!=null) {
			String query="select iDETALLELIBERACION,iCodContrato,cCodProveedor,cCodEstablecimiento,iCodLiberacion,"
        			+ "iCodDetLiberacion,dcCantidad,iCodFichaTecnica,vNombreFichaTecnica,iCodCronogramaEntrega from "
        			+ NOMBRE_TABLA+" where iCodContrato= "+iCodContrato+"  AND cCodEstablecimiento like "+ cCodEstablecimiento.trim() + " AND iNumEntrega like " + iNumEntrega.trim() ;	

			//" AND iNumEntrega like "+ iNumEntrega.trim()
			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();

			for (int i = 0; i < numRows; ++i) {
				DETALLELIBERACION entidad = new DETALLELIBERACION();
				entidad.iDETALLELIBERACION = fila.getInt(0);
				entidad.iCodContrato = fila.getInt(1);
				entidad.cCodProveedor = fila.getString(2);
				entidad.cCodEstablecimiento = fila.getString(3);
				entidad.iCodLiberacion = fila.getInt(4);
				entidad.iCodDetLiberacion = fila.getInt(5);
				entidad.dcCantidad = fila.getDouble(6);
				entidad.iCodFichaTecnica = fila.getInt(7);
				entidad.vNombreFichaTecnica = fila.getString(8);
				entidad.iCodCronogramaEntrega = fila.getInt(9);
				list.add(entidad);

				fila.moveToNext();
			}
        }

		bd.close();
		return list;
    }
 
    public static void BorrarXCodCOntrato(Context context, int iCodContrato) {
   	 SQLite admin=new SQLite(context,null);
	     SQLiteDatabase bd=admin.getWritableDatabase();
	     bd.delete(NOMBRE_TABLA, "iCodContrato="+iCodContrato, null);
	     Log.e("BorrarXCodCOntrato", "" + iCodContrato);
	     bd.close();
	     
	     

   }
    
    public static void BorrarXCodCOntratoxCronograma(Context context, int iCodContrato, int iCodCronogramaEntrega) {
      	 SQLite admin=new SQLite(context,null);
   	     SQLiteDatabase bd=admin.getWritableDatabase();
   	     bd.delete(NOMBRE_TABLA, "iCodContrato="+iCodContrato+" AND iCodCronogramaEntrega="+iCodCronogramaEntrega, null);
   	     Log.e("BorrarXCodCOntrato", "" + iCodContrato);
   	     bd.close();
   	     
   	     

      }
    
    
    public static void BorrarXCodCOntratoxCronogramaxEstado(Context context, int iCodContrato, int iCodCronograma) {
     	 SQLite admin=new SQLite(context,null);
  	     SQLiteDatabase bd=admin.getWritableDatabase();
  	    
  	     
  	     
  	     if(bd != null)
  	     {
  	    	     	    	   	    
  	    
  	    	
  	    	String query="DELETE FROM DETALLELIBERACION WHERE iCodDetFicha in ( SELECT l.iCodDetFicha "
  	    	+" FROM LIBERACIONRECORDCARDITEM l INNER JOIN DETALLELIBERACION c ON (c.iCodDetFicha = l.iCodDetFicha ) "
  	    	/*+" WHERE l.iIndexCombo = 1 OR l.iIndexCombo = 2) and iCodContrato = " + iCodContrato + " and iCodCronograma = " + iCodCronograma;*/
			 +" WHERE l.iIndexCombo = 1 OR l.iIndexCombo = 2) and iCodContrato = " + iCodContrato + " and iCodCronogramaEntrega = " + iCodCronograma;



			 bd.execSQL(query);
  	    	 bd.close();
  	     }
     }
    
    public static void BorrarXCodCOntratoxEstado(Context context, int iCodContrato) {
      	 SQLite admin=new SQLite(context,null);
   	     SQLiteDatabase bd=admin.getWritableDatabase();
   	    
   	     
   	     
   	     if(bd != null)
   	     {
   	    	     	    	   	    
   	    
   	    	
   	    	String query="DELETE FROM DETALLELIBERACION "
   	    	+" WHERE iCodDetFicha in ( "
   	    	+" SELECT l.iCodDetFicha "
   	    	+" FROM LIBERACIONRECORDCARDITEM l " 
   	    	+" INNER JOIN DETALLELIBERACION  c ON (c.iCodDetFicha = l.iCodDetFicha ) "
   	    	+" WHERE l.iIndexCombo = 1 OR l.iIndexCombo = 2) and iCodContrato = " + iCodContrato; 
   	    	
   	    	
   	    	
             bd.execSQL(query);
   	    	 bd.close();
   	     }
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
