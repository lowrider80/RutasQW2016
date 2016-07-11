package pe.gob.qw.rutas.entities;

import java.util.Date;

public class SMFICHASGRABADASRACIONES {

	public int idFichasGrabadas;
	public String cCodEstablecimiento;
	public String cCodProveedor;
	public int iCodFicha;
	public int iEstado;
	public String vDescFicha;
	
	public String vNControlCalidad;
	public String vDNIControlCalidad;
	
	public String vNRepresentanteLegal;
	public String vDNIRepresentanteLegal;
	
	public String vNSupervisor;
	public String vDNISupervisor;
	
	public String vNPNAEQW;
	public String vDNIPNAEQW;
	
	public Long lFecha;
	public Long lFechaFin;
	public String vDatosRelevantes;
	
	
	public int iTotalOperarios;	
	public String dOpcion1;
	public double dOpcion2;
	public double dOpcion3;
	public double dOpcion4;
	
	public double dPuntajeFicha;
	public double dPuntajeAnexo;
	public int iTotalPreguntas;
	public boolean bCalificaFicha;
	public boolean bCalificaAnexo;
	
	
	public String vEspecialidad;
	public String vColegiatura;
	public String cCategoria;
	

	public String vTipoVehiculo;
	public String vPlaca;
	public String vGuiaRemision;

	public int iCantidadRacionesAdjudicadas;
	public int iCantidadRacionesVerificadas;
	public String vNombreRacionesVerificadas;
	public String vTurno;
	public int iNumeroOperariosHombre;
	public int iNumeroOperariosMujer;
	
	
    public String vTelefonoRepresentanteLegal ;
    public String vEmpresaResponsableSanidad ;
    public long vFechaVigenciaSanidad ;
    public long vFechaVigenciaCertificadoMedico ;
    public String vEmpresaResponsableMedico ;
    public String vNumeroCertificadoSanidad ;
    public boolean bCertificadoMedico;
    public int iNroLiberacion;
    public int iCodContrato;
    
    
    public String vNroContrato;
    public String vNroComiteCompra;
    public String vItem;
    public int iTipoEstablecimiento;
    
	public SMFICHASGRABADASRACIONES() {
		super();
		this.vTipoVehiculo = "";
		this.vPlaca = "";
		this.vGuiaRemision = "";
		this.idFichasGrabadas = 0;
		this.cCodEstablecimiento = "";
		this.cCodProveedor = "";
		this.iCodFicha = 0;
		this.iEstado = 0;
		this.vDescFicha = "";
		this.vNControlCalidad = "";
		this.vDNIControlCalidad = "";
		this.vNRepresentanteLegal = "";
		this.vDNIRepresentanteLegal = "";
		this.vNSupervisor = "";
		this.vDNISupervisor = "";
		this.vNPNAEQW = "";
		this.vDNIPNAEQW = "";
		this.lFecha = new Date().getTime();
		this.lFechaFin = new Date().getTime();
		this.vDatosRelevantes = "";
		this.iTotalOperarios = 0;
		this.dOpcion1 = "0";
		this.dOpcion2 = 0;
		this.dOpcion3 = 0;
		this.dOpcion4 = 0;
		this.dPuntajeFicha = 0;
		this.dPuntajeAnexo = 0;
		this.iTotalPreguntas = 0;
		this.bCalificaFicha = false;
		this.bCalificaAnexo = false;
		this.vEspecialidad = "";
		this.vColegiatura = "";
		this.cCategoria = "";
		
		this.iCantidadRacionesAdjudicadas=0;
		this.iCantidadRacionesVerificadas=0;
		this.vNombreRacionesVerificadas="";
		this.vTurno="";
		this.iNumeroOperariosHombre=0;
		this.iNumeroOperariosMujer=0;
		
		
		this.vTelefonoRepresentanteLegal="";   
		this.vEmpresaResponsableSanidad="";
		this.vEmpresaResponsableMedico="";
		this.vFechaVigenciaSanidad = new Date().getTime();
		this.vFechaVigenciaCertificadoMedico = new Date().getTime();
		this.vNumeroCertificadoSanidad="";
		this.bCertificadoMedico=false;
		this.iNroLiberacion=0;
		this.iCodContrato=0;
		
		this.vNroContrato="";
		this.vNroComiteCompra="";
		this.vItem="";
		this.iTipoEstablecimiento = 0; //1 si es almacen y 2 si  es planta 
		
		
		
	}
	
	
}
