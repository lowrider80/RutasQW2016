package pe.gob.qw.rutas.entities;

public class COMPONENTELIBERACION 
{
	public int iComponenteLiberacion;
	public int iComponenteLiberacionPadre;
	public String cTipoRacion;
	public String cCodProveedor;
	public int iCodFicha;
	
	public int iCodTipoSeccion;
	public int iCodFormato;
	public String cSeccion;
	public String vDetalleSeccion;//detalle
	public int iValor;
	public int iTipoControl;//valor
	public int iOrden;
	
	public String vObservacion;
	
	public String vResultado;//parametro
	
	public boolean bcheck;//selecionado
	public int iIndexCombo;//posicion
	
	public boolean bactivo;//instanciado
	
	
    
	public COMPONENTELIBERACION() 
	{
		this.bcheck=false;
		this.bactivo=false;
		this.vResultado="";
		this.iOrden=0;
		this.iIndexCombo=0;
	}


	
	
}
