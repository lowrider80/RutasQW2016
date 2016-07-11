package pe.gob.qw.rutas.sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.sqlite.SQLite;

public class SMFICHASGRABADAS_DAO {
	private static String NOMBRE_TABLA="SMFICHASGRABADAS";
	private static String NOMBRE_TABLA2="SMFICHASGBOTROS";

	public static int Agregar(Context context, SMFICHASGRABADAS entidad)
	   {
	       int id = 0;
	       SQLite admin=new SQLite(context,null);
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       ContentValues registro=new ContentValues();
	       registro.put("cCodEstablecimiento",entidad.cCodEstablecimiento);  
	       registro.put("cCodProveedor",entidad.cCodProveedor);
	       registro.put("iCodFicha",entidad.iCodFicha);   
	       registro.put("vDescFicha",entidad.vDescFicha);   
	       registro.put("iEstado",entidad.iEstado);
	       registro.put("lFecha",entidad.lFecha);
	       registro.put("lFechaFin",entidad.lFechaFin);
	       registro.put("vNControlCalidad",entidad.vNControlCalidad);
	       registro.put("vDNIControlCalidad",entidad.vDNIControlCalidad);
	       registro.put("vEspecialidad",entidad.vEspecialidad);
	       registro.put("vColegiatura",entidad.vColegiatura);
	       registro.put("vCodPostor",entidad.vCodPostor);
	       
	       registro.put("vNRepresentanteLegal",entidad.vNRepresentanteLegal);	       
	       registro.put("vDNIRepresentanteLegal",entidad.vDNIRepresentanteLegal);
	       
	       registro.put("vNSupervisor",entidad.vNSupervisor);
	       registro.put("vNombresSupervisor",entidad.vNombresSupervisor);
	       registro.put("vApellidosSupervisor",entidad.vApellidosSupervisor);
	       registro.put("vDNISupervisor",entidad.vDNISupervisor);
	       
	       registro.put("vNPNAEQW",entidad.vNPNAEQW);
	       registro.put("vDNIPNAEQW",entidad.vDNIPNAEQW);
	       
	       registro.put("cCategoria",entidad.cCategoria);
	       
	       registro.put("vDatosRelevantes",entidad.vDatosRelevantes);
	       
	       registro.put("iTotalOperarios",entidad.iTotalOperarios);
	       
	       registro.put("dOpcion1",entidad.dOpcion1);
	       
	       registro.put("dOpcion2",entidad.dOpcion2);
	       
	       registro.put("dOpcion3",entidad.dOpcion3);
	       
	       registro.put("dOpcion4",entidad.dOpcion4);
	       
	       registro.put("dPuntajeFicha",entidad.dPuntajeFicha);
	       
	       registro.put("dPuntajeAnexo",entidad.dPuntajeAnexo);
	       
	       registro.put("iTotalPreguntas",entidad.iTotalPreguntas);
	       
	       
	       registro.put("bCalificaFicha",(true == entidad.bCalificaFicha)? 1 : 0);
	       
	       registro.put("bCalificaAnexo",(true == entidad.bCalificaAnexo)? 1 : 0);

	       
	       registro.put("vTipoVehiculo",entidad.vTipoVehiculo);
	       registro.put("vPlaca",entidad.vPlaca);
	       registro.put("vGuiaRemision",entidad.vGuiaRemision);
	       
	       
	       registro.put("iCantidadRacionesAdjudicadas",entidad.iCantidadRacionesAdjudicadas);
	       registro.put("iCantidadRacionesVerificadas",entidad.iCantidadRacionesVerificadas);
	       registro.put("vNombreRacionesVerificadas",entidad.vNombreRacionesVerificadas);
	       registro.put("vTurno",entidad.vTurno);
	       registro.put("iNumeroOperariosHombre",entidad.iNumeroOperariosHombre);
	       registro.put("iNumeroOperariosMujer",entidad.iNumeroOperariosMujer);
	       
	       registro.put("vTelefonoRepresentanteLegal",entidad.vTelefonoRepresentanteLegal);
	       registro.put("vEmpresaResponsableSanidad",entidad.vEmpresaResponsableSanidad);
	       registro.put("vEmpresaResponsableMedico",entidad.vEmpresaResponsableMedico);
	       registro.put("vFechaVigenciaSanidad",entidad.vFechaVigenciaSanidad);
	       registro.put("vFechaVigenciaCertificadoMedico",entidad.vFechaVigenciaCertificadoMedico);
	       registro.put("vNumeroCertificadoSanidad",entidad.vNumeroCertificadoSanidad);
	       registro.put("bCertificadoMedico",(true == entidad.bCertificadoMedico)? 1 : 0);
	       registro.put("iNroLiberacion",entidad.iNroLiberacion);
	       registro.put("iCodContrato",entidad.iCodContrato);
	       registro.put("iCodCronograma",entidad.iCodCronograma);// Se agrego para las liberaciones retroactivas y se tiene que validar no solo por contrato sino tbn por cronogramas
	       
	       
	       registro.put("vNroContrato",entidad.vNroContrato);
	       registro.put("vNroComiteCompra",entidad.vNroComiteCompra);
	       registro.put("vItem",entidad.vItem);
	       
	       registro.put("iTipoEstablecimiento",entidad.iTipoEstablecimiento);

		   registro.put("vObservacion", entidad.vObservacion);
	       
	       
	       id = (int) bd.insert(NOMBRE_TABLA, null, registro);
	       bd.close();    
	       return id;

	   }

