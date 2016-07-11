package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import pe.gob.qw.rutas.entities.SECCIONFICHAGRABADA;
import pe.gob.qw.rutas.sqlite.SQLite;

public class SECCIONFICHAGRABADA_DAO {
	private static String NOMBRE_TABLA="SECCIONFICHAGRABADA";
	public static int Agregar(Context context, SECCIONFICHAGRABADA entidad)
	   {
	       int id = 0;
	       SQLite admin=new SQLite(context,null);
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       ContentValues registro=new ContentValues();
	       registro.put("idFichasGrabadas",entidad.idFichasGrabadas);  
	       registro.put("iCodFicha",entidad.iCodFicha);
	       registro.put("iCodTipoSeccion",entidad.iCodTipoSeccion);   
	       registro.put("bNoAplicaSeccion",entidad.bNoAplicaSeccion?1:0);	     
	       
	       id = (int) bd.insert(NOMBRE_TABLA, null, registro);
	       bd.close();    
	       return id;

	   } 
	

	 public  static boolean ActualizarEstado(Context context, SECCIONFICHAGRABADA entidad)
     {
		 
	    		   
		SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("bNoAplicaSeccion",entidad.bNoAplicaSeccion);
        
        int cant = bd.update(NOMBRE_TABLA, registro, "idFichasGrabadas="+entidad.idFichasGrabadas+" and iCodTipoSeccion="+entidad.iCodTipoSeccion +" and iCodFicha="+entidad.iCodFicha, null);
        bd.close();
        if(cant==1)
            return true;
        else
            return false;
       
    }
	

	 public static  SECCIONFICHAGRABADA Obtener(Context context, SECCIONFICHAGRABADA entidad)
	    {
	       SECCIONFICHAGRABADA entidadout=new SECCIONFICHAGRABADA();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	 String query="select idFichasGrabadas,iCodFicha,iCodTipoSeccion,bNoAplicaSeccion"
	             		+" from "+NOMBRE_TABLA+" where iCodFicha="+entidad.iCodFicha+ " and iCodTipoSeccion="+ entidad.iCodTipoSeccion
	        	 		+" and idFichasGrabadas=" + entidad.idFichasGrabadas;
	        	 		
	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           if(fila.moveToFirst()){
	        	   entidad.idFichasGrabadas= fila.getInt(0);
	        	   entidad.iCodFicha= fila.getInt(1);
	        	   entidad.iCodTipoSeccion=fila.getInt(2);
	        	   entidad.bNoAplicaSeccion=fila.getInt(3)==1?true:false;
	           }
	        }
	       bd.close();   
	       return entidad;
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
