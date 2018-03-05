package adaptivex.pedidoscloud.View.Users;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import adaptivex.pedidoscloud.Controller.UserController;
import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatosUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatosUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosUserFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public DatosUserFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DatosUserFragment newInstance(String param1, String param2) {
        DatosUserFragment fragment = new DatosUserFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_datos_user, container, false);
        UserController uc = new UserController(v.getContext());
        User u = uc.getUserDB();
        if (u==null){
            Toast.makeText(v.getContext(), "Error: No se pudo obtener los datos de usuario", Toast.LENGTH_SHORT).show();
        }
        TextView txtCalle = (TextView) v.findViewById(R.id.user_calle);
        TextView txtEmail = (TextView) v.findViewById(R.id.user_email);

        txtCalle.setText(u.getCalle());
        txtEmail.setText(u.getEmail());
        return v;



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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
