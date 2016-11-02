package com.vk.sdk.util;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 30.08.2016.
 */
public class RegularEx {
    public static final String regular = "\\[(id|club)(\\d{1,11})\\|([^\\]]+)\\]";

    public static CharSequence findRegular(String text){
        int id;
        String name;
        Pattern p = Pattern.compile(regular, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(text);
        while(matcher.find()) {
            id = Integer.parseInt(matcher.group(2));
            id = matcher.group(1).equals("id") ? id : -id;
            name = matcher.group(3);
            Log.d("Match", name + " " + "id " + id);

            text = matcher.replaceFirst(name);
            matcher = p.matcher(text);
        }
        return text;
    }
}
