package com.example.utilmanger.manger;

import android.content.Context;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.utilmanger.R;

public class LayoutManger {

    /**
     *  约束布局动态设置控件位置(要求布局每个view都有id)
     *  ConstraintSet.PARENT_ID 是相对父控件的位置
     *
     * @param constraintlayout  ConstraintLayout根view
     * @param originView        需要动态设置位置的view
     * @param connectView       originView 与 connectView的位置对比
     * @param mContext
     */
    public static void setConstraintLayout(ConstraintLayout constraintlayout, View originView, View connectView, Context mContext) {
        ConstraintSet set = new ConstraintSet();
        set.clone(constraintlayout);
        set.connect(originView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);//(这里也可以  R.id.aaaa)
        set.connect(originView.getId(), ConstraintSet.RIGHT, connectView.getId(), ConstraintSet.RIGHT);//(这里也可以  R.id.aaaa)
        set.connect(originView.getId(), ConstraintSet.TOP, connectView.getId(), ConstraintSet.BOTTOM, ScreenManger.dip2px(mContext, 13));
        set.applyTo(constraintlayout);
    }

}
