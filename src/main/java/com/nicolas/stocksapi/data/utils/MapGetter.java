package com.nicolas.stocksapi.data.utils;

import java.util.Map;

public class MapGetter<T> {

    public static <T> T parse(Map<String, Object> map, String key, Class<T> type) {
        try {
            Object obj = map.get(key);
            T value = type.cast(obj);
            return value;
        }
        catch (Exception e) {
            ErrorMessage(e, key);
            return null; 
        }
    }

    private static void ErrorMessage(Exception e, String object) {
        System.out.println("Map key '" + object + "' caused error: " + e.getMessage());
    }
}
