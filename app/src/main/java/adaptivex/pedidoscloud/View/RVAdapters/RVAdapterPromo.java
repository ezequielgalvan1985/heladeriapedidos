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

import java.util.ArrayList;

import adaptivex.pedidoscloud.Model.Promo;
import adaptivex.pedidoscloud.R;

/**
 * Created by ezequiel on 22/06/2016.
 */
public class RVAdapterPromo extends RecyclerView.Adapter<RVAdapterPromo.PromoViewHolder>
{
    private ArrayList<Promo> promos;
    private ContextWrapper cw;
    private Context ctx;

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public ArrayList<Promo> getPromos() {
        return promos;
    }

    public void RVAdapterPromo(ArrayList<Promo> promos){

        this.setPromos(promos);

    }

    @Override
    public int getItemCount() {
        return getPromos().size();
    }



    @Override
    public PromoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_producto, viewGroup, false);
        cw = new ContextWrapper(v.getContext());
        PromoViewHolder pvh = new PromoViewHolder(v,ctx, getPromos());
        return pvh;
    }

    @Override
    public void onBindViewHolder(PromoViewHolder productoViewHolder, int i) {
        productoViewHolder.ptvId.setText(String.valueOf(getPromos().get(i).getId()));
        productoViewHolder.pNombre.setText(getPromos().get(i).getNombre());
        productoViewHolder.pStock.setText(String.valueOf(getPromos().get(i).getStock()));
        productoViewHolder.pPrecio.setText("$"+ String.valueOf( getPromos().get(i).getPrecio()));


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setPromos(ArrayList<Promo> promos) {
        this.promos = promos;
    }


    public static class PromoViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ArrayList<Promo> promos = new ArrayList<Promo>();
        Context ctx;

        CardView cv;
        TextView txtNombre, txtDescripcion, txtImporteDescuento, txtFechaDesde, txtFechaHasta, txtPrecioPromo ;

        OnHeadlineSelectedListener mCallback;

        public PromoViewHolder(View itemView, Context ctx, ArrayList<Promo> promos) {
            super(itemView);

            mCallback = (OnHeadlineSelectedListener) ctx;
            this.promos = promos;
            this.ctx = ctx;
            ptvId = (TextView)itemView.findViewById(R.id.ptvId);
            pNombre = (TextView)itemView.findViewById(R.id.ptvNombre);
            //pStock = (TextView)itemView.findViewById(R.id.ptvStock);
            //pPrecio = (TextView)itemView.findViewById(R.id.ptvPrecio);
        }


        @Override
        public void onClick(View v) {
            int position  = getAdapterPosition();
            Promo producto = this.promos.get(position);
            Log.d("Debug: OnClick ", producto.getNombre());

            mCallback.onPromoSelected(position, producto);

        }
    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListener {
        public void onPromoSelected(int position, Promo producto);
    }
}