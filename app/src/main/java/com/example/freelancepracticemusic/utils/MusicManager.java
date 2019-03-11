package com.example.freelancepracticemusic.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class MusicManager {
    private static MusicManager sInstance;
    private static MediaPlayer sMediaPlayer;

    private MusicManager() {
        // Constructor
    }

    public static MusicManager getInstance() {
        if (sInstance == null | sMediaPlayer == null) {
            sInstance = new MusicManager();
            sMediaPlayer = new MediaPlayer();
            sMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        return sInstance;
    }

    public void play(String source, EventMusicManager musicManager) {
        if (sMediaPlayer.isPlaying()) {
            sMediaPlayer.stop();
            sMediaPlayer = null;
            sMediaPlayer = new MediaPlayer();
        }

        try {
            sMediaPlayer.setDataSource(source);
            sMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        reStart();

        if (isPlaying()) musicManager.onSuccess();
        else musicManager.onFail();
    }

    public boolean isPlaying() {
        return sMediaPlayer.isPlaying();
    }

    public void reStart() {
        sMediaPlayer.start();
    }

    public void pause() {
        sMediaPlayer.pause();
    }

    public int getCurrentPosition() {
        return sMediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return sMediaPlayer.getDuration();
    }

    public interface EventMusicManager {
        void onSuccess();
        void onFail();
    }
}