package pe.gob.qw.rutas.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uadin12 on 23/03/2016.
 */
public class ItemVRaciones1 {
    private int recordCardItem;
    private String hora;
    private String produccion;
    private String operacion;
    private boolean crit1;
    private boolean crit2;
    private boolean crit3;
    private boolean crit4;
    private boolean crit5;

    public static List<ItemVRaciones1> ITEMSRACIONES = new ArrayList<>();

    public ItemVRaciones1(int recordCardItem, String hora, String produccion, String operacion, boolean crit1, boolean crit2, boolean crit3, boolean crit4, boolean crit5) {
        this.recordCardItem = recordCardItem;
        this.hora = hora;
        this.produccion = produccion;
        this.operacion = operacion;
        this.crit1 = crit1;
        this.crit2 = crit2;
        this.crit3 = crit3;
        this.crit4 = crit4;
        this.crit5 = crit5;
    }

    public static ItemVRaciones1 getItem(int id) {
        for (ItemVRaciones1 item : ITEMSRACIONES) {
            if (item.getRecordCardItem() == id) {
                return item;
            }
        }
        return null;
    }

    public int getRecordCardItem() {
        return this.recordCardItem;
    }

    public void setRecordCardItem(int recordCardItem) {
        this.recordCardItem = recordCardItem;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getProduccion() {
        return produccion;
    }

    public void setProduccion(String produccion) {
        this.produccion = produccion;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public boolean isCrit1() {
        return crit1;
    }

    public void setCrit1(boolean crit1) {
        this.crit1 = crit1;
    }

    public boolean isCrit2() {
        return crit2;
    }

    public void setCrit2(boolean crit2) {
        this.crit2 = crit2;
    }

    public boolean isCrit3() {
        return crit3;
    }

    public void setCrit3(boolean crit3) {
        this.crit3 = crit3;
    }

    public boolean isCrit4() {
        return crit4;
    }

    public void setCrit4(boolean crit4) {
        this.crit4 = crit4;
    }

    public boolean isCrit5() {
        return crit5;
    }

    public void setCrit5(boolean crit5) {
        this.crit5 = crit5;
    }
}
