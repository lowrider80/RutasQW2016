package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import pe.gob.qw.rutas.sqlite.SQLite;

public class SESION_DAO {
    
    private static String NOMBRE_TABLA="SESION";
    
     public static int Agregar(Context context, String vUsuario, String vNombre)
    {
        int id = 0;
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        ContentValues registro=new ContentValues();
        registro.put("iSesion",1);
        registro.put("vUsuario",vUsuario);
        registro.put("vNombre",vNombre);

        id = (int) bd.insert(NOMBRE_TABLA, null, registro);
        bd.close();    
        return id;

    }   
     

     public static int Buscar(Context context)
     {
      
    	int id=0;
    	SQLite admin=new SQLite(context,null); 
       	SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
            String query="select iSesion from "+NOMBRE_TABLA;
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {                           
            	id=fila.getInt(0);
            }
        }
        bd.close();   
        return id;
     }
  
     public static String BuscarUt(Context context)
     {
      
    	String data="";
    	SQLite admin=new SQLite(context,null); 
       	SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
            String query="select vUsuario from "+NOMBRE_TABLA;
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {          
                            
            	data=fila.getString(0);
               
                
            }
        }
        bd.close();   
        return data;
     }

    public static String BuscarNombUt(Context context)
    {

        String data="";
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            String query="select vNombre from "+NOMBRE_TABLA;
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {

                data=fila.getString(0);


            }
        }
        bd.close();
        return data;
    }

    public static String BuscarCodUt(Context context)
    {

        String data="";
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            String query="select vUsuario from "+NOMBRE_TABLA;
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {

                data=fila.getString(0);


            }
        }
        bd.close();
        return data;
    }
  
     public static void Borrar(Context context) {
    	 SQLite admin=new SQLite(context,null);
	     SQLiteDatabase bd=admin.getWritableDatabase();
	     bd.delete(NOMBRE_TABLA, null, null);
	     bd.close();
    }
     
     public static void BorrarData(Context context, boolean logout){

         if(logout) {
              SQLite admin=new SQLite(context,null);
              SQLiteDatabase bd=admin.getWritableDatabase();

             if(bd!=null) {
                  String query="delete from "+ NOMBRE_TABLA;
                  bd.execSQL(query);
             }

             bd.close();
         }

         ACTACOLEGIOSITEM_DAO.BorrarData(context);
         COLEGIOSITEM_DAO.BorrarData(context);
         COMPONENTELIBERACION_DAO.BorrarData(context);
         COMPONENTERACION_DAO.BorrarData(context) ;
         CONTRATO_DAO.BorrarData(context);
         DBQUESTIONARY_DAO.BorrarData(context);
         DETALLELIBERACION_DAO.BorrarData(context);
         ESTABLECIMIENTOSITEMSATENDIDOS_DAO.BorrarData(context);
         FICHATECNICAPRESENTACION_DAO.BorrarData(context);
         ITEMSATENDIDOS_DAO.BorrarData(context);
         LIBERACIONRECORDCARDITEM_DAO.BorrarData(context);
         LIBERACIONRUTAS_DAO.BorrarData(context);
         MATERIAPRIMA_DAO.BorrarData(context);
         PROVEEDORESITEMS.BorrarData(context);
         RECORDCARDITEM_DAO.BorrarData(context);
         RUTASCOLEGIOS_DAO.BorrarData(context);
         SECCIONFICHAGRABADA_DAO.BorrarData(context);
         SMALMACEN_DAO.BorrarData(context);
         SMBCOPREGUNTAS_DAO.BorrarData(context);
         SMCAUSALES_DAO.BorrarData(context);
         SMDETFOTOS_DAO.BorrarData(context);
         SMDETFOTOSRUTAS_DAO.BorrarData(context);
         SMEQUIPOS_DAO.BorrarData(context);
         SMFICHAINICIALANEXO_DAO.BorrarData(context);
         SMFICHASGOTROS_DAO.BorrarData(context);
         SMFICHASGRABADAS_DAO.BorrarData(context);
         SMFORMATO_DAO.BorrarData(context);
         SMITEMSTRANSPORTE_DAO.BorrarData(context);
         SMMAEFICHAS_DAO.BorrarData(context);
         SMPUNTOCRITICO_DAO.BorrarData(context);
         TABLETESPECIALISTAS_DAO.BorrarData(context);
         TABLETPOSTORES_DAO.BorrarData(context);
         TABLETPROVEEDORES_DAO.BorrarData(context);
         TABLETSUPERVISORES_DAO.BorrarData(context);
         TABLETVEHICULOS_DAO.BorrarData(context);
         SESION_DAO.Borrar(context);
     }
}