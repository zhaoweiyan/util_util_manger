package com.example.utilmanger.manger;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.example.utilmanger.utils.LogUtils;

import java.lang.reflect.Field;

public class ScreenManger {
    /**
     * 可用--获取状态栏高度方法一
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight1(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 可用-- 获取状态栏高度方法二
     *
     * @param mContext
     * @return
     */
    public static int getStatusBarHeight2(Context mContext) {
        int statusBarHeight = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 可用--获取虚拟导航栏高度---因为方法二失效，经测试，这个方法有效
     *
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight1(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            d.getRealMetrics(realDisplayMetrics);
        }

        int realHeight = realDisplayMetrics.heightPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        LogUtils.d("realHeight=" + realHeight + " displayHeight=" + displayHeight);
        return realHeight - displayHeight;
    }

    //弃用---------获取虚拟按键的高度二 这个判断在三星S9手机上失效，部分手机虚拟导航条的切换会导致导航栏高度变换
    public static int getNavigationBarHeight2(Context context) {
        int result = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 可用--是否隐藏了导航键---因为部分机型适配的原因，所以，多加一层判断  !isNavBarHideKey
     *
     * @param activity
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isNavBarShow(Activity activity) {
        if (!isNavBarHideKey(activity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Display display = activity.getWindowManager().getDefaultDisplay();
                Point size = new Point();
                Point realSize = new Point();
                display.getSize(size);
                display.getRealSize(realSize);
                LogUtils.d("realSize.y=" + realSize.y + " size.y=" + size.y);
                return realSize.y != size.y;
            } else {
                boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
                boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
                if (menu || back) {
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * 可用---获取屏幕高度(整个屏幕高度)
     *
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(dm);
        } else {
            display.getMetrics(dm);
        }
        return dm.heightPixels;
    }

    /**
     * 可用---获取屏幕内容实际高度(整个屏幕高度-顶部状态栏-底部虚拟导航栏)
     *
     * @param activity
     * @return
     */
    public static int getScreenContentHeight(Activity activity) {
        int result = getStatusBarHeight1(activity);
        //底部导航栏高度（部分手机有底部虚拟导航栏） AppUtils.getNavigationBarHeight() 这个判断在三星S9手机上失效，部分手机虚拟导航条的切换会导致导航栏高度变换
        int naviBarHeight = getNavigationBarHeight1(activity);
        int screenHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (isNavBarShow(activity)) {
                screenHeight = getScreenHeight(activity) - naviBarHeight - result;
            } else {
                screenHeight = getScreenHeight(activity) - result;
            }
        } else {
            screenHeight = getScreenHeight(activity);
        }
        return screenHeight;
    }

    /**
     * 可用--- dp转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 可用---设置控件宽高
     *
     * @param mContext
     * @param view
     * @param width
     * @param height
     */
    public static void setViewWidthHeight(Context mContext, View view, int width, int height) {
        LinearLayout.LayoutParams params_1 = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (height != 0) {
            params_1.height = dip2px(mContext, height);
        }
        if (width != 0) {
            params_1.width = dip2px(mContext, width);
        }
        view.setLayoutParams(params_1);
    }


    /**
     * 获取控件高度
     */
    public static int getMeasureHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
    }

    /**
     * 获取控件宽度
     */
    public static int getMeasureWidth(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredWidth();
    }


    /**
     * （内部调用，不暴露在外） 是否隐藏了导航键
     *
     * @param activity
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static boolean isNavBarHideKey(Activity activity) {
        try {
            String brand = Build.BRAND;
            LogUtils.d("dm.heightPixels =" + brand);
            // 这里做判断主要是不同的厂商注册的表不一样
            if (!TextUtils.isEmpty(brand) && (brand.equalsIgnoreCase("VIVO") || brand.equalsIgnoreCase("OPPO"))) {
                return Settings.Secure.getInt(activity.getContentResolver(), getDeviceForceName(), 0) != 0;
            } else if (!TextUtils.isEmpty(brand) && brand.equalsIgnoreCase("Nokia")) {
                //甚至 nokia 不同版本注册的表不一样， key 还不一样。。。
                return Settings.Secure.getInt(activity.getContentResolver(), "swipe_up_to_switch_apps_enabled", 0) == 1
                        || Settings.System.getInt(activity.getContentResolver(), "navigation_bar_can_hiden", 0) != 0;
            } else
                return Settings.Global.getInt(activity.getContentResolver(), getDeviceForceName(), 0) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * （内部调用，不暴露在外）各个手机厂商注册导航键相关的 key
     *
     * @return
     */
    private static String getDeviceForceName() {
        String brand = Build.BRAND;
        if (TextUtils.isEmpty(brand)) return "navigationbar_is_min";
        if (brand.equalsIgnoreCase("HUAWEI") || "HONOR".equals(brand)) {
            return "navigationbar_is_min";
        } else if (brand.equalsIgnoreCase("XIAOMI")) {
            return "force_fsg_nav_bar";
        } else if (brand.equalsIgnoreCase("VIVO")) {
            return "navigation_gesture_on";
        } else if (brand.equalsIgnoreCase("OPPO")) {
            return "hide_navigationbar_enable";
        } else if (brand.equalsIgnoreCase("samsung")) {
            return "navigationbar_hide_bar_enabled";
        } else if (brand.equalsIgnoreCase("Nokia")) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                return "navigation_bar_can_hiden";
            } else {
                return "swipe_up_to_switch_apps_enabled";
            }
        } else {
            return "navigationbar_is_min";
        }
    }
}
