package com.rohithvaranasi.goodeath;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.content.pm.PackageManager;
import com.sinch.android.rtc.*;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;

public class Sinch extends CordovaPlugin {
    public static final int CALL_REQ_CODE = 0;
    public static final int PERMISSION_DENIED_ERROR = 20;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;

    private CallbackContext callbackContext;        // The callback context from which we were invoked.
    private JSONArray executeArgs;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        this.executeArgs = args;

        if (action.equals("callNumber")) {
        if (cordova.hasPermission(CALL_PHONE)) {
            callPhone(executeArgs);
        } else {
            getCallPermission(CALL_REQ_CODE);
        }
        } else if (action.equals("isCallSupported")) {
            this.callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, isTelephonyEnabled()));
        } else {
        return false;
        }

        return true;
    }

    private SinchClient sinchClient;

    private void start(JSONArray args){
        String appKey = args.getString(0);
        String appSecret = args.getString(1);
        String host = args.getString(2);
        String userId = args.getString(3);
        Context context = this.cordova.getActivity().getApplicationContext();
        sinchClient = Sinch.getSinchClientBuilder().context(context)
                .applicationKey(appKey)
                .applicationSecret(appSecret)
                .environmentHost(host)
                .userId(userId)
                .build();
        sinchClient.setSupportMessaging(true);
        sinchClient.setSupportCalling(true);
        sinchClient.setSupportActiveConnectionInBackground(true);
        sinchClient.setSupportPushNotifications(true);
        sinchClient.start();
    }

    private void callUser(JSONArray args){
        CallClient callClient = sinchClient.getCallClient();
        Call call = callClient.callUser(args.getString(0));
    }

}