package adaptivex.pedidoscloud.Config;

/**
 * Created by egalvan on 11/3/2018.
 */

public class Constants {

    //HELADERIA MEDIDAS DE LOS POTES
    public static final int MEDIDA_KILO        = 1000;
    public static final int MEDIDA_TRESCUARTOS = 750;
    public static final int MEDIDA_MEDIO       = 500;
    public static final int MEDIDA_CUARTO      = 250;


    //Estados de un pedido
    public static final int ESTADO_NUEVO            = 0;
    public static final int ESTADO_ENPREPARACION    = 1;
    public static final int ESTADO_ENCAMINO         = 2;
    public static final int ESTADO_ENTREGADO        = 3;
    public static final int ESTADO_TODOS            = 99;


    public static final String MEDIDA_HELADO_POCO        = "Poco";
    public static final String MEDIDA_HELADO_EQUILIBRADO = "Equilibrado";
    public static final String MEDIDA_HELADO_MUCHO       = "Mucho";


    public static final int MEDIDA_HELADO_POCO_DESDE = 0;
    public static final int MEDIDA_HELADO_POCO_HASTA = 50;

    public static final int MEDIDA_HELADO_EQUILIBRADO_DESDE = 51;
    public static final int MEDIDA_HELADO_EQUILIBRADO_HASTA = 100;

    public static final int MEDIDA_HELADO_MUCHO_LIMIT_DESDE = 101;
    public static final int MEDIDA_HELADO_MUCHO_LIMIT_HASTA = 150;

    public static final String PARAM_ANDROID_ID                 = "android_id";
    public static final String PARAM_PEDIDO_ANDROID_ID          = "pedido_android_id";
    public static final String PARAM_PEDIDODETALLE_ANDROID_ID   = "pedidodetalle_android_id";
    public static final String PARAM_PEDIDO_NRO_POTE            = "nro_pte";
    public static final String PARAM_MODE_EDIT_USER             = "datos_user";
    public static final String PARAM_PRECIO_CUCURUCHO           = "preciocucurucho";

    public static final boolean PARAM_MODE_EDIT_USER_ON         = true;
    public static final boolean PARAM_MODE_EDIT_USER_OFF        = false;
    public static final String PARAM_TIPO_LISTADO               = "tipo_listado";
    public static final Integer VALUE_TIPO_LISTADO_HELADOS      = 1;
    public static final Integer VALUE_TIPO_LISTADO_POSTRES      = 2;



    //PRECIO DEL HELADO, simula el parametro hasta que se desarrolle la funcionalidad
    public static double PRECIO_HELADO_KILO         = 0;
    public static double PRECIO_HELADO_MEDIO        = 0;
    public static double PRECIO_HELADO_CUARTO       = 0;
    public static double PRECIO_HELADO_TRESCUARTOS  = 0;
    public static double PRECIO_CUCURUCHO           = 0;


    public static final String FRAGMENT_CARGAR_DIRECCION    = "cargar_direccion";
    public static final String FRAGMENT_CARGAR_CANTIDAD     = "cargar_cantidad";
    public static final String FRAGMENT_CARGAR_HELADOS      = "cargar_helados";
    public static final String FRAGMENT_CARGAR_OTROS_DATOS  = "cargar_otros_datos";
    public static final String FRAGMENT_CARGAR_RESUMEN      = "cargar_resumen";
    public static final String FRAGMENT_CARGAR_HOME         = "cargar_home";
    public static final String FRAGMENT_HOME_LOGIN          = "home_login";


    //SQL
    public static final String MAYOR = ">";
    public static final String IGUAL = "=";
    public static final String MENOR = "<";
    public static final String MAYOR_IGUAL = ">=";
    public static final String MENOR_IGUAL = "<=";
    public static final String LIKE = "%";
    public static final String AND = " AND ";
    public static final String OR = " OR ";
    //CATEGORIAS
    public static final Integer CATEGORIA_HELADOS = 1;
    public static final Integer CATEGORIA_POSTRES = 2;

    //FECHA FORMATOS
    public static final String DATE_FORMAT_SQLITE = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DISPLAY_APP = "dd-MM-yyyy";

    //HelperUser Service
    public static final String SERVICE_OPTION_LOGIN         = "login";
    public static final String SERVICE_OPTION_REGISTER      = "register";
    public static final String SERVICE_OPTION_UPDATE_USER   = "update";


    public static final String SERVICE_PEDIDO_OPTION_UPDATE_USER   = "update";

}
