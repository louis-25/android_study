package me.onulhalin.sample.onul_shop.connection.callback;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.onulhalin.sample.onul_shop.model.Order;
import me.onulhalin.sample.onul_shop.model.Product;

public class CallbackOrder implements Serializable {
   // public String status = "";
    public String product_id;
    public String category_id;

    public String id;
    public String status;
    public List<Order> order = new ArrayList<>();

    public Order order_detail = null;


 public Product product_detail = null;
}
