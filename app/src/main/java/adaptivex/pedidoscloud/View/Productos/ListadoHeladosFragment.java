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

import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListadoHeladosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListadoHeladosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListadoHeladosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private OnFragmentInteractionListener mListener;




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
                //viewHolder.icon = (ImageView) rowView.findViewById(R.id.rowImageView);
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListadoHeladosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListadoHeladosFragment newInstance(String param1, String param2) {
        ListadoHeladosFragment fragment = new ListadoHeladosFragment();


        return fragment;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}