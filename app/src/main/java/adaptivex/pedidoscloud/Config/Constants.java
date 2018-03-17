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
    public static final int ESTADO_NUEVO = 0;
    public static final int ESTADO_ENVIADO = 1;
    public static final int ESTADO_PREPARADO = 2;
    public static final int ESTADO_ENTREGADO = 3;
    public static final int ESTADO_TODOS = 99;


    public static final String MEDIDA_HELADO_POCO        = "Poco";
    public static final String MEDIDA_HELADO_EQUILIBRADO = "Equilibrado";
    public static final String MEDIDA_HELADO_MUCHO       = "Mucho";


    public static final int MEDIDA_HELADO_POCO_DESDE = 0;
    public static final int MEDIDA_HELADO_POCO_HASTA = 50;

    public static final int MEDIDA_HELADO_EQUILIBRADO_DESDE = 51;
    public static final int MEDIDA_HELADO_EQUILIBRADO_HASTA = 100;

    public static final int MEDIDA_HELADO_MUCHO_LIMIT_DESDE = 101;
    public static final int MEDIDA_HELADO_MUCHO_LIMIT_HASTA = 150;

    public static final String PARAM_ANDROID_ID = "android_id";
    public static final String PARAM_PEDIDO_ANDROID_ID = "pedido_android_id";
    public static final String PARAM_PEDIDODETALLE_ANDROID_ID = "pedidodetalle_android_id";
    public static final String PARAM_PEDIDO_NRO_POTE = "nro_pte";



    //PRECIO DEL HELADO, simula el parametro hasta que se desarrolle la funcionalidad
    public static double PRECIO_HELADO_KILO = 250.00;
    public static double PRECIO_HELADO_MEDIO = 125.00;
    public static final double PRECIO_HELADO_CUARTO = 60.00;
    public static final double PRECIO_HELADO_TRESCUARTOS = 180.00;

    public static final double PRECIO_CUCURUCHO = 5;

}
