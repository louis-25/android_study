package me.onulhalin.sample.onul_shop.data;



import me.onulhalin.sample.onul_shop.connection.callback.CallbackProduct;

public class listviewitem {
    private int icon;
    private CallbackProduct name;
    public int getIcon(){return icon;}
    public CallbackProduct getName(){return name;}
    public listviewitem(CallbackProduct name){

        this.name=name;
    }
}