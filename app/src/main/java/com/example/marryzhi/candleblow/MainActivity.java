package com.example.marryzhi.candleblow;

import android.Manifest;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static android.media.AudioFormat.CHANNEL_IN_MONO;
import static com.example.marryzhi.candleblow.AudioRecordDemo.dist;

public class MainActivity extends AppCompatActivity {

    static DevicePolicyManager dpm ;
   static ComponentName componentName;
    private static final int REQUEST_CODE_ACTIVE_COMPONENT=1;
    Button start;
    static Activity activity;
    static RelativeLayout bg;
    private static String[] PERMISSION = {      //需要动态申请的权限
            Manifest.permission.RECORD_AUDIO
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=MainActivity.this;
        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this,lockReceiver.class);
        start=findViewById(R.id.start);
        bg=findViewById(R.id.bg);

        requestPermissions(MainActivity.this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                new AudioRecordDemo().getNoiseLevel();
                if(dist==true) bg.setBackgroundResource(R.drawable.cloud);
                }

        }).start();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dpm.isAdminActive(componentName)){
                    Toast.makeText(MainActivity.this,"设备管理器：已激活",Toast.LENGTH_SHORT);
                }else{
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,componentName);
                    startActivityForResult(intent,REQUEST_CODE_ACTIVE_COMPONENT);
                }
            }
        });

    }





    public static void requestPermissions(Activity activity) {  //动态申请权限（API>=23）
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,PERMISSION,
                    1);
        }
    }
}
