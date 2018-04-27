package adaptivex.pedidoscloud.View.RVAdapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidodetalleController;
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
    private ArrayList<Object> listaHelados = new ArrayList<Object>();
    private long pedido_android_id ;
    private Integer pedido_nro_pote ;





    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public ArrayList<Object> getProductos() {
        return productos;
    }

    public void RVAdapterHelado( ){

    }

    public void init( Context c, long pedido_android_id, Integer pedido_nro_pote){
        /*
        * 1 GlobalValues.getINSTANCIA().listaHeladosSeleccionados es un arraylist de ItemHelado, con todos los productos, en el evento load, machean con los items seleccionados del pote
        *       este actua de espejo a lo que vemos en el formulario
        *
        * 2 listaItemsHelados es un arraylist de ItemHelado que solo contiene los seleccionados en el pote
        * 3
        * */
        setCtx(c);
        setPedido_android_id(pedido_android_id);
        setPedido_nro_pote(pedido_nro_pote);
        GlobalValues.getINSTANCIA().listaHeladosSeleccionados = cargarListaItemsHelados();

    }

    private ArrayList<ItemHelado> cargarListaItemsHelados ( ){
        /*Genera lista de items */
        ArrayList<ItemHelado> arrayListItemHelado = new ArrayList<ItemHelado>();


        PedidodetalleController pdc = new PedidodetalleController(getCtx());
        Cursor c = pdc.abrir().findByPedidoAndroidIdAndNroPote(getPedido_android_id(), getPedido_nro_pote());
        pdc.cerrar();
        ArrayList<Pedidodetalle> listaHeladosSelected = new ArrayList<Pedidodetalle>();
        listaHeladosSelected = pdc.abrir().parseCursorToArrayList(c);

        ProductoController dbHelper = new ProductoController(getCtx());
        listaHelados = dbHelper.abrir().findAllToArrayList();
        setProductos(listaHelados);

        //crear la lista de Items helado
        //Recorrer lista de productos

        for(Object o: listaHelados){
            Producto p       = (Producto) o;
            Pedidodetalle pd = checkHelado(p,listaHeladosSelected);
            ItemHelado ih = new ItemHelado(p, false,75);

            if (pd!=null){
                ih.setPedidodetalle(pd);
                ih.setChecked(true);
                ih.setProporcion(pd.getProporcionHelado());
            }
            arrayListItemHelado.add(ih);
        }
        return arrayListItemHelado;
    }


    public Pedidodetalle checkHelado(Producto p, ArrayList<Pedidodetalle> listaHeladosSelected ){
        //devuelve el pedidodetalle que coincide con el Producto,
        // y devuelve el pedido detalle para el producto
        //Pregunta si el producto esta dentro de los seleccionados y devuelve el pedidodetalle asociado
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

            return null;
        }
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


        ItemHelado item = GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(i);

        Producto p = item.getHelado();
        holder.tvId.setText(String.valueOf(p.getId()));
        holder.tvNombre.setText(p.getNombre());

            //Cargar en memoria el Item Seleccionado
        if (item.isChecked()){
            holder.chkHelado.setChecked(true);
            holder.seekProporcionHelado.setProgress(item.getProporcion());
            holder.tvProporcionDescripcion.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getProporcionDesc(item.getPedidodetalle().getProporcionHelado()));
            holder.seekProporcionHelado.refreshDrawableState();
            holder.seekProporcionHelado.setVisibility(View.VISIBLE);
            holder.tvProporcionDescripcion.setVisibility(View.VISIBLE);
        }else{
            holder.seekProporcionHelado.setVisibility(View.INVISIBLE);
            holder.tvProporcionDescripcion.setVisibility(View.INVISIBLE);
            holder.chkHelado.setChecked(false);
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

    public long getPedido_android_id() {
        return pedido_android_id;
    }

    public void setPedido_android_id(long pedido_android_id) {
        this.pedido_android_id = pedido_android_id;
    }

    public Integer getPedido_nro_pote() {
        return pedido_nro_pote;
    }

    public void setPedido_nro_pote(Integer pedido_nro_pote) {
        this.pedido_nro_pote = pedido_nro_pote;
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
