package com.skywell.test.data.attachments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skywell.test.R;
import com.vk.sdk.api.model.VKApiAudio;

public class ProgramHolders {

    public static class AudioHolder{
        private TextView mTextViewTitle;
        private TextView mTextViewLength;
        private final Context mContext;
        private LinearLayout mLinearLayoutMain;
        private final Drawable mDrawable;
        private final VKApiAudio mVKApiAudio;

        public AudioHolder(Context context, VKApiAudio audio, Drawable audioDrawable) {
            mVKApiAudio = audio;
            mContext = context;
            mDrawable = audioDrawable;
            createView();
            setUpView();
        }

        public LinearLayout getView(){
            mLinearLayoutMain.setPadding(0, 3, 0, 3);
            return mLinearLayoutMain;
        }

        private void createView(){
            mTextViewTitle = new TextView(mContext);
            mTextViewLength = new TextView(mContext);
            mLinearLayoutMain = new LinearLayout(mContext);
            mLinearLayoutMain.setOrientation(LinearLayout.HORIZONTAL);
            mLinearLayoutMain.setGravity(Gravity.CENTER_VERTICAL);
            ImageView imageView = new ImageView(mContext);
            imageView.setImageDrawable(mDrawable);
            mLinearLayoutMain.addView(imageView);
            mLinearLayoutMain.addView(mTextViewTitle, getLayoutParams(5, 1, -2));
            mLinearLayoutMain.addView(mTextViewLength, getLayoutParams(10, 0, -2));
        }

        private LinearLayout.LayoutParams getLayoutParams(int margin, int weight, int width){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, -2);
            params.leftMargin = margin;
            params.weight = weight;
            return params;
        }

        public TextView getTextViewTitle() {
            return mTextViewTitle;
        }

        private void setUpView(){
            mTextViewTitle.setTextColor(mContext.getResources().getColor(R.color.status_bar_color));
            AttachmentHolders.setLength(mVKApiAudio.duration, mTextViewLength);
        }

        public static CharSequence setUpName(VKApiAudio audio, Context context){
            int textSize1 = context.getResources().getDimensionPixelSize(R.dimen.text_size_name);
            int textSize2 = context.getResources().getDimensionPixelSize(R.dimen.text_size_date);

            SpannableString span1 = new SpannableString(audio.artist);
            span1.setSpan(new AbsoluteSizeSpan(textSize1), 0, audio.artist.length(), 0);

            SpannableString span2 = new SpannableString(audio.title);
            span2.setSpan(new AbsoluteSizeSpan(textSize2), 0, audio.title.length(), 0);

            return TextUtils.concat(span1, "\n", span2);
        }
    }
}
