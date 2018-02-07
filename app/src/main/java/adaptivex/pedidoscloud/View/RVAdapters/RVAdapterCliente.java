package adaptivex.pedidoscloud.View.RVAdapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Model.Cliente;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ezequiel on 22/06/2016.
 */
public class RVAdapterCliente extends RecyclerView.Adapter<RVAdapterCliente.ClienteViewHolder>{
    private ArrayList<Cliente> clientes;
    private ContextWrapper cw;
    private Context ctx;

    public Context getCtx() {
        return ctx;
    }


    public void RVAdapterCliente(ArrayList<Cliente> clientes){
        this.setClientes(clientes);
    }

    @Override
    public int getItemCount() {
        return getClientes().size();
    }



    @Override
    public ClienteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cliente, viewGroup, false);
        cw = new ContextWrapper(v.getContext());
        ClienteViewHolder pvh = new ClienteViewHolder(v, getCtx(), getClientes());
        return pvh;
    }

    @Override
    public void onBindViewHolder(ClienteViewHolder clienteViewHolder, int i) {
        clienteViewHolder.tvContacto.setText(getClientes().get(i).getContacto());
        //clienteViewHolder.tvNdoc.setText(getClientes().get(i).getNdoc());
        clienteViewHolder.tvRazonsocial.setText(getClientes().get(i).getRazonsocial());
        clienteViewHolder.tvDireccion.setText(getClientes().get(i).getDireccion());
        clienteViewHolder.tvTelefono.setText(getClientes().get(i).getTelefono());
        //clienteViewHolder.tvEmail.setText(getClientes().get(i).getEmail());


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }


    public static class ClienteViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        Context ctx;
        OnHeadlineSelectedListener mCallback;

        CardView cv;
        TextView tvContacto;
        //TextView tvNdoc;
        TextView tvRazonsocial;
        TextView tvDireccion;
        TextView tvTelefono;
        //TextView tvEmail;
        ImageView ivImagen;



        public ClienteViewHolder(View itemView, Context ctx, ArrayList<Cliente> clientes) {
            super(itemView);

            mCallback = (OnHeadlineSelectedListener) ctx;
            this.clientes = clientes;
            this.ctx = ctx;
            itemView.setOnClickListener(this);

            cv = (CardView)itemView.findViewById(R.id.cvCliente);
            tvContacto = (TextView)itemView.findViewById(R.id.tvContactoCli);
            //tvNdoc = (TextView)itemView.findViewById(R.id.tvNdocCli);
            tvRazonsocial = (TextView)itemView.findViewById(R.id.tvRazonsocialCli);
            tvDireccion = (TextView)itemView.findViewById(R.id.tvDireccionCli);
            tvTelefono = (TextView)itemView.findViewById(R.id.tvTelefonoCli);
            //tvEmail = (TextView)itemView.findViewById(R.id.tvEmailCli);
            //ivImagen = (ImageView)itemView.findViewById(R.id.pivImagen);

            //

        }


        @Override
        public void onClick(View v) {
            //Si viene de menu Nuevo pedido, generar el nuevo pedido, con el cliente seleccionado
            // Si Marca nuevo pedido es true,  entonces si se genera realizar logica Nuevo pedido y poner marca de nuevo pedido en false
            // de lo contrario NO.

            if (GlobalValues.getINSTANCIA().isVgFlagMenuNuevoPedido()){

                int position  = getAdapterPosition();
                Cliente cliente = this.clientes.get(position);
                Log.d("Debug: OnClick clie", String.valueOf(cliente.getId()));

                PedidoController gestdb = new PedidoController(v.getContext());
                Date fecha = new Date();
                Calendar cal = Calendar.getInstance();
                //cal.add(Calendar.DATE, 1);
                fecha = cal.getTime();

                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                String fechaDMY = df1.format(fecha);

                Pedido pedido = new Pedido();

                pedido.setEstadoId(GlobalValues.consPedidoEstadoNuevo);
                pedido.setCliente_id(cliente.getId());
                pedido.setCreated(fechaDMY);
                pedido.setId(0);

                gestdb.abrir();
                long id = gestdb.agregar(pedido);

               // pedido = gestdb.buscar(id);
                gestdb.cerrar();

                GlobalValues.getINSTANCIA().setVgClienteSelecionado(pedido.getCliente_id());
                GlobalValues.getINSTANCIA().setVgPedidoIdActual(id);
                gestdb.cerrar();

                Toast.makeText(v.getContext(), "Generando Nuevo Pedido  "+ String.valueOf(id) , Toast.LENGTH_SHORT).show();
                Log.d("Debug: Generar pedido ", String.valueOf(GlobalValues.getINSTANCIA().getVgPedidoIdActual()));

                // Esta bandera hace q no se genere nuevo pedido cada vez que se hace click en listado de clientes
                // excepto que haya hecho click en nuevo pedido
                GlobalValues.getINSTANCIA().setVgFlagMenuNuevoPedido(false);
                this.mCallback.onClienteSelected(position, cliente);
            }
            //
        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListener {
        public void onClienteSelected(int position, Cliente cliente);
    }

}