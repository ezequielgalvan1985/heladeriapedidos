package adaptivex.pedidoscloud.Config;

import android.content.Context;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Controller.PromoController;
import adaptivex.pedidoscloud.Core.IniciarApp;
import adaptivex.pedidoscloud.Model.ItemHelado;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Model.Pote;
import adaptivex.pedidoscloud.Model.Promo;
import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.View.RVAdapters.RVAdapterHelado;

/**
 * Created by ezequiel on 26/06/2016.
 * VARIABLES DE APLICACION, INTERNA ENTRE ACTIVIDADES, VA
 */
public class GlobalValues {

    private static GlobalValues INSTANCIA;
    public  static final String ACTION_GET_STOCK_PRECIOS = "1";


    public Pedido PEDIDO_TEMPORAL;
    public Integer CURRENT_FRAGMENT_NUEVO_PEDIDO;
    public Context ctxTemporal;









    //Variables de Login user
    private int USER_ID_LOGIN;
    private User userlogued;

    private int ActualFragment;
    public static final int LISTADOPRODUCTOS = 1;
    public static final int LISTADOCLIENTES = 2;
    public static final int LISTADOMARCAS = 3;
    public static final int LISTADOCATEGORIAS = 4;
    public static final int LISTADOPEDIDOS = 5;
    public static final int LISTADOPEDIDODETALLES = 6;
    public static final int DETALLEPEDIDO = 7;
    public static final int PRODUCTODETALLE = 8;
    public static final int LISTADOHOJARUTA = 9;
    public static final int HOME = 10;
    public static final int CONFIGURACION = 11;
    public static final int DATOS_USER = 12;

    //FRAGMENTS Heladerias
    public static final int NP_CARGAR_DIRECCION         = 13;
    public static final int NP_CARGAR_CANTIDAD          = 14;
    public static final int NP_LISTADO_POTES            = 15;
    public static final int NP_CARGAR_HELADOS           = 16;
    public static final int NP_CARGAR_PROPORCION_HELADO = 17;
    public static final int NP_CARGAR_OTROS_DATOS       = 18;
    public static final int NP_RESUMEN_PEDIDO           = 19;



    //ERRORES
    public static final int RETURN_OK = 1;
    public static final int RETURN_ERROR_200 = 200;
    public static final int RETURN_ERROR_404 = 404;
    public static final int RETURN_ERROR_99 = 99;
    public static final int RETURN_ERROR = 20;

    //Estados de un pedido
    public static final int consPedidoEstadoNuevo = 0;
    public static final int consPedidoEstadoEnviado = 1;
    public static final int consPedidoEstadoPreparado = 2;
    public static final int consPedidoEstadoEntregado = 3;
    public static final int consPedidoEstadoTodos = 99;

    //Estados de un pedido
    public static final int ESTADO_NUEVO = 0;
    public static final int ESTADO_ENVIADO = 1;
    public static final int ESTADO_PREPARADO = 2;
    public static final int ESTADO_ENTREGADO = 3;
    public static final int ESTADO_TODOS = 99;



    //CONSTANTERS HELPER PEDIDOS
    public static final int OPTION_HELPER_ENVIO_PEDIDO = 1;
    public static final int OPTION_HELPER_ENVIO_PEDIDOS_PENDIENTES = 2;



    private int PEDIDO_ACTION_VALUE;
    public static final int PEDIDO_ACTION_DELETE = 1;
    public static final int PEDIDO_ACTION_SEND = 2;
    public static final int PEDIDO_ACTION_VIEW = 3;

    public static final int LUNES =1;
    public static final int MARTES =2;
    public static final int MIERCOLES =3;
    public static final int JUEVES =4;
    public static final int VIERNES =5;
    public static final int SABADO =6;
    private int diaSelecionado;


    //DATOS DE USUARIO
    public static final Integer PARAM_USERID = 1;
    public static final Integer PARAM_EMAIL = 2;
    public static final Integer PARAM_GROUPID = 3;
    public static final Integer PARAM_ENTIDADID = 4;
    public static final Integer PARAM_INSTALLED = 5;
    public static final Integer PARAM_CONFIGFILE =6;
    public static final Integer PARAM_REINICIARAPP = 7;
    public static final Integer PARAM_SERVICE_STOCK_PRECIOS_ACTIVATE = 8;
    public static final Integer PARAM_SERVICE_STOCK_PRECIOS_WORKING = 9;
    public static final Integer PARAM_EMPRESA_ID = 10;
    public static final Integer PARAM_DOWNLOAD_DATABASE = 11;
    public static final Integer PARAM_SERVICE_ENVIO_PEDIDOS_ACTIVATE = 12;
    public static final Integer PARAM_USERNAME = 13;
    public static final Integer PARAM_LOCALIDAD = 14;
    public static final Integer PARAM_CALLE = 15;
    public static final Integer PARAM_NRO = 16;
    public static final Integer PARAM_PISO = 17;
    public static final Integer PARAM_CONTACTO = 18;
    public static final Integer PARAM_TELEFONO = 19;




    public static final String VALUE_SERVICE_STOCK_PRECIOS_WORKING = "Y";
    public static final String VALUE_SERVICE_STOCK_PRECIOS_WORKING_NOT = "N";

