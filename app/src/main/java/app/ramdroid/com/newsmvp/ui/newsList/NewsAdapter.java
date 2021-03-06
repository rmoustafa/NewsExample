package app.ramdroid.com.newsmvp.ui.newsList;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.ArrayList;
import java.util.List;

import app.ramdroid.com.newsmvp.R;
import app.ramdroid.com.newsmvp.data.model.MediaEntity;
import app.ramdroid.com.newsmvp.data.model.NewsEntity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ramadanmoustafa on 5/3/17.
 */

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public interface OnNewsItemClickListener{
        void onNewsItemClicked(String newsTitle);
    }
    private OnNewsItemClickListener mListener;
    private List<NewsEntity> mValues;

    public NewsAdapter(OnNewsItemClickListener listener) {
        mValues = new ArrayList<>();;
        mListener = listener;
    }
    public void updateDataSet(List<NewsEntity> items){
        mValues = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mNewsEntity = mValues.get(position);
        holder.newsTitle.setText(holder.mNewsEntity.getTitle());
        List<MediaEntity> mediaEntityList = holder.mNewsEntity.getMultimedia();
        String thumbnailURL = "";
        if(mediaEntityList != null && mediaEntityList.size()>0) {
            MediaEntity mediaEntity = mediaEntityList.get(0);
            thumbnailURL = mediaEntity.getUrl();
        }

        DraweeController draweeController = Fresco.newDraweeControllerBuilder().setImageRequest(ImageRequest.fromUri
                (Uri.parse(thumbnailURL))).setOldController(holder.imageView.getController()).build();
        holder.imageView.setController(draweeController);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null)
                    mListener.onNewsItemClicked(holder.mNewsEntity.getTitle());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public NewsEntity mNewsEntity;

        @BindView(R.id.news_title)
        TextView newsTitle;
        @BindView(R.id.news_item_image)
        DraweeView imageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + newsTitle.getText() + "'";
        }
    }
}
