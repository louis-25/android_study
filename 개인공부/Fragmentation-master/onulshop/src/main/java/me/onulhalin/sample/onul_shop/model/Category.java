package me.onulhalin.sample.onul_shop.model;

import java.io.Serializable;

public class Category implements Serializable {

  public Long id;
  public String name;
  public String icon;
  public Integer draft;
  public String brief;
  public String color;
  public Long created_at;
  public Long last_update;

  public String category_id;
  public String product_id;

  public Category() {
  }

  public Category(String product_id, String category_id) {
    this.product_id = product_id;
    this.category_id = category_id;

  }


}
