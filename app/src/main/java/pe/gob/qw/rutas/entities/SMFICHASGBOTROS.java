package pe.gob.qw.rutas.entities;


import java.util.Date;

public class SMFICHASGBOTROS {

    public int idFichasGrabadas;
    public int idSMFICHAS;
    public String cCodEstablecimiento;
    public String cCodProveedor;
    public int iCodFicha;
    public int iNumDiasPlazo;
    public Long lFechaPlazo;
    public String vNSupervisor;
    public int vDNISupervisor;
    public int iTipoEstablecimiento;
    public int iNumConforme;
    public int iNumNoConforme;
    public int iNumTotalVh;
    public int iNumTotalVhRv;


    public SMFICHASGBOTROS()
    {
        this.idFichasGrabadas = 0;
        this.idSMFICHAS=0;
        this.cCodEstablecimiento = "";
        this.cCodProveedor = "";
        this.iCodFicha = 0;
        this.iNumDiasPlazo=0;
        this.lFechaPlazo=new Date().getTime();
        this.vNSupervisor="";
        this.vDNISupervisor=0;
        this.iTipoEstablecimiento = 0;
        this.iNumConforme=0;
        this.iNumNoConforme=0;
        this.iNumTotalVh=0;
        this.iNumTotalVhRv=0;
      }

}
