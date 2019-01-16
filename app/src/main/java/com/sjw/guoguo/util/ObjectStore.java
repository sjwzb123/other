package com.sjw.guoguo.util;

import android.content.Context;

import com.sjw.guoguo.GGApp;

import java.util.HashMap;

public class ObjectStore {
    private static HashMap<String, Object> map = new HashMap<>();
    public static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ObjectStore.context = context;
    }

    public static void add(String key, Object value) {
        map.put(key, value);
    }

    public static Object get(String key) {
        Object o = map.get(key);
        map.remove(key);
        return o;
    }

}
