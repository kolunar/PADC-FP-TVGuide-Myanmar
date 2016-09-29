package com.padc.tvguide.utils;

import com.google.gson.Gson;

/**
 * Created by user on 9/24/2016.
 */
public class CommonInstances {

    private static Gson gson = new Gson();

    public static Gson getGsonInstance() {
        return gson;
    }
}
