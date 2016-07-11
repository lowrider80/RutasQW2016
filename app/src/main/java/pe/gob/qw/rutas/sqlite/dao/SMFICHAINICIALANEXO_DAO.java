package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.SMFICHAINICIALANEXO;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.sqlite.SQLite;

public class SMFICHAINICIALANEXO_DAO {
	private static String NOMBRE_TABLA="SMFICHAINICIALANEXO";
	public static int Agregar(Context context, SMFICHAINICIALANEXO entidad)
	   {
	       int id = 0;
	       SQLite admin=new SQLite(context,null);
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       ContentValues registro=new ContentValues();
	       
	       registro.put("idFichaGrabadas",entidad.idFichaGrabadas);
	       registro.put("iCodModalidad",entidad.iCodModalidad);
	       registro.put("iCodComite",entidad.iCodComite);
	       registro.put("vComite",entidad.vComite);
	       registro.put("vCodPostor",entidad.vCodPostor);
	       registro.put("vItem",entidad.vItem);
	       registro.put("vCodItem",entidad.vCodItem);
	       
	       registro.put("iCantSolido",entidad.iCantSolido);
	       registro.put("iCantSolidoP",entidad.iCantSolidoP);
	       registro.put("iCantBebible",entidad.iCantBebible);
	       registro.put("iCantBebibleP",entidad.iCantBebibleP);
	       registro.put("iCantAcompanamiento",entidad.iCantAcompanamiento);
	       registro.put("iCantAcompanamientoP",entidad.iCantAcompanamientoP);
	       registro.put("iCantGalleta",entidad.iCantGalleta);
	       registro.put("iCantGalletaP",entidad.iCantGalletaP);
	       registro.put("iCantAlimento",entidad.iCantAlimento);
	       registro.put("iCantAlimentoP",entidad.iCantAlimentoP);
	       registro.put("iCalificado", entidad.iCalificado);
	    
	       id = (int) bd.insert(NOMBRE_TABLA, null, registro);
	       bd.close();    
	       return id;

	 }

	public static boolean Actualizar(Context context, SMFICHAINICIALANEXO entidad)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		ContentValues registro=new ContentValues();
		registro.put("idFichaGrabadas",entidad.idFichaGrabadas);
		registro.put("iCodModalidad",entidad.iCodModalidad);
		registro.put("iCodComite",entidad.iCodComite);
		registro.put("vComite",entidad.vComite);
		registro.put("vCodPostor",entidad.vCodPostor);
		registro.put("vItem",entidad.vItem);
		registro.put("vCodItem",entidad.vCodItem);

		registro.put("iCantSolido",entidad.iCantSolido);
		registro.put("iCantSolidoP",entidad.iCantSolidoP);
		registro.put("iCantBebible",entidad.iCantBebible);
		registro.put("iCantBebibleP",entidad.iCantBebibleP);
		registro.put("iCantAcompanamiento",entidad.iCantAcompanamiento);
		registro.put("iCantAcompanamientoP",entidad.iCantAcompanamientoP);
		registro.put("iCantGalleta",entidad.iCantGalleta);
		registro.put("iCantGalletaP",entidad.iCantGalletaP);
		registro.put("iCantAlimento",entidad.iCantAlimento);
		registro.put("iCantAlimentoP",entidad.iCantAlimentoP);
		registro.put("iCalificado", entidad.iCalificado);


