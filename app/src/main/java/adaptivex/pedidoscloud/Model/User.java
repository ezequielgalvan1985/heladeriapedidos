package adaptivex.pedidoscloud.Model;

import adaptivex.pedidoscloud.Core.WorkString;

/**
 * Created by ezequiel on 23/05/2016.
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private int entidad_id;
    private int group_id;
    private int empleado_id;

    private String telefono;
    private String localidad;
    private String calle;
    private String nro;
    private String piso;
    private String contacto;

    private String logued;
    private Integer id_android;

    private static String createTable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getUserDescription(){
        return getUsername() + " (" + getEmail() + ")";

    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




    protected static void setCreateTable(){
        createTable ="CREATE TABLE telefonos " +
                "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "numero TEXT NOT NULL " +
                ")";
    }

    public static String getCreatetable(){
        setCreateTable();
        return createTable;

    }

    public int getEntidad_id() {
        return entidad_id;
    }

    public void setEntidad_id(int entidad_id) {
        this.entidad_id = entidad_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }



    public String getLocalidad() {
        return WorkString.getTexto(localidad);
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return WorkString.getTexto(calle);
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNro() {
        if (nro==null) nro = "";
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public String getPiso() {
        if (piso==null) piso = "";
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getContacto() {
        if (contacto==null){
            contacto = "";
        }
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {

        return WorkString.getTexto(telefono);
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getLogued() {
        return logued;
    }

    public void setLogued(String logued) {
        this.logued = logued;
    }

    public Integer getId_android() {
        return id_android;
    }

    public void setId_android(Integer id_android) {
        this.id_android = id_android;
    }
}
