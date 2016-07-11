package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.COMPONENTELIBERACION;
import pe.gob.qw.rutas.entities.RECORDCARDITEM;
import pe.gob.qw.rutas.entities.TIPOCOMPONENTERACION;
import pe.gob.qw.rutas.sqlite.SQLite;

public class COMPONENTELIBERACION_DAO {

	private static String NOMBRE_TABLA="COMPONENTELIBERACION";
	
	 public static  int Agregar(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
	    {
		 int numRows=0;
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	      
	        if(bd!=null)
	        {
	        	String query="select iCodBcoPreg,iCodFicha,ordenllamada,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
		             		+ "iTipoControl,iOrden,vTabla,vCampo,iTipoColumna from DBQUESTIONARY where iCodFicha="+iCodFicha; 

		           fila=bd.rawQuery(query,null);
		           numRows = fila.getCount();   
		           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   
	                   ContentValues registro=new ContentValues();
	                   registro.put("iCodBcoPreg",fila.getString(0));
	                   registro.put("iCodFicha",fila.getInt(1));
	                   registro.put("ordenllamada",fila.getString(2));
	                   registro.put("iCodTipoSeccion",fila.getInt(3));
	                   registro.put("cSeccion",fila.getString(4));
	                   registro.put("vDetalleSeccion",fila.getString(5));
	                   registro.put("iValor",fila.getInt(6));
	                   registro.put("iTipoControl",fila.getInt(7));
	                   registro.put("iOrden",i);
	                   registro.put("vCampo",fila.getString(10));
	                   registro.put("vTabla",fila.getString(9));
	                   registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
	                   registro.put("cCodEstablecimiento",cCodEstablecimiento);
	                   registro.put("cCodProveedor",cCodProveedor);
	                   registro.put("vResultado","");
	                   registro.put("iIndexCombo",0);
	                   registro.put("idFichasGrabadas",idFichasGrabadas);
	                   registro.put("bcheck",0);
	                   registro.put("bactivo",0);
	                   int id = (int) bd.insert(NOMBRE_TABLA, null, registro);  
	                   
	                   fila.moveToNext();   
	               }    
	        }
	       bd.close();   
	       return numRows;
	       
	    }
	 
	
	
	
	 
	 public static  int AgregarComponenteRaciones(Context context, int iCodFicha, String cCodProveedor, String cTipoRacion )
	    {
		 int numRows=0;
		 int componenteLiberacionPadre=0 ;
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       String[] rowsHeader=null;
	       int indexHeader=0;
	        if(bd!=null)
	        {
	        	String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
		             		+ "iTipoControl,iOrden, bVisible from SMFORMATO where iCodTipoSeccion=8 and iTipoControl<>0 and iCodFicha="+iCodFicha +" order by iOrden"; 

		           fila=bd.rawQuery(query,null);
		           numRows = fila.getCount();   
		           fila.moveToFirst();
	               for (int i = 0; i < numRows; ++i) 
	               {   
	            	  
	                   ContentValues registro=new ContentValues();
	                   registro.put("iCodFormato",fila.getString(0));
	                   registro.put("iCodFicha",fila.getInt(1));
	                   registro.put("iCodTipoSeccion",fila.getInt(2));
	                   registro.put("cSeccion",fila.getString(3));
	                   registro.put("vDetalleSeccion",fila.getString(4));
	                   registro.put("iValor",0);
	                   registro.put("iTipoControl",fila.getInt(6));
	                   registro.put("iOrden",i);
	                   registro.put("bVisible", fila.getInt(8));
	                   registro.put("cTipoRacion",cTipoRacion);
	                   registro.put("cCodProveedor",cCodProveedor);
	                   registro.put("vResultado","");
	                   registro.put("iIndexCombo",0);
	                   registro.put("bcheck",0);
	                   registro.put("bactivo",1);
	                   
	                 
	                	if(i!=0){
	                		   registro.put("iComponenteLiberacionPadre",componenteLiberacionPadre);
	                	   }
	                	   //registro.put("iTipoColumna", j);
	                	int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
	                	if(i==0)
	                		componenteLiberacionPadre=id;
	                
	                   
	                   fila.moveToNext();   
	               }    
	        }
	       bd.close();   
	       return componenteLiberacionPadre;
	       
	    }

