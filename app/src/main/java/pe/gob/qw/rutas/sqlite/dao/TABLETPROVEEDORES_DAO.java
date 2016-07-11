
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
import pe.gob.qw.rutas.entities.TABLETPROVEEDORES;
import pe.gob.qw.rutas.sqlite.SQLite;

public class TABLETPROVEEDORES_DAO
{
    private static String NOMBRE_TABLA="TABLETPROVEEDORES";

    public static int Agregar(Context context, TABLETPROVEEDORES entidad)
    {
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

                    String dir[] = json_data.getString("vDireccionEstablecimiento").split("/");
				 
		            registro.put("cCodDepartamento",json_data.getString("cCodDepartamento").trim());
		            registro.put("cCodEstablecimiento",json_data.getString("cCodEstablecimiento").trim());        
		            registro.put("cCodProveedor",json_data.getString("cCodProveedor").trim());        
		            registro.put("cCodProvincia",json_data.getString("cCodProvincia").trim());        
		            registro.put("cCodUbigeo",json_data.getString("cCodUbigeo").trim()); 
		            registro.put("cMultiple",json_data.getString("cMultiple").trim()); 
		            registro.put("cRuc",json_data.getString("cRuc").trim());        
		            registro.put("iNro",json_data.getInt("iNro"));        
		            registro.put("vDireccionEstablecimiento",json_data.getString("vDireccionEstablecimiento").trim());
                    //registro.put("vDireccionEstablecimiento",dir[dir.length-1]);
		            registro.put("vEstablecimientoDepartamento",json_data.getString("vEstablecimientoDepartamento").trim());   
		            registro.put("vEstablecimientoDistrito",json_data.getString("vEstablecimientoDistrito").trim());   
		            registro.put("vEstablecimientoProvincia",json_data.getString("vEstablecimientoProvincia").toUpperCase().trim()); 
		            registro.put("vLatitudEstablecimiento",json_data.getString("vLatitudEstablecimiento").trim());        
		            registro.put("vLongitudEstablecimiento",json_data.getString("vLongitudEstablecimiento").trim());        
		            //registro.put("vNombreProveedor",json_data.getString("vNombreProveedor").trim());
                    registro.put("vNombreProveedor",dir[0]);
                    registro.put("vReferenciaEstablecimiento",json_data.getString("vReferenciaEstablecimiento").trim());
		            registro.put("iTipo",json_data.getInt("vTipo"));
		            registro.put("vObservacion",json_data.getString("vObservacion"));
		            registro.put("vNombreDepartamento", json_data.getString("vDepartamento"));
		            registro.put("vNombreProvincia", json_data.getString("vProvincia"));
		            registro.put("vNombreDistrito", json_data.getString("vDistrito"));
		            registro.put("iCodTipoRacion",json_data.getString("iCodTipoRacion"));
		          
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

    public static List<TABLETPROVEEDORES> Listar(Context context, String cCodUbigeo)
     {
        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
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

                	TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();     
                    entidad.cCodProveedor=fila.getString(0);
                    entidad.cCodUbigeo=fila.getString(1);
                    entidad.cRUC=fila.getString(2);
                    entidad.vNombreProveedor=fila.getString(3);
                    entidad.vEstablecimientoDepartamento=fila.getString(4);
                    entidad.vEstablecimientoDistrito=fila.getString(5);
                    entidad.vEstablecimientoProvincia=fila.getString(6);
                    entidad.vDireccionEstablecimiento=fila.getString(7);
                    entidad.vReferenciaEstablecimiento=fila.getString(8);
                    entidad.cCodEstablecimiento=fila.getString(9);
               
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

    public static TABLETPROVEEDORES BuscarRuc2(Context context, String Ruc, String Proveedor)
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
                    + "where P.cCodEstablecimiento = '"+Ruc+"'";//+"' and P.vEstablecimientoProvincia like '"+Proveedor+"'";// Se comento ya que la valdicación ya no es necesaria y además afectaba la visualizacion de postores de fichas iniciales

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

    public static List<TABLETPROVEEDORES> BuscarRuc(Context context, String Ruc, String Proveedor)
     {
         TABLETPROVEEDORES entidad=null;
         SQLite admin=new SQLite(context,null);
         SQLiteDatabase bd=admin.getWritableDatabase();
         List<TABLETPROVEEDORES> lstProveedores = new ArrayList<TABLETPROVEEDORES>();
         if(bd!=null)
         {
             String query="select P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,"
                     + "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,"
                     + "P.vLatitudEstablecimiento,P.vLongitudEstablecimiento,"
                     + "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
                     + "P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
                     + "P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha from "
                     + NOMBRE_TABLA+" as P inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst "
                     + "where P.cCodEstablecimiento like '"+Ruc+"'";//+"' and P.vEstablecimientoProvincia like '"+Proveedor+"'";// Se comento ya que la valdicacián ya no es necesaria y además afectaba la visualizacion de postores de fichas iniciales

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
                 lstProveedores.add(entidad);

             }
         }
         bd.close();
         return lstProveedores;
     }

    public static List<TABLETPROVEEDORES> BuscarProveedoresxEstablecimiento(Context context, String cCodEstablecimiento, String esProveedor, String cCategoria)
    {
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        List<TABLETPROVEEDORES> lstProveedores = new ArrayList<TABLETPROVEEDORES>();
        if(bd!=null)
        {
            String query="select distinct P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,"
                    + "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento, P.vObservacion,"
                    + "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
                    + "P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
                    + "P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple, P.vNombreProvincia, P.vNombreDistrito,F.iCodFicha,F.vDescFicha from "
                    + NOMBRE_TABLA + " as P inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst "
                    + "where P.cCodEstablecimiento="+cCodEstablecimiento+" and P.vEstablecimientoProvincia = '" + esProveedor + "' and f.cCategoria like '%" + cCategoria + "%'";

            Cursor fila=bd.rawQuery(query,null);

            int numRows = fila.getCount();
            fila.moveToFirst();

            for(int i=0;i<numRows;i++)
            {

                TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();
                entidad.cCodProveedor=fila.getString(0);
                entidad.vNombreProveedor=fila.getString(1);
                entidad.cCodEstablecimiento=fila.getString(2);
                entidad.iNro=fila.getInt(3);
                String dir[] = fila.getString(4).split("/");
                entidad.vDireccionEstablecimiento=dir[dir.length-1];
                entidad.vReferenciaEstablecimiento=fila.getString(5);
                entidad.vObservacion=fila.getString(6);
                //entidad.vLatitudEstablecimiento=fila.getString(6);
                //entidad.vLongitudEstablecimiento=fila.getString(7);
                entidad.vEstablecimientoDistrito=fila.getString(7);
                entidad.vEstablecimientoProvincia=fila.getString(8);
                entidad.vEstablecimientoDepartamento=fila.getString(9);

                entidad.iTipo=fila.getInt(10);
                entidad.cCodDepartamento=fila.getString(11);
                entidad.cCodProvincia=fila.getString(12);
                entidad.cCodUbigeo=fila.getString(13);
                entidad.cRUC=fila.getString(14);
                entidad.cMultiple=fila.getString(15);
                entidad.vNombreProvincia=fila.getString(16);
                entidad.vNombreDistrito=fila.getString(17);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(18),fila.getString(19));
                lstProveedores.add(entidad);

                fila.moveToNext();
            }

        }
        bd.close();
        return lstProveedores;
    }


