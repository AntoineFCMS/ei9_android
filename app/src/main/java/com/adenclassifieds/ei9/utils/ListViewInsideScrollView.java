package com.adenclassifieds.ei9.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;

import com.adenclassifieds.ei9.adapter.AdListAdapter;

/**
 * Created by Antoine GALTIER on 17/02/15.
 */
public class ListViewInsideScrollView {


    public static void setListViewInsideAScrollView(ListView list) {
        ListAdapter listAdapter = list.getAdapter();
        if (listAdapter == null || listAdapter.getCount() == 0)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(list.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, list);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, TableLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = list.getLayoutParams();
        params.height = totalHeight + (list.getDividerHeight() * (listAdapter.getCount() - 1));
        list.setLayoutParams(params);
        list.requestLayout();
    }

}