	public static List<SMFICHASGRABADAS> ListarUltima(Context context, boolean estado)
	{
		List<SMFICHASGRABADAS> list=new ArrayList<SMFICHASGRABADAS>();
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		Cursor fila;
		int numRows;
		if(bd!=null)
		{
			String query="select idFichasGrabadas,cCodEstablecimiento,cCodProveedor,"
					+ "iCodFicha,iEstado,lFecha,vNControlCalidad,vNRepresentanteLegal,"
					+ "vDNIRepresentanteLegal,vNSupervisor,vDNISupervisor,vNPNAEQW,vDNIPNAEQW,"
					+ "vDescFicha,vDatosRelevantes,iTotalOperarios,dOpcion1,dOpcion2,dOpcion3,dOpcion4,"
					+ "dPuntajeFicha,dPuntajeAnexo,bCalificaFicha,bCalificaAnexo,iTotalPreguntas,vDNIControlCalidad,"
					+ "vEspecialidad,vColegiatura,lFechaFin,cCategoria,vTipoVehiculo,vPlaca,vGuiaRemision,iCantidadRacionesAdjudicadas,"
					+ "iCantidadRacionesVerificadas,vNombreRacionesVerificadas,vTurno,iNumeroOperariosHombre,iNumeroOperariosMujer,"
					+ "vTelefonoRepresentanteLegal,vEmpresaResponsableSanidad,vEmpresaResponsableMedico,vFechaVigenciaSanidad,"
					+ "vFechaVigenciaCertificadoMedico,vNumeroCertificadoSanidad,bCertificadoMedico,iNroLiberacion,iCodContrato,"
					+ "vNroContrato,vNroComiteCompra,vItem,IFNULL(vComite,''),IFNULL(vNombreRacion,''),IFNULL(vNombreEstablecimiento,''),IFNULL(vCantidadSupervisada,'0'),IFNULL(vCantidadProgramada,'0'),"
					+" IFNULL(vObservacion,''),vCodPostor  from "+NOMBRE_TABLA+" where iEstado=0";
			if(estado)
				query+=" and iEstado<4";//4

			fila=bd.rawQuery(query,null);
			numRows = fila.getCount();
			fila.moveToFirst();
			for (int i = 0; i < numRows; ++i)
			{

				SMFICHASGRABADAS entidad=new SMFICHASGRABADAS();

				entidad.idFichasGrabadas=fila.getInt(0);
				entidad.cCodEstablecimiento=fila.getString(1);
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
				entidad.vItem=fila.getString(50);
				entidad.vComite=fila.getString(51);
				entidad.vNombreRacion=fila.getString(52);
				entidad.vNombreEstablecimiento=fila.getString(53);
				entidad.vCantidadSupervisada=fila.getString(54);
				entidad.vCantidadProgramada=fila.getString(55);//52)
				entidad.vObservacion =fila.getString(56);
				entidad.vCodPostor=fila.getString(57);

				entidad.objSMMAEFICHAS= SMMAEFICHAS_DAO.BuscarId(context, entidad.iCodFicha);
				list.add(entidad);

				fila.moveToNext();
			}

		}
		bd.close();
		return list;
	}

