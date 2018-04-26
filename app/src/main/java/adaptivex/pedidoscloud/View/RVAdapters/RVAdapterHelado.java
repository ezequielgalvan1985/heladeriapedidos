package adaptivex.pedidoscloud.View.RVAdapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.ItemHelado;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;

/**
 * Created by egalvan on 11/3/2018.
 */

public class RVAdapterHelado extends RecyclerView.Adapter<RVAdapterHelado.HeladoViewHolder> {
    private ArrayList<Object> productos;
    private ContextWrapper cw;
    private Context ctx;
    private ArrayList<Pedidodetalle> listaHeladosSelected = new ArrayList<Pedidodetalle>();
    private ArrayList<ItemHelado> listaItemsHelados = new ArrayList<ItemHelado>();







    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public ArrayList<Object> getProductos() {
        return productos;
    }

    public void RVAdapterHelado(ArrayList<Object> productos){
        this.setProductos(productos);
    }

    public void setProductos(ArrayList<Object> productos) {
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

        HeladoViewHolder pvh = new HeladoViewHolder(v, ctx, getProductos());

        return pvh;
    }

    @Override
    public void onBindViewHolder(HeladoViewHolder holder, int i) {
        //Completa el Item Helado dentro del recycle view


        ItemHelado item = getListaItemsHelados().get(i);
        Producto p = item.getHelado();
        holder.tvId.setText(String.valueOf(p.getId()));
        holder.tvNombre.setText(p.getNombre());

            //Cargar en memoria el Item Seleccionado
        if (item.isChecked()){
            GlobalValues.getINSTANCIA().listaHeladosSeleccionados.set(i,item);
            holder.seekProporcionHelado.setProgress(item.getProporcion());
            holder.tvProporcionDescripcion.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getProporcionDesc(item.getPedidodetalle().getProporcionHelado()));
            holder.seekProporcionHelado.refreshDrawableState();
            holder.seekProporcionHelado.setVisibility(View.VISIBLE);
            holder.tvProporcionDescripcion.setVisibility(View.VISIBLE);
        }


    }




    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public ArrayList<Pedidodetalle> getListaHeladosSelected() {
        return listaHeladosSelected;
    }

    public void setListaHeladosSelected(ArrayList<Pedidodetalle> listaHeladosSelected) {
        this.listaHeladosSelected = listaHeladosSelected;
    }

    public ArrayList<ItemHelado> getListaItemsHelados() {
        return listaItemsHelados;
    }

    public void setListaItemsHelados(ArrayList<ItemHelado> listaItemsHelados) {
        this.listaItemsHelados = listaItemsHelados;
    }


    public static class HeladoViewHolder extends RecyclerView.ViewHolder
            implements  View.OnClickListener{

        private ArrayList<Object> productos = new ArrayList<Object>();
        private Context ctx;

        private TextView tvNombre, tvId, tvOptions, tvProporcionDescripcion;
        private CheckBox chkHelado;
        private SeekBar  seekProporcionHelado;






        public HeladoViewHolder(View itemView, Context ctx, ArrayList<Object> productos) {
            super(itemView);

            this.productos = productos;
            this.ctx = ctx;

            itemView.setOnClickListener(this);


            tvNombre  = (TextView)itemView.findViewById(R.id.item_helado_nombre);
            tvId      = (TextView)itemView.findViewById(R.id.item_helado_id);
            chkHelado = (CheckBox) itemView.findViewById(R.id.item_helado_chk);
            tvProporcionDescripcion = (TextView) itemView.findViewById(R.id.item_helado_cantidad_desc);

            chkHelado.setOnClickListener(this);
            seekProporcionHelado = (SeekBar) itemView.findViewById(R.id.item_helado_proporcion);

            seekProporcionHelado.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressChangedValue = 0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressChangedValue = progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int position  = getAdapterPosition();
                    if (progressChangedValue >= 0 && progressChangedValue <= 50){
                        GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(position).setProporcion(progressChangedValue);
                        tvProporcionDescripcion.setText("Poco");
                    }
                    if (progressChangedValue >= 50 && progressChangedValue <= 100){
                        GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(position).setProporcion(progressChangedValue);
                        tvProporcionDescripcion.setText("Equilibrado");
                    }
                    if (progressChangedValue >= 100 && progressChangedValue <= 150){
                        GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(position).setProporcion(progressChangedValue);
                        tvProporcionDescripcion.setText("Mucho");
                    }


                }
            });

        }


        @Override
        public void onClick(View v) {
            int position  = getAdapterPosition();
            Producto producto = (Producto)this.productos.get(position);
            switch (v.getId()){
                case R.id.item_helado_chk:
                    //Preguntar si esta marcado o no
                    boolean newState = !GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(position).isChecked();
                    GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(position).setChecked( newState);
                    if (newState){
                        seekProporcionHelado.setVisibility(View.VISIBLE);
                        tvProporcionDescripcion.setVisibility(View.VISIBLE);
                    }else{
                        seekProporcionHelado.setVisibility(View.INVISIBLE);
                        tvProporcionDescripcion.setVisibility(View.INVISIBLE);
                    }
                    break;



            }

        }


    }


}
