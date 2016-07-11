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

import pe.gob.qw.rutas.entities.RUTASCOLEGIOS;
import pe.gob.qw.rutas.sqlite.SQLite;

/**
 * Created by uadin11 on 11/03/2016.
 */
public class RUTASCOLEGIOS_DAO {

    private static String NOMBRE_TABLA="RUTASCOLEGIOS";


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
                registro.put("cCodColegio",json_data.getString("cCodColegio"));
                registro.put("vNombre",json_data.getString("vNombre"));
                registro.put("cCodModular",json_data.getString("cCodModular"));

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



    public static List<RUTASCOLEGIOS> BuscarModular(Context context, String cCodDocPersona){
        List<RUTASCOLEGIOS> list =new ArrayList<RUTASCOLEGIOS>();
        // Select All Query
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="select cCodModular from "
                    + NOMBRE_TABLA+" as P where P.cCodModular like '%"+cCodDocPersona+"%'";
            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                RUTASCOLEGIOS entidad= new RUTASCOLEGIOS();
                //entidad.vNombre=fila.getString(0).toUpperCase();
                entidad.cCodModular=fila.getString(0).toUpperCase();
                //entidad.vDesCargo=fila.getString(2).toUpperCase();
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
