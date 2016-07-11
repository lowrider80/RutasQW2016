package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.SMEQUIPOS;
import pe.gob.qw.rutas.sqlite.SQLite;

public class SMEQUIPOS_DAO {
	private static String NOMBRE_TABLA="SMEQUIPOS";
	public static int Agregar(Context context, SMEQUIPOS entidad)
	   {
	       int id = 0;
	       SQLite admin=new SQLite(context,null);
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       ContentValues registro=new ContentValues();
	       registro.put("idFichasGrabadas",entidad.idFichasGrabadas);   
	       registro.put("vEquipo",entidad.vEquipo);
	       registro.put("vMarca",entidad.vMarca);
	       registro.put("vNSerie",entidad.vNSerie);
	      
	       id = (int) bd.insert(NOMBRE_TABLA, null, registro);
	       bd.close();    
	       return id;

	   } 
	
	 public static List<SMEQUIPOS> Listar(Context context, int idFichasGrabadas)
	    {
	       List<SMEQUIPOS> list=new ArrayList<SMEQUIPOS>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select iEquipos,idFichasGrabadas,"
	        			+ "vEquipo,vMarca,vNSerie from "+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas;	        	
	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   

	            	   SMEQUIPOS entidad= new SMEQUIPOS();            
	                   entidad.iEquipos=fila.getInt(0);           
	                   entidad.idFichasGrabadas=fila.getInt(1);
	                   entidad.vEquipo=fila.getString(2);
	                   entidad.vMarca=fila.getString(3);
	                   entidad.vNSerie=fila.getString(4);
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
	     bd.delete(NOMBRE_TABLA, "iEquipos="+id, null);
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
