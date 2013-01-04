package com.android.settings.aokp;

import android.app.ActivityManagerNative;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import net.margaritov.preference.colorpicker.ColorPickerPreference;

public class StatusBarBattery extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String PREF_BATT_BAR = "battery_bar_list";
    private static final String PREF_BATT_BAR_STYLE = "battery_bar_style";
    private static final String PREF_BATT_BAR_COLOR = "battery_bar_color";
    private static final String PREF_BATT_BAR_WIDTH = "battery_bar_thickness";
    private static final String PREF_BATT_ANIMATE = "battery_bar_animate";

    ListPreference mBatteryBar;
    ListPreference mBatteryBarStyle;
    ListPreference mBatteryBarThickness;
    CheckBoxPreference mBatteryBarChargingAnimation;
    ColorPickerPreference mBatteryBarColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getPreferenceManager() != null) {
            getActivity().setTitle(R.string.title_statusbar_battery);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.prefs_statusbar_battery);

            PreferenceScreen prefSet = getPreferenceScreen();

            mBatteryBar = (ListPreference) prefSet.findPreference(PREF_BATT_BAR);
            mBatteryBar.setOnPreferenceChangeListener(this);
            mBatteryBar.setValue((Settings.System
                    .getInt(getActivity().getContentResolver(),
                            Settings.System.STATUSBAR_BATTERY_BAR, 0))
                    + "");

            mBatteryBarStyle = (ListPreference) prefSet.findPreference(PREF_BATT_BAR_STYLE);
            mBatteryBarStyle.setOnPreferenceChangeListener(this);
            mBatteryBarStyle.setValue((Settings.System.getInt(getActivity()
                    .getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_BAR_STYLE, 0))
                    + "");

            mBatteryBarColor = (ColorPickerPreference) prefSet.findPreference(PREF_BATT_BAR_COLOR);
            mBatteryBarColor.setOnPreferenceChangeListener(this);

            mBatteryBarChargingAnimation = (CheckBoxPreference) prefSet.findPreference(PREF_BATT_ANIMATE);
            mBatteryBarChargingAnimation.setChecked(Settings.System.getInt(
                    getActivity().getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_BAR_ANIMATE, 0) == 1);

            mBatteryBarThickness = (ListPreference) prefSet.findPreference(PREF_BATT_BAR_WIDTH);
            mBatteryBarThickness.setOnPreferenceChangeListener(this);
            mBatteryBarThickness.setValue((Settings.System.getInt(getActivity()
                    .getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_BAR_THICKNESS, 1))
                    + "");
            }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference) {
        if (preference == mBatteryBarChargingAnimation) {

            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_BAR_ANIMATE,
                    ((CheckBoxPreference) preference).isChecked() ? 1 : 0);
            return true;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

     @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mBatteryBarColor) {

            String hex = ColorPickerPreference.convertToARGB(Integer
                    .valueOf(String.valueOf(newValue)));
            preference.setSummary(hex);

            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_BAR_COLOR, intHex);
            return true;

        } else if (preference == mBatteryBar) {

            int val = Integer.parseInt((String) newValue);
            return Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_BAR, val);

        } else if (preference == mBatteryBarStyle) {

            int val = Integer.parseInt((String) newValue);
            return Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_BAR_STYLE, val);

        } else if (preference == mBatteryBarThickness) {

            int val = Integer.parseInt((String) newValue);
            return Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_BAR_THICKNESS, val);

        }
        return false;
    }

}
