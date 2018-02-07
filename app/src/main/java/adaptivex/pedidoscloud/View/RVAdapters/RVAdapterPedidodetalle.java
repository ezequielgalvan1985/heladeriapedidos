package adaptivex.pedidoscloud.View.RVAdapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.R;

import java.util.List;

/**
 * Created by ezequiel on 22/06/2016.
 */
public class RVAdapterPedidodetalle extends RecyclerView.Adapter<RVAdapterPedidodetalle.PedidodetalleViewHolder>{
    private List<Pedidodetalle> pedidodetalles;
    private ContextWrapper cw;
    private Context ctx;


    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }


    public void RVAdapterPedidodetalle(List<Pedidodetalle> pedidodetalles){
        this.setPedidodetalles(pedidodetalles);
    }

    @Override
    public int getItemCount() {
        return pedidodetalles.size();
    }



    @Override
    public PedidodetalleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pedidodetalle, viewGroup, false);
        cw = new ContextWrapper(v.getContext());
        PedidodetalleViewHolder pvh = new PedidodetalleViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PedidodetalleViewHolder pedidodetalleViewHolder, int i) {
        pedidodetalleViewHolder.tvPedidoId.setText(String.valueOf(pedidodetalles.get(i).getPedidoId()));
        pedidodetalleViewHolder.tvPedidoIdTmp.setText(String.valueOf(pedidodetalles.get(i).getPedidoTmpId()));

        pedidodetalleViewHolder.tvIpdProductoId.setText(String.valueOf(pedidodetalles.get(i).getProductoId()));
        pedidodetalleViewHolder.tvIpdProductoNombre.setText(pedidodetalles.get(i).getProducto().getNombre());
        pedidodetalleViewHolder.tvCantidad.setText(String.valueOf(pedidodetalles.get(i).getCantidad()));
        pedidodetalleViewHolder.tvPreciounitario.setText(String.valueOf(pedidodetalles.get(i).getPreciounitario()));
        pedidodetalleViewHolder.tvMonto.setText(String.valueOf(pedidodetalles.get(i).getMonto()));


        //pedidodetalleViewHolder.tvStock.setText(String.valueOf(pedidodetalles.get(i).getStock()));


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setPedidodetalles(List<Pedidodetalle> pedidodetalles) {
        this.pedidodetalles = pedidodetalles;
    }


    public static class PedidodetalleViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvPedidoId;
        TextView tvPedidoIdTmp;
        TextView tvIpdProductoId;
        TextView tvCantidad;
        TextView tvPreciounitario;
        TextView tvMonto;
        //TextView tvStock;
        TextView tvIpdProductoNombre;


        public PedidodetalleViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvPedidodetalle);
            tvPedidoId = (TextView)itemView.findViewById(R.id.tvPedidoId);
            tvPedidoIdTmp =(TextView)itemView.findViewById(R.id.tvPedidoIdTmp);
            tvIpdProductoId = (TextView)itemView.findViewById(R.id.tvIpdProductoId);
            tvCantidad = (TextView)itemView.findViewById(R.id.tvCantidad);
            tvPreciounitario = (TextView)itemView.findViewById(R.id.tvPreciounitario);
            tvMonto = (TextView)itemView.findViewById(R.id.tvMonto);
            //tvStock = (TextView)itemView.findViewById(R.id.tvFpdStock);

            tvIpdProductoNombre  = (TextView)itemView.findViewById(R.id.tvIpdProductoNombre);

        }




    }

}