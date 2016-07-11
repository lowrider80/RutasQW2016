package pe.gob.qw.rutas.entities;

public class COMPONENTERACION 
{
	public int iCodFichaTecnicaRacion;
	public int iGrupoRacion;
	public String vNombreComponente;
	public String vGrupoComponente;
	public int iCodTipoRacion;
	public String vTipoComponente;
	public String vTipoRacion;
	
    
	public COMPONENTERACION() 
	{
	
	}

	public COMPONENTERACION(int iCodFichaTecnicaRacion, String vNombreComponente)
	{
		this.iCodFichaTecnicaRacion=iCodFichaTecnicaRacion;
		this.vNombreComponente=vNombreComponente;
	}
	
	@Override
	public String toString(){
		
		return vNombreComponente;
	}
}
