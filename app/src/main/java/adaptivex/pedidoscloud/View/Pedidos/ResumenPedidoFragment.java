package adaptivex.pedidoscloud.View.Pedidos;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.Productos.ListadoHeladosFragment;
import ivb.com.materialstepper.stepperFragment;

public class ResumenPedidoFragment extends Fragment {

    ItemsHeladoAdapter myItemsListAdapter;
    ArrayList<Pedidodetalle> items;
    private TextView lbl_cantidad_kilos, lbl_kilos_monto, lbl_cucuruchos_monto, lbl_monto_total;




    public ResumenPedidoFragment() {

    }


    public static ResumenPedidoFragment newInstance(String param1, String param2) {
        ResumenPedidoFragment fragment = new ResumenPedidoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resumen_pedido, container, false);
        TextView direccion = (TextView) v.findViewById(R.id.resumen_pedido_direccion);
        TextView cantidad = (TextView) v.findViewById(R.id.resumen_pedido_cantidad);
        TextView cucuruchos = (TextView) v.findViewById(R.id.resumen_pedido_cucuruchos);
        TextView cucharitas = (TextView) v.findViewById(R.id.resumen_pedido_cucharitas);

        lbl_cantidad_kilos    = (TextView) v.findViewById(R.id.cargar_cantidad_lbl_cantidad_kilos);
        lbl_kilos_monto       = (TextView) v.findViewById(R.id.cargar_cantidad_lbl_kilos_monto);
        lbl_cucuruchos_monto  = (TextView) v.findViewById(R.id.cargar_cantidad_lbl_cucuruchos_monto);
        lbl_monto_total       = (TextView) v.findViewById(R.id.cargar_cantidad_lbl_monto_total);

        direccion.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getStringDireccion());
        cucuruchos.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCucuruchos().toString());
        cucharitas.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCucharitas().toString());
        cantidad.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidadKilos().toString() +"Kg");

        //cargar listado de productos
        ListView lv = (ListView) v.findViewById(R.id.resumen_pedido_helados);
        items = GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getDetalles();
        myItemsListAdapter = new ItemsHeladoAdapter(getContext(), items);
        lv.setAdapter(myItemsListAdapter);
        refreshTextViews();
        return v;
    }

    public void refreshTextViews(){
        try{
            //Actualizar valor de los textview, formatear valores pesos a #.##
            lbl_cantidad_kilos.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getKilosHeladosString());
            lbl_kilos_monto.setText("$ " + String.valueOf(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoHelados()));
            lbl_cucuruchos_monto.setText("$ " + String.valueOf(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMontoCucuruchos()));
            lbl_monto_total.setText("$ " + String.valueOf(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getMonto()));
        }catch(Exception e ){
            Toast.makeText(getContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    public class Item {
        Producto producto;

        Item(Producto t){
            producto = t;
        }
    }

    static class ViewHolder {
        TextView text;
    }


    public class ItemsHeladoAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<Pedidodetalle> list;

        ItemsHeladoAdapter(Context c, ArrayList<Pedidodetalle> l) {
            context = c;
            list = l;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }



        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // reuse views
            ViewHolder viewHolder = new ViewHolder();
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.resumen_helado_row, null);
                viewHolder.text = (TextView) rowView.findViewById(R.id.tvResumenHeladoNombre);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }
            final String itemStr = list.get(position).getProducto().getNombre().toUpperCase().toString();
            viewHolder.text.setText(itemStr);
            return rowView;
        }
    }






}
