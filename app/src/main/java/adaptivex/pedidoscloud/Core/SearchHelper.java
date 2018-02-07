package adaptivex.pedidoscloud.Core;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ClienteController;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.View.Clientes.ListadoClientesFragment;
import adaptivex.pedidoscloud.View.Productos.ListadoProductosFragment;

/**
 * Created by Ezequiel on 15/04/2017.
 */

public class SearchHelper {
    private Context ctx;
    private Fragment fragment;



    public SearchHelper(Context pCtx){
        this.setCtx(pCtx);
    }

    public boolean buscar(String query){
        boolean respuesta = false;
        boolean fragmentTransaction = false;
        fragment = null;
        Bundle args = new Bundle();

        if (GlobalValues.getINSTANCIA().getActualFragment() == GlobalValues.getINSTANCIA().LISTADOPRODUCTOS){
            //Filtrar productos por Texto
            try{
                ProductoController pc = new ProductoController(this.getCtx());
                Cursor c = pc.abrir().findByNombre(query);
                GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPRODUCTOS);
                GlobalValues.getINSTANCIA().setVgFlagMenuNuevoPedido(false);
                ListadoProductosFragment fragment = new ListadoProductosFragment();
                fragment.setArguments(args);
                //Completar un array List con
                fragment.setCursorProductos(c);
                this.setFragment(fragment);
                respuesta = true;
            }catch(Exception e ){
                Log.println(Log.ERROR,"MainActivity:",e.getMessage());
            }

        }else if (GlobalValues.getINSTANCIA().getActualFragment() == GlobalValues.getINSTANCIA().LISTADOCLIENTES){
            //SI SE FILTRO EN LISTADO DE CLIENTES
            //Filtrar productos por Texto
            try{
                ClienteController pc = new ClienteController(this.getCtx());
                Cursor c = pc.abrir().findByNombreApellido(query);
                ListadoClientesFragment fragment = new ListadoClientesFragment();
                fragment.setArguments(args);
                //Completar un array List con
                fragment.setCursorClientes(pc.parseCursorToArray(c));
                this.setFragment(fragment);
                respuesta = true;
            }catch(Exception e ){
                Log.println(Log.ERROR,"MainActivity:",e.getMessage());
            }

        }

        return respuesta;

    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
