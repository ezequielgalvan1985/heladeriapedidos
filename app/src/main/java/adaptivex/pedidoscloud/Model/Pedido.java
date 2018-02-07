package adaptivex.pedidoscloud.Model;

import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ezequiel on 25/06/2016.
 */
public class Pedido {
    private Integer id;
    private Integer idTmp;
    private String created;
    private Double subtotal;
    private Double iva;
    private Double monto;
    private Integer cliente_id;
    private Double bonificacion;
    private Integer estadoId;
    private Integer nroPedidoReal;

    //Entidades externas
    private Cliente cliente;
    private ArrayList<Pedidodetalle> detalles;

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

    public Integer getIdTmp() {
        return idTmp;
    }

    public void setIdTmp(Integer idTmp) {
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
        this.detalles = new ArrayList<Pedidodetalle>();

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
            this.detalles.add(registro);
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

}
