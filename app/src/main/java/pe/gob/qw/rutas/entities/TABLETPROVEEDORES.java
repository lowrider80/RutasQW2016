package pe.gob.qw.rutas.entities;

//import com.google.gson.annotations.SerializedName;

public class TABLETPROVEEDORES 
{
	public String cCodProveedor;
	public String vNombreProveedor;
	public String cCodEstablecimiento;
	public int iNro;
	public String vDireccionEstablecimiento;
	public String vReferenciaEstablecimiento;
	public String vLatitudEstablecimiento;
	public String vLongitudEstablecimiento;
	public String vEstablecimientoDistrito;
	public String vEstablecimientoProvincia;
	public String vEstablecimientoDepartamento;
	public int iTipo;
	public String cCodDepartamento;
	public String cCodProvincia;
	public String cCodUbigeo;
	public String cRUC;
	public String cMultiple;
	public String vObservacion;
	public SMMAEFICHAS objSMMAEFICHAS;
	public String vItems;
	public String vNombreRacion;
	public String vComites;
	public String vCantidadProgramada;
	public String vCantidadSupervisada;
	public String vIdComites;
	public String vNombreProvincia;
	public String vNombreDepartamento;
	public String vNombreDistrito;
	public int iTipoRacion;
	
	public TABLETPROVEEDORES() 
	{
	}
	public TABLETPROVEEDORES(String cCodEstablecimiento, String vNombreEstablecimiento) {
		this.cCodEstablecimiento=cCodEstablecimiento;
		//this.vDireccionEstablecimiento = vDireccionEstablecimiento;
		this.vObservacion = vNombreEstablecimiento;
	
	}

	@Override
	public String toString() {
		if (this.vNombreProveedor == null) {
			if (this.vObservacion == null) {
				if (this.vDireccionEstablecimiento.split("/").length > 1) {
					return this.vDireccionEstablecimiento.split("/")[1];
				}
				return this.vDireccionEstablecimiento.split("/")[0];
			} else if (this.vObservacion.isEmpty()) {
				return this.vDireccionEstablecimiento.split("/").length > 1 ? this.vDireccionEstablecimiento.split("/")[1] : this.vDireccionEstablecimiento.split("/")[0];
			} else {
				return this.vObservacion;
			}
		} else if (!this.vNombreProveedor.isEmpty()) {
			if (this.vObservacion == null) {
				return this.vDireccionEstablecimiento.split("/").length > 1 ? this.vDireccionEstablecimiento.split("/")[1] : this.vDireccionEstablecimiento.split("/")[0];
			} else {
				if (this.vObservacion.isEmpty()) {
					return this.vDireccionEstablecimiento.split("/").length > 1 ? this.vDireccionEstablecimiento.split("/")[1] : this.vDireccionEstablecimiento.split("/")[0];
				} else {
					return this.vObservacion;
				}
			}
		} else {
			if (this.vObservacion == null) {
				return this.vDireccionEstablecimiento.split("/").length > 1 ? this.vDireccionEstablecimiento.split("/")[1] : this.vDireccionEstablecimiento.split("/")[0];
			} else {
				if (this.vObservacion.isEmpty()) {
					return this.vDireccionEstablecimiento.split("/").length > 1 ? this.vDireccionEstablecimiento.split("/")[1] : this.vDireccionEstablecimiento.split("/")[0];
				} else {
					return this.vObservacion;
				}
			}
		}
	}

//	@Override
//	public String toString() {
//		if(vNombreProveedor==null)
//		{
//			if(vObservacion==null)
//			{
//				return vDireccionEstablecimiento.split("/").length>1?
//						vDireccionEstablecimiento.split("/")[1]:vDireccionEstablecimiento.split("/")[0];
//			}
//			else
//			{
//				if(vObservacion.isEmpty())
//				{
//					return vDireccionEstablecimiento.split("/").length>1?
//							vDireccionEstablecimiento.split("/")[1]:vDireccionEstablecimiento.split("/")[0];
//
//				}
//				else
//				{
//					return vObservacion;
//				}
//			}
//		}
//		else{
//			if(vNombreProveedor.isEmpty())
//			{
//				if(vObservacion==null)
//				{
//					return vDireccionEstablecimiento.split("/").length>1?
//							vDireccionEstablecimiento.split("/")[1]:vDireccionEstablecimiento.split("/")[0];
//				}
//				else
//				{
//					if(vObservacion.isEmpty())
//					{
//						return vDireccionEstablecimiento.split("/").length>1?
//								vDireccionEstablecimiento.split("/")[1]:vDireccionEstablecimiento.split("/")[0];
//
//					}
//					else
//					{
//						return vObservacion;
//					}
//				}
//
//			}
//		}
//		return vNombreProveedor;
//	}

	

}
