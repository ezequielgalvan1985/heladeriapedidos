package adaptivex.pedidoscloud.Controller.AdaptersListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;

import java.util.ArrayList;

/**
 * Created by ezequiel on 31/05/2016.
 */

public class ProductoAdapter extends ArrayAdapter<Producto> {
    public ProductoAdapter(Context context, ArrayList<Producto> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Producto producto = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }
        //ImageView imgImg = (ImageView) convertView.findViewById(R.id.pivImagen);
        TextView tvNombre = (TextView) convertView.findViewById(R.id.ptvNombre);
        //TextView tvDescripcion = (TextView) convertView.findViewById(R.id.ptvDescripcion);
        TextView tvPrecio = (TextView) convertView.findViewById(R.id.ptvPrecio);

        // Populate the data into the template view using the data object
        tvNombre.setText(producto.getNombre());
        //tvDescripcion.setText(producto.getDescripcion());
        tvPrecio.setText(String.valueOf(producto.getPrecio()));


       // tvPrecio.setText(precio);
        // Return the completed view to render on screen
        return convertView;
    }
}
