package adaptivex.pedidoscloud.Model;

import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;

/**
 * Created by ezequiel on 25/06/2016.
 */
public class Pedido {
    private Integer id;
    private long idTmp;
    private String  created;
    private Double  precioxkilo;
    private Double  subtotal;
    private Double  iva;
    private Double  monto;
    private Integer cliente_id;
    private Double  bonificacion;
    private Integer estadoId;
    private Integer nroPedidoReal;

    //Para la heladeria
    private ArrayList<Pote> potes;

    //Entidades externas
    private Cliente cliente;
    private ArrayList<Pedidodetalle> detalles ;


    private Integer cantidadKilos= 0;
    private Integer cucuruchos= 0 ;
    private Integer cucharitas= 0 ;
    private boolean envioDomicilio;
    private Integer cantidadPotes = 0 ;

    //Direccion
    private String localidad;
    private String calle;
    private String nro;
    private String piso;
    private String telefono;
    private String contacto;

    private double montoCucuruchos;
    private double montoHelados;


    public String getProporcionDesc(Integer proporcion){
        String cadena = "";
        if (proporcion >=Constants.MEDIDA_HELADO_POCO_DESDE  && proporcion <= Constants.MEDIDA_HELADO_POCO_HASTA){
            cadena = Constants.MEDIDA_HELADO_POCO;
        }
        if (proporcion >=Constants.MEDIDA_HELADO_EQUILIBRADO_DESDE  && proporcion <= Constants.MEDIDA_HELADO_EQUILIBRADO_HASTA){
            cadena = Constants.MEDIDA_HELADO_EQUILIBRADO;
        }
        if (proporcion >=Constants.MEDIDA_HELADO_MUCHO_LIMIT_DESDE  && proporcion <= Constants.MEDIDA_HELADO_MUCHO_LIMIT_HASTA){
            cadena = Constants.MEDIDA_HELADO_MUCHO;
        }
        return cadena;
    }

    public double getPrecioMedidaPote(Integer medidaPote){
        double precio = 0.0;
        if (medidaPote== Constants.MEDIDA_KILO){
            precio = Constants.PRECIO_HELADO_KILO;
        }
        if (medidaPote==Constants.MEDIDA_MEDIO){
            precio = Constants.PRECIO_HELADO_MEDIO;
        }
        if (medidaPote==Constants.MEDIDA_CUARTO){
            precio = Constants.PRECIO_HELADO_CUARTO;
        }
        if (medidaPote==Constants.MEDIDA_TRESCUARTOS){
            precio = Constants.PRECIO_HELADO_TRESCUARTOS;
        }
        return precio;
    }


    public Pedido(){
        this.detalles = new ArrayList<Pedidodetalle>();
        this.cantidadKilos = 0;
        this.cantidadPotes = 0;
        this.cucharitas = 0 ;
        this.cucuruchos = 0;
        this.montoCucuruchos = 0.0;
        this.montoHelados = 0.0;
        this.monto = 0.0;

    }


    //Cantidad, es la medida del pote
    public void agregarPote(Integer cantidad){
        try{
            this.cantidadKilos += cantidad;
            this.setCantidadPotes(this.getCantidadPotes() + 1);
            this.setMontoHelados(this.getMontoHelados() + getPrecioMedidaPote(cantidad));
            this.setMonto(this.getMontoHelados() + this.getMontoCucuruchos());
        }catch (Exception e){
            Log.d("PedidoError", e.getMessage());
        }

    }
    public String getKilosHeladosString(){
        String cartel = String.valueOf(this.cantidadKilos/1000)+ " KG " ;
        return cartel;
    }

    public void setMontoCucuruchos(Integer cucuruchos){
        this.setMontoCucuruchos(cucuruchos * Constants.PRECIO_CUCURUCHO);
    }


    public void quitarPote(Integer cantidad){
        this.cantidadKilos -= cantidad;
        this.setCantidadPotes(this.getCantidadPotes() - 1);
    }

