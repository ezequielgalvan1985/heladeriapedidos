package adaptivex.pedidoscloud.Model;

import java.sql.Date;
import java.sql.Date;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class Horario {
    private Integer id;
    private Integer dia;
    private Date apertura;
    private Date cierre;
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

    public Date getApertura() {
        return apertura;
    }

    public void setApertura(Date apertura) {
        this.apertura = apertura;
    }

    public Date getCierre() {
        return cierre;
    }

    public void setCierre(Date cierre) {
        this.cierre = cierre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
