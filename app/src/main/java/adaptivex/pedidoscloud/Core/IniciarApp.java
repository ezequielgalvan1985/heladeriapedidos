package adaptivex.pedidoscloud.Core;

/**
 * Created by Ezequiel on 05/03/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Property;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ClienteController;
import adaptivex.pedidoscloud.Controller.ParameterController;
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Controller.PromoController;
import adaptivex.pedidoscloud.Controller.UserController;
import adaptivex.pedidoscloud.Model.CategoriaDataBaseHelper;
import adaptivex.pedidoscloud.Model.ClienteDataBaseHelper;
import adaptivex.pedidoscloud.Model.HojarutaDataBaseHelper;
import adaptivex.pedidoscloud.Model.HojarutadetalleDataBaseHelper;
import adaptivex.pedidoscloud.Model.MarcaDataBaseHelper;
import adaptivex.pedidoscloud.Model.MemoDataBaseHelper;
import adaptivex.pedidoscloud.Model.Parameter;
import adaptivex.pedidoscloud.Model.ParameterDataBaseHelper;
import adaptivex.pedidoscloud.Model.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Model.PedidodetalleDataBaseHelper;
import adaptivex.pedidoscloud.Model.ProductoDataBaseHelper;
import adaptivex.pedidoscloud.Model.Promo;
import adaptivex.pedidoscloud.Model.PromoDataBaseHelper;
import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.Servicios.HelperCategorias;
import adaptivex.pedidoscloud.Servicios.HelperClientes;
import adaptivex.pedidoscloud.Servicios.HelperHojarutas;
import adaptivex.pedidoscloud.Servicios.HelperMarcas;
import adaptivex.pedidoscloud.Servicios.HelperMemo;
import adaptivex.pedidoscloud.Servicios.HelperParameters;
import adaptivex.pedidoscloud.Servicios.HelperProductos;
import adaptivex.pedidoscloud.Servicios.HelperPromos;

import static java.lang.Thread.sleep;

public  class IniciarApp  {
    private Context context;


    public IniciarApp(Context c ){
        //leer valor de parametro
        setContext(c);
    }
    public  void logout(){
        try{
        ParameterController pc = new ParameterController(getContext());
        Parameter p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_USERID);
        pc.abrir().eliminar(p);
        p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_EMAIL);
        pc.abrir().eliminar(p);
        p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_GROUPID);
        pc.abrir().eliminar(p);
        p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_ENTIDADID);
        pc.abrir().eliminar(p);
        }catch(Exception e){
            Log.d("IniciarAPP", e.getMessage());
        }
    }

    public  boolean  iniciarBD(){
        try{
            SQLiteDatabase db;
            //Tablas
            ClienteDataBaseHelper dba = new ClienteDataBaseHelper(this.getContext());
            db = dba.getWritableDatabase();
            db.execSQL(dba.DROP_TABLE);
            db.execSQL(dba.CREATE_TABLE);
            db.close();

            ProductoDataBaseHelper dbp = new ProductoDataBaseHelper(this.getContext());
            db = dbp.getWritableDatabase();
            db.execSQL(dbp.DROP_TABLE);
            db.execSQL(dbp.CREATE_TABLE);
            db.close();

            MarcaDataBaseHelper m = new MarcaDataBaseHelper(getContext());
            db = m.getWritableDatabase();
            db.execSQL(m.DROP_TABLE);
            db.execSQL(m.CREATE_TABLE);
            db.close();

            CategoriaDataBaseHelper ca = new CategoriaDataBaseHelper(getContext());
            db = ca.getWritableDatabase();
            db.execSQL(ca.DROP_TABLE);
            db.execSQL(ca.CREATE_TABLE);
            db.close();

            PedidoDataBaseHelper pe = new PedidoDataBaseHelper(getContext());
            db = pe.getWritableDatabase();
            db.execSQL(pe.DROP_TABLE);
            db.execSQL(pe.CREATE_TABLE);
            db.close();


            PedidodetalleDataBaseHelper ped = new PedidodetalleDataBaseHelper(getContext());
            db = ped.getWritableDatabase();
            db.execSQL(ped.DROP_TABLE);
            db.execSQL(ped.CREATE_TABLE);
            db.close();



            ParameterDataBaseHelper par = new ParameterDataBaseHelper(getContext());
            db = par.getWritableDatabase();
            db.execSQL(par.DROP_TABLE);
            db.execSQL(par.CREATE_TABLE);
            db.close();


            PromoDataBaseHelper promo = new PromoDataBaseHelper(getContext());
            db = ped.getWritableDatabase();
            db.execSQL(promo.DROP_TABLE);
            db.execSQL(promo.CREATE_TABLE);
            db.close();

            return true;
        }catch(Exception e ){
            Log.println(Log.DEBUG,"IniciarrApp: ", e.getMessage());
            Toast.makeText(context,"IniciarApp:"+e.getMessage(), Toast.LENGTH_LONG);
            return false;
        }

    }


    public boolean loginRemember(User user){
            /* Lee parametros, y los setea con el valor del usuario. Si no existen, los crea */
            boolean respuesta = true;
            //ParameterController pc = new ParameterController(this.getContext());
            ParameterController pc = new ParameterController(this.getContext());
            //SETEO DE USERID
            Parameter p = new Parameter();
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_USERID);
            if (p==null) {
                respuesta = false;
            }else{
                p.setNombre(GlobalValues.getINSTANCIA().PARAM_USERID);
                p.setValor_integer(user.getId());
                p.setDescripcion("Es el Id de usuario en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }



            //SETEO DE ENTIDADID
            p = new Parameter();
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_ENTIDADID);
            if (p==null) {
                respuesta = false;
            }else{
                p.setNombre(GlobalValues.getINSTANCIA().PARAM_ENTIDADID);
                p.setValor_integer(user.getEntidad_id());
                p.setDescripcion("Es el Id de Empresa de usuario en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }



            //SETEO DE EMAIL
            p = new Parameter();
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_EMAIL);
            if (p==null) {
                respuesta = false;
            }else{
                p.setNombre(GlobalValues.getINSTANCIA().PARAM_EMAIL);
                p.setValor_texto(user.getEmail());
                p.setDescripcion("Es el EMAIL de Empresa de usuario en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }



            //SETEO DE GROUPID
            p = new Parameter();
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_GROUPID);
            if (p==null) {
                respuesta = false;
            }else{
                p.setNombre(GlobalValues.getINSTANCIA().PARAM_GROUPID);
                p.setValor_integer(user.getGroup_id());
                p.setDescripcion("Es el Id de GRUPO de usuario  o sea el rol en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }



            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_USERNAME);
            if (p==null) {
                respuesta = false;
            }else{
                p.setNombre(GlobalValues.getINSTANCIA().PARAM_USERNAME);
                p.setValor_texto(user.getUsername());
                p.setDescripcion("Es el username en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();

            }

            //AGREGAR LOS OTROS CAMPOS, localidad, calle, nro, piso, contacto
            //TELEFONO
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_TELEFONO);
            if (p==null) {
                respuesta = false;
            }else{
                //SE MODIFICA
                p.setNombre(GlobalValues.getINSTANCIA().PARAM_TELEFONO);
                p.setValor_texto(user.getTelefono());
                p.setDescripcion("Es el Telefono en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }

            //LOCALIDAD
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_LOCALIDAD);
            if (p==null) {
                respuesta = false;
            }else{
                 //SE MODIFICA
                p.setNombre(GlobalValues.getINSTANCIA().PARAM_LOCALIDAD);
                p.setValor_texto(user.getLocalidad());
                p.setDescripcion("Es el Localidad en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }

            //CALLE
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_CALLE);
            if (p==null) {
                respuesta = false;
            }else{
                //SE MODIFICA
                p.setNombre(GlobalValues.getINSTANCIA().PARAM_CALLE);
                p.setValor_texto(user.getCalle());
                p.setDescripcion("Es el Calle en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }

            //NRO
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_NRO);
            if (p==null) {
                respuesta = false;
            }else{
                //SE MODIFICA
                p.setNombre(GlobalValues.getINSTANCIA().PARAM_NRO);
                p.setValor_texto(user.getNro());
                p.setDescripcion("Es el Nro en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }

            //PISO
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_PISO);
            if (p==null) {
                respuesta = false;
            }else{
                //SE MODIFICA
                p.setNombre(GlobalValues.getINSTANCIA().PARAM_PISO);
                p.setValor_texto(user.getPiso());
                p.setDescripcion("Es el Piso en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }

            //CONTACTO
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_CONTACTO);
            if (p==null) {
                //SE CREA
                respuesta = false;
            }else{
                //SE MODIFICA
                p.setNombre(GlobalValues.getINSTANCIA().PARAM_CONTACTO);
                p.setValor_texto(user.getContacto());
                p.setDescripcion("Es el Contacto en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }

            return true;

    }

    public boolean isLoginRemember(){
        try{
            boolean respuesta = true;
            User u = new User();
            ParameterController pc = new ParameterController(getContext());
            Parameter p = new Parameter();
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_USERID);

            if (p != null) {
                if (p.getValor_integer()< 1){
                    respuesta =  false;
                }
            }else{
                respuesta =  false;
            }

            UserController uc = new UserController(getContext());
            u = uc.getUserDB();
            GlobalValues.getINSTANCIA().setUserlogued(u);

            return respuesta;
        }catch(Exception e){
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }




    public boolean downloadDatabase(){
        try {
            HelperMarcas m = new HelperMarcas(getContext());
            m.execute();

            HelperCategorias ca = new HelperCategorias(getContext());
            ca.execute();

            HelperClientes c = new HelperClientes(getContext());
            c.execute();

            HelperProductos p = new HelperProductos(getContext());
            p.execute();

            HelperParameters hp = new HelperParameters(getContext());
            hp.setCURRENT_OPTION(HelperParameters.OPTION_ALL);
            hp.execute();

            HelperPromos ph = new HelperPromos(getContext());
            ph.execute();

            return true;
        }catch (Exception e ){
          Log.d("IniciarAPP", e.getMessage());
            Toast.makeText(context, "Error " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }



    public void refreshDataFromServer(){
        try{
            HelperParameters hp = new HelperParameters(getContext());
            hp.setCURRENT_OPTION(HelperParameters.OPTION_ALL);
            hp.execute();

            HelperPromos ph = new HelperPromos(getContext());
            ph.execute();
        }catch (Exception e ){
            Log.d("refreshDataFromServer", e.getMessage());
        }
    }

    public void loadPriceVariableGlobal(){
        try{
        //Actulizar variables del sistema con los valores de precio
            ParameterController parameterCtr = new ParameterController(getContext());
            Parameter p;
            p = parameterCtr.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_PRECIOXKILO);
            Constants.PRECIO_HELADO_KILO = p.getValor_decimal();

            p = parameterCtr.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_PRECIOXMEDIO);
            Constants.PRECIO_HELADO_MEDIO = p.getValor_decimal();

            p = parameterCtr.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_PRECIOXCUARTO);
            Constants.PRECIO_HELADO_CUARTO = p.getValor_decimal();

            p = parameterCtr.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_PRECIOTRESCUARTOS);
            Constants.PRECIO_HELADO_TRESCUARTOS = p.getValor_decimal();

            p = parameterCtr.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_PRECIOCUCURUCHO);
            Constants.PRECIO_CUCURUCHO = p.getValor_decimal();
        }catch (Exception e ){
            Log.d("refreshDataFromServer", e.getMessage());
        }
    }

    public boolean isDatabaseDownload(){
        try {
            boolean respuesta = false;
            ProductoController proc = new ProductoController(getContext());
            if (proc.count() < 1) {
                respuesta = false;
            } else {
                respuesta = true;
            }
            return respuesta;
        }catch(Exception e ){
            Log.d("IniciarApp", e.getMessage());
            return false;
        }

    }
    public boolean isInstalled(){
        //Leer Archivo de sistema el parametro INSTALLED
        try {
            boolean respuesta = false;
            ParameterController pc = new ParameterController(getContext());
            Parameter p = new Parameter();
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_INSTALLED);
            if (p != null) {
                if (p.getValor_texto().equals("Y")) {
                    respuesta = true;
                }
            }
            return respuesta;
        }catch(Exception e ){
            Log.d("IniciarApp", e.getMessage());
            return false;
        }
    }

    public boolean setInstalledDatabase(){
        //Leer Archivo de sistema el parametro INSTALLED
        try {
            boolean respuesta = false;
            ParameterController pc = new ParameterController(getContext());
            Parameter p = new Parameter();
            p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_INSTALLED);
            if (p != null) {
                p.setValor_texto("Y");
                pc.abrir().modificar(p);
                respuesta = true;
            }
            return respuesta;
        }catch(Exception e ){
            Log.d("IniciarApp", e.getMessage());
            return false;
        }
    }




    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
