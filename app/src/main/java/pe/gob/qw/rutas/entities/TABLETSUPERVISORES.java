package pe.gob.qw.rutas.entities;

//import com.google.gson.annotations.SerializedName;

public class TABLETSUPERVISORES
{
	public String vNombre;
	public String cCodDocPersona;
	public String vDesCargo;

	public TABLETSUPERVISORES()
	{

	}
	public TABLETSUPERVISORES(String vNombre, String cCodDocPersona, String vDesCargo) {
		this.vNombre=vNombre;
		this.cCodDocPersona = cCodDocPersona;
		this.vDesCargo = vDesCargo;
	
	}


	@Override
	public String toString() {
		String vP = cCodDocPersona;
		return vP;
	}
}
