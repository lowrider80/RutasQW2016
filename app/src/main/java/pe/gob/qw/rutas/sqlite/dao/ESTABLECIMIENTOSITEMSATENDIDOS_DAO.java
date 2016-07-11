package pe.gob.qw.rutas.sqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import pe.gob.qw.rutas.sqlite.SQLite;

public class ESTABLECIMIENTOSITEMSATENDIDOS_DAO {

	private static String NOMBRE_TABLA="ESTABLECIMIENTOSITEMSATENDIDOS";
	
	

	 
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
