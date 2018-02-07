package adaptivex.pedidoscloud;

import android.content.Intent;
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

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Core.IniciarApp;
import adaptivex.pedidoscloud.Core.ParameterHelper;
import adaptivex.pedidoscloud.Core.SearchHelper;
import adaptivex.pedidoscloud.Model.Cliente;
import adaptivex.pedidoscloud.Model.Hojaruta;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.Servicios.HelperMemo;
import adaptivex.pedidoscloud.Servicios.HelperPedidos;
import adaptivex.pedidoscloud.Servicios.HelperProductos;
import adaptivex.pedidoscloud.Servicios.IntentServiceStockPrecios;
import adaptivex.pedidoscloud.View.Categorias.ListadoCategoriasFragment;
import adaptivex.pedidoscloud.View.Clientes.ListadoClientesFragment;
import adaptivex.pedidoscloud.View.Consulting.ConfigFragment;
import adaptivex.pedidoscloud.View.Consulting.ResumenFragment;
import adaptivex.pedidoscloud.View.Hojarutas.ListadoHojarutasFragment;
import adaptivex.pedidoscloud.View.Marcas.ListadoMarcasFragment;
import adaptivex.pedidoscloud.View.Pedidodetalles.ListadoPedidodetallesFragment;
import adaptivex.pedidoscloud.View.Pedidos.DetallePedidoFragment;
import adaptivex.pedidoscloud.View.Pedidos.ListadoPedidosFragment;
import adaptivex.pedidoscloud.View.Productos.ListadoProductosFragment;
import adaptivex.pedidoscloud.View.Productos.ProductoDetalleFragment;
import adaptivex.pedidoscloud.View.Pruebas.DescargaImagenActivity;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterCliente;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterHojaruta;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterPedido;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterProducto;
import adaptivex.pedidoscloud.View.Resumenes.HomeFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ListadoPedidosFragment.OnFragmentInteractionListener,
        ListadoPedidodetallesFragment.OnFragmentInteractionListener,
        ListadoClientesFragment.OnFragmentInteractionListener,
        ListadoProductosFragment.OnFragmentInteractionListener,
        ListadoCategoriasFragment.OnFragmentInteractionListener,
        ListadoMarcasFragment.OnFragmentInteractionListener,
        ListadoHojarutasFragment.OnFragmentInteractionListener,
        ProductoDetalleFragment.OnFragmentInteractionListener,
        DetallePedidoFragment.OnFragmentInteractionListener,
        RVAdapterProducto.OnHeadlineSelectedListener,
        RVAdapterCliente.OnHeadlineSelectedListener,
        RVAdapterPedido.OnHeadlineSelectedListener,
        RVAdapterHojaruta.OnHeadlineSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        ResumenFragment.OnFragmentInteractionListener,
        ConfigFragment.OnFragmentInteractionListener

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


        ParameterHelper ph = new ParameterHelper(getBaseContext());

        if (ph.getServiceStockPrecios().equals("Y")){
            intentServiceStockPrecios = new Intent(MainActivity.this, IntentServiceStockPrecios.class);
            getBaseContext().startService(intentServiceStockPrecios);
        }

        if (!ph.isDownloadDatabase()){
            IniciarApp ia = new IniciarApp(getBaseContext());
            ia.downloadDatabase();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        IniciarApp ia = new IniciarApp(this.getBaseContext());
        ia.isLoginRememberr();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.mnu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                SearchHelper sh = new SearchHelper(MainActivity.this);
                if (sh.buscar(s)){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_main, sh.getFragment()).addToBackStack(null)
                            .commit();
                }


                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                return true;
            }


        });

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
            //setear Flag de nuevo pedido en true
            GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOCLIENTES);
            GlobalValues.getINSTANCIA().setVgFlagMenuNuevoPedido(true);
            fragment = new ListadoClientesFragment();
            fragmentTransaction = true;
            args.putBoolean(ListadoClientesFragment.paramMenuNuevoPedido, true);
            fragment.setArguments(args);

        }else if (id == R.id.mnu_ver_pedido_actual) {
            //setear Flag de nuevo pedido en true
            //GlobalValues.getINSTANCIA().setVgFlagMenuPedidoActual(true);
            //BUSCAR ULTIMO PEDIDO GENERADO EN EL DISPOSITIVO
            GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().DETALLEPEDIDO);

            PedidoController pdba = new PedidoController(this);
            long nroPedido = pdba.getMaxIdTmpPedido();
            if (nroPedido > 0) {
                GlobalValues.getINSTANCIA().setVgPedidoIdActual(pdba.getMaxIdTmpPedido());
                GlobalValues.getINSTANCIA().setVgPedidoSeleccionado(GlobalValues.getINSTANCIA().getVgPedidoIdActual());

                fragment = new DetallePedidoFragment();
                fragmentTransaction = true;
                args.putLong(DetallePedidoFragment.paramPedidoIdTmp, GlobalValues.getINSTANCIA().getVgPedidoIdActual());
                fragment.setArguments(args);

            } else {
                Toast.makeText(this, "MainActivity: No Hay Pedidos Generados", Toast.LENGTH_LONG);
                Log.println(Log.ERROR, "MainActivity:", " No Hay Pedidos Generados ");
            }
        }else if (id==R.id.mnu_continuar_pedido_actual){

            PedidoController pdba = new PedidoController(this);
            long nroPedido = pdba.getMaxIdTmpPedido();
            if (nroPedido > 0) {
                Pedido p = new Pedido();
                p = pdba.abrir().buscar(nroPedido,true);
                GlobalValues.getINSTANCIA().PEDIDO_ID_ACTUAL = p.getIdTmp();
                GlobalValues.getINSTANCIA().CLIENTE_ID_PEDIDO_ACTUAL = p.getCliente_id();
                GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPRODUCTOS);
                fragment = new ListadoProductosFragment();
                fragmentTransaction = true;
            } else {
                Toast.makeText(this, "MainActivity: No Hay Pedidos Generados", Toast.LENGTH_LONG);
                Log.println(Log.ERROR, "MainActivity:", " No Hay Pedidos Generados ");
            }


        }else if (id==R.id.mnu_recordatorio){
            try{
                //Buscar ultimo pedido
                PedidoController pdba = new PedidoController(this);
                long nroPedido = pdba.getMaxIdTmpPedido();
                Pedido p = new Pedido();
                p = pdba.abrir().buscar(nroPedido,true);
                GlobalValues.getINSTANCIA().PEDIDO_ID_ACTUAL = p.getIdTmp();
                GlobalValues.getINSTANCIA().CLIENTE_ID_PEDIDO_ACTUAL = p.getCliente_id();
                if (GlobalValues.getINSTANCIA().PEDIDO_ID_ACTUAL > 0 ){
                    if (GlobalValues.getINSTANCIA().CLIENTE_ID_PEDIDO_ACTUAL > 0){
                        GlobalValues.getINSTANCIA().IS_MEMO = true;
                        GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPRODUCTOS);
                        fragment = new ListadoProductosFragment();
                        fragmentTransaction = true;
                    }else {
                        Toast.makeText(this, "Todavia no seleccion un cliente para el pedido...", Toast.LENGTH_LONG);
                    }
                }else{
                    Toast.makeText(this, "Debe generar un pedido...", Toast.LENGTH_LONG);
                }
            }catch (Exception e ){
                Toast.makeText(this, "MainActivity: Hubo un Error" + e.getMessage().toString(), Toast.LENGTH_LONG);
            }
        } else if (id == R.id.mnu_lunes || id == R.id.mnu_martes || id == R.id.mnu_miercoles || id == R.id.mnu_jueves || id == R.id.mnu_viernes|| id == R.id.mnu_sabado) {
            //Obtener dia seleccionado
            //Pasar dia como argumento
            //Buscar datos de a hoja de ruta para el dia
            //HojarutaController hc = new HojarutaController(this);

            if (id == R.id.mnu_lunes){
                GlobalValues.getINSTANCIA().setDiaSelecionado(GlobalValues.getINSTANCIA().LUNES);
            }else if (id == R.id.mnu_martes){
                GlobalValues.getINSTANCIA().setDiaSelecionado(GlobalValues.getINSTANCIA().MARTES);
            }else if (id == R.id.mnu_miercoles){
                GlobalValues.getINSTANCIA().setDiaSelecionado(GlobalValues.getINSTANCIA().MIERCOLES);
            }else if (id == R.id.mnu_jueves){
                GlobalValues.getINSTANCIA().setDiaSelecionado(GlobalValues.getINSTANCIA().JUEVES);
            }else if (id == R.id.mnu_viernes){
                GlobalValues.getINSTANCIA().setDiaSelecionado(GlobalValues.getINSTANCIA().VIERNES);
            }else if (id == R.id.mnu_sabado){
                GlobalValues.getINSTANCIA().setDiaSelecionado(GlobalValues.getINSTANCIA().SABADO);
            }

            GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOCLIENTES);
            GlobalValues.getINSTANCIA().setVgFlagMenuNuevoPedido(true);
            fragment = new ListadoClientesFragment();
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
        try{

            //noinspection SimplifiableIfStatement
            if (id == R.id.nav_pedidodetalles) {
                fragment = new ListadoPedidodetallesFragment();
                fragmentTransaction = true;

                GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPEDIDODETALLES);


            }else if (id == R.id.nav_pedidos_pendientes) {
                try{
                    fragment = new ListadoPedidosFragment();
                    fragmentTransaction = true;

                    GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPEDIDOS);
                    GlobalValues.getINSTANCIA().setESTADO_ID_SELECCIONADO(GlobalValues.getINSTANCIA().consPedidoEstadoNuevo);
                }catch(Exception e){
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }else if (id == R.id.nav_pedidos_enviados) {
                try {
                    fragment = new ListadoPedidosFragment();
                    fragmentTransaction = true;

                    GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPEDIDOS);
                    GlobalValues.getINSTANCIA().setESTADO_ID_SELECCIONADO(GlobalValues.getINSTANCIA().consPedidoEstadoEnviado);
                }catch(Exception e){
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }else if (id == R.id.nav_enviarpedidospendientes) {
                try {
                    HelperPedidos hp = new HelperPedidos(getBaseContext(), GlobalValues.getINSTANCIA().OPTION_HELPER_ENVIO_PEDIDOS_PENDIENTES);
                    hp.execute();
                }catch(Exception e){
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }else if (id == R.id.nav_categorias) {
                fragment = new ListadoCategoriasFragment();
                fragmentTransaction = true;

                GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOCATEGORIAS);

            } else if (id == R.id.nav_clientes) {
                fragment = new ListadoClientesFragment();
                fragmentTransaction = true;
                Log.d("Debug: ClickCliente ", String.valueOf(id));

                GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOCLIENTES);

            } else if (id == R.id.nav_productos) {
                fragment = new ListadoProductosFragment();
                fragmentTransaction = true;

                GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOPRODUCTOS);

            } else if (id == R.id.nav_marcas) {
                fragment = new ListadoMarcasFragment();
                fragmentTransaction = true;
                GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOMARCAS);

            } else if (id == R.id.nav_hojarutas) {
                fragment = new ListadoHojarutasFragment();
                fragmentTransaction = true;
                GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().LISTADOHOJARUTA);

            } else if (id == R.id.nav_pedidosentrega) {
                Toast.makeText(this, "Descarga de Pedidos para entregar " , Toast.LENGTH_LONG).show();

            } else if (id == R.id.nav_diagramarrecorrido) {
                Toast.makeText(this, "Diagramar en GPS " , Toast.LENGTH_LONG).show();

            } else if (id == R.id.nav_descargaImagen) {
                Intent i = new Intent(this, DescargaImagenActivity.class);
                startActivity(i);


            } else if (id == R.id.nav_login) {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);

            } else if (id == R.id.nav_configuracion) {
                try{

                    fragment = new ConfigFragment();
                    fragmentTransaction = true;
                    GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().CONFIGURACION);
                }catch(Exception e){
                    Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }




            } else if (id == R.id.nav_logout) {
                GlobalValues.getINSTANCIA().setUserlogued(null);
                // Borrar parametro de Base de datos
                IniciarApp ia = new IniciarApp(getBaseContext());
                ia.logout();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);

            } else if (id == R.id.nav_diagramarrecorrido) {
                Intent i = new Intent(this, MapsActivity.class);
                startActivity(i);

            } else if (id == R.id.nav_home) {
                try{
                    fragment = new ResumenFragment();
                    fragmentTransaction = true;
                    GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().HOME);
                }catch(Exception e){
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }





            }
            //item.setChecked(true);
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

        }


        //ESTO MANEJA EL ALTA DE PEDIDO, LA SELECCION DEL PRODUCTO EN LA LISTA
        @Override
        public void onProductoSelected(int position, Producto producto) {
            // Toast.makeText(this, "Hizo Click en Item desde OnArticleSelecter " + String.valueOf(position), Toast.LENGTH_SHORT).show();
            Fragment fragment = new ProductoDetalleFragment();
            Bundle args = new Bundle();

            args.putInt(ProductoDetalleFragment.paramProductoId, producto.getId());
            args.putString(ProductoDetalleFragment.paramNombre, producto.getNombre());
            args.putString(ProductoDetalleFragment.paramDescripcion, producto.getDescripcion());
            args.putString(ProductoDetalleFragment.paramImagen, producto.getImagen());
            args.putDouble(ProductoDetalleFragment.paramPrecio, producto.getPrecio());

            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment).addToBackStack(null)
                    .commit();




        }



        @Override
        public void onClienteSelected(int position, Cliente cliente) {
            //Preguntar si esta generando un nuevo pedido
            Fragment fragment = new ListadoProductosFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment).addToBackStack(null)
                    .commit();


        }


        @Override
        public void onPedidoSelected(int position, Pedido pedido) {

            if (GlobalValues.getINSTANCIA().getPEDIDO_ACTION_VALUE() ==GlobalValues.getINSTANCIA().PEDIDO_ACTION_DELETE ){

            }else if (GlobalValues.getINSTANCIA().getPEDIDO_ACTION_VALUE() ==GlobalValues.getINSTANCIA().PEDIDO_ACTION_VIEW){
                //Preguntar si esta generando un nuevo pedido
                Fragment fragment = new DetallePedidoFragment();
                GlobalValues.getINSTANCIA().setActualFragment(GlobalValues.getINSTANCIA().DETALLEPEDIDO);
                GlobalValues.getINSTANCIA().setVgPedidoSeleccionado(pedido.getIdTmp());
                Bundle args = new Bundle();
                Log.d("Debug Click Ped ", String.valueOf(pedido.getIdTmp()));
                args.putLong(DetallePedidoFragment.paramPedidoIdTmp, pedido.getIdTmp());
                fragment.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, fragment).addToBackStack(null)
                        .commit();



            }
            Toast.makeText(MainActivity.this,"Pedido Eliminado Correctamente ", Toast.LENGTH_LONG);

        }


    @Override
    public void onHojarutaSelected(int position, Hojaruta hojaruta) {
        Toast.makeText(MainActivity.this,"ID Hoja de ruta seleccionada: "+ hojaruta.getId(), Toast.LENGTH_LONG);
    }
}
