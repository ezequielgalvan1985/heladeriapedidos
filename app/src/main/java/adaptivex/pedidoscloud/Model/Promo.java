package adaptivex.pedidoscloud.Model;

import java.util.Date;

import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Core.WorkNumber;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class Promo {

    //Se guardan en la BD
    private Integer id;
    private Integer idAndroid;
    private String nombre;
    private String descripcion;
    private String fechaDesde;
    private String fechaHasta;
    private Integer cantKilos;
    private Double importeDescuento;
    private Double precioPromo;
    private Double precioAnterior;
    private boolean enabled;

    private Integer cantPoteCuarto;
    private Integer cantPoteMedio;
    private Integer cantPoteTresCuarto;
    private Integer cantPoteKilo;


    //atributos, que no se guardan en la base de datos
    private Double mountDiscount;
    private Integer countDiscount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {

        return "'"+descripcion+"'";
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



    public Integer getCantKilos() {
        return cantKilos;
    }

    public String getCantKilosFormatString(){
        return WorkNumber.kilosFormat(cantKilos);
    }

    public void setCantKilos(Integer cantKilos) {
        this.cantKilos = cantKilos;
    }

    public Double getImporteDescuento() {
        return importeDescuento;
    }

    public String getPorcentajeDescuentoDescripcion(){
        String texto ="";
        texto =  "-"+calculatePorcentajeDescuento().toString()+"% OFF" ;
        return texto;
    }
    public Integer calculatePorcentajeDescuento(){
        Double porcentaje = Math.floor(((precioAnterior - precioPromo) /precioAnterior)*100);

        return porcentaje.intValue();
    }

    public void setImporteDescuento(Double importeDescuento) {
        this.importeDescuento = importeDescuento;
    }

    public Double getPrecioPromo() {
        return precioPromo;
    }
    public String getPrecioPromoFormatMoney(){

        return WorkNumber.moneyFormat(precioPromo);
    }
    public String getPrecioPromoFormatMoney2(){

        return  WorkNumber.moneyFormat(precioPromo);
    }

    public String getPrecioAnteriorFormatMoney(){
        String texto = "(antes "+WorkNumber.moneyFormat(precioAnterior) +")";
        return texto;
    }

    public void setPrecioPromo(Double precioPromo) {
        this.precioPromo = precioPromo;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Integer getIdAndroid() {
        return idAndroid;
    }

    public void setIdAndroid(Integer idAndroid) {
        this.idAndroid = idAndroid;
    }

    public String getFechaDesdeFormatDMY(){
        return WorkDate.changeFormatDateString(this.fechaDesde, Constants.DATE_FORMAT_SQLITE, Constants.DATE_FORMAT_DISPLAY_APP);
    }

    public String getVigenciaDescripcion(){
        String texto="Promo valida desde " + getFechaDesdeFormatDMY() + " al " + getFechaHastaFormatDMY();
        return texto;
    }
    public String getFechaHastaFormatDMY(){
        return WorkDate.changeFormatDateString(this.fechaHasta, Constants.DATE_FORMAT_SQLITE, Constants.DATE_FORMAT_DISPLAY_APP);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Double getMountDiscount() {
        return mountDiscount;
    }

    public void setMountDiscount(Double mountDiscount) {
        this.mountDiscount = mountDiscount;
    }


    public Integer getCountDiscount() {
        return countDiscount;
    }

    public void setCountDiscount(Integer countDiscount) {
        this.countDiscount = countDiscount;
    }

    public Double getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(Double precioAnterior) {
        this.precioAnterior = precioAnterior;
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
