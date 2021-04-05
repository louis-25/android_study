package me.onulhalin.sample.onul_shop.connection.callback;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.onulhalin.sample.onul_shop.model.Category;

public class CallbackCategory implements Serializable {
  public String status = "";
  public String product_id;
  public String category_id;


  public List<Category> categories = new ArrayList<>();
}
