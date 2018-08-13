package adaptivex.pedidoscloud.Model;

import java.sql.Time;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class Horario {
    private Integer id;
    private Integer dia;
    private Time apertura;
    private Time cierre;
    private String observaciones;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Time getApertura() {
        return apertura;
    }

    public void setApertura(Time apertura) {
        this.apertura = apertura;
    }

    public Time getCierre() {
        return cierre;
    }

    public void setCierre(Time cierre) {
        this.cierre = cierre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
