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

import pe.gob.qw.rutas.entities.RUTA;
import pe.gob.qw.rutas.entities.TABLETVEHICULOS;
import pe.gob.qw.rutas.sqlite.SQLite;

/**
 * Created by uadin11 on 07/03/2016.
 */
public class TABLETVEHICULOS_DAO {

    private static String NOMBRE_TABLA="TABLETVEHICULOS";

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

                registro.put("iCodRutaEntrega",json_data.getInt("iCodRutaEntrega"));
                registro.put("iCodContrato",json_data.getInt("iCodContrato"));
                registro.put("vPlaca",json_data.getString("vPlaca"));
                registro.put("cCodEstablecimiento",json_data.getString("cCodEstablecimiento"));
                registro.put("cCodProveedor",json_data.getString("cCodProveedor").trim());
                registro.put("iCodUnidad",json_data.getInt("iCodUnidad"));
                registro.put("iCodTipoVehiculo",json_data.getInt("iCodTipoVehiculo"));
                registro.put("vNombre",json_data.getString("vNombre"));

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

    public static List<TABLETVEHICULOS> Listar(Context context, String cCodProveedor)
    {
        List<TABLETVEHICULOS> list=new ArrayList<TABLETVEHICULOS>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="select distinct iCodContrato, vPlaca, cCodEstablecimiento, "
                    +" cCodProveedor, iCodUnidad, iCodTipoVehiculo, vNombre from "
                    +NOMBRE_TABLA+" WHERE cCodProveedor = '"+ cCodProveedor + "'";


            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {

                TABLETVEHICULOS entidad= new TABLETVEHICULOS();
                entidad.iCodContrato = fila.getInt(0);
                entidad.vPlaca=fila.getString(1);
                entidad.cCodEstablecimiento = fila.getString(2);
                entidad.cCodProveedor = fila.getString(3);
                entidad.iCodUnidad = fila.getInt(4);
                entidad.iCodTipoVehiculo = fila.getInt(5);
                entidad.vNombre=fila.getString(6);

                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }

    public static List<TABLETVEHICULOS> ListarPlacasTransporte(Context context, String ccodEstablecimiento, String placa)
    {
        List<TABLETVEHICULOS> list=new ArrayList<TABLETVEHICULOS>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="select  DISTINCT(REPLACE (vPlaca,' ',''))   from " +NOMBRE_TABLA+ "   where cCodEstablecimiento = "
                    +ccodEstablecimiento+ " and  vPlaca like '%"+placa+"%'";
            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                TABLETVEHICULOS entidad= new TABLETVEHICULOS();
                entidad.vPlaca=fila.getString(0);
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }

    public static List<RUTA> ListarRutas(Context context, String cCodProveedor, String vPlaca)
    {
        List<RUTA> list=new ArrayList<RUTA>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="select distinct iCodRutaEntrega from "
                    + NOMBRE_TABLA + " WHERE cCodProveedor = '" + cCodProveedor + "' and vPlaca = '" + vPlaca + "'";


            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {

                RUTA entidad= new RUTA();
                entidad.iCodRutaEntrega = fila.getString(0);
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
