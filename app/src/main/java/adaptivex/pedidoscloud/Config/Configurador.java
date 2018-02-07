package adaptivex.pedidoscloud.Config;

import adaptivex.pedidoscloud.Model.User;

/**
 * Created by ezequiel on 23/05/2016.
 * VARIABLES Y CONFIGURACION DE LA APP EN GENERAL, ORIGNENES DE DATOS, SON TODAS ESTATICAS
 */
public class Configurador {

    public static final int DBVersion = 9;
    public static final String DBName = "pedidosapp1";
    private static Configurador INSTANCIA;
    public static  User userlogin;
    public static final String urlImgClientes = "http://www.ellechero.com.ar/files/producto/imagen/";


    //Casa
    //public static String strRoot = "http://192.168.0.6:8000";

    //claxson
    //public static String strRoot = "http://10.4.4.76:8000";

    //amazon
    public static String strRoot = "http://54.207.7.46";

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


    public  static Configurador getConfigurador() {

        if (INSTANCIA==null) {

            INSTANCIA=new Configurador();
        }
        return INSTANCIA;
    }




}
