
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

import pe.gob.qw.rutas.entities.TABLETSUPERVISORES;
import pe.gob.qw.rutas.sqlite.SQLite;

public class TABLETSUPERVISORES_DAO
{
    private static String NOMBRE_TABLA="TABLETSUPERVISORES";

    public static int Agregar(Context context, TABLETSUPERVISORES entidad)
    {
        // TODO Auto-generated method stub
        int id = 0;
	
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        ContentValues registro=new ContentValues();
        registro.put("vNombre",entidad.vNombre);
        registro.put("cCodDocPersona",entidad.cCodDocPersona);
        registro.put("vDesCargo",entidad.vDesCargo);

        id = (int) bd.insert(NOMBRE_TABLA, null, registro);
        bd.close();

        return id;
    }

    public static int AgregarServicio(Context context, JSONArray Array) {
		// TODO Auto-generated method stub
		int i = 0;
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        for(i=0;i<Array.length();i++)
		{
            JSONObject json_data;
			try {
                json_data = Array.getJSONObject(i);
                ContentValues registro=new ContentValues();

                registro.put("vNombre",json_data.getString("vNombre"));
                registro.put("cCodDocPersona",json_data.getString("cCodDocPersona").trim());
                registro.put("vDesCargo",json_data.getString("vDesCargo"));

                int id = (int) bd.insert(NOMBRE_TABLA, null, registro);

                if(0==id)
                {
                    bd.close();
                    i=Array.length();
                    return 0;
                }else { }
            } catch (JSONException e) {
                bd.close();
				return 0;
            }
        }

		bd.close();    
	    return i;
	}

    public static List<TABLETSUPERVISORES> BuscarDNI(Context context, String cCodDocPersona){
        List<TABLETSUPERVISORES> list =new ArrayList<TABLETSUPERVISORES>();
        // Select All Query
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="select cCodDocPersona from "
                    + NOMBRE_TABLA+" as P where P.cCodDocPersona like '%"+cCodDocPersona+"%'";
            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                TABLETSUPERVISORES entidad= new TABLETSUPERVISORES();
                //entidad.vNombre=fila.getString(0).toUpperCase();
                entidad.cCodDocPersona=fila.getString(0).toUpperCase();
                //entidad.vDesCargo=fila.getString(2).toUpperCase();
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }

    public static List<TABLETSUPERVISORES> Listar(Context context)
     {
        List<TABLETSUPERVISORES> list=new ArrayList<TABLETSUPERVISORES>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
              String query="select vNombre,cCodDocPersona,vDesCargo from "
            		  +NOMBRE_TABLA+"";
                    
                    
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {

                    TABLETSUPERVISORES entidad= new TABLETSUPERVISORES();
                    entidad.vNombre=fila.getString(0);
                    entidad.cCodDocPersona=fila.getString(1);
                    entidad.vDesCargo=fila.getString(1);
               
                    list.add(entidad);
                       
                    fila.moveToNext();   
                }   
         }
        bd.close();   
        return list;
     }
     
    public static TABLETSUPERVISORES Buscar(Context context, String cCodDocPersona)
     {

         TABLETSUPERVISORES entidad=null;
    	SQLite admin=new SQLite(context,null); 
       	SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	 String query="select vNombre,cCodDocPersona,vDesCargo from "
             		+ NOMBRE_TABLA+" as P where P.cCodDocPersona like '"+cCodDocPersona+"'";
        	 
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {
            	entidad= new TABLETSUPERVISORES();
                entidad.vNombre=fila.getString(0);
                entidad.cCodDocPersona=fila.getString(1);
                entidad.vDesCargo=fila.getString(2);
            }
        }
        bd.close();   
        return entidad;
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