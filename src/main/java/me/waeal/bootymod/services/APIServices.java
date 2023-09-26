package me.waeal.bootymod.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//@Service
public class APIServices {
    private final String KEY = "8b1dc615-f175-4bcf-a5e3-88297c44eb13";

    public JsonObject getActiveProfile(String uuid) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL( "https://api.hypixel.net/skyblock/profiles?key=" + KEY + "&uuid=" + uuid).openStream()));
            for (JsonElement e : JsonParser.parseReader(reader).getAsJsonObject().get("profiles").getAsJsonArray()) {
                if (e.getAsJsonObject().get("selected").getAsBoolean())
                    return e.getAsJsonObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonObject getFast(JsonObject obj, String path) {
        for (String str : path.split("."))
            obj = obj.get(str).getAsJsonObject();

        return obj;
    }
}
