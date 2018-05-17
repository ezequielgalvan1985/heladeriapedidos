package adaptivex.pedidoscloud.Vendor.DeliveryTracker.Controller;

import android.util.Log;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Vendor.DeliveryTracker.Entity.RouteLine;

/**
 * Created by egalvan on 17/5/2018.
 */

public class RouteLineController {




    public ArrayList<RouteLine> getListRouteLine(ArrayList<Pedido> listaPedidos ){
        try{
            //Parametros: Lista de pedidos
            //Devuelve: una lista de lineas de rutas que seran diagramadas en el mapa

            Integer point = 0;

            ArrayList<RouteLine> listRouteLine = new ArrayList<RouteLine>();
            RouteLine routeline = new RouteLine();
            Pedido pedidoanterior = null;
            for (Pedido p :listaPedidos){
                point = point +1;

                if (point==1){
                    routeline = new RouteLine();
                }

                if (pedidoanterior != null){
                    if (pedidoanterior.getDireccion()!= null){

                        routeline.setOrigin(pedidoanterior.getDireccion());
                        routeline.setOriginEntregado(pedidoanterior.isEntregado());

                        routeline.setDestine(p.getDireccion());
                        routeline.setDestineEntregado(p.isEntregado());

                        listRouteLine.add(routeline);
                        point = 0;
                    }
                }else{
                    routeline.setOrigin(p.getDireccion());
                    routeline.setOriginEntregado(p.isEntregado());
                }
                pedidoanterior = p;

            }
            return listRouteLine;

            //Obtener las direcciones

        }catch (Exception e){
            Log.e("Error", e.getMessage().toString());
            return null;
        }
    }
}
