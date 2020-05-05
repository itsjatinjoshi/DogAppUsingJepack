package com.example.dogappusingjepack.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPreferenceHelper {

    private static final String PREF_TIME ="pref time";
    private static SharedPreferenceHelper instance;
    private SharedPreferences pref;

    private SharedPreferenceHelper(Context context){
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferenceHelper getInstance(Context context){
        if(instance == null){
            instance = new SharedPreferenceHelper(context);
        }
        return instance;
    }

    public void savedUpdateTime(long time){
        pref.edit().putLong(PREF_TIME, time).apply();
    }
    public long getSavedTime(){
       return pref.getLong(PREF_TIME,0);
    }
}