    //public static  String[] ESTADOS = {"Nuevo","Enviado","Preparado","Entregado"};
    public static  String[] ESTADOS = {"NUEVO","EN PREPARACION","EN CAMINO","ENTREGADO"};

    //Variables globales para Generar Pedido
    private long        vgPedidoIdActual;
    private long        vgPedidoSeleccionado;
    private int         vgClienteSelecionado;
    private boolean     vgFlagMenuNuevoPedido;
    private int         ESTADO_ID_SELECCIONADO;
    public long         PEDIDO_ID_ACTUAL;
    public int          CLIENTE_ID_PEDIDO_ACTUAL;

    public boolean IS_MEMO;

    //Valores impuestos
    public static final Double consIva = 21.00;





    public  static GlobalValues getINSTANCIA() {

        if (INSTANCIA==null) {

            INSTANCIA=new GlobalValues();
        }
        return INSTANCIA;
    }



    public int getActualFragment() {
        return ActualFragment;
    }

    public void setActualFragment(int gvActualFragment) {
        ActualFragment = gvActualFragment;
    }

    public long getVgPedidoIdActual() {
        return vgPedidoIdActual;
    }

    public boolean isUserAuthenticated(){
        if (getUserlogued()==null){
            return false;
        }else{
            return true;
        }

    }
    public void setVgPedidoIdActual(long vgPedidoIdActual) {
        this.vgPedidoIdActual = vgPedidoIdActual;
    }

    public int getVgClienteSelecionado() {
        return vgClienteSelecionado;
    }

    public void setVgClienteSelecionado(int vgClienteSelecionado) {
        this.vgClienteSelecionado = vgClienteSelecionado;
    }

    public boolean isVgFlagMenuNuevoPedido() {
        return vgFlagMenuNuevoPedido;
    }

    public void setVgFlagMenuNuevoPedido(boolean vgFlagMenuNuevoPedido) {
        this.vgFlagMenuNuevoPedido = vgFlagMenuNuevoPedido;
    }

    public int getUSER_ID_LOGIN() {
        return USER_ID_LOGIN;
    }

    public void setUSER_ID_LOGIN(int USER_ID_LOGIN) {
        this.USER_ID_LOGIN = USER_ID_LOGIN;
    }

    public long getVgPedidoSeleccionado() {
        return vgPedidoSeleccionado;
    }

    public void setVgPedidoSeleccionado(long vgPedidoSeleccionado) {
        this.vgPedidoSeleccionado = vgPedidoSeleccionado;
    }

    public int getESTADO_ID_SELECCIONADO() {
        return ESTADO_ID_SELECCIONADO;
    }

    public void setESTADO_ID_SELECCIONADO(int ESTADO_ID_SELECCIONADO) {
        this.ESTADO_ID_SELECCIONADO = ESTADO_ID_SELECCIONADO;
    }

    public int getPEDIDO_ACTION_VALUE() {
        return PEDIDO_ACTION_VALUE;
    }

    public void setPEDIDO_ACTION_VALUE(int PEDIDO_ACTION_VALUE) {
        this.PEDIDO_ACTION_VALUE = PEDIDO_ACTION_VALUE;
    }

    public int getDiaSelecionado() {
        return diaSelecionado;
    }

    public void setDiaSelecionado(int diaSelecionado) {
        this.diaSelecionado = diaSelecionado;
    }

    public User getUserlogued() {
        return userlogued;
    }

    public void setUserlogued(User userlogued) {
        this.userlogued = userlogued;
    }





    // Lista de los items que se seleccinaron en pantalla.
    // NOTA! cuando se edita un Pote, se debe cargar la ListaHeladosSeleecionados con los Items Seleccionados
    // Se pone aca la lista, porque se debe ver desde CargarHeladosFragment y RVAdapterHelado
    public List<ItemHelado> listaHeladosSeleccionados;
    public Integer PEDIDO_ACTUAL_NRO_POTE;
    public Integer PEDIDO_ACTUAL_MEDIDA_POTE;
    public Pote PEDIDO_ACTUAL_POTE;



    public long crearNuevoPedido(Context ctx){
        try{
            IniciarApp ia = new IniciarApp(ctx);
            ia.isLoginRemember();
            PedidoController gestdb = new PedidoController(ctx);
            Date fecha = new Date();
            Calendar cal = Calendar.getInstance();
            fecha = cal.getTime();
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            String fechaDMY = df1.format(fecha);
            //Nuevo Pedido
            Pedido pedido = new Pedido();
            pedido.setEstadoId(Constants.ESTADO_NUEVO);
            pedido.setCliente_id(GlobalValues.getINSTANCIA().getUserlogued().getId());
            pedido.setCreated(fechaDMY);
            long id = gestdb.abrir().agregar(pedido);
            pedido.setIdTmp(id);
            gestdb.cerrar();
            GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL = new Pedido();
            GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL = pedido;

            gestdb.cerrar();
            Toast.makeText(ctx, "Generando Nuevo Pedido  "+ String.valueOf(id) , Toast.LENGTH_SHORT).show();
            return id;
        }catch (Exception e ){
            Toast.makeText(ctx, "Error: " +e.getMessage(),Toast.LENGTH_LONG).show();
            return 0;
        }
    }


}
