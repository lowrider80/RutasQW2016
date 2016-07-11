package pe.gob.qw.rutas.entities;

/**
 * Created by uadin11 on 17/05/2016.
 */
public class ESPECIALISTAS {

    public String cCodEstablecimiento;
    public String vNombreProfesional;
    public String vProfesion;
    public String vColegiatura;


    public ESPECIALISTAS() {}



    public ESPECIALISTAS(String vNombreProfesional, String vProfesion, String vColegiatura){
        this.vNombreProfesional=vNombreProfesional;
        this.vProfesion=vProfesion;
        this.vColegiatura=vColegiatura;
    }

    public String getvNombreProfesional() {
        return vNombreProfesional;
    }

    public void setvNombreProfesional(String vNombreProfesional) {
        this.vNombreProfesional = vNombreProfesional;
    }

    public String getcCodEstablecimiento() {
        return cCodEstablecimiento;
    }

    public void setcCodEstablecimiento(String cCodEstablecimiento) {
        this.cCodEstablecimiento = cCodEstablecimiento;
    }

    public String getvProfesion() {
        return vProfesion;
    }

    public void setvProfesion(String vProfesion) {
        this.vProfesion = vProfesion;
    }

    public String getvColegiatura() {
        return vColegiatura;
    }

    public void setvColegiatura(String vColegiatura) {
        this.vColegiatura = vColegiatura;
    }

    @Override
    public String toString() {
        String vP = vNombreProfesional;
        return vP;
    }


}
