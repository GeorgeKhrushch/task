package com.skywell.test.data.format;

import android.content.Context;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vk.sdk.util.RegularEx;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regular {

    public static void findRegular(final TextView textView, String text, final Context context){
        new AsyncTask<Object, Object, Object>(){

            final Map<String, Integer> namesMap = new HashMap<>();

            @Override
            protected Object doInBackground(Object... params) {
                int id;
                String name;
                SpannableString spannableString;
                String namesList = "(";
                String text = (String) params[0];

                Pattern p = Pattern.compile(RegularEx.regular, Pattern.CASE_INSENSITIVE);
                Matcher matcher = p.matcher(text);

                while(matcher.find()) {
                    id = Integer.parseInt(matcher.group(2));
                    id = matcher.group(1).equals("id") ? id : -id;
                    name = matcher.group(3);
                    Log.d("Match", name + " " + "id " + id);
                    text = matcher.replaceFirst(name);
                    namesList += name + "|";
                    namesMap.put(name, id);
                    matcher = p.matcher(text);
                }

                if(namesMap.size() > 0) {
                    namesList = FormatString.deleteLastChar(namesList) + ")";
                    spannableString = replaceWithSpans(namesList, namesMap, text, context);
                    return spannableString;
                }
                return text;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                CharSequence charSequence = (CharSequence) o;
                if(charSequence.length() == 0)
                    textView.setVisibility(View.GONE);
                else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    textView.setText(charSequence);

                    if(namesMap.size() == 0)
                        Linkify.addLinks(textView, Linkify.WEB_URLS);
                }
            }

        }.execute(text);
    }

    private static SpannableString replaceWithSpans(String namesList, Map<String, Integer> map,
                                                    String text, final Context context){
        Matcher matcherName = Pattern.compile(namesList).matcher(text);
        SpannableString ss = new SpannableString(text);
        while(matcherName.find()) {
            final int finalId = map.get(matcherName.group());
            ss.setSpan(new URLSpan(String.valueOf(finalId)), matcherName.start(), matcherName.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }
}
