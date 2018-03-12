package adaptivex.pedidoscloud;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Servicios.HelperPedidos;
import adaptivex.pedidoscloud.View.Pedidos.BlankFragment;
import adaptivex.pedidoscloud.View.Pedidos.CantidadSeleccionFragment;
import adaptivex.pedidoscloud.View.Pedidos.CargarDireccionFragment;
import adaptivex.pedidoscloud.View.Pedidos.CargarOtrosDatosFragment;
import adaptivex.pedidoscloud.View.Pedidos.ResumenPedidoFragment;
import adaptivex.pedidoscloud.View.Productos.ListadoHeladosFragment;
import ivb.com.materialstepper.progressMobileStepper;

public class NuevoPedidoActivity extends AppCompatActivity {
    //List<Class> stepperFragmentList = new ArrayList<>();

    /*
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
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crearNuevoPedido();
        setContentView(R.layout.activity_nuevo_pedido);
        GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL = new Pedido();
        Fragment fragment = new CargarDireccionFragment();
        GlobalValues.getINSTANCIA().CURRENT_FRAGMENT_NUEVO_PEDIDO = GlobalValues.getINSTANCIA().NP_CARGAR_DIRECCION;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_nuevo_pedido, fragment).addToBackStack(null)
                .commit();



    }




    public long crearNuevoPedido(){
        try{

            PedidoController gestdb = new PedidoController(this);
            Date fecha = new Date();
            Calendar cal = Calendar.getInstance();
            fecha = cal.getTime();
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            String fechaDMY = df1.format(fecha);
            //Nuevo Pedido
            Pedido pedido = new Pedido();
            pedido.setEstadoId(GlobalValues.consPedidoEstadoNuevo);
            pedido.setCliente_id(GlobalValues.getINSTANCIA().getUserlogued().getId());
            pedido.setCreated(fechaDMY);
            pedido.setId(0);
            gestdb.abrir();
            long id = gestdb.agregar(pedido);
            pedido.setIdTmp(id);
            gestdb.cerrar();
            GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL = new Pedido();
            GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL = pedido;

            gestdb.cerrar();
            Toast.makeText(this, "Generando Nuevo Pedido  "+ String.valueOf(id) , Toast.LENGTH_SHORT).show();
            return id;
        }catch (Exception e ){
            Toast.makeText(this, "Error: " +e.getMessage(),Toast.LENGTH_LONG).show();
            return 0;
        }
    }





    public boolean enviarPedido(){
        try{
            //SE graba el pedido en la base de datos android
            Pedido p;
            p = GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL;
            PedidoController pc = new PedidoController(this.getBaseContext());
            Long idTmp = pc.abrir().agregar(p);
            pc.cerrar();

            //Se envia a la web Base de datos MYSQL
            HelperPedidos hp = new HelperPedidos(this.getBaseContext(), idTmp, GlobalValues.getINSTANCIA().OPTION_HELPER_ENVIO_PEDIDO);
            hp.execute();
            return true;
        }catch(Exception e){
            Toast.makeText(getBaseContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }
    }





}
