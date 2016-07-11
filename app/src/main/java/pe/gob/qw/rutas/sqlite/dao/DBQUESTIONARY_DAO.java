package pe.gob.qw.rutas.sqlite.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.DBQUESTIONARY;
import pe.gob.qw.rutas.sqlite.SQLite;

public class DBQUESTIONARY_DAO {
    
    private static String NOMBRE_TABLA="DBQUESTIONARY";
    
	public static int Agregar(Context context, DBQUESTIONARY entidad) {
		// TODO Auto-generated method stub
		int id = 0;
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        ContentValues registro=new ContentValues();
        registro.put("ordenllamada",entidad.ordenllamada);
        registro.put("iCodFicha",entidad.iCodFicha);
        registro.put("iCodTipoSeccion",entidad.iCodTipoSeccion);
        registro.put("iCodBcoPreg",entidad.iCodBcoPreg);
        registro.put("cSeccion",entidad.cSeccion);
        registro.put("vDetalleSeccion",entidad.vDetalleSeccion);
        registro.put("iValor",entidad.iValor);
        registro.put("iTipoControl",entidad.iTipoControl);
        registro.put("iOrden",entidad.iOrden);
        registro.put("iTipoColumna",entidad.iTipoColumna);
        registro.put("vTabla",entidad.vTabla);
        registro.put("vCampo",entidad.vCampo);
       
        id = (int) bd.insert(NOMBRE_TABLA, null, registro);    
        
        bd.close();    
        
        return id;
	}

