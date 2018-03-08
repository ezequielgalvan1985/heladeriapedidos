package adaptivex.pedidoscloud;

import android.content.DialogInterface;
import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.View.Pedidos.BlankFragment;
import adaptivex.pedidoscloud.View.Pedidos.CantidadSeleccionFragment;
import adaptivex.pedidoscloud.View.Pedidos.CargarDireccionFragment;
import adaptivex.pedidoscloud.View.Pedidos.CargarOtrosDatosFragment;
import adaptivex.pedidoscloud.View.Pedidos.ResumenPedidoFragment;
import adaptivex.pedidoscloud.View.Productos.ListadoHeladosFragment;
import ivb.com.materialstepper.progressMobileStepper;

public class NuevoPedidoActivity extends progressMobileStepper {
    List<Class> stepperFragmentList = new ArrayList<>();



    @Override
    public List<Class> init() {
        stepperFragmentList.add(CargarDireccionFragment.class);
        stepperFragmentList.add(CantidadSeleccionFragment.class);
        stepperFragmentList.add(ListadoHeladosFragment.class);
        stepperFragmentList.add(CargarOtrosDatosFragment.class);
        stepperFragmentList.add(BlankFragment.class);
        stepperFragmentList.add(ResumenPedidoFragment.class);

        //Resetear Pedido
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL = new Pedido();

        return stepperFragmentList;
    }
    @Override
    public void onStepperCompleted() {
        showCompletedDialog();
    }

    protected void showCompletedDialog(){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                NuevoPedidoActivity.this);

        // set title
        alertDialogBuilder.setTitle("Enviar Pedido?");
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        //Enviar Pedido
                        enviarPedido();
                        //Finalizar
                        finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                    }
                });

        // create alert dialog
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public boolean enviarPedido(){


        return true;
    }





}
