package com.skywell.test.data.format;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.skywell.test.R;

public class FormatString {

    private static String readMore = "";

    public static String deleteLastChar(String str) {
        if (str != null && str.length() > 0 ) {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }

    public static CharSequence getReadMoreString(Context context, CharSequence main){
        if(readMore.equals(""))
            readMore = context.getResources().getString(R.string.read_more);

        int colorBlue = context.getResources().getColor(R.color.status_bar_color);
        int colorGray = context.getResources().getColor(R.color.main_gray_text);

        SpannableString span1 = new SpannableString(main);
        span1.setSpan(new ForegroundColorSpan(colorGray),
                0, main.length(), 0);

        SpannableString span2 = new SpannableString(readMore);
        span2.setSpan(new ForegroundColorSpan(colorBlue),
                0, readMore.length(), 0);

        return TextUtils.concat(span1, ".. ", span2);
    }
}
