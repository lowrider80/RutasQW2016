package pe.gob.qw.rutas.entities;

public class LIBERACIONRACION {

	 public int iCodLiberacion;
	 public String vFechaLiberacion;
          
	public LIBERACIONRACION() {
	}

	public LIBERACIONRACION(int iCodLiberacion, String vFechaLiberacion) {
		this.iCodLiberacion=iCodLiberacion;
		//this.vDireccionEstablecimiento = vDireccionEstablecimiento;
		this.vFechaLiberacion=vFechaLiberacion;
		
	}

	@Override
	public String toString() {
	
		
		return vFechaLiberacion;
	}
	
	
}
