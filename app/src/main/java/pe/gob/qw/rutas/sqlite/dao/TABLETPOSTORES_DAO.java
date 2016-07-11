
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

import pe.gob.qw.rutas.entities.SMFICHAINICIALANEXO;
import pe.gob.qw.rutas.entities.SMMAEFICHAS;
import pe.gob.qw.rutas.entities.TABLETPOSTORES;
import pe.gob.qw.rutas.entities.TABLETPROVEEDORES;
import pe.gob.qw.rutas.sqlite.SQLite;

public class TABLETPOSTORES_DAO {

private static String NOMBRE_TABLA="TABLETPOSTORES";

public static int Agregar(Context context, TABLETPROVEEDORES entidad) {
	// TODO Auto-generated method stub
	int id = 0;
	
    SQLite admin=new SQLite(context,null);
    SQLiteDatabase bd=admin.getWritableDatabase();
    ContentValues registro=new ContentValues();
    registro.put("cCodDepartamento",entidad.cCodDepartamento);
    registro.put("cCodEstablecimiento",entidad.cCodEstablecimiento);        
    registro.put("cCodProveedor",entidad.cCodProveedor);        
    registro.put("cCodProvincia",entidad.cCodProvincia);        
    registro.put("cCodUbigeo",entidad.cCodUbigeo); 
    registro.put("cMultiple",entidad.cMultiple);        
    registro.put("cRUC",entidad.cRUC);        
    registro.put("iNro",entidad.iNro);        
    registro.put("vDireccionEstablecimiento",entidad.vDireccionEstablecimiento);   
    registro.put("vEstablecimientoDepartamento",entidad.vEstablecimientoDepartamento);   
    registro.put("vEstablecimientoDistrito",entidad.vEstablecimientoDistrito);   
    registro.put("vEstablecimientoProvincia",entidad.vEstablecimientoProvincia); 
    registro.put("vLatitudEstablecimiento",entidad.vLatitudEstablecimiento);        
    registro.put("vLongitudEstablecimiento",entidad.vLongitudEstablecimiento);        
    registro.put("vNombreProveedor",entidad.vNombreProveedor);        
    registro.put("vReferenciaEstablecimiento",entidad.vReferenciaEstablecimiento);   
    registro.put("iTipo",entidad.iTipo);
    registro.put("vObservacion",entidad.vObservacion);  
    
    
    
    
   
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
				 	registro.put("iCodConvocatoria",json_data.getInt("iCodConvocatoria"));
		            registro.put("vConvocatoria",json_data.getString("vConvocatoria").trim());
		            registro.put("iCodModalidad",json_data.getInt("iCodModalidad"));
		            registro.put("vModalidad",json_data.getString("vModalidad").trim());
		            registro.put("iCodUnidad",json_data.getInt("iCodUnidad"));
		            registro.put("vUnidad",json_data.getString("vUnidad").trim());
		            registro.put("iCodComite",json_data.getInt("iCodComite"));
		            registro.put("vComite",json_data.getString("vComite").trim());
		            registro.put("iCodItem",json_data.getInt("iCodItem"));
		            registro.put("vItem",json_data.getString("vItem").trim());
		            registro.put("iCodPostor",json_data.getInt("iCodPostor"));
		            registro.put("cCodProveedor",json_data.getString("cCodProveedor").trim());
		            registro.put("vNombreProveedor",json_data.getString("vNombreProveedor").trim());
		            registro.put("cRuc",json_data.getString("cRuc").trim());
		            registro.put("cCodEstablecimiento",json_data.getString("cCodEstablecimiento").trim());
		            registro.put("vDireccionEstablecimiento",json_data.getString("vDireccionEstablecimiento").trim());
		            registro.put("vComponentes",json_data.getString("vComponentes"));        
		            registro.put("iPuntajeTecnico",json_data.getInt("iPuntajeTecnico"));        
		            registro.put("iPuntajeEconomico",json_data.getInt("iPuntajeEconomico")); 
		            registro.put("dFechaInicio",json_data.getString("dFechaInicio").trim()); 
		            registro.put("dFechaFin",json_data.getString("dFechaFin").trim());        
		               
		               
		             
		            
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


	public static List<TABLETPOSTORES> ListarComitesxEstablecimiento(Context context, String cCodEstablecimiento, int iCodFicha)//String cCodProveedor,String TipoFIcha)
	{
	   List<TABLETPOSTORES> list=new ArrayList<TABLETPOSTORES>();
	   SQLite admin=new SQLite(context,null); 
	   SQLiteDatabase bd=admin.getWritableDatabase();
	    if(bd!=null)
	    {
	   	 String query="select distinct P.iCodModalidad, P.vModalidad, "
	    			+ " P.iCodComite,P.vComite,GROUP_CONCAT(distinct P.iCodItem),GROUP_CONCAT(distinct P.vItem),GROUP_CONCAT(distinct P.iCodPostor)"
	    			+ ",P.cCodEstablecimiento from "
	         		+ NOMBRE_TABLA+" as P join SMMAEFICHAS as F on F.iCodFicha in(19,20) and P.iCodModalidad=CASE WHEN(F.iCodTipoInst=1) then 2 else 1 end "
	         		+ "where P.cCodEstablecimiento = '"+cCodEstablecimiento+"' and F.iCodFicha="+ iCodFicha//+"' and P.vEstablecimientoProvincia like '"+Proveedor+"'";// Se comento ya que la valdicación ya no es necesaria y además afectaba la visualizacion de postores de fichas iniciales
	   	 		+ " group by P.iCodModalidad,P.vModalidad, "
	   	 		+" P.iCodComite,P.vComite, P.cCodEstablecimiento";
	    	                       
	       Cursor fila=bd.rawQuery(query,null);
	       int numRows = fila.getCount();   
	       fila.moveToFirst();   
	           for (int i = 0; i < numRows; ++i) 
	           {   
	
	           	TABLETPOSTORES entidad= new TABLETPOSTORES();     
	           	//entidad.iCodConvocatoria = fila.getInt(0);
	           	//entidad.vConvocatoria= fila.getString(1);
	           	entidad.iCodModalidad=fila.getInt(0);
	           	entidad.vModalidad=fila.getString(1);
	          	entidad.iCodComite =fila.getInt(2);
	           	entidad.vComite=fila.getString(3);
	           	entidad.vCodItem=fila.getString(4);
	           	entidad.vItem=fila.getString(5);
	           	entidad.vCodPostor=fila.getString(6);
	            entidad.cCodEstablecimiento=fila.getString(7);
	               //entidad.vReferenciaEstablecimiento=fila.getString(5);
	               //entidad.iPuntajeTecnico=fila.getInt(14);
	               //entidad.iPuntajeEconomico=fila.getInt(15);
	               //entidad.dFechaInicio=fila.getString(16);
	               //entidad.dFechaFin=fila.getString(17);
	               
	          
	               list.add(entidad);
	                  
	               fila.moveToNext();   
	           }   
	    }
	   bd.close();   
	   return list;
	}

	
	
     public static List<TABLETPOSTORES> Listar(Context context, String cCodUbigeo)
     {
        List<TABLETPOSTORES> list=new ArrayList<TABLETPOSTORES>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
              String query="select cCodProveedor,cCodUbigeo,cRUC,"
              		+ "vNombreProveedor,vEstablecimientoDepartamento,"
              		+ "vEstablecimientoDistrito,vEstablecimientoProvincia,"
              		+ "vDireccionEstablecimiento,vReferenciaEstablecimiento,cCodEstablecimiento from "
            		  +NOMBRE_TABLA+" where cCodUbigeo like '"+cCodUbigeo+"'";
                    
                    
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {   

                	TABLETPOSTORES entidad= new TABLETPOSTORES();     
                    entidad.cCodProveedor=fila.getString(0);
                    entidad.iCodPostor=fila.getInt(1);
                    entidad.cRUC=fila.getString(2);
                    entidad.vNombreProveedor=fila.getString(3);
                    entidad.cCodEstablecimiento=fila.getString(4);
                    entidad.vDireccionEstablecimiento=fila.getString(5);
                    
               
                    list.add(entidad);
                       
                    fila.moveToNext();   
                }   
         }
        bd.close();   
        return list;
     }
     
