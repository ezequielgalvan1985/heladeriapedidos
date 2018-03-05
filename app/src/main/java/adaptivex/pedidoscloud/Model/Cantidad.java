package adaptivex.pedidoscloud.Model;

/**
 * Created by egalvan on 5/3/2018.
 */

public class Cantidad {
    private boolean kilo;
    private boolean medio;
    private boolean cuarto;
    private boolean trescuartos;

    private Integer cucuruchos;
    private Integer cucharitas;

    public boolean isKilo() {
        return kilo;
    }

    public void setKilo(boolean kilo) {
        this.kilo = kilo;
    }

    public boolean isMedio() {
        return medio;
    }

    public void setMedio(boolean medio) {
        this.medio = medio;
    }

    public boolean isCuarto() {
        return cuarto;
    }

    public void setCuarto(boolean cuarto) {
        this.cuarto = cuarto;
    }

    public boolean isTrescuartos() {
        return trescuartos;
    }

    public void setTrescuartos(boolean trescuartos) {
        this.trescuartos = trescuartos;
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
}
