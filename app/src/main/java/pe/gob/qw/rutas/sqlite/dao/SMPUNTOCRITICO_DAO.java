package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.SMPUNTOCRITICO;
import pe.gob.qw.rutas.sqlite.SQLite;

public class SMPUNTOCRITICO_DAO {
	private static String NOMBRE_TABLA="SMPUNTOCRITICO";
	public static int Agregar(Context context, SMPUNTOCRITICO entidad)
	   {
	       int id = 0;
	       SQLite admin=new SQLite(context,null);
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       ContentValues registro=new ContentValues();
	       registro.put("idFichasGrabadas",entidad.idFichasGrabadas);   
	       registro.put("vPuntoCriticoControl",entidad.vPuntoCriticoControl);
	       registro.put("vLimiteCritico",entidad.vLimiteCritico);
	       registro.put("vFrecuenRevision",entidad.vFrecuenRevision);	       
	       registro.put("vCalibracionFrecuencia",entidad.vCalibracionFrecuencia);	  
	       
	       id = (int) bd.insert(NOMBRE_TABLA, null, registro);
	       bd.close();    
	       return id;

	   } 
	
	 public static List<SMPUNTOCRITICO> Listar(Context context, int idFichasGrabadas)
	    {
	       List<SMPUNTOCRITICO> list=new ArrayList<SMPUNTOCRITICO>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select iPuntoCritico,idFichasGrabadas,vPuntoCriticoControl,"
	        			+ "vLimiteCritico,vFrecuenRevision,vCalibracionFrecuencia from "+ NOMBRE_TABLA
	        			+" where idFichasGrabadas="+idFichasGrabadas;	        	
	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   

	            	   SMPUNTOCRITICO entidad= new SMPUNTOCRITICO();            
	                   entidad.iPuntoCritico=fila.getInt(0);              
	                   entidad.idFichasGrabadas=fila.getInt(1);
	                   entidad.vPuntoCriticoControl=fila.getString(2);
	                   entidad.vLimiteCritico=fila.getString(3);
	                   entidad.vFrecuenRevision=fila.getString(4);
	                   entidad.vCalibracionFrecuencia=fila.getString(5);
	                 
	                   list.add(entidad);
	                      
	                   fila.moveToNext();   
	               }   
	           
	        }
	       bd.close();   
	       return list;
	    }
	 
	 
	 public static void BorrarId(Context context, int id) {
    	 SQLite admin=new SQLite(context,null);
	     SQLiteDatabase bd=admin.getWritableDatabase();
	     bd.delete(NOMBRE_TABLA, "iPuntoCritico="+id, null);
	     bd.close();
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
