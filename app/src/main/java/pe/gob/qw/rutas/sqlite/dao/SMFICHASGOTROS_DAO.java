package pe.gob.qw.rutas.sqlite.dao;

/**
 * Created by uadin11 on 04/03/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.SMFICHASGBOTROS;
import pe.gob.qw.rutas.sqlite.SQLite;


public class SMFICHASGOTROS_DAO {
    private static String NOMBRE_TABLA="SMFICHASGBOTROS";


    public static List<SMFICHASGBOTROS> SeleccionarFichas(Context context, int icodFicha, int idFichasGrabadas)
    {
        List<SMFICHASGBOTROS> list=new ArrayList<SMFICHASGBOTROS>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
        int numRows;
        if(bd!=null)
        {
            String query="select  idSMFICHAS,cCodEstablecimiento,cCodProveedor,iCodFicha ,iNumDiasPlazo,lFechaPlazo,vNSupervisor,vDNISupervisor, " +
                    "iTipoEstablecimiento  , " +
                    " iNumConforme,iNumNoConforme,iNumTotalVh,iNumTotalVhRv from SMFICHASGBOTROS where idsmfichas= "+idFichasGrabadas+ " and icodficha= "+icodFicha;

            fila=bd.rawQuery(query,null);
            numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                SMFICHASGBOTROS entidad=new SMFICHASGBOTROS();
                entidad.idSMFICHAS=fila.getInt(0);
                entidad.cCodEstablecimiento=fila.getString(1);
                entidad.cCodProveedor=fila.getString(2);
                entidad.iCodFicha=fila.getInt(3);
                entidad.iNumDiasPlazo=fila.getInt(4);
                entidad.lFechaPlazo=fila.getLong(5);
                entidad.vNSupervisor=fila.getString(6);
                entidad.vDNISupervisor=fila.getInt(7);
                entidad. iTipoEstablecimiento=fila.getInt(8);
                entidad.iNumConforme=fila.getInt(9);
                entidad.iNumNoConforme=fila.getInt(10);
                entidad.iNumTotalVh=fila.getInt(11);
                entidad.iNumTotalVhRv=fila.getInt(12);
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }



    public static  SMFICHASGBOTROS Buscar(Context context, int idSMFichasGrabadas) {
        SMFICHASGBOTROS  entidad=null;
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {


            String query="select idSMFICHAS , cCodEstablecimiento,cCodProveedor,iCodFicha,iNumDiasPlazo,lFechaPlazo,vNSupervisor,"
                    +" vDNISupervisor,iTipoEstablecimiento,iNumconforme,iNumNoConforme,iNumTotalVh,iNumTotalVhRv from " +NOMBRE_TABLA+
                    " where idSMFICHAS= "+idSMFichasGrabadas;
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {
                entidad= new SMFICHASGBOTROS();
                entidad.idSMFICHAS=fila.getInt(0);
                entidad.cCodEstablecimiento=fila.getString(1);
                entidad.cCodProveedor=fila.getString(2);
                entidad.iCodFicha=fila.getInt(3);
                entidad.iNumDiasPlazo=fila.getInt(4);
                entidad.lFechaPlazo=fila.getLong(5);
                entidad.vNSupervisor=fila.getString(6);
                entidad.vDNISupervisor=fila.getInt(7);
                entidad.iTipoEstablecimiento=fila.getInt(8);
                entidad.iNumConforme=fila.getInt(9);
                entidad.iNumNoConforme=fila.getInt(10);
                entidad.iNumTotalVh=fila.getInt(11);
                entidad.iNumTotalVhRv=fila.getInt(12);
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



