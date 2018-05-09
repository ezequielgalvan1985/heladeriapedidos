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
import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Core.WorkNumber;

/**
 * Created by ezequiel on 25/06/2016.
 */
public class Pedido {
    private Integer id;
    private long    idTmp;
    private String  created;
    private Date    fechaWebRecibido;
    private Date    fechaWebEnviado;

    private Double  subtotal;
    private Double  iva;
    private Double  monto;
    private Integer cliente_id;
    private Double  bonificacion;
    private Integer estadoId;
    private Integer nroPedidoReal;
    private Double  montoabona;




    //Datos que no se guardan en la DB
    //Para la heladeria
    private ArrayList<Pote> potes;

    //Entidades externas
    private Cliente cliente;
    private ArrayList<Pedidodetalle> detalles ;





    //Direccion
    private String localidad;
    private String calle;
    private String nro;
    private String piso;
    private String telefono;
    private String contacto;

    private Double  precioxkilo;
    private Double  montoDescuento;
    private Integer cantidadDescuento;


    private Integer cantidadKilos= 0;
    private Integer cucuruchos= 0 ;
    private Integer cucharitas= 0 ;
    private Double  montoCucuruchos;
    private Double  montoHelados;
    private boolean envioDomicilio;
    private Integer cantidadPotes = 0 ;

    private Date     horaRecepcion;
    private Integer  tiempoDemora;
    private Date     horaentrega;


