package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.FICHATECNICAPRESENTACION;
import pe.gob.qw.rutas.entities.LIBERACIONRECORDCARDITEM;
import pe.gob.qw.rutas.sqlite.SQLite;

public class LIBERACIONRECORDCARDITEM_DAO {

	private static String NOMBRE_TABLA="LIBERACIONRECORDCARDITEM";
	
	public static int Agregar(Context context, LIBERACIONRECORDCARDITEM entidad) {
		// TODO Auto-generated method stub
		int id = 0;
        SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd=admin.getWritableDatabase();
        if(bd!=null)
        {
	        ContentValues registro=new ContentValues();
	        registro.put("idFichasGrabadas",entidad.idFichasGrabadas);
	        registro.put("iCodProducto",entidad.iCodProducto);
	        registro.put("vNomProducto",entidad.vNomProducto);
	        registro.put("vNomMarca",entidad.vNomMarca);
	        registro.put("iPresentacion",entidad.iPresentacion);
	        registro.put("iIndexComboPresentacion",entidad.iIndexComboPresentacion);
	        registro.put("iCantidad",entidad.iCantidad);
	        registro.put("iIndexCombo",entidad.iIndexCombo);
	        registro.put("vResultado",entidad.vResultado);
	        registro.put("bactivo",entidad.bactivo);
	        registro.put("iOrden",entidad.iOrden);
	        
	        id = (int) bd.insert(NOMBRE_TABLA, null, registro);  
        }
        bd.close();    
      
        return id;
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

	public static List<LIBERACIONRECORDCARDITEM> ListarPagina(Context context, int idFichasGrabadas, int pInicial, int pFinal)
	{
		List<LIBERACIONRECORDCARDITEM> list=new ArrayList<LIBERACIONRECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();


		if(bd!=null)
		{
			String query="select iLIBERACIONRECORDCARDITEM,idFichasGrabadas,iCodProducto,vNomProducto,vNomMarca,iPresentacion,"
					+ "iIndexComboPresentacion,iCantidad,iIndexCombo,vResultado,bactivo,iOrden from "
					+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas
					+" and iOrden>"+pInicial+" and iOrden<= "+pFinal +" and (iIndexCombo = 3 OR iIndexCombo = 0)";

			Cursor fila1=bd.rawQuery(query,null);
			int numRows1 = fila1.getCount();
			fila1.moveToFirst();

			for (int i = 0; i < numRows1; ++i)
			{
				LIBERACIONRECORDCARDITEM entidad= new LIBERACIONRECORDCARDITEM();

				entidad.iLIBERACIONRECORDCARDITEM=fila1.getInt(0);
				entidad.idFichasGrabadas=fila1.getInt(1);
				entidad.iCodProducto=fila1.getInt(2);
				entidad.vNomProducto=fila1.getString(3).trim();
				entidad.vNomMarca=fila1.getString(4).trim();
				entidad.iPresentacion=fila1.getInt(5);
				entidad.iIndexComboPresentacion=fila1.getInt(6);
				entidad.iCantidad=fila1.getInt(7);
				entidad.iIndexCombo=fila1.getInt(8);
				entidad.vResultado=fila1.getString(9).trim();
				entidad.bactivo=fila1.getInt(10);
				entidad.iOrden=fila1.getInt(11);

				List<FICHATECNICAPRESENTACION> listFICHATECNICAPRESENTACION=new ArrayList<FICHATECNICAPRESENTACION>();

				//Log.e("entidad.iLIBERACIONRECORDCARDITEM",""+entidad.iLIBERACIONRECORDCARDITEM);
				query="select iCodFichaTecnicaPresentacion,iCodFichaTecnica,"
						+ "vNombre from FICHATECNICAPRESENTACION where iCodFichaTecnica="+entidad.iCodProducto;
				//Log.e("entidad.query",query);
				Cursor fila2=bd.rawQuery(query,null);

				int numRows2 = fila2.getCount();

				fila2.moveToFirst();
				for (int j = 0;  j< numRows2; ++j)
				{
					FICHATECNICAPRESENTACION OBJFICHATECNICAPRESENTACION= new FICHATECNICAPRESENTACION();
					OBJFICHATECNICAPRESENTACION.iCodFichaTecnicaPresentacion=fila2.getInt(0);
					OBJFICHATECNICAPRESENTACION.iCodFichaTecnica=fila2.getInt(1);
					OBJFICHATECNICAPRESENTACION.vNombre=fila2.getString(2);

					listFICHATECNICAPRESENTACION.add(OBJFICHATECNICAPRESENTACION);

					fila2.moveToNext();

				}
				entidad.LISTFICHATECNICAPRESENTACION=listFICHATECNICAPRESENTACION;
				//Log.e("entidad.LISTFICHATECNICAPRESENTACION",""+entidad.LISTFICHATECNICAPRESENTACION.size());
				list.add(entidad);

				fila1.moveToNext();
			}

		}
		bd.close();
		return list;
	}


	public  static boolean Actualizar(Context context, LIBERACIONRECORDCARDITEM entidad) {
		SQLite admin=new SQLite(context,null);
	    SQLiteDatabase bd = admin.getWritableDatabase();
	    ContentValues registro = new ContentValues();

		registro.put("vNomMarca",entidad.vNomMarca);
		registro.put("iPresentacion",entidad.iPresentacion);
		registro.put("iIndexComboPresentacion",entidad.iIndexComboPresentacion);
		registro.put("iCantidad",entidad.iCantidad);
		registro.put("iIndexCombo",entidad.iIndexCombo);
		registro.put("vResultado",entidad.vResultado);
		registro.put("bactivo",entidad.bactivo);

		int cant = bd.update(NOMBRE_TABLA, registro, "iLIBERACIONRECORDCARDITEM="+entidad.iLIBERACIONRECORDCARDITEM, null);

		bd.close();
		if(cant==1) return true;
		else return false;
	}
	  
	  
	public static  int GetCantidadLiberada(Context context, int idFichasGrabadas ) {
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int total=0;
		if(bd!=null) {
			String query="select  SUM(iCantidadLiberada) from ACTACOLEGIOSITEM"
						+" where idFichasGrabadas="+idFichasGrabadas;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst()) {
				total =fila.getInt(0);
			}
		}
		return total;
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
			        			+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas;	        	
			        	
			            fila=bd.rawQuery(query,null);
				        if (fila.moveToFirst())
				        {     
				        	int total=fila.getInt(0);
				        	
				        	
				        	//Se agregó la siguiente validación (iIndexCombo =2 and vResultado!='') de forma que todos los NO CONFORME tengan la observación como obligatorio.
				        	query="select  count(*) from "
				        			+ NOMBRE_TABLA + 
				        			" where  vNomProducto!='' and iCantidad>0 and iIndexCombo>0 and iIndexComboPresentacion>0 " + 
				        			"and idFichasGrabadas="+idFichasGrabadas+ " AND ((iIndexCombo=1 or iIndexCombo=3) or (iIndexCombo =2 and vResultado !='')) ";	        	
				        	
				            fila=bd.rawQuery(query,null);
					        if (fila.moveToFirst())
					        {     
					        	numRows=total-fila.getInt(0);
					            	
					        }
				            	
				       
		        }
	        }
	       return numRows;
	       
	    }
	  
	  
	  public static  int GetPreguntasConformes(Context context, int idFichasGrabadas)
	    {
		 int numRows=-1;
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
				        	query="select  count(*) from "
				        			+ NOMBRE_TABLA+" where (iIndexCombo=1 or iIndexCombo = 3)  and idFichasGrabadas="+idFichasGrabadas;		   	
				        	
				            fila=bd.rawQuery(query,null);
					        if (fila.moveToFirst())
					        {     
					        	numRows=numRows-fila.getInt(0);
					            	
					        }
				        }
				        
		  }

		          
	       bd.close();   
	       return numRows;
	       
	    }
	  
	  
	  
	  public static  int GetPreguntasNoConformes(Context context, int idFichasGrabadas)
	    {
		 int numRows=-1;
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
				        	query="select  count(*) from "
				        			+ NOMBRE_TABLA+" where (iIndexCombo=2 or iIndexCombo=3)  and idFichasGrabadas="+idFichasGrabadas;		   	
				        	
				            fila=bd.rawQuery(query,null);
					        if (fila.moveToFirst())
					        {     
					        	numRows=numRows-fila.getInt(0);
					            	
					        }
				        }
				        
		  }

		          
	       bd.close();   
	       return numRows;
	       
	    }
	  
	  
	  public static  int GetPreguntasPendientesConformes(Context context, int idFichasGrabadas)
	    {
		 int numRows=-1;
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
				        	query="select  count(*) from "
				        			+ NOMBRE_TABLA+" where (iIndexCombo=2 or iIndexCombo=1)  and idFichasGrabadas="+idFichasGrabadas;		   	
				        	
				            fila=bd.rawQuery(query,null);
					        if (fila.moveToFirst())
					        {     
					        	numRows=numRows-fila.getInt(0);
					            	
					        }
				        }
				        
		  }

		          
	       bd.close();   
	       return numRows;
	       
	    }
  
	  public static int AgregarFicha(Context context, int idFichasGrabadas, int iCodContrato, String cCodEstablecimiento, String iNumEntrega  ) {
			// TODO Auto-generated method stub
			int id = 0;
	        SQLite admin=new SQLite(context,null);
	        SQLiteDatabase bd=admin.getWritableDatabase();
	        Cursor fila;
	        int numRows;
	        if(bd!=null)
	        {
	        	
	        	String query="select iCodFichaTecnica,vNombreFichaTecnica,Marca,iCodFichaTecnicaPresentacion,iCodDetFicha,dcCantidad from DETALLELIBERACION where iCodDetFicha not in"
						+" (SELECT l.iCodDetFicha "
			   	    	+" FROM LIBERACIONRECORDCARDITEM l " 
			   	    	+" INNER JOIN DETALLELIBERACION  c ON (c.iCodDetFicha = l.iCodDetFicha ) )"			   	    	
	        			+ " AND iCodContrato="+iCodContrato+"  AND cCodEstablecimiento="+cCodEstablecimiento.trim()+ " AND iNumEntrega like " + iNumEntrega.trim() ;
	        	
	        			fila=bd.rawQuery(query,null);
	        			numRows = fila.getCount();   
	        			fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   
	                   	ContentValues registro=new ContentValues();
		   		        registro.put("idFichasGrabadas",idFichasGrabadas);
		   		        registro.put("iCodProducto",fila.getInt(0));
		   		        registro.put("vNomProducto",fila.getString(1));
		   		        registro.put("vNomMarca",fila.getString(2));//agregar la marca
		   		        registro.put("iPresentacion",0);// agregar la presentacion
		   		        registro.put("iIndexComboPresentacion",fila.getInt(3));
		   		        registro.put("iCantidad",0);
		   		        registro.put("iIndexCombo",0);
		   		        registro.put("vResultado","");
		   		        registro.put("bactivo",0);
		   		        registro.put("iOrden",(i+1));
		   		        registro.put("iCodDetFicha",fila.getInt(4));
		   		        registro.put("iCantidad",fila.getInt(5));
		   		        id = (int) bd.insert(NOMBRE_TABLA, null, registro);  
	                      
	                   fila.moveToNext();   
	               }   
		        
	        }
	        bd.close();    
	      
	        return id;
		}
	  
	  
	  
	public static  int Existe(Context context, int idFichasGrabadas, int iCodContrato, String cCodEstablecimiento  ) {
		// TODO Auto-generated method stub
		int id = 0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null) {
			String query="  SELECT l.iLIBERACIONRECORDCARDITEM, l.iCodDetFicha, c.iCodFichaTecnica,c.vNombreFichaTecnica,c.Marca,c.iCodFichaTecnicaPresentacion,c.iCodDetFicha,c.dcCantidad "
					+" FROM LIBERACIONRECORDCARDITEM l "
					+" INNER JOIN DETALLELIBERACION  c ON (c.iCodDetFicha = l.iCodDetFicha ) and iIndexCombo =3 "
					+ " AND c.iCodContrato="+iCodContrato+"  AND c.cCodEstablecimiento="+cCodEstablecimiento.trim()+ " and l.iIndexCombo =3";

					fila=bd.rawQuery(query,null);
					numRows = fila.getCount();
					fila.moveToFirst();

			for (int i = 0; i < numRows; ++i) {
				id = (int)fila.getInt(0);
				fila.moveToNext();
			}
		}

		bd.close();
		return id;
	}
	  
	  
	  
	  
	  public static List<LIBERACIONRECORDCARDITEM> ListarXidFichasGrabadas(Context context, int idFichasGrabadas)
	    {
	       List<LIBERACIONRECORDCARDITEM> list=new ArrayList<LIBERACIONRECORDCARDITEM>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       
	      
	        if(bd!=null)
	        {
	        	String query="select iLIBERACIONRECORDCARDITEM,idFichasGrabadas,iCodProducto,vNomProducto,vNomMarca,iPresentacion,"
	        			+ "iIndexComboPresentacion,iCantidad,iIndexCombo,vResultado,bactivo,iOrden,iCodDetFicha from "
	        			+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas+" and iIndexCombo < 3";	        	
	        	
	        	Cursor fila1=bd.rawQuery(query,null);
	        	int numRows1 = fila1.getCount();   
	        	fila1.moveToFirst();   
	             
	        	for (int i = 0; i < numRows1; ++i) 
	               {   
	            	   LIBERACIONRECORDCARDITEM entidad= new LIBERACIONRECORDCARDITEM();            
	            	   
	            	   entidad.iLIBERACIONRECORDCARDITEM=fila1.getInt(0);
	            	   entidad.idFichasGrabadas=fila1.getInt(1);
	                   entidad.iCodProducto=fila1.getInt(2);
	                   entidad.vNomProducto=fila1.getString(3).trim();              
	                   entidad.vNomMarca=fila1.getString(4).trim();
	                   entidad.iPresentacion=fila1.getInt(5);
	                   entidad.iIndexComboPresentacion=fila1.getInt(6);
	                   entidad.iCantidad=fila1.getInt(7);
	                   entidad.iIndexCombo=fila1.getInt(8);
	                   entidad.vResultado=fila1.getString(9).trim();
	                   entidad.bactivo=fila1.getInt(10);
	                   entidad.iOrden=fila1.getInt(11);
	                   entidad.iCodDetFicha=fila1.getInt(12);
	                 
	                  
	        	       
	        	       list.add(entidad);
	                      
	                   fila1.moveToNext();   
	               }   
	           
	        }
	       bd.close();   
	       return list;
	    }
	  public static void BorrarXid(Context context, int id) {
		   	 SQLite admin=new SQLite(context,null);
			     SQLiteDatabase bd=admin.getWritableDatabase();
			     bd.delete(NOMBRE_TABLA, "iLIBERACIONRECORDCARDITEM="+id, null);
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
