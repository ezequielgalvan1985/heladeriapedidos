package adaptivex.pedidoscloud.View.Productos;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Controller.PedidodetalleController;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductoDetalleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductoDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductoDetalleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    // Valores staticos finales
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String paramProductoId = "producto_id";
    public static final String paramNombre = "nombre";
    public static final String paramDescripcion = "descripcion";
    public static final String paramPrecio = "precio";
    public static final String paramImagen = "imagen";

    // Variables
    private String mParam1;
    private String mParam2;

    private Integer vlProductoId;
    private String vlNombre, vlDescripcion, vlImagen;
    private Double vlPrecio;

    private OnFragmentInteractionListener mListener;

    //Elementos
    private TextView tvFpdProductoId, tvFpdNombre,tvFpdDescripcion,tvFpdPrecio, tvFpdStock, tvFpdMonto;
    private ImageView ivFpdImagen;
    private EditText etFpdCantidad;
    private Button btnFpdAgregar;

    private ContextWrapper cw;

    public ProductoDetalleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductoDetalleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductoDetalleFragment newInstance(String param1, String param2) {
        ProductoDetalleFragment fragment = new ProductoDetalleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);



        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            vlProductoId = getArguments().getInt(paramProductoId);
            vlNombre = getArguments().getString(paramNombre);
            vlDescripcion = getArguments().getString(paramDescripcion);
            vlImagen = getArguments().getString(paramImagen);
            vlPrecio = getArguments().getDouble(paramPrecio);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_producto_detalle, container, false);

        Log.d("Debug: param ", String.valueOf(vlProductoId));
        //Referencias
        tvFpdProductoId  = (TextView) vista.findViewById(R.id.tvFpdProductoId);
        tvFpdNombre  = (TextView) vista.findViewById(R.id.tvFpdNombre);
        tvFpdDescripcion  = (TextView) vista.findViewById(R.id.tvFpdDescripcion);
        tvFpdPrecio  = (TextView) vista.findViewById(R.id.tvFpdPrecio);
        //ivFpdImagen  = (ImageView) vista.findViewById(R.id.ivFpdImagen);
        etFpdCantidad  = (EditText) vista.findViewById(R.id.etFpdCantidad);
        tvFpdStock = (TextView) vista.findViewById(R.id.tvFpdStock);
        tvFpdMonto = (TextView) vista.findViewById(R.id.tvFpdMonto);

        Integer cantidadstock = 0;
        ProductoController pc = new ProductoController(this.getContext());
        Producto p = new Producto();
        try{
            p = pc.abrir().buscar(Integer.parseInt(vlProductoId.toString()));
            tvFpdProductoId.setText(String.valueOf(vlProductoId));
            tvFpdNombre.setText(vlNombre);
            tvFpdDescripcion.setText(vlDescripcion);
            tvFpdPrecio.setText(String.valueOf(p.getPrecio()));
            tvFpdMonto.setText(String.valueOf(0));
            cantidadstock = p.getStock();
            tvFpdStock.setText(String.valueOf(cantidadstock));
            //Setear Valores

        }catch(Exception e){
            Toast.makeText(getContext(), "Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }


        //Carga de Imagen
        /*
        Bitmap imgprueba;
        cw = new ContextWrapper(vista.getContext());
        File dirImages = cw.getDir("Imagenes", Context.MODE_PRIVATE);
        File myPath = new File(dirImages, vlImagen);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(myPath);
            imgprueba = BitmapFactory.decodeStream(fis);
            ivFpdImagen.setImageBitmap(imgprueba);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */

        //Botones

        // Set a text changed callback for search to enable/disable our search button
        etFpdCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    Double fCantidad = Double.parseDouble( etFpdCantidad.getText().toString());
                    Double fMonto = vlPrecio * fCantidad;
                    tvFpdMonto.setText(String.valueOf(fMonto));
                }catch(Exception e){
                    //Toast.makeText(getContext(), "Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
                    tvFpdMonto.setText(String.valueOf(0));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                //searchButton.setEnabled(searchText.getText().toString().length() > 0);
            }
        });

        btnFpdAgregar = (Button) vista.findViewById(R.id.btnFpdAgregar);
        btnFpdAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Agregar itemPedidoDetalle
                    Pedidodetalle itemPedidoDetalle = new Pedidodetalle();
                    itemPedidoDetalle.setId(0);
                    itemPedidoDetalle.setPedidoId(0);

                    long nped = GlobalValues.getINSTANCIA().getVgPedidoIdActual();
                    itemPedidoDetalle.setPedidoTmpId(nped);
                    itemPedidoDetalle.setProductoId(Integer.valueOf(tvFpdProductoId.getText().toString()));
                    itemPedidoDetalle.setEstadoId(GlobalValues.consPedidoEstadoNuevo);
                    //CALCULAR COSTO
                    Double precio = Double.valueOf(tvFpdPrecio.getText().toString());
                    Double cantidad = Double.valueOf(etFpdCantidad.getText().toString());
                    if (cantidad == 0) {
                        Toast.makeText(v.getContext(), "Cantidad de ser Mayor a Cero  ", Toast.LENGTH_SHORT).show();
                    }
                    Double monto = cantidad * precio;
                    itemPedidoDetalle.setPreciounitario(precio);
                    itemPedidoDetalle.setCantidad(cantidad);
                    itemPedidoDetalle.setMonto(monto);
                    Log.d("Debug: MontoPedido ", String.valueOf(itemPedidoDetalle.getMonto()));

                    ProductoController dbProd = new ProductoController(v.getContext());
                    dbProd.abrir();
                    Producto prod = dbProd.buscar(itemPedidoDetalle.getProductoId());
                    dbProd.cerrar();
                    itemPedidoDetalle.setProducto(prod);

                    //CREAR REGISTRO DETALLE PEDIDO
                    PedidodetalleController gestdb = new PedidodetalleController(v.getContext());
                    long id = gestdb.abrir().agregar(itemPedidoDetalle);
                    gestdb.cerrar();


                    Pedidodetalle pd = gestdb.abrir().findByIdTmp(id);

                    //ACTUALIZAR TOTALES EN PEDIDO

                    PedidoController dbPedido = new PedidoController(v.getContext());
                    dbPedido.abrir();

                    //dbPedido.actualizarTotales(itemPedidoDetalle.getPedidoTmpId());
                    int idtemp = (int) GlobalValues.getINSTANCIA().getVgPedidoIdActual();
                    Pedido pedido = dbPedido.buscar(idtemp, true);

                    pedido.setMonto(pedido.getMonto() + monto);
                    dbPedido.abrir().modificar(pedido, true);
                    dbPedido.cerrar();

                    Toast.makeText(v.getContext(), "Producto Agregado Correctamente al Pedido  " + String.valueOf(GlobalValues.getINSTANCIA().getVgPedidoIdActual()), Toast.LENGTH_SHORT).show();
                    Log.d("Debug: Agregar Item ", String.valueOf(id));

                    if (getFragmentManager().getBackStackEntryCount() > 0) {
                        getFragmentManager().popBackStack();
                    }
            }catch(NumberFormatException e){
                Toast.makeText(v.getContext(), "Producto debe ser mayor a Cero" , Toast.LENGTH_SHORT).show();
                Log.d("Debug: Agregar Item ", e.getMessage());
            }catch(Exception e){
                    Toast.makeText(v.getContext(), "ProductoDetalle  " + e.getMessage() , Toast.LENGTH_SHORT).show();
                    Log.d("Debug: Agregar Item ", e.getMessage());
            }


            }

        });


        //logica change cantidad

        //leer stock



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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
