package adaptivex.pedidoscloud.Vendor.DeliveryTracker.Behavior;

import java.util.List;

import adaptivex.pedidoscloud.Vendor.DeliveryTracker.Entity.Route;

/**
 * Created by Mai Thanh Hiep on 4/3/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
