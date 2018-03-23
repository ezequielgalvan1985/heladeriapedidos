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

import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;

import java.util.ArrayList;

/**
 * Created by ezequiel on 22/06/2016.
 */
public class RVAdapterProducto extends RecyclerView.Adapter<RVAdapterProducto.ProductoViewHolder>
{
    private ArrayList<Object> productos;
    private ContextWrapper cw;
    private Context ctx;

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public ArrayList<Object> getProductos() {
        return productos;
    }

    public void RVAdapterProducto(ArrayList<Object> productos){

        this.setProductos(productos);

    }

    @Override
    public int getItemCount() {
        return getProductos().size();
    }



    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_producto, viewGroup, false);
        cw = new ContextWrapper(v.getContext());
        ProductoViewHolder pvh = new ProductoViewHolder(v,ctx, getProductos());
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder productoViewHolder, int i) {

        Producto item = (Producto) getProductos().get(i);

        productoViewHolder.ptvId.setText(item.getId().toString());
        productoViewHolder.pNombre.setText(item.getNombre());
        productoViewHolder.pDescripcion.setText(item.getDescripcion());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setProductos(ArrayList<Object> productos) {
        this.productos = productos;
    }


    public static class ProductoViewHolder extends RecyclerView.ViewHolder{
        ArrayList<Object> productos = new ArrayList<Object>();
        Context ctx;
        TextView pNombre,ptvId, pDescripcion;


        public ProductoViewHolder(View itemView, Context ctx, ArrayList<Object> productos) {
            super(itemView);
            this.productos = productos;
            this.ctx = ctx;
            ptvId        = (TextView)itemView.findViewById(R.id.item_producto_id);
            pNombre      = (TextView)itemView.findViewById(R.id.item_producto_nombre);
            pDescripcion = (TextView)itemView.findViewById(R.id.item_producto_descripcion);

        }


    }

}