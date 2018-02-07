package adaptivex.pedidoscloud.Core;

/**
 * Created by Ezequiel on 05/03/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Controller.ClienteController;
import adaptivex.pedidoscloud.Controller.ParameterController;
import adaptivex.pedidoscloud.Controller.ProductoController;
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
import adaptivex.pedidoscloud.Model.User;
import adaptivex.pedidoscloud.Servicios.HelperCategorias;
import adaptivex.pedidoscloud.Servicios.HelperClientes;
import adaptivex.pedidoscloud.Servicios.HelperHojarutas;
import adaptivex.pedidoscloud.Servicios.HelperMarcas;
import adaptivex.pedidoscloud.Servicios.HelperMemo;
import adaptivex.pedidoscloud.Servicios.HelperProductos;

public  class IniciarApp  {
    private Context context;


    public IniciarApp(Context c ){
        //leer valor de parametro
        setContext(c);
    }
    public void logout(){
        try{
        ParameterController pc = new ParameterController(getContext());
        Parameter p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_USERID);
        pc.abrir().eliminar(p);
        p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_EMAIL);
        pc.abrir().eliminar(p);
        p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_GROUPID);
        pc.abrir().eliminar(p);
        p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_ENTIDADID);
        pc.abrir().eliminar(p);
        }catch(Exception e){
            Log.d("IniciarAPP", e.getMessage());
        }
    }


    public  boolean  iniciarBD(){
        try{
            SQLiteDatabase db;

            ClienteDataBaseHelper dba = new ClienteDataBaseHelper(this.getContext());
            db = dba.getWritableDatabase();
            db.execSQL(dba.DROP_TABLE);
            db.execSQL(dba.CREATE_TABLE);

            ProductoDataBaseHelper dbp = new ProductoDataBaseHelper(this.getContext());
            db = dbp.getWritableDatabase();
            db.execSQL(dbp.DROP_TABLE);
            db.execSQL(dbp.CREATE_TABLE);


            MarcaDataBaseHelper m = new MarcaDataBaseHelper(getContext());
            db = m.getWritableDatabase();
            db.execSQL(m.DROP_TABLE);
            db.execSQL(m.CREATE_TABLE);

            CategoriaDataBaseHelper ca = new CategoriaDataBaseHelper(getContext());
            db = ca.getWritableDatabase();
            db.execSQL(ca.DROP_TABLE);
            db.execSQL(ca.CREATE_TABLE);

            PedidoDataBaseHelper pe = new PedidoDataBaseHelper(getContext());
            db = pe.getWritableDatabase();
            db.execSQL(pe.DROP_TABLE);
            db.execSQL(pe.CREATE_TABLE);

            PedidodetalleDataBaseHelper ped = new PedidodetalleDataBaseHelper(getContext());
            db = ped.getWritableDatabase();
            db.execSQL(ped.DROP_TABLE);
            db.execSQL(ped.CREATE_TABLE);

            HojarutaDataBaseHelper h = new HojarutaDataBaseHelper(getContext());
            db = h.getWritableDatabase();
            db.execSQL(h.DROP_TABLE);
            db.execSQL(h.CREATE_TABLE);

            HojarutadetalleDataBaseHelper hd = new HojarutadetalleDataBaseHelper(getContext());
            db = hd.getWritableDatabase();
            db.execSQL(hd.DROP_TABLE);
            db.execSQL(hd.CREATE_TABLE);

            ParameterDataBaseHelper par = new ParameterDataBaseHelper(getContext());
            db = par.getWritableDatabase();
            db.execSQL(par.DROP_TABLE);
            db.execSQL(par.CREATE_TABLE);

            MemoDataBaseHelper mdb = new MemoDataBaseHelper(getContext());
            db = par.getWritableDatabase();
            db.execSQL(mdb.DROP_TABLE);
            db.execSQL(mdb.CREATE_TABLE);


            //PARAMETROS...
            ParameterController pc = new ParameterController(getContext());
            Parameter p = new Parameter();

            p.setId(GlobalValues.getINSTANCIA().PARAM_INSTALLED);
            p.setValor_texto("Y");
            pc.abrir().agregar(p);
            pc.cerrar();

            p = new Parameter();
            p.setId(GlobalValues.getINSTANCIA().PARAM_SERVICE_STOCK_PRECIOS_ACTIVATE);
            p.setValor_texto("N");
            pc.abrir().agregar(p);
            pc.cerrar();

            p = new Parameter();
            p.setId(GlobalValues.getINSTANCIA().PARAM_SERVICE_STOCK_PRECIOS_WORKING);
            p.setValor_texto("N");
            pc.abrir().agregar(p);
            pc.cerrar();

            //Variables de usuario
            p = new Parameter();
            p.setId(GlobalValues.getINSTANCIA().PARAM_USERID);
            p.setValor_texto("");
            pc.abrir().agregar(p);
            pc.cerrar();


            p = new Parameter();
            p.setId(GlobalValues.getINSTANCIA().PARAM_EMAIL);
            p.setValor_texto("");
            pc.abrir().agregar(p);
            pc.cerrar();

            p = new Parameter();
            p.setId(GlobalValues.getINSTANCIA().PARAM_EMPRESA_ID);
            p.setValor_texto("");
            pc.abrir().agregar(p);
            pc.cerrar();

            p = new Parameter();
            p.setId(GlobalValues.getINSTANCIA().PARAM_DOWNLOAD_DATABASE);
            p.setValor_texto("N");
            pc.abrir().agregar(p);
            pc.cerrar();

            return true;
        }catch(Exception e ){
            Log.println(Log.DEBUG,"IniciarrApp: ", e.getMessage());
            Toast.makeText(context,"IniciarApp:"+e.getMessage(), Toast.LENGTH_LONG);
            return false;
        }

    }

    public boolean isLoginRememberr(){
        try{
            // Si Parameter userid no existe, devuelve false
            // Si Parameter userid EXISTE,   cargan los datos en Userlogin
            //ParameterController pc = new ParameterController(this.getContext());
            //Parameter p = pc.abrir().findByNombre(GlobalValues.getINSTANCIA().PARAM_EMAIL);
            ParameterController pc = new ParameterController(this.getContext());
            Parameter p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_EMAIL);
            User user = new User();
            if (p!=null){
                if (p.getValor_texto()!=""){
                    //CARGAR USUARIO RECORDADO
                    user.setEmail(p.getValor_texto());

                    p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_USERID);
                    if (p!=null){
                        user.setId(p.getValor_integer());
                    }

                    p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_USERNAME);
                    if (p!=null) {
                        user.setUsername(p.getValor_texto());
                    }

                    p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_ENTIDADID);
                    if (p!=null) {
                        user.setEntidad_id(p.getValor_integer());
                    }

                    p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_GROUPID);
                    if (p!=null){
                        user.setGroup_id(p.getValor_integer());
                    }

                    GlobalValues.getINSTANCIA().setUserlogued(user);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }catch(Exception e){
            Log.d("IniciarApp", e.getMessage());
            return false;
        }
    }

    public boolean loginRememberr(User user){
        /* Lee parametros, y los setea con el valor del usuario. Si no existen, los crea */

            //ParameterController pc = new ParameterController(this.getContext());
            ParameterController pc = new ParameterController(this.getContext());
            //SETEO DE USERID
            Parameter p = new Parameter();
            p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_USERID);
            if (p==null) {
                p = new Parameter();
                p.setId(GlobalValues.getINSTANCIA().PARAM_USERID);
                p.setValor_integer(user.getId());
                p.setDescripcion("Es el Id de usuario en el sistema web");
                pc.abrir().agregar(p);
                pc.cerrar();
            }else{
                p.setId(GlobalValues.getINSTANCIA().PARAM_USERID);
                p.setValor_integer(user.getId());
                p.setDescripcion("Es el Id de usuario en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }



            //SETEO DE ENTIDADID
            p = new Parameter();
            p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_ENTIDADID);
            if (p==null) {
                p = new Parameter();
                p.setId(GlobalValues.getINSTANCIA().PARAM_ENTIDADID);
                p.setValor_integer(user.getEntidad_id());
                p.setDescripcion("Es el Id de Empresa de usuario en el sistema web");
                pc.abrir().agregar(p);
                pc.cerrar();
            }else{
                p.setId(GlobalValues.getINSTANCIA().PARAM_ENTIDADID);
                p.setValor_integer(user.getEntidad_id());
                p.setDescripcion("Es el Id de Empresa de usuario en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }



            //SETEO DE EMAIL
            p = new Parameter();
            p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_EMAIL);
            if (p==null) {
                p = new Parameter();
                p.setId(GlobalValues.getINSTANCIA().PARAM_EMAIL);
                p.setValor_texto(user.getEmail());
                p.setDescripcion("Es el EMAIL de Empresa de usuario en el sistema web");
                pc.abrir().agregar(p);
                pc.cerrar();
            }else{
                p.setId(GlobalValues.getINSTANCIA().PARAM_EMAIL);
                p.setValor_texto(user.getEmail());
                p.setDescripcion("Es el EMAIL de Empresa de usuario en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }



            //SETEO DE GROUPID
            p = new Parameter();
            p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_GROUPID);
            if (p==null) {
                p = new Parameter();
                p.setId(GlobalValues.getINSTANCIA().PARAM_GROUPID);
                p.setValor_integer(user.getGroup_id());
                p.setDescripcion("Es el Id de GRUPO de usuario  o sea el rol en el sistema web");
                pc.abrir().agregar(p);
                pc.cerrar();
            }else{
                p.setId(GlobalValues.getINSTANCIA().PARAM_GROUPID);
                p.setValor_integer(user.getGroup_id());
                p.setDescripcion("Es el Id de GRUPO de usuario  o sea el rol en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();
            }



            p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_USERNAME);
            if (p==null) {
                p = new Parameter();
                p.setId(GlobalValues.getINSTANCIA().PARAM_USERNAME);
                p.setValor_texto(user.getUsername());
                p.setDescripcion("Es el username en el sistema web");
                pc.abrir().agregar(p);
                pc.cerrar();
            }else{
                p.setId(GlobalValues.getINSTANCIA().PARAM_USERNAME);
                p.setValor_texto(user.getUsername());
                p.setDescripcion("Es el username en el sistema web");
                pc.abrir().modificar(p);
                pc.cerrar();

            }



            return true;

    }

    public boolean crearParametersLogin(){
        try{
            ParameterController pc = new ParameterController(this.getContext());

            Parameter p = new Parameter();
            p.setNombre("user_id");
            p.setValor_integer(GlobalValues.getINSTANCIA().getUserlogued().getId());
            pc.abrir().agregar(p);

            p = new Parameter();
            p.setNombre("entidad_id");
            p.setValor_integer(GlobalValues.getINSTANCIA().getUserlogued().getEntidad_id());
            pc.abrir().agregar(p);

            p = new Parameter();
            p.setNombre("group_id");
            p.setValor_integer(GlobalValues.getINSTANCIA().getUserlogued().getGroup_id());
            pc.abrir().agregar(p);

            return true;
        }catch(Exception e){
            Log.d("IniciarApp", e.getMessage());
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

            HelperHojarutas hd = new HelperHojarutas(getContext());
            hd.execute();

            HelperMemo hp = new HelperMemo(getContext());
            hp.execute();

            ParameterController pc  = new ParameterController(getContext());
            Parameter param1 = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_DOWNLOAD_DATABASE);
            param1.setValor_texto("Y");
            pc.abrir().modificar(param1);
            pc.cerrar();

            return true;
        }catch (Exception e ){
          Log.d("IniciarAPP", e.getMessage());
            Toast.makeText(context, "Error " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean isDatabaseDownload(){
        try {
            boolean respuesta = false;
            ClienteController cc = new ClienteController(getContext());
            ProductoController proc = new ProductoController(getContext());
            if (cc.count() < 1 || proc.count() < 1) {
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
            ParameterController pc = new ParameterController(getContext());
            Parameter p = new Parameter();
            p = pc.abrir().findById(GlobalValues.getINSTANCIA().PARAM_INSTALLED);
            boolean respuesta = false;
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

    public boolean descargarData(){
        /* Va a descargar toda la info desde el sitio web */
        //Marcas
        try {
            HelperMarcas hm = new HelperMarcas(getContext());
            hm.execute();

            //Categorias
            HelperCategorias hc = new HelperCategorias(getContext());
            hc.execute();

            HelperProductos hp = new HelperProductos(getContext());
            hp.execute();

            HelperClientes hcli = new HelperClientes(getContext());
            hcli.execute();
            return true;

        }catch(Exception e ){
            Log.d("IniciarApp", e.getMessage());
            return false;
        }
    }

    public void descargarImagenes(){

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void crearParameters(){
        try{
            ParameterController db = new ParameterController(this.getContext());
            Parameter parametro = new Parameter();
            //parametro.setNombre(GlobalValues.getINSTANCIA().PARAM_REINICIARAPP);
            parametro.setDescripcion("Sirve para ver si la aplicacion ya se encuentra instalada");
            parametro.setValor_integer(0);
            db.abrir();
            db.agregar(parametro);
            db.cerrar();
            Parameter param = db.findById(1);

            crearParametersLogin();

        }catch(Exception e){
            Toast.makeText(this.getContext(),"IniciarAPP:"+e.getMessage(), Toast.LENGTH_LONG);
            Log.println(Log.DEBUG,"IniciarAPP:",e.getMessage());
        }


    }
}
