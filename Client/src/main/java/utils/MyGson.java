package utils;

import com.google.gson.Gson;

/**
 * @author Anselm
 * @date 2024/3/18 23 57
 * description
 */

public class MyGson {
    public static Gson gson = new Gson();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(String text, Class<T> tClass) {
        return (T) gson.fromJson(text, tClass);
    }
}
