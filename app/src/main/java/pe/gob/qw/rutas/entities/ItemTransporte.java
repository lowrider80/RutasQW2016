package pe.gob.qw.rutas.entities;

/**
 * Created by uadin12 on 08/04/2016.
 */
public class ItemTransporte {
    public int iRecordCardItem = 0;
    public int iCodFicha = 0;
    public String cCodEstablecimiento = "";
    public String cCodProveedor = "";
    public String vResultado = ""; //PLACA
    public int idFichasGrabadas = 0;
    public String vCampo ="";
    public String iRecordCardItemPadre = ""; // iRecordCardItemPadre (Acumulado)
    public String vObservacion = ""; // Revaluaciones (Acumulado)


    public ItemTransporte(){ };

    public ItemTransporte(int iRecordCardItem, int iCodFicha, String cCodEstablecimiento, String cCodProveedor, String vResultado, int idFichasGrabadas, String vCampo, String iRecordCardItemPadre, String vObservacion) {
        this.iRecordCardItem = iRecordCardItem;
        this.iCodFicha = iCodFicha;
        this.cCodEstablecimiento = cCodEstablecimiento;
        this.cCodProveedor = cCodProveedor;
        this.vResultado = vResultado;
        this.idFichasGrabadas = idFichasGrabadas;
        this.vCampo = vCampo;
        this.iRecordCardItemPadre = iRecordCardItemPadre;
        this.vObservacion = vObservacion;
    }

    @Override
    public String toString() {
        return String.valueOf(iRecordCardItem);
    }
}
