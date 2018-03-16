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
    private ArrayList<Producto> productos;
    private ContextWrapper cw;
    private Context ctx;
    private ArrayList<Pedidodetalle> listaHeladosSelected = new ArrayList<Pedidodetalle>();






    //Item que representa a un Helado dentro del recycle view,
    // este Item se almacenara en en un List para almacenar en memoria la seleccion
    // y luego utilizarla para recorrer el List y generar los registros




    //Inicializa el vector o Lista donde se almacenara el estado de cada registro(helado)
    private void initItems(){
        GlobalValues.getINSTANCIA().listaHeladosSeleccionados = new ArrayList<ItemHelado>();


        //Lista todos los helados
        for(Producto producto: productos){

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

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void RVAdapterHelado(ArrayList<Producto> productos){
        this.setProductos(productos);
    }

    public void setProductos(ArrayList<Producto> productos) {

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
        holder.tvId.setText(String.valueOf(getProductos().get(i).getId()));
        holder.tvNombre.setText(getProductos().get(i).getNombre());

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

        private ArrayList<Producto> productos = new ArrayList<Producto>();
        private Context ctx;

        private CardView cv;
        private TextView tvNombre, tvId, tvOptions, tvProporcionDescripcion;
        private CheckBox chkHelado;
        private SeekBar  proporcionHelado;

        OnHeadlineSelectedListener mCallback;





        public HeladoViewHolder(View itemView, Context ctx, ArrayList<Producto> productos) {
            super(itemView);

            this.productos = productos;
            this.ctx = ctx;

            itemView.setOnClickListener(this);

            cv = (CardView)itemView.findViewById(R.id.cvHelado);
            tvNombre  = (TextView)itemView.findViewById(R.id.item_helado_nombre);
            tvId      = (TextView)itemView.findViewById(R.id.item_helado_id);
            chkHelado = (CheckBox) itemView.findViewById(R.id.item_helado_chk);
            tvProporcionDescripcion = (TextView) itemView.findViewById(R.id.item_helado_cantidad_desc);

            chkHelado.setOnClickListener(this);
            proporcionHelado = (SeekBar) itemView.findViewById(R.id.item_helado_proporcion);

            proporcionHelado.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
            //Crear vector tipo productoid, proporcion
            //Cada click en check deberia cargar un registro en un vector con el id del producto y su actual medida
            //Cada vez que se mueva el seekbar se deberia registrar en el vector




        }


        @Override
        public void onClick(View v) {
            int position  = getAdapterPosition();
            Producto producto = this.productos.get(position);
            switch (v.getId()){
                case R.id.item_helado_chk:
                    //Preguntar si esta marcado o no
                    boolean newState = !GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(position).isChecked();
                    GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(position).setChecked( newState);
                    if (newState){
                        proporcionHelado.setVisibility(View.VISIBLE);
                        tvProporcionDescripcion.setVisibility(View.VISIBLE);
                    }else{
                        proporcionHelado.setVisibility(View.INVISIBLE);
                        tvProporcionDescripcion.setVisibility(View.INVISIBLE);
                    }
                    break;



            }

        }


    }
    // La actividad contenedora debe implementar esta interfaz
    public interface OnHeadlineSelectedListener {
        public void onProductoSelected(int position, Producto producto);
    }

}
