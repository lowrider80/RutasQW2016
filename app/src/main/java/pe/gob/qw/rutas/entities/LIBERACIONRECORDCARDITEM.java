package pe.gob.qw.rutas.entities;

import java.util.List;

public class LIBERACIONRECORDCARDITEM 
{
	public int iLIBERACIONRECORDCARDITEM;	
	public int idFichasGrabadas;
	public int iCodProducto;
	public String vNomProducto;
	public String vNomMarca;
	public int iPresentacion;		
	public int iIndexComboPresentacion;		
	public int iCantidad;	
	public int iIndexCombo;		
	public String vResultado;//parametro
	public int bactivo;//instanciado
	public int iOrden;
	public int iCodDetFicha;
	public int iPuntaje;
	
	public List<FICHATECNICAPRESENTACION> LISTFICHATECNICAPRESENTACION;
    
	public LIBERACIONRECORDCARDITEM() 
	{
		this.bactivo=0;
		this.iCantidad=0;
		this.iPresentacion=0;
		this.vResultado="";
		this.vNomMarca="";
		this.iIndexCombo=0;
		this.iIndexComboPresentacion=0;
		this.iPuntaje=50;
	}




	public LIBERACIONRECORDCARDITEM(int idFichasGrabadas, int iCodProducto,
									String vNomProducto, String vNomMarca, int iPresentacion,
									int iIndexComboPresentacion, int iCantidad, int iIndexCombo,
									String vResultado, int bactivo, int iOrden, int iPuntaje) {
		super();
		this.idFichasGrabadas = idFichasGrabadas;
		this.iCodProducto = iCodProducto;
		this.vNomProducto = vNomProducto;
		this.vNomMarca = vNomMarca;
		this.iPresentacion = iPresentacion;
		this.iIndexComboPresentacion = iIndexComboPresentacion;
		this.iCantidad = iCantidad;
		this.iIndexCombo = iIndexCombo;
		this.vResultado = vResultado;
		this.bactivo = bactivo;
		this.iOrden = iOrden;
		this.iCodDetFicha = iCodDetFicha;
		this.iPuntaje=iPuntaje;
	}









	


	
	
}
