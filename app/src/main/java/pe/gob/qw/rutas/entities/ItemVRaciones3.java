package pe.gob.qw.rutas.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uadin12 on 23/03/2016.
 */
public class ItemVRaciones3 {
    private int RecordCardItem;
    private String hora;
    private boolean crit1;
    private boolean crit2;
    private boolean crit3;
    private boolean crit4;
    private boolean crit5;
    private boolean crit6;
    private boolean crit7;
    private boolean crit8;

    public static List<ItemVRaciones3> ITEMSRACIONES = new ArrayList<>();


    public ItemVRaciones3(int recordCardItem, String hora, boolean crit1, boolean crit2, boolean crit3, boolean crit4, boolean crit5, boolean crit6, boolean crit7, boolean crit8) {
        RecordCardItem = recordCardItem;
        this.hora = hora;
        this.crit1 = crit1;
        this.crit2 = crit2;
        this.crit3 = crit3;
        this.crit4 = crit4;
        this.crit5 = crit5;
        this.crit6 = crit6;
        this.crit7 = crit7;
        this.crit8 = crit8;
    }

    public static ItemVRaciones3 getItem(int id) {
        for (ItemVRaciones3 item : ITEMSRACIONES) {
            if (item.getRecordCardItem() == id) {
                return item;
            }
        }
        return null;
    }

    public int getRecordCardItem() {
        return RecordCardItem;
    }

    public void setRecordCardItem(int recordCardItem) {
        RecordCardItem = recordCardItem;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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

    public boolean isCrit6() {
        return crit6;
    }

    public void setCrit6(boolean crit6) {
        this.crit6 = crit6;
    }

    public boolean isCrit7() {
        return crit7;
    }

    public void setCrit7(boolean crit7) {
        this.crit7 = crit7;
    }

    public boolean isCrit8() {
        return crit8;
    }

    public void setCrit8(boolean crit8) {
        this.crit8 = crit8;
    }
}
