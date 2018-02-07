package adaptivex.pedidoscloud.Model;

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

    public int getEmpleado_id() {
        return empleado_id;
    }

    public void setEmpleado_id(int empleado_id) {
        this.empleado_id = empleado_id;
    }
}