	public static int AgregarFSeguimientoRacion(Context context, SMFICHASGRABADAS entidad)
	   {
	       int id = 0;
	       SQLite admin=new SQLite(context,null);
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       ContentValues registro=new ContentValues();
	       registro.put("cCodEstablecimiento",entidad.cCodEstablecimiento);  
	       registro.put("cCodProveedor",entidad.cCodProveedor);
	       registro.put("iCodFicha",entidad.iCodFicha);   
	       registro.put("vDescFicha",entidad.vDescFicha);   
	       registro.put("iEstado",entidad.iEstado);
	       registro.put("lFecha",entidad.lFecha);
	       registro.put("lFechaFin",entidad.lFechaFin);
	       registro.put("vNControlCalidad",entidad.vNControlCalidad);
	       registro.put("vDNIControlCalidad",entidad.vDNIControlCalidad);
	       registro.put("vEspecialidad",entidad.vEspecialidad);
	       registro.put("vColegiatura",entidad.vColegiatura);
	       
	       registro.put("vNRepresentanteLegal",entidad.vNRepresentanteLegal);	       
	       registro.put("vDNIRepresentanteLegal",entidad.vDNIRepresentanteLegal);
	       
	       registro.put("vNSupervisor",entidad.vNSupervisor);
	       registro.put("vDNISupervisor",entidad.vDNISupervisor);
	       
	       registro.put("vNPNAEQW",entidad.vNPNAEQW);
	       registro.put("vDNIPNAEQW",entidad.vDNIPNAEQW);
	       
	       registro.put("cCategoria",entidad.cCategoria);
	       
	       registro.put("vDatosRelevantes",entidad.vDatosRelevantes);
	       
	       registro.put("iTotalOperarios",entidad.iTotalOperarios);
	       
	       registro.put("dOpcion1",entidad.dOpcion1);
	       
	       registro.put("dOpcion2",entidad.dOpcion2);
	       
	       registro.put("dOpcion3",entidad.dOpcion3);
	       
	       registro.put("dOpcion4",entidad.dOpcion4);
	       
	       registro.put("dPuntajeFicha",entidad.dPuntajeFicha);
	       
	       registro.put("dPuntajeAnexo",entidad.dPuntajeAnexo);
	       
	       registro.put("iTotalPreguntas",entidad.iTotalPreguntas);
	       
	       
	       registro.put("bCalificaFicha",(true == entidad.bCalificaFicha)? 1 : 0);
	       
	       registro.put("bCalificaAnexo",(true == entidad.bCalificaAnexo)? 1 : 0);

	       
	       registro.put("vTipoVehiculo",entidad.vTipoVehiculo);
	       registro.put("vPlaca",entidad.vPlaca);
	       registro.put("vGuiaRemision",entidad.vGuiaRemision);
	       
	       
	       registro.put("iCantidadRacionesAdjudicadas",entidad.iCantidadRacionesAdjudicadas);
	       registro.put("iCantidadRacionesVerificadas",entidad.iCantidadRacionesVerificadas);
	       registro.put("vNombreRacionesVerificadas",entidad.vNombreRacionesVerificadas);
	       registro.put("vTurno",entidad.vTurno);
	       registro.put("iNumeroOperariosHombre",entidad.iNumeroOperariosHombre);
	       registro.put("iNumeroOperariosMujer",entidad.iNumeroOperariosMujer);
	       
	       registro.put("vTelefonoRepresentanteLegal",entidad.vTelefonoRepresentanteLegal);
	       registro.put("vEmpresaResponsableSanidad",entidad.vEmpresaResponsableSanidad);
	       registro.put("vEmpresaResponsableMedico",entidad.vEmpresaResponsableMedico);
	       registro.put("vFechaVigenciaSanidad",entidad.vFechaVigenciaSanidad);
	       registro.put("vFechaVigenciaCertificadoMedico",entidad.vFechaVigenciaCertificadoMedico);
	       registro.put("vNumeroCertificadoSanidad",entidad.vNumeroCertificadoSanidad);
	       registro.put("bCertificadoMedico",(true == entidad.bCertificadoMedico)? 1 : 0);
	       registro.put("iNroLiberacion",entidad.iNroLiberacion);
	       registro.put("iCodContrato",entidad.iCodContrato);
	       
	       registro.put("vNroContrato",entidad.vNroContrato);
	       registro.put("vNroComiteCompra",entidad.vNroComiteCompra);
	       registro.put("vItem",entidad.vItem);
	       registro.put("vComite",entidad.vComite);
	       registro.put("vNombreRacion",entidad.vNombreRacion);
	       registro.put("vNombreEstablecimiento", entidad.vNombreEstablecimiento);
	       registro.put("vCantidadSupervisada",entidad.vCantidadSupervisada);
	       registro.put("vCantidadProgramada",entidad.vCantidadProgramada);
	       
	       
	       registro.put("iTipoEstablecimiento",entidad.iTipoEstablecimiento);
	       

	       registro.put("vNombresSupervisor",entidad.vNombresSupervisor);
	       registro.put("vApellidosSupervisor",entidad.vApellidosSupervisor);
	       
	       id = (int) bd.insert(NOMBRE_TABLA, null, registro);
	       bd.close();    
	       return id;

	   }


