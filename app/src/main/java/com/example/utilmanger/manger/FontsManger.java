package com.example.utilmanger.manger;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

public class FontsManger {

    /**
     * 全局替换 字体
     * @param context
     * @param staticTypefaceFieldName
     * @param fontAssetName
     */
    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }


    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Typeface setTypefaceBz(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/fz_biao_ys.ttf");
    }

    public static Typeface setTypefaceCt(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/fzcysk.ttf");
    }

}

