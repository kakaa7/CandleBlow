package com.example.marryzhi.candleblow;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class lockReceiver extends DeviceAdminReceiver {

    public void onEnabled(Context context,Intent intent){
        super.onEnabled(context,intent);
        Toast.makeText(context,"设备管理器：已激活",Toast.LENGTH_SHORT);
    }
    public void onDisabled(Context context,Intent intent){
        super.onDisabled(context,intent);
        Toast.makeText(context,"设备管理器：未激活",Toast.LENGTH_SHORT);
    }
}
