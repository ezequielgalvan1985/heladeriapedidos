package adaptivex.pedidoscloud.View.RVAdapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import adaptivex.pedidoscloud.Model.Hojaruta;
import adaptivex.pedidoscloud.R;

import java.util.ArrayList;

/**
 * Created by ezequiel on 22/06/2016.
 */
public class RVAdapterHojaruta extends RecyclerView.Adapter<RVAdapterHojaruta.HojarutaViewHolder>{
    private ArrayList<Hojaruta> Hojarutas;
    private ContextWrapper cw;
    private Context ctx;

    public Context getCtx() {
        return ctx;
    }
    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
    public ArrayList<Hojaruta> getHojarutas() {
        return Hojarutas;
    }



    public void RVAdapterHojaruta(ArrayList<Hojaruta> Hojarutas){
        this.setHojarutas(Hojarutas);
    }


    @Override
    public int getItemCount() {
        return Hojarutas.size();
    }



    @Override
    public HojarutaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hojaruta, viewGroup, false);
        cw = new ContextWrapper(v.getContext());
        HojarutaViewHolder pvh = new HojarutaViewHolder(v, getCtx(), getHojarutas());
        return pvh;
    }

    @Override
    public void onBindViewHolder(HojarutaViewHolder HojarutaViewHolder, int i) {
        HojarutaViewHolder.tvId.setText("ID: "+ String.valueOf(Hojarutas.get(i).getId()));
        HojarutaViewHolder.tvUser_id.setText("User: "+ Hojarutas.get(i).getUser_id());
        HojarutaViewHolder.tvDia_id.setText("Dia: "+ String.valueOf(Hojarutas.get(i).getDia_id()));
        HojarutaViewHolder.tvTitulo.setText("Titulo: "+ String.valueOf("Nro. Hojaruta: "+ Hojarutas.get(i).getTitulo()));
        HojarutaViewHolder.tvNotas.setText("Notas: "+ String.valueOf(Hojarutas.get(i).getNotas()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setHojarutas(ArrayList<Hojaruta> Hojarutas) {
        this.Hojarutas = Hojarutas;
    }



    public static class HojarutaViewHolder extends RecyclerView.ViewHolder {
        ArrayList<Hojaruta> Hojarutas = new ArrayList<Hojaruta>();
        Context ctx;
        OnHeadlineSelectedListener mCallback;
        CardView cv;
        TextView tvId, tvDia_id, tvUser_id, tvNotas, tvTitulo;

        public HojarutaViewHolder(View itemView, Context ctx, ArrayList<Hojaruta> Hojarutas) {
            super(itemView);

            mCallback = (OnHeadlineSelectedListener) ctx;
            this.Hojarutas = Hojarutas;
            this.ctx = ctx;
            cv = (CardView) itemView.findViewById(R.id.cvHojaruta);
            tvId = (TextView) itemView.findViewById(R.id.tvIhfId);
            tvDia_id = (TextView) itemView.findViewById(R.id.tvIhfDia_id);
            tvUser_id = (TextView) itemView.findViewById(R.id.tvIhfUser_id);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvIhfTitulo);
            tvNotas = (TextView) itemView.findViewById(R.id.tvIhfNotas);

        }
    }
        // La actividad contenedora debe implementar esta interfaz
        public interface OnHeadlineSelectedListener {
            public void onHojarutaSelected(int position, Hojaruta Hojaruta);
        }





}