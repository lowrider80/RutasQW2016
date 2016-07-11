package pe.gob.qw.rutas.entities;

public class TURNO {

	 public String cCodTurno;
	 public String vNombreTurno;
          
	public TURNO() {
	}

	public TURNO(String cCodTurno, String vNombreTurno) {
		this.cCodTurno=cCodTurno;
		//this.vDireccionEstablecimiento = vDireccionEstablecimiento;
		this.vNombreTurno=vNombreTurno;
		
	}

	@Override
	public String toString() {
	
		
		return vNombreTurno;
	}
	
	
}