	 public static int CantidadCamposVaciosComponente(Context context, int iCodFicha, int[] arraySeccion, int iComponenteLiberacion){
		 int numRows=0;
		 int camposVacios=0 ;
		 String productos="";
		 String presentacion="";
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       String stringArraySecciones="";
		 	for(int i=0;i<arraySeccion.length;i++){
		 		if(i==0){
		 			stringArraySecciones= String.valueOf(arraySeccion[i]);
		 		}else{
		 			stringArraySecciones=stringArraySecciones+","+ String.valueOf(arraySeccion[i]);
		 		}
		 	}
	       int indexHeader=0;
	        if(bd!=null)
	        {
	        	String query="select COUNT(*) as cantidad FROM COMPONENTELIBERACION "
		             		+ "c where c.iCodTipoSeccion in ("+stringArraySecciones+") and c.iTipoControl<>0 and c.iCodFicha="+iCodFicha 
		             		+" and  ifnull(c.iComponenteLiberacionPadre,c.iComponenteLiberacion)="+iComponenteLiberacion
		             		+" and ("
		             		+" (c.iTipoControl=4 and ( c.iValor=0))"
		             		//+" or (c.iTipoControl=8 and  1!=(SELECT IFNULL(SUM(CAST(cc.vTipoComponente as decimal)),0) from COMPONENTERACION cc where cc.iCodFichaTecnicaRacion in (c.vResultado) ))"
		             		+" or (c.iTipoControl=9 and (LTRIM(c.vResultado='')))"
		             		+" or (c.iTipoControl=17 and (LTRIM(c.iValor=0)))"
		             		+" or (c.iTipoControl=2 and LTRIM(c.vResultado=''))"
		             		+" or (c.iTipoControl=6 and LTRIM(c.vResultado=''))"
		             		+" or (c.iTipoControl=13 and LTRIM(c.vResultado=''))"
		             		+" or (c.iTipoControl=11 and LTRIM(c.vResultado=''))"
		             		+" or (c.iTipoControl=10 and LTRIM(c.vResultado=''))"
		             		+" or (c.iTipoControl=12 and ((LTRIM(c.vResultado='') or c.iValor=0) and c.bcheck=0) )"
		             		+" or (c.iTipoControl=14 and (LTRIM(c.vResultado='') and c.bcheck=0))"
		             		+" or (c.iTipoControl=15 and (LTRIM(c.vResultado='' and c.iValor<>3) or( iValor=2 and vObservacion='' )or(c.iValor=0)))"
		             		+" or (c.iTipoControl=16 and (LTRIM(c.vResultado='') or c.iValor=0)))"; 

		           fila=bd.rawQuery(query,null);
		           numRows = fila.getCount();   
		           if(fila.moveToFirst())
		        	   camposVacios=fila.getInt(0);

		           
	               String query2="select c.vResultado FROM COMPONENTELIBERACION "
		             		+ "c where c.iCodTipoSeccion in ("+stringArraySecciones+") and c.iTipoControl<>0 and c.iCodFicha="+iCodFicha 
		             		+" and  c.iComponenteLiberacionPadre="+iComponenteLiberacion
		             		+" and (c.iTipoControl=8)";
	               fila=bd.rawQuery(query2,null);
		           numRows = fila.getCount();  
		           if(fila.moveToFirst()){
		        	   productos = fila.getString(0);
		           }
		           
		           

	               query2="select count(*) FROM COMPONENTELIBERACION "
		             		+ "c where c.iCodTipoSeccion in ("+stringArraySecciones+") and c.iTipoControl<>0 and c.iCodFicha="+iCodFicha 
		             		+" and  c.iComponenteLiberacionPadre="+iComponenteLiberacion
		             		+" and (c.iTipoControl=8 and (0.9 >(SELECT IFNULL(SUM(cast(cc.vTipoComponente as decimal)),0) FROM COMPONENTERACION cc where cc.iCodFichaTecnicaRacion in ("+productos+ ")) and c.vObservacion=''))";
	               fila=bd.rawQuery(query2,null);
		           numRows = fila.getCount();  
		           if(fila.moveToFirst()){
		        	   camposVacios+= fila.getInt(0);
		           }
		           
		           
		           query2="select c.vObservacion FROM COMPONENTELIBERACION "
		             		+ "c where c.iCodTipoSeccion in ("+stringArraySecciones+") and c.iTipoControl<>0 and c.iCodFicha="+iCodFicha 
		             		+" and  c.iComponenteLiberacionPadre="+iComponenteLiberacion
		             		+" and (c.iTipoControl=9)";
	               fila=bd.rawQuery(query2,null);
		           numRows = fila.getCount();  
		           if(fila.moveToFirst()){
		        	   presentacion = fila.getString(0);
		           }
		           
		           

	               query2="select count(*) FROM COMPONENTELIBERACION "
		             		+ "c where c.iCodTipoSeccion in ("+stringArraySecciones+") and c.iTipoControl<>0 and c.iCodFicha="+iCodFicha 
		             		+" and  c.iComponenteLiberacionPadre="+iComponenteLiberacion
		             		+" and (c.iTipoControl=9 and 0 in ("+ presentacion +"))";
	               fila=bd.rawQuery(query2,null);
		           numRows = fila.getCount();  
		           if(fila.moveToFirst()){
		        	   camposVacios+= fila.getInt(0);
		           }
		           
		           
		           
	        }
	       bd.close();   
	       return camposVacios;
	 }
	 