	public static int AgregarFVerifRacion(Context context, SMFICHASGRABADAS entidad)
	{
		int id = 0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		ContentValues registro=new ContentValues();
		registro.put("cCodEstablecimiento",entidad.cCodEstablecimiento);
		registro.put("cCodProveedor",entidad.cCodProveedor);
		registro.put("iCodFicha",entidad.iCodFicha);
		registro.put("vDescFicha",entidad.vDescFicha);
		registro.put("iEstado",entidad.iEstado);
		registro.put("lFecha",entidad.lFecha);
		registro.put("lFechaFin",entidad.lFechaFin);
		registro.put("vNControlCalidad",entidad.vNControlCalidad);
		registro.put("vDNIControlCalidad",entidad.vDNIControlCalidad);
		registro.put("vEspecialidad",entidad.vEspecialidad);
		registro.put("vColegiatura",entidad.vColegiatura);

		registro.put("vNRepresentanteLegal",entidad.vNRepresentanteLegal);
		registro.put("vDNIRepresentanteLegal",entidad.vDNIRepresentanteLegal);

		registro.put("vNSupervisor",entidad.vNSupervisor);
		registro.put("vDNISupervisor",entidad.vDNISupervisor);

		registro.put("vNPNAEQW",entidad.vNPNAEQW);
		registro.put("vDNIPNAEQW",entidad.vDNIPNAEQW);

		registro.put("cCategoria",entidad.cCategoria);

		registro.put("vDatosRelevantes",entidad.vDatosRelevantes);

		registro.put("iTotalOperarios",entidad.iTotalOperarios);

		registro.put("dOpcion1",entidad.dOpcion1);

		registro.put("dOpcion2",entidad.dOpcion2);

		registro.put("dOpcion3",entidad.dOpcion3);

		registro.put("dOpcion4",entidad.dOpcion4);

		registro.put("dPuntajeFicha",entidad.dPuntajeFicha);

		registro.put("dPuntajeAnexo",entidad.dPuntajeAnexo);

		registro.put("iTotalPreguntas",entidad.iTotalPreguntas);


		registro.put("bCalificaFicha",(true == entidad.bCalificaFicha)? 1 : 0);

		registro.put("bCalificaAnexo",(true == entidad.bCalificaAnexo)? 1 : 0);


		registro.put("vTipoVehiculo",entidad.vTipoVehiculo);
		registro.put("vPlaca",entidad.vPlaca);
		registro.put("vGuiaRemision",entidad.vGuiaRemision);


		registro.put("iCantidadRacionesAdjudicadas",entidad.iCantidadRacionesAdjudicadas);
		registro.put("iCantidadRacionesVerificadas",entidad.iCantidadRacionesVerificadas);
		registro.put("vNombreRacionesVerificadas",entidad.vNombreRacionesVerificadas);
		registro.put("vTurno",entidad.vTurno);
		registro.put("iNumeroOperariosHombre",entidad.iNumeroOperariosHombre);
		registro.put("iNumeroOperariosMujer",entidad.iNumeroOperariosMujer);

		registro.put("vTelefonoRepresentanteLegal",entidad.vTelefonoRepresentanteLegal);
		registro.put("vEmpresaResponsableSanidad",entidad.vEmpresaResponsableSanidad);
		registro.put("vEmpresaResponsableMedico",entidad.vEmpresaResponsableMedico);
		registro.put("vFechaVigenciaSanidad",entidad.vFechaVigenciaSanidad);
		registro.put("vFechaVigenciaCertificadoMedico",entidad.vFechaVigenciaCertificadoMedico);
		registro.put("vNumeroCertificadoSanidad",entidad.vNumeroCertificadoSanidad);
		registro.put("bCertificadoMedico",(true == entidad.bCertificadoMedico)? 1 : 0);
		registro.put("iNroLiberacion",entidad.iNroLiberacion);
		registro.put("iCodContrato",entidad.iCodContrato);

		registro.put("vNroContrato",entidad.vNroContrato);
		registro.put("vNroComiteCompra",entidad.vNroComiteCompra);
		registro.put("vItem",entidad.vItem);
		registro.put("vComite",entidad.vComite);
		registro.put("vNombreRacion",entidad.vNombreRacion);
		registro.put("vNombreEstablecimiento", entidad.vNombreEstablecimiento);
		registro.put("vCantidadSupervisada",entidad.vCantidadSupervisada);
		registro.put("vCantidadProgramada",entidad.vCantidadProgramada);


		registro.put("iTipoEstablecimiento",entidad.iTipoEstablecimiento);


		registro.put("vNombresSupervisor",entidad.vNombresSupervisor);
		registro.put("vApellidosSupervisor",entidad.vApellidosSupervisor);

		id = (int) bd.insert(NOMBRE_TABLA, null, registro);
		bd.close();
		return id;

	}