		int cant = bd.update(NOMBRE_TABLA, registro, "idFichaGrabadas="+entidad.idFichaGrabadas +" and iCodComite="+entidad.iCodComite, null);
		bd.close();
		if(cant==1)
			return true;
		else
			return false;

	}

	public static List<SMFICHAINICIALANEXO> Listar(Context context, int idFichaGrabadas) {
		List<SMFICHAINICIALANEXO> list=new ArrayList<SMFICHAINICIALANEXO>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null) {
			String query="select idFichaGrabadas,iCodModalidad,iCodComite,vComite,"
					+ "vCodItem,vItem,vCodPostor,iCantSolido,"
					+ "iCantSolidoP,iCantBebible,iCantBebibleP,iCantAcompanamiento,iCantAcompanamientoP,"
					+ "iCantGalleta,iCantGalletaP,iCantAlimento,iCantAlimentoP,iCalificado"
					+"  from "+NOMBRE_TABLA+" where idFichaGrabadas="+idFichaGrabadas;

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				SMFICHAINICIALANEXO entidad=new SMFICHAINICIALANEXO();
				entidad.idFichaGrabadas=fila.getInt(0);
				entidad.iCodModalidad =fila.getInt(1);
				entidad.iCodComite =fila.getInt(2);
				entidad.vComite=fila.getString(3);
				entidad.vCodItem = fila.getString(4);
				entidad.vItem=fila.getString(5);
				entidad.vCodPostor=fila.getString(6);
				entidad.iCantSolido=fila.isNull(7)?null: fila.getInt(7);
				entidad.iCantSolidoP =fila.isNull(8)?null: fila.getInt(8);
				entidad.iCantBebible =fila.isNull(9)?null:fila.getInt(9);
				entidad.iCantBebibleP= fila.isNull(10)?null:fila.getInt(10);
				entidad.iCantAcompanamiento = fila.isNull(11)?null:fila.getInt(11);
				entidad.iCantAcompanamientoP =fila.isNull(12)?null: fila.getInt(12);
				entidad.iCantGalleta =fila.isNull(13)?null:fila.getInt(13);
				entidad.iCantGalletaP=fila.isNull(14)?null:fila.getInt(14);
				entidad.iCantAlimento = fila.isNull(15)?null:fila.getInt(15);
				entidad.iCantAlimentoP = fila.isNull(16)?null:fila.getInt(16);
				entidad.iCalificado = fila.getInt(17);
				list.add(entidad);
				fila.moveToNext();
			}
		}

		bd.close();
		return list;
	}

	public static void BorrarId(Context context, int idFichasGrabadas) {
    	 SQLite admin=new SQLite(context,null);
	     SQLiteDatabase bd=admin.getWritableDatabase();
	     bd.delete(NOMBRE_TABLA, "idFichasGrabadas="+idFichasGrabadas, null);
	     bd.close();
    }
	 
	public static void BorrarComite(Context context, int idFichasGrabadas, int iCodComite, int iCodModalidad) {
    	 SQLite admin=new SQLite(context,null);
	     SQLiteDatabase bd=admin.getWritableDatabase();
	     bd.delete(NOMBRE_TABLA, "idFichaGrabadas="+idFichasGrabadas+" and iCodComite="+iCodComite+ " and iCodModalidad="+iCodModalidad, null);
	     bd.close();
    }
	 
	 public static void BorrarRecordFichaGrabada(Context context, int idFichasGrabadas){
	    	SQLite admin=new SQLite(context,null); 
	        	SQLiteDatabase bd=admin.getWritableDatabase();
	          if(bd!=null)
	          {
	        	  bd.delete(NOMBRE_TABLA, "idFichasGrabadas="+idFichasGrabadas, null);
	     	   
	             
	         }
	          bd.close();
	     }
	 
	 public static  SMFICHASGRABADAS ExisteFicha(Context context, SMFICHASGRABADAS entidadResult)
     {
		 SMFICHASGRABADAS  entidad= null;
		 SQLite admin=new SQLite(context,null); 
         SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
            String query="select idFichasGrabadas  from "+NOMBRE_TABLA+" where cCodEstablecimiento="+entidadResult.cCodEstablecimiento+" AND cCodProveedor= "+entidadResult.cCodProveedor+" AND iCodFicha="+entidadResult.iCodFicha;
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {    
                entidad= new SMFICHASGRABADAS();            
                
                entidad.idFichasGrabadas=fila.getInt(0);
                /*entidad.cCodEstablecimiento=fila.getString(1);
                entidad.cCodProveedor=fila.getString(2);
                entidad.iCodFicha=fila.getInt(3);
                entidad.iEstado=fila.getInt(4);
                entidad.lFecha=fila.getLong(5);
                entidad.vNControlCalidad=fila.getString(6);
                entidad.vNRepresentanteLegal=fila.getString(7);
                entidad.vDNIRepresentanteLegal=fila.getString(8);
                entidad.vNSupervisor=fila.getString(9);
                entidad.vDNISupervisor=fila.getString(10);
                entidad.vNPNAEQW=fila.getString(11);
                entidad.vDNIPNAEQW=fila.getString(12);
                entidad.vDescFicha=fila.getString(13);
                
                entidad.vDatosRelevantes=fila.getString(14);
                entidad.iTotalOperarios=fila.getInt(15);
                entidad.dOpcion1=fila.getString(16);
                entidad.dOpcion2=fila.getDouble(17);
                entidad.dOpcion3=fila.getDouble(18);
                entidad.dOpcion4=fila.getDouble(19);
                entidad.dPuntajeFicha=fila.getDouble(20);
                entidad.dPuntajeAnexo=fila.getDouble(21);
                if(fila.getInt(22)==1)
                entidad.bCalificaFicha=true;
                if(fila.getInt(23)==1)
                entidad.bCalificaAnexo=true;
                entidad.iTotalPreguntas=fila.getInt(24);
                
                entidad.vDNIControlCalidad=fila.getString(25);
                entidad.vEspecialidad=fila.getString(26);
                entidad.vColegiatura=fila.getString(27);
                entidad.lFechaFin=fila.getLong(28);
                entidad.cCategoria=fila.getString(29);
                entidad.vTipoVehiculo=fila.getString(30);
                entidad.vPlaca=fila.getString(31);
                entidad.vGuiaRemision=fila.getString(32);
                
                entidad.iCantidadRacionesAdjudicadas=fila.getInt(33);
                entidad.iCantidadRacionesVerificadas=fila.getInt(34);
                entidad.vNombreRacionesVerificadas=fila.getString(35);
                entidad.vTurno=fila.getString(36);
                entidad.iNumeroOperariosHombre=fila.getInt(37);
                entidad.iNumeroOperariosMujer=fila.getInt(38);
                
                entidad.vTelefonoRepresentanteLegal=fila.getString(39);
                entidad.vEmpresaResponsableSanidad=fila.getString(40);
                entidad.vEmpresaResponsableMedico=fila.getString(41);
                entidad.vFechaVigenciaSanidad=fila.getLong(42);
                entidad.vFechaVigenciaCertificadoMedico=fila.getLong(43);
                entidad.vNumeroCertificadoSanidad=fila.getString(44);
                if(fila.getInt(45)==1)
                    entidad.bCertificadoMedico=true;
                entidad.iNroLiberacion=fila.getInt(46);
                entidad.iCodContrato=fila.getInt(47);
                
                entidad.vNroContrato=fila.getString(48);
                entidad.vNroComiteCompra=fila.getString(49);
                entidad.vItem=fila.getString(50);*/
                
                
                //Log.e("DETALLELIBERACION_DAO",""+entidad.iCodContrato);
            }
        }
        bd.close();   
       
        return entidad;
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
