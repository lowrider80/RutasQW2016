package pe.gob.qw.rutas.entities;

/**
 * Created by uadin11 on 14/03/2016.
 */
public class LIBERACIONRUTAS {

//public int iCodLiberacion ;

    public int idSMFICHAS;
    public int iCodItem;
    public String vNomItem;
    /*public int iCodContrato;
    public String cCodProveedor;
    public String vNombreProveedor;
    public int iCodCronogramaEntrega;
    public int iNumEntrega ;
    public int iCodCampana;
    public String MesCronograma;*/
    public int iNumEntrega;
    public String dFecIniciaPlazoEntrega;
    public String dFecFinalPlazoEntrega;
    public String dFecLiberacion;
    public String cCodEstablecimiento;
    public int NomIEE;
    public int NumLib;


    public LIBERACIONRUTAS() {
    }

    public LIBERACIONRUTAS(int iCodItem, String vNomItem, Integer iNumEntrega) {
        this.iCodItem = iCodItem;
        this.vNomItem = vNomItem;
        this.iNumEntrega = iNumEntrega;
        //this.dFecIniciaPlazoEntrega=dFecIniciaPlazoEntrega;
        // this.dFecFinalPlazoEntrega=dFecFinalPlazoEntrega;
    }


    @Override
    public String toString() {
        String vP = vNomItem;
        return vP;
    }


    public int getiCodItem() {
        return iCodItem;
    }

    public void setiCodItem(int iCodItem) {
        this.iCodItem = iCodItem;
    }

    public String getvNomItem() {
        return vNomItem;
    }

    public void setvNomItem(String vNomItem) {
        this.vNomItem = vNomItem;
    }

    public String getdFecIniciaPlazoEntrega() {
        return dFecIniciaPlazoEntrega;
    }

    public void setdFecIniciaPlazoEntrega(String dFecIniciaPlazoEntrega) {
        this.dFecIniciaPlazoEntrega = dFecIniciaPlazoEntrega;
    }

    public String getdFecFinalPlazoEntrega() {
        return dFecFinalPlazoEntrega;
    }

    public void setdFecFinalPlazoEntrega(String dFecFinalPlazoEntrega) {
        this.dFecFinalPlazoEntrega = dFecFinalPlazoEntrega;
    }

    public int getiNumEntrega() {
        return iNumEntrega;
    }

    public void setiNumEntrega(int iNumEntrega) {
        this.iNumEntrega = iNumEntrega;
    }
}