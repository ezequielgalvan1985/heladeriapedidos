package adaptivex.pedidoscloud.View.Pruebas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.Model.ProductoDataBaseHelper;
import adaptivex.pedidoscloud.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DescargaImagenActivity extends AppCompatActivity {
    //public static final String URL = "http://www.thebiblescholar.com/android_awesome.jpg";
    public static final String URL = "http://www.ellechero.com.ar/files/producto/imagen/430/enteros_potes.png";
   // public static final String URL = "http://www.revistacabal.coop/sites/default/files/nube_de_almacenamiento.jpg";


    private ImageView imgImagen;
    private Button btnGuardar,btnCarga2,btnLeer, btnLimpiar,btnDescImgProductos,btnMostrarImagen;
    public String nombreImagen = "img4.png";
    private EditText etNombreImagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descarga_imagen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgImagen = (ImageView)findViewById(R.id.imagen);

        //Muestra Imagen que se ingreso en el text box
        etNombreImagen = (EditText)findViewById(R.id.etNombreImagen);

        btnMostrarImagen =  (Button)findViewById(R.id.btnMostrarImagen);
        btnMostrarImagen.setOnClickListener(new View.OnClickListener() {

                                                   @Override
                                                   public void onClick(View v) {
                                                       Toast.makeText(getBaseContext(), "Mostrando Imagen, por favor espere...", Toast.LENGTH_LONG).show();

                                                       Bitmap imgprueba;
                                                       ContextWrapper cw = new ContextWrapper(getApplicationContext());
                                                       File dirImages = cw.getDir("Imagenes", Context.MODE_PRIVATE);
                                                       File myPath = new File(dirImages, etNombreImagen.getText().toString());
                                                       FileInputStream fis = null;
                                                       try {
                                                           fis = new FileInputStream(myPath);
                                                           imgprueba = BitmapFactory.decodeStream(fis);
                                                           imgImagen.setImageBitmap(imgprueba);
                                                       } catch (FileNotFoundException e) {
                                                           e.printStackTrace();
                                                       }


                                                   }
                                               });






        btnDescImgProductos =  (Button)findViewById(R.id.btnDescImgProductos);
        btnDescImgProductos.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // Recorrer todos los productos

                Toast.makeText(getBaseContext(), "Comienza Descarga de Imagenes, por favor esperar...", Toast.LENGTH_LONG).show();
                Log.i("Debug 1:", "Comienza Descarga de Imagenes, por favor esperar...");

                ProductoController dbHelper = new ProductoController(getApplicationContext());
                Cursor resultSet = dbHelper.abrir().obtenerTodos();
                Producto producto;

                for(resultSet.moveToFirst(); !resultSet.isAfterLast(); resultSet.moveToNext()) {
                    producto = new Producto();

                    //leer imagenurl
                    producto.setImagen(resultSet.getString(resultSet.getColumnIndex(ProductoDataBaseHelper.CAMPO_IMAGEN)));
                    producto.setImagenurl(resultSet.getString(resultSet.getColumnIndex(ProductoDataBaseHelper.CAMPO_IMAGENURL)));
                    Log.i("Debug 2:", producto.getImagenurl());
                    Log.i("Debug 2:", producto.getImagen());
                    ServImagenes nuevaTarea = new ServImagenes();
                    nuevaTarea.setNombreImagen(producto.getImagen());

                    //cargar imagen con imagenurl y guardar imagen con imagenurl
                    nuevaTarea.execute(producto.getImagenurl());

                    producto = null;
                }


                Toast.makeText(getBaseContext(), "FIN  Descarga de Imagenes...", Toast.LENGTH_LONG).show();

            }

        });



        btnGuardar = (Button)findViewById(R.id.btnGuardarImagen);
        btnGuardar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Bitmap imagen = ((BitmapDrawable)imgImagen.getDrawable()).getBitmap();

                String ruta = guardarImagen(getApplicationContext(), nombreImagen, imagen);

                Toast.makeText(getApplicationContext(), ruta, Toast.LENGTH_LONG).show();
            }

        });

        btnCarga2 = (Button)findViewById(R.id.btnCarga2);
        btnCarga2.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View v) {

                                             Bitmap imgprueba;
                                             ContextWrapper cw = new ContextWrapper(getApplicationContext());
                                             File dirImages = cw.getDir("Imagenes", Context.MODE_PRIVATE);
                                             File myPath = new File(dirImages, nombreImagen);
                                             FileInputStream fis = null;
                                             try {
                                                 fis = new FileInputStream(myPath);
                                                 imgprueba = BitmapFactory.decodeStream(fis);
                                                 imgImagen.setImageBitmap(imgprueba);
                                             } catch (FileNotFoundException e) {
                                                 e.printStackTrace();
                                             }



                                         }
                                     });

        btnLimpiar = (Button)findViewById(R.id.btnLimpiar);
        btnLimpiar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                imgImagen.setImageBitmap(null);
                Toast.makeText(getApplicationContext(), "Limpieza realizada", Toast.LENGTH_LONG).show();
            }
        });


        btnLeer = (Button)findViewById(R.id.btnLeer);
        btnLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ServImagenes nuevaTarea = new ServImagenes();
                nuevaTarea.execute(URL);

            }
        });


    }

    private String guardarImagen (Context context, String nombre, Bitmap imagen){
        ContextWrapper cw = new ContextWrapper(context);

        File dirImages = cw.getDir("Imagenes", Context.MODE_PRIVATE);
        File myPath = new File(dirImages, nombre );

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
            fos.flush();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }






    private class ServImagenes extends AsyncTask<String, Void, Bitmap> {
        ProgressDialog pDialog;
        private String nombreImagen;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(DescargaImagenActivity.this);
            pDialog.setMessage("Cargando Imagen");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.i("doInBackground", "Entra en doInBackground");
            String url = params[0];
            Bitmap imagen = descargarImagen(url);

            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            guardarImagenLocal(DescargaImagenActivity.this, getNombreImagen(), result);
            //imgImagen.setImageBitmap(result);


            //Creamos
            pDialog.dismiss();
        }

        private Bitmap descargarImagen (String imageHttpAddress){
            java.net.URL imageUrl = null;
            Bitmap imagen = null;
            try{
                imageUrl = new URL(imageHttpAddress);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                imagen = BitmapFactory.decodeStream(conn.getInputStream());
            }catch(IOException ex){
                ex.printStackTrace();
            }

            return imagen;
        }

        protected String guardarImagenLocal (Context context, String nombre, Bitmap imagen){
            ContextWrapper cw = new ContextWrapper(context);

            File dirImages = cw.getDir("Imagenes", Context.MODE_PRIVATE);
            File myPath = new File(dirImages, nombre );

            FileOutputStream fos = null;
            try{
                fos = new FileOutputStream(myPath);
                imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
                fos.flush();
            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }catch (IOException ex){
                ex.printStackTrace();
            }
            return myPath.getAbsolutePath();
        }

        public String getNombreImagen() {
            return nombreImagen;
        }

        public void setNombreImagen(String nombreImagen) {
            this.nombreImagen = nombreImagen;
        }
    }

}
