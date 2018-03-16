package adaptivex.pedidoscloud.Core.Components;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Model.PoteItem;
import adaptivex.pedidoscloud.R;

/**
 * Created by egalvan on 14/3/2018.
 */

public class ListAdapterHelados extends BaseAdapter {
    private Context context;
    private ArrayList<PoteItem> items;

    public ListAdapterHelados(Context context, ArrayList<PoteItem> datos) {
        //super(context, R.layout.item_listview_helado, datos);
        this.context = context;
        this.items = datos;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }
    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // En primer lugar "inflamos" una nueva vista, que será la que se
        // mostrará en la celda del ListView. Para ello primero creamos el
        // inflater, y después inflamos la vista.
        try{
            View v = convertView;
            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inf.inflate(R.layout.item_listview_helado, null);
            }
            //LayoutInflater inflater = LayoutInflater.from(context);
            //View item = inflater.inflate(R.layout.item_listview_helado, null);

            // Recogemos el TextView para mostrar el nombre y establecemos el
            // nombre.
            TextView helado = (TextView) v.findViewById(R.id.item_listview_helado_sabor);
            helado.setText(items.get(position).getProducto().getNombre());

            // Recogemos el TextView para mostrar el número de celda y lo
            // establecemos.
            TextView cantidad = (TextView) v.findViewById(R.id.item_listview_helado_cantidad);
            cantidad.setText(items.get(position).getCantidadDescripction());

            // Devolvemos la vista para que se muestre en el ListView.
            return v;
        }catch(Exception e){
            Toast.makeText(context,"Error: "+ e.getMessage(),Toast.LENGTH_LONG).show();
            return null;
        }

    }
}
