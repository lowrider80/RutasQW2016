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

import pe.gob.qw.rutas.entities.ITEM;
import pe.gob.qw.rutas.entities.ITEMSATENDIDOS;
import pe.gob.qw.rutas.sqlite.SQLite;

public class ITEMSATENDIDOS_DAO {

private static String NOMBRE_TABLA="ESTABLECIMIENTOSITEMSATENDIDOS";

public static int Agregar(Context context, ITEMSATENDIDOS entidad) {
	// TODO Auto-generated method stub
	int id = 0;
	
    SQLite admin=new SQLite(context,null);
    SQLiteDatabase bd=admin.getWritableDatabase();
    ContentValues registro=new ContentValues();
    
    registro.put("cCodEstablecimiento",entidad.cCodEstablecimiento);        
    registro.put("iCodProveedor",entidad.iCodProveedor);        
    registro.put("iCodFicha",entidad.iCodFicha);        
    registro.put("iCodItem",entidad.iCodItem); 
    registro.put("iCodComite",entidad.iCodComite);
    
    
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
		            registro.put("cCodProveedor",json_data.getString("cCodProveedor").trim());        
		            registro.put("iCodFicha",json_data.getString("cCodFicha").trim());        
		            registro.put("iCodItem",json_data.getInt("iCodItem"));;  
		          
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

	
	
     public static List<ITEMSATENDIDOS> ListarItemsAtendidos(Context context, String cCodEstablecimiento, String cCodProveedor, int cCodFicha)
     {
        List<ITEMSATENDIDOS> list=new ArrayList<ITEMSATENDIDOS>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	 //MODIFICAR MAS ADELANTE EL TIPOFICHA POR DEFINIR, HASTA EL MOMENTO SE TOMA 1 COMO RACIONES.
              String query="select distinct P.cCodProveedor,P.iCodComite,P.iCodItem,"
              		+ "IFNULL(IA.iCodFicha,'"+cCodFicha+"') as iCodFicha, P.vNombreItem ,P.vComite,IFNULL(TP.cCodEstablecimiento,'"+cCodEstablecimiento
              		+"') as cCodEstablecimiento,IFNULL(IA.cCodEstablecimiento,'0') as bItemAtendido  from CONTRATO as P"
              		+ " left join TABLETPROVEEDORES TP on CAST(P.iCodItem as varchar) = TP.vLatitudEstablecimiento and CAST(P.cCodProveedor as VARCHAR)=TP.cCodProveedor " // and IA.cCodEstablecimiento=TP.cCodEstablecimiento"
              		+" LEFT JOIN " +NOMBRE_TABLA+" AS IA"
            		+ " on IA.iCodItem=P.iCodItem and IA.iCodProveedor =P.cCodProveedor and IA.iCodComite = P.iCodComite and IA.cCodEstablecimiento=TP.cCodEstablecimiento "
              		
              		+ " where P.cCodProveedor ="+cCodProveedor+" and IFNULL(IA.cCodEstablecimiento,'"+cCodEstablecimiento+"')='"+cCodEstablecimiento
              		+ "' and P.iCodModalidad=1 and IFNULL(TP.iTipo,2)=2 and ifnull(IA.iCodFicha,"+cCodFicha +")="+cCodFicha 
              		+" AND IFNULL(IA.cCodEstablecimiento,'"+cCodEstablecimiento+"')=IFNULL(TP.cCodEstablecimiento,'"
              		+ cCodEstablecimiento+"')";
                    
                    
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; i++) 
                {   

                	ITEMSATENDIDOS entidad= new ITEMSATENDIDOS();     
                    entidad.iCodProveedor =fila.getInt(fila.getColumnIndex("cCodProveedor"));
                    entidad.cCodEstablecimiento=fila.getString(fila.getColumnIndex("cCodEstablecimiento"));
                    entidad.iCodFicha=fila.getInt(fila.getColumnIndex("iCodFicha"));
                    entidad.iCodItem=fila.getInt(fila.getColumnIndex("iCodItem"));
                    entidad.iCodComite=fila.getInt(fila.getColumnIndex("iCodComite"));
                    entidad.vComite =fila.getString(fila.getColumnIndex("vComite"));
                    entidad.vItem=fila.getString(fila.getColumnIndex("vNombreItem"));
                    entidad.itemAtendido=fila.getString(fila.getColumnIndex("bItemAtendido")).equals("0")?false:true; 
                    list.add(entidad);
                    fila.moveToNext();   
                }   
         }
        bd.close();   
        return list;
     }

    public static List<ITEM> ListarItems(Context context, String cCodEstablecimiento, String cCodProveedor, int cCodFicha)
    {
        List<ITEM> list=new ArrayList<ITEM>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            //MODIFICAR MAS ADELANTE EL TIPOFICHA POR DEFINIR, HASTA EL MOMENTO SE TOMA 1 COMO RACIONES.
            String query="select distinct  p.vNombreItem  ,p.iCodItem from tabletproveedores t "
                    +" inner join  proveedoresitems p on t.ccodproveedor=p.ccodproveedor  where t.ccodestablecimiento= " +cCodEstablecimiento ;
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; i++)
            {   ITEM entidad= new ITEM();
                entidad.iCodItem=fila.getInt(fila.getColumnIndex("iCodItem"));
                entidad.vNombreItem= fila.getString(fila.getColumnIndex("vNombreItem"));
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }

    public static List<ITEM> ListarItemsV2(Context context, String cCodEstablecimiento, String cCodProveedor, int cCodFicha)
    {
        List<ITEM> list=new ArrayList<ITEM>();
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
            //MODIFICAR MAS ADELANTE EL TIPOFICHA POR DEFINIR, HASTA EL MOMENTO SE TOMA 1 COMO RACIONES.
            String query="select  distinct P.vLatitudEstablecimiento,P.vLongitudEstablecimiento from TABLETPROVEEDORES as P "+
            " join SMMAEFICHAS as F on F.iCodFicha in ('"+cCodFicha+ "')  and F.iCodTipoInst = CASE WHEN (P.iTipo=2) then 1 else 1 end "+

            " where P.cCodProveedor = '"+cCodProveedor+ "' and P.cCodEstablecimiento = '"+cCodEstablecimiento+"' ";

            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();
            fila.moveToFirst();
            for (int i = 0; i < numRows; i++)
            {   ITEM entidad= new ITEM();
                entidad.iCodItem= Integer.parseInt(fila.getString(fila.getColumnIndex("vLatitudEstablecimiento")));
                entidad.vNombreItem= fila.getString(fila.getColumnIndex("vLongitudEstablecimiento"));
                list.add(entidad);
                fila.moveToNext();
            }
        }
        bd.close();
        return list;
    }



     
     public static List<ITEMSATENDIDOS> ListarItemsNoAtendidos(Context context, String cCodEstablecimiento, String cRUC , String cCodProveedor, int cCodFicha)
     {
        List<ITEMSATENDIDOS> list=new ArrayList<ITEMSATENDIDOS>();
        SQLite admin=new SQLite(context,null); 
        SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
        	 //MODIFICAR MAS ADELANTE EL TIPOFICHA POR DEFINIR, HASTA EL MOMENTO SE TOMA 1 COMO RACIONES.
              String query="select distinct P.cCodProveedor,P.iCodComite,P.iCodItem,"
            		  + cCodFicha + " as iCodFicha,"
            		  +" P.vNombreItem ,P.vComite,IFNULL(TP.cCodEstablecimiento,'"+cCodEstablecimiento+"') as cCodEstablecimiento, "
            		 + "0 as bItemAtendido"  
            		 + " from CONTRATO as P left join TABLETPROVEEDORES TP on CAST(P.iCodItem as varchar) = TP.vLatitudEstablecimiento and CAST(P.cCodProveedor as VARCHAR)=TP.cCodProveedor " 
            		 + " where P.cCodProveedor ='"+cCodProveedor 
            		 + "'  and P.iCodModalidad=1 and IFNULL(TP.iTipo,2)=2"
            		 + " and (select IA.cCodEstablecimiento from ESTABLECIMIENTOSITEMSATENDIDOS IA where IA.cCodEstablecimiento=TP.cCodEstablecimiento and" 
            		 + " IA.iCodFicha=17 and IA.iCodProveedor=TP.cCodProveedor and  IA.iCodItem= TP.vLatitudEstablecimiento) is null";
                    
                    
            Cursor fila=bd.rawQuery(query,null);
            int numRows = fila.getCount();   
            fila.moveToFirst();   
                for (int i = 0; i < numRows; i++) 
                {   

                	ITEMSATENDIDOS entidad= new ITEMSATENDIDOS();     
                    entidad.iCodProveedor =fila.getInt(fila.getColumnIndex("cCodProveedor"));
                    entidad.cCodEstablecimiento=fila.getString(fila.getColumnIndex("cCodEstablecimiento"));
                    entidad.iCodFicha=fila.getInt(fila.getColumnIndex("iCodFicha"));
                    entidad.iCodItem=fila.getInt(fila.getColumnIndex("iCodItem"));
                    entidad.iCodComite=fila.getInt(fila.getColumnIndex("iCodComite"));
                    entidad.vComite =fila.getString(fila.getColumnIndex("vComite"));
                    entidad.vItem=fila.getString(fila.getColumnIndex("vNombreItem"));
                    entidad.itemAtendido=fila.getString(fila.getColumnIndex("bItemAtendido")).equals("0")?false:true; 
                    list.add(entidad);
                       
                    fila.moveToNext();   
                }   
         }
        bd.close();   
        return list;
     }
     
     public static final String ObtenerItemsResumen(List<ITEMSATENDIDOS> listItems){
    	 String concat="";
    	 try{
    		
    		 if(listItems!=null)
    		 if(listItems.size()>0){
    		
    			 for(int i=0;i<listItems.size();i++){
    				if(i>0){
    					concat=concat+","+listItems.get(i).vItem;
    					
    				}
    				else
    				{
    					concat=listItems.get(i).vItem;
    				}
    				 
    			 }
    			 return concat;
    			 
    		 }
    		 else
    		 {
    			 return "";
    		 }
    		 else
    		 {
    			 return "";
    		 }
    	 
    	 }
    	 catch(Exception ex){
    		 
    	 }
		return concat;
    	 
     }
     
     
     public static void BorrarItemsAtendidos(Context context, String cCodEstablecimiento, String cCodProveedor, String cCodFicha){
      	SQLite admin=new SQLite(context,null); 
          	SQLiteDatabase bd=admin.getWritableDatabase();
            if(bd!=null)
            {
               String query="delete from "+NOMBRE_TABLA +" where cCodProveedor='"+cCodProveedor
            		   		+"' and cCodEstablecimiento='"+cCodEstablecimiento+"'";
           
               bd.execSQL(query);
               
           }
           bd.close();   
       }
     
     public static void BorrarItemAtendido(Context context, String cCodEstablecimiento, String cCodProveedor, Integer iCodItem, Integer iCodComite){
       	SQLite admin=new SQLite(context,null); 
           	SQLiteDatabase bd=admin.getWritableDatabase();
             if(bd!=null)
             {
                String query="delete from "+NOMBRE_TABLA +" where iCodProveedor='"+cCodProveedor
             		   		+"' and cCodEstablecimiento='"+cCodEstablecimiento+"' and iCodItem="+iCodItem +" and iCodComite="+iCodComite;
            
                bd.execSQL(query);
                
            }
            bd.close();   
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
