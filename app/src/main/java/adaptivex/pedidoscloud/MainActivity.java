package adaptivex.pedidoscloud;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Core.IniciarApp;
import adaptivex.pedidoscloud.Core.SearchHelper;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Servicios.HelperPedidos;
import adaptivex.pedidoscloud.View.Categorias.ListadoCategoriasFragment;
import adaptivex.pedidoscloud.View.Consulting.ConfigFragment;
import adaptivex.pedidoscloud.View.Consulting.ResumenFragment;
import adaptivex.pedidoscloud.View.Hojarutas.ListadoHojarutasFragment;
import adaptivex.pedidoscloud.View.Marcas.ListadoMarcasFragment;
import adaptivex.pedidoscloud.View.Pedidodetalles.ListadoPedidodetallesFragment;
import adaptivex.pedidoscloud.View.Pedidos.CargarDireccionFragment;
import adaptivex.pedidoscloud.View.Pedidos.CargarHeladosFragment;
import adaptivex.pedidoscloud.View.Pedidos.ListadoPedidosFragment;
import adaptivex.pedidoscloud.View.Pedidos.ResumenPedidoFragment;
import adaptivex.pedidoscloud.View.Productos.ListadoHeladosFragment;
import adaptivex.pedidoscloud.View.Promos.ListadoPromosFragment;
import adaptivex.pedidoscloud.View.Pruebas.DescargaImagenActivity;

