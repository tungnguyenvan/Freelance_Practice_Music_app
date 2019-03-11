package com.example.freelancepracticemusic.views.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.media.MediaMetadataRetriever;
import android.widget.Toast;

import com.example.freelancepracticemusic.R;
import com.example.freelancepracticemusic.models.SongModel;
import com.example.freelancepracticemusic.utils.ImageFromMusicFile;
import com.example.freelancepracticemusic.utils.MusicManager;

import java.io.IOException;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context mContext;
    private List<SongModel> mSongs;
    private HomeActivityIMP mHomeIMP;

    public HomeAdapter(Context context,HomeActivityIMP homeIMP, List<SongModel> songs) {
        this.mContext = context;
        this.mSongs = songs;
        this.mHomeIMP = homeIMP;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.song_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setArtist(mSongs.get(i).getArtist());
        viewHolder.setName(mSongs.get(i).getTitle());
        viewHolder.setImage(mSongs.get(i).getPath());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private String mPath;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            RelativeLayout layout = itemView.findViewById(R.id.song_item_layout);
            // onclick lambda function
            layout.setOnClickListener(v -> {
                MusicManager.getInstance().play(mPath, new MusicManager.EventMusicManager() {
                    @Override
                    public void onSuccess() {
                        mHomeIMP.onStartMusicSuccess(getAdapterPosition());
                    }

                    @Override
                    public void onFail() {
                        mHomeIMP.onStartMusicFail();
                    }
                });
            });
        }

        public void setName(String name) {
            TextView txtName = itemView.findViewById(R.id.song_item_txt_name);
            txtName.setText(name);
        }

        public void setArtist(String singer) {
            TextView txtSinger = itemView.findViewById(R.id.song_item_txt_singer);
            txtSinger.setText(singer);
        }

        public void setImage(String link) {
            this.mPath = link;
            ImageView imgAvatar = itemView.findViewById(R.id.song_item_image);

            //set image
            ImageFromMusicFile.getInstance().setImage(imgAvatar, mPath);
        }
    }
}
