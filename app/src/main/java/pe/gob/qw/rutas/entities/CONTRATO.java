package pe.gob.qw.rutas.entities;

public class CONTRATO {

	 public int iCodContrato;
	 public int iNumContrato;
     public String vNomContrato;
     
     public String cCodProveedor;
     
     public int iCodItem;
     
     public int iCodComite;
     
     public int iCodUnidad;
     
     public int iCodModalidad;
     
     public String DescripcionUT;
     
     public String vComite;
     
     public String vNombreItem;
     
     public int iCodProveedor;
     //liberacion
     public String vNumContrato;
     public String vDireccionEstablecimiento;
     public String cCodEstablecimiento;
     public int iTipo;
     
     public String vObservacion; //Nombre de Establecimiento
     
     public String iNumEntrega;
     
	public CONTRATO() {
	}

	public CONTRATO(int iCodContrato, String vNumContrato) {
		this.iCodContrato=iCodContrato;
		//this.vDireccionEstablecimiento = vDireccionEstablecimiento;
		this.vNumContrato = vNumContrato;
		this.vNombreItem = vNumContrato;
	}
	public CONTRATO(int iCodContrato, String vNumContrato, String vNomContrato) {
		this.iCodContrato=iCodContrato;
		//this.vDireccionEstablecimiento = vDireccionEstablecimiento;
		this.vNumContrato = vNomContrato;
		this.vNombreItem = vNumContrato;
	}

	@Override
	public String toString() {
		
		String concat = vNombreItem;
		
		
		if(iNumEntrega ==null)
			iNumEntrega="";
		else
			concat = concat + " - " + iNumEntrega;
		if(vObservacion==null){
			vObservacion="";
			
		}
		else
			concat = concat + " - " + vObservacion;
		
		
		return concat;
	}
	
	
}
