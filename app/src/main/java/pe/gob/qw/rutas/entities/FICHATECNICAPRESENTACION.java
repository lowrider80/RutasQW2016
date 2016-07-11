package pe.gob.qw.rutas.entities;

public class FICHATECNICAPRESENTACION {

	public int iCodFichaTecnicaPresentacion;
	public int iCodFichaTecnica;
	public String vNombre;
	
	public FICHATECNICAPRESENTACION() {
	}

	public FICHATECNICAPRESENTACION(int iCodFichaTecnicaPresentacion,
			int iCodFichaTecnica, String vNombre) {
		super();
		this.iCodFichaTecnicaPresentacion = iCodFichaTecnicaPresentacion;
		this.iCodFichaTecnica = iCodFichaTecnica;
		this.vNombre = vNombre;
	}

	@Override
	public String toString() {
		return  vNombre;
	}

	
}
