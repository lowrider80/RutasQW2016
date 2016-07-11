package pe.gob.qw.rutas.sqlite;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.gob.qw.rutas.entities.LIBERACIONRECORDCARDITEM;
import pe.gob.qw.rutas.entities.SMDETFOTOS;
import pe.gob.qw.rutas.entities.SMFICHASGRABADAS;
import pe.gob.qw.rutas.entities.SMMAEFICHAS;
import pe.gob.qw.rutas.sqlite.dao.SESION_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMDETFOTOS_DAO;
import pe.gob.qw.rutas.sqlite.dao.SMMAEFICHAS_DAO;

public class setDataServiceLiberacion {
	public static String SendData(Context context, SMFICHASGRABADAS OBJSMFICHASGRABADAS, LIBERACIONRECORDCARDITEM OBJLIBERACIONRECORDCARDITEM) {
		String Data = "";

		try {
			JSONObject objJSON = new JSONObject();
			objJSON.put("iCodFicha", OBJSMFICHASGRABADAS.iCodFicha);
			objJSON.put("iCodDetFicha", OBJSMFICHASGRABADAS.idFichasGrabadas);
			objJSON.put("vCodProveedor", OBJSMFICHASGRABADAS.cCodProveedor);
			objJSON.put("vCodEstablecimiento", OBJSMFICHASGRABADAS.cCodEstablecimiento);

			SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

			objJSON.put("sdtFechaSupervicion", formatted.format(new Date(OBJSMFICHASGRABADAS.lFecha)));
			objJSON.put("sdtFecTermino", formatted.format(new Date(OBJSMFICHASGRABADAS.lFechaFin)));


			objJSON.put("vFechaVigenciaSanidad", formatted.format(new Date(OBJSMFICHASGRABADAS.vFechaVigenciaSanidad)));
			objJSON.put("vFechaVigenciaCertificadoMedico", formatted.format(new Date(OBJSMFICHASGRABADAS.vFechaVigenciaCertificadoMedico)));


			objJSON.put("vEspecialidad", OBJSMFICHASGRABADAS.vEspecialidad);
			objJSON.put("vColegiatura", OBJSMFICHASGRABADAS.vColegiatura);


			objJSON.put("vTipoVehiculo", OBJSMFICHASGRABADAS.vTipoVehiculo);
			objJSON.put("vPlaca", OBJSMFICHASGRABADAS.vPlaca);
			objJSON.put("vGuiaRemision", OBJSMFICHASGRABADAS.vGuiaRemision);


			objJSON.put("dPorcentaje", OBJSMFICHASGRABADAS.dPuntajeFicha);
			//objJSON.put("iPuntaje",(int)(OBJSMFICHASGRABADAS.dPuntajeFicha));
			objJSON.put("iPuntaje", OBJLIBERACIONRECORDCARDITEM.iPuntaje);
			///objJSON.put("vObservacion",OBJSMFICHASGRABADAS.vDatosRelevantes);
			objJSON.put("iTotalOperarios", OBJSMFICHASGRABADAS.iTotalOperarios);
			//objJSON.put("iCodUsuario",Integer.parseInt(SESION_DAO.BuscarUt(context)));//falta
			objJSON.put("vComite", SESION_DAO.BuscarUt(context));


			objJSON.put("iCantidadRacionesAdjudicadas", OBJSMFICHASGRABADAS.iCantidadRacionesAdjudicadas);
			objJSON.put("iCantidadRacionesVerificadas", OBJSMFICHASGRABADAS.iCantidadRacionesVerificadas);
			objJSON.put("vNombreRacionesVerificadas", OBJSMFICHASGRABADAS.vNombreRacionesVerificadas);
			objJSON.put("vTurno", OBJSMFICHASGRABADAS.vTurno);
			objJSON.put("iNumeroOperariosHombre", OBJSMFICHASGRABADAS.iNumeroOperariosHombre);
			objJSON.put("iNumeroOperariosMujer", OBJSMFICHASGRABADAS.iNumeroOperariosMujer);

			objJSON.put("vTelefonoRepresentanteLegal", OBJSMFICHASGRABADAS.vTelefonoRepresentanteLegal);
			objJSON.put("vEmpresaResponsableSanidad", OBJSMFICHASGRABADAS.vEmpresaResponsableSanidad);
			objJSON.put("vEmpresaResponsableMedico", OBJSMFICHASGRABADAS.vEmpresaResponsableMedico);
			objJSON.put("vNumeroCertificadoSanidad", OBJSMFICHASGRABADAS.vNumeroCertificadoSanidad);
			objJSON.put("bCertificadoMedico", OBJSMFICHASGRABADAS.bCertificadoMedico);

			objJSON.put("iNroLiberacion", OBJSMFICHASGRABADAS.iNroLiberacion);
			objJSON.put("bCertificadoMedico", OBJSMFICHASGRABADAS.bCertificadoMedico);

			objJSON.put("vNombreSupervisor", OBJSMFICHASGRABADAS.vNSupervisor);
			objJSON.put("vDNISupervisor", OBJSMFICHASGRABADAS.vDNISupervisor);

			objJSON.put("vNroContrato", OBJSMFICHASGRABADAS.vNroContrato);
			objJSON.put("vNroComiteCompra", OBJSMFICHASGRABADAS.vNroComiteCompra);
			objJSON.put("vItem", OBJSMFICHASGRABADAS.vItem);

			objJSON.put("vObservacion", OBJLIBERACIONRECORDCARDITEM.vResultado);

			SMMAEFICHAS objeto = SMMAEFICHAS_DAO.BuscarId(context, OBJSMFICHASGRABADAS.iCodFicha);

			if (objeto != null) {
				if (objeto.iCodTipoInst == 0)
					objJSON.put("cModalidad", "P");
				else
					objJSON.put("cModalidad", "A");
			}

			JSONArray listJSONObservaciones = new JSONArray();

			JSONObject Observaciones1 = new JSONObject();
			Observaciones1.put("iCodOBSERVACIONES", 1);
			Observaciones1.put("iCodBcoPreg", 1298);
			//Observaciones1.put("iCoDetFicha",OBJSMFICHASGRABADAS.idFichasGrabadas);
			Observaciones1.put("iCoDetFicha", OBJLIBERACIONRECORDCARDITEM.iCodDetFicha);
			Observaciones1.put("vObservacion", "" + OBJLIBERACIONRECORDCARDITEM.iPresentacion);
			listJSONObservaciones.put(Observaciones1);

			JSONObject Observaciones2 = new JSONObject();
			Observaciones2.put("iCodOBSERVACIONES", 2);
			Observaciones2.put("iCodBcoPreg", 1300);
			//Observaciones2.put("iCoDetFicha",OBJSMFICHASGRABADAS.idFichasGrabadas);
			Observaciones2.put("iCoDetFicha", OBJLIBERACIONRECORDCARDITEM.iCodDetFicha);
			Observaciones2.put("vObservacion", "" + OBJLIBERACIONRECORDCARDITEM.iCantidad);
			listJSONObservaciones.put(Observaciones2);

			JSONObject Observaciones3 = new JSONObject();
			Observaciones3.put("iCodOBSERVACIONES", 3);
			Observaciones3.put("iCodBcoPreg", 1292);
			//Observaciones3.put("iCoDetFicha",OBJSMFICHASGRABADAS.idFichasGrabadas);
			Observaciones3.put("iCoDetFicha", OBJLIBERACIONRECORDCARDITEM.iCodDetFicha);
			Observaciones3.put("vObservacion", "" + OBJLIBERACIONRECORDCARDITEM.iCodProducto);
			listJSONObservaciones.put(Observaciones3);

			JSONObject Observaciones4 = new JSONObject();
			Observaciones4.put("iCodOBSERVACIONES", 4);
			Observaciones4.put("iCodBcoPreg", 1297);
			//Observaciones4.put("iCoDetFicha",OBJSMFICHASGRABADAS.idFichasGrabadas);
			Observaciones4.put("iCoDetFicha", OBJLIBERACIONRECORDCARDITEM.iCodDetFicha);
			Observaciones4.put("vObservacion", OBJLIBERACIONRECORDCARDITEM.vNomMarca);
			listJSONObservaciones.put(Observaciones4);
			//listJSONCausales
			objJSON.put("Observaciones", listJSONObservaciones);

			JSONArray listJSONParticipantes = new JSONArray();
			JSONObject Participantes2 = new JSONObject();
			Participantes2.put("iCodDetFicha", OBJSMFICHASGRABADAS.idFichasGrabadas);
			Participantes2.put("cDNI", OBJSMFICHASGRABADAS.vDNIControlCalidad);
			Participantes2.put("vNombres", OBJSMFICHASGRABADAS.vNControlCalidad);
			Participantes2.put("iCodCargo", 5);
			Participantes2.put("iCodParticipante", 1);
			listJSONParticipantes.put(Participantes2);
			objJSON.put("Participantes", listJSONParticipantes);

			JSONArray listJSONFotos = new JSONArray();

			//nueva tabla
			Calendar c = Calendar.getInstance();
			String formattedDate = formatted.format(c.getTime());

			List<SMDETFOTOS> listFotos = SMDETFOTOS_DAO.ListarTodo(context, OBJSMFICHASGRABADAS.idFichasGrabadas);

			if (listFotos.size() > 0) {
				for (int i = 0; i < listFotos.size(); i++) {
					JSONObject Fotos = new JSONObject();
					Fotos.put("iCodDetFicha", OBJSMFICHASGRABADAS.idFichasGrabadas);
					Fotos.put("iCodDetFoto", (i + 1));
					Fotos.put("vNombreFoto", listFotos.get(i).vNombreFoto + ".jpg");
					Fotos.put("vLatitud", "" + listFotos.get(i).vLatitud);
					Fotos.put("vLongitud", "" + listFotos.get(i).vLongitud);
					Fotos.put("dFecReg",formatted.format(new Date(listFotos.get(i).dtFecReg)));
					listJSONFotos.put(Fotos);
				}
			}
			else {
				JSONObject Fotos = new JSONObject();
				Fotos.put("iCodDetFicha", OBJSMFICHASGRABADAS.idFichasGrabadas);
				Fotos.put("iCodDetFoto", 1);
				Fotos.put("vNombreFoto", "none.jpg");
				Fotos.put("vLatitud", "0");
				Fotos.put("vLongitud", "0");
				Fotos.put("dFecReg",formattedDate);
				listJSONFotos.put(Fotos);
			}

			objJSON.put("Fotos", listJSONFotos);

			Data = objJSON.toString();

		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		return Data;
	}
}