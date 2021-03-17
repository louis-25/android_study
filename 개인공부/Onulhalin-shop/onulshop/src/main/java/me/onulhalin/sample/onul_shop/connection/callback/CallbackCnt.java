package me.onulhalin.sample.onul_shop.connection.callback;


import java.io.Serializable;

import me.onulhalin.sample.onul_shop.model.Status;

public class CallbackCnt implements Serializable {

  public String status = "";
  public Status status1 = new Status();
  public String stock;
}