package pe.gob.qw.rutas.entities;

public class RUTA {

	 public String iCodRutaEntrega;

	public RUTA() {
	}

	public RUTA(String iCodRutaEntrega) {
		//this.vDireccionEstablecimiento = vDireccionEstablecimiento;
		this.iCodRutaEntrega=iCodRutaEntrega;
		
	}

	@Override
	public String toString() {
	
		
		return iCodRutaEntrega;
	}
	
	
}
