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

import pe.gob.qw.rutas.entities.ESPECIALISTAS;
import pe.gob.qw.rutas.sqlite.SQLite;

/**
 * Created by uadin11 on 17/05/2016.
 */
public class TABLETESPECIALISTAS_DAO {

    private static String NOMBRE_TABLA="TABLETESPECIALISTAS";



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
                registro.put("cCodEstablecimiento",json_data.getString("cCodEstablecimiento"));
                registro.put("vNombreProfesional",json_data.getString("vNombreProfesional"));
                registro.put("vProfesion",json_data.getString("vProfesion"));
                registro.put("vColegiatura",json_data.getString("vColegiatura"));

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

    public static List<ESPECIALISTAS> SpinEspecialistas(Context context, String CCod){
        List<ESPECIALISTAS> list =new ArrayList<ESPECIALISTAS>();
        // Select All Query
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query = "SELECT vNombreProfesional,vColegiatura,vProfesion FROM  " +NOMBRE_TABLA+ " WHERE TRIM(cCodEstablecimiento)= '" +CCod+"'";
            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                ESPECIALISTAS entidad= new ESPECIALISTAS();
                entidad.vNombreProfesional= fila.getString(0).toUpperCase();
                entidad.vColegiatura= fila.getString(1).toUpperCase();
                entidad.vProfesion=fila.getString(2).toUpperCase();
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }






}
