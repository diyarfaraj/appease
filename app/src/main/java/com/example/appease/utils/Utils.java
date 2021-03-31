package com.example.appease.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Process;

import static android.app.AppOpsManager.MODE_ALLOWED;

public class Utils {
public static boolean PermissionCheck(Context c){
    AppOpsManager opsManager = (AppOpsManager)c.getSystemService(Context.APP_OPS_SERVICE);
    int mode = opsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,Process.myUid(), c.getPackageName());
    return mode == MODE_ALLOWED;
}
}
