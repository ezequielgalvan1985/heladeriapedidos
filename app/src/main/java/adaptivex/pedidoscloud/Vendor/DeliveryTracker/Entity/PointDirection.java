package adaptivex.pedidoscloud.Vendor.DeliveryTracker.Entity;

/**
 * Created by egalvan on 14/5/2018.
 */

public class PointDirection {
    private String origin;
    private String destine;
    private boolean entregado;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestine() {
        return destine;
    }

    public void setDestine(String destine) {
        this.destine = destine;
    }


    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }
}
