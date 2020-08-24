package com.nadxlib.robi_all_intregation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nadxlib.robi_all_intregation.advert.SP;

import static com.nadxlib.robi_all_intregation.advert.AdsLib.checkSubStatus;
import static com.nadxlib.robi_all_intregation.advert.AdsLib.subscribe;
import static com.nadxlib.robi_all_intregation.advert.SP.setSubCode;
import static com.nadxlib.robi_all_intregation.advert.bdapps.Robi.MSG_TEXT;
import static com.nadxlib.robi_all_intregation.advert.bdapps.Robi.USSD;

public class MainActivity extends AppCompatActivity {

    Button subscribe_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //First OF All We Check User Subscribed Or Not
        checkSubStatus(SP.getSubCode());


        //Setup Permission Method For Call
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                1);



        subscribe_button=findViewById(R.id.subscribe);


        //Where We Want To Put Our Subscription Method
        subscribe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First6 We Check User Subscribed Or Not Then We Allow Them To Access
                //IF SP.getSubscriptionStatus == true We Block Them,And Ask Them To Subscribe
                //IF SP.getSubscriptionStatus == false We Give them to access
                //So We Use This Method Anywhere We Want To Block User For Subscribe
                if (SP.getSubscriptionStatus()){
                    //showDialog is our method where we host SMS/USSD method and dialouge Design
                    showDialog(MainActivity.this);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Subscribed Successfully ",Toast.LENGTH_SHORT).show();
                }



            }
        });

    }



    //This Is the Hole Method Where We Use Our Subscription Options
    public void showDialog(final Activity activity) {


        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);



        dialog.setContentView(R.layout.sub2);

        final TextView textView_sub = dialog.findViewById(R.id.textView_sub);
        final TextView textView_sub1 = dialog.findViewById(R.id.textView_sub1);
        final Button button_s_daily = dialog.findViewById(R.id.button_s_daily);
        final Button button_s_daily_api = dialog.findViewById(R.id.button_s_daily_api);
        final Button bt_send_sms = dialog.findViewById(R.id.bt_send_sms);
        final Button submit_code = dialog.findViewById(R.id.submit_code);
        final Button ussd_call = dialog.findViewById(R.id.ussd_call);
        final Button otp_submit = dialog.findViewById(R.id.otp_submit);
        final LinearLayout ll_sub = dialog.findViewById(R.id.ll_sub);
        final LinearLayout ll_hide = dialog.findViewById(R.id.ll_hide);
        final LinearLayout ll_sub_1 = dialog.findViewById(R.id.ll_sub_1);
        final EditText otp_code = dialog.findViewById(R.id.otp_code);





        button_s_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView_sub.setText("সাবস্ক্রাইব করতে আপনার মোবাইল নাম্বার দিন");
                textView_sub1.setText("শুধুমাত্র রবি এবং এয়ারটেল গ্রাহকদের জন্য");
                // ll_sub.setVisibility(View.VISIBLE);
                ll_sub_1.setVisibility(View.GONE);
                bt_send_sms.setVisibility(View.VISIBLE);
                subscribe();

            }
        });
        submit_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_hide.setVisibility(View.VISIBLE);
//                ll_sub_1.setVisibility(View.INVISIBLE);

            }
        });


        //use CASS Api
        button_s_daily_api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call sub api
            }
        });


        //Use SMS Api
        bt_send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("smsto:21213");


                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", MSG_TEXT);
                activity.startActivity(intent);
                dialog.dismiss();
            }
        });


        otp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String otp  =otp_code.getText().toString();
                Log.e("get",otp);
                Log.e("test","asasd");
                Uri uri = Uri.parse("smsto:21213");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", otp);
                activity.startActivity(intent);
                dialog.dismiss();



            }
        });
        //Use USSD Api
        ussd_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test","asasd");
                //Log.e("test",otp);
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + USSD)));
                dialog.dismiss();
            }
        });

//        Button dialogButton = (Button) dialog.findViewById(R.id.video_ad);
//
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });


        dialog.show();

    }

}