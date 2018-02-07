package adaptivex.pedidoscloud.Pedidos;

/**
 * Created by ezequiel on 15/11/2017.
 */

public class PedidoException extends Exception {
    String message;
    Throwable cause;
    public PedidoException() {
        super();
    }

    public PedidoException(String message, Throwable cause)
    {
        super(message, cause);

        this.cause = cause;
        this.message = message;
    }

}
