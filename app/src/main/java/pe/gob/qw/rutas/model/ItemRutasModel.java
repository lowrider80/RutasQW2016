package pe.gob.qw.rutas.model;

/**
 * Created by uadin12 on 08/04/2016.
 */
public class ItemRutasModel {
    public final int recordCardItem;
    public final String fecha;
    public final String sitioDescarga;
    public final int iCodFicha;

    public ItemRutasModel(int recordCardItem, String fecha, String sitioDescarga, int iCodFicha) {
        this.recordCardItem = recordCardItem;
        this.fecha = fecha;
        this.sitioDescarga = sitioDescarga;
        this.iCodFicha = iCodFicha;
    }

    @Override
    public String toString() {
        return fecha;
    }
}
