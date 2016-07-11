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

import pe.gob.qw.rutas.entities.COMPONENTERACION;
import pe.gob.qw.rutas.entities.TIPOCOMPONENTERACION;
import pe.gob.qw.rutas.sqlite.SQLite;

public class COMPONENTERACION_DAO {

	private static String NOMBRE_TABLA="COMPONENTERACION";
	
	

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
				 
		            registro.put("iCodFichaTecnicaRacion",json_data.getInt("iCodFichaTecnicaRacion"));      
		            registro.put("iGrupoRacion",json_data.getInt("iGrupoRacion"));        
		            registro.put("vNombreComponente",json_data.getString("vNombreComponente").trim());        
		            registro.put("vGrupoComponente",json_data.getString("vGrupoComponente").trim()); 
		            registro.put("iCodTipoRacion",json_data.getInt("iCodTipoRacion"));        
		            registro.put("vTipoComponente",json_data.getString("vTipoComponente"));   
		            registro.put("vTipoRacion",json_data.getString("vTipoRacion"));
		     
		          
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
	
	 
	
	
	 public static List<COMPONENTERACION> ListarComponentesPorTipoComponente(Context context, int iCodTipoRacion)
	    {
		 	
		 
	       List<COMPONENTERACION> list=new ArrayList<COMPONENTERACION>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select  iCodFichaTecnicaRacion, iGrupoRacion, vNombreComponente, vGrupoComponente,iCodTipoRacion, vTipoComponente, vTipoRacion"
	        			+ " from "+NOMBRE_TABLA
	        			+ " where  iCodTipoRacion="+iCodTipoRacion + " and Cast(vTipoComponente as decimal)>=0.5 "; 

	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   
	            	   COMPONENTERACION entidad= new COMPONENTERACION();
	            	   
	                   entidad.iCodFichaTecnicaRacion=fila.getInt(0);
	                   entidad.iGrupoRacion=fila.getInt(1);
	                   entidad.vNombreComponente=fila.getString(2);
	                   entidad.vGrupoComponente=fila.getString(3);              
	                   entidad.iCodTipoRacion=fila.getInt(4);
	                   entidad.vTipoComponente=fila.getString(5);
	                   entidad.vTipoRacion=fila.getString(6);
	                   
	                   list.add(entidad);
	                      
	                   fila.moveToNext();   
	               }   
	           
	        }
	       bd.close();   
	       return list;
	    }
	
	 public static List<COMPONENTERACION> ListarComponentesPorTipoComponenteAcompanamiento(Context context, int iCodTipoRacion, String vTipoComponente)
	    {
		 	
		 
	       List<COMPONENTERACION> list=new ArrayList<COMPONENTERACION>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select  iCodFichaTecnicaRacion, iGrupoRacion, vNombreComponente, vGrupoComponente,iCodTipoRacion, vTipoComponente, vTipoRacion"
	        			+ " from "+NOMBRE_TABLA
	        			+ " where  iCodTipoRacion="+iCodTipoRacion 
	        			+ "  and CAST(vTipoComponente as DECIMAL)+"+vTipoComponente +"<1.1"
	        			+ "  AND CAST(vTipoComponente as DECIMAL)+"+vTipoComponente +">0.8"
	        			+ " AND 0.5<"+vTipoComponente +" or (vTipoComponente='0.5' AND 0.6="+ vTipoComponente + ")"; 

	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   
	            	   COMPONENTERACION entidad= new COMPONENTERACION();
	            	   
	                   entidad.iCodFichaTecnicaRacion=fila.getInt(0);
	                   entidad.iGrupoRacion=fila.getInt(1);
	                   entidad.vNombreComponente=fila.getString(2);
	                   entidad.vGrupoComponente=fila.getString(3);              
	                   entidad.iCodTipoRacion=fila.getInt(4);
	                   entidad.vTipoComponente=fila.getString(5);
	                   entidad.vTipoRacion=fila.getString(6);
	                   
	                   list.add(entidad);
	                      
	                   fila.moveToNext();   
	               }   
	           
	        }
	       bd.close();   
	       return list;
	    }
	 

	 public static List<TIPOCOMPONENTERACION> ListarTipoComponente(Context context, int iTipoRacion, String vTipoComponente)
	    {
		 	
		 
	       List<TIPOCOMPONENTERACION> list=new ArrayList<TIPOCOMPONENTERACION>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select  distinct iCodTipoRacion, vTipoRacion"
	        			+ " from "+NOMBRE_TABLA +" where iCodTipoRacion="+ (iTipoRacion==3?"iCodTipoRacion":iTipoRacion); 

	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   
	            	   TIPOCOMPONENTERACION entidad= new TIPOCOMPONENTERACION();
	            	   
	                   entidad.iCodTipoRacion=fila.getInt(0);
	                   entidad.vTipoRacion=fila.getString(1);   
	                   list.add(entidad);
	                      
	                   fila.moveToNext();   
	               }   
	           
	        }
	       bd.close();   
	       return list;
	    }
	
	 
	 
	 public static List<TIPOCOMPONENTERACION> ListarNivelEducativo(Context context, int iCodItem, String cTurno, String cCodEstablecimiento, String cCodProveedor, int iCodLiberacion)
	    {
		 	
		 
	       List<TIPOCOMPONENTERACION> list=new ArrayList<TIPOCOMPONENTERACION>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select  distinct cCodNivelEducativo, vNivelEducativo"
	        			+ " from ACTACOLEGIOSITEM where cCodNivelEducativo !='null' and iCodItem="+ iCodItem+ " and cCodTurno='"+cTurno+"'"
	        			+ " and cCodEstablecimiento='"+ cCodEstablecimiento +"'" 
	        			+ " and cCodProveedor='"+ cCodProveedor+"'"
	        			+ " and iCodLiberacion=" + iCodLiberacion ; 

	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   
	            	   TIPOCOMPONENTERACION entidad= new TIPOCOMPONENTERACION();
	            	   
	                   entidad.iCodTipoRacion=fila.getInt(0);
	                   entidad.vTipoRacion=fila.getString(1);   
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
