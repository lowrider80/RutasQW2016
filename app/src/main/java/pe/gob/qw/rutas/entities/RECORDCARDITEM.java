package pe.gob.qw.rutas.entities;

public class RECORDCARDITEM 
{
	public int iRecordCardItem;
	public int iRecordCardItemPadre;
	public String cCodEstablecimiento;
	public String cCodProveedor;
	public int iCodFicha;
	public String ordenLlamada;//orden
	
	public int iCodTipoSeccion;
	public String iCodBcoPreg;//numeracion
	public int iCodFormato;
	public String cSeccion;
	public String vDetalleSeccion;//detalle
	public int iValor;
	public int iTipoControl;//valor
	public int iOrden;
	public int iTipoColumna;
	public String vTabla;
	public String vCampo;
	public String vObservacion;
	
	public String vResultado;//parametro
	
	public boolean bcheck;//selecionado
	public int iIndexCombo;//posicion
	
	public boolean bactivo;//instanciado
	
	
	public int idFichasGrabadas;
    
	public RECORDCARDITEM() 
	{
		this.bcheck=false;
		this.bactivo=false;
		this.vResultado="";
		this.iOrden=0;
		this.iIndexCombo=0;
	}


	
	
}
