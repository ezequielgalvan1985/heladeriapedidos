package adaptivex.pedidoscloud.Core.Interfaces;

import android.database.Cursor;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Model.Pedido;

/**
 * Created by egalvan on 22/3/2018.
 */

public interface ControllerInterface {
    public long add(Object object);



    public boolean edit(Object object);
    public boolean delete(Object object);
    public Cursor findAll();
    public Cursor findAllByIdAndroid(long idAndroid);
    public Cursor findAllById(long id);
    public ArrayList<Object> parseCursorToArrayList(Cursor c);

    public void limpiar();
    public void beginTransaction();
    public void flush();
    public void commit();



}