     public static TABLETPROVEEDORES Buscar(Context context, String cCodProveedor, String cCodEstablecimiento )
     {
      
    	TABLETPROVEEDORES entidad=null;
    	SQLite admin=new SQLite(context,null); 
       	SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	 String query="select P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,"
               		+ "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,"
               		+ "P.vLatitudEstablecimiento,P.vLongitudEstablecimiento,"
               		+ "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
               		+ "P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
               		+ "P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha from "
             		+ NOMBRE_TABLA+" as P inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst where P.cCodProveedor like '"+cCodProveedor
             		+ "' and P.cCodEstablecimiento like '"+cCodEstablecimiento+"'";
        	 
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {          
                            
            	entidad= new TABLETPROVEEDORES();     
                entidad.cCodProveedor=fila.getString(0);
                entidad.vNombreProveedor=fila.getString(1);
                entidad.cCodEstablecimiento=fila.getString(2);
                entidad.iNro=fila.getInt(3);
                entidad.vDireccionEstablecimiento=fila.getString(4);
                entidad.vReferenciaEstablecimiento=fila.getString(5);
                entidad.vLatitudEstablecimiento=fila.getString(6);
                entidad.vLongitudEstablecimiento=fila.getString(7);
                entidad.vEstablecimientoDistrito=fila.getString(8);
                entidad.vEstablecimientoProvincia=fila.getString(9);
                entidad.vEstablecimientoDepartamento=fila.getString(10);
                entidad.iTipo=fila.getInt(11);
                entidad.cCodDepartamento=fila.getString(12);
                entidad.cCodProvincia=fila.getString(13);
                entidad.cCodUbigeo=fila.getString(14);
                entidad.cRUC=fila.getString(15);
                entidad.cMultiple=fila.getString(16);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(17),fila.getString(18));
                
            }
        }
        bd.close();   
        return entidad;
     }


     public static TABLETPOSTORES BuscarRuc(Context context, String Ruc)
     {
    	 TABLETPOSTORES entidad=null;
    	SQLite admin=new SQLite(context,null); 
       	SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	 String query="select P.iCodConvocatoria,P.vConvocatoria, P.iCodModalidad, P.iCodModalidad, "
        			+ "P.iCodComite,P.vComite,P.iCodItem,P.vItem,P.iCodPostor,"
        			+ "P.cCodProveedor,P.vNombreProveedor,P.cRuc,P.cCodEstablecimiento,"
               		+ "P.vDireccionEstablecimiento,P.iPuntajeTecnico,P.iPuntajeEconomico,"
        			+" P.dFechaInicio,P.dFechaFin, F.iCodFicha,F.vDescFicha from "
             		+ NOMBRE_TABLA+" as P join SMMAEFICHAS as F on F.iCodFicha in(19,20) and P.iCodModalidad=CASE WHEN(F.iCodTipoInst=1) then 2 else 1 end "
             		+ "where P.cCodEstablecimiento = '"+Ruc+"'";//+"' and P.vEstablecimientoProvincia like '"+Proveedor+"'";// Se comento ya que la valdicación ya no es necesaria y además afectaba la visualizacion de postores de fichas iniciales
        	 
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {          
                            
            	entidad= new TABLETPOSTORES();     
            	entidad.iCodConvocatoria = fila.getInt(0);
            	entidad.vConvocatoria= fila.getString(1);
            	entidad.iCodModalidad=fila.getInt(2);
            	entidad.vModalidad=fila.getString(3);
            	entidad.iCodComite =fila.getInt(4);
            	entidad.vComite=fila.getString(5);
            	entidad.iCodItem=fila.getInt(6);
            	entidad.vItem=fila.getString(7);
            	entidad.iCodPostor=fila.getInt(8);
                entidad.cCodProveedor=fila.getString(9);
                entidad.vNombreProveedor=fila.getString(10);
                entidad.cRUC=fila.getString(11);
                entidad.cCodEstablecimiento=fila.getString(12);
                entidad.vDireccionEstablecimiento=fila.getString(13);
                //entidad.vReferenciaEstablecimiento=fila.getString(5);
                entidad.iPuntajeTecnico=fila.getInt(14);
                entidad.iPuntajeEconomico=fila.getInt(15);
                entidad.dFechaInicio=fila.getString(16);
                entidad.dFechaFin=fila.getString(17);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(18),fila.getString(19));
                
            }
        }
        bd.close();   
        return entidad;
     }
     
     public static TABLETPROVEEDORES BuscarRucLiberacion(Context context, String Ruc, String Proveedor)
     {
    	TABLETPROVEEDORES entidad=null;
    	SQLite admin=new SQLite(context,null); 
       	SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	 String query="select distinct P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,"
               		+ "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,"
               		+ "P.vLatitudEstablecimiento,P.vLongitudEstablecimiento,"
               		+ "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
               		+ "P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
               		+ "P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha from "
             		+ NOMBRE_TABLA+" as P inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst "
             		+ "inner join CONTRATO as C on C.cCodProveedor=P.cCodProveedor inner join "
             		+ "DETALLELIBERACION as D on D.iCodContrato=C.iCodContrato and  "
             		+ "P.cCodProveedor=D.cCodProveedor and P.cCodEstablecimiento=D.cCodEstablecimiento "
             		+ "where P.cRUC like '"+Ruc+"' and P.vEstablecimientoProvincia like '"+Proveedor+"'";
        	 
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {          
                            
            	entidad= new TABLETPROVEEDORES();     
                entidad.cCodProveedor=fila.getString(0);
                entidad.vNombreProveedor=fila.getString(1);
                entidad.cCodEstablecimiento=fila.getString(2);
                entidad.iNro=fila.getInt(3);
                entidad.vDireccionEstablecimiento=fila.getString(4);
                entidad.vReferenciaEstablecimiento=fila.getString(5);
                entidad.vLatitudEstablecimiento=fila.getString(6);
                entidad.vLongitudEstablecimiento=fila.getString(7);
                entidad.vEstablecimientoDistrito=fila.getString(8);
                entidad.vEstablecimientoProvincia=fila.getString(9);
                entidad.vEstablecimientoDepartamento=fila.getString(10);
                entidad.iTipo=fila.getInt(11);
                entidad.cCodDepartamento=fila.getString(12);
                entidad.cCodProvincia=fila.getString(13);
                entidad.cCodUbigeo=fila.getString(14);
                entidad.cRUC=fila.getString(15);
                entidad.cMultiple=fila.getString(16);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(17),fila.getString(18));
                
            }
        }
        bd.close();   
        return entidad;
     }

     public static TABLETPROVEEDORES BuscarcCodProveedor(Context context, String cCodProveedor)
     {
      
    	TABLETPROVEEDORES entidad=null;
    	SQLite admin=new SQLite(context,null); 
       	SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	 String query="select P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,"
               		+ "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,"
               		+ "P.vLatitudEstablecimiento,P.vLongitudEstablecimiento,"
               		+ "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
               		+ "P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
               		+ "P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha from "
             		+ NOMBRE_TABLA+" as P inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst "
             		+ "where P.cCodProveedor like '"+cCodProveedor+"'";
        	 
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {          
                            
            	entidad= new TABLETPROVEEDORES();     
                entidad.cCodProveedor=fila.getString(0);
                entidad.vNombreProveedor=fila.getString(1);
                entidad.cCodEstablecimiento=fila.getString(2);
                entidad.iNro=fila.getInt(3);
                entidad.vDireccionEstablecimiento=fila.getString(4);
                entidad.vReferenciaEstablecimiento=fila.getString(5);
                entidad.vLatitudEstablecimiento=fila.getString(6);
                entidad.vLongitudEstablecimiento=fila.getString(7);
                entidad.vEstablecimientoDistrito=fila.getString(8);
                entidad.vEstablecimientoProvincia=fila.getString(9);
                entidad.vEstablecimientoDepartamento=fila.getString(10);
                entidad.iTipo=fila.getInt(11);
                entidad.cCodDepartamento=fila.getString(12);
                entidad.cCodProvincia=fila.getString(13);
                entidad.cCodUbigeo=fila.getString(14);
                entidad.cRUC=fila.getString(15);
                entidad.cMultiple=fila.getString(16);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(17),fila.getString(18));
                
            }
        }
        bd.close();   
        return entidad;
     }

     public static List<TABLETPOSTORES> ListarEstablecimientosFichaInicial(Context context, String cCodEstablecimiento, String TipoFIcha)//String cCodProveedor,String TipoFIcha)
     {
        List<TABLETPOSTORES> list=new ArrayList<TABLETPOSTORES>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	 String query="select P.iCodModalidad, P.vModalidad,GROUP_CONCAT(distinct P.iCodUnidad), GROUP_CONCAT(distinct P.vUnidad), "
         			+ "GROUP_CONCAT(distinct P.iCodComite),GROUP_CONCAT(distinct P.vComite),GROUP_CONCAT(distinct P.iCodItem),GROUP_CONCAT(distinct P.vItem),GROUP_CONCAT(distinct P.iCodPostor),"
         			+ "GROUP_CONCAT(distinct P.cCodProveedor),GROUP_CONCAT(distinct P.vNombreProveedor),GROUP_CONCAT(distinct P.cRuc),P.cCodEstablecimiento,"
             		+ "P.vDireccionEstablecimiento,"
         			+"  F.iCodFicha,F.vDescFicha from "
              		+ NOMBRE_TABLA+" as P join SMMAEFICHAS as F on F.iCodFicha in(19,20) and P.iCodModalidad=CASE WHEN(F.iCodTipoInst=1) then 2 else 1 end "
              		+ "where P.cCodEstablecimiento = '"+cCodEstablecimiento+"'"//+"' and P.vEstablecimientoProvincia like '"+Proveedor+"'";// Se comento ya que la valdicación ya no es necesaria y además afectaba la visualizacion de postores de fichas iniciales
        	 		+ " group by P.iCodModalidad,P.vModalidad"
        	 		+" , P.cCodEstablecimiento,P.vDireccionEstablecimiento, F.iCodFicha, F.vDescFicha";
         	                       
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {   

                	TABLETPOSTORES entidad= new TABLETPOSTORES();     
                	//entidad.iCodConvocatoria = fila.getInt(0);
                	//entidad.vConvocatoria= fila.getString(1);
                	entidad.iCodModalidad=fila.getInt(0);
                	entidad.vModalidad=fila.getString(1);
                	entidad.vCodUnidad =fila.getString(2);
                	entidad.vUnidad=fila.getString(3);
                	entidad.vCodComite =fila.getString(4);
                	entidad.vComite=fila.getString(5);
                	entidad.vCodItem=fila.getString(6);
                	entidad.vItem=fila.getString(7);
                	entidad.vCodPostor=fila.getString(8);
                    entidad.cCodProveedor=fila.getString(9);
                    entidad.vNombreProveedor=fila.getString(10);
                    entidad.cRUC=fila.getString(11);
                    entidad.cCodEstablecimiento=fila.getString(12);
                    entidad.vDireccionEstablecimiento=fila.getString(13);
                    //entidad.vReferenciaEstablecimiento=fila.getString(5);
                    //entidad.iPuntajeTecnico=fila.getInt(14);
                    //entidad.iPuntajeEconomico=fila.getInt(15);
                    //entidad.dFechaInicio=fila.getString(16);
                    //entidad.dFechaFin=fila.getString(17);
                    entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(14),fila.getString(15));
                    
               
                    list.add(entidad);
                       
                    fila.moveToNext();   
                }   
         }
        bd.close();   
        return list;
     }
     
     public static List<SMFICHAINICIALANEXO> ListarComitePostoresxEstablecimiento(Context context, String cCodEstablecimiento, int iCodFicha)
     {
        List<SMFICHAINICIALANEXO> list=new ArrayList<SMFICHAINICIALANEXO>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	 String query="select P.iCodModalidad, P.vModalidad, "
                 + "P.iCodComite,P.vComite,GROUP_CONCAT(distinct P.iCodItem),GROUP_CONCAT(distinct P.vItem),GROUP_CONCAT(distinct P.iCodPostor),"
                 +"P.vComponentes,  F.iCodFicha,F.vDescFicha from "
                 + NOMBRE_TABLA+" as P join SMMAEFICHAS as F on F.iCodFicha in(19,20) and P.iCodModalidad=CASE WHEN(F.iCodTipoInst=1) then 2 else 1 end "
                 + "where P.cCodEstablecimiento = '"+cCodEstablecimiento+"' and F.iCodFicha="+ iCodFicha //+"' and P.vEstablecimientoProvincia like '"+Proveedor+"'";// Se comento ya que la valdicación ya no es necesaria y además afectaba la visualizacion de postores de fichas iniciales
                 + " group by P.iCodModalidad,P.vModalidad, P.iCodComite,P.vComite,P.vComponentes"
                 +" , F.iCodFicha, F.vDescFicha";
         	                       
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {   

                	SMFICHAINICIALANEXO entidad= new SMFICHAINICIALANEXO();     
                	//entidad.iCodConvocatoria = fila.getInt(0);
                	//entidad.vConvocatoria= fila.getString(1);
                	entidad.iCodModalidad=fila.getInt(0);
                	//entidad.vModalidad=fila.getString(1);
                	entidad.iCodComite = fila.getInt(2);
                	entidad.vComite=fila.getString(3);
                	entidad.vCodItem=fila.getString(4);
                	entidad.vItem=fila.getString(5);
                	entidad.vCodPostor=fila.getString(6);
                	String vComponentes = fila.isNull(7)?"":fila.getString(7);
                	String[] arrayCompo=  vComponentes.split("\\s*,\\s*");
                	if(entidad.iCodModalidad==1)
                	{
	                	for(int j=0;j<arrayCompo.length;j++)
	                	{
	                		if(arrayCompo[j].equals("1"))
	                		{
	                			entidad.iCantSolido=0;
	                			entidad.iCantSolidoP=0;
	                		}
	                		else
	                			if(arrayCompo[j].equals("2"))
		                		{
	                				entidad.iCantBebible=0;
	                				entidad.iCantBebibleP=0;
		                		}
	                		
	                			else if(arrayCompo[j].equals("3"))
		                		{
	                				entidad.iCantGalleta=0;
	    		                    entidad.iCantGalletaP=0;
	                				
		                		}
	                			else if(arrayCompo[j].equals("4"))
	                			{
	                				entidad.iCantAcompanamiento=0;
	                				entidad.iCantAcompanamientoP=0;
	                			}
		                    
	                	}
                	}
                	else
                	{
                		entidad.iCantAlimento=0;
                		entidad.iCantAlimentoP=0;
                	}
               
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

