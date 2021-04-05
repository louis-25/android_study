package me.onulhalin.sample.onul_shop.model;

import java.io.Serializable;

public class ProductOrder implements Serializable {

  public String buyer;
  public String address;
  public String email;
  public String shipping;
  public Long date_ship;
  public String phone;
  public String comment;
  public String status;
  public Double total_fees;
  public Double tax;
  public String serial;
  public Long created_at = System.currentTimeMillis();
  public Long last_update = System.currentTimeMillis();
  public String test ;

  public ProductOrder() {
  }

  public ProductOrder(BuyerProfile buyerProfile, String shipping, Long date_ship, String comment) {
    this.buyer = buyerProfile.name;
    this.address = buyerProfile.address;
    this.email = buyerProfile.email;
    this.phone = buyerProfile.phone;
    this.shipping = shipping;
    this.date_ship = date_ship;
    this.comment = comment;

  }
}