    private Integer cantPoteCuarto;
    private Integer cantPoteMedio;
    private Integer cantPoteTresCuarto;
    private Integer cantPoteKilo;


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
            cantidadKilos += cantidad;
            setCantidadPotes(getCantidadPotes() + 1);
            addCantPoteMedida(cantidad);
            setMontoHelados(getMontoHelados() + getPrecioMedidaPote(cantidad));
            refreshMontoTotal();
        }catch (Exception e){
            Log.d("PedidoError", e.getMessage());
        }
    }
    public void addCantPoteMedida(Integer cantidad){
        switch (cantidad){
            case Constants.MEDIDA_CUARTO:
                cantPoteCuarto +=1;
                break;
            case Constants.MEDIDA_MEDIO:
                cantPoteMedio +=1;
                break;
            case Constants.MEDIDA_TRESCUARTOS:
                cantPoteTresCuarto +=1;
                break;
            case Constants.MEDIDA_KILO:
                cantPoteKilo +=1;
                break;
        }
    }

    public void deleteCantPoteMedida(Integer cantidad){
        switch (cantidad){
            case Constants.MEDIDA_CUARTO:
                cantPoteCuarto -=1;
                break;
            case Constants.MEDIDA_MEDIO:
                cantPoteMedio -=1;
                break;
            case Constants.MEDIDA_TRESCUARTOS:
                cantPoteTresCuarto -=1;
                break;
            case Constants.MEDIDA_KILO:
                cantPoteKilo -=1;
                break;
        }
    }

    public String getKilosHeladosString(){
        //String cartel = String.valueOf(this.cantidadKilos/1000)+ " KG " ;
        String cartel =  GlobalValues.getINSTANCIA().PEDIDO_TEMPORAL.getCantidadKilos().toString() +"Kg";
        return cartel;
    }

    public void setMontoCucuruchos(Double monto){
        this.montoCucuruchos= monto;
    }


    public void quitarPote(Integer cantidad){
        this.cantidadKilos -= cantidad;
        this.setCantidadPotes(this.getCantidadPotes() - 1);
    }

    public void addPedidodetalle(Pedidodetalle pd){
        this.detalles.add(pd);
    }

    public void editPedidodetalle(Pedidodetalle pd_param){
        try{
            Integer index = 0;
            for (Pedidodetalle pd : detalles){

                if (pd_param.getIdTmp() == pd.getIdTmp()){

                    this.detalles.set(index, pd_param);

                }
                index++;
            }
        }catch (Exception e ){
            Log.e( "Error ", e.getMessage());
        }

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
        String direccion = "Localidad: " + getLocalidad() + "\n"+
                           "Calle:         " + getCalle() + " " + getNro() + " (Piso: " + getPiso() + ")\n"+
                           "Telefono:  " + getTelefono() +"\n"+
                           "Contacto: " + getContacto() ;
        return direccion;
    }


    public Integer getCucuruchos() {
        return WorkNumber.getValue(cucuruchos);
    }

    public void setCucuruchos(Integer cucuruchos) {
        this.cucuruchos = cucuruchos;
        //Cada vez que se setea los cucuruchos se calcula su monto
        Double monto = cucuruchos * Constants.PRECIO_CUCURUCHO;
        setMontoCucuruchos(monto);
        refreshMontoTotal();
    }

    public void refreshMontoTotal(){
        subtotal =  WorkNumber.getValue(montoHelados) + WorkNumber.getValue(montoCucuruchos);
        monto = WorkNumber.getValue(montoHelados) + WorkNumber.getValue(montoCucuruchos) - WorkNumber.getValue(montoDescuento);
    }

    public Integer getCucharitas() {
        return WorkNumber.getValue(cucharitas);
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
        return WorkNumber.getValue(subtotal);
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIva() {
        return WorkNumber.getValue(iva);
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getMonto() {
        return WorkNumber.getValue(monto);
    }

    public String getMontoFormatMoney(){
        return WorkNumber.moneyFormat(getMonto());
    }

    public String getMontoHeladoFormatMoney(){
        return WorkNumber.moneyFormat(getMontoHelados());
    }

    public String getMontoAbonaFormatMoney(){
        return WorkNumber.moneyFormat(getMontoabona());
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
        return WorkNumber.getValue(bonificacion);
    }

    public void setBonificacion(Double bonificacion) {
        this.bonificacion = bonificacion;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public String getEstadoDescripcion(){
        String estadodesc ="";
        return estadodesc;
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
        return WorkNumber.getValue(precioxkilo);
    }

    public void setPrecioxkilo(Double precioxkilo) {
        this.precioxkilo = precioxkilo;
    }

    public String getCantidadKilosFormatString(){
        return WorkNumber.kilosFormat(this.getCantidadKilos());
    }

    public Integer getCantidadKilos() {
        return WorkNumber.getValue(cantidadKilos);
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
        return WorkNumber.getValue(cantidadPotes);
    }

    public void setCantidadPotes(Integer cantidadPotes) {
        this.cantidadPotes = cantidadPotes;
    }

    public double getMontoCucuruchos() {
        return WorkNumber.getValue(montoCucuruchos);
    }
    public String getMontoCucuruchosFormatMoney() {
        return WorkNumber.moneyFormat(this.montoCucuruchos);
    }



    public double getMontoHelados() {
        return WorkNumber.getValue(montoHelados);
    }

    public void setMontoHelados(double montoHelados) {
        this.montoHelados = montoHelados;
    }

    public String getEnvioDomicilio(){
        String cartel = "NO";
        if (this.envioDomicilio) cartel = "SI";
        return cartel;
    }
    public boolean getEnvioDomicilioBoolean(){
        return this.envioDomicilio;
    }

    public Double getMontoDescuento() {
        return WorkNumber.getValue(montoDescuento);
    }
    public String getMontoDescuentoFormatMoney(){
        return WorkNumber.moneyFormat(this.montoDescuento);
    }
    public void setMontoDescuento(Double montoDescuento) {
        this.montoDescuento = montoDescuento;
        refreshMontoTotal();
    }

    public Integer getCantidadDescuento() {
        return WorkNumber.getValue(cantidadDescuento);
    }

    public void setCantidadDescuento(Integer cantidadDescuento) {
        this.cantidadDescuento = cantidadDescuento;
    }





    public Date getFechaWebRecibido() {
        return fechaWebRecibido;
    }

    public void setFechaWebRecibido(Date fechaWebRecibido) {
        this.fechaWebRecibido = fechaWebRecibido;
    }

    public Date getFechaWebEnviado() {
        return fechaWebEnviado;
    }

    public void setFechaWebEnviado(Date fechaWebEnviado) {
        this.fechaWebEnviado = fechaWebEnviado;
    }

    public Date getHoraentrega() {
        return horaentrega;
    }

    public void getHoraentrega(Date horaentrega) {
        this.setHoraentrega(horaentrega);
    }

    public void setHoraentrega(Date horaentrega) {
        this.horaentrega = horaentrega;
    }

    //Recibe Hora en String y lo pasa a
    public void setHoraEntregaStringToDate(String paramHoraentrega){
        this.horaentrega = WorkDate.parseStringToDate(paramHoraentrega);

    }

    public Date getHoraRecepcion() {
        return horaRecepcion;
    }

    public void setHoraRecepcion(Date horaRecepcion) {
        this.horaRecepcion = horaRecepcion;
    }

    public Integer getTiempoDemora() {
        return tiempoDemora;
    }

    public void setTiempoDemora(Integer tiempoDemora) {
        this.tiempoDemora = tiempoDemora;
    }

    public Double getMontoabona() {
        return montoabona;
    }

    public void setMontoabona(Double montoabona) {
        this.montoabona = montoabona;
    }

    public Integer getCantPoteCuarto() {
        return cantPoteCuarto;
    }

    public void setCantPoteCuarto(Integer cantPoteCuarto) {
        this.cantPoteCuarto = cantPoteCuarto;
    }

    public Integer getCantPoteMedio() {
        return cantPoteMedio;
    }

    public void setCantPoteMedio(Integer cantPoteMedio) {
        this.cantPoteMedio = cantPoteMedio;
    }

    public Integer getCantPoteTresCuarto() {
        return cantPoteTresCuarto;
    }

    public void setCantPoteTresCuarto(Integer cantPoteTresCuarto) {
        this.cantPoteTresCuarto = cantPoteTresCuarto;
    }

    public Integer getCantPoteKilo() {
        return cantPoteKilo;
    }

    public void setCantPoteKilo(Integer cantPoteKilo) {
        this.cantPoteKilo = cantPoteKilo;
    }
}