	 public static List<Integer> ComponentesTablet(Context context, int iCodFicha, int[] arraySeccion)
	 {
		 int numRows;
	     SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
		 Cursor fila;
	       String stringArraySecciones="";
	       List<Integer> listComponentes=new ArrayList<Integer>();
		 	for(int i=0;i<arraySeccion.length;i++){
		 		if(i==0){
		 			stringArraySecciones= String.valueOf(arraySeccion[i]);
		 		}else{
		 			stringArraySecciones=stringArraySecciones+","+ String.valueOf(arraySeccion[i]);
		 		}
		 	}
	       int indexHeader=0;
	        if(bd!=null)
	        {
	        	String query="select iComponenteLiberacion  FROM COMPONENTELIBERACION "
	             		+ "c where c.iCodTipoSeccion in ("+stringArraySecciones+") and c.iTipoControl<>0 and c.iComponenteLiberacionPadre is null";
	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();
	           fila.moveToFirst();
	           for (int i = 0; i < numRows; ++i) 
               {
	        	   listComponentes.add(fila.getInt(0));
	        	   fila.moveToNext();
               }
	        }
	        bd.close();   
		    return listComponentes;
	 }
	 
	 public static String VerificarCantidadRequerida(Context context, double iRecordCardItem){
		 int numRows=0;
		 double vCantidadRequerida=0 ;
		 double vCantidadStock=0;
		 int iRecordCardItemProducto=0;
		 String Mensaje="";
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	      
	       int indexHeader=0;
	        if(bd!=null)
	        {
	        	String query="select vResultado,iRecordCardItemReference FROM RECORDCARDITEM"
		             		+ " where iCodTipoSeccion=10 and iTipoControl<>0 and iRecordCardItemPadre="+iRecordCardItem +" and vDetalleSeccion like '%requerida%'";

		       fila=bd.rawQuery(query,null);
		          
		       if(fila.moveToFirst())
		       {
		    	   vCantidadRequerida=fila.getString(0).trim().equals("")?0: Double.parseDouble(fila.getString(0));
		    	   iRecordCardItemProducto= fila.getInt(1);
		       }
		       String query2="select vResultado FROM RECORDCARDITEM"
	             		+ " where iCodTipoSeccion=8 and iTipoControl<>0 and iRecordCardItemPadre="+iRecordCardItemProducto +" and vDetalleSeccion like '%CANTIDAD%'";
		       
		       fila=bd.rawQuery(query2,null);
		       if(fila.moveToFirst())
		       {
		    	   vCantidadStock=fila.getString(0).trim().equals("")?0: Double.parseDouble(fila.getString(0));
		       }
		       
		       if(vCantidadRequerida==0){
		    	   Mensaje="Se le informa que la cantidad requerida que está ingresando es 0";
		       }
		       else
		       {
		    	   if(vCantidadStock<vCantidadRequerida){
		    		   Mensaje="Se le informa que la cantidad requerida ingresada es mayor a la especificada en la cantidad stock";
		    	   }
		       }
		       
		       
	        }
	       bd.close();   
	       return Mensaje;
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
	 
	 public static  int GetSizeInSeccion(Context context, int idFichasGrabadas, int[] arraySecciones)
	    {
		 String stringArraySecciones="";
		 	for(int i=0;i<arraySecciones.length;i++){
		 		if(i==0){
		 			stringArraySecciones= String.valueOf(arraySecciones[i]);
		 		}else{
		 			stringArraySecciones=stringArraySecciones+","+ String.valueOf(arraySecciones[i]);
		 		}
		 	}
		 int numRows=0;
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	      
	        if(bd!=null)
	        {
	        	String query="select  count(*) from "
	        			+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas +" and iCodTiposeccion in ("+stringArraySecciones+")";	    
	        	
	        	
	            fila=bd.rawQuery(query,null);
		            if (fila.moveToFirst())
		            {     
		            	numRows=fila.getInt(0);
		            }
	           
	        	
	        
	        }
	       bd.close();   
	       return numRows;
	       
	    }
	 
	 public static  int GetValida(Context context, int idFichasGrabadas)
	    {
		 int numRows=-1;
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	      
	        if(bd!=null)
	        {
	        	String query="select  count(*) from "
	        			+ NOMBRE_TABLA+" where bcheck=1 and iOrden < 20 and bactivo=1 and idFichasGrabadas="+idFichasGrabadas;	        	
	        	
	            fila=bd.rawQuery(query,null);
		        if (fila.moveToFirst())
		        {   
		        	if(fila.getInt(0)==0)
		        	{
		        		 
			        	query="select  count(*) from "
			        			+ NOMBRE_TABLA+" where (iTipoControl=4 or iTipoControl=8 or iTipoControl=9) and idFichasGrabadas="+idFichasGrabadas;	        	
			        	
			            fila=bd.rawQuery(query,null);
				        if (fila.moveToFirst())
				        {     
				        	int total=fila.getInt(0);
				        	
				        	query="select  count(*) from "
				        			+ NOMBRE_TABLA+" where (iTipoControl=4 or iTipoControl=8 or iTipoControl=9) and (iIndexCombo=1 or iIndexCombo=3)   and idFichasGrabadas="+idFichasGrabadas;	        	
				        	
				            fila=bd.rawQuery(query,null);
					        if (fila.moveToFirst())
					        {     
					        	numRows=total-fila.getInt(0);
					        	
					        	
					        	query="select  count(*) from "
					        			+ NOMBRE_TABLA+" where (iTipoControl=4 or iTipoControl=8 or iTipoControl=9 or iTipoControl = 6  ) and (iIndexCombo=2 and vResultado <> '')    and idFichasGrabadas="+idFichasGrabadas;	        	
					        	
					            fila=bd.rawQuery(query,null);
						        if (fila.moveToFirst())
						        {     
						        	numRows=numRows-fila.getInt(0);
						            	
						        }
					            	
					        }
				            	
				        }
				        else
				        	 numRows=-1;
		        	}	
		        }
	        }
	       bd.close();   
	       return numRows;
	       
	    }
	 
	 public static  int GetValidaCausales(Context context, int idFichasGrabadas)
	    {
		 int numRows=-1;
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	      
	        if(bd!=null)
	        {
	        	String query="select  count(*) from "
	        			+ NOMBRE_TABLA+" where bcheck=1 and bactivo=1 and iCodTipoSeccion in (5,6) and idFichasGrabadas="+idFichasGrabadas;	        	
	        	
	            fila=bd.rawQuery(query,null);
		        if (fila.moveToFirst())
		        {   
		        	
		        	numRows= fila.getInt(0);	
		        }
	        }
	       bd.close();
	       if(numRows>0)
	    	   numRows=-1;
	       return numRows;
	       
	    }
	 
	 public static String GetValidaVerificacion(Context context, int idFichasGrabadas, int iCodFicha)
	    {
		 String secciones="";
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	      
	        if(bd!=null)
	        {
	        	String leftSideQ=" (select  IFNULL((SELECT s.idFichasGrabadas from SECCIONFICHAGRABADA s where s.idFichasgrabadas="+idFichasGrabadas
	        			+"  and s.iCodFicha="+iCodFicha +" and s.iCodTipoSeccion=";
	        	String middleSideQ = " and s.bNoAplicaSeccion=1),count(*)) from "
	        			+ NOMBRE_TABLA+" r where  r.bactivo=1 and r.iCodTipoSeccion in (";
	        	String rightSideQ=") and r.idFichasGrabadas="+idFichasGrabadas +")";
	        			
	        	String query="SELECT"
	        			+leftSideQ + "8"+ middleSideQ+"8"+rightSideQ+" || ',' || "
	        			+leftSideQ+ "9"+ middleSideQ+"9" +rightSideQ+" || ',' || "
	        			+leftSideQ+ "10"+ middleSideQ+"10"+rightSideQ+" || ',' || "
	        			+leftSideQ+ "11"+ middleSideQ+"11"+rightSideQ+" || ',' || "
	        			+leftSideQ+ "12"+ middleSideQ+"12"+rightSideQ;
	            fila=bd.rawQuery(query,null);
		        if (fila.moveToFirst())
		        {   
		        		
		        		secciones= fila.getString(0);
			     
		        }
	        }
	       bd.close();   
	       return secciones;
	       
	    }
	 
	 public static  int GetTotalPreguntas(Context context, int idFichasGrabadas)
	    {
		 int numRows=-1;
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	      
	        if(bd!=null)
	        {
	        	String query="select  count(*) from "
			        			+ NOMBRE_TABLA+" where (iTipoControl=4 or iTipoControl=8 or iTipoControl=9) and idFichasGrabadas="+idFichasGrabadas;       	
			        	
			            fila=bd.rawQuery(query,null);
				        if (fila.moveToFirst())
				        {     
				        	numRows=fila.getInt(0);
				        }
				        
		  }

		          
	       bd.close();   
	      
	       return numRows;
	       
	    }
	 
	 
	 public static RECORDCARDITEM ObtenerCabeceraSeccionVerificacion(Context context, int idFichasGrabadas, int iCodFicha, int iCodSeccion ){
	 
		 RECORDCARDITEM item=new RECORDCARDITEM();
		 SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select p.iCodFormato,p.iCodFicha,p.iCodTipoSeccion,"
	        			+ "p.cSeccion,p.vDetalleSeccion,p.iValor,p.iTipoControl,p.iOrden,"
	        			+"p.vTabla,p.vCampo from "
	        			+" SMFORMATO p where p.iTipoControl=0 and p.iCodTipoSeccion="+iCodSeccion
	        			+ " and (p.vDetalleSeccion like '1%' or p.vDetalleSeccion like '2%' or p.vDetalleSeccion like '3%' or " +
	        			" p.vDetalleSeccion like '4%' or p.vDetalleSeccion like '5%' ) order by iOrden";	        	

	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	           			item.iCodFormato=fila.getInt(0);
	            	   item.iCodFicha=fila.getInt(1);              
	            	   item.iCodTipoSeccion=fila.getInt(2);
	            	   item.cSeccion=fila.getString(3);
	            	   item.vDetalleSeccion=fila.getString(4);
	            	   item.iValor=fila.getInt(5);
	            	   item.iTipoControl=fila.getInt(6);
	            	   item.iOrden=fila.getInt(7);
	            	   //item.vTabla= fila.getString(13);//.trim();        
	                   //item.vCampo=fila.getString(17);//.trim();    
	                   
	           
	        }
	       bd.close();   
	       return item;
		 
	 }
	 
