package adaptivex.pedidoscloud.Vendor.DeliveryTracker.Entity;

/**
 * Created by egalvan on 14/5/2018.
 */

public class RouteLine {
    private String origin;
    private boolean originEntregado;
    private String destine;
    private boolean destineEntregado;

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


    public boolean isOriginEntregado() {
        return originEntregado;
    }

    public void setOriginEntregado(boolean originEntregado) {
        this.originEntregado = originEntregado;
    }

    public boolean isDestineEntregado() {
        return destineEntregado;
    }

    public void setDestineEntregado(boolean destineEntregado) {
        this.destineEntregado = destineEntregado;
    }
}
