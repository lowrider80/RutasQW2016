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

import pe.gob.qw.rutas.entities.LIBERACIONRUTAS;
import pe.gob.qw.rutas.sqlite.SQLite;

/**
 * Created by uadin11 on 08/03/2016.
 */
public class LIBERACIONRUTAS_DAO {
    private static String NOMBRE_TABLA="LIBERACIONRUTAS";
    private static String NOMBRE_TABLA2="ITEMSRUTAS";


    SQLiteDatabase mydb;

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
                registro.put("iCodLiberacion",json_data.getInt("iCodLiberacion"));
                registro.put("iCodItem",json_data.getInt("iCodItem"));
                registro.put("vNomItem",json_data.getString("vNomItem"));
                registro.put("iCodContrato",json_data.getInt("iCodContrato"));
                registro.put("cCodProveedor",json_data.getString("cCodProveedor"));
                registro.put("vNombreProveedor",json_data.getString("vNombreProveedor"));
                registro.put("iCodCronogramaEntrega",json_data.getInt("iCodCronogramaEntrega"));
                registro.put("iNumEntrega",json_data.getInt("iNumEntrega"));
                registro.put("iCodCampana",json_data.getInt("iCodCampana"));
                registro.put("MesCronograma",json_data.getString("MesCronograma"));
                registro.put("dFecIniciaPlazoEntrega",json_data.getString("dFecIniciaPlazoEntrega"));
                registro.put("dFecFinalPlazoEntrega",json_data.getString("dFecFinalPlazoEntrega"));
                registro.put("dFecLiberacion",json_data.getString("dFecLiberacion"));
                registro.put("cCodEstablecimiento",json_data.getString("cCodEstablecimiento"));
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

    public static List<LIBERACIONRUTAS> SpinItems(Context context, String CCod){
        List<LIBERACIONRUTAS> list =new ArrayList<LIBERACIONRUTAS>();
        // Select All Query
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query = "SELECT DISTINCT(icodItem),vNomItem FROM  " +NOMBRE_TABLA+ " WHERE TRIM(CCODESTABLECIMIENTO)= '" +CCod+"'";
            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                LIBERACIONRUTAS entidad= new LIBERACIONRUTAS();
                entidad.iCodItem= fila.getInt(0);
                entidad.vNomItem= fila.getString(1);
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }

    public static List SpinLiberacion(Context context, String ITEM, String CCod){
        List list =new ArrayList();
        // Select All Query
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query = "SELECT inumEntrega FROM  " +NOMBRE_TABLA+ " WHERE vNomItem='"+ITEM+"' and    TRIM(CCODESTABLECIMIENTO)= '" +CCod+"'";



            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                //LIBERACIONRUTAS entidad= new LIBERACIONRUTAS();
                list.add(fila.getInt(0));
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }


    public static List<LIBERACIONRUTAS> ListarPlazos(Context context, String CCod, int numentrega){
        List<LIBERACIONRUTAS> list =new ArrayList<LIBERACIONRUTAS>();
        // Select All Query
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query = "SELECT dFecIniciaPlazoEntrega,dFecFinalPlazoEntrega FROM  " +NOMBRE_TABLA+ " WHERE vNomItem= '" +CCod+ "' and  iNumEntrega="+numentrega;
            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                LIBERACIONRUTAS entidad= new LIBERACIONRUTAS();
                entidad.dFecIniciaPlazoEntrega= fila.getString(0);
                entidad.dFecFinalPlazoEntrega= fila.getString(1);
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }


    public static  int AgregarITEMSRUTA(Context context, int idFichasGrabadas, int iCodItem, String vNomItem, String dFecIniciaPlazoEntrega
    , String dFecFinalPlazoEntrega , int NumIEE, int NumLib)
    {
        int numRows=0;

        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
         String[] rowsHeader=null;
        int indexHeader=0;
        if(bd!=null)
        {
                ContentValues registro=new ContentValues();
                registro.put("idSMFICHAS",idFichasGrabadas);
                registro.put("iCodItem",iCodItem);
                registro.put("vNomItem", vNomItem);
                registro.put("dFecIniciaPlazoEntrega",dFecIniciaPlazoEntrega);
                registro.put("dFecFinalPlazoEntrega",dFecFinalPlazoEntrega);
                registro.put("NumIEE",NumIEE);
                registro.put("NumLib",NumLib);
                int id = (int) bd.insert(NOMBRE_TABLA2, null, registro);
        }
        bd.close();
        return idFichasGrabadas;

    }

