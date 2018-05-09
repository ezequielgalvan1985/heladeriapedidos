package adaptivex.pedidoscloud.View.Pedidos;

import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Core.WorkNumber;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.Servicios.HelperPedidos;

public class ResumenPedidoFragment extends Fragment implements View.OnClickListener {


    private TextView lbl_cantidad_kilos, lbl_kilos_monto, lbl_cucuruchos_monto, lbl_monto_total;
    private TextView txt_cucuruchos, txt_direccion, txt_cucharitas, txt_monto_total, txtEnvio,
                     txt_pedido_id, txt_hora_entrega, txt_estado,
                     txt_monto_descuento, txt_cantidad_descuento, txt_tiempo_demora, txt_monto_abona;
    private Button   btnEnviarPedido;
    public ResumenPedidoFragment() {

    }


    public static ResumenPedidoFragment newInstance(String param1, String param2) {
        ResumenPedidoFragment fragment = new ResumenPedidoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resumen_pedido, container, false);
        txt_direccion        = (TextView) v.findViewById(R.id.resumen_pedido_direccion);
        lbl_cantidad_kilos   = (TextView) v.findViewById(R.id.resumen_pedido_lbl_cantidad_kilos);
        lbl_kilos_monto      = (TextView) v.findViewById(R.id.resumen_pedido_lbl_kilos_monto);
        txt_cucuruchos       = (TextView) v.findViewById(R.id.resumen_pedido_txt_cucuruchos);
        lbl_cucuruchos_monto = (TextView) v.findViewById(R.id.resumen_pedido_txt_cucuruchos_monto);
        txt_cucharitas       = (TextView) v.findViewById(R.id.resumen_pedido_txt_cucharitas);
        lbl_monto_total      = (TextView) v.findViewById(R.id.resumen_pedido_txt_monto_total);
        txtEnvio             = (TextView) v.findViewById(R.id.resumen_pedido_txt_envio);
        txt_monto_total      = (TextView) v.findViewById(R.id.resumen_pedido_txt_monto_total);

        txt_estado           = (TextView) v.findViewById(R.id.resumen_pedido_txt_estado);
        txt_hora_entrega     = (TextView) v.findViewById(R.id.resumen_pedido_txt_hora_entrega);
        txt_tiempo_demora    = (TextView) v.findViewById(R.id.resumen_pedido_txt_tiempo_demora);
        txt_pedido_id        = (TextView) v.findViewById(R.id.resumen_pedido_txt_pedido_id);

        //txt_cantidad_descuento = (TextView) v.findViewById(R.id.resumen_pedido_txt_descuento_cantidad);
        txt_monto_descuento    = (TextView) v.findViewById(R.id.resumen_pedido_txt_descuento_monto);
        txt_monto_abona        = (TextView) v.findViewById(R.id.resumen_pedido_txt_monto_abona);


        btnEnviarPedido        =  (Button) v.findViewById(R.id.resumen_pedido_btn_enviar);
        btnEnviarPedido.setOnClickListener(this);
        checkStatusPedido();
        refreshTextViews();
        return v;
    }





    public void refreshTextViews(){
        try{
            //Actualizar valor de los textview, formatear valores pesos a #.##
            txt_direccion.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getStringDireccion());
            lbl_cantidad_kilos.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidadKilosFormatString());
            txt_cucuruchos.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCucuruchos().toString());
            lbl_cucuruchos_monto.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoCucuruchosFormatMoney());
            txt_cucharitas.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCucharitas().toString());
            txtEnvio.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getEnvioDomicilio());

            txt_pedido_id.setText(WorkNumber.getValue(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getId()).toString());
            String estado = GlobalValues.getINSTANCIA().ESTADOS[WorkNumber.getValue(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getEstadoId())];
            txt_estado.setText(estado);

            txt_hora_entrega.setText(WorkDate.parseDateToStringFormatHHmmss(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getHoraentrega()));

            txt_tiempo_demora.setText(WorkDate.calculateDiffereceDatesFormatMM(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getHoraentrega(),WorkDate.getNowDate() ));

            //txt_cantidad_descuento.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidadDescuento().toString());
            txt_monto_descuento.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoDescuentoFormatMoney());
            txt_monto_total.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoFormatMoney());
            lbl_kilos_monto.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoHeladoFormatMoney());

            txt_monto_abona.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoAbonaFormatMoney());

            btnEnviarPedido.setEnabled(true);
            if (GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getId()!=null){
                if (GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getId()> 0 ){
                    btnEnviarPedido.setEnabled(false);
                }
            }

        }catch(Exception e ){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void checkStatusPedido(){
        try{
            Pedido p = GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL;
            if(p.getId()!=null){
                if (p.getId() > 0 ){

                    HelperPedidos hp = new HelperPedidos(getContext());
                    hp.setOpcion(HelperPedidos.OPTION_CHECK_STATUS);
                    hp.setPedido(p);
                    hp.execute();
                    SystemClock.sleep(1000);
                }
            }
        }catch(Exception e ){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resumen_pedido_btn_enviar:
                enviarPedido();

        }
    }

    public void enviarPedido(){
        HelperPedidos hp = new HelperPedidos(getContext(),  GlobalValues.getINSTANCIA().OPTION_HELPER_ENVIO_PEDIDO, GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL );
        Toast.makeText(getContext(),"Enviando pedido..." ,Toast.LENGTH_LONG).show();
        hp.execute();


    }

}
