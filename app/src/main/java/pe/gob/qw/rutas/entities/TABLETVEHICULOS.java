package pe.gob.qw.rutas.entities;

/**
 * Created by uadin11 on 07/03/2016.
 */
public class TABLETVEHICULOS {

    public int iCodContrato;
    public String vPlaca;
    public String cCodEstablecimiento;
    public String cCodProveedor;
    public int iCodUnidad;
    public int iCodTipoVehiculo;
    public String vNombre;

    public TABLETVEHICULOS(){

    }

    public TABLETVEHICULOS(int iCodContrato, String vPlaca) {
        this.iCodContrato=iCodContrato;
        this.vPlaca = vPlaca;
    }

    @Override
    public String toString() {

        String vP = vPlaca;

        return vP;
    }

}
