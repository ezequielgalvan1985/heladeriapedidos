package adaptivex.pedidoscloud.Config;

import adaptivex.pedidoscloud.Model.User;

/**
 * Created by ezequiel on 23/05/2016.
 * VARIABLES Y CONFIGURACION DE LA APP EN GENERAL, ORIGNENES DE DATOS, SON TODAS ESTATICAS
 */
public class Configurador {

    public static final int DBVersion = 13;
    public static final String DBName = "heladeria_01";
    private static Configurador INSTANCIA;
    public static  User userlogin;
    public static final String urlImgClientes = "http://www.ellechero.com.ar/files/producto/imagen/";

    //RED CELU
    //public static String strRoot = "http://192.168.43.44:8000";

    //Casa
    //public static String strRoot = "http://192.168.0.4:8000";

    //claxson
    //public static String strRoot = "http://10.4.4.93:8000";

    //amazon
    public static String strRoot = "http://18.228.6.207";

    public static String urlPedidos = strRoot+"/api/pedidos";
    public static String urlMemos = strRoot+"/api/memoclientes";
    public static String urlClientes = strRoot+"/api/clientes";
    public static String urlProductos = strRoot+"/api/productos";
    public static String urlCategorias = strRoot+"/api/categorias";
    public static String urlMarcas = strRoot+"/api/marcas";
    public static String urlHojarutas =strRoot+"/api/hojarutas";
    public static String urlHojarutadetalles = strRoot+"/api/hojarutadetalles";
    public static String urlPostPedido =strRoot+"/api/pedido/add";
    public static String urlPostPedidodetalle =strRoot+"/api/pedidodetallessend";
    public static String urlPostClientes = strRoot+"/api/clientes";
    public static String urlPostLogin = strRoot+"/api/user/login";
    public static String urlPostRegister = strRoot+"/api/user/register";
    public static String urlPostUpdateUser = strRoot+"/api/user/update";
    public static String urlPromos = strRoot+"/api/promos";


    public  static Configurador getConfigurador() {

        if (INSTANCIA==null) {

            INSTANCIA=new Configurador();
        }
        return INSTANCIA;
    }




}
