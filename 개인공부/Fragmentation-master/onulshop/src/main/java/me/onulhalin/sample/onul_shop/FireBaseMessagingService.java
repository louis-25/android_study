package me.onulhalin.sample.onul_shop;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.activity.NumberPickerActivity;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackOrderCancel;
import me.onulhalin.sample.onul_shop.model.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Notification.DEFAULT_SOUND;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE;


public class FireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    String serial="";

    @Override

    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());



        if (remoteMessage.getData().size() > 0) {

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());



            if (true) {

            } else {

                handleNow();

            }

        }


        showNotification(remoteMessage);

        if (remoteMessage.getData() != null) {

          //  Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        //    sendNotification(remoteMessage.getNotification().getBody());

        }

    }

    private void handleNow() {

        Log.d(TAG, "Short lived task is done.");

    }



    private void sendNotification(String messageBody) {

        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,

                PendingIntent.FLAG_ONE_SHOT);



        String channelId = getString(R.string.default_notification_channel_id);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =

                new NotificationCompat.Builder(this, channelId)

                        .setSmallIcon(R.drawable.ic_launcher)

                        .setContentTitle("FCM Message")

                        .setContentText(messageBody)

                        .setAutoCancel(true)

                        .setSound(defaultSoundUri)

                        .setContentIntent(pendingIntent);



        NotificationManager notificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelName = getString(R.string.default_notification_channel_id);

            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(channel);

        }

        notificationManager.notify(0, notificationBuilder.build());

    }





    private void showNotification(RemoteMessage remoteMessage){


        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE );
        PowerManager.WakeLock wakeLock = pm.newWakeLock( PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG" );
        wakeLock.acquire(3000);




//  NotificationCompat.Builder mBuilder =
//          new NotificationCompat.Builder(getBaseContext().getApplicationContext(), "notify_001");
//  Intent ii = new Intent(getBaseContext().getApplicationContext(), DefaultIntroActivity.class);
//  PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, ii, 0);


        String title2 = remoteMessage.getData().get("title");
        String body2 = remoteMessage.getData().get("message");
         serial = remoteMessage.getData().get("serial");

        if (serial!=null) {

            Handler handler = new Handler(Looper.getMainLooper());


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    submitOrderData(serial);

                  //  Toast.makeText(FireBaseMessagingService.this, serial, Toast.LENGTH_SHORT).show();
                    // TODO
                }
            }, 10000);



             getRunActivity();
             // Intent intent = new Intent(getBaseContext(),NumberPickerActivity.class);
           //    startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));

        }




//  String title=remoteMessage.getNotification().getTitle();
//  String body=remoteMessage.getNotification().getBody();
//  NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
////  bigText.bigText(verseurl);
//  bigText.setBigContentTitle(title2);
//  bigText.setSummaryText(body2);
//
//  mBuilder.setContentIntent(pendingIntent);
//  mBuilder.setSmallIcon(R.mipmap.launcher_icon);
//  mBuilder.setContentTitle(title2);
//  mBuilder.setContentText(body2);
// // mBuilder.setPriority(Notification.PRIORITY_MAX);
//  mBuilder.setStyle(bigText);
//  mBuilder.setVibrate(new long[]{1000, 1000});
//
//mBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
//  mBuilder.setDefaults(Notification.DEFAULT_ALL);
//  mBuilder.setPriority(Notification.PRIORITY_HIGH);
//
//  Log.d("kimtest","@@@@@@화면깨우기"+title2);
//
//
//  NotificationManager mNotificationManager =
//          (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String name = title2;
            String description = body2;
            int importance = NotificationManager.IMPORTANCE_HIGH; //Important for heads-up notification
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


//    NotificationChannel channel = new NotificationChannel("notify_001",
//            "Channel human readable title",
//            NotificationManager.IMPORTANCE_DEFAULT);
//    mNotificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.backbtn)
                .setColor(getResources().getColor(R.color.bg_app))
                .setContentTitle(title2)
                .setContentText(body2)
                .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE) //Important for heads-up notification
                .setPriority(Notification.PRIORITY_MAX);



//  mNotificationManager.notify(0, mBuilder.build());


        Notification buildNotification = mBuilder.build();
        NotificationManager mNotifyMgr = (NotificationManager) getBaseContext().getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(001, buildNotification);

//
//  Intent intent=new Intent(this,DefaultIntroActivity.class);
//  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
//          PendingIntent.FLAG_ONE_SHOT);
//  Bitmap notifyImage = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//  Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//  NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//          .setSmallIcon(R.mipmap.ic_launcher)
//          .setLargeIcon(notifyImage)
//          .setColor(Color.parseColor("#FFE74C3C"))
//          .setContentTitle(title)
//          .setContentText(body)
//          .setAutoCancel(true)
//          .setSound(defaultSoundUri)
////          .setDefaults(Notification.DEFAULT_ALL)
//          .setPriority(Notification.PRIORITY_HIGH)
//          .setVisibility(Notification.VISIBILITY_PUBLIC)
//          .setContentIntent(pendingIntent);
//  NotificationManager notificationManager =
//          (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//  notificationManager.notify(0, notificationBuilder.build());


    }

    void getRunActivity()	{

        ActivityManager activity_manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> task_info = activity_manager.getRunningTasks(9999);



        for(int i=0; i<task_info.size(); i++) {

            Log.d("kimtest", "[" + i + "] activity:"+ task_info.get(i).topActivity.getPackageName() + " >> " + task_info.get(i).topActivity.getClassName());

      if (String.valueOf(task_info.get(i).topActivity.getClassName()).equals("me.yokeyword.sample.demo_zhihu.activity.NumberPickerActivity")){

      }
      else {


      }
            if (task_info.size()>1){
                Intent intent = new Intent(getBaseContext(),NumberPickerActivity.class);
                startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));

            }
            else {

            }
        }

    }

    private void submitOrderData(String key) {


        Order order = new Order();

        order.uid = key;
        order.cancel = "app결제 취소 테스트";
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
                    Toast.makeText(FireBaseMessagingService.this, "취소 성공", Toast.LENGTH_SHORT).show();


                }
                else {
                    Toast.makeText(FireBaseMessagingService.this, "취소실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CallbackOrderCancel> call, Throwable t) {
                //  Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure", t.getMessage());
            }
        });
    }


}



