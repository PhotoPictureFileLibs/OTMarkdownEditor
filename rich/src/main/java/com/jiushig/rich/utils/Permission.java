package com.jiushig.rich.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by zk on 2017/6/16.
 * 权限请求管理
 */

public class Permission {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * SD卡读写权限
     *
     * @param activity
     * @return 是否获得改权限
     */
    public static boolean storage(Activity activity) {
        if (!(ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
            Toast.makeText(activity, "无存储卡读写权限", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
