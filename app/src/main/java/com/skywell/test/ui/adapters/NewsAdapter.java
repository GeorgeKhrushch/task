package com.skywell.test.ui.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skywell.test.R;
import com.skywell.test.data.NewsInfo;
import com.skywell.test.data.attachments.GraphicalAttachments;
import com.skywell.test.data.attachments.PasteAttachment;
import com.skywell.test.data.format.FormatString;
import com.skywell.test.ui.images.LoadListViewImage;
import com.skywell.test.ui.views.ContainerLayout;
import com.skywell.test.ui.views.LikesColorsHolder;
import com.skywell.test.ui.views.RecycleScrollListener;
import com.vk.sdk.api.model.VKApiOwner;
import com.vk.sdk.api.model.VKApiPost;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context mContext;
    private NewsInfo mNewsInfo;
    private LikesColorsHolder mLikesColorsHolder;
    private final RecycleScrollListener mScrollListener;

    public NewsAdapter(Context context, NewsInfo newsInfo, RecycleScrollListener scrollListener) {
        super();
        mContext = context;
        mNewsInfo = newsInfo;
        mScrollListener = scrollListener;

        if(context != null) {
            setHasStableIds(true);
            mLikesColorsHolder = new LikesColorsHolder(mContext);
        }
    }

    @Override
    public long getItemId(int position) {
        try {
            return mNewsInfo.getPosts().get(position).id;
        }catch (NullPointerException | IndexOutOfBoundsException e){
            e.printStackTrace();
            return position;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent, false);
            return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setUpPostView(mNewsInfo.getPosts().get(position), holder);
    }

    private void setUpPostView(final VKApiPost post, final ViewHolder holder) {
        VKApiOwner owner = mNewsInfo.getUsersAndGroups().getMappedOwner(post.from_id);
        if(owner == null)
            return;

        if(post.text.length() == 0 || post.charSequence == null) {
            holder.tvBody.setVisibility(View.GONE);
        } else {
            CharSequence text = post.charSequence.length() == VKApiPost.SEQUENCE_LENGTH ?
                    FormatString.getReadMoreString(mContext, post.charSequence) : post.charSequence;
            holder.tvBody.setText(text);
            holder.tvBody.setVisibility(View.VISIBLE);
        }

        setUpName(owner, post, holder);

        setLikesAndPostsCount(holder, post, mLikesColorsHolder);

        if(post.comments_count == 0){
            holder.buttonCommentsCount.setVisibility(View.INVISIBLE);
        }else{
            holder.buttonCommentsCount.setVisibility(View.VISIBLE);
            holder.buttonCommentsCount.setText(String.valueOf(post.comments_count));
        }

        if(mScrollListener.mIsScrollingDown)
            holder.itemView.post(() -> pasteAttachments(holder, post, mNewsInfo));
        else
            pasteAttachments(holder, post, mNewsInfo);
    }

    public static void setUpName(final VKApiOwner owner, final VKApiPost post, final ViewHolder holder) {
        final Context context = holder.itemView.getContext();
        new AsyncTask<Object, Object, Object>(){

            @Override
            protected Object doInBackground(Object... params) {
                int textSize1 = context.getResources().getDimensionPixelSize(R.dimen.text_size_name);
                int textSize2 = context.getResources().getDimensionPixelSize(R.dimen.text_size_date);

                SpannableString span1 = new SpannableString(owner.name);
                span1.setSpan(new AbsoluteSizeSpan(textSize1), 0, owner.name.length(), 0);

                SpannableString span2 = new SpannableString(post.dateString);
                span2.setSpan(new AbsoluteSizeSpan(textSize2), 0, post.dateString.length(), 0);
                return TextUtils.concat(span1, "\n", span2);
            }

            @Override
            protected void onPostExecute(Object o) {
                holder.tvName.setText((CharSequence) o);
                LoadListViewImage.validLoad(holder.postPhoto, owner.photo_100);
            }
        }.execute();
    }

    private static void removeAttachments(ViewHolder holder){
        holder.mainLayout.setTrueWidth();
        int count = holder.mainLayout.getChildCount();
        if (count > 3) {
            holder.mainLayout.removeViews(2, count - 3);
        }
    }

    public static void pasteAttachments(final ViewHolder holder, final VKApiPost post,
                                        NewsInfo mNewsInfo) {
        removeAttachments(holder);

        if(post.hasAttachments()) {
            PasteAttachment.putSortedAttachments(post.sortedAttachments,
                    holder.mainLayout,
                    mNewsInfo.getUsersAndGroups());

            if(post.copy_history.size() != 0)
                GraphicalAttachments.putPostAttachment(post.copy_history,
                        mNewsInfo.getUsersAndGroups(), holder.mainLayout, false);
        }
    }

    public static void setLikesAndPostsCount(ViewHolder holder, VKApiPost post,
                                             LikesColorsHolder colorsHolder) {
        int likesColor;
        int repostColor;
        holder.buttonLikesCount.setText(String.valueOf(post.likes_count));
        holder.buttonRepostsCount.setText(String.valueOf(post.reposts_count));

        if(post.user_likes)
            likesColor = colorsHolder.likedColor;
        else
            likesColor = colorsHolder.regularColor;

        if(post.user_reposted)
            repostColor = colorsHolder.likedColor;
        else
            repostColor = colorsHolder.regularColor;

        holder.buttonLikesCount.setTextColor(likesColor);
        holder.buttonRepostsCount.setTextColor(repostColor);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewNewsItemName)public TextView tvName;
        @BindView(R.id.textViewNewsItemBody)public TextView tvBody;
        @BindView(R.id.buttonNewsItemLikeCount) public TextView buttonLikesCount;
        @BindView(R.id.buttonNewsItemCommentsCount) public TextView buttonCommentsCount;
        @BindView(R.id.buttonNewsItemRepostCount) public TextView buttonRepostsCount;
        public final ContainerLayout mainLayout;
        @BindView(R.id.imageViewNewsItemAvatar)public CircleImageView postPhoto;

        public ViewHolder(View v) {
            super(v);
            Log.d("Recycled", "construct");
            ButterKnife.bind(this, v);
            mainLayout = (ContainerLayout) itemView;
        }
    }

    @Override
    public int getItemCount() {
        return mNewsInfo.getPosts().size();
    }

    public void setNewsInfo(NewsInfo newsInfo) {
        mNewsInfo = newsInfo;
    }
}