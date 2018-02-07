package adaptivex.pedidoscloud.Controller.AdaptersListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import adaptivex.pedidoscloud.Model.Marca;
import adaptivex.pedidoscloud.R;

import java.util.ArrayList;

/**
 * Created by ezequiel on 31/05/2016.
 */

public class MarcaAdapter extends ArrayAdapter<Marca> {
    public MarcaAdapter(Context context, ArrayList<Marca> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Marca marca = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_marca, parent, false);
        }


        TextView tvDescripcion = (TextView) convertView.findViewById(R.id.tvDescripcion);


        // Populate the data into the template view using the data object

        tvDescripcion.setText(marca.getDescripcion());


        return convertView;
    }
}
