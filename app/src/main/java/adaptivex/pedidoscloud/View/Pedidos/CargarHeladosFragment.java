package adaptivex.pedidoscloud.View.Pedidos;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidodetalleController;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.ItemHelado;
import adaptivex.pedidoscloud.Model.Pedidodetalle;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.R;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterHelado;

public class CargarHeladosFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Cursor cursorHelados;
    //Variables
    private RecyclerView rvHelados;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Producto> arrayOfProductos = new ArrayList<Producto>();
    private RVAdapterHelado rvAdapterHelado;







    public CargarHeladosFragment() {
        // Required empty public constructor
    }


    public static CargarHeladosFragment newInstance(String param1, String param2) {
        CargarHeladosFragment fragment = new CargarHeladosFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_cargar_helados, container, false);
            ProductoController dbHelper = new ProductoController(v.getContext());

            rvHelados = (RecyclerView)v.findViewById(R.id.rvHelados);
            rvHelados.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            rvHelados.setLayoutManager(llm);

            rvAdapterHelado = new RVAdapterHelado();
            rvAdapterHelado.setCtx(getContext());
            arrayOfProductos = dbHelper.findAll();
            rvAdapterHelado.setProductos(arrayOfProductos);
            rvHelados.setAdapter(rvAdapterHelado);


            Button btnListo = (Button) v.findViewById(R.id.cargar_helados_btn_listo);
            btnListo.setOnClickListener(this);


            return v;
        }catch(Exception e){
            Toast.makeText(getContext(), "Error: " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cargar_helados_btn_listo:
                showSelection();

                // Obtener Lista de Seleccion de helados
                // Gennerar los pedidos detalles
                break;



        }
    }

    public boolean savePedidodetalle(){

        try{
            for (int i=0; i<GlobalValues.getINSTANCIA().listaHeladosSeleccionados.size(); i++){
                if (GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(i).isChecked()){
                    ItemHelado item = (ItemHelado) (GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(i));
                    Pedidodetalle pd = new Pedidodetalle();
                    pd.setId(0);
                    pd.setEstadoId(Constants.ESTADO_NUEVO);
                    pd.setCantidad(Double.parseDouble(String.valueOf(GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_MEDIDA_POTE)));
                    pd.setMonto(0.0);
                    pd.setMedidaPote(item.getProporcion());
                    pd.setNroPote(GlobalValues.getINSTANCIA().PEDIDO_ACTUAL_NRO_POTE);
                    pd.setProducto(item.getHelado());
                    PedidodetalleController pdc = new PedidodetalleController(getContext());
                    long idAndroid =pdc.abrir().agregar(pd);
                    Toast.makeText(getContext(), "Pedidodetalle Generado: " + String.valueOf(idAndroid), Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }catch (Exception e ){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }
    public void showSelection(){
        String str = "Helados Seleccionados:\n";

        for (int i=0; i<GlobalValues.getINSTANCIA().listaHeladosSeleccionados.size(); i++){
            if (GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(i).isChecked()){

                ItemHelado item = (ItemHelado) (GlobalValues.getINSTANCIA().listaHeladosSeleccionados.get(i));
                Producto p = (Producto)item.getHelado();
                String texto = p.getNombre().toString();
                texto += " Cantidad -> " +String.valueOf(item.getProporcion());
                str += texto + "\n";
            }
        }

        Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
