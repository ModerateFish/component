package com.thornbirds.framework.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by yangli on 2017/9/9.
 */
public class PreferenceUtil {
    public static boolean test() {
        Context context = null;

        context.getExternalFilesDir(null);

        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getFreeSpace();
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ;
    }
}
