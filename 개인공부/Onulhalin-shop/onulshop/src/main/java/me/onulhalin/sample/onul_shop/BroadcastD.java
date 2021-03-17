package me.onulhalin.sample.onul_shop;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackOrderCancel;
import me.onulhalin.sample.onul_shop.model.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by GHKwon on 2016-02-17.
 */
public class BroadcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1");
//        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, FireBaseMessagingService.class), PendingIntent.FLAG_UPDATE_CURRENT);
//        //   Notification.Builder builder = new Notification.Builder(context);
//        builder.setSmallIcon(R.drawable.on).setTicker("HETT").setWhen(System.currentTimeMillis())
//                .setNumber(1).setContentTitle("푸쉬 제목").setContentText("푸쉬내용")
//                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);
//
//        notificationmanager.notify(1, builder.build());

        Log.d("kimtest","@@@호출함?"+intent.getStringExtra("test"));

        Order order = new Order();

        order.uid = intent.getStringExtra("test");
        order.cancel = "자동결제 취소";
        //  order.product_id ="325";
        API api = RestAdapter.createAPI();
        Call<CallbackOrderCancel> callbackCall = api.cancelOrder(order);

        callbackCall.enqueue(new Callback<CallbackOrderCancel>() {
            @Override
            public void onResponse(Call<CallbackOrderCancel> call, Response<CallbackOrderCancel> response) {
                CallbackOrderCancel resp = response.body();
                if (resp != null && resp.code.equals("0")) {
                    //    Log.d("kimtest","@@" +resp.user.name);
                    //  sharedPref.setOpenAppCounter(0);
                    //   Toast.makeText(FireBaseMessagingService.this, "취소 성공", Toast.LENGTH_SHORT).show();


                }
                else {
                    //  Toast.makeText(FireBaseMessagingService.this, "취소실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CallbackOrderCancel> call, Throwable t) {
                //  Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure", t.getMessage());
            }
        });


        Intent intenta = new Intent(context, BroadcastD.class);
      //  intenta.setAction(BroadcastD.ACTION_RESTART_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intenta, 0);
        AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }




    }


