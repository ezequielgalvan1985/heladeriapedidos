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




    public void RVAdapterProducto(ArrayList<Producto> productos){

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
        productoViewHolder.ptvId.setText(String.valueOf(getProductos().get(i).getId()));
        productoViewHolder.pNombre.setText(getProductos().get(i).getNombre());
        productoViewHolder.pStock.setText(String.valueOf(getProductos().get(i).getStock()));
        productoViewHolder.pPrecio.setText("$"+ String.valueOf( getProductos().get(i).getPrecio()));
       // productoViewHolder.pImagen.setText(productos.get(i).getImagen());
       // productoViewHolder.pImagenurl.setText(productos.get(i).getImagenurl());

        //productoViewHolder.pPrecio.setImageResource(productos.get(i).getPrecio());

       // productoViewHolder.pIV.setText(productos.get(i).getImagenurl());

        /* codigo para mostrar imagen
        Bitmap imgprueba;

        File dirImages = cw.getDir("Imagenes", Context.MODE_PRIVATE);
        File myPath = new File(dirImages, getProductos().get(i).getImagen().toString());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(myPath);
            imgprueba = BitmapFactory.decodeStream(fis);
            productoViewHolder.pivImagen.setImageBitmap(imgprueba);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }


    public static class ProductoViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ArrayList<Producto> productos = new ArrayList<Producto>();
        Context ctx;

        CardView cv;
        TextView pNombre,ptvId;
        TextView pDescripcion;
       // TextView pImagen;
        //TextView pImagenurl;
        TextView pPrecio;
        TextView pStock;
        ImageView pivImagen;
        OnHeadlineSelectedListener mCallback;

        public ProductoViewHolder(View itemView, Context ctx, ArrayList<Producto> productos) {
            super(itemView);

            mCallback = (OnHeadlineSelectedListener) ctx;
            this.productos = productos;
            this.ctx = ctx;
            itemView.setOnClickListener(this);
            cv = (CardView)itemView.findViewById(R.id.cvProducto);
            ptvId = (TextView)itemView.findViewById(R.id.ptvId);
            pNombre = (TextView)itemView.findViewById(R.id.ptvNombre);
            pStock = (TextView)itemView.findViewById(R.id.ptvStock);
            pPrecio = (TextView)itemView.findViewById(R.id.ptvPrecio);
        }


        @Override
        public void onClick(View v) {
            int position  = getAdapterPosition();
            Producto producto = this.productos.get(position);
            Log.d("Debug: OnClick ", producto.getNombre());

            mCallback.onProductoSelected(position, producto);

        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListener {
        public void onProductoSelected(int position, Producto producto);
    }
}