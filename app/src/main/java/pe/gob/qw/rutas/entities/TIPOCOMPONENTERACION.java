package pe.gob.qw.rutas.entities;

public class TIPOCOMPONENTERACION 
{
	public int iGrupoRacion;
	public String vGrupoComponente;
	public int iCodTipoRacion;
	public String vTipoComponente;
	public String vTipoRacion;
	
    
	public TIPOCOMPONENTERACION() 
	{
	
	}

	public TIPOCOMPONENTERACION(int iCodTipoRacion, String vTipoRacion)
	{
	
		this.iCodTipoRacion=iCodTipoRacion;
		this.vTipoRacion=vTipoRacion;
	}
	
	@Override
	public String toString(){
		return vTipoRacion;
	}
}
