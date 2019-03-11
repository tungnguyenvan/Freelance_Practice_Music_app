package com.example.freelancepracticemusic.views.small.play;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.freelancepracticemusic.R;
import com.example.freelancepracticemusic.models.SongModel;
import com.example.freelancepracticemusic.utils.ImageFromMusicFile;
import com.example.freelancepracticemusic.utils.MusicManager;
import com.example.freelancepracticemusic.views.play.PlayActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class SmallPlay {
    private static final String TAG = SmallPlay.class.getSimpleName();
    private View mView;

    // ui
    private CardView mCard;
    private RelativeLayout mLayout;
    private CircleImageView mImageAvatar;
    private TextView mTxtName, mTxtSinger;
    private ImageView mBtnPrevious, mBtnPause, mBtnNext;

    // listener
    private OnSmallPlayListener mListener;

    // data
    private SongModel mSongModel;

    public SmallPlay(View view, OnSmallPlayListener listener) {
        this.mView = view;
        this.mListener = listener;

        addControls();
        addEvents();
        dismiss();
    }

    private void setButtonPause() {
        if (MusicManager.getInstance().isPlaying()) {
            mBtnPause.setImageDrawable(mView.getResources().getDrawable(R.drawable.ic_pause_black_24dp));
        } else {
            mBtnPause.setImageDrawable(mView.getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
        }
    }

    private void addControls() {
        mCard = mView.findViewById(R.id.small_play_card);
        mLayout = mView.findViewById(R.id.small_play_layout);
        mImageAvatar = mView.findViewById(R.id.small_play_iamge);
        mTxtName = mView.findViewById(R.id.small_play_name);
        mTxtSinger = mView.findViewById(R.id.small_play_txt_singer);
        mBtnPrevious = mView.findViewById(R.id.small_play_btn_previous);
        mBtnPause = mView.findViewById(R.id.small_play_btn_pause);
        mBtnNext = mView.findViewById(R.id.small_play_btn_next);
    }

    private void addEvents() {
        mBtnPrevious.setOnClickListener(v -> {
            // previous
            mListener.onPrevious();
        });

        mBtnPause.setOnClickListener( v -> {
            // pause
            if (MusicManager.getInstance().isPlaying()) {
                MusicManager.getInstance().pause();
            } else {
                MusicManager.getInstance().reStart();
            }

            setButtonPause();
            mListener.onPause();
        });

        mBtnNext.setOnClickListener(v -> {
            // next
            mListener.onNext();
        });

        mLayout.setOnClickListener(v -> {
            Intent iPlay = new Intent(mView.getContext(), PlayActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("data", mSongModel);
            iPlay.putExtra("bundle", bundle);

            mView.getContext().startActivity(iPlay);
        });
    }

    public void setData(SongModel songModel) {
        this.mSongModel = songModel;

        ImageFromMusicFile.getInstance().setImage(mImageAvatar, songModel.getPath());
        mTxtName.setText(songModel.getTitle());
        mTxtSinger.setText(songModel.getArtist());
    }

    public void show() {
        mCard.setVisibility(View.VISIBLE);

        // set image view to play or pause
        setButtonPause();
    }

    public void dismiss() {
        mCard.setVisibility(View.GONE);
    }

    public interface OnSmallPlayListener {
        void onPause();
        void onPrevious();
        void onNext();
    }
}