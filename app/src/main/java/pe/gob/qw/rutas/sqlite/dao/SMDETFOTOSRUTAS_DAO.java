package pe.gob.qw.rutas.sqlite.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.SMDETFOTOSRUTAS;
import pe.gob.qw.rutas.sqlite.SQLite;

public class SMDETFOTOSRUTAS_DAO {
    
    private static String NOMBRE_TABLA="SMDETFOTOSRUTAS";
    
	public static int Agregar(Context context, SMDETFOTOSRUTAS entidad) {
		// TODO Auto-generated method stub
		int id = 0;
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        ContentValues registro=new ContentValues();
        registro.put("idFichasGrabadas",entidad.idFichasGrabadas);
        registro.put("vNombreFoto",entidad.vNombreFoto);
        registro.put("vModular",entidad.vModular);
        registro.put("vLatitud",entidad.vLatitud);
        registro.put("vLongitud",entidad.vLongitud);
        registro.put("dtFecReg",entidad.dtFecReg);
        id = (int) bd.insert(NOMBRE_TABLA, null, registro);    
        bd.close();    
        Log.e("Agregar ", "............................................" + id + " registros");
        return id;
	}

	


     public static List<String> Listar(Context context, int idFichasGrabadas)
     {
        List<String> list=new ArrayList<String>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
            String query="select vNombreFoto from "+NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas;
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {   Log.e("ListFotos ", "............................................" + numRows + " registros");
                    list.add(fila.getString(0));
                    fila.moveToNext();   
                }   
         }
        bd.close();   
        return list;
     }
     
     public static List<SMDETFOTOSRUTAS> ListarTodo(Context context, int idFichasGrabadas)
	    {
	       List<SMDETFOTOSRUTAS> list=new ArrayList<SMDETFOTOSRUTAS>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select iCodDetFoto,idFichasGrabadas,"
	        			+ "vNombreFoto,vModular,vLatitud,vLongitud,dtFecReg from "
	        			+ NOMBRE_TABLA+" WHERE idFichasGrabadas="+idFichasGrabadas;	        	
	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {
                       SMDETFOTOSRUTAS entidad= new SMDETFOTOSRUTAS();
	                   entidad.iCodDetFoto=fila.getInt(0);             
	                   entidad.idFichasGrabadas=fila.getInt(1);
	                   entidad.vNombreFoto=fila.getString(2);
                       entidad.vModular=fila.getString(3);
	                   entidad.vLatitud=fila.getDouble(4);
	                   entidad.vLongitud=fila.getDouble(5);
	                   entidad.dtFecReg=fila.getString(6);
	                   list.add(entidad);
	                   fila.moveToNext();   
	               }
	        }
	       bd.close();   
	       return list;
	    }

     public static void BorrarNombre(Context context, String vNombreFoto) {
    	 SQLite admin=new SQLite(context,null);
	     SQLiteDatabase bd=admin.getWritableDatabase();
	     bd.delete(NOMBRE_TABLA, "vNombreFoto like '"+vNombreFoto+"'", null);
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


    public static int VerificarFoto(Context context, int idFichasGrabadas, String MODULAR) {
        int numFoto = -1;
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila;
        if (bd != null) {
            String query = "select count (*) from "+NOMBRE_TABLA+ " where   vModular='"+MODULAR+"' and idfichasgrabadas= " + idFichasGrabadas;
            fila = bd.rawQuery(query, null);
            if (fila.moveToFirst()) {
                numFoto = fila.getInt(0);
            }
        }
        bd.close();
        if (numFoto < 0)
            numFoto = -1;
        return numFoto;
    }

    public static void BorrarFoto(Context context, int idFichasGrabadas, String MODULAR) {
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        bd.delete(NOMBRE_TABLA, "vModular='"+MODULAR+"' and idfichasgrabadas= " + idFichasGrabadas, null);
        bd.close();
    }


}