import adaptivex.pedidoscloud.View.Resumenes.HomeFragment;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        ListadoCategoriasFragment.OnFragmentInteractionListener,
        ListadoMarcasFragment.OnFragmentInteractionListener,

        HomeFragment.OnFragmentInteractionListener,
        ResumenFragment.OnFragmentInteractionListener,
        ConfigFragment.OnFragmentInteractionListener,
        CargarHeladosFragment.OnFragmentInteractionListener,
        ListadoPromosFragment.OnFragmentInteractionListener
{
    private FloatingActionButton BTN_PRINCIPAL;
    protected Intent intentServiceStockPrecios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        Fragment  fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, fragment).addToBackStack(Constants.FRAGMENT_CARGAR_HOME)
                .commit();



    }



    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onBackPressed() {


        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return  super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        Bundle args = new Bundle();
        //noinspection SimplifiableIfStatement


        if (id == R.id.mnu_nuevo_pedido) {
            GlobalValues.getINSTANCIA().crearNuevoPedido(this);
            fragment = new CargarDireccionFragment();
            fragmentTransaction = true;


        }else if (id == R.id.mnu_ver_pedido_actual) {
            fragment = new ResumenPedidoFragment();
            if (GlobalValues.getINSTANCIA().FL_VerPedidoActual(this)){
                fragmentTransaction = true;
            }else{
                Toast.makeText(this, "MainActivity: No Hay Pedidos Generados", Toast.LENGTH_LONG);
                Log.println(Log.ERROR, "MainActivity:", " No Hay Pedidos Generados ");
            }

        }else if (id==R.id.mnu_continuar_pedido_actual){

            PedidoController pdba = new PedidoController(this);
            long nroPedido = pdba.getMaxIdTmpPedido();
            if (nroPedido > 0) {
                Cursor c = pdba.abrir().findByIdAndroid(nroPedido);
                Pedido p = pdba.abrir().parseCursorToPedido(c);
                GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL = p;
                GlobalValues.getINSTANCIA().PEDIDO_ID_ACTUAL = p.getIdTmp();
                fragment = new CargarDireccionFragment();
            } else {
                Toast.makeText(this, "MainActivity: No Hay Pedidos Generados", Toast.LENGTH_LONG);
                Log.println(Log.ERROR, "MainActivity:", " No Hay Pedidos Generados ");
            }

            GlobalValues.getINSTANCIA().setVgFlagMenuNuevoPedido(true);

            fragmentTransaction = true;
            fragment.setArguments(args);


        }

        if (fragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment).addToBackStack(null)
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Click en menu Navegation
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        Bundle args = new Bundle();
        try{
            switch (id){
                case R.id.nav_ver_delivery:
                    Intent maps = new Intent(this, MapsActivity.class);
                    startActivity(maps);
                    break;
                case R.id.nav_pedidodetalles:
                    fragment = new ListadoPedidodetallesFragment();
                    fragmentTransaction = true;
                    GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPEDIDODETALLES);
                    break;
                case R.id.nav_promos:
                    try{
                        fragment = new ListadoPromosFragment();
                        fragmentTransaction = true;
                    }catch(Exception e){
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case  R.id.nav_pedidos_pendientes:
                    try{
                        fragment = new ListadoPedidosFragment();
                        fragmentTransaction = true;

                        GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPEDIDOS);
                        GlobalValues.getINSTANCIA().setESTADO_ID_SELECCIONADO(GlobalValues.getINSTANCIA().consPedidoEstadoNuevo);
                    }catch(Exception e){
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                case R.id.nav_pedidos_enviados:
                    try {
                        fragment = new ListadoPedidosFragment();
                        fragmentTransaction = true;

                        GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPEDIDOS);
                        GlobalValues.getINSTANCIA().setESTADO_ID_SELECCIONADO(GlobalValues.getINSTANCIA().consPedidoEstadoEnviado);
                    }catch(Exception e){
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.nav_enviarpedidospendientes:
                    try {
                        HelperPedidos hp = new HelperPedidos(getBaseContext(), GlobalValues.getINSTANCIA().OPTION_HELPER_ENVIO_PEDIDOS_PENDIENTES);
                        hp.execute();
                    }catch(Exception e){
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case  R.id.nav_categorias:
                    fragment = new ListadoCategoriasFragment();
                    fragmentTransaction = true;
                    GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOCATEGORIAS);
                    break;

                case R.id.nav_productos:
                    fragment = new ListadoHeladosFragment();
                    args.putInt(Constants.PARAM_TIPO_LISTADO,Constants.VALUE_TIPO_LISTADO_HELADOS);
                    fragment.setArguments(args);
                    fragmentTransaction = true;
                    break;

                case R.id.nav_postres:
                    fragment = new ListadoHeladosFragment();
                    args.putInt(Constants.PARAM_TIPO_LISTADO,Constants.VALUE_TIPO_LISTADO_POSTRES);
                    fragment.setArguments(args);
                    fragmentTransaction = true;
                    break;

                case R.id.nav_marcas:
                    fragment = new ListadoMarcasFragment();
                    fragmentTransaction = true;
                    GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOMARCAS);
                    break;

                case R.id.nav_hojarutas:
                    fragment = new ListadoHojarutasFragment();
                    fragmentTransaction = true;
                    GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOHOJARUTA);
                    break;

                case R.id.nav_pedidosentrega:
                    Toast.makeText(this, "Descarga de Pedidos para entregar " , Toast.LENGTH_LONG).show();
                    break;

                case R.id.nav_descargaImagen:
                    Intent i = new Intent(this, DescargaImagenActivity.class);
                    startActivity(i);
                    break;

                case R.id.nav_login:
                    Intent re = new Intent(this, RegisterActivity.class);
                    startActivity(re);
                    break;

                case R.id.nav_datos_user:
                    fragment = new CargarDireccionFragment();
                    args = new Bundle();
                    args.putBoolean(Constants.PARAM_MODE_EDIT_USER, Constants.PARAM_MODE_EDIT_USER_ON);
                    fragment.setArguments(args);
                    fragmentTransaction = true;
                    GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().DATOS_USER);
                    break;

                case R.id.nav_configuracion:
                    try{
                        fragment = new ConfigFragment();
                        fragmentTransaction = true;
                        GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().CONFIGURACION);
                    }catch(Exception e){
                        Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.nav_logout:
                    GlobalValues.getINSTANCIA().setUserlogued(null);
                    // Borrar parametro de Base de datos
                    IniciarApp ia = new IniciarApp(getBaseContext());
                    ia.logout();
                    Intent ir = new Intent(this, RegisterActivity.class);
                    startActivity(ir);
                    break;
                case  R.id.nav_home:
                    try{
                        fragment = new HomeFragment();
                        fragmentTransaction = true;
                        GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().HOME);
                    }catch(Exception e){
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            if (fragmentTransaction) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, fragment).addToBackStack(null)
                        .commit();

            }
            drawer.closeDrawers();
        }catch(Exception e){
            Toast.makeText(this,"MainActivity:"+e.getMessage(), Toast.LENGTH_LONG);
            Log.println(Log.ERROR,"MainActivity:",e.getMessage());
        }
        return true;
    }





        @Override
        public void onFragmentInteraction(Uri uri) {
            Toast.makeText(this,"Mensaje: " + uri.getFragment().toString(),Toast.LENGTH_LONG).show();
        }




}
