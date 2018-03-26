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


    //Inicializa el vector o Lista donde se almacenara el estado de cada registro(helado)
    private void initItems(){
        GlobalValues.getINSTANCIA().listaHeladosSeleccionados = new ArrayList<ItemHelado>();

        //Lista todos los helados
        for(Object object: productos){
            Producto producto = (Producto) object;
            ItemHelado item = new ItemHelado(producto, false, 75);
            GlobalValues.getINSTANCIA().listaHeladosSeleccionados.add(item);
        }
    }





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
        initItems();
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
        Producto p = (Producto) getProductos().get(i);

        holder.tvId.setText(String.valueOf(p.getId()));
        holder.tvNombre.setText(p.getNombre());

        // Chequear si producto esta seleccionado
        Pedidodetalle pd = checkHelado(p);



        if (pd!=null){
            //Cargar en memoria el Item Seleccionado
            ItemHelado item = new ItemHelado(p, true, pd.getProporcionHelado(),pd);
            GlobalValues.getINSTANCIA().listaHeladosSeleccionados.set(i,item);

            holder.chkHelado.setChecked(true);
            holder.seekProporcionHelado.setVisibility(View.VISIBLE);
            holder.seekProporcionHelado.setProgress(pd.getProporcionHelado());
            holder.seekProporcionHelado.refreshDrawableState();
            holder.tvProporcionDescripcion.setVisibility(View.VISIBLE);
            holder.tvProporcionDescripcion.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getProporcionDesc(pd.getProporcionHelado()));
        }


    }

    public Pedidodetalle checkHelado(Producto p){
        //recorrer lista de itemSelecetd
        try{
            Pedidodetalle pdSelected = null;
            if (listaHeladosSelected != null){
                if (listaHeladosSelected.size()> 0 ){
                    for(Pedidodetalle pd: listaHeladosSelected){
                        if (pd.getProducto().getId()==p.getId()) {
                            // El Item fue seleccionado
                            pdSelected =  pd;
                        }
                    }
                }
            }
            return pdSelected;

        }catch (Exception e ){
            Toast.makeText(ctx,"Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
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
