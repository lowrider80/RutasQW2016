package pe.gob.qw.rutas.entities;

public class MATERIAPRIMA {
	public String cCodEstablecimiento ;
	public String cCodProveedor;
	public String cEstado ;
	public int iCodModalidad ;
	public String vCaracteristica;
	public String vFabricante;
	public String vMarca ;
	public String vNroLote ;

	public MATERIAPRIMA() {
		// TODO Auto-generated constructor stub
	}


	public MATERIAPRIMA(String vCaracteristica, String vNroLote) {
		this.vCaracteristica=vCaracteristica;
		this.vNroLote = vNroLote;
	}

	@Override
	public String toString() {
		String vC = vCaracteristica;
		return vC;
	}

}
