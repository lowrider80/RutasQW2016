package pe.gob.qw.rutas.sqlite.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.gob.qw.rutas.entities.SMBCOPREGUNTAS;
import pe.gob.qw.rutas.sqlite.SQLite;

public class SMBCOPREGUNTAS_DAO {
    
    private static String NOMBRE_TABLA="SMBCOPREGUNTAS";
    
	public static int Agregar(Context context, SMBCOPREGUNTAS entidad) {
		// TODO Auto-generated method stub
		int id = 0;
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        ContentValues registro=new ContentValues();
        registro.put("bactivo",entidad.bactivo);
        registro.put("bVisible",entidad.bVisible);
        registro.put("cSeccion",entidad.cSeccion);        
        registro.put("iCodBcoPreg",entidad.iCodBcoPreg);        
        registro.put("iCodFicha",entidad.iCodFicha);        
        registro.put("iCodTipoSeccion",entidad.iCodTipoSeccion);        
        registro.put("iOrden",entidad.iOrden);        
        registro.put("iTipoColumna",entidad.iTipoColumna);        
        registro.put("iTipoControl",entidad.iTipoControl);        
        registro.put("iValor",entidad.iValor);        
        registro.put("vDetalleSeccion",entidad.vDetalleSeccion);   
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
	            registro.put("bactivo",(true == json_data.getBoolean("bactivo"))? 1 : 0);
	            registro.put("bVisible",(true == json_data.getBoolean("bVisible"))? 1 : 0);
	            registro.put("cSeccion",json_data.getString("cSeccion"));        
	            registro.put("iCodBcoPreg",json_data.getInt("iCodBcoPreg"));        
	            registro.put("iCodFicha",json_data.getInt("iCodFicha"));        
	            registro.put("iCodTipoSeccion",json_data.getInt("iCodTipoSeccion"));        
	            registro.put("iOrden",json_data.getInt("iOrden"));        
	            registro.put("iTipoColumna",json_data.getInt("iTipoColumna"));        
	            registro.put("iTipoControl",json_data.getInt("iTipoControl"));        
	            registro.put("iValor",json_data.getInt("iValor"));        
	            registro.put("vDetalleSeccion", json_data.getString("vDetalleSeccion"));   
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
//  
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