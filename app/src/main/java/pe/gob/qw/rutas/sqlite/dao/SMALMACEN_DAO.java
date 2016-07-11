package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.SMALMACEN;
import pe.gob.qw.rutas.sqlite.SQLite;

public class SMALMACEN_DAO {
	private static String NOMBRE_TABLA="SMALMACEN";
	public static int Agregar(Context context, SMALMACEN entidad)
	   {
	       int id = 0;
	       SQLite admin=new SQLite(context,null);
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       ContentValues registro=new ContentValues();
	       registro.put("idFichasGrabadas",entidad.idFichasGrabadas);   
	       registro.put("vAlmacen",entidad.vAlmacen);
	       registro.put("vArea",entidad.vArea);       
	       
	       id = (int) bd.insert(NOMBRE_TABLA, null, registro);
	       bd.close();    
	       return id;

	   } 
	
	 public static List<SMALMACEN> Listar(Context context, int idFichasGrabadas)
	    {
	       List<SMALMACEN> list=new ArrayList<SMALMACEN>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select iAlmacen,idFichasGrabadas,"
	        			+ "vAlmacen,vArea from "+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas;	        	
	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   

	            	   SMALMACEN entidad= new SMALMACEN();            
	                   entidad.iAlmacen=fila.getInt(0);             
	                   entidad.idFichasGrabadas=fila.getInt(1);
	                   entidad.vAlmacen=fila.getString(2);
	                   entidad.vArea=fila.getString(3);
	                 
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
	     bd.delete(NOMBRE_TABLA, "iAlmacen="+id, null);
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
