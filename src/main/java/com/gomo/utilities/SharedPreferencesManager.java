package com.gomo.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import com.gomo.R;

import java.util.Set;

public class SharedPreferencesManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private String gomoSharedPreferencesKey;

    public SharedPreferencesManager(Context context) {

        gomoSharedPreferencesKey = context.getString(R.string.gomo_shared_preferences);
        this.sharedPreferences = context.getSharedPreferences(gomoSharedPreferencesKey, Context.MODE_PRIVATE);
        this.sharedPreferencesEditor = sharedPreferences.edit();
    }

    public boolean clearAll() {

        try {

            sharedPreferencesEditor.clear();
            sharedPreferencesEditor.commit();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveString(String key, String value) {

        try {

            sharedPreferencesEditor.putString(key, value);
            sharedPreferencesEditor.commit();
            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public String loadString(String key, String defaultValue) {

        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean saveInt(String key, int value) {

        try {

            sharedPreferencesEditor.putInt(key, value);
            sharedPreferencesEditor.commit();
            return true;

        } catch (Exception e) {

            return false;
        }
    }

    public int loadInt(String key, int defaultValue) {

        return sharedPreferences.getInt(key, defaultValue);
    }

    public boolean saveStringSet(String key, Set<String> value) {

        try {

            sharedPreferencesEditor.putStringSet(key, value);
            sharedPreferencesEditor.commit();
            return true;

        } catch (Exception e) {

            return false;
        }

    }

    public Set<String> loadStringSet(String key, Set<String> defaultValue) {

        return sharedPreferences.getStringSet(key, defaultValue);
    }

    public boolean saveBoolean(String key, boolean value) {

        try {

            sharedPreferencesEditor.putBoolean(key, value);
            sharedPreferencesEditor.commit();
            return true;

        } catch (Exception e) {

            return false;
        }

    }

    public boolean loadBoolean(String key, boolean defaultValue) {

        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public boolean remove(String key) {

        try {

            sharedPreferencesEditor.remove(key);
            sharedPreferencesEditor.commit();

            return true;


        } catch (Exception e) {
            return false;
        }
    }
}
