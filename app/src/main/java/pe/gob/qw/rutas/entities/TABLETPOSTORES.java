package pe.gob.qw.rutas.entities;

//import com.google.gson.annotations.SerializedName;

public class TABLETPOSTORES 
{
	public int iCodConvocatoria;
	public String vConvocatoria;
	public int iCodModalidad;
	public String vModalidad;
	public int iCodUnidad;
	public String vCodUnidad;
	public String vUnidad;
	public int iCodComite;
	public String vCodComite;
	public String vComite;
	public int iCodItem;
	public String vCodItem;
	public String vItem;
	public int iCodPostor;
	public String vCodPostor;
	public String cCodProveedor;
	public String vNombreProveedor;
	public String cCodEstablecimiento;
	public String vDireccionEstablecimiento;
	public String vReferenciaEstablecimiento;
	public int iPuntajeTecnico;
	public int iPuntajeEconomico;
	public String dFechaInicio;
	public String dFechaFin;
	public String cRUC;
	public String cMultiple;
	public String vObservacion;
	public SMMAEFICHAS objSMMAEFICHAS;
	
	
	public TABLETPOSTORES() 
	{

	}
	public TABLETPOSTORES(String vCodPostor, String vComite) {
		this.vCodPostor=vCodPostor;
		//this.vDireccionEstablecimiento = vDireccionEstablecimiento;
		this.vComite = vComite;
	
	}
	@Override
	public String toString() {

		return vComite;
	}

	

}
