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

import pe.gob.qw.rutas.entities.MATERIAPRIMA;
import pe.gob.qw.rutas.sqlite.SQLite;

public class MATERIAPRIMA_DAO {

private static String NOMBRE_TABLA="MATERIAPRIMA";

public static int Agregar(Context context, MATERIAPRIMA entidad) {
	// TODO Auto-generated method stub
	int id = 0;
	
    SQLite admin=new SQLite(context,null);
    SQLiteDatabase bd=admin.getWritableDatabase();
    ContentValues registro=new ContentValues();
    registro.put("cCodEstablecimiento",entidad.cCodEstablecimiento);
    registro.put("cCodProveedor",entidad.cCodProveedor);
    registro.put("cEstado",entidad.cEstado);
    registro.put("iCodModalidad",entidad.iCodModalidad);
    registro.put("vCaracteristica",entidad.vCaracteristica);
    registro.put("vFabricante",entidad.vFabricante);
    registro.put("vMarca",entidad.vMarca);
    registro.put("vNroLote",entidad.vNroLote);

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
				 
		            registro.put("cCodEstablecimiento",json_data.getString("cCodEstablecimiento").trim());
		            registro.put("cCodProveedor",json_data.getString("cCodProveedor"));
		            registro.put("cEstado",json_data.getString("cEstado").trim());
		            registro.put("iCodModalidad",json_data.getInt("iCodModalidad"));
		            registro.put("vCaracteristica",json_data.getString("vCaracteristica"));
		            registro.put("vFabricante",json_data.getString("vFabricante"));
		            registro.put("vMarca",json_data.getString("vMarca"));
		            registro.put("vNroLote",json_data.getString("vNroLote"));

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
	
	
     public static List<MATERIAPRIMA> Listar(Context context)
     {
        List<MATERIAPRIMA> list=new ArrayList<MATERIAPRIMA>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
              String query="select cCodEstablecimiento,cCodProveedor,"
              		+ "cEstado,iCodModalidad,"
              		+ "vCaracteristica,"
              		+ "vFabricante,vMarca,vNroLote from "
            		  +NOMBRE_TABLA;
                    
                    
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {
                    MATERIAPRIMA entidad= new MATERIAPRIMA();
                    entidad.cCodEstablecimiento=fila.getString(0);
                    entidad.cCodProveedor=fila.getString(1);
                    entidad.cEstado=fila.getString(2);
                    entidad.iCodModalidad=fila.getInt(3);
                    entidad.vCaracteristica=fila.getString(4);
                    entidad.vFabricante=fila.getString(5);
                    entidad.vMarca=fila.getString(6);
                    entidad.vNroLote=fila.getString(7);
               
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
