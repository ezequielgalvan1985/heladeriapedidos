package adaptivex.pedidoscloud;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;


import adaptivex.pedidoscloud.Core.Interfaces.OnTaskCompleted;
import adaptivex.pedidoscloud.Servicios.HelperPedidos;
import adaptivex.pedidoscloud.Vendor.DeliveryTracker.Behavior.DirectionFinder;
import adaptivex.pedidoscloud.Vendor.DeliveryTracker.Behavior.DirectionFinderListener;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Vendor.DeliveryTracker.Entity.PointDirection;
import adaptivex.pedidoscloud.Vendor.DeliveryTracker.Entity.Route;


public class MapsActivity extends FragmentActivity
        implements  OnMapReadyCallback, DirectionFinderListener, OnTaskCompleted {

    private GoogleMap mMap;
    private Button btnFindPath, btnDibujarRecorrido;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    private HelperPedidos restPedidos;
    private int durationValue = 0;
    private int distanceValue = 0;

    private ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
    private ArrayList<PointDirection> listaPoint = new ArrayList<PointDirection>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        restPedidos = new HelperPedidos(this);
        listaPedidos = new ArrayList<Pedido>();
        btnDibujarRecorrido = (Button) findViewById(R.id.btnDibujarRecorrido);

        btnDibujarRecorrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesarRecorrido();
            }
        });

    }

    private void procesarRecorrido(){
        try{
            listaPedidos = new ArrayList<Pedido>();
            restPedidos.setOpcion(HelperPedidos.OPTION_FIND_ESTADO_ENCAMINO);
            restPedidos.execute();
        }catch (Exception e){
            Log.e("Error", e.getMessage().toString());

        }
    }

    private void dibujarRecorrido(){
        try{
            //Blanquear variables
            polylinePaths = new ArrayList<>();
            originMarkers = new ArrayList<>();
            destinationMarkers = new ArrayList<>();
            durationValue = 0;
            distanceValue = 0;

            //Obtener lista de pedido para entregar
            ArrayList<PointDirection> lista = new ArrayList<PointDirection>();
            lista = getListaPoints();
            for (PointDirection point :lista){
                new DirectionFinder((adaptivex.pedidoscloud.Vendor.DeliveryTracker.Interfaces.DirectionFinderListener) this, point.getOrigin(), point.getDestine()).execute();
            }

            //Obtener las direcciones
            dibujarUbicacionDelivery();
        }catch (Exception e){
            Log.e("Error", e.getMessage().toString());
        }
    }

    private void dibujarUbicacionDelivery(){
        try{
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_moto))
                    .title("Moto")
                    .position(new LatLng(-34.602438, -58.427951))));

        }catch (Exception e){
            Log.e("Error", e.getMessage().toString());
        }
    };

    private ArrayList<PointDirection> getListaPoints(){
        try{
            //Obtener lista de pedido para entregar
            Integer point = 0;
            listaPoint = new ArrayList<PointDirection>();
            PointDirection pd = new PointDirection();
            Pedido pedidoanterior = null;
            for (Pedido p :listaPedidos){
                point = point +1;

                if (point==1){
                    pd = new PointDirection();
                }

                if (pedidoanterior != null){
                    if (pedidoanterior.getDireccion()!= null){
                        pd.setOrigin(pedidoanterior.getDireccion());
                        pd.setDestine(p.getDireccion());
                        if (pedidoanterior.isEntregado() && p.isEntregado()){
                            pd.setEntregado(true);
                        }else{
                            pd.setEntregado(false);
                        }
                        listaPoint.add(pd);
                        point = 0;
                    }
                }else{
                    pd.setOrigin(p.getDireccion());
                }
                pedidoanterior = p;

            }
            return listaPoint;

            //Obtener las direcciones

        }catch (Exception e){
            Log.e("Error", e.getMessage().toString());
            return null;
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        //progressDialog.dismiss();
        /*

         */
        Integer index = 0;
        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));

            durationValue += route.duration.value;
            distanceValue += route.distance.value;


            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                    .title(route.startAddress)
                    .position(route.startLocation)));

            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                    .title(route.endAddress)
                    .position(route.endLocation)));



            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color( Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
            index+=1;
        }

        ((TextView) findViewById(R.id.tvDuration)).setText(String.valueOf(durationValue));
        ((TextView) findViewById(R.id.tvDistance)).setText(String.valueOf(distanceValue));

    }

    @Override
    public void onDirectionFinderStart() {



    }

    @Override
    public void onTaskCompleted() {
        switch (restPedidos.getOpcion()){
            case HelperPedidos.OPTION_FIND_ESTADO_ENCAMINO:
                dibujarRecorrido();
                break;
        }
    }

    @Override
    public void onTaskError() {

    }





}
