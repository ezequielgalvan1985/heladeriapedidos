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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_promo, viewGroup, false);
        cw = new ContextWrapper(v.getContext());
        PromoViewHolder pvh = new PromoViewHolder(v,ctx, getPromos());
        return pvh;
    }

    @Override
    public void onBindViewHolder(PromoViewHolder productoViewHolder, int i) {
        productoViewHolder.txtNombre.setText(String.valueOf(getPromos().get(i).getNombre()));
        productoViewHolder.txtDescripcion.setText(getPromos().get(i).getDescripcion());
        productoViewHolder.txtCantKilos.setText(String.valueOf(getPromos().get(i).getCantKilos()));
        productoViewHolder.txtImporteDescuento.setText(String.valueOf( getPromos().get(i).getImporteDescuento()));
        productoViewHolder.txtFechaDesde.setText(getPromos().get(i).getFechaDesdeFormatDMY());
        productoViewHolder.txtFechaHasta.setText(String.valueOf( getPromos().get(i).getFechaHastaFormatDMY()));
        productoViewHolder.txtPrecioPromo.setText(String.valueOf( getPromos().get(i).getPrecioPromo()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setPromos(ArrayList<Promo> promos) {
        this.promos = promos;
    }


    public static class PromoViewHolder extends RecyclerView.ViewHolder{
        ArrayList<Promo> promos = new ArrayList<Promo>();
        Context ctx;
        TextView txtNombre, txtDescripcion, txtCantKilos, txtImporteDescuento, txtFechaDesde, txtFechaHasta, txtPrecioPromo ;


        public PromoViewHolder(View itemView, Context ctx, ArrayList<Promo> promos) {
            super(itemView);
            this.promos = promos;
            this.ctx = ctx;
            txtNombre              = (TextView)itemView.findViewById(R.id.item_promo_txt_nombre);
            txtDescripcion         = (TextView)itemView.findViewById(R.id.item_promo_txt_descripcion);
            txtCantKilos           = (TextView)itemView.findViewById(R.id.item_promo_txt_cantidad_kilos);
            txtImporteDescuento    = (TextView)itemView.findViewById(R.id.item_promo_txt_importe_descuento);
            txtFechaDesde          = (TextView)itemView.findViewById(R.id.item_promo_txt_fechadesde);
            txtFechaHasta          = (TextView)itemView.findViewById(R.id.item_promo_txt_fechahasta);
            txtPrecioPromo         = (TextView)itemView.findViewById(R.id.item_promo_txt_precio_monto);
        }

    }

}