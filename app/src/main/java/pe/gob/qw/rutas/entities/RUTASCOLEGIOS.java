package pe.gob.qw.rutas.entities;

/**
 * Created by uadin11 on 19/05/2016.
 */
public class RUTASCOLEGIOS {

    public String cCodColegio;
    public String vNombre;
    public String cCodModular;


    public RUTASCOLEGIOS(){}


    public RUTASCOLEGIOS(String cCodColegio, String cCodModular, String vNombre) {

        this.cCodColegio = cCodColegio;
        this.cCodModular = cCodModular;
        this.vNombre = vNombre;
    }
    @Override
    public String toString() {
        String vP = cCodModular;
        return vP;
    }


    public String getcCodColegio() {
        return cCodColegio;
    }

    public void setcCodColegio(String cCodColegio) {
        this.cCodColegio = cCodColegio;
    }

    public String getvNombre() {
        return vNombre;
    }

    public void setvNombre(String vNombre) {
        this.vNombre = vNombre;
    }

    public String getcCodModular() {
        return cCodModular;
    }

    public void setcCodModular(String cCodModular) {
        this.cCodModular = cCodModular;
    }




}