    public static List<DBQUESTIONARY> Listar(Context context, int iCodFicha)
    {
       List<DBQUESTIONARY> list=new ArrayList<DBQUESTIONARY>();
       SQLite admin=new SQLite(context,null); 
       SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
             String query="select  ordenllamada,iCodFicha,iCodTipoSeccion,iCodBcoPreg,cSeccion,"
             		 +"vDetalleSeccion,iValor,iTipoControl,iOrden,iTipoColumna,vTabla,vCampofrom "
            		 +NOMBRE_TABLA+" where iCodFicha="+iCodFicha;    
           Cursor fila=bd.rawQuery(query,null);
           int numRows = fila.getCount();   
           fila.moveToFirst();   
               for (int i = 0; i < numRows; ++i) 
               {   

            	   DBQUESTIONARY entidad= new DBQUESTIONARY();            
                   entidad.ordenllamada=fila.getInt(0);
                   entidad.iCodFicha=fila.getInt(1);
                   entidad.iCodTipoSeccion=fila.getInt(2);              
                   entidad.iCodBcoPreg=fila.getString(3);
                   entidad.cSeccion=fila.getString(4);
                   entidad.vDetalleSeccion=fila.getString(5);
                   entidad.iValor=fila.getInt(5);
                   entidad.iTipoControl=fila.getInt(6);
                   entidad.iOrden=fila.getInt(7);
                   entidad.iTipoColumna=fila.getInt(9);
                   entidad.vTabla=fila.getString(10);
                   entidad.vCampo=fila.getString(11);                   
                
                   
                   list.add(entidad);
                      
                   fila.moveToNext();   
               }   
        }
       bd.close();   
       return list;
    }
	//EDITADO
     public static  int AgregarFichas(Context context)
     {
    	//int i=0;
    	 
    	int count = 0;
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
        
        try{
         if(bd!=null)
         {
        	 String query="select iCodFicha from SMMAEFICHAS";
        	 Cursor fila1=bd.rawQuery(query,null);
             int numRows = fila1.getCount();   
             fila1.moveToFirst();   
                 for (int i2 = 0; i2 < numRows; ++i2) 
                 {   
                	int type=fila1.getInt(0);
                	query = "select ";
     	     		query += "0 as ordenllamada ";
     	     		query += ",t1.iCodFicha as iCodFicha";
     	     		query += ",iCodTipoSeccion ";
     	     		query += ",'' AS iCodBcoPreg ";
     	     		query += ",cSeccion ";
     	     		query += ",vDetalleSeccion ";
     	     		query += ",iValor ";
     	     		query += ",iTipoControl ";
     	     		query += ",iOrden ";
     	     		query += ",'' AS iTipoColumna ";
     	     		query += ",vTabla ";
     	     		query += ",vCampo ";
     	     		query += "from SMFORMATO t1 ";
     	     		query += "where ";
     	     		query += " t1.iCodFicha =IFNULL(" + type + ",t1.iCodFicha) ";
     	     		query += "AND T1.iCodTipoSeccion = 1	 ";
     	     		query += "union ";
     	     		query += "select  ";
     	     		query += "1 as ordenllamada ";
     	     		query += ",t1.iCodFicha  as iCodFicha";
     	     		query += ",iCodTipoSeccion ";
     	     		query += ",'' AS iCodBcoPreg ";
     	     		query += ",cSeccion ";
     	     		query += ",vDetalleSeccion ";
     	     		query += ",iValor ";
     	     		query += ",iTipoControl ";
     	     		query += ",iOrden ";
     	     		query += ",'' AS iTipoColumna ";
     	     		query += ",vTabla ";
     	     		query += ",vCampo ";
     	     		query += "from SMFORMATO t1 ";
     	     		query += "where  ";
     	     		query += " t1.iCodFicha =IFNULL(" + type + ",t1.iCodFicha) ";
     	     		query += "AND T1.iCodTipoSeccion = 2	 ";
     	     		query += "union	 ";
     	     		query += "select  ";
     	     		query += "3 as ordenllamada ";
     	     		query += ",t1.iCodFicha  as iCodFicha";
     	     		query += ",iCodTipoSeccion ";
     	     		query += ",'' AS iCodBcoPreg ";
     	     		query += ",cSeccion ";
     	     		query += ",vDetalleSeccion ";
     	     		query += ",iValor ";
     	     		query += ",iTipoControl ";
     	     		query += ",iOrden ";
     	     		query += ",'' AS iTipoColumna ";
     	     		query += ",vTabla ";
     	     		query += ",vCampo ";
     	     		query += "from SMFORMATO t1 ";
     	     		query += "where  ";
     	     		query += " t1.iCodFicha =IFNULL(" + type + ",t1.iCodFicha) ";
     	     		query += "AND T1.iCodTipoSeccion = 3	 ";
     	     		query += "union		 ";
     	     		query += "select  ";
     	     		query += "4 as ordenllamada ";
     	     		query += ",t1.iCodFicha  as iCodFicha";
     	     		query += ",iCodTipoSeccion ";
     	     		query += ",'' AS iCodBcoPreg ";
     	     		query += ",cSeccion ";
     	     		query += ",vDetalleSeccion ";
     	     		query += ",iValor ";
     	     		query += ",iTipoControl ";
     	     		query += ",iOrden ";
     	     		query += ",'' AS iTipoColumna ";
     	     		query += ",vTabla ";
     	     		query += ",vCampo ";
     	     		query += "from SMFORMATO t1 ";
     	     		query += "where  ";
     	     		query += " t1.iCodFicha =IFNULL(" + type + ",t1.iCodFicha) ";
     	     		query += "AND T1.iCodTipoSeccion = 4 ";
     	     		query += "union	 ";
     	     		query += "select  ";
     	     		query += "5 as ordenllamada ";
     	     		query += ",t1.iCodFicha  as iCodFicha";
     	     		query += ",iCodTipoCausal as iCodTipoSeccion ";
     	     		query += ",cAlternativa as iCodBcoPreg ";
     	     		query += ",cAlternativa as cSeccion ";
     	     		query += ",vDesCausal as vDetalleSeccion ";
     	     		query += ",' ' as iValor ";
     	     		query += ",iTipoControl ";
     	     		query += ",iOrden ";
     	     		query += ",'' AS iTipoColumna ";
     	     		query += ",t2.vTabla ";
     	     		query += ",t2.vCampo ";
     	     		query += "from SMMAEFICHAS t1 ";
     	     		query += "inner join SMCAUSALES t2 on t2.iCodFicha = t1.iCodFicha ";
     	     		query += "where  ";
     	     		query += " t2.bActivo = 1 ";
     	     		query += "and t2.iCodTipoCausal = 5 ";
     	     		query += "and t1.iCodFicha = iFnull(" + type + ",t1.iCodFicha) ";
     	     		query += "union	 ";
     	     		query += "select  ";
     	     		query += "6 as ordenllamada ";
     	     		query += ",t1.iCodFicha  as iCodFicha";
     	     		query += ",iCodTipoCausal as iCodTipoSeccion ";
     	     		query += ",cAlternativa as iCodBcoPreg ";
     	     		query += ",cAlternativa as cSeccion ";
     	     		query += ",vDesCausal as vDetalleSeccion ";
     	     		query += ",' ' as iValor ";
     	     		query += ",iTipoControl ";
     	     		query += ",iOrden ";
     	     		query += ",'' AS iTipoColumna ";
     	     		query += ",t2.vTabla ";
     	     		query += ",t2.vCampo ";
     	     		query += "from SMMAEFICHAS t1 ";
     	     		query += "inner join SMCAUSALES t2 on t2.iCodFicha = t1.iCodFicha ";
     	     		query += "where  ";
     	     		query += " t2.bActivo = 1 ";
     	     		query += "and t2.iCodTipoCausal = 6 ";
     	     		query += "and t1.iCodFicha = ifnull(" + type + ",t1.iCodFicha) ";
     	     		query += "union ";
     	     		query += "select  ";
     	     		query += "7 as ordenllamada ";
     	     		query += ",t1.iCodFicha  as iCodFicha";
     	     		query += ",iCodTipoSeccion ";
     	     		query += ",cast(iCodBcoPreg as TEXT) as iCodBcoPreg ";
     	     		query += ",cSeccion ";
     	     		query += ",vDetalleSeccion ";
     	     		query += ",CAST(iValor AS TEXT) AS iValor ";
     	     		query += ",iTipoControl ";
     	     		query += ",iOrden ";
     	     		query += ",iTipoColumna ";
     	     		query += ",t2.vTabla ";
     	     		query += ",t2.vCampo ";
     	     		query += "from SMMAEFICHAS t1 ";
     	     		query += "inner join  SMBCOPREGUNTAS t2 on t2.iCodFicha = t1.iCodFicha ";
     	     		query += "where ";
     	     		query += " t2.bactivo = 1 ";
     	     		query += "and t2.bVisible = 1 ";
     	     		query += "and t2.iCodFicha = IFNULL(" + type + ",t2.iCodFicha) ";
     	     		//query += "AND iCodTipoSeccion = 7 "; SE AGREGA MÁS SECCIONES PARA LA FICHA DE SEGUIMIENTO
     	     		query += "AND iCodTipoSeccion  in (7,8,9,10,11,12) ";
     	     		query += "UNION ";
     	     		query += "select  ";
     	     		query += "8 as ordenllamada ";
     	     		query += ",t1.iCodFicha  as iCodFicha";
     	     		query += ",iCodTipoSeccion ";
     	     		query += ",'' AS iCodBcoPreg ";
     	     		query += ",cSeccion ";
     	     		query += ",vDetalleSeccion ";
     	     		query += ",iValor ";
     	     		query += ",iTipoControl ";
     	     		query += ",iOrden ";
     	     		query += ",'' AS iTipoColumna ";
     	     		query += ",vTabla ";
     	     		query += ",vCampo ";
     	     		query += "from SMFORMATO t1 ";
     	     		query += "where  ";
     	     		query += " t1.iCodFicha =IFNULL(" + type + ",t1.iCodFicha) ";
     	     		query += "AND T1.iCodTipoSeccion = 8 ";
     	     		
     	     		query += " UNION";
     	     		query += " select";
     	     		query += " 9 as ordenllamada";
     	     		query += " ,t1.iCodFicha  as iCodFicha";
     	     		query += " ,iCodTipoSeccion";
     	     		query += " ,'' AS iCodBcoPreg";
     	     		query += " ,cSeccion";
     	     		query += " ,vDetalleSeccion";
     	     		query += " ,iValor";
     	     		query += " ,iTipoControl";
     	     		query += " ,iOrden";
     	     		query += " ,'' AS iTipoColumna";
     	     		query += ",vTabla ";
     	     		query += ",vCampo ";
     	     		query += " from SMFORMATO t1";
     	     		query += " where ";
     	     		query += "  t1.iCodFicha =IFNULL(" + type + ",t1.iCodFicha)";
     	     		query += " AND T1.iCodTipoSeccion = 9 ";
     	     		query += "order by ordenllamada,iCodTipoSeccion,iOrden ";
     	     		
     	            Cursor fila2=bd.rawQuery(query,null);
     	            int numRows2 = fila2.getCount();   
     	            fila2.moveToFirst();   
     	            for (int i = 0; i < numRows2; ++i) 
     	            {   
     	                	
     	                	 ContentValues registro=new ContentValues();
     	                     registro.put("ordenllamada",fila2.getInt(fila2.getColumnIndex("ordenllamada")));
     	                     registro.put("iCodFicha",fila2.getInt(fila2.getColumnIndex("iCodFicha")));
     	                     registro.put("iCodTipoSeccion",fila2.getInt(fila2.getColumnIndex("iCodTipoSeccion")));
     	                     registro.put("iCodBcoPreg",fila2.getString(fila2.getColumnIndex("iCodBcoPreg")));
     	                     registro.put("cSeccion",fila2.getString(fila2.getColumnIndex("cSeccion")));
     	                     registro.put("vDetalleSeccion",fila2.getString(fila2.getColumnIndex("vDetalleSeccion")));
     	                     registro.put("iValor",fila2.getInt(fila2.getColumnIndex("iValor")));
     	                     registro.put("iTipoControl",fila2.getInt(fila2.getColumnIndex("iTipoControl")));
     	                     registro.put("iOrden",fila2.getInt(fila2.getColumnIndex("iOrden")));
     	                     registro.put("iTipoColumna",fila2.getInt(fila2.getColumnIndex("iTipoColumna")));
     	                     registro.put("vTabla",fila2.getString(fila2.getColumnIndex("vTabla")));
     	                     registro.put("vCampo",fila2.getString(fila2.getColumnIndex("vCampo")));
     	                        
     	                     int id = (int) bd.insert(NOMBRE_TABLA, null, registro);    
     	                     fila2.moveToNext();
     	                     
     	                     count++;
     	             }   
                	      
                     fila1.moveToNext();   
                 }   

	    }
         
         
        bd.close();   
        
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        //return i;
        return count;
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