package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.CONTRATO;
import pe.gob.qw.rutas.sqlite.SQLite;

public class CONTRATO_DAO {
    private static String NOMBRE_TABLA="CONTRATO";

    public static int AgregarServicio(Context context, JSONArray Array)
    {
        int i;
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        for(i=0;i<Array.length();i++)
        {
            try {
                JSONObject json_data = Array.getJSONObject(i);
                ContentValues registro=new ContentValues();
                registro.put("iCodContrato",json_data.getInt("iCodContrato"));
                registro.put("iNumContrato",json_data.getInt("iNumContrato"));
                registro.put("vNomContrato",json_data.getString("vNomContrato").trim());
                registro.put("cCodProveedor",json_data.getString("cCodProveedor").trim());
                registro.put("iCodItem",json_data.getInt("iCodItem"));
                registro.put("iCodComite",json_data.getInt("iCodComite"));
                registro.put("iCodUnidad",json_data.getInt("iCodUnidad"));
                registro.put("iCodModalidad",json_data.getInt("iCodModalidad"));
                registro.put("DescripcionUT",json_data.getString("DescripcionUT").trim());
                registro.put("vComite",json_data.getString("vComite").trim());
                registro.put("vNombreItem",json_data.getString("vNombreItem").trim());
                registro.put("iCodProveedor",json_data.getInt("iCodProveedor"));
                registro.put("iCantidadProgramada",json_data.getInt("iCantidadProgramada"));

                int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
                Log.e("AgregarServicio","insertando " + id);

                if(0==id)
                {
                    bd.close();
                    i=Array.length();
                    return 0;

                }
            } catch (JSONException e) {
                bd.close();
                return 0;
            }
        }

        bd.close();
        return i;

    }


    public static List<CONTRATO> ListarXProveedorLiberacion(Context context, String cCodProveedor, String cRUC)
    {
        List<CONTRATO> list=new ArrayList<CONTRATO>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="select distinct C.iCodContrato,C.vNomContrato,C.cCodProveedor,C.iCodItem,C.iCodComite,"
                    + "C.iCodUnidad,C.iCodModalidad,C.DescripcionUT,C.vComite,C.vNombreItem,"
                    + "C.iCodProveedor,C.iNumContrato,P.vDireccionEstablecimiento,D.cCodEstablecimiento,P.iTipo , P.vObservacion, D.inumentrega " //se agrego el P.vObservacion(Nombre de establecimiento) para tenerlo en las fichas de liberaciones
                    + "from "+ NOMBRE_TABLA+" as C inner join DETALLELIBERACION as D on "
                    + "C.iCodContrato=D.iCodContrato inner join TABLETPROVEEDORES as P on "
                    + "D.cCodEstablecimiento=P.cCodEstablecimiento  WHERE P.cRUC like '"+cRUC+"'";//C.cCodProveedor like '"+cCodProveedor+"'";
            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {

                CONTRATO entidad= new CONTRATO();
                entidad.iCodContrato=fila.getInt(0);
                entidad.vNomContrato=fila.getString(1);
                entidad.cCodProveedor=fila.getString(2);
                entidad.iCodItem=fila.getInt(3);
                entidad.iCodComite=fila.getInt(4);
                entidad.iCodUnidad=fila.getInt(5);
                entidad.iCodModalidad=fila.getInt(6);
                entidad.DescripcionUT=fila.getString(7);
                entidad.vComite=fila.getString(8);
                entidad.vNombreItem=fila.getString(9);
                entidad.iCodProveedor=fila.getInt(10);
                entidad.iNumContrato=fila.getInt(11);
                entidad.vDireccionEstablecimiento=fila.getString(12);
                entidad.cCodEstablecimiento=fila.getString(13);
                entidad.iTipo=fila.getInt(14);
                entidad.vNumContrato=""+fila.getInt(11);
                entidad.vObservacion=fila.getString(15);
                entidad.iNumEntrega =  fila.getString(16);

                list.add(entidad);

                fila.moveToNext();
            }

        }
        bd.close();
        return list;
    }

    public static List<CONTRATO> ListarXProveedorOcurrencias(Context context, String cCodProveedor, String cRUC)
    {
        List<CONTRATO> list=new ArrayList<CONTRATO>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="select distinct C.iCodContrato,C.vNomContrato,C.cCodProveedor,C.iCodItem,C.iCodComite,"
                    + "C.iCodUnidad,C.iCodModalidad,C.DescripcionUT,C.vComite,C.vNombreItem,"
                    + "C.iCodProveedor,C.iNumContrato,P.iTipo "
                    + "from "+ NOMBRE_TABLA+" as C inner join TABLETPROVEEDORES as P on "
                    + "C.cCodProveedor=P.cCodProveedor  WHERE P.cRUC like '"+cRUC+"'";//C.cCodProveedor like '"+cCodProveedor+"'";
            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {

                CONTRATO entidad= new CONTRATO();
                entidad.iCodContrato=fila.getInt(0);
                entidad.vNomContrato=fila.getString(1);
                entidad.cCodProveedor=fila.getString(2);
                entidad.iCodItem=fila.getInt(3);
                entidad.iCodComite=fila.getInt(4);
                entidad.iCodUnidad=fila.getInt(5);
                entidad.iCodModalidad=fila.getInt(6);
                entidad.DescripcionUT=fila.getString(7);
                entidad.vComite=fila.getString(8);
                entidad.vNombreItem=fila.getString(9);
                entidad.iCodProveedor=fila.getInt(10);
                entidad.iNumContrato=fila.getInt(11);
                entidad.iTipo=fila.getInt(12);
                entidad.vNumContrato=fila.getString(1);//+fila.getInt(11);
                entidad.vObservacion=fila.getString(1);

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