package com.example.zgk.udpsend;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ZGK on 2019/4/28.
 */

public class ToastUtil {
    static ToastUtil toastUtil;
    Toast toast;
    public static ToastUtil getInstance() {
        if (null == toastUtil) {
            synchronized (ToastUtil.class) {
                toastUtil = new ToastUtil();
            }
        }
        return toastUtil;
    }
    public void show(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