    public static List<TABLETPROVEEDORES> BuscarProveedoresxEstablecimientov2(Context context, String cCodEstablecimiento, String esProveedor, String cCategoria)
    {
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        List<TABLETPROVEEDORES> lstProveedores = new ArrayList<TABLETPROVEEDORES>();
        if(bd!=null)
        {
            String query="select distinct P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,"
                    + "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento, P.vObservacion,"
                    + "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
                    + "P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
                    + "P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple, P.vNombreProvincia, P.vNombreDistrito,F.iCodFicha,F.vDescFicha from "
                    + NOMBRE_TABLA + " as P inner join SMMAEFICHAS as F on 1=F.iCodTipoInst "
                    + "where P.cCodEstablecimiento="+cCodEstablecimiento+" and P.vEstablecimientoProvincia = '" + esProveedor + "' and f.cCategoria like '%" + cCategoria + "%'";

            Cursor fila=bd.rawQuery(query,null);

            int numRows = fila.getCount();
            fila.moveToFirst();

            for(int i=0;i<numRows;i++)
            {

                TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();
                entidad.cCodProveedor=fila.getString(0);
                entidad.vNombreProveedor=fila.getString(1);
                entidad.cCodEstablecimiento=fila.getString(2);
                entidad.iNro=fila.getInt(3);
                String dir[] = fila.getString(4).split("/");
                entidad.vDireccionEstablecimiento=dir[dir.length-1];
                entidad.vReferenciaEstablecimiento=fila.getString(5);
                entidad.vObservacion=fila.getString(6);
                //entidad.vLatitudEstablecimiento=fila.getString(6);
                //entidad.vLongitudEstablecimiento=fila.getString(7);
                entidad.vEstablecimientoDistrito=fila.getString(7);
                entidad.vEstablecimientoProvincia=fila.getString(8);
                entidad.vEstablecimientoDepartamento=fila.getString(9);
                entidad.iTipo=fila.getInt(10);
                entidad.cCodDepartamento=fila.getString(11);
                entidad.cCodProvincia=fila.getString(12);
                entidad.cCodUbigeo=fila.getString(13);
                entidad.cRUC=fila.getString(14);
                entidad.cMultiple=fila.getString(15);
                entidad.vNombreProvincia=fila.getString(16);
                entidad.vNombreDistrito=fila.getString(17);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(18),fila.getString(19));
                lstProveedores.add(entidad);

                fila.moveToNext();
            }

        }
        bd.close();
        return lstProveedores;
    }



    public static List<TABLETPROVEEDORES> BuscarRucRacionesSeguimiento(Context context, String Ruc, String Proveedor)
    {
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        List<TABLETPROVEEDORES> lstProveedores = new ArrayList<TABLETPROVEEDORES>();
        if(bd!=null)
        {
            String query="select distinct P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,"
                    + "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,"
                    + "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
                    + "P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
                    + "P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha from "
                    + NOMBRE_TABLA+" as P inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst JOIN CONTRATO con on con.cCodProveedor = P.cCODproveedor and con.iCodModalidad=1 "
                    //+ "where P.cRUC like '"+Ruc+"' and F.iCodTipoInst = 2 and f.cCategoria like '%D%' ";
                    + "where P.cCodEstablecimiento like '"+Ruc+"' and F.iCodTipoInst = 2 and f.cCategoria like '%D%' ";

            Cursor fila=bd.rawQuery(query,null);

            int numRows = fila.getCount();
            fila.moveToFirst();

            for(int i=0;i<numRows;i++)
            {

                TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();
                entidad.cCodProveedor=fila.getString(0);
                entidad.vNombreProveedor=fila.getString(1);
                entidad.cCodEstablecimiento=fila.getString(2);
                entidad.iNro=fila.getInt(3);
                entidad.vDireccionEstablecimiento=fila.getString(4);
                entidad.vReferenciaEstablecimiento=fila.getString(5);
                //entidad.vLatitudEstablecimiento=fila.getString(6);
                //entidad.vLongitudEstablecimiento=fila.getString(7);
                entidad.vEstablecimientoDistrito=fila.getString(6);
                entidad.vEstablecimientoProvincia=fila.getString(7);
                entidad.vEstablecimientoDepartamento=fila.getString(8);
                entidad.iTipo=fila.getInt(9);
                entidad.cCodDepartamento=fila.getString(10);
                entidad.cCodProvincia=fila.getString(11);
                entidad.cCodUbigeo=fila.getString(12);
                entidad.cRUC=fila.getString(13);
                entidad.cMultiple=fila.getString(14);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(15),fila.getString(16));
                lstProveedores.add(entidad);

                fila.moveToNext();


            }

        }
        bd.close();
        return lstProveedores;
    }
     
    public static List<TABLETPROVEEDORES> BuscarRucRacionesLiberacion(Context context, String Ruc, String Proveedor)
     {
    	 List<TABLETPROVEEDORES> lstProveedores = new ArrayList<TABLETPROVEEDORES>();
    	SQLite admin=new SQLite(context,null); 
       	SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	 String query="select distinct P.cCodProveedor,P.vNombreProveedor,"
        			 +" P.cCodEstablecimiento, P.iNro, "
        			 +" (SELECT sp.vDireccionEstablecimiento from " + NOMBRE_TABLA+ " as sp where sp.cCodProveedor= p.cCodProveedor limit 1) as vDireccionEstablecimiento,"
               		//+ "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,"
               		//+ "P.vLatitudEstablecimiento,P.vLongitudEstablecimiento,"
               		//+ "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
               		//+ "P.vEstablecimientoDepartamento,P.cCodDepartamento," 
               		+ "P.iTipo,"
               		//+ "P.cCodProvincia,P.cCodUbigeo,"
               		+ "P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha from "
             		+ NOMBRE_TABLA+" as P inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst "
             		+ " inner join ACTACOLEGIOSITEM a on P.cCodProveedor=a.cCodProveedor and P.cCodEstablecimiento=a.cCodEstablecimiento and a.cEstado='1'"
             		//+ " where P.cRUC like '"+Ruc+"' and F.iCodTipoInst = 2 and f.cCategoria like '%L%'";
                     + " where P.cCodEstablecimiento like '"+Ruc+"' and F.iCodTipoInst = 2 and f.cCategoria like '%L%'";
        	 
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();
                            
            for(int i=0;i<numRows;i++)
            {

            	TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();     
                entidad.cCodProveedor=fila.getString(0);
                entidad.vNombreProveedor=fila.getString(1);
                entidad.cCodEstablecimiento=fila.getString(2);
                entidad.iNro=fila.getInt(3);
                entidad.vDireccionEstablecimiento=fila.getString(4);
                //entidad.vReferenciaEstablecimiento=fila.getString(5);
                //entidad.vLatitudEstablecimiento=fila.getString(6);
                //entidad.vLongitudEstablecimiento=fila.getString(7);
                //entidad.vEstablecimientoDistrito=fila.getString(6);
                //entidad.vEstablecimientoProvincia=fila.getString(7);
               // entidad.vEstablecimientoDepartamento=fila.getString(8);
                entidad.iTipo=fila.getInt(5);
                //entidad.cCodDepartamento=fila.getString(10);
                //entidad.cCodProvincia=fila.getString(11);
                //entidad.cCodUbigeo=fila.getString(12);
                entidad.cRUC=fila.getString(6);
                entidad.cMultiple=fila.getString(7);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(8),fila.getString(9));
                lstProveedores.add(entidad);
                
                fila.moveToNext();
                
            }
        }
        bd.close();   
        return lstProveedores;
     }
     
    public static List<TABLETPROVEEDORES> BuscarRucRacionesLiberacionxCodProveedor(Context context, String cCodProveedor)
     {
    	 List<TABLETPROVEEDORES> lstProveedores = new ArrayList<TABLETPROVEEDORES>();
    	SQLite admin=new SQLite(context,null); 
       	SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	 String query="select distinct P.cCodProveedor,P.vNombreProveedor,"
        			 //+"P.cCodEstablecimiento,P.iNro,"
        			 +" (SELECT sp.vDireccionEstablecimiento from " + NOMBRE_TABLA+ " as sp where sp.cCodProveedor= p.cCodProveedor limit 1) as vDireccionEstablecimiento,"
               		//+ "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,"
               		//+ "P.vLatitudEstablecimiento,P.vLongitudEstablecimiento,"
               		//+ "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
               		//+ "P.vEstablecimientoDepartamento,P.cCodDepartamento," 
               		+ "P.iTipo,"
               		//+ "P.cCodProvincia,P.cCodUbigeo,"
               		+ "P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha from "
             		+ NOMBRE_TABLA+" as P inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst "
             		+ " inner join ACTACOLEGIOSITEM a on P.cCodProveedor=a.cCodProveedor and P.cCodEstablecimiento=a.cCodEstablecimiento and a.cEstado='1'"
             		+ " where P.cCodProveedor = '"+cCodProveedor+"' and F.iCodTipoInst = 2 and f.cCategoria like '%L%'";//+"' and P.vEstablecimientoProvincia like '"+Proveedor+"'";// Se comento ya que la valdicación ya no es necesaria y además afectaba la visualizacion de postores de fichas iniciales
        	 
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();
                            
            for(int i=0;i<numRows;i++)
            {

            	TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();     
                entidad.cCodProveedor=fila.getString(0);
                entidad.vNombreProveedor=fila.getString(1);
                //entidad.cCodEstablecimiento=fila.getString(2);
                //entidad.iNro=fila.getInt(3);
                entidad.vDireccionEstablecimiento=fila.getString(2);
                //entidad.vReferenciaEstablecimiento=fila.getString(5);
                //entidad.vLatitudEstablecimiento=fila.getString(6);
                //entidad.vLongitudEstablecimiento=fila.getString(7);
                //entidad.vEstablecimientoDistrito=fila.getString(6);
                //entidad.vEstablecimientoProvincia=fila.getString(7);
               // entidad.vEstablecimientoDepartamento=fila.getString(8);
                entidad.iTipo=fila.getInt(3);
                //entidad.cCodDepartamento=fila.getString(10);
                //entidad.cCodProvincia=fila.getString(11);
                //entidad.cCodUbigeo=fila.getString(12);
                entidad.cRUC=fila.getString(4);
                entidad.cMultiple=fila.getString(5);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(6),fila.getString(7));
                lstProveedores.add(entidad);
                
                fila.moveToNext();
                
            }
        }
        bd.close();   
        return lstProveedores;
     }
     
    public static TABLETPROVEEDORES BuscarRucSupervision(Context context, String Ruc, String Proveedor)
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
             		+ "where P.cRUC like '"+Ruc+"' and P.vEstablecimientoProvincia like '"+Proveedor+"' and F.iCodFicha = 6 ";
        	 
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

    public static List<TABLETPROVEEDORES> BuscarRucLiberacion(Context context, String Ruc, String Proveedor)
     {
    	TABLETPROVEEDORES entidad=null;
    	SQLite admin=new SQLite(context,null); 
       	SQLiteDatabase bd=admin.getWritableDatabase();
         List<TABLETPROVEEDORES> lstProveedores = new ArrayList<TABLETPROVEEDORES>();
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
             		//+ "where P.cRUC like '"+Ruc+"' and P.vEstablecimientoProvincia like '"+Proveedor+"'";
                     + "where P.cCodEstablecimiento like '"+Ruc+"' and P.vEstablecimientoProvincia like '"+Proveedor+"'";
        	 
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
                lstProveedores.add(entidad);
                
            }
        }
        bd.close();   
        return lstProveedores;
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

    public static List<TABLETPROVEEDORES> ListarEstablcimientosFichaInicial(Context context, String cRuc, String TipoFIcha)//String cCodProveedor,String TipoFIcha)
     {
        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
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
              		+ NOMBRE_TABLA+" as P LEFT OUTER join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst LEFT OUTER JOIN "
              		+ "SMFICHASGRABADAS as S on P.cCodEstablecimiento=S.cCodEstablecimiento and P.vLatitudEstablecimiento = S.vItem "//Se agregó and P.vLatitudEstablecimiento = S.vItem para que se mostraran las inspecciones del mismo establecimeinto pero para las distintas UT aun asi ya se haya calificado para una de ellas 31/03/2015 10:51
              		+ "where S.iCodFicha is null and  P.cRuc like '"+cRuc+"' and F.cCategoria like '"+TipoFIcha+"'"; //P.cCodProveedor like '"+cCodProveedor+"' and F.cCategoria like '"+TipoFIcha+"'";
                               
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {   

                	TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();     
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
                    
               
                    list.add(entidad);
                       
                    fila.moveToNext();   
                }   
         }
        bd.close();   
        return list;
     }

    //AGREGADO: MÉTODO NUEVO
    public static List<TABLETPROVEEDORES> ListarProveedoresxEstablecimiento(Context context, String cCodEstablecimiento, String TipoFIcha)
    {

        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            String query = "select " +
                                "P.iTipo, " +
                                "P.vObservacion, " +
                                "P.vReferenciaEstablecimiento, " +
                                "P.vEstablecimientoDepartamento, " +
                                "P.vEstablecimientoDistrito, " +
                                "P.vDireccionEstablecimiento, " +
                                "GROUP_CONCAT(distinct P.cCodProveedor), " +
                                "GROUP_CONCAT(distinct P.vNombreProveedor), " +
                                "GROUP_CONCAT(distinct P.vLongitudEstablecimiento), " +
                                "P.cCodEstablecimiento, " +
                                "F.iCodFicha, " +
                                "F.vDescFicha " +
                            "from " +
                                NOMBRE_TABLA + " as P join SMMAEFICHAS as F on F.iCodFicha in (21,22) and P.iTipo = CASE WHEN (F.iCodTipoInst=1) then 1 else 2 end " +
                            "where " +
                                "P.cCodEstablecimiento = '" + cCodEstablecimiento + "' " +
                            "group by " +
                                "P.iTipo, " +
                                "P.vObservacion, " +
                                "P.vReferenciaEstablecimiento, " +
                                "P.vEstablecimientoDepartamento, " +
                                "P.vEstablecimientoDistrito, " +
                                "P.cCodEstablecimiento, " +
                                "F.iCodFicha, " +
                                "F.vDescFicha";

            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();
                    entidad.iTipo=fila.getInt(0);
                    entidad.vObservacion=fila.getString(1);
                    entidad.vReferenciaEstablecimiento=fila.getString(2);
                    entidad.vEstablecimientoDepartamento=fila.getString(3);
                    entidad.vEstablecimientoDistrito=fila.getString(4);
                    entidad.vDireccionEstablecimiento=fila.getString(5);
                    entidad.cCodProveedor=fila.getString(6);
                    entidad.vNombreProveedor=fila.getString(7);
                    entidad.vLongitudEstablecimiento=fila.getString(8);
                    entidad.cCodEstablecimiento=fila.getString(9);
                    entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(10),fila.getString(11));

                    list.add(entidad);

                    fila.moveToNext();
            }
        }

        bd.close();
        return list;
    }

    //AGREGADO POR CAMBIO DE REQUERIMIENTOS
    public static List<TABLETPROVEEDORES> ListarItemsxProveedorEnEstablecimiento(Context context, String cCodProveedor, String cCodEstablecimiento, String cCategoria)
    {

        String cCategoriaString;

        if (cCategoria=="S") cCategoriaString = "21,22";
        else if (cCategoria=="T") cCategoriaString = "23";
        else cCategoriaString = "24";

        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            /*
            String query = "select " +
                                "P.iTipo, " +
                                "P.vEstablecimientoDistrito, " +
                                "P.vDireccionEstablecimiento, " +
                                "P.cCodProveedor, " +
                                "P.vNombreProveedor, " +
                                "GROUP_CONCAT(distinct P.vLatitudEstablecimiento), " +
                                "GROUP_CONCAT(distinct P.vLongitudEstablecimiento), " +
                                "P.cCodEstablecimiento, " +
                                "F.iCodFicha, " +
                                "F.vDescFicha " +
                                "from " + NOMBRE_TABLA +
                                " as P join SMMAEFICHAS as F on F.iCodFicha in ('" + cCategoriaString + "')  and F.iCodTipoInst = CASE WHEN (P.iTipo=2) then 1 else 1 end " +
                            "where P.cCodProveedor = '" + cCodProveedor + "' and P.cCodEstablecimiento = '" + cCodEstablecimiento + "'" +
                            "group by " +
                                "P.iTipo, " +
                                "P.vEstablecimientoDistrito, " +
                                "P.cCodEstablecimiento, " +
                                "F.iCodFicha, " +
                                "F.vDescFicha";
                                */
            String query= "SELECT P.iTipo, P.vEstablecimientoDistrito, P.vDireccionEstablecimiento,  P.cCodProveedor,  P.vNombreProveedor, " +
                    "GROUP_CONCAT(DISTINCT P.vLatitudEstablecimiento), GROUP_CONCAT(DISTINCT P.vLongitudEstablecimiento), " +
                    "P.cCodEstablecimiento, F.iCodFicha, F.vDescFicha " +
                    "FROM TABLETPROVEEDORES AS P JOIN SMMAEFICHAS AS F ON F.iCodFicha IN (" + cCategoriaString + ") AND F.iCodTipoInst = CASE WHEN (P.iTipo=2) THEN 1 ELSE 1 END " +
                    "JOIN CONTRATO AS C ON P.cCodProveedor=C.cCodProveedor AND P.vLatitudEstablecimiento=C.iCodItem " +
                    "WHERE P.cCodProveedor = '" + cCodProveedor + "' AND P.cCodEstablecimiento = '" + cCodEstablecimiento + "'" +
                    "GROUP BY P.iTipo, P.vEstablecimientoDistrito, P.cCodEstablecimiento, F.iCodFicha, F.vDescFicha";

            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();
                entidad.iTipo=fila.getInt(0);
                entidad.vEstablecimientoDistrito=fila.getString(1);
                entidad.vDireccionEstablecimiento=fila.getString(2);
                entidad.cCodProveedor=fila.getString(3);
                entidad.vNombreProveedor=fila.getString(4);
                entidad.vLatitudEstablecimiento=fila.getString(5);
                entidad.vLongitudEstablecimiento=fila.getString(6);
                entidad.cCodEstablecimiento=fila.getString(7);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(8),fila.getString(9));

                list.add(entidad);

                fila.moveToNext();
            }
        }

        bd.close();
        return list;
    }

    //AGREGADO POR CAMBIO DE REQUERIMIENTOS
    public static List<TABLETPROVEEDORES> ListarItemsxProveedorEnEstablecimientoSeg(Context context, String cCodProveedor, String cCodEstablecimiento, String cCategoria)
    {

        String cCategoriaString;

        if (cCategoria=="S") cCategoriaString = "21,22";
        else if (cCategoria=="T") cCategoriaString = "23";
        else cCategoriaString = "24";

        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            /*String query = "select " +
                    "P.iTipo, " +
                    "P.vEstablecimientoDistrito, " +
                    "P.vDireccionEstablecimiento, " +
                    "P.cCodProveedor, " +
                    "P.vNombreProveedor, " +
                    "GROUP_CONCAT(distinct P.vLatitudEstablecimiento), " +
                    "GROUP_CONCAT(distinct P.vLongitudEstablecimiento), " +
                    "P.cCodEstablecimiento, " +
                    "F.iCodFicha, " +
                    "F.vDescFicha " +
                    "from " + NOMBRE_TABLA +
                    " as P join SMMAEFICHAS as F on F.iCodFicha in (" + cCategoriaString + ") and P.iTipo = CASE WHEN (F.iCodTipoInst=2) then 2 else 1 end"  +
                    " where P.cCodProveedor = '" + cCodProveedor + "' and P.cCodEstablecimiento = '" + cCodEstablecimiento + "'" +
                    "group by " +
                    "P.iTipo, " +
                    "P.vEstablecimientoDistrito, " +
                    "P.cCodEstablecimiento, " +
                    "F.iCodFicha, " +
                    "F.vDescFicha";*/

            String query="SELECT P.iTipo, P.vEstablecimientoDistrito, P.vDireccionEstablecimiento, P.cCodProveedor, P.vNombreProveedor, " +
                    "GROUP_CONCAT(DISTINCT P.vLatitudEstablecimiento), GROUP_CONCAT(DISTINCT P.vLongitudEstablecimiento), " +
                    "P.cCodEstablecimiento, F.iCodFicha, F.vDescFicha " +
                    "FROM " + NOMBRE_TABLA + " AS P JOIN SMMAEFICHAS AS F ON F.iCodFicha IN (" + cCategoriaString + ") AND P.iTipo = CASE WHEN (F.iCodTipoInst=2) THEN 2 ELSE 1 END " +
                    "JOIN CONTRATO AS C ON P.cCodProveedor=C.cCodProveedor AND P.vLatitudEstablecimiento=C.iCodItem " +
                    "WHERE P.cCodProveedor = '" + cCodProveedor + "' AND P.cCodEstablecimiento = '" + cCodEstablecimiento + "'" +
                    "GROUP BY P.iTipo, P.vEstablecimientoDistrito, P.cCodEstablecimiento, F.iCodFicha, F.vDescFicha";

            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {
                TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();
                entidad.iTipo=fila.getInt(0);
                entidad.vEstablecimientoDistrito=fila.getString(1);
                entidad.vDireccionEstablecimiento=fila.getString(2);
                entidad.cCodProveedor=fila.getString(3);
                entidad.vNombreProveedor=fila.getString(4);
                entidad.vLatitudEstablecimiento=fila.getString(5);
                entidad.vLongitudEstablecimiento=fila.getString(6);
                entidad.cCodEstablecimiento=fila.getString(7);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(8),fila.getString(9));

                list.add(entidad);

                fila.moveToNext();
            }
        }

        bd.close();
        return list;
    }

     public static boolean ActualizarEstablecimientoFichaSeguimientoRaciones(Context context, TABLETPROVEEDORES entidad)
     {
    	
      	   SQLite admin=new SQLite(context,null);
            SQLiteDatabase bd = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("vNombreRacion",entidad.vNombreRacion);
            registro.put("vCantidadSupervisada",entidad.vCantidadSupervisada);
            registro.put("vObservacion",entidad.vObservacion);
          
            int cant = bd.update(NOMBRE_TABLA, registro, "cCodProveedor='"+entidad.cCodProveedor+"' and cCodEstablecimiento='"+ entidad.cCodEstablecimiento+"' and CAST(vLatitudEstablecimiento as integer) in ("+entidad.vLatitudEstablecimiento +") AND iTipo=2 ", null);
            
            
            bd.close();
            if(cant==1)
                return true;
            else
                return false;
      
     }
     
     public static List<TABLETPROVEEDORES> ListarEstablecimientosFichaSeguimientoRaciones(Context context, String cCodProveedor, String TipoFIcha)
     {
        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
            	String query ="select P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,IFNULL(GROUP_CONCAT(distinct c.iCodItem),''),IFNULL(GROUP_CONCAT(distinct C.vNombreItem),'') as vLongitudEstablecimiento,"
        			+"P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
        			 +"P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha, P.vObservacion, IFNULL(GROUP_CONCAT( distinct c.vComite),'') as vComite, "
        			+ "ifnull(SUM(c.iCantidadProgramada),0) as CantidadProgramada, IFNULL(vNombreRacion,'') as vNombreRacion,IFNULL(vCantidadSupervisada,'') as vCantidadSupervisada,"
        			
        			+" IFNULL(GROUP_CONCAT(C.iCodItem),'') as vItems, IFNULL(GROUP_CONCAT( distinct c.iCodComite),'') as vIdComites, "
        			+" IFNULL(P.vNombreDepartamento,'') as vNombreDepartamento," 
        			+" IFNULL(P.vNombreProvincia,'') as vNombreProvincia,IFNULL(P.vNombreDistrito,'') as vNombreDistrito from taBLETPROVEEDORES"
        			+" as P inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst"
        			+ " LEFT join ESTABLECIMIENTOSITEMSATENDIDOS I on P.cCodProveedor = I.iCodProveedor  and P.vLatitudEstablecimiento = I.iCodItem AND i.cCodEstablecimiento=p.cCodEstablecimiento "
        			+ " LEFT JOIN CONTRATO C on I.iCodProveedor=c.cCodProveedor and I.iCodItem=c.iCodItem and I.iCodComite=c.iCodComite"
        			//+ " where P.cCodProveedor like '"+cCodProveedor +"' and F.iCodTipoInst=2 and P.iTipo = P.iTipo and F.cCategoria like '"+TipoFIcha+"' and IFNULL(c.iCodModalidad,1)=1 "
        			+ " where P.cCodProveedor = '"+cCodProveedor +"' and F.iCodTipoInst=2 and P.iTipo = P.iTipo and F.cCategoria like '"+TipoFIcha+"' and IFNULL(c.iCodModalidad,1)=1 "
        			+ " group by P.cCodEstablecimiento;";
                               
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {   

                	TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();     
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
                    entidad.vObservacion=fila.getString(19);
                    entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(17),fila.getString(18));
                    entidad.vComites= fila.getString(20);;
                    entidad.vCantidadProgramada = String.valueOf(fila.getInt(21));
                    entidad.vNombreRacion=fila.getString(22);
                    entidad.vCantidadSupervisada =fila.getString(23);
                    entidad.vItems=fila.getString(24);
                    entidad.vIdComites=fila.getString(25);
               
                    entidad.vNombreDepartamento=fila.getString(26);
                    entidad.vNombreProvincia =fila.getString(27);
                    entidad.vNombreDistrito = fila.getString(28);
                    
                    list.add(entidad);
                       
                    fila.moveToNext();   
                }   
         }
        bd.close();   
        return list;
     }

     public static List<TABLETPROVEEDORES> ListarEstablecimientosFichaLiberacionRaciones(Context context, String cCodProveedor, String TipoFIcha)
     {
        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
            	String query ="select P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,IFNULL(GROUP_CONCAT(distinct c.iCodItem),''),IFNULL(GROUP_CONCAT(distinct C.vNombreItem),'') as vLongitudEstablecimiento,"
        			+"P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
        			 +"P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha, P.vObservacion, IFNULL(GROUP_CONCAT( distinct c.vComite),'') as vComite, "
        			+ "ifnull(SUM(c.iCantidadProgramada),0) as CantidadProgramada, IFNULL(vNombreRacion,'') as vNombreRacion,IFNULL(vCantidadSupervisada,'') as vCantidadSupervisada,"
        			
        			+" IFNULL(GROUP_CONCAT(C.iCodItem),'') as vItems, IFNULL(GROUP_CONCAT( distinct c.iCodComite),'') as vIdComites, "
        			+" IFNULL(P.vNombreDepartamento,'') as vNombreDepartamento," 
        			+" IFNULL(P.vNombreProvincia,'') as vNombreProvincia,IFNULL(P.vNombreDistrito,'') as vNombreDistrito, iCodTipoRacion from taBLETPROVEEDORES"
        			+" as P inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst"
        			+" JOIN ACTACOLEGIOSITEM A on P.cCodProveedor=A.cCodProveedor and P.cCodEstablecimiento= A.cCodEstablecimiento and A.cEstado='1' "
        			+ " LEFT JOIN CONTRATO C on P.cCodProveedor=c.cCodProveedor and c.iCodItem= p.vLatitudEstablecimiento"
        			+ " where P.cCodProveedor like '"+cCodProveedor +"' and F.iCodTipoInst=2 and P.iTipo = P.iTipo and F.cCategoria like '"+TipoFIcha+"' and IFNULL(c.iCodModalidad,1)=1 "
        			+ " group by P.cCodEstablecimiento;";
                               
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {   

                	TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();     
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
                    entidad.vObservacion=fila.getString(19);
                    entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(17),fila.getString(18));
                    entidad.vComites= fila.getString(20);;
                    entidad.vCantidadProgramada = String.valueOf(fila.getInt(21));
                    entidad.vNombreRacion=fila.getString(22);
                    entidad.vCantidadSupervisada =fila.getString(23);
                    entidad.vItems=fila.getString(24);
                    entidad.vIdComites=fila.getString(25);
               
                    entidad.vNombreDepartamento=fila.getString(26);
                    entidad.vNombreProvincia =fila.getString(27);
                    entidad.vNombreDistrito = fila.getString(28);
                    entidad.iTipoRacion=fila.getInt(29);
                    
                    list.add(entidad);
                       
                    fila.moveToNext();   
                }   
         }
        bd.close();   
        return list;
     }

    public static List<TABLETPROVEEDORES> ListarEstablecimientosFichaLiberacionRacionesv2(Context context, String cCodProveedor, String TipoFIcha, String cCodEstablecimiento)
    {
        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            String query ="select P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,IFNULL(GROUP_CONCAT(distinct c.iCodItem),''),IFNULL(GROUP_CONCAT(distinct C.vNombreItem),'') as vLongitudEstablecimiento,"
                    +"P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
                    +"P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha, P.vObservacion, IFNULL(GROUP_CONCAT( distinct c.vComite),'') as vComite, "
                    + "ifnull(SUM(c.iCantidadProgramada),0) as CantidadProgramada, IFNULL(vNombreRacion,'') as vNombreRacion,IFNULL(vCantidadSupervisada,'') as vCantidadSupervisada,"

                    +" IFNULL(GROUP_CONCAT(C.iCodItem),'') as vItems, IFNULL(GROUP_CONCAT( distinct c.iCodComite),'') as vIdComites, "
                    +" IFNULL(P.vNombreDepartamento,'') as vNombreDepartamento,"
                    +" IFNULL(P.vNombreProvincia,'') as vNombreProvincia,IFNULL(P.vNombreDistrito,'') as vNombreDistrito, iCodTipoRacion from taBLETPROVEEDORES"
                    +" as P inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst"
                    +" JOIN ACTACOLEGIOSITEM A on P.cCodProveedor=A.cCodProveedor and P.cCodEstablecimiento= A.cCodEstablecimiento and A.cEstado='1' "
                    + " LEFT JOIN CONTRATO C on P.cCodProveedor=c.cCodProveedor and c.iCodItem= p.vLatitudEstablecimiento"
                    + " where P.cCodProveedor like '"+cCodProveedor +"' and F.iCodTipoInst=2 and P.iTipo = P.iTipo and F.cCategoria like '"+TipoFIcha+"' and IFNULL(c.iCodModalidad,1)=1 and P.cCodEstablecimiento = '" + cCodEstablecimiento + "'"
                    + " group by P.cCodEstablecimiento";

            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; ++i)
            {

                TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();
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
                entidad.vObservacion=fila.getString(19);
                entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(17),fila.getString(18));
                entidad.vComites= fila.getString(20);;
                entidad.vCantidadProgramada = String.valueOf(fila.getInt(21));
                entidad.vNombreRacion=fila.getString(22);
                entidad.vCantidadSupervisada =fila.getString(23);
                entidad.vItems=fila.getString(24);
                entidad.vIdComites=fila.getString(25);

                entidad.vNombreDepartamento=fila.getString(26);
                entidad.vNombreProvincia =fila.getString(27);
                entidad.vNombreDistrito = fila.getString(28);
                entidad.iTipoRacion=fila.getInt(29);

                list.add(entidad);

                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }

     public static List<TABLETPROVEEDORES> ListarEstablcimientosFichaOcurrencias(Context context, String cCodProveedor, String TipoFIcha, String cRuc)
     {
        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
         if(bd!=null)
         {
        	 SMMAEFICHAS OBJSMMAEFICHAS = new SMMAEFICHAS();
        	 
        	 String query="select iCodFicha,vDescFicha from SMMAEFICHAS where cCategoria like '"+TipoFIcha+"'";
         	 
             fila=bd.rawQuery(query,null);
             if (fila.moveToFirst())
             {     
            	 OBJSMMAEFICHAS.iCodFicha= fila.getInt(0);
            	 OBJSMMAEFICHAS.vDescFicha=fila.getString(1);
            	 
            	 query="select P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,"
                 		+ "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,"
                 		+ "P.vLatitudEstablecimiento,GROUP_CONCAT(P.vLongitudEstablecimiento) as vLongitudEstablecimiento, "
                 		+ "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
                 		+ "P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
                 		+ "P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple, P.vObservacion from "
               		+ NOMBRE_TABLA+" AS P "
               		+ " inner join proVEEDORESITEMS I on P.cCodProveedor = I.cCodProveedor   and P.vLatitudEstablecimiento = I.iCodItem "
       				//+ "where P.cCodProveedor like '"+cCodProveedor+"'"
       				+ "where P.cRUC like '"+cRuc+"' and P.iTipo = I.iTipoFicha group by P.cCodEstablecimiento";
                                
	             fila=bd.rawQuery(query,null);
	             int numRows = fila.getCount();   
	             fila.moveToFirst();   
	                 for (int i = 0; i < numRows; ++i) 
	                 {   
	
	                 	TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();     
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
	                     entidad.vObservacion=fila.getString(17);
	                     entidad.objSMMAEFICHAS=OBJSMMAEFICHAS;
	                     list.add(entidad);
	                        
	                     fila.moveToNext();   
	                 }    
                 
             }
        	
         }
        bd.close();   
        return list;
     }
     
     public static List<TABLETPROVEEDORES> ListarEstablcimientosFichaLiberacion(Context context, String cCodProveedor, String TipoFIcha)
     {
        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
        Cursor fila;
         if(bd!=null)
         {
        	 SMMAEFICHAS OBJSMMAEFICHAS = new SMMAEFICHAS();
        	 
        	 String query="select iCodFicha,vDescFicha from SMMAEFICHAS where cCategoria like '"+TipoFIcha+"'";
         	 
             fila=bd.rawQuery(query,null);
             if (fila.moveToFirst())
             {     
            	 OBJSMMAEFICHAS.iCodFicha= fila.getInt(0);
            	 OBJSMMAEFICHAS.vDescFicha=fila.getString(1);
            	 
            	 query="select P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,"
                 		+ "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,"
                 		+ "P.vLatitudEstablecimiento,P.vLongitudEstablecimiento,"
                 		+ "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
                 		+ "P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
                 		+ "P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple from "
               		+ NOMBRE_TABLA+" AS P where P.cCodProveedor like '"+cCodProveedor+"'";
                                
	             fila=bd.rawQuery(query,null);
	             int numRows = fila.getCount();   
	             fila.moveToFirst();   
	                 for (int i = 0; i < numRows; ++i) 
	                 {   
	
	                 	TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();     
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
	                     entidad.objSMMAEFICHAS=OBJSMMAEFICHAS;
	                     list.add(entidad);
	                        
	                     fila.moveToNext();   
	                 }    
                 
             }
        	
         }
        bd.close();   
        return list;
     }
     
     public static List<TABLETPROVEEDORES> ListarEstablcimientos(Context context, String cCodProveedor, String TipoFIcha)
     {
        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	String query="select P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,"
                		+ "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,"
                		+ "P.vLatitudEstablecimiento, GROUP_CONCAT(P.vLongitudEstablecimiento) as vLongitudEstablecimiento, "
                		+ "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
                		+ "P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
                		+ "P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha, P.vObservacion from "
              		+ NOMBRE_TABLA+" as P "
              		+ " inner join proVEEDORESITEMS I on P.cCodProveedor = I.cCodProveedor  and P.vLatitudEstablecimiento = I.iCodItem "
          			+ "inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst "//LEFT OUTER JOIN "
              		//+ " SMFICHASGRABADAS as S on P.cCodEstablecimiento=S.cCodEstablecimiento and P.vLatitudEstablecimiento = S.vItem "
              		+ "where P.cCodProveedor like '"+cCodProveedor+"' and P.iTipo = I.iTipoFicha and F.cCategoria like '"+TipoFIcha+"'"
              		+" group by P.cCodEstablecimiento";
        	
                               
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i) 
                {   

                	TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();     
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
                    entidad.vObservacion = fila.getString(19);
                    entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(17),fila.getString(18));
                    
               
                    list.add(entidad);
                       
                    fila.moveToNext();   
                }   
         }
        bd.close();   
        return list;
     }

     public static List<TABLETPROVEEDORES> ListarEstablecimientosTransporte(Context context, String cCodProveedor, String TipoFIcha, String cRuc)
     {
        List<TABLETPROVEEDORES> list=new ArrayList<TABLETPROVEEDORES>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	String query="select P.cCodProveedor,P.vNombreProveedor,P.cCodEstablecimiento,P.iNro,"
                		+ "P.vDireccionEstablecimiento,P.vReferenciaEstablecimiento,"
                		+ "P.vLatitudEstablecimiento, GROUP_CONCAT(P.vLongitudEstablecimiento) as vLongitudEstablecimiento, "
                		+ "P.vEstablecimientoDistrito,P.vEstablecimientoProvincia,"
                		+ "P.vEstablecimientoDepartamento,P.iTipo,P.cCodDepartamento,"
                		+ "P.cCodProvincia,P.cCodUbigeo,P.cRUC,P.cMultiple,F.iCodFicha,F.vDescFicha, P.vObservacion  from "
              		+ NOMBRE_TABLA+" as P "
              		+ " inner join proVEEDORESITEMS I on P.cCodProveedor = I.cCodProveedor  and P.vLatitudEstablecimiento = I.iCodItem "
          			+ "inner join SMMAEFICHAS as F on P.iTipo=F.iCodTipoInst "//LEFT OUTER JOIN "
              		//+ " SMFICHASGRABADAS as S on P.cCodEstablecimiento=S.cCodEstablecimiento and P.vLatitudEstablecimiento = S.vItem "
              		//+ "where P.cCodProveedor like '"+cCodProveedor+"' and P.iTipo = I.iTipoFicha and F.cCategoria like '"+TipoFIcha+"'"
              		+ "where P.cRUC like '"+cRuc+"' and P.iTipo = I.iTipoFicha and F.cCategoria like '"+TipoFIcha+"'"
              		+" group by P.cCodEstablecimiento";
        	
                               
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; ++i)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
                {   

                	TABLETPROVEEDORES entidad= new TABLETPROVEEDORES();     
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
                    //if(fila.getString(19) != null)
                	entidad.vObservacion = fila.getString(19);
          
                    entidad.objSMMAEFICHAS=new SMMAEFICHAS(fila.getInt(17),fila.getString(18));
                    
               
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