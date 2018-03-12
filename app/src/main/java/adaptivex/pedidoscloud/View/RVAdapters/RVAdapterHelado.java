package adaptivex.pedidoscloud.View.RVAdapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;

/**
 * Created by egalvan on 11/3/2018.
 */

public class RVAdapterHelado extends RecyclerView.Adapter<RVAdapterHelado.HeladoViewHolder> {
    private ArrayList<Producto> productos;
    private ContextWrapper cw;
    private Context ctx;

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void RVAdapterHelado(ArrayList<Producto> productos){
        this.setProductos(productos);
    }
    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public int getItemCount() {
        return getProductos().size();
    }


    @Override
    public HeladoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_helado, viewGroup, false);
        cw = new ContextWrapper(v.getContext());
        HeladoViewHolder pvh = new HeladoViewHolder(v,ctx, getProductos());
        return pvh;
    }

    @Override
    public void onBindViewHolder(HeladoViewHolder holder, int i) {
        //Completa el Item Helado dentro del recycle view
        holder.tvId.setText(String.valueOf(getProductos().get(i).getId()));
        holder.tvNombre.setText(getProductos().get(i).getNombre());

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }




    public static class HeladoViewHolder extends RecyclerView.ViewHolder
            implements  View.OnClickListener{
        private ArrayList<Producto> productos = new ArrayList<Producto>();
        private Context ctx;

        private CardView cv;
        private TextView tvNombre, tvId, tvOptions;
        private CheckBox chkHelado;

        OnHeadlineSelectedListener mCallback;

        public HeladoViewHolder(View itemView, Context ctx, ArrayList<Producto> productos) {
            super(itemView);
            mCallback = (OnHeadlineSelectedListener) ctx;
            this.productos = productos;
            this.ctx = ctx;
            itemView.setOnClickListener(this);

            cv = (CardView)itemView.findViewById(R.id.cvHelado);
            tvNombre  = (TextView)itemView.findViewById(R.id.item_helado_nombre);
            tvId      = (TextView)itemView.findViewById(R.id.item_helado_id);
            chkHelado = (CheckBox) itemView.findViewById(R.id.item_helado_chk);

            chkHelado.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int position  = getAdapterPosition();
            Producto producto = this.productos.get(position);
            switch (v.getId()){
                case R.id.item_helado_chk:
                    //Preguntar si esta marcado o no
                    Toast.makeText(ctx, "Click", Toast.LENGTH_SHORT).show();
                    break;



            }

        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListener {
        public void onProductoSelected(int position, Producto producto);
    }

}
