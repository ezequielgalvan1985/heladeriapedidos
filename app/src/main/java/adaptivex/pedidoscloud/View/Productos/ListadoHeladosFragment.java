package adaptivex.pedidoscloud.View.Productos;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;
import ivb.com.materialstepper.stepperFragment;

public class ListadoHeladosFragment extends stepperFragment {
    // TODO: Rename parameter arguments, choose names that match



    public class Item {
        boolean checked;
        Drawable ItemDrawable;
        Producto producto;

        Item(Drawable drawable, Producto t, boolean b){
            ItemDrawable = drawable;
            producto = t;
            checked = b;
        }

        public boolean isChecked(){
            return checked;
        }
    }

    static class ViewHolder {
        CheckBox checkBox;
        ImageView icon;
        TextView text;
    }

    public class ItemsListAdapter extends BaseAdapter {

        private Context context;
        private List<Item> list;

        ItemsListAdapter(Context c, List<Item> l) {
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

        public boolean isChecked(int position) {

            return list.get(position).checked;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;



            // reuse views
            ViewHolder viewHolder = new ViewHolder();
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.helado_row, null);

                viewHolder.checkBox = (CheckBox) rowView.findViewById(R.id.rowCheckBox);
                viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }

            //viewHolder.icon.setImageDrawable(list.get(position).ItemDrawable);
            viewHolder.checkBox.setChecked(list.get(position).checked);

            final String itemStr = list.get(position).producto.getNombre().toUpperCase().toString();
            viewHolder.text.setText(itemStr);

            viewHolder.checkBox.setTag(position);



            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean newState = !list.get(position).isChecked();
                    list.get(position).checked = newState;
                    //Toast.makeText(getContext(), itemStr + "setOnClickListener\nchecked: " + newState, Toast.LENGTH_LONG).show();
                }
            });

            viewHolder.checkBox.setChecked(isChecked(position));

            return rowView;
        }
    }

    Button btnLookup;
    List<Item> items;
    List<Producto> itemsproducto;
    ListView listView;
    ItemsListAdapter myItemsListAdapter;
    TextView tvMessage;

    private void initItems(){
        items = new ArrayList<Item>();

        ProductoController pc = new ProductoController(getContext());
        Cursor c = pc.abrir().obtenerTodos();
        itemsproducto = pc.abrir().parseCursorToArray(c);

        for(Producto producto: itemsproducto){
            Item item = new Item(null, producto, false);
            items.add(item);
        }
      }























    public ListadoHeladosFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPRODUCTOS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_listado_helados, container, false);


        if (GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidad()!=null){
            tvMessage = (TextView) vista.findViewById(R.id.helados_message);
            tvMessage.setText(GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidad().getStringCantidadHelado());
        }


        listView = (ListView)vista.findViewById(R.id.listview);
        btnLookup = (Button)vista.findViewById(R.id.lookup);

        initItems();
        myItemsListAdapter = new ItemsListAdapter(getContext(), items);
        listView.setAdapter(myItemsListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Item item = (Item)(parent.getItemAtPosition(position));
                Producto p = (Producto)item.producto;
                String texto = p.getNombre().toString();

                Toast.makeText(getContext(), texto, Toast.LENGTH_LONG).show();
            }});

        btnLookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = "Check items:\n";

                for (int i=0; i<items.size(); i++){
                    if (items.get(i).isChecked()){

                        Item item = (Item)(items.get(i));
                        Producto p = (Producto)item.producto;
                        String texto = p.getNombre().toString();


                        str += texto + "\n";
                    }
                }

                Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();

            }
        });

        return vista;
    }

    @Override
    public boolean onNextButtonHandler() {
        //Validar cantidad de helados seleccionados
        Integer cantidadLimite = GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidad().calculateCantidadGustos();
        if (getCantidadHeladoSeleccionado()> cantidadLimite){
            String cartel = "Puedes Seleccionar Solo Hasta "+ cantidadLimite.toString() +". (Cantidad que has seleccionado "+getCantidadHeladoSeleccionado() + ")";
            Toast.makeText(getView().getContext(),cartel,Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public Integer getCantidadHeladoSeleccionado(){
        Integer cantidadSeleccionado = 0;
        for (int i=0; i<items.size(); i++){
            if (items.get(i).isChecked()){
                cantidadSeleccionado += 1;
            }
        }
        return cantidadSeleccionado;
    }

}
