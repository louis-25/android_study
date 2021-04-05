package me.onulhalin.sample.onul_shop.connection.callback;



import java.io.Serializable;

import me.onulhalin.sample.onul_shop.model.Product;


public class CallbackProductDetails implements Serializable {

  public String status = "";
  public Product product = null;

}