	public static int AgregarFLiberacionRacion(Context context, SMFICHASGRABADAS entidad)
	   {
	       int id = 0;
	       SQLite admin=new SQLite(context,null);
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       ContentValues registro=new ContentValues();
	       registro.put("cCodEstablecimiento",entidad.cCodEstablecimiento);  
	       registro.put("cCodProveedor",entidad.cCodProveedor);
	       registro.put("iCodFicha",entidad.iCodFicha);   
	       registro.put("vDescFicha",entidad.vDescFicha);   
	       registro.put("iEstado",entidad.iEstado);
	       registro.put("lFecha",entidad.lFecha);
	       registro.put("lFechaFin",entidad.lFechaFin);
	       registro.put("vNControlCalidad",entidad.vNControlCalidad);
	       registro.put("vDNIControlCalidad",entidad.vDNIControlCalidad);
	       registro.put("vEspecialidad",entidad.vEspecialidad);
	       registro.put("vColegiatura",entidad.vColegiatura);
	       
	       registro.put("vNRepresentanteLegal",entidad.vNRepresentanteLegal);	       
	       registro.put("vDNIRepresentanteLegal",entidad.vDNIRepresentanteLegal);
	       
	       registro.put("vNSupervisor",entidad.vNSupervisor);
	       registro.put("vDNISupervisor",entidad.vDNISupervisor);
	       
	       registro.put("vNPNAEQW",entidad.vNPNAEQW);
	       registro.put("vDNIPNAEQW",entidad.vDNIPNAEQW);
	       
	       registro.put("cCategoria",entidad.cCategoria);
	       
	       registro.put("vDatosRelevantes",entidad.vDatosRelevantes);
	       
	       registro.put("iTotalOperarios",entidad.iTotalOperarios);
	       
	       registro.put("dOpcion1",entidad.dOpcion1);
	       
	       registro.put("dOpcion2",entidad.dOpcion2);
	       
	       registro.put("dOpcion3",entidad.dOpcion3);
	       
	       registro.put("dOpcion4",entidad.dOpcion4);
	       
	       registro.put("dPuntajeFicha",entidad.dPuntajeFicha);
	       
	       registro.put("dPuntajeAnexo",entidad.dPuntajeAnexo);
	       
	       registro.put("iTotalPreguntas",entidad.iTotalPreguntas);
	       
	       
	       registro.put("bCalificaFicha",(true == entidad.bCalificaFicha)? 1 : 0);
	       
	       registro.put("bCalificaAnexo",(true == entidad.bCalificaAnexo)? 1 : 0);

	       
	       registro.put("vTipoVehiculo",entidad.vTipoVehiculo);
	       registro.put("vPlaca",entidad.vPlaca);
	       registro.put("vGuiaRemision",entidad.vGuiaRemision);
	       
	       
	       registro.put("iCantidadRacionesAdjudicadas",entidad.iCantidadRacionesAdjudicadas);
	       registro.put("iCantidadRacionesVerificadas",entidad.iCantidadRacionesVerificadas);
	       registro.put("vNombreRacionesVerificadas",entidad.vNombreRacionesVerificadas);
	       registro.put("vTurno",entidad.vTurno);
	       registro.put("iNumeroOperariosHombre",entidad.iNumeroOperariosHombre);
	       registro.put("iNumeroOperariosMujer",entidad.iNumeroOperariosMujer);
	       
	       registro.put("vTelefonoRepresentanteLegal",entidad.vTelefonoRepresentanteLegal);
	       registro.put("vEmpresaResponsableSanidad",entidad.vEmpresaResponsableSanidad);
	       registro.put("vEmpresaResponsableMedico",entidad.vEmpresaResponsableMedico);
	       registro.put("vFechaVigenciaSanidad",entidad.vFechaVigenciaSanidad);
	       registro.put("vFechaVigenciaCertificadoMedico",entidad.vFechaVigenciaCertificadoMedico);
	       registro.put("vNumeroCertificadoSanidad",entidad.vNumeroCertificadoSanidad);
	       registro.put("bCertificadoMedico",(true == entidad.bCertificadoMedico)? 1 : 0);
	       registro.put("iNroLiberacion",entidad.iNroLiberacion);
	       registro.put("iCodContrato",entidad.iCodContrato);
	       
	       registro.put("vNroContrato",entidad.vNroContrato);
	       registro.put("vNroComiteCompra",entidad.vNroComiteCompra);
	       registro.put("vItem",entidad.vItem);
	       registro.put("vComite",entidad.vComite);
	       registro.put("vNombreRacion",entidad.vNombreRacion);
	       registro.put("vNombreEstablecimiento", entidad.vNombreEstablecimiento);
	       registro.put("vCantidadSupervisada",entidad.vCantidadSupervisada);
	       registro.put("vCantidadProgramada",entidad.vCantidadProgramada);
	       
	       
	       registro.put("iTipoEstablecimiento",entidad.iTipoEstablecimiento);
	       registro.put("vNombresSupervisor",entidad.vNombresSupervisor);
	       registro.put("vApellidosSupervisor",entidad.vApellidosSupervisor);
	       
	       
	       id = (int) bd.insert(NOMBRE_TABLA, null, registro);
	       bd.close();    
	       return id;

	   } 
	
