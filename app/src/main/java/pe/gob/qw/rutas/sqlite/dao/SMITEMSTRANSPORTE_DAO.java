package pe.gob.qw.rutas.sqlite.dao;

 /**
 * Created by uadin11 on 05/03/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.ITEM;
import pe.gob.qw.rutas.entities.SMITEMSTRANSPORTE;
import pe.gob.qw.rutas.sqlite.SQLite;


public class SMITEMSTRANSPORTE_DAO {


    private static String NOMBRE_TABLA = "SMITEMSTRANSPORTE";
    private static String NOMBRE_TABLA2 = "SMITEMSTRANSPORTE2";

    public static int AgregarITEMSCARRO(Context context, int idSMFICHAS, int iRecordCardPadre, int vCodItem, String vNItem) {
        int id = 0;
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("idSMFICHAS", idSMFICHAS);
        registro.put("iRecordCardPadre", iRecordCardPadre);
        registro.put("vCodItem", vCodItem);
        registro.put("vNItem", vNItem);
        id = (int) bd.insert(NOMBRE_TABLA, null, registro);
        bd.close();
        return id;
    }

    public static void BorrarItemsCarro(Context context, int idSMFICHAS, int iRecordCardPadre, int vCodItem) {
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        if (bd != null) {
            bd.delete(NOMBRE_TABLA, "idSMFICHAS = " + idSMFICHAS + " and iRecordCardPadre= " + iRecordCardPadre + " and  vCodItem= " + vCodItem, null);
        }
        bd.close();
    }

    public static void BorrarItemsCarroTB2(Context context, int idSMFICHAS, int iRecordCardPadre, int vCodItem) {
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        if (bd != null) {
            bd.delete(NOMBRE_TABLA2, "idSMFICHAS = " + idSMFICHAS + " and iRecordCardPadre= " + iRecordCardPadre + " and  vCodItem= " + vCodItem, null);
        }
        bd.close();
    }


    public static void BorrartodoslosItemsDelcarro(Context context, int idSMFICHAS, int iRecordCardPadre, int vCodItem) {

        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        if (bd != null) {
            bd.delete(NOMBRE_TABLA, "idSMFICHAS = " + idSMFICHAS + " and iRecordCardPadre= " + iRecordCardPadre, null);
        }
        bd.close();

    }


    public static List<SMITEMSTRANSPORTE> SeleccionarFichas(Context context, int icodFicha, int idFichasGrabadas) {
        List<SMITEMSTRANSPORTE> list = new ArrayList<SMITEMSTRANSPORTE>();
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if (bd != null) {
            String query = "select  idSMFICHAS,iRecordCardPadre,vCodItem,vNItem  " +
                    "  from SMITEMSTRANSPORTE where idsmfichas= " + idFichasGrabadas;

            fila = bd.rawQuery(query, null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                SMITEMSTRANSPORTE entidad = new SMITEMSTRANSPORTE();
                entidad.idSMFICHAS = fila.getInt(0);
                entidad.iRecordCardPadre = fila.getInt(1);
                entidad.vCodItem = fila.getInt(2);
                entidad.vNItem = fila.getString(3);
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }


    public static List<SMITEMSTRANSPORTE> Items(Context context, int icodFicha, int idFichasGrabadas) {
        List<SMITEMSTRANSPORTE> list = new ArrayList<SMITEMSTRANSPORTE>();
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if (bd != null) {
            String query = "SELECT GROUP_CONCAT(vNItem) AS ITEMS from SMITEMSTRANSPORTE where idsmfichas=" + idFichasGrabadas;
            fila = bd.rawQuery(query, null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                SMITEMSTRANSPORTE entidad = new SMITEMSTRANSPORTE();
                entidad.vNItem = fila.getString(0);
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }

    public static List<SMITEMSTRANSPORTE> ItemsRegistro(Context context, int icodFicha, int idFichasGrabadas, int RCpadre) {
        List<SMITEMSTRANSPORTE> list = new ArrayList<SMITEMSTRANSPORTE>();
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if (bd != null) {
            String query = "SELECT GROUP_CONCAT(vNItem) AS ITEMS from SMITEMSTRANSPORTE where idsmfichas=" + idFichasGrabadas + " and iRecordCardPAdre=" + RCpadre;
            fila = bd.rawQuery(query, null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                SMITEMSTRANSPORTE entidad = new SMITEMSTRANSPORTE();
                entidad.vNItem = fila.getString(0);
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }


    public static List<SMITEMSTRANSPORTE> ItemsRegistroTB2(Context context, int icodFicha, int idFichasGrabadas, int RCpadre)
    {
        List<SMITEMSTRANSPORTE> list=new ArrayList<SMITEMSTRANSPORTE>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="SELECT GROUP_CONCAT(vNItem) AS ITEMS from SMITEMSTRANSPORTE2 where idsmfichas=" +idFichasGrabadas+ " and iRecordCardPAdre="+RCpadre;
            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                SMITEMSTRANSPORTE entidad=new SMITEMSTRANSPORTE();
                entidad.vNItem = fila.getString(0);
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }


    public static int VerificarITEMS(Context context, int idFichasGrabadas, int RC) {
        int conformes = -1;
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila;

        if (bd != null) {
            String query = "select count(*) from SMITEMSTRANSPORTE where idSMFICHAS= "+idFichasGrabadas+ " and iRecordCardPadre= "+RC;

            fila = bd.rawQuery(query, null);
            if (fila.moveToFirst()) {

                conformes = fila.getInt(0);
            }
        }
        bd.close();
        if (conformes < 0)
            conformes = -1;
        return conformes;
    }

    public static int ContarItemsTB2(Context context, int idFichasGrabadas) {
        int conformes = -1;
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila;

        if (bd != null) {
            String query = "select count(*) from SMITEMSTRANSPORTE2 where idSMFICHAS= "+idFichasGrabadas;

            fila = bd.rawQuery(query, null);
            if (fila.moveToFirst()) {

                conformes = fila.getInt(0);
            }
        }
        bd.close();
        if (conformes < 0)
            conformes = -1;
        return conformes;
    }

    public static int AgregarITEMSCARROTB2(Context context, int idSMFICHAS, int iRecordCardPadre, int vCodItem, String vNItem) {
        int id = 0;
        SQLite admin = new SQLite(context, null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("idSMFICHAS", idSMFICHAS);
        registro.put("iRecordCardPadre", iRecordCardPadre);
        registro.put("vCodItem", vCodItem);
        registro.put("vNItem", vNItem);
        registro.put("Estado",0);
        id = (int) bd.insert(NOMBRE_TABLA2, null, registro);
        bd.close();
        return id;
    }

    public static List<ITEM> ListarItemsTB2(Context context, int idFichasGrabadas)
    {
        List<ITEM> list=new ArrayList<ITEM>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            //MODIFICAR MAS ADELANTE EL TIPOFICHA POR DEFINIR, HASTA EL MOMENTO SE TOMA 1 COMO RACIONES.
            String query="select vCodItem,vNItem from SMITEMSTRANSPORTE2 where idSMFICHAS="+idFichasGrabadas+ "  and Estado=0" ;
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();
            fila.moveToFirst();
            list.add(new ITEM(0,"SELECCIONE"));
            for (int i = 0; i < numRows; i++)
            {   ITEM entidad= new ITEM();
                entidad.iCodItem=fila.getInt(fila.getColumnIndex("vCodItem"));
                entidad.vNombreItem= fila.getString(fila.getColumnIndex("vNItem"));
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }

    public  static boolean ActualizarEstadoITEM(Context context, int idfichas, int vCodItem)
    {	boolean x=false;
        try
        {
            SQLite admin=new SQLite(context,null);
            SQLiteDatabase bd = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("Estado", 1);
            int cant = bd.update(NOMBRE_TABLA2, registro, "idSMFICHAS=" + idfichas+  " and vCodItem= " +vCodItem , null);
            bd.close();
            if(cant==1)
                return true;
            else
                return false;}
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        return x;
    }




    public static List<SMITEMSTRANSPORTE> Items(Context context, int icodFicha, int idFichasGrabadas, int rc)
    {
        List<SMITEMSTRANSPORTE> list=new ArrayList<SMITEMSTRANSPORTE>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="SELECT GROUP_CONCAT(vNItem) AS ITEMS from SMITEMSTRANSPORTE where idsmfichas=" +idFichasGrabadas+ " and iRecordCardPadre= " +rc;
            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                SMITEMSTRANSPORTE entidad=new SMITEMSTRANSPORTE();
                entidad.vNItem = fila.getString(0);
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }









    public static void BorrarItemsCarro2(Context context, int idSMFICHAS, int iRecordCardPadre)
    {
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            bd.delete(NOMBRE_TABLA, "idSMFICHAS = "+idSMFICHAS+ " and iRecordCardPadre= "+iRecordCardPadre,null);
        }
        bd.close();
    }



    public static void BorrarItemFichas(Context context, int idFichasGrabadas, int RC){
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            bd.delete(NOMBRE_TABLA, "idSMFichas="+idFichasGrabadas+ " and iRecordCardPadre= "+RC, null);
        }
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

    public static void BorrarDataTB2(Context context){
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            String query="delete from "+NOMBRE_TABLA2;

            bd.execSQL(query);

        }
        bd.close();
    }


}
