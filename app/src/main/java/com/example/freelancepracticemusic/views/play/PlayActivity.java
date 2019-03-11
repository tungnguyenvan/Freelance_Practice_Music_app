package com.example.freelancepracticemusic.views.play;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.freelancepracticemusic.R;
import com.example.freelancepracticemusic.models.SongModel;
import com.example.freelancepracticemusic.utils.ImageFromMusicFile;
import com.example.freelancepracticemusic.utils.MusicManager;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayActivity extends AppCompatActivity {
    private static final String TAG = PlayActivity.class.getSimpleName();

    // ui
    private Toolbar mToolbar;
    private CircleImageView mImageView;
    private ProgressBar mProgress;
    private TextView mTxtDuration, mTxtPosition;

    // data
    private SongModel mSongModel;

    // util
    SimpleDateFormat mSimpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // get data
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        mSongModel = (SongModel) bundle.getSerializable("data");

        addControls();
        updateTimeMusic();
    }

    private void addControls() {
        // toolbar
        mToolbar = findViewById(R.id.play_toolbar);
        mToolbar.setTitle(mSongModel.getTitle());
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        mToolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        // image view
        mImageView = findViewById(R.id.play_image);
        ImageFromMusicFile.getInstance().setImage(mImageView, mSongModel.getPath());

        // progress bar
        mProgress = findViewById(R.id.play_progress);
        mProgress.setMax(MusicManager.getInstance().getDuration());

        // text view duration
        mTxtDuration = findViewById(R.id.play_txt_duration);
        mSimpleDateFormat = new SimpleDateFormat("mm:ss");
        mTxtDuration.setText(mSimpleDateFormat.format(MusicManager.getInstance().getDuration()));

        mTxtPosition = findViewById(R.id.play_txt_position);
    }

    private void updateTimeMusic() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress.setProgress(MusicManager.getInstance().getCurrentPosition());
                mTxtPosition.setText(mSimpleDateFormat.format(MusicManager.getInstance().getCurrentPosition()));
                handler.postDelayed(this, 500);
            }
        }, 100);
    }
}
