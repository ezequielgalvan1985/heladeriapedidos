package adaptivex.pedidoscloud.View.Productos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import adaptivex.pedidoscloud.R;

public class ProductoDetalleActivity extends AppCompatActivity {
    TextView tvNombre, tvDescripcion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvNombre = (TextView) findViewById(R.id.tvApdNombre);
        tvDescripcion = (TextView) findViewById(R.id.tvApdDescripcion);
        tvNombre.setText(getIntent().getStringExtra("nombre"));
        tvDescripcion.setText(getIntent().getStringExtra("descripcion"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
