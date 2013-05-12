package com.android.settings.aokp.widgets;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.settings.demented.StatusBarToggles;
import com.android.settings.R;

public class CustomTogglePref extends Preference {

    private StatusBarToggles mParent;

    public CustomTogglePref(Context context) {
        super(context);
        setLayoutResource(R.layout.custom_toggle_pref);
    }
    public CustomTogglePref(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.custom_toggle_pref);
    }

    public void setParent(StatusBarToggles parent){
        mParent = parent;
    }

    @Override
    public void onBindView (View view) {
        super.onBindView(view);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.widget_frame);
        mParent.setupToggleViews(ll);
    }
}
