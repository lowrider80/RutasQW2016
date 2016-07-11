package pe.gob.qw.rutas.entities;

//import com.google.gson.annotations.SerializedName;

public class SMFICHAINICIALANEXO 
{
	public int iCodModalidad;
	public int idFichaGrabadas;
	public int iCodComite;
	public int iCalificado;
	public String vComite;
	public String vCodItem;
	public String vItem;
	public String vCodPostor;
	public String cCodProveedor;
	public Integer iCantSolido;
	public Integer iCantSolidoP;
	public Integer iCantBebible;
	public Integer iCantBebibleP;
	public Integer iCantAcompanamiento;
	public Integer iCantAcompanamientoP;
	public Integer iCantGalleta;
	public Integer iCantGalletaP;
	public Integer iCantAlimento;
	public Integer iCantAlimentoP;
	
	
	public SMFICHAINICIALANEXO() 
	{
		iCantSolido=null;
		iCantSolidoP=null;
		iCantBebible=null;
		iCantBebibleP=null;
		iCantAcompanamiento=null;
		iCantAcompanamientoP=null;
		iCantGalleta=null;
		iCantGalletaP=null;
		iCantAlimento=null;
		iCantAlimentoP=null;
		iCalificado=0;
	}
	public SMFICHAINICIALANEXO(String vCodPostor, String vComite) {
		this.vCodPostor=vCodPostor;
		//this.vDireccionEstablecimiento = vDireccionEstablecimiento;
		this.vComite = vComite;
	
	}
	@Override
	public String toString() {

		return vComite;
	}

	

}
