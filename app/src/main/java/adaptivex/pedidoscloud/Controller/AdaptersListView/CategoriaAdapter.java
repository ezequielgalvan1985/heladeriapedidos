package adaptivex.pedidoscloud.Controller.AdaptersListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import adaptivex.pedidoscloud.Model.Categoria;
import adaptivex.pedidoscloud.R;

import java.util.ArrayList;

/**
 * Created by ezequiel on 31/05/2016.
 */

public class CategoriaAdapter extends ArrayAdapter<Categoria> {
    public CategoriaAdapter(Context context, ArrayList<Categoria> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Categoria categoria = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_categoria, parent, false);
        }

        TextView tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
        TextView tvDescripcion = (TextView) convertView.findViewById(R.id.tvDescripcion);


        // Populate the data into the template view using the data object
        tvNombre.setText(categoria.getNombre());
        tvDescripcion.setText(categoria.getDescripcion());


        return convertView;
    }
}
