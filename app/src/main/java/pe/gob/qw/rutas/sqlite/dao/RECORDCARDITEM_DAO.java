package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.ItemTransporte;
import pe.gob.qw.rutas.entities.RECORDCARDITEM;
import pe.gob.qw.rutas.sqlite.SQLite;

public class RECORDCARDITEM_DAO {

	private static String NOMBRE_TABLA="RECORDCARDITEM";

	//CREA TABLA EN RECORD CARD ITEM
	public static  int Agregarv2(Context context, int idFichasGrabadas, int iCodFicha, String stringArraySecciones, String cCodProveedor, String cCodEstablecimiento )
	{
		int numRows=0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		//String stringArraySecciones="";
		//for(int i=0;i<arraySeccion.length;i++){
		//	if(i==0){
		//		stringArraySecciones=String.valueOf(arraySeccion[i]);
		//	}else{
		//		stringArraySecciones=stringArraySecciones+","+ String.valueOf(arraySeccion[i]);
		//	}
		//}

		if(bd!=null)
		{
			String query="select iCodBcoPreg,iCodFicha,ordenllamada,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,iTipoColumna from DBQUESTIONARY where iCodFicha=" + iCodFicha
					+ " and iCodTipoSeccion in ("+stringArraySecciones+")";

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

	public static String NumVehiculos (Context context, int idfichasGrabadas, int icodficha)
	{
		String numero="";
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows=-1;
		if(bd!=null)
		{
			String query="select vDatosRelevantes from SMFICHASGRABADAS where idFichasGrabadas="+idfichasGrabadas+" and icodficha="+icodficha;
			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				numero=fila.getString(0);
				fila.moveToNext();
			}
		}
		bd.close();
		return numero;

	}

	public  static boolean ActualizarPlaca(Context context, RECORDCARDITEM entidad)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		ContentValues registro = new ContentValues();
		registro.put("vResultado",entidad.vResultado);
		registro.put("iIndexCombo",entidad.iIndexCombo);
		registro.put("bcheck",(true == entidad.bcheck)? 1 : 0);
		registro.put("bactivo",(true == entidad.bactivo)? 1 : 0);
		registro.put("vObservacion",entidad.vObservacion);
		int cant = bd.update(NOMBRE_TABLA, registro, "iRecordCardItem="+entidad.iRecordCardItem, null);
		bd.close();
		if(cant==1)
			return true;
		else
			return false;
	}

	public static int VerificarCombosConformesVehiculo(Context context, int iCodFicha, int idFichaGrabada, int RC) {

		int numRows = 0;
		int cnum = 0;
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;
		if (bd != null) {
			String query = "SELECT  count(*) FROM " +NOMBRE_TABLA+ " WHERE iCodFicha= "+iCodFicha+
					" and iTipoControl=8 and vResultado='SELECCIONE' and iRecordCardItemPadre="+RC+ " and idFichasGrabadas="+idFichaGrabada;
			fila = bd.rawQuery(query, null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				cnum = fila.getInt(0);
				fila.moveToNext();
			}
		}
		bd.close();
		return cnum;
	}

