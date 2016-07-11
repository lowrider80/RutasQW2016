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

import pe.gob.qw.rutas.entities.COLEGIOSITEM;
import pe.gob.qw.rutas.sqlite.SQLite;

public class COLEGIOSITEM_DAO {

private static String NOMBRE_TABLA="COLEGIOSITEM";

public static int Agregar(Context context, COLEGIOSITEM entidad) {
	// TODO Auto-generated method stub
	int id = 0;
	
    SQLite admin=new SQLite(context,null);
    SQLiteDatabase bd=admin.getWritableDatabase();
    ContentValues registro=new ContentValues();
    registro.put("cCodColegio",entidad.cCodColegio);             
    registro.put("iCodUnidad",entidad.iCodUnidad);        
    registro.put("cCodModular",entidad.cCodModular); 
    registro.put("cCodNivelEducativo",entidad.cCodNivelEducativo);        
    registro.put("vNombre",entidad.vNombre);        
    registro.put("vDireccion",entidad.vDireccion);   
    registro.put("vNombreItem",entidad.vNombreItem);   
    registro.put("iCodItem",entidad.iCodItem); 
    registro.put("EstadoItem",entidad.EstadoItem);        
    registro.put("iNroUsuario",entidad.iNroUsuario);      
    
    
   
    id = (int) bd.insert(NOMBRE_TABLA, null, registro);        
    bd.close();    
    
    return id;
}
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
				 
		            registro.put("cCodColegio",json_data.getString("cCodColegio").trim());      
		            registro.put("iCodUnidad",json_data.getInt("iCodUnidad"));        
		            registro.put("cCodModular",json_data.getString("cCodModular").trim());        
		            registro.put("cCodNivelEducativo",json_data.getString("cCodNivelEducativo").trim()); 
		            registro.put("vNombre",json_data.getString("vNombre").trim());        
		            registro.put("vDireccion",json_data.getString("vDireccion"));        
		            registro.put("vNombreItem",json_data.getString("vNombreItem").trim());   
		            registro.put("iCodItem",json_data.getInt("iCodItem"));   
		            registro.put("EstadoItem",json_data.getInt("EstadoItem"));
		            registro.put("iNroUsuario",json_data.getInt("iNroUsuario"));
		          
		            	int id = (int) bd.insert(NOMBRE_TABLA, null, registro);   
			            if(0==id)
			            {
			            	bd.close();  
			            	
			            	i=Array.length();
			            	return 0;	            	
			            }else
			            {
			            	
			            }
		            
				} catch (JSONException e) {
					bd.close();  
					return 0;
				}
        }
             
		bd.close();    
	    return i;
	}
	
	
     public static List<COLEGIOSITEM> Listar(Context context, int iCodItem)
     {
        List<COLEGIOSITEM> list=new ArrayList<COLEGIOSITEM>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
              String query="select cCodColegio,iCodUnidad,"
              		+ "cCodModular,cCodNivelEducativo,"
              		+ "vNombre,"
              		+ "vDireccion,vNombreItem,iCodItem,EstadoItem,iNroUsuario from "
            		  +NOMBRE_TABLA+" where iCodItem  ="+iCodItem;
                    
                    
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {   

                	COLEGIOSITEM entidad= new COLEGIOSITEM();     
                    entidad.cCodColegio=fila.getString(0);
                    entidad.iCodUnidad=fila.getInt(1);
                    entidad.cCodModular=fila.getString(2);
                    entidad.cCodNivelEducativo=fila.getString(3);
                    entidad.vNombre=fila.getString(4);
                    entidad.vDireccion=fila.getString(5);
                    entidad.vNombreItem=fila.getString(6);
                    entidad.iCodItem=fila.getInt(7);
                    entidad.EstadoItem=fila.getInt(8);
                    entidad.iNroUsuario =fila.getInt(9);
               
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