	 public static RECORDCARDITEM ObtenerCabeceraSeccionObservacion(Context context, int idFichasGrabadas, int iCodFicha, int iCodSeccion ){
		 
		 RECORDCARDITEM item=new RECORDCARDITEM();
		 SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select p.iCodFormato,p.iCodFicha,p.iCodTipoSeccion,"
	        			+ "p.cSeccion,p.vDetalleSeccion,p.iValor,p.iTipoControl,p.iOrden,"
	        			+"p.vTabla,p.vCampo from "
	        			+" SMFORMATO p where p.iTipoControl=0 and p.iCodTipoSeccion="+iCodSeccion
	        			+ " order by iOrden";	        	

	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	           			item.iCodFormato=fila.getInt(0);
	            	   item.iCodFicha=fila.getInt(1);              
	            	   item.iCodTipoSeccion=fila.getInt(2);
	            	   item.cSeccion=fila.getString(3);
	            	   item.vDetalleSeccion=fila.getString(4);
	            	   item.iValor=fila.getInt(5);
	            	   item.iTipoControl=fila.getInt(6);
	            	   item.iOrden=fila.getInt(7);
	            	   //item.vTabla= fila.getString(13);//.trim();        
	                   //item.vCampo=fila.getString(17);//.trim();    
	                   
	           
	        }
	       bd.close();   
	       return item;
		 
	 }
	 
	 
	 public static int CantidadVerificacionSeccion(Context context, int[] arraySecciones, int idFichasGrabadas)
	 {
			String stringArraySecciones="";
		 	for(int i=0;i<arraySecciones.length;i++){
		 		if(i==0){
		 			stringArraySecciones= String.valueOf(arraySecciones[i]);
		 		}else{
		 			stringArraySecciones=stringArraySecciones+","+ String.valueOf(arraySecciones[i]);
		 		}
		 	}
		  int cantidad=0;
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select count(*) from "
	        			+ NOMBRE_TABLA  //" p inner join DBQUESTIONARY t on p.iCodBcoPreg = t.iCodBcoPreg "
	        			+" p where p.idFichasGrabadas="+idFichasGrabadas+" and p.iCodTipoSeccion in ("
	        			+stringArraySecciones+ ") and p.iRecordCardItemPadre is null order by p.iRecordCardItemPadre";	        	
	        	  fila=bd.rawQuery(query,null);
		           numRows = fila.getCount();   
		           if(fila.moveToFirst()){
		        	   cantidad=fila.getInt(0);
		        	   
		           }
		        	   
		           
	        
	        }
	        bd.close();   
		    return cantidad;
		 
	 }
	 
	 public static List<List<COMPONENTELIBERACION>> ListarSeccion(Context context, String cTipoRacion, String cCodProveedor, int[] arraySecciones)
	    {
		 	String stringArraySecciones="";
		 	for(int i=0;i<arraySecciones.length;i++){
		 		if(i==0){
		 			stringArraySecciones= String.valueOf(arraySecciones[i]);
		 		}else{
		 			stringArraySecciones=stringArraySecciones+","+ String.valueOf(arraySecciones[i]);
		 		}
		 	}
		 
	       List<List<COMPONENTELIBERACION>> list=new ArrayList<List<COMPONENTELIBERACION>>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select p.iComponenteLiberacion,p.iCodFormato,p.iCodFicha,p.iCodTipoSeccion,"
	        			+ "p.cSeccion,p.vDetalleSeccion, "
	        			+"case when p.iTipoControl=9 then (SELECT CAST(ch.vResultado as Integer) FROM COMPONENTELIBERACION ch where ch.iComponenteLiberacion = p.iComponenteLiberacionPadre) else p.iValor end as iValor," +
	        			"p.iTipoControl,p.iOrden,p.cTipoRacion,"
	        			+ "p.cCodProveedor,"
	        			+ " case when p.iTipoControl=2 then p.vResultado when p.iTipoControl=6 then p.vResultado"
	        			+ " when p.iTipoControl=4 then case when p.vResultado='0' then '(SELECCIONE)' when p.vResultado='1' then 'COMPONENTE BEBIBLE'"
	        			+ " when p.vResultado='2' then 'COMPONENTE SÓLIDO' else '' end "
	        			+ " when p.iTipoControl=8 then p.vResultado when p.iTipoControl=9 then p.vResultado "
	        			+ " when p.iTipoControl=10 then p.vResultado when p.iTipoControl=1 then p.vResultado "
	        			+ " when p.iTipoControl=11 then p.vResultado when  p.iTipoControl=12 then (p.vResultado ||' - ' ||"
	        			+" case when p.iValor=1 then 'Kg' when p.iValor=2 then 'L' else 'No tiene unidad' end" 
	        			+ " )" 
	        			+ " when  p.iTipoControl=16 then (p.vResultado ||' - ' ||"
	    	        			+" case when p.iValor=1 then 'Kg' when p.iValor=2 then 'L' else 'No tiene unidad' end" 
	    	        			+ " ) "
	        			+ " when p.iTipoControl=13 then p.vResultado "
	        			+ " when p.iTipoControl=17 then p.vResultado "
	        			+ " when p.iTipoControl=15 then (p.vResultado || ' - ' || case when p.iValor=0 then '(SIN VOBO)'"
	        			+ " when p.iValor=1 then 'CONFORME' when p.iValor=2 then 'NO CONFORME' when p.iValor=3 then 'NO APLICA' else '' end)else '' end "
	        			+ " as vResultado"
	        			+",p.iIndexCombo,p.bcheck,p.bactivo,ifnull(p.iComponenteLiberacionPadre,p.iComponenteLiberacion) as iRecordCardItemPadre, p.vObservacion from "
	        			+ NOMBRE_TABLA  //" p inner join DBQUESTIONARY t on p.iCodBcoPreg = t.iCodBcoPreg "
	        			+" p where p.bVisible=1 and p.cTipoRacion='"+cTipoRacion+"' and p.iCodTipoSeccion in ("+stringArraySecciones+ ") and p.cCodProveedor='"+cCodProveedor+"' order by iRecordCardItemPadre";	        	

	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();  
	           int actual=0;
	           List<COMPONENTELIBERACION> sublist=null;
	               for (int i = 0; i < numRows; ++i) 
	               {   
	            	   
	            	   COMPONENTELIBERACION entidad= new COMPONENTELIBERACION();            
	                   entidad.iComponenteLiberacion=fila.getInt(0);
	                   entidad.iCodFormato=fila.getInt(1);
	                   entidad.iCodFicha=fila.getInt(2);              
	                   entidad.iCodTipoSeccion=fila.getInt(3);
	                   entidad.cSeccion=fila.getString(4);
	                   entidad.vDetalleSeccion=fila.getString(5).trim();
	                   entidad.iValor=fila.getInt(6);
	                   entidad.iTipoControl=fila.getInt(7);
	                   entidad.iOrden=fila.getInt(8);
	                   entidad.cTipoRacion=fila.getString(9);
	                   entidad.cCodProveedor=fila.getString(10).trim();
	                   entidad.vResultado=fila.getString(11).trim();   
	                   if(entidad.iTipoControl==8){
	                	   entidad.vResultado=GetComponentes(context, entidad.vResultado);
	                	   if(entidad.vResultado==null){
	                		   entidad.vResultado=fila.getString(16);
	                	   }
	                   }else if(entidad.iTipoControl==9){
	                	   entidad.vResultado=GetPresentacion(context, fila.getString(16),entidad.vResultado,entidad.iValor);
	                   }
	                   entidad.iIndexCombo=fila.getInt(12);  
	                   if(fila.getInt(13)==1)
	                	   entidad.bcheck=true;        
	                   if(fila.getInt(14)==1)
	                	    entidad.bactivo=true;        
	                   entidad.iComponenteLiberacionPadre=fila.getInt(15);
	                   
	                   if(actual==0 || (actual!=0 && actual!=fila.getInt(15)))
	                   {
	                	   if(actual!=0){
		                		  list.add(sublist);
		                	  }
	                	  sublist=null;
	                	  sublist=new ArrayList<COMPONENTELIBERACION>();
	                	  actual=fila.getInt(15);
	                   }
	                   sublist.add(entidad);
	                   if(i+1==numRows)
	                		list.add(sublist);
	                   
	                   fila.moveToNext();   
	               }   
	           
	        }
	       bd.close();   
	       return list;
	    }
	 
	public static String GetComponentes (Context context, String iCods){
		String result="";
		List<COMPONENTELIBERACION> list=new ArrayList<COMPONENTELIBERACION>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	//String query="select " + "." + "+ vNombreComponente) FROM COMPONENTERACION where iCodFichaTecnicaRacion in ("+iCods+")";
				String query="select GROUP_CONCAT(vNombreComponente) FROM COMPONENTERACION where iCodFichaTecnicaRacion in ("+iCods+")";
	        	fila=bd.rawQuery(query,null);
		        if(fila.moveToFirst()){
		        	result= fila.getString(0); 
		        	
		        }
	        			
	        }
	        bd.close();   
		    return result;
	        
	}
	
	public static String GetPresentacion (Context context, String iCods, String vMedicion, int iCodTipoRacion){
		String result="";
		
		List<String> listPresentaciones=new ArrayList<String>();
		listPresentaciones.add("SELECCIONE");
		List<String> listUnidades=new ArrayList<String>();
		listUnidades.add("SELECCIONE");
		
		switch(iCodTipoRacion)
		{
			case 1:
				listPresentaciones.add("TETRABRIK");
				listPresentaciones.add("ENVASE PLÁSTICO");
				listPresentaciones.add("HOJALATA");
				listUnidades.add("ml");
				
				break;
			case 2:
				listPresentaciones.add("BOLSA DE POLIETILENO");
				listPresentaciones.add("BOLSA DE PROPILENO");
				listPresentaciones.add("POLIPROPILENO BIORENTADO");
				listUnidades.add("g");
				break;
			default:
				break;
				
		}
		
		
		result= listPresentaciones.get(Integer.valueOf(iCods.split(",")[0]))
		+" x " + vMedicion+ " "+listUnidades.get(Integer.valueOf(iCods.split(",")[1]) );
		
		 return result;
	        
	}
	
	 public static List<COMPONENTELIBERACION> ListarFormatoItemValores(Context context, int iCodFicha, int[] arraySecciones, int iComponenteLiberacion)
	    {
		 	String stringArraySecciones="";
		 	for(int i=0;i<arraySecciones.length;i++){
		 		if(i==0){
		 			stringArraySecciones= String.valueOf(arraySecciones[i]);
		 		}else{
		 			stringArraySecciones=stringArraySecciones+","+ String.valueOf(arraySecciones[i]);
		 		}
		 	}
		 
	       List<COMPONENTELIBERACION> list=new ArrayList<COMPONENTELIBERACION>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select IFNULL(r.iComponenteLiberacion,0)as iComponenteLiberacion,IFNULL(IFNULL(r.iComponenteLiberacionPadre,r.iComponenteLiberacion),0) as iComponenteLiberacionPadre,"
	        			+ " f.iCodFormato,"
	        			+ "f.iCodFicha,f.iCodTipoSeccion,f.cSeccion," 
	        			+ "case when f.iTipoControl=1 then IFNULL(r.vDetalleSeccion,f.vDetalleSeccion ) else f.vDetalleSeccion end as vDetalleSeccion,"
	        			+ "case when f.iTipoControl=8 then IFNULL(r.iValor,1) when f.iTipoControl=12 then IFNULL(r.iValor,0)"
	        			+" when f.iTipoControl=15 then IFNULL(r.iValor,1) when f.iTipoControl=16 then IFNULL(r.iValor,0)"
	        			+ " when f.iTipoControl=17 then IFNULL(r.iValor,0) else 0 end as iValor,"
	             		+ " f.iTipoControl,f.iOrden,r.cTipoRacion,"
	        			+ "case when f.iTipoControl=2 then IFNULL(r.vResultado,'') when f.iTipoControl=6 then IFNULL(r.vResultado,'')"
	             		+ " when f.iTipoControl =10 then iFNULL(r.vResultado,'')"
	             		+ " when f.iTipoControl =17 then iFNULL(r.vResultado,'')"
	             		+ " when f.iTipoControl =11 then iFNULL(r.vResultado,'') when f.iTipoControl=12 then IFNULL(r.vResultado,'')"
	        			+ " when f.iTipoControl=13  then IFNULL(r.vResultado,'') when f.iTipoControl=14 THEN IFNULL(r.vResultado,'')" 
	             		+ " when f.iTipoControl=15 then IFNULL(r.vResultado,'')when f.iTipoControl=16 then IFNULL(r.vResultado,'') else '' end  as vResultado,"
	             		+ "case when f.iTipoControl=8 then IFNULL(r.vObservacion,'') when f.iTipoControl=12 then IFNULL(r.vObservacion,'')"
	        			+ " when f.iTipoControl=13 then IFNULL(r.vObservacion,'') when f.iTipoControl=15 then ifnull(r.vObservacion,'')"
	        			+ " when f.iTipoControl=16 then IFNULL(r.vObservacion,'') else '' end as vObservacion, bcheck"
	        			+ " from SMFORMATO f left join COMPONENTELIBERACION r on f.iCodFormato =r.iCodFormato "
	        			+ " and ifnull(r.iComponenteLiberacionPadre,r.iComponenteLiberacion)="+iComponenteLiberacion
	        			+ " where  f.iCodTipoSeccion in ("+stringArraySecciones 
	             		+") and f.iTipoControl<>0  and f.iCodFicha="+iCodFicha+" order by f.iOrden"; 

	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   
	            	   COMPONENTELIBERACION entidad= new COMPONENTELIBERACION();
	            	   
	                   entidad.iComponenteLiberacion=fila.getInt(0);
	                   entidad.iComponenteLiberacionPadre=fila.getInt(1);
	                   entidad.iCodFormato=fila.getInt(2);
	                   entidad.iCodFicha=fila.getInt(3);              
	                   entidad.iCodTipoSeccion=fila.getInt(4);
	                   entidad.cSeccion=fila.getString(5);
	                   entidad.vDetalleSeccion=fila.getString(6).trim();
	                   entidad.iValor=fila.getInt(7);
	                   entidad.iTipoControl=fila.getInt(8);
	                   entidad.iOrden=fila.getInt(9);
	                   entidad.cTipoRacion=fila.getString(10);
	                   entidad.vResultado=fila.getString(11);
	                   entidad.vObservacion=fila.getString(12);
	                   entidad.bcheck= fila.getInt(13)==1? true:false;
	                   list.add(entidad);
	                      
	                   fila.moveToNext();   
	               }   
	           
	        }
	       bd.close();   
	       return list;
	    }
	
	 
	 public static void EliminarRegistroComponente(Context context, int iCodFicha, int[] arraySecciones, int iComponenteLiberacion)
	    {
		 	String stringArraySecciones="";
		 	for(int i=0;i<arraySecciones.length;i++){
		 		if(i==0){
		 			stringArraySecciones= String.valueOf(arraySecciones[i]);
		 		}else{
		 			stringArraySecciones=stringArraySecciones+","+ String.valueOf(arraySecciones[i]);
		 		}
		 	}
		 
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="DELETE FROM " + NOMBRE_TABLA
	        			+ " where iCodFicha="+ iCodFicha+" and ifnull(iComponenteLiberacionPadre,iComponenteLiberacion)="+iComponenteLiberacion
	        			+ " and  iCodTipoSeccion in ("+stringArraySecciones	+")"; 

	           bd.execSQL(query);
	           
	           
	        }
	       bd.close();   
	    }
	
	 public static List<RECORDCARDITEM> ListarPaginaSeccion(Context context, int idFichasGrabadas, int[] arraySecciones, int pInicial, int pFinal)
	    {
		 	String stringArraySecciones="";
		 	for(int i=0;i<arraySecciones.length;i++){
		 		if(i==0){
		 			stringArraySecciones= String.valueOf(arraySecciones[i]);
		 		}else{
		 			stringArraySecciones=stringArraySecciones+","+ String.valueOf(arraySecciones[i]);
		 		}
		 	}
		 
	       List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select p.iRecordCardItem,p.iCodFormato,p.iCodFicha,p.ordenllamada,p.iCodTipoSeccion,"
	        			+ "p.cSeccion,p.vDetalleSeccion,p.iValor,p.iTipoControl,p.iOrden,p.cCodEstablecimiento,"
	        			+ "p.cCodProveedor,p.vResultado,p.vTabla,p.iIndexCombo,p.bcheck,p.bactivo,p.vCampo, p.iTipoColumna from "
	        			+ NOMBRE_TABLA  //" p inner join DBQUESTIONARY t on p.iCodBcoPreg = t.iCodBcoPreg "
	        			+" p where p.idFichasGrabadas="+idFichasGrabadas+" and p.iCodTipoSeccion in ("+stringArraySecciones+ ")"
	        			+" and p.iOrden>="+pInicial+" and p.iOrden<"+pFinal +" order by iTipoColumna";	        	

	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   
	            	   RECORDCARDITEM entidad= new RECORDCARDITEM();            
	                   entidad.iRecordCardItem=fila.getInt(0);
	                   entidad.iCodBcoPreg=fila.getString(1);
	                   entidad.iCodFicha=fila.getInt(2);              
	                   entidad.ordenLlamada=fila.getString(3).trim();
	                   entidad.iCodTipoSeccion=fila.getInt(4);
	                   entidad.cSeccion=fila.getString(5);
	                   entidad.vDetalleSeccion=fila.getString(6).trim();
	                   entidad.iValor=fila.getInt(7);
	                   entidad.iTipoControl=fila.getInt(8);
	                   entidad.iOrden=fila.getInt(9);
	                   entidad.cCodEstablecimiento=fila.getString(10).trim();
	                   entidad.cCodProveedor=fila.getString(11).trim();
	                   entidad.vResultado=fila.getString(12).trim();                   
	                   entidad.vTabla=fila.getString(13).trim();        
	                   entidad.iIndexCombo=fila.getInt(14);  
	                   if(fila.getInt(15)==1)
	                	   entidad.bcheck=true;        
	                   if(fila.getInt(16)==1)
	                	    entidad.bactivo=true;        
	                   entidad.vCampo=fila.getString(17).trim();    
	                   
	                   entidad.iTipoColumna = fila.getInt(18); 
	                   
	                   list.add(entidad);
	                      
	                   fila.moveToNext();   
	               }   
	           
	        }
	       bd.close();   
	       return list;
	    }
	
	 public static List<TIPOCOMPONENTERACION> ListarTipoRacionporTipoRacion(Context context, int iCodTipoRacion, String cCodProveedor, int iCodFicha)
	 {
		   List<TIPOCOMPONENTERACION> list=new ArrayList<TIPOCOMPONENTERACION>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	String query="select distinct p.cTipoRacion,p.vResultado,p.vResultado from "
	        			+ NOMBRE_TABLA  //" p inner join DBQUESTIONARY t on p.iCodBcoPreg = t.iCodBcoPreg "
	        			+" p where p.iCodFicha="+iCodFicha+" and p.cTipoRacion='"+iCodTipoRacion
	        			+"' and p.iComponenteLiberacionPadre is null and p.cCodProveedor='"+cCodProveedor+"'";	        	

	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   
	            	   TIPOCOMPONENTERACION entidad= new TIPOCOMPONENTERACION();            
	                   entidad.iCodTipoRacion= Integer.parseInt(fila.getString(1));
	                   
	                   list.add(entidad);
	                      
	                   fila.moveToNext();   
	               }   
	           
	        }
	       bd.close();   
	       return list;
		 
		 
	 }
     
     public  static boolean ActualizarComponente(Context context, COMPONENTELIBERACION entidad)
     {
  	   SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("vResultado",entidad.vResultado);
        registro.put("iValor",entidad.iValor);
        registro.put("vObservacion",entidad.vObservacion);
        
        
        registro.put("bcheck",(true == entidad.bcheck)? 1 : 0);
      
        registro.put("bactivo",(true == entidad.bactivo)? 1 : 0);
        int cant = bd.update(NOMBRE_TABLA, registro, "iComponenteLiberacion="+entidad.iComponenteLiberacion, null);
        
        
        bd.close();
        if(cant==1)
            return true;
        else
            return false;
       
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
