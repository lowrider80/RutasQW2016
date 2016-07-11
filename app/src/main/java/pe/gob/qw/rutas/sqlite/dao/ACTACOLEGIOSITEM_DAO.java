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

import pe.gob.qw.rutas.entities.ACTACOLEGIOSITEM;
import pe.gob.qw.rutas.entities.COLEGIOSITEM;
import pe.gob.qw.rutas.entities.ITEM;
import pe.gob.qw.rutas.entities.LIBERACIONRACION;
import pe.gob.qw.rutas.entities.TURNO;
import pe.gob.qw.rutas.sqlite.SQLite;

public class ACTACOLEGIOSITEM_DAO {

private static String NOMBRE_TABLA="ACTACOLEGIOSITEM";

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
        if(Array.length()>0){
        	ACTACOLEGIOSITEM_DAO.BorrarData(context);
        }
        for(i=0;i<Array.length();i++)
		{
        	
            JSONObject json_data;
			try {
				
				json_data = Array.getJSONObject(i);
				
				
				 	ContentValues registro=new ContentValues();
				 
		            registro.put("cCodColegio",json_data.getString("cCodColegio").trim());      
		            registro.put("iCodLiberacion",json_data.getInt("iCodLiberacion"));        
		            registro.put("cCodTurno",json_data.getString("cCodTurno").trim());        
		            registro.put("cCodEstablecimiento",json_data.getString("cCodEstablecimiento").trim()); 
		            registro.put("cCodProveedor",json_data.getString("cCodProveedor").trim());        
		            registro.put("cEstado",json_data.getString("cEstado"));        
		            registro.put("idFichasGrabadas",0);   
		            registro.put("iCantidadProgramada",json_data.getString("iCantidadProgramada"));   
		            registro.put("iCantidadLiberada",json_data.getString("iCantidadProgramada"));
		            registro.put("iCodItem",json_data.getInt("iCodItem"));
		            //registro.put("vObservacion",json_data.getInt("vObservacion"));
		            registro.put("iOrden", json_data.getInt("iOrden"));
		            //anexo
		            registro.put("iCodUnidad",json_data.getInt("iCodUnidad"));   
		            registro.put("cCodModular",json_data.getString("cCodModular"));
		            registro.put("cCodNivelEducativo",json_data.getString("cCodNivelEducativo"));
		            registro.put("vNivelEducativo",json_data.getString("vNombreNivel"));
		            registro.put("vNombreColegio",json_data.getString("vNombreColegio"));
		            registro.put("vFechaLiberacion",json_data.getString("vFechaLiberacion"));
		            registro.put("iEstadoAdendado",json_data.getInt("iEstadoAdendado"));//json_data.getInt("iEstadoAdendado"));
		            
		     
		          
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
	

public static  int ActualizarColegiosActa(Context context, int idFichasGrabadas, int iCodItem, String cCodEstablecimiento,
										  String cCodProveedor, String cCodTurno, int iCodLiberacion)
{
 int numRows=0;
   SQLite admin=new SQLite(context,null); 
   SQLiteDatabase bd=admin.getWritableDatabase();
   Cursor fila;
  
    if(bd!=null)
    {
    	
    	
               ContentValues registro=new ContentValues();
            
	            registro.put("idFichasGrabadas",idFichasGrabadas);        
	              
	            numRows = bd.update(NOMBRE_TABLA, registro, "cCodEstablecimiento='"+cCodEstablecimiento
	            											+"' and cCodProveedor='"+cCodProveedor+"' " 
	            											+" and cCodTurno='"+cCodTurno+"'"
	            											+" and iCodItem="+iCodItem
	            											+" and iCodLiberacion="+iCodLiberacion, null);
              // numRows = (int) bd.update(table, values, whereClause, whereArgs)(NOMBRE_TABLA, null, registro);  
          
    }
   bd.close();   
   return numRows;
   
}


public static  int ActualizarColegiosEliminarActa(Context context, int idFichasGrabadas)
{
 int numRows=0;
   SQLite admin=new SQLite(context,null); 
   SQLiteDatabase bd=admin.getWritableDatabase();
   Cursor fila;
  
    if(bd!=null)
    {
    	
    	
               ContentValues registro=new ContentValues();
            
	            registro.put("idFichasGrabadas",0);        
	            registro.put("cEstado","1");  
	            numRows = bd.update(NOMBRE_TABLA, registro, "idFichasGrabadas="+idFichasGrabadas, null);
              // numRows = (int) bd.update(table, values, whereClause, whereArgs)(NOMBRE_TABLA, null, registro);  
          
    }
   bd.close();   
   return numRows;
   
}

public static  int ObtenerLiberacion(Context context, int iCodItem, String cCodEstablecimiento,
									 String cCodProveedor, String cCodTurno)
{
int numRows=0;
SQLite admin=new SQLite(context,null); 
SQLiteDatabase bd=admin.getWritableDatabase();
Cursor fila;
int codLiberacion=0;
if(bd!=null)
{
	String query="select distinct iCodLiberacion from  "
			+ NOMBRE_TABLA+" where cCodProveedor='"+cCodProveedor
			+"' and cCodEstablecimiento='"+cCodEstablecimiento+"' and cEstado=1"
			+" and cCodTurno='" +cCodTurno
			+"' and iCodItem="+iCodItem;	 
	Cursor fila1=bd.rawQuery(query,null);
	int numRows1 = fila1.getCount();   
	
	if(fila1.moveToFirst())
		codLiberacion=fila1.getInt(0);
}
bd.close();   
return codLiberacion;

}


public  static boolean Actualizar(Context context, ACTACOLEGIOSITEM entidad)
{
	   SQLite admin=new SQLite(context,null);
   SQLiteDatabase bd = admin.getWritableDatabase();
   ContentValues registro = new ContentValues();
   
   //registro.put("iCodItem",entidad.iCodItem);
   //registro.put("cCodColegio",entidad.cCodColegio);
   //registro.put("idFichasGrabadas", entidad.idFichasGrabadas);
   registro.put("iCantidadProgramada",entidad.iCantidadProgramada);
   registro.put("iCantidadLiberada",entidad.iCantidadLiberada);
   registro.put("vObservacion",entidad.vObservacion);
   
   int cant = bd.update(NOMBRE_TABLA, registro, "iACTACOLEGIOSITEM="+entidad.iACTACOLEGIOSITEM, null);
   
   bd.close();
   if(cant==1)
       return true;
   else
       return false;
  
} 

public  static boolean ActualizarEstado(Context context, int idFichasGrabadas)
{
	   SQLite admin=new SQLite(context,null);
   SQLiteDatabase bd = admin.getWritableDatabase();
   ContentValues registro = new ContentValues();
   
   registro.put("cEstado","2");
   //registro.put("iCantidadLiberada",entidad.iCantidadLiberada);
   //registro.put("vObservacion",entidad.vObservacion);
   
   int cant = bd.update(NOMBRE_TABLA, registro, "idFichasGrabadas="+idFichasGrabadas, null);
   
   bd.close();
   if(cant==1)
       return true;
   else
       return false;
  
} 


public static  int GetValida(Context context, int idFichasGrabadas )
{
 int numRows=-1;
   SQLite admin=new SQLite(context,null); 
   SQLiteDatabase bd=admin.getWritableDatabase();
   Cursor fila;
  
    if(bd!=null)
    { 
        String query="select  count(*) from "
		        			+ NOMBRE_TABLA + 
		        			" where  CAST(iCantidadLiberada AS integer)=0 and  (LTRIM(vObservacion)='' or vObservacion is null) " + 
		        			"and idFichasGrabadas="+idFichasGrabadas;	        	
		        	
		            fila=bd.rawQuery(query,null);
			        if (fila.moveToFirst())
			        {     
			        	numRows=fila.getInt(0);
			            	
			        }
		    
    }
   return numRows;
   
}



public static  int GetSize(Context context, int idFichasGrabadas)
{
	 int numRows=0;
      SQLite admin=new SQLite(context,null); 
      SQLiteDatabase bd=admin.getWritableDatabase();
      Cursor fila;
     
       if(bd!=null)
       {
       	String query="select  count(*) from "
       			+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas;	    
       	
       	
           fila=bd.rawQuery(query,null);
	            if (fila.moveToFirst())
	            {     
	            	numRows=fila.getInt(0);
	            }

       }
      bd.close();   
      return numRows;
      
   }

	public static List<TURNO> ListarTurno(Context context, String cCodProveedor, String cCodEstablecimiento)
	{
		 List<TURNO> list=new ArrayList<TURNO>();
		   SQLite admin=new SQLite(context,null); 
		   SQLiteDatabase bd=admin.getWritableDatabase();
		   

		    if(bd!=null)
		    {
		    	String query="select distinct cCodTurno,case when cCodTurno='M' then 'MAÑANA' when cCodTurno='T' then 'TARDE'"
		    			+ " else '' end as vNombreTurno from  "
		    			+ NOMBRE_TABLA+" where cCodProveedor='"+cCodProveedor
		    			+"' and cCodEstablecimiento='"+cCodEstablecimiento+"' and cEstado='1'" ;	        	
		    	
		    	Cursor fila1=bd.rawQuery(query,null);
		    	int numRows1 = fila1.getCount();   
		    	fila1.moveToFirst();   
		    	   
		    	for (int i = 0; i < numRows1; ++i) 
		           {   
		    		TURNO entidad= new TURNO();            
		        	   entidad.cCodTurno=fila1.getString(0);
		        	   entidad.vNombreTurno=fila1.getString(1);
		               
		           		//Log.e("entidad.LISTFICHATECNICAPRESENTACION",""+entidad.LISTFICHATECNICAPRESENTACION.size());
		               list.add(entidad);
		                  
		               fila1.moveToNext();   
		           }   
		    }
		   bd.close();   
		   return list;
	}

	public static List<LIBERACIONRACION> ListarLiberacion(Context context, String cCodProveedor, String cCodEstablecimiento, String cCodTurno)
	{
		 List<LIBERACIONRACION> list=new ArrayList<LIBERACIONRACION>();
		   SQLite admin=new SQLite(context,null); 
		   SQLiteDatabase bd=admin.getWritableDatabase();
		   
		  
		    if(bd!=null)
		    {
		    	String query="select distinct a.iCodLiberacion, a.vFechaLiberacion from  "
		    			+ NOMBRE_TABLA+" a " 
		    			+ " where a.cCodProveedor='"+cCodProveedor
		    			+"' and a.cCodEstablecimiento='"+cCodEstablecimiento
		    			+"' and a.cCodTurno='"+cCodTurno+ "' and a.cEstado='1'" ;	        	
		    	
		    	Cursor fila1=bd.rawQuery(query,null);
		    	int numRows1 = fila1.getCount();   
		    	fila1.moveToFirst();   
		    	   
		    	for (int i = 0; i < numRows1; ++i) 
		           {   
		    		LIBERACIONRACION entidad= new LIBERACIONRACION();            
		        	   entidad.iCodLiberacion=fila1.getInt(0);
		        	   entidad.vFechaLiberacion=fila1.getString(1);
		               
		           		//Log.e("entidad.LISTFICHATECNICAPRESENTACION",""+entidad.LISTFICHATECNICAPRESENTACION.size());
		               list.add(entidad);
		                  
		               fila1.moveToNext();   
		           }   
		    }
		   bd.close();   
		   return list;
	}
	
	public static List<ITEM> ListarItem(Context context, String cCodProveedor, String cCodEstablecimiento, String cCodTurno, int iCodLiberacion)
	{
		 List<ITEM> list=new ArrayList<ITEM>();
		   SQLite admin=new SQLite(context,null); 
		   SQLiteDatabase bd=admin.getWritableDatabase();
		   
		  
		    if(bd!=null)
		    {
		    	String query="select distinct a.iCodItem, p.vNombreItem from  "
		    			+ NOMBRE_TABLA+" a  join PROVEEDORESITEMS p on a.cCodProveedor=p.cCodProveedor and  a.iCodItem=p.iCodItem " 
		    			+ " where a.cCodProveedor='"+cCodProveedor
		    			+"' and a.cCodEstablecimiento='"+cCodEstablecimiento
		    			+"' and a.cCodTurno='"+cCodTurno
		    			+"' and a.iCodLiberacion="+iCodLiberacion+ " and a.cEstado='1'" ;	        	
		    	
		    	Cursor fila1=bd.rawQuery(query,null);
		    	int numRows1 = fila1.getCount();   
		    	fila1.moveToFirst();   
		    	   
		    	for (int i = 0; i < numRows1; ++i) 
		           {   
		    		ITEM entidad= new ITEM();            
		        	   entidad.iCodItem=fila1.getInt(0);
		        	   entidad.vNombreItem=fila1.getString(1);
		               
		           		//Log.e("entidad.LISTFICHATECNICAPRESENTACION",""+entidad.LISTFICHATECNICAPRESENTACION.size());
		               list.add(entidad);
		                  
		               fila1.moveToNext();   
		           }   
		    }
		   bd.close();   
		   return list;
	}

	public static List<ACTACOLEGIOSITEM> ListarTodo(Context context, int idFichasGrabadas)
	{
	   List<ACTACOLEGIOSITEM> list=new ArrayList<ACTACOLEGIOSITEM>();
	   SQLite admin=new SQLite(context,null); 
	   SQLiteDatabase bd=admin.getWritableDatabase();
	   
	  
	    if(bd!=null)
	    {
	    	String query="select iACTACOLEGIOSITEM,idFichasGrabadas,iCodItem,cCodColegio,iCantidadProgramada,iCantidadLiberada,vObservacion,"
	    			+ "iCodLiberacion,cCodTurno,cCodEstablecimiento,cCodProveedor,cEstado,iCodUnidad,"
	    			+ " cCodModular,cCodNivelEducativo,vNivelEducativo,vNombreColegio, iOrden,vFechaLiberacion from "
	    			+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas;        	
	    	
	    		    	
	    	
	    	Cursor fila1=bd.rawQuery(query,null);
	    	int numRows1 = fila1.getCount();   
	    	fila1.moveToFirst();   
	         
	    	for (int i = 0; i < numRows1; ++i) 
	           {   
	    		ACTACOLEGIOSITEM entidad= new ACTACOLEGIOSITEM();            
	        	   entidad.iACTACOLEGIOSITEM=fila1.getInt(0);
	        	   entidad.idFichasGrabadas=fila1.getInt(1);
	        	   entidad.iCodItem=fila1.getInt(2);
	               entidad.cCodColegio=fila1.getString(3);
	               entidad.iCantidadProgramada=fila1.getString(4).trim();              
	               entidad.iCantidadLiberada=fila1.getString(5).trim();
	               entidad.vObservacion=fila1.getString(6);
	               entidad.iCodLiberacion=fila1.getInt(7);
	               entidad.cCodTurno=fila1.getString(8);
	               entidad.cCodEstablecimiento=fila1.getString(9);
	               entidad.cCodProveedor=fila1.getString(10).trim();
	               entidad.cEstado=fila1.getString(11);
	               entidad.iCodUnidad=fila1.getInt(12);
	               
	               entidad.cCodModular=fila1.getString(13);
	               entidad.cCodNivelEducativo=fila1.getString(14);
	               entidad.vNivelEducativo=fila1.getString(15).trim();
	               entidad.vNombreColegio=fila1.getString(16);
	               entidad.iOrden=fila1.getInt(17);
	               entidad.vFechaLiberacion=fila1.getString(18);
	               
	           		//Log.e("entidad.LISTFICHATECNICAPRESENTACION",""+entidad.LISTFICHATECNICAPRESENTACION.size());
	               list.add(entidad);
	                  
	               fila1.moveToNext();   
	           }   
	       
	    }
	   bd.close();   
	   return list;
	}

	public static List<ACTACOLEGIOSITEM> ListarPagina(Context context, int idFichasGrabadas, int pInicial, int pFinal)
	{
	   List<ACTACOLEGIOSITEM> list=new ArrayList<ACTACOLEGIOSITEM>();
	   SQLite admin=new SQLite(context,null); 
	   SQLiteDatabase bd=admin.getWritableDatabase();
	   
	  
	    if(bd!=null)
	    {
	    	String query="select iACTACOLEGIOSITEM,idFichasGrabadas,iCodItem,cCodColegio,iCantidadProgramada,iCantidadLiberada,vObservacion,"
	    			+ "iCodLiberacion,cCodTurno,cCodEstablecimiento,cCodProveedor,cEstado,iCodUnidad,"
	    			+ " cCodModular,cCodNivelEducativo,vNivelEducativo,vNombreColegio, iOrden, iEstadoAdendado from "
	    			+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas
	    			+" and iOrden>"+pInicial+" and iOrden<= "+pFinal ;	        	
	    	
	    		    	
	    	
	    	Cursor fila1=bd.rawQuery(query,null);
	    	int numRows1 = fila1.getCount();   
	    	fila1.moveToFirst();   
	         
	    	for (int i = 0; i < numRows1; ++i) 
	           {   
	    		ACTACOLEGIOSITEM entidad= new ACTACOLEGIOSITEM();            
	        	   entidad.iACTACOLEGIOSITEM=fila1.getInt(0);
	        	   entidad.idFichasGrabadas=fila1.getInt(1);
	        	   entidad.iCodItem=fila1.getInt(2);
	               entidad.cCodColegio=fila1.getString(3);
	               entidad.iCantidadProgramada=fila1.getString(4).trim();              
	               entidad.iCantidadLiberada=fila1.getString(5).trim();
	               entidad.vObservacion=fila1.getString(6);
	               entidad.iCodLiberacion=fila1.getInt(7);
	               entidad.cCodTurno=fila1.getString(8);
	               entidad.cCodEstablecimiento=fila1.getString(9);
	               entidad.cCodProveedor=fila1.getString(10).trim();
	               entidad.cEstado=fila1.getString(11);
	               entidad.iCodUnidad=fila1.getInt(12);
	               
	               entidad.cCodModular=fila1.getString(13);
	               entidad.cCodNivelEducativo=fila1.getString(14);
	               entidad.vNivelEducativo=fila1.getString(15).trim();
	               entidad.vNombreColegio=fila1.getString(16);
	               entidad.iOrden=fila1.getInt(17);
	               entidad.iEstadoAdendado=fila1.getInt(18);
	             
	               
	           		//Log.e("entidad.LISTFICHATECNICAPRESENTACION",""+entidad.LISTFICHATECNICAPRESENTACION.size());
	               list.add(entidad);
	                  
	               fila1.moveToNext();   
	           }   
	       
	    }
	   bd.close();   
	   return list;
	}


	public static Boolean VerificarCantidad(Context context, String cantidad, int iACTACOLEGIOSITEM){
		int CantidadEquivale=0;
		SQLite admin=new SQLite(context,null); 
		   SQLiteDatabase bd=admin.getWritableDatabase();
		   
		   if(bd!=null)
		    {
		    	String query="select count(*) from "
		    			+ NOMBRE_TABLA+" where iACTACOLEGIOSITEM="+iACTACOLEGIOSITEM
		    			+" and CAST(iCantidadProgramada as integer)="+ cantidad;	        	
		    	Cursor fila1=bd.rawQuery(query,null);
		    	int numRows1 = fila1.getCount();   
		    	if(fila1.moveToFirst()){
		    		CantidadEquivale =fila1.getInt(0);
		    	}
		         
		    }
		   bd.close();   
		   return CantidadEquivale>0;
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
              String query="delete from "+NOMBRE_TABLA ;
          
              bd.execSQL(query);
              String query2=" delete from RECORDCARDITEM where iCodFicha=18;";
              
              bd.execSQL(query2);
              
              String query3="delete from SMFICHASGRABADAS where iCodFicha=18;";
              
              bd.execSQL(query3);
          }
          bd.close();   
      }


}
