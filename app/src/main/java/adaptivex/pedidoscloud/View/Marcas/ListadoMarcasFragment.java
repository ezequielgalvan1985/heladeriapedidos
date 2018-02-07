package adaptivex.pedidoscloud.View.Marcas;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import adaptivex.pedidoscloud.Controller.AdaptersListView.MarcaAdapter;
import adaptivex.pedidoscloud.Controller.MarcaController;
import adaptivex.pedidoscloud.Model.Marca;
import adaptivex.pedidoscloud.Model.MarcaDataBaseHelper;
import adaptivex.pedidoscloud.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListadoMarcasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListadoMarcasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListadoMarcasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListadoMarcasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListadoProductosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListadoMarcasFragment newInstance(String param1, String param2) {
        ListadoMarcasFragment fragment = new ListadoMarcasFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Generar Vista
        View vista = inflater.inflate(R.layout.fragment_listado_marcas, container, false);

        //Obtener listview de marca
        MarcaController dbHelper = new MarcaController(vista.getContext());
        dbHelper.abrir();
        // Construct the data source
        ArrayList<Marca> arrayOfMarcas = new ArrayList<Marca>();
        // Create the adapter to convert the array to views
        MarcaAdapter adapter = new MarcaAdapter(vista.getContext(), arrayOfMarcas);
        // Attach the adapter to a ListView
        ListView lvMarcas = (ListView) vista.findViewById(R.id.lvMarcas);
        lvMarcas.setAdapter(adapter);


        Cursor c = dbHelper.obtenerTodos();
        String datos = "";
        Marca p2;

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            p2 = new Marca();
            p2.setDescripcion(c.getString(c.getColumnIndex(MarcaDataBaseHelper.CAMPO_DESCRIPCION)));
            
            arrayOfMarcas.add(p2);
            p2 = null;
        }


        lvMarcas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(), "presiono " + i, Toast.LENGTH_SHORT).show();
            }
        });


        lvMarcas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(), "presiono LARGO " + i, Toast.LENGTH_SHORT).show();
                return false;
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
