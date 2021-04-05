package me.onulhalin.sample.onul_shop.connection.callback;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.onulhalin.sample.onul_shop.model.Product;

public class CallbackProduct implements Serializable {

  public String status = "";
  public int count = -1;
  public int count_total = -1;
  public int pages = -1;
  public List<Product> products = new ArrayList<>();
  public List<Product> product = new ArrayList<>();
  public Product data = new Product();
}