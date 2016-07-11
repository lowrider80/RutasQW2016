package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.gob.qw.rutas.entities.SMCAUSALES;
import pe.gob.qw.rutas.sqlite.SQLite;

public class SMCAUSALES_DAO {
private static String NOMBRE_TABLA="SMCAUSALES";
    
	public static int Agregar(Context context, SMCAUSALES entidad) {
		// TODO Auto-generated method stub
		int id = 0;
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        ContentValues registro=new ContentValues();
        registro.put("bActivo",entidad.bActivo);
        registro.put("cAlternativa",entidad.cAlternativa);
        registro.put("iCodFicha",entidad.iCodFicha);        
        registro.put("iCodTipoCausal",entidad.iCodTipoCausal);        
        registro.put("iOrden",entidad.iOrden);        
        registro.put("iTipoControl",entidad.iTipoControl);    
        registro.put("vDesCausal",entidad.vDesCausal);   
        registro.put("vTabla",entidad.vTabla);    
        registro.put("vCampo",entidad.vCampo);   

        id = (int) bd.insert(NOMBRE_TABLA, null, registro);        
        bd.close();    
        return id;
	}

	public static int AgregarServicio(Context context, JSONArray Array) {
		// TODO Auto-generated method stub
		int i;
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        for(i=0;i<Array.length();i++)
		{
            JSONObject json_data;
			try {
				json_data = Array.getJSONObject(i);
				ContentValues registro=new ContentValues();
	            registro.put("bActivo",(true == json_data.getBoolean("bActivo"))? 1 : 0);
	            registro.put("cAlternativa", json_data.getString("cAlternativa"));
	            registro.put("iCodFicha",json_data.getInt("iCodFicha"));        
	            registro.put("iCodTipoCausal",json_data.getInt("iCodTipoCausal"));        
	            registro.put("iOrden",json_data.getInt("iOrden"));        
	            registro.put("iTipoControl",json_data.getInt("iTipoControl"));    
	            registro.put("vDesCausal",json_data.getString("vDesCausal"));   
	            registro.put("vTabla",json_data.getString("vTabla"));    
	            registro.put("vCampo",json_data.getString("vCampo"));   
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

//     public  static boolean Actualizar(Context context,clsMovimiento entidad) 
//     {
//        bdSQLite admin=new bdSQLite(context,null);
//        SQLiteDatabase bd = admin.getWritableDatabase();
//        ContentValues registro = new ContentValues();
//        registro.put("str_nombre",entidad.getStr_nombre());
//        registro.put("dat_fecha_actualizacion",entidad.getDat_fecha_actualizacion().getTime());
//        registro.put("int_id_tipo_producto",entidad.getObjTipoProducto().getInt_id_tipo_producto());
//        registro.put("str_nombre_tipo_producto",entidad.getObjTipoProducto().getStr_nombre());
//        registro.put("int_id_marca",entidad.getObjMarca().getInt_id_marca());
//        registro.put("str_nombre_marca",entidad.getObjMarca().getStr_nombre());
//        
//        int cant = bd.update(NOMBRE_TABLA, registro, "int_id_producto="+entidad.getInt_id_producto(), null);
//        bd.close();
//        if(cant==1)
//            return true;
//        else
//            return false;
//       
//    }  
//
//     public static  clsMovimiento Buscar(Context context,int id)
//     {
//        clsMovimiento  entidad=null;
//        bdSQLite admin=new bdSQLite(context,null); 
//        SQLiteDatabase bd=admin.getWritableDatabase();
//         if(bd!=null)
//         {
//            String query="select int_id_producto,str_nombre,dat_fecha_actualizacion,int_id_tipo_producto,"
//                    +"str_nombre_tipo_producto,int_id_marca,str_nombre_marca from "+NOMBRE_TABLA
//                    +" where int_id_producto="+id;
//
//            Cursor fila=bd.rawQuery(query,null);
//            if (fila.moveToFirst())
//            {          
//                clsTipoProducto objTipoProducto = new clsTipoProducto();
//                objTipoProducto.setInt_id_tipo_producto(fila.getInt(3));
//                objTipoProducto.setStr_nombre(fila.getString(4));
//                
//                clsMarca objMarca = new clsMarca();
//                objMarca.setInt_id_marca(fila.getInt(5));
//                objMarca.setStr_nombre(fila.getString(6));
//                
//                entidad= new clsMovimiento();            
//                entidad.setInt_id_producto(fila.getInt(0));
//                entidad.setStr_nombre(fila.getString(1));
//                entidad.setDat_fecha_actualizacion(new Date(fila.getLong(2)));
//                entidad.setObjTipoProducto(objTipoProducto);
//                entidad.setObjMarca(objMarca);
//                
//            }
//        }
//        bd.close();   
//        return entidad;
//     }
//     
     /**
     public static  List<clsMovimiento> Listar(Context context,int tipo)
     {
        List<clsMovimiento> list=new ArrayList<clsMovimiento>();
        bdSQLite admin=new bdSQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
              String query="select m.int_id_movimiento,m.int_id_tipo_movimiento,m.int_id_servicio,m.str_detalle,"
                      + "m.dou_total,m.dou_total_acumulado,m.int_couta_total,m.int_couta_ingresadas,m.int_estado,"
                      + "m.int_alerta_inicio,m.int_repeticion_alerta,m.dat_fecha_creacion,m.dat_fecha_movimiento,"
                      + "m.bool_ingreso,s.str_nombre from "+NOMBRE_TABLA+" as m inner join SERVICIO as s on "
                      + "m.int_id_servicio=s.int_id_servicio";
              
                    if(tipo==1)
                        query+=" where m.bool_ingreso=1";
                    else if(tipo==2)
                        query+=" where m.bool_ingreso=0";
                    
                    query+=" order by m.dat_fecha_movimiento ASC";
                    
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {   

                    clsMovimiento entidad= new clsMovimiento();            
                    entidad.setInt_id_movimiento(fila.getInt(0));
                    entidad.setObjTipoMovimiento(new clsTipoMovimiento(fila.getInt(1),fila.getString(13)));
                    entidad.setObjServicio(new clsServicio(fila.getInt(2)));                    
                    entidad.setStr_detalle(fila.getString(3));
                    entidad.setDou_total(fila.getDouble(4));
                    entidad.setDou_total_acumulado(fila.getDouble(5));
                    entidad.setInt_couta_total(fila.getInt(6));
                    entidad.setInt_couta_ingresadas(fila.getInt(7));
                    entidad.setInt_estado(fila.getInt(8));
                    entidad.setInt_alerta_inicio(fila.getInt(9));
                    entidad.setInt_repeticion_alerta(fila.getInt(10));
                    entidad.setDat_fecha_creacion(new Date(fila.getLong(11)));
                    entidad.setDat_fecha_movimiento(new Date(fila.getLong(12)));
                    
                    if(fila.getInt(13)==1)
                    entidad.setBool_ingreso(true);
                    else
                        entidad.setBool_ingreso(false);
                    
                    list.add(entidad);
                       
                    fila.moveToNext();   
                }   
         }
        bd.close();   
        return list;
     }
     
   */
     
     public static void Borrar(Context context) {
    	 SQLite admin=new SQLite(context,null);
	     SQLiteDatabase bd=admin.getWritableDatabase();
	     bd.delete(NOMBRE_TABLA, null, null);
	     bd.close();
    }

     public static void BorrarxFicha(Context context, int idFichasGrabadas) {
    	 SQLite admin=new SQLite(context,null);
	     SQLiteDatabase bd=admin.getWritableDatabase();
	     bd.delete(NOMBRE_TABLA, " idFichasGrabadas="+idFichasGrabadas, null);
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
