package me.onulhalin.sample.onul_shop.connection.callback;
import java.io.Serializable;
import me.onulhalin.sample.onul_shop.model.login;

public class CallbackJoin implements Serializable {
    public String status = "";
    public String message = "";

    public login data = null;

}
