package com.nick.finalyearproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;


public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService() {

        //super("481100784977");
        super("1034075539594");

    }

    private static final String TAG = "===GCMIntentService===";


    @Override
    protected void onRegistered(Context arg0, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
    }

    @Override
    protected void onUnregistered(Context arg0, String arg1) {
        Log.i(TAG, "unregistered = "+arg1);
    }

    @Override
    protected void onMessage(Context arg0, Intent arg1) {
        Log.d(TAG, "new message= ");
        if(arg1.getExtras()!=null)
        {
            generateNotification(arg0,arg1.getExtras().getString("message"),arg1.getExtras().getString("title"),arg1.getExtras().getString("image"),arg1.getExtras().getString("link"),arg1.getExtras().getString("date"));
        }

    }

    @Override
    protected void onError(Context arg0, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        return super.onRecoverableError(context, errorId);
    }

    private static void generateNotification(Context context, String message,String Title,String img,String Link,String date) {
        //int icon = R.mipmap.ic_launcher;
        //long when = System.currentTimeMillis();


        try {
            SQLiteDatabase kalashdatabase;
            kalashdatabase = context.openOrCreateDatabase("sai.db", MODE_PRIVATE, null);
            kalashdatabase.execSQL("insert into notification(title,msg,link,img,date) values('" + Title + "','" + message + "','" + Link + "','" + img + "','" + date + "')");


        } catch (Exception e) {
            Log.d("Error", e.toString());
        }

        Intent notificationIntent=new Intent(context, PayOnline.class);;
        if(message.contains("By")) {
            notificationIntent = new Intent(context, CollectionAmount .class);
        }
        if(message.contains("Delay"))
        {
            notificationIntent = new Intent(context, PayOnline.class);
        }
        if(message.contains("Ended (P.O.P.A)"))
        {
            notificationIntent = new Intent(context, TimeEnded.class);

        }
        if(message.contains("Starts (P.O.P.A"))
        {
            notificationIntent = new Intent(context, TimeStarted.class);

        }

      //  String police_id=message.substring(message.lastIndexOf(" ")+1);
        notificationIntent.putExtra("type", "police_direct_fine");
        notificationIntent.putExtra("police_id", "-999");
        notificationIntent.putExtra("img", img);
        notificationIntent.putExtra("msg", message);
        notificationIntent.putExtra("Title", Title);
        notificationIntent.putExtra("Link", Link);
        notificationIntent.putExtra("date", date);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent intent = PendingIntent.getActivity(context,0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(intent)
                .setSmallIcon(android.R.drawable.ic_delete)
          //      .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.some_big_img))
                .setTicker("Message From SmartPark")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("SmartPark")
                .setContentText(message);
        Notification n = builder.build();

        nm.notify(0, n);

    }




}
