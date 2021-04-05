package me.onulhalin.sample.onul_shop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {

    public Long id;
    public String name;
    public String image;
    public int price;
    public int price_discount;
    public int stock;
    public int draft;
    public String description;
    public String status;
    public String created_at;
    public String last_update;
    public String userid;

    public String ptstart;
    public String ptend;
    public String delivery;
    public String type;

    public List<Category> categories = new ArrayList<>();
    public List<ProductImage> product_images = new ArrayList<>();

}
