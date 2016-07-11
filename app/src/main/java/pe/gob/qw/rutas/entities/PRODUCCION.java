package pe.gob.qw.rutas.entities;

/**
 * Created by uadin12 on 24/02/2016.
 */
public class PRODUCCION {
    public int cCodProduccion;
    public String cCodProveedor;
    public String vNombreProduccion;

    public PRODUCCION (){}

    public PRODUCCION(int cCodProduccion, String cCodProveedor, String vNombreProduccion) {
        this.cCodProduccion = cCodProduccion;
        this.cCodProveedor = cCodProveedor;
        this.vNombreProduccion = vNombreProduccion;
    }

    @Override
    public String toString() {
        return vNombreProduccion;
    }
}
