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

import pe.gob.qw.rutas.entities.SMMAEFICHAS;
import pe.gob.qw.rutas.sqlite.SQLite;

public class SMMAEFICHAS_DAO {
private static String NOMBRE_TABLA="SMMAEFICHAS";
    
    public static int Agregar(Context context, SMMAEFICHAS entidad)
   {
       int id = 0;
       SQLite admin=new SQLite(context,null);
       SQLiteDatabase bd=admin.getWritableDatabase();
       ContentValues registro=new ContentValues();
       registro.put("bactivo",entidad.bactivo);
       registro.put("dtFecReg",entidad.dtFecReg);  
       registro.put("iCodFicha",entidad.iCodFicha);
       registro.put("iCodTipoInst",entidad.iCodTipoInst);   
       registro.put("iCodUsuario",entidad.iCodUsuario);
       registro.put("vDescFicha",entidad.vDescFicha);
       registro.put("vIpTerminal",entidad.vIpTerminal);
       registro.put("vNombreTerminal",entidad.vNombreTerminal);
       registro.put("cCategoria",entidad.cCategoria);
       
       id = (int) bd.insert(NOMBRE_TABLA, null, registro);
       bd.close();    
       return id;

   } 
    
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
		            registro.put("bactivo",(true == json_data.getBoolean("bactivo"))? 1 : 0);
		            registro.put("dtFecReg",json_data.getString("dtFecReg"));  
		            registro.put("iCodFicha",json_data.getInt("iCodFicha"));
		            registro.put("iCodTipoInst",json_data.getInt("iCodTipoInst"));   
		            registro.put("iCodUsuario",json_data.getInt("iCodUsuario"));
		            registro.put("vDescFicha",json_data.getString("vDescFicha"));
		            registro.put("vIpTerminal",json_data.getString("vIpTerminal"));
		            registro.put("vNombreTerminal",json_data.getString("vNombreTerminal"));
		            registro.put("cCategoria",json_data.getString("cCategoria").trim());
		            
		            int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
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

//    public  static boolean Actualizar(Context context,clsMovimiento entidad) 
//    {
//       bdSQLite admin=new bdSQLite(context,null);
//       SQLiteDatabase bd = admin.getWritableDatabase();
//       ContentValues registro = new ContentValues();
//       registro.put("str_nombre",entidad.getStr_nombre());
//       registro.put("dat_fecha_actualizacion",entidad.getDat_fecha_actualizacion().getTime());
//       registro.put("int_id_tipo_producto",entidad.getObjTipoProducto().getInt_id_tipo_producto());
//       registro.put("str_nombre_tipo_producto",entidad.getObjTipoProducto().getStr_nombre());
//       registro.put("int_id_marca",entidad.getObjMarca().getInt_id_marca());
//       registro.put("str_nombre_marca",entidad.getObjMarca().getStr_nombre());
//       
//       int cant = bd.update(NOMBRE_TABLA, registro, "int_id_producto="+entidad.getInt_id_producto(), null);
//       bd.close();
//       if(cant==1)
//           return true;
//       else
//           return false;
//      
//   }  
//
//    public static  clsMovimiento Buscar(Context context,int id)
//    {
//       clsMovimiento  entidad=null;
//       bdSQLite admin=new bdSQLite(context,null); 
//       SQLiteDatabase bd=admin.getWritableDatabase();
//        if(bd!=null)
//        {
//           String query="select int_id_producto,str_nombre,dat_fecha_actualizacion,int_id_tipo_producto,"
//                   +"str_nombre_tipo_producto,int_id_marca,str_nombre_marca from "+NOMBRE_TABLA
//                   +" where int_id_producto="+id;
//
//           Cursor fila=bd.rawQuery(query,null);
//           if (fila.moveToFirst())
//           {          
//               clsTipoProducto objTipoProducto = new clsTipoProducto();
//               objTipoProducto.setInt_id_tipo_producto(fila.getInt(3));
//               objTipoProducto.setStr_nombre(fila.getString(4));
//               
//               clsMarca objMarca = new clsMarca();
//               objMarca.setInt_id_marca(fila.getInt(5));
//               objMarca.setStr_nombre(fila.getString(6));
//               
//               entidad= new clsMovimiento();            
//               entidad.setInt_id_producto(fila.getInt(0));
//               entidad.setStr_nombre(fila.getString(1));
//               entidad.setDat_fecha_actualizacion(new Date(fila.getLong(2)));
//               entidad.setObjTipoProducto(objTipoProducto);
//               entidad.setObjMarca(objMarca);
//               
//           }
//       }
//       bd.close();   
//       return entidad;
//    }
//    

    public static List<SMMAEFICHAS> Listar(Context context)
    {
       List<SMMAEFICHAS> list=new ArrayList<SMMAEFICHAS>();
       SQLite admin=new SQLite(context,null); 
       SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
             String query="select iCodFicha,vDescFicha from "+NOMBRE_TABLA;
           Cursor fila=bd.rawQuery(query,null);
           int numRows = fila.getCount();   
           fila.moveToFirst();   
               for (int i = 0; i < numRows; ++i) 
               {   

            	   SMMAEFICHAS entidad= new SMMAEFICHAS();            
                   entidad.iCodFicha=fila.getInt(0);
                   entidad.vDescFicha=fila.getString(1);
                   list.add(entidad);
                      
                   fila.moveToNext();   
               }   
        }
       bd.close();   
       return list;
    }

    public static  SMMAEFICHAS BuscarXTipo(Context context, int iTipo, String cCategoria)
    {
    	SMMAEFICHAS entidad = null;
       SQLite admin=new SQLite(context,null); 
       SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
             String query="select iCodFicha,vDescFicha from "+NOMBRE_TABLA+" where cCategoria like '"+cCategoria+"' and iCodTipoInst like '"+iTipo+"'";
           Cursor fila=bd.rawQuery(query,null);
          
               if(fila.moveToFirst()) 
               {   

            	   entidad= new SMMAEFICHAS();            
                   entidad.iCodFicha=fila.getInt(0);
                   entidad.vDescFicha=fila.getString(1);
               }   
        }
       bd.close();   
       return entidad;
    }
    

    public static List<SMMAEFICHAS> ListarCategoria(Context context)
    {
       List<SMMAEFICHAS> list=new ArrayList<SMMAEFICHAS>();
       SQLite admin=new SQLite(context,null); 
       SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
             String query="select distinct cCategoria from "+NOMBRE_TABLA;
           Cursor fila=bd.rawQuery(query,null);
           int numRows = fila.getCount();   
           fila.moveToFirst();   
               for (int i = 0; i < numRows; ++i) 
               {   

            	   SMMAEFICHAS entidad= new SMMAEFICHAS();    
                   entidad.cCategoria=fila.getString(0);
                   list.add(entidad);
                      
                   fila.moveToNext();   
               }   
        }
       bd.close();   
       return list;
    }
    
    public static SMMAEFICHAS BuscarId(Context context, int iCodFicha)
    {
     
    SMMAEFICHAS entidad=null;
   	SQLite admin=new SQLite(context,null); 
      	SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
        	String query="select iCodFicha,vDescFicha,cCategoria,iCodTipoInst from "+NOMBRE_TABLA
        			+" where iCodFicha="+iCodFicha;
           Cursor fila=bd.rawQuery(query,null);
           if (fila.moveToFirst())
           {          
                           
        	   entidad= new SMMAEFICHAS();            
               entidad.iCodFicha=fila.getInt(0);
               entidad.vDescFicha=fila.getString(1);
               entidad.cCategoria=fila.getString(2);
               entidad.iCodTipoInst=fila.getInt(3);
               
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