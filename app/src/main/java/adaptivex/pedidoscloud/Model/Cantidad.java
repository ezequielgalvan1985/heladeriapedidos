package adaptivex.pedidoscloud.Model;

import adaptivex.pedidoscloud.Core.WorkInteger;

/**
 * Created by egalvan on 5/3/2018.
 */

public class Cantidad {
    private Integer kilo;
    private Integer medio;
    private Integer cuarto;
    private Integer trescuartos;

    private Integer cucuruchos;
    private Integer cucharitas;



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
}