	public static List<RECORDCARDITEM> ChequearEstado(Context context, int iCodFicha, int iRecordCardItem, String Placa)
	{


		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query=" select vObservacion from RECORDCARDITEM where vResultado ='"+Placa+"'";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				RECORDCARDITEM entidad= new RECORDCARDITEM();
				entidad.vObservacion=fila.getString(0);

				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public  static boolean ActualizarEstados(Context context, RECORDCARDITEM entidad, int idfichas, int iCodFicha, String Placa)
	{	boolean x=false;
		try
		{
			SQLite admin=new SQLite(context,null);
			SQLiteDatabase bd = admin.getWritableDatabase();
			ContentValues registro = new ContentValues();
			registro.put("vObservacion", entidad.vObservacion);
			int cant = bd.update(NOMBRE_TABLA, registro, "iCodFicha=" + iCodFicha + " and idFichasGrabadas=" + idfichas + " and vResultado='"+Placa+"'", null);
			bd.close();
			if(cant==1)
				return true;
			else
				return false;}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		return x;
	}

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

	public static List<RECORDCARDITEM> ListarFormatoTransporteValores(Context context, int iCodFicha, int[] arraySecciones, int iRecordCardItem)
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
			String query="select IFNULL(r.iRecordCardItem,0)as iRecordCardItem,IFNULL(IFNULL(r.iRecordCardItemPadre,r.iRecordCardItem),0) as iRecordCardItemPadre, f.iCodFormato,"
					+ "f.iCodFicha,f.iCodTipoSeccion,f.cSeccion,"
					+ "case when f.iTipoControl=1 then IFNULL(r.vDetalleSeccion,f.vDetalleSeccion ) else f.vDetalleSeccion end as vDetalleSeccion,"
					+ "case when f.iTipoControl=8 then IFNULL(r.iValor,1) when f.iTipoControl=9 then IFNULL(r.iValor,0) when f.iTipoControl=12 then IFNULL(r.iValor,0)"
					+" when f.iTipoControl=15 then IFNULL(r.iValor,1) when f.iTipoControl=16 then IFNULL(r.iValor,0) else 0 end as iValor,"
					+ " f.iTipoControl,f.iOrden,f.vTabla,f.vCampo,"
					+ "case when f.iTipoControl=2 then IFNULL(r.vResultado,'') when f.iTipoControl=6 then IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =10 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =9 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =8 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =11 then iFNULL(r.vResultado,'') when f.iTipoControl=12 then IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl=13  then IFNULL(r.vResultado,'') when f.iTipoControl=14 THEN IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl=15 then IFNULL(r.vResultado,'')when f.iTipoControl=16 then IFNULL(r.vResultado,'') else '' end  as vResultado,"
					+ "case when f.iTipoControl=8 then IFNULL(r.vObservacion,'') when f.iTipoControl=12 then IFNULL(r.vObservacion,'')"
					+ " when f.iTipoControl=13 then IFNULL(r.vObservacion,'') when f.iTipoControl=15 then ifnull(r.vObservacion,'')"
					+ " when f.iTipoControl=16 then IFNULL(r.vObservacion,'') else '' end as vObservacion, bcheck"
					+ " from SMFORMATO f left join RECORDCARDITEM r on f.iCodFormato =r.iCodFormato "
					+ " and ifnull(r.iRecordCardItemPadre,r.iRecordCardItem)="+iRecordCardItem
					+ " where  f.iCodTipoSeccion in ("+stringArraySecciones
					+") and f.iCodFicha="+iCodFicha+" order by f.iCodTipoSeccion, f.iOrden";
			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				RECORDCARDITEM entidad= new RECORDCARDITEM();

				entidad.iRecordCardItem=fila.getInt(0);
				entidad.iRecordCardItemPadre=fila.getInt(1);
				entidad.iCodFormato=fila.getInt(2);
				entidad.iCodFicha=fila.getInt(3);
				entidad.ordenLlamada= String.valueOf(fila.getInt(4));
				entidad.iCodTipoSeccion=fila.getInt(4);
				entidad.cSeccion=fila.getString(5);
				entidad.vDetalleSeccion=fila.getString(6).trim();
				entidad.iValor=fila.getInt(7);
				entidad.iTipoControl=fila.getInt(8);
				entidad.iOrden=fila.getInt(9);
				entidad.vTabla= fila.getString(10);//.trim();
				entidad.vCampo=fila.getString(11);//.trim();
				entidad.vResultado=fila.getString(12);
				entidad.vObservacion=fila.getString(13);
				entidad.bcheck= fila.getInt(14)==1? true:false;
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static  int ContarTransbordos(Context context, int idFichasGrabadas, String T, int seccion, int iCodFicha)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		if(bd!=null)
		{
			String query="SELECT COUNT(CSECCION) FROM RECORDCARDITEM WHERE ICODFICHA=" +iCodFicha+" AND ICODTIPOSECCION=" +seccion+ " AND  VDETALLESECCION='PLACA' and idfichasgrabadas="+idFichasGrabadas;
			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);
			}
		}
		bd.close();
		return numRows;
	}

	/// AGREGAR FORMATO

	public static  int AgregarDescarga(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento, List<RECORDCARDITEM> listformato )
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{

			numRows = listformato.size();

			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",listformato.get(i).iCodFormato);
				registro.put("iCodFicha",iCodFicha);
				registro.put("ordenllamada",listformato.get(i).ordenLlamada);
				registro.put("iCodTipoSeccion",listformato.get(i).iCodTipoSeccion);
				registro.put("cSeccion",listformato.get(i).cSeccion);
				registro.put("vDetalleSeccion",listformato.get(i).vDetalleSeccion);
				registro.put("iValor",1);
				registro.put("iTipoControl",listformato.get(i).iTipoControl);
				registro.put("iOrden",listformato.get(i).iOrden);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible",1);
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado",listformato.get(i).vResultado);
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",listformato.get(i).bcheck);
				registro.put("bactivo",listformato.get(i).bactivo);
				registro.put("vObservacion",listformato.get(i).vObservacion);


				if(i!=0){
					registro.put("iRecordCardItemPadre",recordCardPadres);
				}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(i==0)
					recordCardPadres=id;
			}
		}
		bd.close();
		return recordCardPadres;

	}

	public static  int AgregarTrasbordo1(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento, List<RECORDCARDITEM> listformato )
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{

			numRows = listformato.size();

			if(listformato.get(2).vResultado.trim().equals("SI"))
			{
				for (int i = 0; i < numRows; ++i)
				{
					if(i!=4 && i!=5 && i!=6 && i!=7 ) {

						ContentValues registro = new ContentValues();
						registro.put("iCodFormato", listformato.get(i).iCodFormato);
						registro.put("iCodFicha", iCodFicha);
						registro.put("ordenllamada", listformato.get(i).ordenLlamada);
						registro.put("iCodTipoSeccion", listformato.get(i).iCodTipoSeccion);
						registro.put("cSeccion", listformato.get(i).cSeccion);
						registro.put("vDetalleSeccion", listformato.get(i).vDetalleSeccion);
						registro.put("iValor", 1);
						registro.put("iTipoControl", listformato.get(i).iTipoControl);
						registro.put("iOrden", listformato.get(i).iOrden);
						//registro.put("vCampo",fila.getString(10));
						//registro.put("vTabla",fila.getString(9));
						//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
						registro.put("bVisible", 1);
						registro.put("cCodEstablecimiento", cCodEstablecimiento);
						registro.put("cCodProveedor", cCodProveedor);
						registro.put("vResultado", listformato.get(i).vResultado);
						registro.put("iIndexCombo", 0);
						registro.put("idFichasGrabadas", idFichasGrabadas);
						registro.put("bcheck", listformato.get(i).bcheck);
						registro.put("bactivo", listformato.get(i).bactivo);
						registro.put("vObservacion", listformato.get(i).vObservacion);
						if (i != 0) {
							registro.put("iRecordCardItemPadre", recordCardPadres);
						}
						//registro.put("iTipoColumna", j);
						int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
						if (i == 0)
							recordCardPadres = id;
					}
				}
			}
			else
			{
				for (int i = 0; i < numRows; ++i)
				{
					if(i!=3 ) {
						ContentValues registro = new ContentValues();
						registro.put("iCodFormato", listformato.get(i).iCodFormato);
						registro.put("iCodFicha", iCodFicha);
						registro.put("ordenllamada", listformato.get(i).ordenLlamada);
						registro.put("iCodTipoSeccion", listformato.get(i).iCodTipoSeccion);
						registro.put("cSeccion", listformato.get(i).cSeccion);
						registro.put("vDetalleSeccion", listformato.get(i).vDetalleSeccion);
						registro.put("iValor", 1);
						registro.put("iTipoControl", listformato.get(i).iTipoControl);
						registro.put("iOrden", listformato.get(i).iOrden);
						//registro.put("vCampo",fila.getString(10));
						//registro.put("vTabla",fila.getString(9));
						//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
						registro.put("bVisible", 1);
						registro.put("cCodEstablecimiento", cCodEstablecimiento);
						registro.put("cCodProveedor", cCodProveedor);
						registro.put("vResultado", listformato.get(i).vResultado);
						registro.put("iIndexCombo", 0);
						registro.put("idFichasGrabadas", idFichasGrabadas);
						registro.put("bcheck", listformato.get(i).bcheck);
						registro.put("bactivo", listformato.get(i).bactivo);
						registro.put("vObservacion", listformato.get(i).vObservacion);
						if (i != 0) {
							registro.put("iRecordCardItemPadre", recordCardPadres);
						}
						//registro.put("iTipoColumna", j);
						int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
						if (i == 0)
							recordCardPadres = id;
					}
				}
			}

		}
		bd.close();
		return recordCardPadres;

	}

	public static  int AgregarVerificacion(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento, List<RECORDCARDITEM> listformato )
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{

			numRows = listformato.size();

			if(listformato.get(6).vResultado.trim().equals("NO"))
			{
				for (int i = 0; i < numRows; ++i)
				{
					if(i!=10 && i!=11 && i!=12 ) {

						ContentValues registro = new ContentValues();
						registro.put("iCodFormato", listformato.get(i).iCodFormato);
						registro.put("iCodFicha", iCodFicha);
						registro.put("ordenllamada", listformato.get(i).ordenLlamada);
						registro.put("iCodTipoSeccion", listformato.get(i).iCodTipoSeccion);
						registro.put("cSeccion", listformato.get(i).cSeccion);
						registro.put("vDetalleSeccion", listformato.get(i).vDetalleSeccion);
						registro.put("iValor", 1);
						registro.put("iTipoControl", listformato.get(i).iTipoControl);
						registro.put("iOrden", listformato.get(i).iOrden);
						//registro.put("vCampo",fila.getString(10));
						//registro.put("vTabla",fila.getString(9));
						//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
						registro.put("bVisible", 1);
						registro.put("cCodEstablecimiento", cCodEstablecimiento);
						registro.put("cCodProveedor", cCodProveedor);
						registro.put("vResultado", listformato.get(i).vResultado);
						registro.put("iIndexCombo", 0);
						registro.put("idFichasGrabadas", idFichasGrabadas);
						registro.put("bcheck", listformato.get(i).bcheck);
						registro.put("bactivo", listformato.get(i).bactivo);
						registro.put("vObservacion", listformato.get(i).vObservacion);
						if (i != 0) {
							registro.put("iRecordCardItemPadre", recordCardPadres);
						}
						//registro.put("iTipoColumna", j);
						int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
						if (i == 0)
							recordCardPadres = id;
					}
				}
			}
			else
			{
				for (int i = 0; i < numRows; ++i)
				{
					if(i!=9 ) {
						ContentValues registro = new ContentValues();
						registro.put("iCodFormato", listformato.get(i).iCodFormato);
						registro.put("iCodFicha", iCodFicha);
						registro.put("ordenllamada", listformato.get(i).ordenLlamada);
						registro.put("iCodTipoSeccion", listformato.get(i).iCodTipoSeccion);
						registro.put("cSeccion", listformato.get(i).cSeccion);
						registro.put("vDetalleSeccion", listformato.get(i).vDetalleSeccion);
						registro.put("iValor", 1);
						registro.put("iTipoControl", listformato.get(i).iTipoControl);
						registro.put("iOrden", listformato.get(i).iOrden);
						//registro.put("vCampo",fila.getString(10));
						//registro.put("vTabla",fila.getString(9));
						//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
						registro.put("bVisible", 1);
						registro.put("cCodEstablecimiento", cCodEstablecimiento);
						registro.put("cCodProveedor", cCodProveedor);
						registro.put("vResultado", listformato.get(i).vResultado);
						registro.put("iIndexCombo", 0);
						registro.put("idFichasGrabadas", idFichasGrabadas);
						registro.put("bcheck", listformato.get(i).bcheck);
						registro.put("bactivo", listformato.get(i).bactivo);
						registro.put("vObservacion", listformato.get(i).vObservacion);
						if (i != 0) {
							registro.put("iRecordCardItemPadre", recordCardPadres);
						}
						//registro.put("iTipoColumna", j);
						int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
						if (i == 0)
							recordCardPadres = id;
					}
				}
			}

		}
		bd.close();
		return recordCardPadres;

	}

	public static List<RECORDCARDITEM> ListarPaginaSeccion2(Context context, int idFichasGrabadas, int[] arraySecciones)
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
					+" p where p.idFichasGrabadas="+idFichasGrabadas+" and iTipoControl=0 and p.iCodTipoSeccion in ("+stringArraySecciones+ ")"
					+" order by iTipoColumna";

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

	//METODOS RAO

	public static  int GetSumaIEERCVerificacion(Context context, int idFichasGrabadas, String T, int tipocontrol, int iCodFicha)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		if(bd!=null)
		{
			String query="select count(cSeccion) from " + NOMBRE_TABLA + " where idFichasGrabadas= " +idFichasGrabadas +
					" and  trim(cSeccion)='"+T+ "' and iTipoControl="+tipocontrol+ " and IcodFicha="+iCodFicha+ " and iCodTipoSeccion=11";

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);
			}
		}
		bd.close();
		return numRows;
	}

	public static  int GetSumaIEERC(Context context, int idFichasGrabadas, String T, int tipocontrol, int iCodFicha)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		if(bd!=null)
		{
			String query="select ifnull(sum(vResultado),0) from " + NOMBRE_TABLA+" where idFichasGrabadas= " +idFichasGrabadas +
					" and  trim(cSeccion)='"+T+ "' and iTipoControl="+tipocontrol+ " and IcodFicha="+iCodFicha;
			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);
			}
		}
		bd.close();
		return numRows;
	}

	public static List<RECORDCARDITEM> ListarPaginaSEG(Context context, int idFichasGrabadas, String[] arraySecciones)
	{
		String stringArraySecciones="";
		for(int i=0;i<arraySecciones.length;i++){
			if(i==0){
				stringArraySecciones= arraySecciones[i];
			}else{
				stringArraySecciones += ","+ arraySecciones[i];
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
					+" p where p.idFichasGrabadas="+idFichasGrabadas+" and iTipoControl=0 and p.iCodTipoSeccion in ("+stringArraySecciones+ ")"
					+" order by iTipoColumna";

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

   //public static List<RECORDCARDITEM> ListarPagina2(Context context,int idFichasGrabadas, String[] arraySecciones)
	public static List<RECORDCARDITEM> ListarPagina2(Context context, int idFichasGrabadas, String arraySecciones)
	{
		String stringArraySecciones="";
		/*for(int i=0;i<arraySecciones.length;i++){
			if(i==0){
				stringArraySecciones= arraySecciones[i];
			}else{
				stringArraySecciones += ","+ arraySecciones[i];
			}
		}*/

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
					+" p where p.idFichasGrabadas="+idFichasGrabadas+" and iTipoControl=0 and p.iCodTipoSeccion in ("+arraySecciones+ ")"
					//+" p where p.idFichasGrabadas="+idFichasGrabadas+" and iTipoControl=0 and p.iCodTipoSeccion in ("+stringArraySecciones+ ")"
					+" order by iTipoColumna";

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
				entidad.iIndexCombo=fila.getInt(14);
				if(fila.getInt(15)==1)
					entidad.bcheck=true;
				if(fila.getInt(16)==1)
					entidad.bactivo=true;
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static List<RECORDCARDITEM> ListarPagina3(Context context, int iCodFicha, String arraySecciones, int iRecordCardItem)
	{
		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select IFNULL(r.iRecordCardItem,0)as iRecordCardItem,IFNULL(IFNULL(r.iRecordCardItemPadre,r.iRecordCardItem),0) as iRecordCardItemPadre, f.iCodFormato,"
					+ "f.iCodFicha,f.iCodTipoSeccion,f.cSeccion,"
					+ "case when f.iTipoControl=1 then IFNULL(r.vDetalleSeccion,f.vDetalleSeccion ) else f.vDetalleSeccion end as vDetalleSeccion,"
					+ "case when f.iTipoControl=8 then IFNULL(r.iValor,1) when f.iTipoControl=9 then IFNULL(r.iValor,0) when f.iTipoControl=12 then IFNULL(r.iValor,0)"
					+" when f.iTipoControl=15 then IFNULL(r.iValor,1) when f.iTipoControl=16 then IFNULL(r.iValor,0) else 0 end as iValor,"
					+ " f.iTipoControl,f.iOrden,f.vTabla,f.vCampo,"
					+ "case when f.iTipoControl=2 then IFNULL(r.vResultado,'') when f.iTipoControl=6 then IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =10 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =9 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =8 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =11 then iFNULL(r.vResultado,'') when f.iTipoControl=12 then IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl=13  then IFNULL(r.vResultado,'') when f.iTipoControl=14 THEN IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl=15 then IFNULL(r.vResultado,'')when f.iTipoControl=16 then IFNULL(r.vResultado,'') else '' end  as vResultado,"
					+ "case when f.iTipoControl=8 then IFNULL(r.vObservacion,'') when f.iTipoControl=12 then IFNULL(r.vObservacion,'')"
					+ " when f.iTipoControl=13 then IFNULL(r.vObservacion,'') when f.iTipoControl=15 then ifnull(r.vObservacion,'')"
					+ " when f.iTipoControl=16 then IFNULL(r.vObservacion,'') else '' end as vObservacion, bcheck"
					+ " from SMFORMATO f left join RECORDCARDITEM r on f.iCodFormato =r.iCodFormato "
					+ " and ifnull(r.iRecordCardItemPadre,r.iRecordCardItem)="+iRecordCardItem
					+ " where  f.iCodTipoSeccion in ("+arraySecciones
					+") and f.iCodFicha="+iCodFicha+" and  f.iTipoControl=0 order by f.iCodTipoSeccion, f.iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				RECORDCARDITEM entidad= new RECORDCARDITEM();

				entidad.iRecordCardItem=fila.getInt(0);
				entidad.iRecordCardItemPadre=fila.getInt(1);
				entidad.iCodFormato=fila.getInt(2);
				entidad.iCodFicha=fila.getInt(3);
				entidad.ordenLlamada= String.valueOf(fila.getInt(4));
				entidad.iCodTipoSeccion=fila.getInt(4);
				entidad.cSeccion=fila.getString(5);
				entidad.vDetalleSeccion=fila.getString(6).trim();
				entidad.iValor=fila.getInt(7);
				entidad.iTipoControl=fila.getInt(8);
				entidad.iOrden=fila.getInt(9);
				entidad.vTabla= fila.getString(10);//.trim();
				entidad.vCampo=fila.getString(11);//.trim();
				entidad.vResultado=fila.getString(12);
				entidad.vObservacion=fila.getString(13);
				entidad.bcheck= fila.getInt(14)==1? true:false;
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static List<RECORDCARDITEM> ListarChecksCausales(Context context, int idFichasGrabadas, String arraySecciones)
	{
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
					+" p where p.idFichasGrabadas="+idFichasGrabadas+" and iTipoControl=1 and p.iCodTipoSeccion in ("+arraySecciones+ ")"
					+" order by iTipoColumna";

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

	public static List<RECORDCARDITEM> ListarElementos(Context context, int idFichasGrabadas, String arraySecciones)
	{
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
					+ NOMBRE_TABLA
					+" p where p.idFichasGrabadas="+idFichasGrabadas+" and iTipoControl in (1,8) and iTipoColumna <> 3 and p.iCodTipoSeccion in ("+arraySecciones+ ")"
					+" order by iTipoColumna";

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

	public static List<RECORDCARDITEM> ListarElementosv2(Context context, int idFichasGrabadas, String arraySecciones, int iRecordCardItem)
	{
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
					+ NOMBRE_TABLA
					+" p where p.idFichasGrabadas="+idFichasGrabadas+" and iTipoControl=1 and p.iOrden in ("+arraySecciones+ ")"
					+" order by iTipoColumna and iRecordCardItemPadre=" + iRecordCardItem;

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

	public static  int AgregarItemsMismaPlaca(Context context, int idFichasGrabadas, int irecordcardpadre, int RCnuevo )
	{
		int numRows=0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		if(bd!=null)
		{
			String query="select idSMFICHAS,iRecordCardPadre,vCodItem,vNItem from SMITEMSTRANSPORTE where irecordcardpadre= " +irecordcardpadre;

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				ContentValues registro=new ContentValues();
				registro.put("idSMFICHAS",fila.getInt(0));
				registro.put("iRecordCardPadre",RCnuevo);
				registro.put("vCodItem",fila.getInt(2));
				registro.put("vNItem",fila.getString(3));


				int id = (int) bd.insert("SMITEMSTRANSPORTE", null, registro);

				fila.moveToNext();
			}
		}
		bd.close();
		return numRows;

	}

	public static  int VerificarCausalesTransporte(Context context, int idFichasGrabadas, int iRC)
	{
		int numRows=0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA+" where iOrden in (13,14,15) and idFichasGrabadas= "+idFichasGrabadas+
					" and bcheck=1  and iRecordCardItemPadre= "+iRC;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{

				numRows= fila.getInt(0);
			}
		}
		bd.close();
		//if(numRows>0)
		//	numRows=-1;
		return numRows;

	}

	public static int AgregarCausalesRaciones(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
	{
		int numRows=0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select iCodBcoPreg,iCodFicha,ordenllamada,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,iTipoColumna from DBQUESTIONARY where (iCodTipoSeccion=5 or iCodTipoSeccion=6) and iCodFicha="+iCodFicha;

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


	public static  int AgregarVerificacionIIRaciones(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
	{
		int numRows=0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion=9 and iTipoControl<>0 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			ArrayList<Integer> recordCardPadres=null;
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3));
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",1);
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",i);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado","");
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",0);
				registro.put("bactivo",1);

				if(i==0){
					rowsHeader=fila.getString(4).split("\\|");
					recordCardPadres=new ArrayList<Integer>();
				}
				for(int j=0; j<rowsHeader.length;j++){
					if(i==0)
					{registro.put("vDetalleSeccion", rowsHeader[j]);

					}
					else
					{
						registro.put("iRecordCardItemPadre",recordCardPadres.get(j));
					}
					//registro.put("iTipoColumna", j);
					int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
					if(i==0)
						recordCardPadres.add(id);

				}



				fila.moveToNext();
			}
		}
		bd.close();
		return numRows;

	}

	public static  int AgregarElabRacionesPanif(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
	{
		int numRows=0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion=9 and iTipoControl>0 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			ArrayList<Integer> recordCardPadres=null;
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3).trim());
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",1);
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",i);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado","");
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",0);
				registro.put("bactivo",1);

				if(i==0){
					rowsHeader=fila.getString(4).split("\\|");
					recordCardPadres=new ArrayList<Integer>();
				}
				for(int j=0; j<rowsHeader.length;j++){
					if(i==0)
					{registro.put("vDetalleSeccion", rowsHeader[j]);

					}
					else
					{
						registro.put("iRecordCardItemPadre",recordCardPadres.get(j));
					}
					//registro.put("iTipoColumna", j);
					int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
					if(i==0)
						recordCardPadres.add(id);

				}
				fila.moveToNext();
			}
		}
		bd.close();
		return numRows;

	}

	public static  int AgregarVerificacionIVRaciones(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion=11 and iTipoControl<>0 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3));
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",1);
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",i);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado","");
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",0);
				registro.put("bactivo",1);


				if(i!=0){
					registro.put("iRecordCardItemPadre",recordCardPadres);
				}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(i==0)
					recordCardPadres=id;


				fila.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;

	}


	public static  int AgregarVerificacionIIIRaciones(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor,
													  String cCodEstablecimiento, String vNombreProducto, int iRecordCardItemReference, String vNroLote)
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion=10 and iTipoControl<>0 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3));
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",1);
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",i);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado","");
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",0);
				registro.put("bactivo",1);
				registro.put("iRecordCardItemReference",iRecordCardItemReference);

				if(i!=0){

					registro.put("iRecordCardItemPadre",recordCardPadres);
					if(fila.getInt(6)==15){
						registro.put("iValor", 3);

					}else{
						if(fila.getString(4).toUpperCase().contains("LOTE"))
							registro.put("vResultado", vNroLote);

					}
				}
				else
				{
					registro.put("vDetalleSeccion",vNombreProducto);
					registro.put("vResultado",vNombreProducto);
					registro.put("bcheck", "1");
				}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(i==0)
					recordCardPadres=id;


				fila.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;

	}

	public static  int AgregarElabRacionesVerif2(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor,
												 String cCodEstablecimiento)
	{
		int numRows=0, numRows0=0, vReference=0;
		String vNroLote="", vCaracteristica="";
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila, fila0;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query0 = "select id, vCaracteristica, vNroLote from MATERIAPRIMA";
			fila0 = bd.rawQuery(query0,null);
			numRows0 = fila0.getCount();

			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion=10 and iTipoControl<>0 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query, null);
			numRows = fila.getCount();

			fila0.moveToFirst();
			for (int j = 0; j < numRows0; ++j){

				vReference = fila0.getInt(0);
				vCaracteristica = fila0.getString(1);
				vNroLote = fila0.getString(2);

				fila.moveToFirst();
				for (int i = 0; i < numRows; ++i) {

					ContentValues registro=new ContentValues();
					registro.put("iCodFormato",fila.getString(0));
					registro.put("iCodFicha",fila.getInt(1));
					registro.put("ordenllamada",fila.getString(2));
					registro.put("iCodTipoSeccion",fila.getInt(2));
					registro.put("cSeccion",fila.getString(3));
					registro.put("vDetalleSeccion",fila.getString(4));
					registro.put("iValor",1);
					registro.put("iTipoControl",fila.getInt(6));
					registro.put("iOrden",i);
					//registro.put("vCampo",fila.getString(10));
					//registro.put("vTabla",fila.getString(9));
					//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
					registro.put("bVisible", fila.getInt(10));
					registro.put("cCodEstablecimiento",cCodEstablecimiento);
					registro.put("cCodProveedor",cCodProveedor);
					registro.put("vResultado","");
					registro.put("iIndexCombo",0);
					registro.put("idFichasGrabadas",idFichasGrabadas);
					registro.put("bcheck",0);
					registro.put("bactivo",1);
					registro.put("iRecordCardItemReference",vReference);

					if(i!=0){
						registro.put("iRecordCardItemPadre",recordCardPadres);
						if(fila.getInt(6)==15){
							registro.put("iValor", 0);

						}else{
							if(fila.getString(4).toUpperCase().contains("LOTE"))
								registro.put("vResultado", vNroLote);

						}
					}
					else
					{
						//registro.put("vDetalleSeccion",fila0.getString(1));
						registro.put("vResultado",vCaracteristica);
						registro.put("bcheck", "1");
					}
					//registro.put("iTipoColumna", j);
					int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
					if(i==0)
						recordCardPadres=id;


					fila.moveToNext();
				}

				fila0.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;

	}

	public static  int AgregarVerificacionVRaciones(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion=12 and iTipoControl<>0 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3));
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",1);
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",i);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado","");
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",0);
				registro.put("bactivo",1);


				if(i!=0){
					registro.put("iRecordCardItemPadre",recordCardPadres);
				}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(i==0)
					recordCardPadres=id;


				fila.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;

	}

	public static  int AgregarElabRacionesVerif3(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento, String stringArrayProduccion)
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

