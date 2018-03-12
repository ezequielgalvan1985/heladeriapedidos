package adaptivex.pedidoscloud.Model;

import android.database.Cursor;
import android.provider.Settings;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Core.WorkInteger;

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
    private ArrayList<Pedidodetalle> detalles;

    //CANTIDAD
    private Integer kilo;
    private Integer medio;
    private Integer cuarto;
    private Integer trescuartos;

    private Integer cucuruchos;
    private Integer cucharitas;

    //Direccion
    private String localidad;
    private String calle;
    private String nro;
    private String piso;
    private String telefono;
    private String contacto;

    private Integer cantidadPotes = 0 ;

    public Integer agregarPote(){
        return this.cantidadPotes++;
    }

    public Integer getCantidadPotes(){
        Integer cantidadPotes = WorkInteger.parseInteger(kilo.toString())   +
                                WorkInteger.parseInteger(medio.toString())  +
                                WorkInteger.parseInteger(cuarto.toString()) +
                                WorkInteger.parseInteger(trescuartos.toString());
        return cantidadPotes;
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
    }

    public Integer getCucharitas() {
        return cucharitas;
    }

    public void setCucharitas(Integer cucharitas) {
        this.cucharitas = cucharitas;
    }

    public Integer getKilo() {
        return kilo;
    }

    public void setKilo(Integer kilo) {
        this.kilo = kilo;
    }

    public Integer getMedio() {
        return medio;
    }

    public void setMedio(Integer medio) {
        this.medio = medio;
    }

    public Integer getCuarto() {
        return cuarto;
    }

    public void setCuarto(Integer cuarto) {
        this.cuarto = cuarto;
    }

    public Integer getTrescuartos() {
        return trescuartos;
    }

    public void setTrescuartos(Integer trescuartos) {
        this.trescuartos = trescuartos;
    }

    public Double calculateCantidadKilos(){
        Double cant = 0.0;
        cant =(Double.parseDouble(getKilo().toString()) * 1) +
                (Double.parseDouble(getMedio().toString()) * 0.5) +
                (Double.parseDouble(getCuarto().toString()) * 0.25) +
                (Double.parseDouble(getTrescuartos().toString()) * 0.75);
        return cant;
    }
    public Integer calculateCantidadGustos(){
        Integer cant = 0;
        cant = WorkInteger.parseInteger(getKilo().toString()) * 4;
        cant += WorkInteger.parseInteger(getMedio().toString()) * 3;
        cant += WorkInteger.parseInteger(getCuarto().toString()) * 3;
        cant += WorkInteger.parseInteger(getTrescuartos().toString()) * 3;
        return cant;
    }
    public String getStringCantidadHelado(){
        String message = "* Tu Compra es de " + this.calculateCantidadKilos().toString();
        message +=  " Kg, puedes elegir hasta "+ this.calculateCantidadGustos().toString() +" helados ";
        return message;
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
}
