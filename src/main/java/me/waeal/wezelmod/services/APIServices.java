package me.waeal.wezelmod.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.nullicorn.nedit.type.NBTList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class APIServices {
    private final String KEY = "39e8bbec-1b15-4bd4-a6b6-4a0dcbb49386";// 5e8731bb-3be1-4c67-83e3-c91e1a6aaf30

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
        for (String str : path.split(","))
            obj = obj.get(str).getAsJsonObject();

        return obj;
    }

    public ItemStack getItem(String nbt) {
        try {
            return ItemStack.loadItemStackFromNBT(JsonToNBT.getTagFromJson(nbt));
        } catch (NBTException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ItemStack> getItems(NBTList list) {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
            items.add(getItem(list.getCompound(i).toString()));
        return items;
    }
}