//		String stringArrayProduccion="'0','6','7','8'";
//		for(int i=0;i<arrayProduccion.length;i++){
//			if(arrayProduccion[i].toString().equals("1")){
//				stringArrayProduccion +=",'1','3','4'";
//			}
//			else if(arrayProduccion[i].toString().equals("2")){
//				stringArrayProduccion +=",'2','3'";
//			}
//			else if(arrayProduccion[i].toString().equals("3")){
//				stringArrayProduccion +=",'2','5'";
//			}
//		}

		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion=11 and TRIM(cSeccion) in ("
					+ stringArrayProduccion
					+ ") and iTipoControl<>0 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3));
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",1);
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",i);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado","");
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",0);
				registro.put("bactivo",1);


				if(i!=0){
					registro.put("iRecordCardItemPadre",recordCardPadres);
				}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(i==0)
					recordCardPadres=id;


				fila.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;

	}

	public static  int AgregarElabRacionesVerif1(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion=9 and iTipoControl<>0 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3));
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",1);
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",i);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado","");
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",0);
				registro.put("bactivo",1);


				if(i!=0){
					registro.put("iRecordCardItemPadre",recordCardPadres);
				}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(i==0)
					recordCardPadres=id;


				fila.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;

	}

	public static  int AgregarTransporte(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion=9 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3));
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",1);
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",i);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado","");
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",0);
				registro.put("bactivo",1);


				if(i!=0){
					registro.put("iRecordCardItemPadre",recordCardPadres);
				}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(i==0)
					recordCardPadres=id;


				fila.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;

	}

	public static  int AgregarVerificacionIRaciones(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo, bVisible from SMFORMATO where iCodTipoSeccion=8 and iTipoControl<>0 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3));
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",1);
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",i);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado","");
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",0);
				registro.put("bactivo",1);


				if(i!=0){
					registro.put("iRecordCardItemPadre",recordCardPadres);
				}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(i==0)
					recordCardPadres=id;


				fila.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;

	}


	public static int CantidadCamposVaciosRecordCardItemSupervision(Context context, int idFichasGrabadas){
		int numRows=0;
		int camposVacios=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select COUNT(*) as cantidad FROM RECORDCARDITEM"
					+ " where iTipoColumna=3 and idFichasGrabadas="+idFichasGrabadas
					+" and (iTipoControl=1 and bcheck = 1 and LTRIM(vResultado=''))";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				camposVacios= fila.getInt(0);

				fila.moveToNext();
			}
		}
		bd.close();
		return camposVacios;
	}

	public static int CantidadCamposVaciosRecordCardItemVerificacion(Context context, int iCodFicha, int[] arraySeccion, int iRecordCardItem){
		int numRows=0;
		int camposVacios=0 ;
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
			String query="select COUNT(*) as cantidad FROM RECORDCARDITEM"
					+ " where iCodTipoSeccion in ("+stringArraySecciones+") and iTipoControl<>0 and iCodFicha="+iCodFicha
					+" and  ifnull(iRecordCardItemPadre,iRecordCardItem)="+iRecordCardItem
					+" and ("
					+"(iTipoControl=8 and ((iValor=2 and (LTRIM(vObservacion)='' or vObservacion is null)) or (iValor=2 and (LTRIM(vResultado)='' or vResultado is null)) or iValor=0))"
					//+"or (iTipoControl=4 and ((iValor=2 and (LTRIM(vObservacion)='' or vObservacion is null)) or iValor=0))"
					+" or (iTipoControl=2 and LTRIM(vResultado=''))"
					+" or (iTipoControl=6 and LTRIM(vResultado=''))"
					+" or (iTipoControl=13 and LTRIM(vResultado=''))"
					+" or (iTipoControl=11 and LTRIM(vResultado=''))"
					+" or (iTipoControl=10 and LTRIM(vResultado=''))"
					+" or (iTipoControl=12 and ((LTRIM(vResultado='') or iValor=0) and bcheck=0) )"
					+" or (iTipoControl=14 and (LTRIM(vResultado='') and bcheck=0))"
					+" or (iTipoControl=15 and (LTRIM(vResultado='' and iValor<>3) or( iValor=2 and vObservacion='' )or(iValor=0)))"
					+" or (iTipoControl=16 and (LTRIM(vResultado='') or iValor=0)))";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				camposVacios= fila.getInt(0);

				fila.moveToNext();
			}
		}
		bd.close();
		return camposVacios;
	}

	public static int TamañoDNIT(Context context, String dato, int orden, int iCodFicha, int idFichaGrabada, int prueba) {
		int numRows = 0;
		int cnum = 0;
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;
		if (bd != null) {
			String query = "select length( "+dato+ " ) from RECORDCARDITEM where idFichasGrabadas= "+idFichaGrabada+" and iOrden= "+orden+ " and iCodFicha= "+iCodFicha+
					" and ifnull(iRecordCardItemPadre,iRecordCardItem)="+prueba;
			fila = bd.rawQuery(query, null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				cnum = fila.getInt(0);
				fila.moveToNext();
			}
		}
		bd.close();
		return cnum;
	}

	public static int VerificarFichasenRC(Context context, int iCodFicha, int idFichaGrabada) {

		int numRows = 0;
		int cnum = 0;
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;
		if (bd != null) {
			String query = " select count(*) from RECORDCARDITEM where idFichasGrabadas= " + idFichaGrabada;
			fila = bd.rawQuery(query, null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				cnum = fila.getInt(0);

				fila.moveToNext();
			}
		}

		bd.close();
		return cnum;


	}

	public static List<RECORDCARDITEM> ListarSeccion3(Context context, int idFichasGrabadas, int[] arraySecciones) {
		String stringArraySecciones = "";
		for (int i = 0; i < arraySecciones.length; i++) {
			if (i == 0) {
				stringArraySecciones = String.valueOf(arraySecciones[i]);
			} else {
				stringArraySecciones = stringArraySecciones + "," + String.valueOf(arraySecciones[i]);
			}
		}

		List<RECORDCARDITEM> list = new ArrayList<RECORDCARDITEM>();
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if (bd != null) {
			String query = "select p.iRecordCardItem,p.iCodFormato,p.iCodFicha,p.ordenllamada,p.iCodTipoSeccion,p.cSeccion,p.vDetalleSeccion,p.iValor,p.iTipoControl,p.iOrden,p.cCodEstablecimiento,p.cCodProveedor, "
					+ "case when p.iValor='1 ' then p.vResultado end as vResultado "
					+ ",ifnull(p.iRecordCardItemPadre,p.iRecordCardItem) as iRecordCardItemPadre "
					+ "from RECORDCARDITEM p where p.bVisible=1 and p.idFichasGrabadas= " + idFichasGrabadas + " and p.iCodTipoSeccion = 3 order by iRecordCardItemPadre";

			fila = bd.rawQuery(query, null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				RECORDCARDITEM entidad = new RECORDCARDITEM();
				entidad.iRecordCardItem = fila.getInt(0);
				entidad.iCodBcoPreg = fila.getString(1);
				entidad.iCodFicha = fila.getInt(2);
				entidad.ordenLlamada = fila.getString(3).trim();
				entidad.iCodTipoSeccion = fila.getInt(4);
				entidad.cSeccion = fila.getString(5);
				entidad.vDetalleSeccion = fila.getString(6).trim();
				entidad.iValor = fila.getInt(7);
				entidad.iTipoControl = fila.getInt(8);
				entidad.iOrden = fila.getInt(9);
				//entidad.cCodEstablecimiento=fila.getString(10).trim();
				entidad.cCodProveedor = fila.getString(11).trim();
				entidad.vResultado = fila.getString(12).trim();
				//entidad.vTabla= fila.getString(13);//.trim();
				//entidad.iIndexCombo=fila.getInt(14);
				//if(fila.getInt(15)==1)
				//	entidad.bcheck=true;
				//if(fila.getInt(16)==1)
				//	entidad.bactivo=true;
				//entidad.vCampo=fila.getString(17);//.trim();

				//entidad.iTipoColumna = fila.getInt(18);
				entidad.iRecordCardItemPadre = fila.getInt(13);
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static List<RECORDCARDITEM> ListarSeccion4(Context context, int idFichasGrabadas, int[] arraySecciones) {
		String stringArraySecciones = "";
		for (int i = 0; i < arraySecciones.length; i++) {
			if (i == 0) {
				stringArraySecciones = String.valueOf(arraySecciones[i]);
			} else {
				stringArraySecciones = stringArraySecciones + "," + String.valueOf(arraySecciones[i]);
			}
		}

		List<RECORDCARDITEM> list = new ArrayList<RECORDCARDITEM>();
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if (bd != null) {

			String query = "select p.iRecordCardItem,p.iCodFormato,p.iCodFicha,p.ordenllamada,p.iCodTipoSeccion,p.cSeccion,p.vDetalleSeccion,p.iValor,p.iTipoControl,p.iOrden,p.cCodEstablecimiento,p.cCodProveedor,p.vResultado,"
					+ " p.bcheck,p.bactivo,p.iTipoColumna,p.iRecordCardItemPadre,p.vObservacion, p.bVisible from "
					+ NOMBRE_TABLA + " p  where  p.idFichasGrabadas= " + idFichasGrabadas + " and p.iCodFicha=23  and p.iOrden in(2,10)  order by iRecordCardItemPadre ";


			fila = bd.rawQuery(query, null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				RECORDCARDITEM entidad = new RECORDCARDITEM();
				entidad.iRecordCardItem = fila.getInt(0);
				entidad.iCodBcoPreg = fila.getString(1);
				entidad.iCodFicha = fila.getInt(2);
				entidad.ordenLlamada = fila.getString(3).trim();
				entidad.iCodTipoSeccion = fila.getInt(4);
				entidad.cSeccion = fila.getString(5);
				entidad.vDetalleSeccion = fila.getString(6).trim();
				entidad.iValor = fila.getInt(7);
				entidad.iTipoControl = fila.getInt(8);
				entidad.iOrden = fila.getInt(9);
				//entidad.cCodEstablecimiento=fila.getString(10).trim();
				entidad.cCodProveedor = fila.getString(11).trim();
				entidad.vResultado = fila.getString(12).trim();
				//entidad.vTabla= fila.getString(13);//.trim();
				//entidad.iIndexCombo=fila.getInt(14);
				//if(fila.getInt(15)==1)
				//	entidad.bcheck=true;
				//if(fila.getInt(16)==1)
				//	entidad.bactivo=true;
				//entidad.vCampo=fila.getString(17);//.trim();

				//entidad.iTipoColumna = fila.getInt(18);
				entidad.iRecordCardItemPadre = fila.getInt(16);
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static int InsertarRC(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento, int RC) {
		int numRows = 0;
		int recordCardPadres = 0;
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader = null;
		int indexHeader = 0;
		if (bd != null) {
			String query = "select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,iTipoControl,iOrden,vTabla,vCampo, bVisible  from SMFORMATO where  iCodFicha= "
					+ iCodFicha + " order by iCodTipoSeccion";


			fila = bd.rawQuery(query, null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				ContentValues registro = new ContentValues();
				registro.put("iCodFormato", fila.getString(0));
				registro.put("iCodFicha", fila.getInt(1));
				registro.put("ordenllamada", fila.getString(2));
				registro.put("iCodTipoSeccion", fila.getInt(2));
				registro.put("cSeccion", fila.getString(3));
				registro.put("vDetalleSeccion", fila.getString(4));
				registro.put("iValor", 1);
				registro.put("iTipoControl", fila.getInt(6));
				registro.put("iOrden", i);
				registro.put("vCampo",fila.getString(9));
				registro.put("vTabla",fila.getString(8));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento", cCodEstablecimiento);
				registro.put("cCodProveedor", cCodProveedor);
				registro.put("vResultado", "");
				registro.put("iIndexCombo", 0);
				registro.put("idFichasGrabadas", idFichasGrabadas);
				registro.put("bcheck", 0);
				registro.put("bactivo", 1);
				if (i != 0) {
					registro.put("iRecordCardItemPadre", recordCardPadres);
				}
				registro.put("iRecordCardItemReference", RC);
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if (i == 0)
					recordCardPadres = id;
				fila.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;


	}

	public static List<RECORDCARDITEM> ListarSeccion2(Context context, int prueba, int[] arraySecciones, int idFichasGrabadas) {
		String stringArraySecciones = "";
		for (int i = 0; i < arraySecciones.length; i++) {
			if (i == 0) {
				stringArraySecciones = String.valueOf(arraySecciones[i]);
			} else {
				stringArraySecciones = stringArraySecciones + "," + String.valueOf(arraySecciones[i]);
			}
		}

		List<RECORDCARDITEM> list = new ArrayList<RECORDCARDITEM>();
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if (bd != null) {
			String query = "select p.iRecordCardItem,p.iCodFormato,p.iCodFicha,p.ordenllamada,p.iCodTipoSeccion,p.cSeccion,p.vDetalleSeccion,p.iValor,p.iTipoControl,p.iOrden,p.cCodEstablecimiento,p.cCodProveedor,p.vResultado,"
					+ "	p.bcheck,p.bactivo,p.iTipoColumna,p.iRecordCardItemPadre,p.vObservacion, p.bVisible from "
					+ NOMBRE_TABLA + " p  where p.iRecordCardItemReference= " + prueba + " and idFichasGrabadas= " + idFichasGrabadas + "  order by icodTipoSeccion ";


			fila = bd.rawQuery(query, null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				RECORDCARDITEM entidad = new RECORDCARDITEM();
				entidad.iRecordCardItem = fila.getInt(0);
				entidad.iCodFormato = fila.getInt(1);
				entidad.iCodFicha = fila.getInt(2);
				entidad.ordenLlamada = fila.getString(3).trim();
				entidad.iCodTipoSeccion = fila.getInt(4);
				entidad.cSeccion = fila.getString(5);
				entidad.vDetalleSeccion = fila.getString(6).trim();
				entidad.iValor = fila.getInt(7);
				entidad.iTipoControl = fila.getInt(8);
				entidad.iOrden = fila.getInt(9);
				//	entidad.cCodEstablecimiento=fila.getString(10).trim();
				entidad.cCodProveedor = fila.getString(11).trim();
				entidad.vResultado = fila.getString(12).trim();
				//entidad.vTabla= fila.getString(13);//.trim();
				//entidad.iIndexCombo=fila.getInt(13);
				if (fila.getInt(13) == 1)
					entidad.bcheck = true;
				if (fila.getInt(13) == 0)
					entidad.bcheck = false;
				//if(fila.getInt(16)==1)
				//	entidad.bactivo=true;
				//entidad.vCampo=fila.getString(17);//.trim();

				entidad.iTipoColumna = fila.getInt(15);
				entidad.iRecordCardItemPadre = fila.getInt(16);
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static int GetCuentaCausalesTransporteCheckeados(Context context, int iCodFicha, int idFichaGrabada, int prueba) {
		int numRows=0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null){
			String query="select  count(*) from " + NOMBRE_TABLA+" where iTipoControl = 1  and bcheck=1 and idFichasGrabadas="+idFichaGrabada
					+  " and iCodFicha= "+iCodFicha+" and ifnull(iRecordCardItemPadre,iRecordCardItem)="+prueba;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows= fila.getInt(0);
			}
		}
		bd.close();
		return numRows;
	}

	public  static boolean Conforme(Context context, int idCodFicha, int  idFichasGrabadas, int prueba, String vresultado)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		ContentValues registro = new ContentValues();

		registro.put("vResultado","CONFORME");

		//select  count(*) from recordcarditem where bcheck=1 and icodtiposeccion=4 and idfichasgrabadas= " +idFichaGrabada+ " and irecordcarditemreference=
		int cant = bd.update(NOMBRE_TABLA, registro, "iCodTipoSeccion=3 and iOrden=10 and idFichasGrabadas= " + idFichasGrabadas + " and iRecordCardItemReference= " + prueba + " and iCodFicha= " + idCodFicha, null);
		ContentValues registro2 = new ContentValues();
		registro2.put("vObservacion","1");

		int cant2= bd.update(NOMBRE_TABLA, registro2, "iCodTipoSeccion=3 and iOrden=2 and idFichasGrabadas= " + idFichasGrabadas +  " and iCodFicha= " + idCodFicha +" and vresultado= '"+vresultado+"'", null);
		bd.close();
		if(cant==1 )
			return true;
		else
			return false;
	}
	public  static boolean NoConforme(Context context, int idCodFicha, int  idFichasGrabadas, int prueba, String vresultado)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		ContentValues registro = new ContentValues();

		registro.put("vResultado","NO CONFORME");

		//select  count(*) from recordcarditem where bcheck=1 and icodtiposeccion=4 and idfichasgrabadas= " +idFichaGrabada+ " and irecordcarditemreference=
		int cant = bd.update(NOMBRE_TABLA, registro, "iCodTipoSeccion=3 and iOrden=10 and idFichasGrabadas= "+idFichasGrabadas+" and iRecordCardItemReference= " +prueba+ " and iCodFicha= " +idCodFicha, null);
		ContentValues registro2 = new ContentValues();
		registro2.put("vObservacion","0");
		int cant2= bd.update(NOMBRE_TABLA, registro2, "iCodTipoSeccion=3 and iOrden=2 and idFichasGrabadas= " + idFichasGrabadas +  " and iCodFicha= " + idCodFicha +" and vresultado= '"+vresultado+"'", null);
		bd.close();
		if(cant==1)
			return true;
		else
			return false;
	}

	public static void EliminarRegistroRC(Context context, int iCodFicha, int[] arraySecciones, int iRecordCardItem, int RC, int idFichasGrabadas)
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
			String query="DELETE FROM RECORDCARDITEM "
					+ "where iCodFicha="+ iCodFicha+" and iRecordCardItemReference =  "+RC + " and idFichasGrabadas= "+idFichasGrabadas;

			bd.execSQL(query);


		}
		bd.close();
	}

	public static List<RECORDCARDITEM> BuscarRCPadre(Context context, String placa, int idFichasGrabadas, int iCodFicha) {
		List<RECORDCARDITEM> list = new ArrayList<RECORDCARDITEM>();
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if (bd != null) {
			String query = "select iRecordcarditempadre from RECORDCARDITEM where vresultado='" + placa + "' and idfichasgrabadas= " + idFichasGrabadas +
					" and iCodFicha= "+iCodFicha;

			fila = bd.rawQuery(query, null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				RECORDCARDITEM entidad = new RECORDCARDITEM();
				entidad.iRecordCardItemPadre = fila.getInt(0);
				list.add(entidad);
				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public  static boolean ActualizarFichaVehiculos(Context context, String Resultado, int idFichasGrabadas, int prueba, int idCodFicha, int rc)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		ContentValues registro = new ContentValues();
		registro.put("vResultado",Resultado);
		int cant = bd.update(NOMBRE_TABLA, registro,"idFichasGrabadas= "+idFichasGrabadas+" and iRecordCardItemReference= " +prueba+ " and iCodFicha= " +idCodFicha+
				" and iRecordCardItem= "+rc, null);
		bd.close();
		if(cant==1)
			return true;
		else
			return false;
	}

	public static List<RECORDCARDITEM> ListarSeccion5(Context context, int idFichasGrabadas, int[] arraySecciones) {
		String stringArraySecciones = "";
		for (int i = 0; i < arraySecciones.length; i++) {
			if (i == 0) {
				stringArraySecciones = String.valueOf(arraySecciones[i]);
			} else {
				stringArraySecciones = stringArraySecciones + "," + String.valueOf(arraySecciones[i]);
			}
		}
		List<RECORDCARDITEM> list = new ArrayList<RECORDCARDITEM>();
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if (bd != null) {
			String query = "select p.iRecordCardItem,p.iCodFormato,p.iCodFicha,p.ordenllamada,p.iCodTipoSeccion,p.cSeccion,p.vDetalleSeccion,p.iValor,p.iTipoControl,p.iOrden,p.cCodEstablecimiento,p.cCodProveedor, "
					+ "case when p.iValor='1 ' then p.vResultado end as vResultado "
					+ ",ifnull(p.iRecordCardItemPadre,p.iRecordCardItem) as iRecordCardItemPadre "
					+ "from RECORDCARDITEM p where p.bVisible=1 and p.idFichasGrabadas= " + idFichasGrabadas + " and p.iOrden in(2,10) order by iRecordCardItemPadre";

			fila = bd.rawQuery(query, null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				RECORDCARDITEM entidad = new RECORDCARDITEM();
				entidad.iRecordCardItem = fila.getInt(0);
				entidad.iCodBcoPreg = fila.getString(1);
				entidad.iCodFicha = fila.getInt(2);
				entidad.ordenLlamada = fila.getString(3).trim();
				entidad.iCodTipoSeccion = fila.getInt(4);
				entidad.cSeccion = fila.getString(5);
				entidad.vDetalleSeccion = fila.getString(6).trim();
				entidad.iValor = fila.getInt(7);
				entidad.iTipoControl = fila.getInt(8);
				entidad.iOrden = fila.getInt(9);
				//entidad.cCodEstablecimiento=fila.getString(10).trim();
				entidad.cCodProveedor = fila.getString(11).trim();
				entidad.vResultado = fila.getString(12).trim();
				//entidad.vTabla= fila.getString(13);//.trim();
				//entidad.iIndexCombo=fila.getInt(14);
				//if(fila.getInt(15)==1)
				//	entidad.bcheck=true;
				//if(fila.getInt(16)==1)
				//	entidad.bactivo=true;
				//entidad.vCampo=fila.getString(17);//.trim();

				//entidad.iTipoColumna = fila.getInt(18);
				entidad.iRecordCardItemPadre = fila.getInt(13);
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static List<RECORDCARDITEM> TraerDatosVehiculo(Context context, int RC) {
		List<RECORDCARDITEM> list = new ArrayList<RECORDCARDITEM>();
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if (bd != null) {
			String query = "select  icodTipoSeccion,cSeccion,vDetalleSeccion,iValor,iTipoControl,iOrden,cCodEstablecimiento,vResultado from RECORDCARDITEM where irecordcarditempadre= "
					+RC;

			fila = bd.rawQuery(query, null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				RECORDCARDITEM entidad = new RECORDCARDITEM();
				entidad.iCodTipoSeccion= fila.getInt(0);
				entidad.cSeccion=fila.getString(1);
				entidad.vDetalleSeccion=fila.getString(2);
				entidad.iValor=fila.getInt(3);
				entidad.iTipoControl=fila.getInt(4);
				entidad.iOrden=fila.getInt(5);
				entidad.cCodEstablecimiento=fila.getString(6);
				entidad.vResultado=fila.getString(7);
				list.add(entidad);
				fila.moveToNext();
			}
		}
		bd.close();
		return list;
	}


	public static int TamañoDNI(Context context, int iCodFicha, int idFichaGrabada, int prueba) {
		int numRows = 0;
		int cnum = 0;
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;
		if (bd != null) {
			String query = "select length(vresultado) from RECORDCARDITEM where idFichasGrabadas= "+idFichaGrabada+" and iOrden=8 and iCodFicha= "+iCodFicha+
					" and ifnull(iRecordCardItemPadre,iRecordCardItem)="+prueba;
			fila = bd.rawQuery(query, null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i) {
				cnum = fila.getInt(0);
				fila.moveToNext();
			}
		}
		bd.close();
		return cnum;
	}

	public static int FichasRCactuales(Context context, int iCodFicha, int idFichasGrabadas) {
		int numRows = 0;
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;

		if (bd != null) {
			String query = "select count(distinct(iRecordCardItemReference)) from recordcarditem where icodficha= " + iCodFicha +
					"  and idFichasGrabadas= " + idFichasGrabadas;

			fila = bd.rawQuery(query, null);
			if (fila.moveToFirst()) {
				numRows = fila.getInt(0);
			}


		}
		bd.close();
		return numRows;

	}

	public static int ValidarFicha(Context context, int idFichasGrabadas) {
		int validar = -1;
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;

		if (bd != null) {
			String query = "select   count (distinct  vREsultado)  from RECORDCARDITEM where iCodficha=23 and iORden=2  and idfichasgrabadas= "+idFichasGrabadas;


			fila = bd.rawQuery(query, null);
			if (fila.moveToFirst()) {

				validar = fila.getInt(0);
			}
		}
		bd.close();
		if (validar < 0)
			validar = -1;
		return validar;
	}


	public static int VehiculosConformes(Context context, int idFichasGrabadas) {
		int conformes = -1;
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;

		if (bd != null) {
			String query = "select count (*) from RECORDCARDITEM where iCodficha=23 and vDetalleSeccion='ESTADO' and vRESULTADO='CONFORME'" +
					" and idfichasgrabadas= " + idFichasGrabadas;

			fila = bd.rawQuery(query, null);
			if (fila.moveToFirst()) {

				conformes = fila.getInt(0);
			}
		}
		bd.close();
		if (conformes < 0)
			conformes = -1;
		return conformes;
	}

	public static int VehiculosTotales(Context context, int idFichasGrabadas) {
		int conformes = -1;
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;

		if (bd != null) {
			String query = "select   count (distinct  vResultado)  from RECORDCARDITEM where iCodficha=23  and iOrden=2   and idfichasgrabadas= " + idFichasGrabadas;

			fila = bd.rawQuery(query, null);
			if (fila.moveToFirst()) {

				conformes = fila.getInt(0);
			}
		}
		bd.close();
		if (conformes < 0)
			conformes = -1;
		return conformes;
	}

	public static int VehiculosnoConformes(Context context, int idFichasGrabadas) {
		int conformes = -1;
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;

		if (bd != null) {
			String query = "select  count(distinct vresultado) from RECORDCARDITEM where icodficha=23 and vobservacion='NO CONFORME' and idfichasgrabadas= "+idFichasGrabadas+" and trim(CSeccion)='P'";

			fila = bd.rawQuery(query, null);
			if (fila.moveToFirst()) {

				conformes = fila.getInt(0);
			}
		}
		bd.close();
		if (conformes < 0)
			conformes = -1;
		return conformes;
	}

	public static int VehiculosReevaluados(Context context, int idFichasGrabadas) {
		int conformes = -1;
		SQLite admin = new SQLite(context, null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		Cursor fila;

		if (bd != null) {
/*			String query = " select sum(reevaluados) from (  SELECT   count(distinct(vResultado))-1 as reevaluados "+
					" FROM RECORDCARDITEM where iCodficha=23 and iorden=2 and idFichasGrabadas= " + idFichasGrabadas+
					" GROUP BY vResultado HAVING count(*) > 1) as a";*/

			String query="select count(*) from (select count(vResultado) as P FROM RECORDCARDITEM " +
					"where iCodficha=23 and iorden=2 and idFichasGrabadas= "+idFichasGrabadas+"    group by vResultado) as T where  T.P>1";


			//count( distinct(vResultado))

			fila = bd.rawQuery(query, null);
			if (fila.moveToFirst()) {

				conformes = fila.getInt(0);
			}
		}
		bd.close();
		if (conformes < 0)
			conformes = -1;
		return conformes;
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

	public static  int AgregarComponenteLiberacion(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento, String cTipoRacion )
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select c.iCodFormato,c.iCodFicha,c.iCodTipoSeccion,c.cSeccion,c.vDetalleSeccion,"
					+ " case when c.iTipoControl=9 then (SELECT CAST(ch.vResultado as Integer) FROM COMPONENTELIBERACION ch where ch.iComponenteLiberacion = c.iComponenteLiberacionPadre) else c.iValor end as iValor,"
					+ "c.iTipoControl,c.iOrden,c.bVisible,c.vResultado "
					+" ,c.bCheck,c.bActivo,c.vObservacion,c.iComponenteLiberacion,"
					+" ifnull(c.iComponenteLiberacionPadre,c.iComponenteLiberacion) as iComponenteLiberacionPadre from COMPONENTELIBERACION c"
					+" where c.iCodTipoSeccion=8 and c.iTipoControl<>0 and c.iCodFicha="+iCodFicha +" and c.cCodProveedor='"+cCodProveedor
					+"' and c.cTipoRacion='"+ cTipoRacion+"' order by iComponenteLiberacionPadre,c.iComponenteLiberacion";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3));
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",fila.getInt(5));
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",fila.getInt(7));
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(6));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(8));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				if(fila.getInt(6)==9)
				{
					List<String> listPresentaciones=new ArrayList<String>();
					listPresentaciones.add("SELECCIONE");
					List<String> listUnidades=new ArrayList<String>();
					listUnidades.add("SELECCIONE");

					switch(fila.getInt(5))
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

					registro.put("vResultado", listPresentaciones.get(Integer.valueOf(fila.getString(12).split(",")[0]))
							+" x " + fila.getString(9)+ " "+listUnidades.get(Integer.valueOf(fila.getString(12).split(",")[1]) ));

				}else
				{
					registro.put("vResultado",fila.getString(9));
				}

				registro.put("iIndexCombo",0);
				registro.put("vObservacion",fila.getString(12));
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",fila.getInt(10));
				registro.put("bactivo",fila.getInt(11));



				if(fila.getInt(13)!=fila.getInt(14)){
					registro.put("iRecordCardItemPadre",recordCardPadres);
				}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(fila.getInt(13)==fila.getInt(14))
					recordCardPadres=id;


				fila.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;

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
					+ NOMBRE_TABLA+" where	 bcheck=1 and bactivo=1 and iCodTipoSeccion in (5,6) and idFichasGrabadas="+idFichasGrabadas;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{

				numRows= fila.getInt(0);
			}
		}
		bd.close();
		//if(numRows>0)
		//	   numRows=-1;
		return numRows;
	}


	public static  int GetValidaChecksTransporte(Context context, int idFichasGrabadas, int iRecordCardItemPadre)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA+" where bcheck=1 and bactivo=1 and iOrden>12 and iOrden<16 and idFichasGrabadas="+idFichasGrabadas
					+ " and iRecordCardItemPadre=" + iRecordCardItemPadre;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{

				numRows= fila.getInt(0);
			}
		}
		bd.close();
		//if(numRows>0)
		//	   numRows=-1;
		return numRows;
	}

	public static  int GetValidaCausal_K(Context context, int idFichasGrabadas)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA + " where bcheck=1 and bactivo=1 and iCodTipoSeccion=5 and TRIM(cSeccion)='k' and iCodFicha=21 and idFichasGrabadas="+idFichasGrabadas;

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

	public static int GetValidaChkTipo3(Context context, int idFichasGrabadas, int iCodFicha)
	{
		int numRows0=0, numRows=0;

		String cSeccion = "";
		if (iCodFicha==21) cSeccion = "2.1";
		else cSeccion = "5.1";

		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila0, fila;

		if(bd!=null)
		{
			String query0 = "select  count(*) from "
					+ NOMBRE_TABLA + " where  (iTipoControl=1 and trim(cSeccion) = '" + cSeccion + "' and bactivo=1 and bcheck=1)  and idFichasGrabadas="+idFichasGrabadas;

			fila0=bd.rawQuery(query0,null);
			if (fila0.moveToFirst())
			{

				numRows0= fila0.getInt(0);
			}

			if (numRows0>0){
				String query="select  count(*) from "
						+ NOMBRE_TABLA + " where  iTipoControl=1 and iTipoColumna=3 and bactivo=1 and bcheck=1 and idFichasGrabadas="+idFichasGrabadas;

				fila=bd.rawQuery(query,null);
				if (fila.moveToFirst())
				{

					numRows= fila.getInt(0);
				}
			}
		}
		bd.close();

		if (numRows0==0 && numRows==0){
			return 1;
		}
		else if (numRows0>0 && numRows>0){
			return 1;
		}
		else{
			return 0;
		}
	}

	public static int GetValidaCbo(Context context, int idFichasGrabadas)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA+" where  iTipoControl=8 and iIndexCombo=0 and bactivo=0 and iCodTipoSeccion=7 and idFichasGrabadas="+idFichasGrabadas;

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

	public static  int GetValidaCausalesContractuales(Context context, int idFichasGrabadas)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA+"  where bcheck=1 and  iTipoControl = 1 and iCodTipoSeccion = 6 and idFichasGrabadas="+idFichasGrabadas;

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

	//AGREGADO:
	public static  int GetCuentaCausales(Context context, int idFichasGrabadas)
	{
		int numRows=0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA+" where iCodTipoSeccion in (5,6) and iTipoControl > 0 and idFichasGrabadas="+idFichasGrabadas;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{

				numRows= fila.getInt(0);
			}
		}
		bd.close();
		//if(numRows>0)
		//	numRows=-1;
		return numRows;

	}

	public static  int GetCuentaCausales_Checkeados(Context context, int idFichasGrabadas)
	{
		int numRows=0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA+" where iCodTipoSeccion in (5,6) and bcheck=1 and idFichasGrabadas="+idFichasGrabadas;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{

				numRows= fila.getInt(0);
			}
		}
		bd.close();
		//if(numRows>0)
		//	numRows=-1;
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

	//AGREGADO
	public static  int GetTotalPreguntas_2(Context context, int idFichasGrabadas)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			//NO CUENTA LAS SECCIONES DE CAUSALES, REQUIERE MODIFICAR IDCODSECCION DE "COMPROMISOS D PROVEEDOR EN BD"
			String query="select  count(*) from " + NOMBRE_TABLA + " where  iTipoControl = 1 and iCodTipoSeccion>6 and idFichasGrabadas = "+idFichasGrabadas;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);
			}

		}


		bd.close();

		return numRows;

	}

	public static  int GetTotalPuntos(Context context, int idFichasGrabadas)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select  sum(iValor) from "
					+ NOMBRE_TABLA+" where (iTipoControl=4 or iTipoControl=8 or iTipoControl=9) and iIndexCombo<3  and idFichasGrabadas="+idFichasGrabadas;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);

			}

		}


		bd.close();
		return numRows;

	}


	//AGREGADO
	public static  int GetTotalPuntos_2(Context context, int idFichasGrabadas)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			//SE AGREGA EL TIPO DE CONTROL 1 PARA CHECKBOX'S Y ASÍ CALCULAR EL TOTAL PUNTOS
			String query="select sum(iValor) from " + NOMBRE_TABLA + " where iCodTipoSeccion > 7 and iTipoControl = 1 and idFichasGrabadas = " + idFichasGrabadas;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);

			}

		}


		bd.close();
		return numRows;

	}


	public static  int GetPuntos(Context context, int idFichasGrabadas)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select  sum(iValor) from "
					+ NOMBRE_TABLA+" where  iIndexCombo=1 and (iTipoControl=4 or iTipoControl=8 or iTipoControl=9) and idFichasGrabadas="+idFichasGrabadas;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);
			}

		}


		bd.close();
		return numRows;

	}


	//AGREGADO
	public static  int GetPuntos_2(Context context, int idFichasGrabadas)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			//SE AGREGA EL TIPO DE CONTROL 1 PARA CHECKBOX'S Y ASÍ CONTAR EL TOTAL PUNTOS
			String query="select sum(iValor) from " + NOMBRE_TABLA + " where iCodTipoSeccion > 7 and (iTipoControl = 1 and bcheck=0) and idFichasGrabadas = " + idFichasGrabadas;

			fila=bd.rawQuery(query, null);
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
					+" SMFORMATO p where p.iTipoControl=0 and iCodFicha="+ iCodFicha +" and p.iCodTipoSeccion="+iCodSeccion
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

	public static List<RECORDCARDITEM> ListarPagina(Context context, int idFichasGrabadas, int pInicial, int pFinal)
	{
		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select p.iRecordCardItem,p.iCodBcoPreg,p.iCodFicha,p.ordenllamada,p.iCodTipoSeccion,"
					+ "p.cSeccion,p.vDetalleSeccion,p.iValor,p.iTipoControl,p.iOrden,p.cCodEstablecimiento,"
					+ "p.cCodProveedor,p.vResultado,p.vTabla,p.iIndexCombo,p.bcheck,p.bactivo,p.vCampo, p.iTipoColumna from "
					+ NOMBRE_TABLA  //" p inner join DBQUESTIONARY t on p.iCodBcoPreg = t.iCodBcoPreg "
					+" p where p.idFichasGrabadas="+idFichasGrabadas+" and p.iOrden>="+pInicial+" and p.iOrden<="+pFinal;


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

	public static List<RECORDCARDITEM> ListarSubItems(Context context, int idFichasGrabadas)
	{
		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select p.iRecordCardItem,p.iCodBcoPreg,p.iCodFicha,p.ordenllamada,p.iCodTipoSeccion,"
					+ "p.cSeccion,p.vDetalleSeccion,p.iValor,p.iTipoControl,p.iOrden,p.cCodEstablecimiento,"
					+ "p.cCodProveedor,p.vResultado,p.vTabla,p.iIndexCombo,p.bcheck,p.bactivo,p.vCampo, p.iTipoColumna from "
					+ NOMBRE_TABLA  //" p inner join DBQUESTIONARY t on p.iCodBcoPreg = t.iCodBcoPreg "
					+" p where p.idFichasGrabadas="+idFichasGrabadas+" and p.iTipoColumna=3";


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

	public static List<RECORDCARDITEM> ListarSeccion(Context context, int idFichasGrabadas, int[] arraySecciones)
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
					+ "p.cCodProveedor,"
					+ " case when p.iTipoControl=2 then p.vResultado when p.iTipoControl=9 then p.vResultado when p.iTipoControl=6 then p.vResultado"
					+ " when p.iTipoControl=8 then case when p.iValor=0 then '(SIN VOBO)' when p.iValor=1 then 'CONFORME'"
					+ " when p.iValor=2 then 'NO CONFORME' when p.iValor=3 then 'NO APLICA' else '' end "
					+ " when p.iTipoControl=10 then p.vResultado when p.iTipoControl=1 then p.vResultado "
					+ " when p.iTipoControl=11 then p.vResultado when  p.iTipoControl=12 then (p.vResultado ||' - ' ||"
					+" case when p.iValor=1 then 'Kg' when p.iValor=2 then 'L' else 'No tiene unidad' end"
					+ " )"
					+ " when  p.iTipoControl=16 then (p.vResultado ||' - ' ||"
					+" case when p.iValor=1 then 'Kg' when p.iValor=2 then 'L' else 'No tiene unidad' end"
					+ " ) "
					+ " when p.iTipoControl=13 then p.vResultado "
					+ " when p.iTipoControl=15 then (p.vResultado || ' - ' || case when p.iValor=0 then '(SIN VOBO)'"
					+ " when p.iValor=1 then 'CONFORME' when p.iValor=2 then 'NO CONFORME' when p.iValor=3 then 'NO APLICA' else '' end)else '' end "
					+ " as vResultado"
					+",p.vTabla,p.iIndexCombo,p.bcheck,p.bactivo,p.vCampo, p.iTipoColumna,ifnull(p.iRecordCardItemPadre,p.iRecordCardItem) as iRecordCardItemPadre from "
					+ NOMBRE_TABLA  //" p inner join DBQUESTIONARY t on p.iCodBcoPreg = t.iCodBcoPreg "
					+" p where p.bVisible=1 and p.idFichasGrabadas="+idFichasGrabadas+" and p.iCodTipoSeccion in ("+stringArraySecciones+ ") order by iRecordCardItemPadre";

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
				entidad.vTabla= fila.getString(13);//.trim();
				entidad.iIndexCombo=fila.getInt(14);
				if(fila.getInt(15)==1)
					entidad.bcheck=true;
				if(fila.getInt(16)==1)
					entidad.bactivo=true;
				entidad.vCampo=fila.getString(17);//.trim();

				entidad.iTipoColumna = fila.getInt(18);
				entidad.iRecordCardItemPadre=fila.getInt(19);
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static List<RECORDCARDITEM> ListarRecordCardPadresSeguimiento(Context context,
																		 int idFichasGrabadas, int iCodFicha, int[] arraySecciones)
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
			String query="select p.iRecordCardItem,p.iCodTipoSeccion "
					+" from "
					+ NOMBRE_TABLA  //" p inner join DBQUESTIONARY t on p.iCodBcoPreg = t.iCodBcoPreg "
					+" p where p.bVisible=1 and p.idFichasGrabadas="+idFichasGrabadas+" and p.iCodFicha="+ iCodFicha
					+" and p.iCodTipoSeccion in ("
					+stringArraySecciones+ ") and p.iRecordCardItemPadre is null order by p.iRecordCardItemPadre";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				RECORDCARDITEM entidad= new RECORDCARDITEM();
				entidad.iRecordCardItem=fila.getInt(0);
				entidad.iCodTipoSeccion=fila.getInt(1);
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}


	public static List<RECORDCARDITEM> ListarFormatoItemValores(Context context, int iCodFicha, int[] arraySecciones, int iRecordCardItem)
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
			String query="select IFNULL(r.iRecordCardItem,0)as iRecordCardItem,IFNULL(IFNULL(r.iRecordCardItemPadre,r.iRecordCardItem),0) as iRecordCardItemPadre, f.iCodFormato,"
					+ "f.iCodFicha,f.iCodTipoSeccion,f.cSeccion,"
					+ "case when f.iTipoControl=1 then IFNULL(r.vDetalleSeccion,f.vDetalleSeccion ) else f.vDetalleSeccion end as vDetalleSeccion,"
					+ "case when f.iTipoControl=8 then IFNULL(r.iValor,1) when f.iTipoControl=9 then IFNULL(r.iValor,0) when f.iTipoControl=12 then IFNULL(r.iValor,0)"
					+" when f.iTipoControl=15 then IFNULL(r.iValor,1) when f.iTipoControl=16 then IFNULL(r.iValor,0) else 0 end as iValor,"
					+ " f.iTipoControl,f.iOrden,f.vTabla,f.vCampo,"
					+ "case when f.iTipoControl=2 then IFNULL(r.vResultado,'') when f.iTipoControl=6 then IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =1 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =10 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =9 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =8 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =11 then iFNULL(r.vResultado,'') when f.iTipoControl=12 then IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl=13  then IFNULL(r.vResultado,'') when f.iTipoControl=14 THEN IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl=15 then IFNULL(r.vResultado,'')when f.iTipoControl=16 then IFNULL(r.vResultado,'') else '' end  as vResultado,"
					+ "case when f.iTipoControl=8 then IFNULL(r.vObservacion,'') when f.iTipoControl=12 then IFNULL(r.vObservacion,'')"
					+ " when f.iTipoControl=13 then IFNULL(r.vObservacion,'') when f.iTipoControl=15 then ifnull(r.vObservacion,'')"
					+ " when f.iTipoControl=16 then IFNULL(r.vObservacion,'') else '' end as vObservacion, bcheck"
					+ " from SMFORMATO f left join RECORDCARDITEM r on f.iCodFormato =r.iCodFormato "
					+ " and ifnull(r.iRecordCardItemPadre,r.iRecordCardItem)="+iRecordCardItem
					+ " where  f.iCodTipoSeccion in ("+stringArraySecciones
					+") and f.iTipoControl<>0  and f.iCodFicha="+iCodFicha+" order by f.iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				RECORDCARDITEM entidad= new RECORDCARDITEM();

				entidad.iRecordCardItem=fila.getInt(0);
				entidad.iRecordCardItemPadre=fila.getInt(1);
				entidad.iCodFormato=fila.getInt(2);
				entidad.iCodFicha=fila.getInt(3);
				entidad.ordenLlamada= String.valueOf(fila.getInt(4));
				entidad.iCodTipoSeccion=fila.getInt(4);
				entidad.cSeccion=fila.getString(5);
				entidad.vDetalleSeccion=fila.getString(6).trim();
				entidad.iValor=fila.getInt(7);
				entidad.iTipoControl=fila.getInt(8);
				entidad.iOrden=fila.getInt(9);
				entidad.vTabla= fila.getString(10);//.trim();
				entidad.vCampo=fila.getString(11);//.trim();
				entidad.vResultado=fila.getString(12);
				entidad.vObservacion=fila.getString(13);

				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static List<RECORDCARDITEM> ListarFormatoTransporteValoresv2(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento, int seccion)
	{

		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion="+seccion+ " and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				RECORDCARDITEM entidad= new RECORDCARDITEM();

				//entidad.iRecordCardItem=fila.getInt(0);
				//entidad.iRecordCardItemPadre=fila.getInt(1);

				entidad.iCodFormato=fila.getInt(0);
				entidad.iCodFicha=fila.getInt(1);
				entidad.ordenLlamada= String.valueOf(fila.getInt(2));
				entidad.iCodTipoSeccion=fila.getInt(2);
				entidad.cSeccion=fila.getString(3);
				entidad.vDetalleSeccion=fila.getString(4);
				entidad.iValor=1;
				entidad.iTipoControl=fila.getInt(6);
				entidad.iOrden=i;
				entidad.cCodEstablecimiento=cCodEstablecimiento;
				entidad.cCodProveedor=cCodProveedor;
				entidad.vResultado="";
				//entidad.vTabla=fila.getString(10);//.trim();
				//entidad.vCampo=fila.getString(11);//.trim();
				entidad.iIndexCombo = 0;
				entidad.idFichasGrabadas = idFichasGrabadas;
				entidad.bcheck = false;
				entidad.bactivo = true;
				entidad.vObservacion="";

				list.add(entidad);
				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}


	public static List<RECORDCARDITEM> ListarFormatoTransporteValoresvEdwx(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento, int seccion, int iRecordCardItem)
	{

		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select ifnull(r.iRecordCardItem,0) as iRecordCardItem, f.iCodFormato, f.iCodFicha, f.iCodTipoSeccion, f.cSeccion, f.vDetalleSeccion," +
					" ifnull(r.iValor,1) as iValor, f.iTipoControl, f.iOrden, ifnull(r.vResultado,'') as vResultado, f.vTabla, ifnull(r.iIndexCombo,0) as iIndexCombo,"+
					"  ifnull(r.bcheck,0) as bcheck, ifnull(r.bactivo,1) as bactivo, ifnull(r.vCampo,f.vCampo) as vCampo, ifnull(ifnull(r.iRecordCardItemPadre, r.iRecordCardItem),0) as iRecordCardItemPadre, ifnull(r.vObservacion,'') as vObservacion," +
					" f.bVisible from SMFORMATO f left join RECORDCARDITEM r on f.iCodFormato = r.iCodFormato and ifnull(r.iRecordCardItemPadre,r.iRecordCardItem)="+iRecordCardItem+
					" where f.iCodTipoSeccion in ("+seccion+") and f.iCodFicha="+iCodFicha+" order by f.iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				RECORDCARDITEM entidad= new RECORDCARDITEM();

				entidad.iRecordCardItem=fila.getInt(0);
				entidad.iCodFormato=fila.getInt(1);
				entidad.iCodFicha=fila.getInt(2);
				entidad.ordenLlamada= String.valueOf(fila.getInt(3));
				entidad.iCodTipoSeccion=fila.getInt(3);
				entidad.cSeccion=fila.getString(4);
				entidad.vDetalleSeccion=fila.getString(5);
				entidad.iValor=fila.getInt(6);
				entidad.iTipoControl=fila.getInt(7);
				entidad.iOrden=fila.getInt(8);
				entidad.cCodEstablecimiento=cCodEstablecimiento;
				entidad.cCodProveedor=cCodProveedor;
				entidad.vResultado=fila.getString(9);
				entidad.vTabla= fila.getString(10);//.trim();
				entidad.iIndexCombo = fila.getInt(11);
				entidad.idFichasGrabadas = idFichasGrabadas;
				entidad.bcheck= fila.getInt(12)==1 ? true : false;
				entidad.bactivo = fila.getInt(13)==1 ? true : false;
				entidad.vCampo=fila.getString(14);//.trim();
				entidad.iRecordCardItemPadre=fila.getInt(15);
				entidad.vObservacion=fila.getString(16);

				list.add(entidad);
				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static List<RECORDCARDITEM> ListarFormato3ItemValores(Context context, int iCodFicha, int[] arraySecciones, int iRecordCardItem, String stringArrayProduccion)
	{
		String stringArraySecciones="";
		for(int i=0;i<arraySecciones.length;i++){
			if(i==0){
				stringArraySecciones= String.valueOf(arraySecciones[i]);
			}else{
				stringArraySecciones=stringArraySecciones+","+ String.valueOf(arraySecciones[i]);
			}
		}

//		String stringArrayProduccion="'0','6','7','8'";
//		for(int i=0;i<arrayProduccion.length;i++){
//			if(arrayProduccion[i].toString().equals("1")){
//				stringArrayProduccion +=",'1','3','4'";
//			}
//			else if(arrayProduccion[i].toString().equals("2")){
//				stringArrayProduccion +=",'2','3'";
//			}
//			else if(arrayProduccion[i].toString().equals("3")){
//				stringArrayProduccion +=",'2','5'";
//			}
//		}

		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select IFNULL(r.iRecordCardItem,0)as iRecordCardItem,IFNULL(IFNULL(r.iRecordCardItemPadre,r.iRecordCardItem),0) as iRecordCardItemPadre, f.iCodFormato,"
					+ "f.iCodFicha,f.iCodTipoSeccion,f.cSeccion,"
					+ "case when f.iTipoControl=1 then IFNULL(r.vDetalleSeccion,f.vDetalleSeccion ) else f.vDetalleSeccion end as vDetalleSeccion,"
					+ "case when f.iTipoControl=8 then IFNULL(r.iValor,1) when f.iTipoControl=9 then IFNULL(r.iValor,0) when f.iTipoControl=12 then IFNULL(r.iValor,0)"
					+" when f.iTipoControl=15 then IFNULL(r.iValor,1) when f.iTipoControl=16 then IFNULL(r.iValor,0) else 0 end as iValor,"
					+ " f.iTipoControl,f.iOrden,f.vTabla,f.vCampo,"
					+ "case when f.iTipoControl=2 then IFNULL(r.vResultado,'') when f.iTipoControl=6 then IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =10 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =9 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =8 then iFNULL(r.vResultado,'')"
					+ " when f.iTipoControl =11 then iFNULL(r.vResultado,'') when f.iTipoControl=12 then IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl=13  then IFNULL(r.vResultado,'') when f.iTipoControl=14 THEN IFNULL(r.vResultado,'')"
					+ " when f.iTipoControl=15 then IFNULL(r.vResultado,'')when f.iTipoControl=16 then IFNULL(r.vResultado,'') else '' end  as vResultado,"
					+ "case when f.iTipoControl=8 then IFNULL(r.vObservacion,'') when f.iTipoControl=12 then IFNULL(r.vObservacion,'')"
					+ " when f.iTipoControl=13 then IFNULL(r.vObservacion,'') when f.iTipoControl=15 then ifnull(r.vObservacion,'')"
					+ " when f.iTipoControl=16 then IFNULL(r.vObservacion,'') else '' end as vObservacion, bcheck"
					+ " from SMFORMATO f left join RECORDCARDITEM r on f.iCodFormato =r.iCodFormato "
					+ " and ifnull(r.iRecordCardItemPadre,r.iRecordCardItem)="+iRecordCardItem
					+ " where  f.iCodTipoSeccion in ("+stringArraySecciones
					+") and TRIM(f.cSeccion) in ("+stringArrayProduccion
					+") and f.iTipoControl<>0  and f.iCodFicha="+iCodFicha+" order by f.iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				RECORDCARDITEM entidad= new RECORDCARDITEM();

				entidad.iRecordCardItem=fila.getInt(0);
				entidad.iRecordCardItemPadre=fila.getInt(1);
				entidad.iCodFormato=fila.getInt(2);
				entidad.iCodFicha=fila.getInt(3);
				entidad.ordenLlamada= String.valueOf(fila.getInt(4));
				entidad.iCodTipoSeccion=fila.getInt(4);
				entidad.cSeccion=fila.getString(5);
				entidad.vDetalleSeccion=fila.getString(6).trim();
				entidad.iValor=fila.getInt(7);
				entidad.iTipoControl=fila.getInt(8);
				entidad.iOrden=fila.getInt(9);
				entidad.vTabla= fila.getString(10);//.trim();
				entidad.vCampo=fila.getString(11);//.trim();
				entidad.vResultado=fila.getString(12);
				entidad.vObservacion=fila.getString(13);
				entidad.bcheck= fila.getInt(14)==1? true:false;
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static void EliminarRegistroVerificacion(Context context, int iCodFicha, int[] arraySecciones, int iRecordCardItem)
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
			String query="DELETE FROM RECORDCARDITEM "
					+ "where iCodFicha="+ iCodFicha+" and ifnull(iRecordCardItemPadre,iRecordCardItem)="+iRecordCardItem
					+ " and  iCodTipoSeccion in ("+stringArraySecciones	+")";

			bd.execSQL(query);


		}
		bd.close();
	}

	public static void EliminarRegistroVerificacionv2(Context context, int iCodFicha, int iRecordCardItem)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		int numRows;
		if(bd!=null)
		{
			String query="DELETE FROM RECORDCARDITEM "
					+ "where iCodFicha="+ iCodFicha+" and ifnull(iRecordCardItemPadre,iRecordCardItem)="+iRecordCardItem;

			bd.execSQL(query);


		}
		bd.close();
	}

	public static void EliminarRegistroVerificacionIII(Context context, int iCodFicha, int iRecordCardItem)
	{
		int[] arraySecciones={10};
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
			String query="DELETE FROM RECORDCARDITEM "
					+ "where iCodFicha="+ iCodFicha+" and iRecordCardItemReference="+iRecordCardItem
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


	public static List<RECORDCARDITEM> ListarSupervisionDiaria1(Context context, int idFichasGrabadas)
	{
		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select iRecordCardItem,iCodBcoPreg,iCodFicha,ordenllamada,iCodTipoSeccion,"
					+ "cSeccion,vDetalleSeccion,iValor,iTipoControl,iOrden,cCodEstablecimiento,"
					+ "cCodProveedor,vResultado,vTabla,iIndexCombo,bcheck,bactivo,vCampo from "
					+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas+" and iTipoControl<2";

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

				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}


	public  static boolean Actualizar(Context context, RECORDCARDITEM entidad)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		ContentValues registro = new ContentValues();
		registro.put("vResultado",entidad.vResultado);
		registro.put("iIndexCombo",entidad.iIndexCombo);

		registro.put("bcheck",(true == entidad.bcheck)? 1 : 0);

		registro.put("bactivo",(true == entidad.bactivo)? 1 : 0);
		registro.put("vObservacion",entidad.vObservacion);
		int cant = bd.update(NOMBRE_TABLA, registro, "iRecordCardItem="+entidad.iRecordCardItem, null);


		bd.close();
		if(cant==1)
			return true;
		else
			return false;

	}

	public  static boolean ActualizarEstado(Context context, RECORDCARDITEM entidad)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		ContentValues registro = new ContentValues();
		registro.put("vResultado",entidad.vResultado);
		int cant = bd.update(NOMBRE_TABLA, registro, "iRecordCardItem="+entidad.iRecordCardItem, null);


		bd.close();
		if(cant==1)
			return true;
		else
			return false;

	}


	public  static boolean ActualizarCausales(Context context, RECORDCARDITEM entidad)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		ContentValues registro = new ContentValues();

		registro.put("bcheck",(true == entidad.bcheck)? 1 : 0);
		registro.put("bactivo",(true == entidad.bactivo)? 1 : 0);

		int cant = bd.update(NOMBRE_TABLA, registro, "iRecordCardItem="+entidad.iRecordCardItem, null);


		bd.close();
		if(cant==1)
			return true;
		else
			return false;

	}

	public  static boolean ActualizarVerificacion(Context context, RECORDCARDITEM entidad)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		ContentValues registro = new ContentValues();
		registro.put("vResultado",entidad.vResultado);
		registro.put("iValor",entidad.iValor);
		registro.put("vObservacion",entidad.vObservacion);
		registro.put("iIndexCombo",entidad.iIndexCombo);


		registro.put("bcheck",(true == entidad.bcheck)? 1 : 0);

		registro.put("bactivo",(true == entidad.bactivo)? 1 : 0);
		int cant = bd.update(NOMBRE_TABLA, registro, "iRecordCardItem="+entidad.iRecordCardItem, null);


		bd.close();
		if(cant==1) return true;
		else return false;
	}

	public  static boolean ActualizarVerificacionIII(Context context, String vNombreProducto, String vNroLote, int iRecordCardItemReference)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd = admin.getWritableDatabase();

		boolean flag=true;
		ContentValues registroProd = new ContentValues();
		registroProd.put("vResultado",vNombreProducto);
		registroProd.put("vDetalleSeccion",vNombreProducto);

		int cant = bd.update(NOMBRE_TABLA, registroProd, "iRecordCardItemReference="+iRecordCardItemReference+" and iRecordCardItemPadre is null ", null);

		if(cant==0)
			flag=false;

		ContentValues registroLote = new ContentValues();
		registroLote.put("vResultado",vNroLote);

		cant = bd.update(NOMBRE_TABLA, registroLote, "iRecordCardItemReference="+iRecordCardItemReference+" and vDetalleSeccion like '%Lote%' ", null);

		if(cant==0)
			flag=false;

		bd.close();

		return flag;

	}


	public static List<RECORDCARDITEM> ListarPreguntas(Context context, int idFichasGrabadas)
	{
		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select iRecordCardItem,iCodBcoPreg,iCodFicha,ordenllamada,iCodTipoSeccion,"
					+ "cSeccion,vDetalleSeccion,iValor,iTipoControl,iOrden,cCodEstablecimiento,"
					+ "cCodProveedor,vResultado,vTabla,iIndexCombo,bcheck,bactivo,vCampo from "
					+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas+" and  iTipoControl>0";

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

				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}


	public static List<RECORDCARDITEM> ListarPreguntasRaciones(Context context, int idFichasGrabadas)
	{
		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select iRecordCardItem,IFNULL(iCodFormato,0),iCodFicha,ordenllamada,iCodTipoSeccion,"
					+ "cSeccion,vDetalleSeccion,iValor,iTipoControl,iOrden,cCodEstablecimiento,"
					+ "cCodProveedor,vResultado,IFNULL(vTabla,''),iIndexCombo,bcheck,bactivo,IFNULL(vCampo,''),IFNULL(vObservacion,''), IFNULL(iRecordCardItemPadre,0) from "
					+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas+" and  iTipoControl>0";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				RECORDCARDITEM entidad= new RECORDCARDITEM();
				entidad.iRecordCardItem=fila.getInt(0);
				entidad.iCodFormato=fila.getInt(1);
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
				entidad.vObservacion=fila.getString(18);
				entidad.iRecordCardItemPadre=fila.getInt(19);

				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}
	public static List<RECORDCARDITEM> ListarRecordCardTransporte(Context context, int idFichasGrabadas)
	{
		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select iRecordCardItem,IFNULL(iCodFormato,0),iCodFicha,ordenllamada,iCodTipoSeccion,"
					+ "cSeccion,vDetalleSeccion,iValor,iTipoControl,iOrden,cCodEstablecimiento,"
					+ "cCodProveedor,vResultado,IFNULL(vTabla,''),iIndexCombo,bcheck,bactivo,IFNULL(vCampo,''),IFNULL(vObservacion,''), IFNULL(iRecordCardItemPadre,0) from "
					+ NOMBRE_TABLA+" where idFichasGrabadas="+idFichasGrabadas+" and  iTipoControl>=0";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				RECORDCARDITEM entidad= new RECORDCARDITEM();
				entidad.iRecordCardItem=fila.getInt(0);
				entidad.iCodFormato=fila.getInt(1);
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
				entidad.vObservacion=fila.getString(18);
				entidad.iRecordCardItemPadre=fila.getInt(19);

				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}


	public static  int GetValidaTransportes(Context context, int idFichasGrabadas )
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		//tipo 4 conforme o no conforme tipo

		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA+" where iTipoControl=4 and idFichasGrabadas="+idFichasGrabadas;	       //se cambio de tipo control de 4 a 9

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				int total=fila.getInt(0);

				query="select  count(*) from "
						+ NOMBRE_TABLA+" where iTipoControl=4 and iIndexCombo>0 and idFichasGrabadas="+idFichasGrabadas;

				fila=bd.rawQuery(query,null);
				if (fila.moveToFirst())
				{
					numRows=total-fila.getInt(0);

				}


			}
		}
		return numRows;

	}


	public static  int GetValidaOcurrencias(Context context, int idFichasGrabadas )
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA+" where (iTipoControl=6 or iTipoControl=10) and idFichasGrabadas="+idFichasGrabadas;

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				int total=fila.getInt(0);

				query="select  count(*) from "
						+ NOMBRE_TABLA+" where (iTipoControl=6 or iTipoControl=10) and vResultado!='' and idFichasGrabadas="+idFichasGrabadas;

				fila=bd.rawQuery(query,null);
				if (fila.moveToFirst())
				{
					numRows=total-fila.getInt(0);

				}


			}
		}
		bd.close();
		return numRows;

	}
	/*
     public static  int GetTotalPreguntasTransporte(Context context,int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
        {
         int numRows=0;
           SQLite admin=new SQLite(context,null);
           SQLiteDatabase bd=admin.getWritableDatabase();
           Cursor fila;

            if(bd!=null)
            {
                String  query="select  count(*) from "
                                + NOMBRE_TABLA+" where iTipoControl=9 and cCodProveedor like '"+cCodProveedor+"' and cCodEstablecimiento like '"
                                +cCodEstablecimiento+"' and iCodFicha="+iCodFicha;
                        Log.e("RECORDCARDITEM query",query);

                        fila=bd.rawQuery(query,null);
                        if (fila.moveToFirst())
                        {
                            numRows=fila.getInt(0);
                        }

          }

           bd.close();
           Log.e("RECORDCARDITEM","numRows " + numRows);
           return numRows;
        }

     */
	public static  int GetPreguntasConformesTransporte(Context context, int idFichasGrabadas)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;

		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA+" where (iTipoControl=4 or iTipoControl=8) and bActivo = 1 and iValor=1 and idFichasGrabadas="+idFichasGrabadas;	   // se cambio de 9 a 4

			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);
				query="select  count(*) from "
						+ NOMBRE_TABLA+" where (iTipoControl=4 or iTipoControl=8) and iValor=1 and (iIndexCombo=1 or iIndexCombo=3) and bActivo = 1 and idFichasGrabadas="+idFichasGrabadas;

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

	public static void BorrarRecordFicha(Context context, int idFichasGrabadas){
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		if(bd!=null)
		{
			bd.delete(NOMBRE_TABLA, "idFichasGrabadas="+idFichasGrabadas, null);


		}
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


	/////////////////////////////////////////////////////////////////

	public static  int GetCantidadRC(Context context, int idFichasGrabadas, String T, int tipocontrol, int iCodFicha)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA+" where idFichasGrabadas= " +idFichasGrabadas+ " and  trim(cSeccion)='"+T+ "' and iTipoControl="+tipocontrol+ " and IcodFicha="+iCodFicha;
			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);
			}
		}
		bd.close();
		return numRows;
	}

	public static  int GetCantidadRCporSeccion(Context context, int idFichasGrabadas, int tiposeccion, int tipocontrol, int iCodFicha)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		if(bd!=null)
		{
			String query="select  count(*) from "
					+ NOMBRE_TABLA+" where idFichasGrabadas= " +idFichasGrabadas+ " and iCodTipoSeccion='"+tiposeccion+ "' and iTipoControl="+tipocontrol+ " and IcodFicha="+iCodFicha;
			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);
			}
		}
		bd.close();
		return numRows;
	}




	///////////////////////////////////////////////////////////

	public static List<RECORDCARDITEM> ListarSeccionRutasDescarga(Context context, int idFichasGrabadas, int[] arraySecciones)
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
			String query="select p.iRecordCardItem,p.iCodFormato,p.iCodFicha,p.ordenllamada,p.iCodTipoSeccion,p.cSeccion," +
					"p.vDetalleSeccion,p.iValor,p.iTipoControl,p.iOrden,p.cCodEstablecimiento,p.cCodProveedor,  "
					+" case when p.iTipoControl=2 then p.vResultado "
					+" when p.iTipoControl=9 then p.vResultado  "
					+" when p.iTipoControl=6 then p.vResultado  "
					+" when p.iTipoControl=8 then p.vResultado  "

					+" when p.iTipoControl=10 then p.vResultado  "
					+" when p.iTipoControl=1 then p.vResultado  "
					+" when p.iTipoControl=11 then p.vResultado  "
					+" when  p.iTipoControl=12 then (p.vResultado ||' - ' ||  "
					+" case  "
					+" when p.iValor=1 then 'Kg'  "
					+" when p.iValor=2 then 'L' else 'No tiene unidad'  "
					+" end )  "
					+" when  p.iTipoControl=16 then (p.vResultado ||' - ' ||  "
					+" case  "
					+" 	when p.iValor=1 then 'Kg'  "
					+" when p.iValor=2 then 'L' else 'No tiene unidad'  "
					+" 	end )  "
					+" when p.iTipoControl=13 then p.vResultado  "
					+" 	when p.iTipoControl=15 then (p.vResultado || ' - ' ||  "
					+" case  "
					+" 	when p.iValor=0 then '(SIN VOBO)'  "
					+" when p.iValor=1 then 'CONFORME'  "
					+" when p.iValor=2 then 'NO CONFORME'  "
					+" when p.iValor=3 then 'NO APLICA' else ''  "
					+" end) else ''  "
					+" end  as vResultado,  "

					+" p.vTabla,p.iIndexCombo,p.bcheck,p.bactivo,p.vCampo, p.iTipoColumna,ifnull(p.iRecordCardItemPadre,p.iRecordCardItem) as iRecordCardItemPadre from  "+NOMBRE_TABLA+ " p"
					+" where p.bVisible=1 and p.idFichasGrabadas= " +idFichasGrabadas+ " and p.iCodTipoSeccion in ( " +stringArraySecciones+ " ) order by iRecordCardItemPadre ";

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
				entidad.vTabla= fila.getString(13);//.trim();
				entidad.iIndexCombo=fila.getInt(14);
				if(fila.getInt(15)==1)
					entidad.bcheck=true;
				if(fila.getInt(16)==1)
					entidad.bactivo=true;
				entidad.vCampo=fila.getString(17);//.trim();

				entidad.iTipoColumna = fila.getInt(18);
				entidad.iRecordCardItemPadre=fila.getInt(19);
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static List<RECORDCARDITEM> ListarSeccionRutasDescargav2(Context context, int idFichasGrabadas, int arraySecciones)
	{
		String stringArraySecciones= String.valueOf(arraySecciones);

		List<RECORDCARDITEM> list=new ArrayList<RECORDCARDITEM>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select p.iRecordCardItem,p.iCodFormato,p.iCodFicha,p.ordenllamada,p.iCodTipoSeccion,p.cSeccion," +
					"p.vDetalleSeccion,p.iValor,p.iTipoControl,p.iOrden,p.cCodEstablecimiento,p.cCodProveedor,  "
					+" case when p.iTipoControl=2 then p.vResultado "
					+" when p.iTipoControl=9 then p.vResultado  "
					+" when p.iTipoControl=6 then p.vResultado  "
					+" when p.iTipoControl=8 then p.vResultado  "

					+" when p.iTipoControl=10 then p.vResultado  "
					+" when p.iTipoControl=1 then p.vResultado  "
					+" when p.iTipoControl=11 then p.vResultado  "
					+" when  p.iTipoControl=12 then (p.vResultado ||' - ' ||  "
					+" case  "
					+" when p.iValor=1 then 'Kg'  "
					+" when p.iValor=2 then 'L' else 'No tiene unidad'  "
					+" end )  "
					+" when  p.iTipoControl=16 then (p.vResultado ||' - ' ||  "
					+" case  "
					+" 	when p.iValor=1 then 'Kg'  "
					+" when p.iValor=2 then 'L' else 'No tiene unidad'  "
					+" 	end )  "
					+" when p.iTipoControl=13 then p.vResultado  "
					+" 	when p.iTipoControl=15 then (p.vResultado || ' - ' ||  "
					+" case  "
					+" 	when p.iValor=0 then '(SIN VOBO)'  "
					+" when p.iValor=1 then 'CONFORME'  "
					+" when p.iValor=2 then 'NO CONFORME'  "
					+" when p.iValor=3 then 'NO APLICA' else ''  "
					+" end) else ''  "
					+" end  as vResultado,  "

					+" p.vTabla,p.iIndexCombo,p.bcheck,p.bactivo,p.vCampo, p.iTipoColumna,ifnull(p.iRecordCardItemPadre,p.iRecordCardItem) as iRecordCardItemPadre, p.vObservacion from  "+NOMBRE_TABLA+ " p"
					+" where p.bVisible=1 and p.idFichasGrabadas= " +idFichasGrabadas+ " and p.iCodTipoSeccion in ( " +stringArraySecciones+ " ) order by iRecordCardItemPadre ";

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
				entidad.vTabla= fila.getString(13);//.trim();
				entidad.iIndexCombo=fila.getInt(14);
				if(fila.getInt(15)==1)
					entidad.bcheck=true;
				if(fila.getInt(16)==1)
					entidad.bactivo=true;
				entidad.vCampo=fila.getString(17);//.trim();

				entidad.iTipoColumna = fila.getInt(18);
				entidad.iRecordCardItemPadre=fila.getInt(19);
				entidad.vObservacion = fila.getString(20);
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static List<ItemTransporte> ListarSeccionRutasDescargavEdwx(Context context, int idFichasGrabadas, int arraySecciones)
	{
		String stringArraySecciones= String.valueOf(arraySecciones);

		List<ItemTransporte> list=new ArrayList<ItemTransporte>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select GROUP_CONCAT(p.iRecordCardItem) as iRecordCardItem, p.iCodFormato, p.iCodFicha, p.ordenllamada," +
					" p.iCodTipoSeccion, p.cSeccion, p.vDetalleSeccion, p.iValor, p.iTipoControl, p.iOrden, p.cCodEstablecimiento," +
					" p.cCodProveedor, vResultado, p.vTabla, p.iIndexCombo, p.idFichasGrabadas, p.bcheck, p.bactivo, p.vCampo, p.iTipoColumna," +
					" GROUP_CONCAT(ifnull(p.iRecordCardItemPadre, p.iRecordCardItem)) as iRecordCardItemPadre," +
					" GROUP_CONCAT(p.vObservacion) as vObservacion from RECORDCARDITEM p where p.bVisible=1 and p.idFichasGrabadas="+idFichasGrabadas+
					" and p.iTipoControl = 2 and p.cSeccion like 'P%' and p.iCodTipoSeccion in ("+ arraySecciones+") group by vResultado" +
					" order by iRecordCardItemPadre";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{
				ItemTransporte entidad= new ItemTransporte();
				entidad.iRecordCardItem=fila.getInt(0);
				entidad.iCodFicha=fila.getInt(2);
				entidad.cCodEstablecimiento=fila.getString(10).trim();
				entidad.cCodProveedor=fila.getString(11).trim();
				entidad.vResultado=fila.getString(12).trim();
				entidad.idFichasGrabadas=fila.getInt(15);
				entidad.vCampo = fila.getString(18);
				entidad.iRecordCardItemPadre=fila.getString(20);
				entidad.vObservacion = fila.getString(21);
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public  static boolean ActualizarVEhiculo(Context context, RECORDCARDITEM entidad)
	{
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd = admin.getWritableDatabase();
		ContentValues registro = new ContentValues();
		registro.put("vObservacion",entidad.vObservacion);
		registro.put("iIndexCombo",entidad.iIndexCombo);

		registro.put("bcheck",(true == entidad.bcheck)? 1 : 0);

		registro.put("bactivo",(true == entidad.bactivo)? 1 : 0);
		int cant = bd.update(NOMBRE_TABLA, registro, "iRecordCardItem="+entidad.iRecordCardItem, null);


		bd.close();
		if(cant==1)
			return true;
		else
			return false;

	}

	public static  int AgregarTransporte2(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion=10 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3));
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",1);
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",i);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado","");
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",0);
				registro.put("bactivo",1);


				if(i!=0){
					registro.put("iRecordCardItemPadre",recordCardPadres);
				}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(i==0)
					recordCardPadres=id;


				fila.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;

	}


	public static  int AgregarTransporte3(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento )
	{
		int numRows=0;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			String query="select iCodFormato,iCodFicha,iCodTipoSeccion,cSeccion,vDetalleSeccion,iValor,"
					+ "iTipoControl,iOrden,vTabla,vCampo,bVisible from SMFORMATO where iCodTipoSeccion=11 and iCodFicha="+iCodFicha +" order by iOrden";

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",fila.getString(0));
				registro.put("iCodFicha",fila.getInt(1));
				registro.put("ordenllamada",fila.getString(2));
				registro.put("iCodTipoSeccion",fila.getInt(2));
				registro.put("cSeccion",fila.getString(3));
				registro.put("vDetalleSeccion",fila.getString(4));
				registro.put("iValor",1);
				registro.put("iTipoControl",fila.getInt(6));
				registro.put("iOrden",i);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible", fila.getInt(10));
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado","");
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",0);
				registro.put("bactivo",1);


				if(i!=0){
					registro.put("iRecordCardItemPadre",recordCardPadres);
				}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(i==0)
					recordCardPadres=id;


				fila.moveToNext();
			}
		}
		bd.close();
		return recordCardPadres;

	}


	public static String Colegio(Context context, String colegio) {
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();

		String str_answer="";
		String query = "SELECT vNombre FROM RUTASCOLEGIOS WHERE cCodModular='"+colegio+"'";
		//Log.i("select: ",sql);
		try
		{
			Cursor cursor = bd.rawQuery(query,null); //<< execute here
			cursor.moveToFirst();
			if (cursor.getCount()>0) {
				str_answer=cursor.getString(cursor.getColumnIndex("vNombre"));
			}
		}
		catch (Exception e)
		{

		}
		return str_answer;
	}

	public static  int VerificarPlaca(Context context, int idFichasGrabadas, String T, int tipocontrol, int iCodFicha, String Carro)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		if(bd!=null)
		{
			String query="select count(*) from "+NOMBRE_TABLA+ " where icodficha= "+iCodFicha+ " and  trim(cSeccion)= '" +T+"'  and idFichasGrabadas= "+idFichasGrabadas+ " and iTipoControl= "+tipocontrol
					+ " and trim(vResultado)='"+Carro+"'";


			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);
			}
		}
		bd.close();
		return numRows;
	}

	public static  int VerificarPlacavEdwx(Context context, int idFichasGrabadas, String T, int tipocontrol, int iCodFicha, String Carro)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		if(bd!=null)
		{
			String query="select count(*) from "+NOMBRE_TABLA+ " where icodficha= "+iCodFicha+ " and  trim(cSeccion)= '" +T+"' and vObservacion='100' and idFichasGrabadas= "+idFichasGrabadas+ " and iTipoControl= "+tipocontrol
					+ " and trim(vResultado)='"+Carro+"'";


			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);
			}
		}
		bd.close();
		return numRows;
	}

	public static  int ContarColegios(Context context, int idFichasGrabadas, String T, int tipocontrol, int iCodFicha, String Carro)
	{
		int numRows=-1;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		if(bd!=null)
		{
			String query="select  count(*) from "+NOMBRE_TABLA+ " where icodficha= "+iCodFicha+ " and  trim(cSeccion)= '" +T+"'  and idFichasGrabadas= "+idFichasGrabadas+ " and iTipoControl= "+tipocontrol
					+ "  and trim(vResultado)='"+Carro+"'";


			fila=bd.rawQuery(query,null);
			if (fila.moveToFirst())
			{
				numRows=fila.getInt(0);
			}
		}
		bd.close();
		return numRows;
	}

	public static  int AgregarTransp(Context context, int idFichasGrabadas, int iCodFicha, String cCodProveedor, String cCodEstablecimiento, List<RECORDCARDITEM> listedit) {
		int numRows=0,numRows1,numRows2,numRows3;
		int recordCardPadres=0 ;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		String[] rowsHeader=null;
		int indexHeader=0;
		if(bd!=null)
		{
			numRows = listedit.size();
			for (int i = 0; i < numRows; ++i)
			{
				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",listedit.get(i).iCodFormato);
				registro.put("iCodFicha",iCodFicha);
				registro.put("ordenllamada",listedit.get(i).ordenLlamada);
				registro.put("iCodTipoSeccion",listedit.get(i).iCodTipoSeccion);
				registro.put("cSeccion",listedit.get(i).cSeccion);
				registro.put("vDetalleSeccion",listedit.get(i).vDetalleSeccion);
				registro.put("iValor",1);
				registro.put("iTipoControl",listedit.get(i).iTipoControl);
				registro.put("iOrden",listedit.get(i).iOrden);
				registro.put("vCampo",listedit.get(i).vCampo);
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible",1);
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado",listedit.get(i).vResultado);
				registro.put("iIndexCombo",listedit.get(i).iIndexCombo);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",listedit.get(i).bcheck);
				registro.put("bactivo",listedit.get(i).bactivo);
				registro.put("vObservacion",listedit.get(i).vObservacion);
				if(i!=0){
					registro.put("iRecordCardItemPadre",recordCardPadres);
				}
				//registro.put("iTipoColumna", j);
				final int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				if(i==0)
					recordCardPadres=id;
			}
			/*numRows1 = listcombo.size();

			for (int i = 0; i < numRows1; ++i)
			{
				ContentValues registro=new ContentValues();
				registro.put("iCodFormato",listcombo.get(i).iCodFormato);
				registro.put("iCodFicha",iCodFicha);
				registro.put("ordenllamada",listcombo.get(i).ordenLlamada);
				registro.put("iCodTipoSeccion",listcombo.get(i).iCodTipoSeccion);
				registro.put("cSeccion",listcombo.get(i).cSeccion);
				registro.put("vDetalleSeccion",listcombo.get(i).vDetalleSeccion);
				registro.put("iValor",1);
				registro.put("iTipoControl",listcombo.get(i).iTipoControl);
				registro.put("iOrden",listcombo.get(i).iOrden);
				//registro.put("vCampo",fila.getString(10));
				//registro.put("vTabla",fila.getString(9));
				//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
				registro.put("bVisible",1);
				registro.put("cCodEstablecimiento",cCodEstablecimiento);
				registro.put("cCodProveedor",cCodProveedor);
				registro.put("vResultado",listcombo.get(i).vResultado);
				registro.put("iIndexCombo",0);
				registro.put("idFichasGrabadas",idFichasGrabadas);
				registro.put("bcheck",listcombo.get(i).bcheck);
				registro.put("bactivo",listcombo.get(i).bactivo);
				registro.put("vObservacion",listcombo.get(i).vObservacion);
				//	if(i!=0){
				registro.put("iRecordCardItemPadre",recordCardPadres);
				//	}
				//registro.put("iTipoColumna", j);
				int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
				//	if(i==0)
				//		recordCardPadres=id;
			}*/

			/*numRows2 = listChecks.size();
			for (int i = 0; i < numRows2; ++i) {

				if(listChecks.get(i).bactivo==true)
				{
					ContentValues registro=new ContentValues();
					registro.put("iCodFormato",listChecks.get(i).iCodFormato);
					registro.put("iCodFicha",iCodFicha);
					registro.put("ordenllamada",listChecks.get(i).ordenLlamada);
					registro.put("iCodTipoSeccion",listChecks.get(i).iCodTipoSeccion);
					registro.put("cSeccion",listChecks.get(i).cSeccion);
					registro.put("vDetalleSeccion",listChecks.get(i).vDetalleSeccion);
					registro.put("iValor",1);
					registro.put("iTipoControl",listChecks.get(i).iTipoControl);
					registro.put("iOrden",listChecks.get(i).iOrden);
					//registro.put("vCampo",fila.getString(10));
					//registro.put("vTabla",fila.getString(9));
					//registro.put("iTipoColumna",fila.getString(11));//Agregado  15/04/2015 15:08
					registro.put("bVisible",1);
					registro.put("cCodEstablecimiento",cCodEstablecimiento);
					registro.put("cCodProveedor",cCodProveedor);
					registro.put("vResultado",listChecks.get(i).vResultado);
					registro.put("iIndexCombo",0);
					registro.put("idFichasGrabadas",idFichasGrabadas);
					registro.put("bcheck",listChecks.get(i).bcheck);
					registro.put("bactivo",listChecks.get(i).bactivo);
					registro.put("vObservacion",listChecks.get(i).vObservacion);
					//	if(i!=0){
					registro.put("iRecordCardItemPadre",recordCardPadres);
					//	}
					//registro.put("iTipoColumna", j);
					int id = (int) bd.insert(NOMBRE_TABLA, null, registro);
					//	if(i==0)
					//		recordCardPadres=id;
				}

			}*/


		}
		bd.close();
		return recordCardPadres;

	}
}
