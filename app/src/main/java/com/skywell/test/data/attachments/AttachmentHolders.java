package com.skywell.test.data.attachments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skywell.test.R;
import com.skywell.test.data.format.Regular;
import com.skywell.test.ui.images.LoadListViewImage;
import com.skywell.test.ui.views.ContainerLayout;
import com.vk.sdk.api.model.VKApiLink;
import com.vk.sdk.api.model.VKApiOwner;
import com.vk.sdk.api.model.VKApiPost;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AttachmentHolders {

    public static class PostHolder{
        final Context context;
        final VKApiPost post;
        final boolean showFullInfo;
        final UsersAndGroups usersAndGroups;
        final ContainerLayout container;
        private TextView textViewPostName;
        private TextView textViewPostBody;

        public PostHolder(ContainerLayout container, VKApiPost post, UsersAndGroups usersAndGroups,
                          boolean showFullInfo){
            context = container.getContext();
            this.post = post;
            this.container = container;
            this.showFullInfo = showFullInfo;
            this.usersAndGroups = usersAndGroups;
            setUpTextViews();
            setUpHolder();
        }

        private void setUpTextViews() {
            textViewPostName = new TextView(context, null, R.style.textLarge);
            textViewPostName.setPadding(0, 5, 0, 10);
            textViewPostName.setTextSize(19);
            textViewPostBody = new TextView(context, null, R.style.textSmall);
            textViewPostBody.setTextSize(16);
        }

        private void setUpHolder(){
            VKApiOwner owner = usersAndGroups.getMappedOwner(post.from_id);
            textViewPostName.setText(owner.name);
            addNameView(owner);
            if(post.text.length() == 0) {
                textViewPostBody.setVisibility(View.GONE);
            }else{
                textViewPostBody.setVisibility(View.VISIBLE);

                if(showFullInfo)
                    Regular.findRegular(textViewPostBody, post.text, context);
                else
                    textViewPostBody.setText(post.charSequence);
            }

            container.addView(textViewPostBody, GraphicalAttachments.getValidViewPosition(container));

            if(post.hasAttachments()) {
                PasteAttachment.pasteTopAttachments(post.attachments,
                        container, usersAndGroups);

                if(post.copy_history.size() != 0)
                    GraphicalAttachments.putPostAttachment(post.copy_history,
                            usersAndGroups, container, showFullInfo);
            }
        }

        private void addNameView(VKApiOwner owner){
            int dimen = context.getResources().getDimensionPixelOffset(R.dimen.load_icon_small);
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            CircleImageView circleImageView = new CircleImageView(context);
            linearLayout.addView(circleImageView, dimen, dimen);
            ((ViewGroup.MarginLayoutParams)circleImageView.getLayoutParams()).rightMargin = 10;
            LoadListViewImage.validLoad(circleImageView, owner.photo_100);
            linearLayout.addView(textViewPostName);
            try {
                container.addView(linearLayout, GraphicalAttachments.getValidViewPosition(container));
            }catch (IllegalStateException e){
                container.removeView(textViewPostName);
                container.addView(linearLayout, GraphicalAttachments.getValidViewPosition(container));
            }
        }
    }

    public static void setLength(int duration, TextView textView){
        textView.setText(getDurationString(duration));
    }

    public static String getDurationString(int duration){
        if(duration % 60 > 9) {
            return duration / 60 + ":" + duration % 60;
        }else {
            return duration / 60 + ":0" + duration % 60;
        }
    }

    public static class LinkHolder{
        @BindView(R.id.textViewLinkTitle)public TextView tvTitle;
        @BindView(R.id.imageViewLinkIcon) public ImageView ivIcon;
        private final VKApiLink mVKApiLink;
        private Context mContext;

        public LinkHolder(View view, VKApiLink link) {
            ButterKnife.bind(this, view);
            mVKApiLink = link;
            mContext = view.getContext();
            setUpView();
        }

        private void setUpView(){
            setUpName();
            try {
                String url = mVKApiLink.photo.photo_130.equals("") ?
                        mVKApiLink.photo.photo_75 : mVKApiLink.photo.photo_130;
                if (mVKApiLink.photo != null && !url.equals(""))
                    LoadListViewImage.validLoad(ivIcon, url);
                else
                    removeDrawable();
            }catch (NullPointerException e){
                removeDrawable();
            }

            tvTitle.setOnClickListener(getLinkClickListener(mVKApiLink));
        }

        private void removeDrawable(){
            ivIcon.setVisibility(View.GONE);
        }

        public void setUpName() {
            new AsyncTask<Object, Object, Object>(){
                @Override
                protected Object doInBackground(Object... params) {
                    int colorBlue = mContext.getResources().getColor(R.color.status_bar_color);
                    int colorGray = mContext.getResources().getColor(R.color.dark_gray_text);
                    mVKApiLink.title = cutSpan(mVKApiLink.title);
                    mVKApiLink.description = cutSpan(mVKApiLink.description);
                    SpannableString span1 = new SpannableString(mVKApiLink.title);
                    span1.setSpan(new ForegroundColorSpan(colorBlue),
                            0, mVKApiLink.title.length(), 0);

                    SpannableString span2 = new SpannableString(mVKApiLink.description);
                    span2.setSpan(new ForegroundColorSpan(colorGray),
                            0, mVKApiLink.description.length(), 0);
                    return TextUtils.concat(span1, "\n", span2);
                }

                @Override
                protected void onPostExecute(Object o) {
                    tvTitle.setText((CharSequence) o);
                }
            }.execute();
        }

        private String cutSpan(String span) {
            String sub = span.substring(0, span.length() > 30 ? 30 : span.length());
            if(sub.contains("\n"))
                sub = sub.split("\\n")[0];

            return sub;
        }

        private View.OnClickListener getLinkClickListener(final VKApiLink link) {
            return v -> {
                try {
                    Uri uri = Uri.parse(link.url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException ignored) {}
            };
        }
    }
}