	 public static  SMFICHASGRABADAS Buscar(Context context)
     {
		 SMFICHASGRABADAS  entidad=null;
		 SQLite admin=new SQLite(context,null); 
         SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
            String query="select F.idFichasGrabadas,F.cCodEstablecimiento,F.cCodProveedor,"
            		+ "F.iCodFicha,F.iEstado,F.lFecha,F.vNControlCalidad,F.vNRepresentanteLegal,"
            		+ "F.vDNIRepresentanteLegal,F.vNSupervisor,F.vDNISupervisor,F.vNPNAEQW,F.vDNIPNAEQW,"
            		+ "F.vDescFicha,F.vDatosRelevantes,F.iTotalOperarios,F.dOpcion1,F.dOpcion2,F.dOpcion3,F.dOpcion4,"
            		+ "F.dPuntajeFicha,F.dPuntajeAnexo,F.bCalificaFicha,F.bCalificaAnexo,F.iTotalPreguntas,F.vDNIControlCalidad,"
	             	+ "F.vEspecialidad,F.vColegiatura,F.lFechaFin,F.cCategoria,F.vTipoVehiculo,F.vPlaca,F.vGuiaRemision,"
	             	+ "F.iCantidadRacionesAdjudicadas,F.iCantidadRacionesVerificadas,F.vNombreRacionesVerificadas,"
	             	+ "F.vTurno,F.iNumeroOperariosHombre,F.iNumeroOperariosMujer,F.vTelefonoRepresentanteLegal,F.vEmpresaResponsableSanidad,"
	             	+ "F.vEmpresaResponsableMedico,F.vFechaVigenciaSanidad,F.vFechaVigenciaCertificadoMedico,F.vNumeroCertificadoSanidad,"
	             	+ "F.bCertificadoMedico,F.iNroLiberacion,F.iCodContrato,F.vNroContrato,F.vNroComiteCompra,F.vItem , F.iTipoEstablecimiento, "
	             	+ "IFNULL(F.vObservacion,''), F.iCodCronograma,(select fic.cCategoria from SMMAEFICHAS fic where fic.iCodFicha=F.iCodFicha),vCodPostor from "+NOMBRE_TABLA+" F where F.iEstado=0 or F.iEstado=1 or (iEstado=3 and iCodFicha=12)";//  or iEstado = 5 ";// ";//el estado 3 es para las fichas de liberacion
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst()) {
                entidad= new SMFICHASGRABADAS();            
                
                entidad.idFichasGrabadas=fila.getInt(0);
                entidad.cCodEstablecimiento=fila.getString(1);
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
                entidad.vItem=fila.getString(50);
                entidad.iTipoEstablecimiento =fila.getInt(51);
                
                entidad.vObservacion=fila.getString(52);
                entidad.iCodCronograma = fila.getInt(53);
                entidad.cCategoria=fila.getString(54);
                entidad.vCodPostor=fila.getString(55);
                Log.e("DETALLELIBERACION_DAO", "" + entidad.iCodContrato);
            }
        }
        bd.close();   
       
        return entidad;
     }



	public static int AgregarFichaGBOTROS(Context context, SMFICHASGRABADAS entidad, int a, int b, int c, int d, int  plazo, long lfplazo)
	{
		int id = 0;
		SQLite admin=new SQLite(context,null);
		SQLiteDatabase bd=admin.getWritableDatabase();
		ContentValues registro=new ContentValues();
		registro.put("idSMFICHAS",entidad.idFichasGrabadas);
		registro.put("cCodEstablecimiento",entidad.cCodEstablecimiento);
		registro.put("cCodProveedor",entidad.cCodProveedor);
		registro.put("iCodFicha",entidad.iCodFicha);
		registro.put ("iNumDiasPlazo",plazo);
		registro.put("lFechaPlazo",lfplazo);
		registro.put("vNSupervisor",entidad.vNSupervisor);
		registro.put("vDNISupervisor",entidad.vDNISupervisor);
		registro.put("iTipoEstablecimiento",entidad.iTipoEstablecimiento);
		registro.put("iNumConforme",a);
		registro.put("iNumNoConforme",b);
		registro.put("iNumTotalVh",c);
		registro.put("iNumTotalVhRv",d);
		id = (int) bd.insert(NOMBRE_TABLA2, null, registro);
		bd.close();
		return id;
	}

	public static  SMFICHASGRABADAS BuscarSupervisorTransportexProveedor(Context context, String cRucProveedor)
     {
		 SMFICHASGRABADAS  entidad=null;
		 SQLite admin=new SQLite(context,null); 
         SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
            String query="select fic.idFichasGrabadas, fic.vNombresSupervisor,fic.vApellidosSupervisor, fic.vDNISupervisor from "+NOMBRE_TABLA
            		+" fic inner join TABLETPROVEEDORES prov on fic.cCodProveedor =prov.cCodProveedor  "+
            		"where fic.iCodFicha in (7,8) AND prov.cRUC ='"+cRucProveedor+"'";//  or iEstado = 5 ";//or (iEstado=3 and iCodFicha=12) ";//el estado 3 es para las fichas de liberacion
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {    
                entidad= new SMFICHASGRABADAS();            
                
                entidad.idFichasGrabadas=fila.getInt(0);
                entidad.vNombresSupervisor=fila.getString(1);
                entidad.vApellidosSupervisor=fila.getString(2);
                entidad.vDNISupervisor=fila.getString(3);
          
            }
        }
        bd.close();   
       
        return entidad;
     }
	 
	 
	 public static  SMFICHASGRABADAS BuscarFichaRacionxLiberacionFecha(Context context, int iCodLiberacion)
     {
		 SMFICHASGRABADAS  entidad=null;
		 SQLite admin=new SQLite(context,null); 
         SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null)
         {
            String query="select idFichasGrabadas,cCodEstablecimiento,cCodProveedor,"
            		+ "iCodFicha,iEstado,lFecha,vNControlCalidad,vNRepresentanteLegal,"
            		+ "vDNIRepresentanteLegal,vNSupervisor,vDNISupervisor,vNPNAEQW,vDNIPNAEQW,"
            		+ "vDescFicha,vDatosRelevantes,iTotalOperarios,dOpcion1,dOpcion2,dOpcion3,dOpcion4,"
            		+ "dPuntajeFicha,dPuntajeAnexo,bCalificaFicha,bCalificaAnexo,iTotalPreguntas,vDNIControlCalidad,"
	             	+ "vEspecialidad,vColegiatura,lFechaFin,cCategoria,vTipoVehiculo,vPlaca,vGuiaRemision,"
	             	+ "iCantidadRacionesAdjudicadas,iCantidadRacionesVerificadas,vNombreRacionesVerificadas,"
	             	+ "vTurno,iNumeroOperariosHombre,iNumeroOperariosMujer,vTelefonoRepresentanteLegal,vEmpresaResponsableSanidad,"
	             	+ "vEmpresaResponsableMedico,vFechaVigenciaSanidad,vFechaVigenciaCertificadoMedico,vNumeroCertificadoSanidad,"
	             	+ "bCertificadoMedico,iNroLiberacion,iCodContrato,vNroContrato,vNroComiteCompra,vItem , iTipoEstablecimiento, "
	             	+ "IFNULL(vObservacion,''), iCodCronograma, vNombresSupervisor, vApellidosSupervisor from "+NOMBRE_TABLA+" where idFichasGrabadas in "
	             	+ "( select distinct idFichasGrabadas  from ACTACOLEGIOSITEM where (idFichasGrabadas!=0 or idFichasGrabadas is not null) and vFechaLiberacion in "
	             	+ " (  select distinct vFechaLiberacion from ACTACOLEGIOSITEM where iCodLiberacion="+iCodLiberacion+"))";
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst())
            {    
                entidad= new SMFICHASGRABADAS();            
                
                entidad.idFichasGrabadas=fila.getInt(0);
                entidad.cCodEstablecimiento=fila.getString(1);
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
                entidad.vItem=fila.getString(50);
                entidad.iTipoEstablecimiento =fila.getInt(51);
                
                entidad.vObservacion=fila.getString(52);
                entidad.iCodCronograma = fila.getInt(53);
                entidad.vNombresSupervisor=fila.getString(54);
                entidad.vApellidosSupervisor = fila.getString(55);
                
                Log.e("DETALLELIBERACION_DAO", "" + entidad.iCodContrato);
            }
        }
        bd.close();   
       
        return entidad;
     }


	 public  static boolean ActualizarEstado(Context context, int  idFichasGrabadas , int iEstado)
     {
		 
	    		   
		SQLite admin=new SQLite(context,null);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("iEstado",iEstado);
        
        int cant = bd.update(NOMBRE_TABLA, registro, "idFichasGrabadas="+idFichasGrabadas, null);
        bd.close();
        if(cant==1)
            return true;
        else
            return false;
       
    }
	
	 public static boolean Actualizar(Context context, SMFICHASGRABADAS entidad)
	   {
	       SQLite admin=new SQLite(context,null);
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       ContentValues registro=new ContentValues();
	       registro.put("cCodEstablecimiento",entidad.cCodEstablecimiento);  
	       registro.put("cCodProveedor",entidad.cCodProveedor);
	       registro.put("iCodFicha",entidad.iCodFicha);   
	       registro.put("vDescFicha",entidad.vDescFicha);   
	       registro.put("iEstado",entidad.iEstado);
	       registro.put("lFecha",entidad.lFecha);
	       registro.put("lFechaFin",entidad.lFechaFin);
	       registro.put("vNControlCalidad",entidad.vNControlCalidad);
	       registro.put("vDNIControlCalidad",entidad.vDNIControlCalidad);
	       registro.put("vEspecialidad",entidad.vEspecialidad);
	       registro.put("vColegiatura",entidad.vColegiatura);
	       registro.put("vCodPostor",entidad.vCodPostor);
	       
	       registro.put("vNRepresentanteLegal",entidad.vNRepresentanteLegal);	       
	       registro.put("vDNIRepresentanteLegal",entidad.vDNIRepresentanteLegal);
	       
	       registro.put("vNSupervisor",entidad.vNSupervisor);
	       registro.put("vDNISupervisor",entidad.vDNISupervisor);
	       
	       registro.put("vNPNAEQW",entidad.vNPNAEQW);
	       registro.put("vDNIPNAEQW",entidad.vDNIPNAEQW);
	      
	       registro.put("vDatosRelevantes",entidad.vDatosRelevantes);
	       
	       registro.put("cCategoria",entidad.cCategoria);
	            
	       registro.put("iTotalOperarios",entidad.iTotalOperarios);
	       
	       registro.put("dOpcion1",entidad.dOpcion1);
	       
	       registro.put("dOpcion2",entidad.dOpcion2);
	       
	       registro.put("dOpcion3",entidad.dOpcion3);
	       
	       registro.put("dOpcion4",entidad.dOpcion4);
	       
	       registro.put("dPuntajeFicha",entidad.dPuntajeFicha);
	       
	       registro.put("dPuntajeAnexo",entidad.dPuntajeAnexo);
	       
	       registro.put("vTipoVehiculo",entidad.vTipoVehiculo);
	       registro.put("vPlaca",entidad.vPlaca);
	       registro.put("vGuiaRemision",entidad.vGuiaRemision);
	       registro.put("bCalificaFicha",(true == entidad.bCalificaFicha)? 1 : 0);
	       registro.put("bCalificaAnexo",(true == entidad.bCalificaAnexo)? 1 : 0);
	       


	       registro.put("iCantidadRacionesAdjudicadas",entidad.iCantidadRacionesAdjudicadas);
	       registro.put("iCantidadRacionesVerificadas",entidad.iCantidadRacionesVerificadas);
	       registro.put("vNombreRacionesVerificadas",entidad.vNombreRacionesVerificadas);
	       registro.put("vTurno",entidad.vTurno);
	       registro.put("iNumeroOperariosHombre",entidad.iNumeroOperariosHombre);
	       registro.put("iNumeroOperariosMujer",entidad.iNumeroOperariosMujer);
	       
	       registro.put("vTelefonoRepresentanteLegal",entidad.vTelefonoRepresentanteLegal);
	       registro.put("vEmpresaResponsableSanidad",entidad.vEmpresaResponsableSanidad);
	       registro.put("vEmpresaResponsableMedico",entidad.vEmpresaResponsableMedico);
	       registro.put("vFechaVigenciaSanidad",entidad.vFechaVigenciaSanidad);
	       registro.put("vFechaVigenciaCertificadoMedico",entidad.vFechaVigenciaCertificadoMedico);
	       registro.put("vNumeroCertificadoSanidad",entidad.vNumeroCertificadoSanidad);
	       registro.put("bCertificadoMedico",(true == entidad.bCertificadoMedico)? 1 : 0);
	       registro.put("iNroLiberacion",entidad.iNroLiberacion);
	       registro.put("iCodContrato",entidad.iCodContrato);
	       
	       registro.put("vNroContrato",entidad.vNroContrato);
	       registro.put("vNroComiteCompra",entidad.vNroComiteCompra);
	       registro.put("vItem",entidad.vItem);
	       registro.put("vObservacion",entidad.vObservacion);
	       
	       int cant = bd.update(NOMBRE_TABLA, registro, "idFichasGrabadas="+entidad.idFichasGrabadas, null);
	       bd.close();    
	       if(cant==1)
	            return true;
	        else
	            return false;

	   } 

	 public static List<SMFICHASGRABADAS> Listar(Context context, boolean estado)
	    {
	       List<SMFICHASGRABADAS> list=new ArrayList<SMFICHASGRABADAS>();
	       SQLite admin=new SQLite(context,null); 
	       SQLiteDatabase bd=admin.getWritableDatabase();
	       Cursor fila;
	       int numRows;
	        if(bd!=null)
	        {
	        	 String query="select idFichasGrabadas,cCodEstablecimiento,cCodProveedor,"
	             		+ "iCodFicha,iEstado,lFecha,vNControlCalidad,vNRepresentanteLegal,"
	             		+ "vDNIRepresentanteLegal,vNSupervisor,vDNISupervisor,vNPNAEQW,vDNIPNAEQW,"
	             		+ "vDescFicha,vDatosRelevantes,iTotalOperarios,dOpcion1,dOpcion2,dOpcion3,dOpcion4,"
	             		+ "dPuntajeFicha,dPuntajeAnexo,bCalificaFicha,bCalificaAnexo,iTotalPreguntas,vDNIControlCalidad,"
	             		+ "vEspecialidad,vColegiatura,lFechaFin,cCategoria,vTipoVehiculo,vPlaca,vGuiaRemision,iCantidadRacionesAdjudicadas,"
	             		+ "iCantidadRacionesVerificadas,vNombreRacionesVerificadas,vTurno,iNumeroOperariosHombre,iNumeroOperariosMujer,"
	             		+ "vTelefonoRepresentanteLegal,vEmpresaResponsableSanidad,vEmpresaResponsableMedico,vFechaVigenciaSanidad,"
	             		+ "vFechaVigenciaCertificadoMedico,vNumeroCertificadoSanidad,bCertificadoMedico,iNroLiberacion,iCodContrato,"
	             		+ "vNroContrato,vNroComiteCompra,vItem,IFNULL(vComite,''),IFNULL(vNombreRacion,''),IFNULL(vNombreEstablecimiento,''),IFNULL(vCantidadSupervisada,'0'),IFNULL(vCantidadProgramada,'0'),"
	             		+" IFNULL(vObservacion,''),vCodPostor  from "+NOMBRE_TABLA+" where iEstado>1";
	        	 		if(estado)
	        	 			query+=" and iEstado<4";//4
	        	 		
	           fila=bd.rawQuery(query,null);
	           numRows = fila.getCount();   
	           fila.moveToFirst();   
	               for (int i = 0; i < numRows; ++i) 
	               {   

	            	   SMFICHASGRABADAS entidad=new SMFICHASGRABADAS();            
	                   
	                   entidad.idFichasGrabadas=fila.getInt(0);
	                   entidad.cCodEstablecimiento=fila.getString(1);
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
	                   entidad.vItem=fila.getString(50);
	                   entidad.vComite=fila.getString(51);
	                   entidad.vNombreRacion=fila.getString(52);
	                   entidad.vNombreEstablecimiento=fila.getString(53);
	                   entidad.vCantidadSupervisada=fila.getString(54);
	                   entidad.vCantidadProgramada=fila.getString(55);//52)
	                   entidad.vObservacion =fila.getString(56);
	                   entidad.vCodPostor=fila.getString(57);

	                   entidad.objSMMAEFICHAS= SMMAEFICHAS_DAO.BuscarId(context, entidad.iCodFicha);
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
	 
	 public static void BorrarRecordFichaGrabada(Context context, int idFichasGrabadas){
	    	SQLite admin=new SQLite(context,null); 
	        	SQLiteDatabase bd=admin.getWritableDatabase();
	          if(bd!=null)
	          {
	        	  bd.delete(NOMBRE_TABLA, "idFichasGrabadas="+idFichasGrabadas, null);
	     	   
	             
	         }
	          bd.close();
	     }
	 
	 public static  SMFICHASGRABADAS ExisteFicha(Context context, SMFICHASGRABADAS entidadResult) {
		 SMFICHASGRABADAS  entidad= null;
		 SQLite admin=new SQLite(context,null); 
         SQLiteDatabase bd=admin.getWritableDatabase();
         if(bd!=null) {
            String query="select idFichasGrabadas  from "+NOMBRE_TABLA+" where cCodEstablecimiento="+entidadResult.cCodEstablecimiento+" AND cCodProveedor= "+entidadResult.cCodProveedor+" AND iCodFicha="+entidadResult.iCodFicha;
            Cursor fila=bd.rawQuery(query,null);
            if (fila.moveToFirst()) {
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
