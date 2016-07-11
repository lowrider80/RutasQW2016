package pe.gob.qw.rutas.entities;

public class ITEM {

	 public int iCodItem;
	 public String vNombreItem;
	 public boolean bcheck = false;
          
	public ITEM() {
	}

	public ITEM(int iCodItem, String vNombreItem) {
		this.iCodItem=iCodItem;
		//this.vDireccionEstablecimiento = vDireccionEstablecimiento;
		this.vNombreItem=vNombreItem;
		
	}

	@Override
	public String toString() {

		return vNombreItem;

	}
}