    public static List Comparar(Context context, int idFichasGrabadas)
    {
        List list =new ArrayList();
        // Select All Query
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query = "select NumLib from ITEMSRUTAS where  idSMFICHAS= "+ idFichasGrabadas;
            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {

                list.add(fila.getInt(0));
                fila.moveToNext();
            }
        }
        bd.close();
        return list;

    }



    public  static boolean ActualizarEstadoITEM(Context context, int idFichasGrabadas)
    {
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("idSMFICHAS",idFichasGrabadas);
        int cant = bd.update(NOMBRE_TABLA2, registro, "idSMFICHAS= 0", null);

        bd.close();
        if(cant==1)
            return true;
        else
            return false;

    }


    public static void BorrarItems(Context context, int idFichasGrabadas){
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            bd.delete(NOMBRE_TABLA2, "idSMFICHAS= "+idFichasGrabadas, null);


        }
        bd.close();
    }


    public static ArrayList<LIBERACIONRUTAS> ListarSeccion(Context context, int idFichasGrabadas)
    {
        ArrayList<LIBERACIONRUTAS> list=new ArrayList<LIBERACIONRUTAS>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="select vNomItem,dFecIniciaPlazoEntrega ,dFecFinalPlazoEntrega ,NumIEE,NumLib from ITEMSRUTAS where  idsmfichas= "+idFichasGrabadas;

            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                LIBERACIONRUTAS entidad= new LIBERACIONRUTAS();
                entidad.vNomItem=fila.getString(0);
                entidad.dFecIniciaPlazoEntrega=fila.getString(1);
                entidad.dFecFinalPlazoEntrega=fila.getString(2);
                entidad.NomIEE=fila.getInt(3);
                entidad.iNumEntrega=fila.getInt(4);
                list.add(entidad);
                fila.moveToNext();
            }

        }
        bd.close();
        return list;
    }

    public static ArrayList<LIBERACIONRUTAS> ListarTodo(Context context, int idFichasGrabadas)
    {

        ArrayList<LIBERACIONRUTAS> list=new ArrayList<LIBERACIONRUTAS>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="select idSMFICHAS,icoditem,vNomItem,dFecIniciaPlazoEntrega ,dFecFinalPlazoEntrega ,NumIEE from ITEMSRUTAS where  idsmfichas= "+idFichasGrabadas;

            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                LIBERACIONRUTAS entidad= new LIBERACIONRUTAS();
                entidad.idSMFICHAS=fila.getInt(0);
                entidad.iCodItem=fila.getInt(1);
                entidad.vNomItem=fila.getString(2);
                entidad.dFecIniciaPlazoEntrega=fila.getString(3);
                entidad.dFecFinalPlazoEntrega=fila.getString(4);
                entidad.NomIEE=fila.getInt(5);
                list.add(entidad);
                fila.moveToNext();
            }

        }
        bd.close();
        return list;
    }
    public static int ContarITEMRUTA(Context context, int idFichasGrabadas) {
        int items = -1;
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila;

        if (bd != null) {
            String query = "select count(*) from ITEMSRUTAS where idSMFICHAS= "+idFichasGrabadas;

            fila = bd.rawQuery(query, null);
            if (fila.moveToFirst()) {

                items = fila.getInt(0);
            }
        }
        bd.close();
        if (items < 0)
            items = -1;
        return items;
    }


    public static int ValidarITEMS(Context context, int idFichasGrabadas, String icoditem) {
        int items = -1;
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila;

        if (bd != null) {
            String query = "select  count(*) from ITEMSRUTAS where vNomItem='"+icoditem+"'  and idSMFICHAS=0";
            fila = bd.rawQuery(query, null);
            if (fila.moveToFirst()) {
                items = fila.getInt(0);
            }
        }
        bd.close();
        if (items < 0)
            items = -1;
        return items;
    }






    public Cursor leerITEMS(Context context, int idFichasGrabadas){
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase db =admin.getWritableDatabase();
        return db.rawQuery("SELECT icoditem,vNomItem where  idsmfichas= "+idFichasGrabadas, null);
    }



    public static int TotalIEE(Context context, int idFichasGrabadas) {
        int items = -1;
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila;

        if (bd != null) {
            String query = "select sum (NumIEE) from ITEMSRUTAS where idSMFICHAS="+idFichasGrabadas;

            fila = bd.rawQuery(query, null);
            if (fila.moveToFirst()) {

                items = fila.getInt(0);
            }
        }
        bd.close();
        if (items < 0)
            items = -1;
        return items;
    }



}
