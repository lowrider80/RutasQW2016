package pe.gob.qw.rutas.entities;

//import com.google.gson.annotations.SerializedName;

public class SMMAEFICHAS 
{
	//@SerializedName("iCodFicha")
	public int iCodFicha;
	
	//@SerializedName("vDescFicha")
	public String vDescFicha;
	
	public String cCategoria ;
	//@SerializedName("iCodTipoInst")
	public int iCodTipoInst;
	
	//@SerializedName("bactivo")
	public int bactivo;
	
	//@SerializedName("iCodUsuario")
	public int iCodUsuario;
	
	//@SerializedName("dtFecReg")
	public String dtFecReg;
	
	//@SerializedName("vNombreTerminal")
	public String vNombreTerminal;
	
	//@SerializedName("vIpTerminal")
	public String vIpTerminal;
	
	public SMMAEFICHAS() 
	{

	}

	public SMMAEFICHAS(int iCodFicha, String vDescFicha) {
		super();
		this.iCodFicha = iCodFicha;
		this.vDescFicha = vDescFicha;
	}

	
}
