package com.vaibhavi.message.adapters;



import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaibhavi.message.model.CallModel;
import com.vaibhavi.message.model.StatusModel;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class PersistenceHelper {
    private static final String PREFS = "wa_prefs";
    private static final String KEY_STATUSES = "key_statuses";
    private static final String KEY_CALLS = "key_calls";

    private final SharedPreferences prefs;
    private final Gson gson;

    public PersistenceHelper(Context ctx) {
        prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Statuses
    public void saveStatusList(List<StatusModel> list) {
        String json = gson.toJson(list);
        prefs.edit().putString(KEY_STATUSES, json).apply();
    }

    public List<StatusModel> loadStatusList() {
        String json = prefs.getString(KEY_STATUSES, null);
        if (json == null) return Collections.emptyList();
        Type t = new TypeToken<List<StatusModel>>(){}.getType();
        return gson.fromJson(json, t);
    }

    // Calls
    public void saveCallList(List<CallModel> list) {
        String json = gson.toJson(list);
        prefs.edit().putString(KEY_CALLS, json).apply();
    }

    public List<CallModel> loadCallList() {
        String json = prefs.getString(KEY_CALLS, null);
        if (json == null) return Collections.emptyList();
        Type t = new TypeToken<List<CallModel>>(){}.getType();
        return gson.fromJson(json, t);
    }
}
