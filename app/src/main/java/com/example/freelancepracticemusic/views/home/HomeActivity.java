package com.example.freelancepracticemusic.views.home;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.freelancepracticemusic.R;
import com.example.freelancepracticemusic.models.SongModel;
import com.example.freelancepracticemusic.utils.MusicManager;
import com.example.freelancepracticemusic.utils.SongUtils;
import com.example.freelancepracticemusic.views.play.PlayActivity;
import com.example.freelancepracticemusic.views.small.play.SmallPlay;

import java.io.Serializable;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeActivityIMP, SmallPlay.OnSmallPlayListener, MusicManager.EventMusicManager {
    // define
    private static final int REQUEST_CODE_PLAY = 20;

    // ui
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    // Data
    private List<SongModel> mSongs;
    private HomeAdapter mAdapter;
    private int mCurrentPosition;

    // Small play panel
    private SmallPlay mPlayPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getData();
        addControls();
        addEvents();
    }

    private void addControls() {
        mToolbar = findViewById(R.id.home_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");

        mRecyclerView = findViewById(R.id.home_recycler_view);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        mPlayPanel = new SmallPlay(getWindow().getDecorView(), this);
    }

    private void addEvents() {

    }

    private void getData() {
        mSongs = SongUtils.getInstance(this).getMusic();
        mAdapter = new HomeAdapter(this,this, mSongs);
    }

    @Override
    public void onStartMusicSuccess(int position) {
        this.mCurrentPosition = position;

        Intent iPlay = new Intent(this, PlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", mSongs.get(position));
        iPlay.putExtra("bundle", bundle);
        startActivityForResult(iPlay, REQUEST_CODE_PLAY);

        mPlayPanel.setData(mSongs.get(position));
        mPlayPanel.show();
    }

    @Override
    public void onStartMusicFail() {
        Toast.makeText(this, "Don't start music.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onPrevious() {
        mCurrentPosition -= 1;

        if (mCurrentPosition <= -1) mCurrentPosition = mSongs.size() - 1;

        MusicManager.getInstance().play(mSongs.get(mCurrentPosition).getPath(), this);
    }

    @Override
    public void onNext() {
        mCurrentPosition += 1;

        if (mCurrentPosition >= mSongs.size()) mCurrentPosition = 0;

        MusicManager.getInstance().play(mSongs.get(mCurrentPosition).getPath(), this);
    }

    @Override
    public void onSuccess() {
        this.onStartMusicSuccess(mCurrentPosition);
    }

    @Override
    public void onFail() {
        this.onStartMusicFail();
    }
}