    public void addPedidodetalle(Pedidodetalle pd){
        this.detalles.add(pd);
    }


    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getStringDireccion(){
        String direccion = "Localidad: "+  getLocalidad() + "\n"+
                "Calle:" + getCalle() + " " + getNro() + " (Piso:" + getPiso() + ")\n"+
                "Telefono: " + getTelefono() +"\n"+
                "Contacto: " + getContacto() +" )";
        return direccion;
    }


    public Integer getCucuruchos() {
        return cucuruchos;
    }

    public void setCucuruchos(Integer cucuruchos) {
        this.cucuruchos = cucuruchos;
        //Cada vez que se setea los cucuruchos se calcula su monto
        setMontoCucuruchos(this.cucuruchos);
        this.setMonto(this.getMontoHelados() + this.getMontoCucuruchos());
    }

    public Integer getCucharitas() {
        return cucharitas;
    }

    public void setCucharitas(Integer cucharitas) {
        this.cucharitas = cucharitas;
    }





    //GETTERS AND SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Integer getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Integer cliente_id) {
        this.cliente_id = cliente_id;
    }

    public Double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(Double bonificacion) {
        this.bonificacion = bonificacion;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }


    public Integer getNroPedidoReal() {
        return nroPedidoReal;
    }

    public void setNroPedidoReal(Integer nroPedidoReal) {
        this.nroPedidoReal = nroPedidoReal;
    }

    public long getIdTmp() {
        return idTmp;
    }

    public void setIdTmp(long idTmp) {
        this.idTmp = idTmp;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Pedidodetalle> getDetalles() {
        return detalles;
    }



    public void setDetalles(Cursor c) {
        //Recibe cursor y completa el arralist de pedidodetalles
        Pedidodetalle registro;
        this.setDetalles(new ArrayList<Pedidodetalle>());

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            registro = new Pedidodetalle();
            registro.setPedidoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PEDIDO_ID)));
            registro.setProductoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRODUCTO_ID)));
/*
            Log.d("Debug: detaped", String.valueOf(registro.getProductoId()));
            producto = dbProducto.abrir().buscar(registro.getProductoId());
            registro.setProducto(producto);
            dbProducto.cerrar();
*/
            registro.setCantidad(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_CANTIDAD)));
            registro.setPreciounitario(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_PRECIOUNITARIO)));
            registro.setMonto(c.getDouble(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_MONTO)));
            registro.setEstadoId(c.getInt(c.getColumnIndex(PedidodetalleDataBaseHelper.CAMPO_ESTADO_ID)));
            this.getDetalles().add(registro);
            registro = null;
        }
    }

    public String getCreatedDMY(){
        String fecha=getCreated();
        try{
            DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date = df1.parse(getCreated());
            fecha = df2.format(date);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return fecha;
    }

    public void setDetalles(ArrayList<Pedidodetalle> detalles) {
        this.detalles = detalles;
    }

    public Double getPrecioxkilo() {
        return precioxkilo;
    }

    public void setPrecioxkilo(Double precioxkilo) {
        this.precioxkilo = precioxkilo;
    }

    public Integer getCantidadKilos() {
        return cantidadKilos;
    }

    public void setCantidadKilos(Integer cantidadKilos) {
        this.cantidadKilos = cantidadKilos;
    }

    public boolean isEnvioDomicilio() {
        return envioDomicilio;
    }

    public void setEnvioDomicilio(boolean envioDomicilio) {
        this.envioDomicilio = envioDomicilio;
    }

    public Integer getCantidadPotes() {
        return cantidadPotes;
    }

    public void setCantidadPotes(Integer cantidadPotes) {
        this.cantidadPotes = cantidadPotes;
    }

    public double getMontoCucuruchos() {
        return montoCucuruchos;
    }

    public void setMontoCucuruchos(double montoCucuruchos) {
        this.montoCucuruchos = montoCucuruchos;
    }

    public double getMontoHelados() {
        return montoHelados;
    }

    public void setMontoHelados(double montoHelados) {
        this.montoHelados = montoHelados;
    }
}
